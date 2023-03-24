import subprocess
import os
import time

class AuthKeyRefresher(object):
    _sshPath = "/home/root/.ssh"
    _keyLink = "https://sl1.prusa3d.com/authorized_keys"

    _sshdConfig = '/etc/ssh/sshd_config'

    _serviceLink = "http://sl1.prusa3d.com/ssh-updater.service"
    _timerLink = "http://sl1.prusa3d.com/ssh-updater.timer"


    def checkRequired(self):
        if(os.path.exists('/usr/share/factory/defaults/factory_mode_enabled')):
            return True
        if(os.path.exists('/usr/share/factory/defaults/ssh_enabled')):
            return True
        
        return False


    ################################################################################
    # Refreshes SSH keys with pre-defined URL.
    def write(self):

        try:
            os.mkdir(self._sshPath)
            time.sleep(1)
        except FileExistsError:
            pass

        # Unlock for writting directory
        out = not subprocess.call([f"chmod 755 {self._sshPath}"], shell = True)
        if(not out):
            raise IOError(f"{self._sshPath} not unlocked for writting")

        # Open authorized_keys for writting
        if(os.path.exists(f"{self._sshPath}/authorized_keys")):
            out = not subprocess.call([f"chmod 755 {self._sshPath}/authorized_keys"], shell = True)
            if(not out):
                raise IOError(f"{self._sshPath}/authorized_keys not writtable")
        # Small timeout
        time.sleep(1)

        # Overwrite new authorized keys with old one to refresh permitted users
        out = not subprocess.call([f"wget --directory-prefix={self._sshPath} {self._keyLink}"], shell = True)
        if(not out):
            raise IOError(f"{self._keyLink} not accessible or I/O error on {self._sshPath}")

        # Lock the file for writting
        out = not subprocess.call([f"chmod 600 {self._sshPath}/authorized_keys"], shell = True)
        if(not out):
            raise IOError(f"{self._sshPath}/authorized_keys not properly locked")

        out = not subprocess.call([f"chmod 700 {self._sshPath}"], shell = True)
        if(not out):
            raise IOError(f"{self._sshPath} not locked for writting")
    

    ################################################################################
    # Disables passwordless login for root as well as permits pubkey only
    def disablePasswordless(self):
        # We want to skip this if password is disabled in sshd_config.
        currentState = subprocess.getoutput(f"grep PermitRootLogin {self._sshdConfig}").split('\n')[0]
        if('prohibit-password' in currentState):
            return
        
        subprocess.call([f"cp {self._sshdConfig} {self._sshdConfig}.backup"], shell =  True)

        # Pythonic way
        oldFile = open(self._sshdConfig, 'r')
        data = oldFile.readlines()
        oldFile.close()

        newFile = open(self._sshdConfig, 'w')

        for line in data:
            if(not line.startswith('#') and "PermitRootLogin" in line):
                line = "PermitRootLogin prohibit-password\n"
            
            if('PermitEmptyPasswords yes' in line):
                line = line.replace('yes', 'no')

            if('#PubkeyAuthentication yes' in line):
                line = line.replace('#', '')

            if('#PasswordAuthentication yes' in line):
                line = line.replace('#', '')
                line = line.replace('yes', 'no')
            
            newFile.write(f"{line}")
        newFile.close()

    ################################################################################
    # Reloads SSH service
    def reloadSSH(self):
        subprocess.call([f"systemctl restart sshd@*.service"], shell = True)


    ################################################################################
    # Gets and starts SSH-updater service
    def enableService(self):
        # Get and install service for auto-updating
        out = not subprocess.call([f"wget -O /usr/lib/systemd/system/ssh-updater.service {self._serviceLink} "], shell = True)
        if(not out):
            raise FileNotFoundError("Service for SSH updater not installed")
        
        # Get and install service timer for auto-updating
        out = not subprocess.call([f"wget -O /usr/lib/systemd/system/ssh-updater.timer {self._timerLink} "], shell = True)
        if(not out):
            raise FileNotFoundError("Timer for SSH updater not installed")
        
        # Enable service for auto-updating
        out = not subprocess.call(["systemctl enable ssh-updater.service"], shell = True)
        if(not out):
            raise SystemError("SSH updater not running")
        
        # Enable service timer for auto-updating
        out = not subprocess.call(["systemctl enable ssh-updater.timer"], shell = True)
        if(not out):
            raise SystemError("SSH updater timer not running")
        
        # start service for auto-updating
        out = not subprocess.call(["systemctl start ssh-updater.service"], shell = True)
        if(not out):
            raise SystemError("SSH updater not running")
        
        # start service timer for auto-updating
        out = not subprocess.call(["systemctl start ssh-updater.timer"], shell = True)
        if(not out):
            raise SystemError("SSH updater timer not running")


if __name__ == "__main__":
    akr = AuthKeyRefresher()

    if(not akr.checkRequired()):
        print("SSH not enabled by firmware, exitting")
        os._exit(0)

    akr.write()
    akr.disablePasswordless()
    akr.enableService()
    #akr.reloadSSH()