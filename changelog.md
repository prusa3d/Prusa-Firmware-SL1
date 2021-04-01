# Version 1.5.0

## Summary
- QR Error codes
- Redesigned Settings menu
- Advanced Prusa Connect Local
- One-click print functionality
- Top bar notifications
- Firmware upgrade & downgrade
- Backlight and screensaver control
- Project files thumbnails
- Log upload improved
- Print statistics
- Others
- Fixed bugs

This is the first public release candidate of the upcoming firmware 1.5.0. There are major changes to the code, user interface, new features, and multiple improvements across the entire firmware.

Compared to the previous release 1.4.2, there are 819 commits, 267 merge requests, and 38 bug fixes. Kudos to the community for reporting the issues.

### QR Error codes
We always keep working on improvements to the user experience - and the QR Error codes are the next evolution step. Until now, if the printer experienced an error, a short description was displayed on the screen, and the user had to open either the Handbook or search the Prusa knowledgebase (help.prusa3d.com) for a solution.

With this firmware release, the printer will display more information: the name of the error followed by a brief description and a QR code. Take your phone, scan the code, and you will be immediately redirected to an article, which will in detail explain what happened to your printer and how to fix the problem.

Each error code comes with a short URL containing a code. This is useful in case you can't scan the QR code. Open help.prusa3d.com, type in the error code (e.g., #10308), or use the displayed short link, and get access to the required article. Moreover, all the articles are translated into seven languages.

![Error code #10308](https://user-images.githubusercontent.com/31956337/110255285-34f00f80-7f93-11eb-8ee0-bfbf072864ea.png)

How are the codes created? Let's use error #10308 again as an example. The code consists of five numbers with the following pattern: XXYZZ.

- XX -> ID of the printer based on the USB PID (for SL1 it is “10”)
- Y -> the category of the error (3 is used for the electronics errors category)
- ZZ -> specific error code (08 is used for a printer without UV calibration)

More information about the types of printers, categories, and a full list of the error codes can be found in the following [GitHub repository](https://github.com/prusa3d/Prusa-Error-Codes).

The URL link embedded in the QR code may contain an optional "device hash" (this can be enabled/disabled in the Settings -> Settings & Sensors). Including this information enables us to check whether you are running the latest version of firmware - and if not, we will inform you at the top of the article. The information provided in the URL is stored in our database to help us better understand which errors are the most common and how many errors each printer experienced.

Example of the URL in the QR code: https://help.prusa3d.com/en/10308/OI6HB7H6/150
- en -> a language of the landing page with the article
- 10308 -> the unique error code sequence
- OI6HB7H6 -> device hash
- 150 -> firmware version in your 3D printer

![Error page example](https://user-images.githubusercontent.com/31956337/110255442-07f02c80-7f94-11eb-9f34-20dda12ea20e.png)

The device hash cannot be used to recover anything sensitive like a serial number and isn't used for anything more than the QR codes feature. Therefore, there is no reason for security concerns. This feature is turned ON by default. In case you don't want to use this enhanced feature, you can switch it off in the printer's menu by setting the "Device hash in QR" to OFF.

Once you turn it off, the QR code URL will contain only the following: https://help.prusa3d.com/en/10308/

### Redesigned Settings menu
This firmware release contains many new features. More will come in the upcoming versions, which means it was necessary to redesign the settings menu. Individual settings were regrouped and sorted based on their use. Most commonly used are on the top.

![Settings menu](https://user-images.githubusercontent.com/13433018/102616896-9218b980-4138-11eb-9564-83e35e1bdb01.png)

The top-level menu Settings menu now contains:
- _Calibration_ - Contains self-test, axis, and UV calibration and display test. All assisted guides the printer offers.  
- _Network_ - Everything regarding network and **remote access**.
- _Platform & Resin Tank_ - Axis movement and various settings for achieving optimal print results, such as 'Limit for Fast Tilt' or 'Tower Offset.'
- _Firmware_ - New section allowing the user to upgrade/downgrade or choose the 'update channel.' Read further for more detailed info. 
- _Settings & sensors_ - General settings of the printer and Sensors management.
- _Touchscreen_ - New section with backlight control. For more information, read further.
- _Language & Time_ - Localization and time options such as 'Timezone' or 'NTP settings.'
- _System Logs_ - Export logs to the USB or export them to the cloud for easier communication with support in case of any issue.
- _Support_ - Contains 'Download examples,' 'System Information,' and useful links to manuals or videos.

### Advanced Prusa Connect Local
The online web user interface (web UI) called Prusa Connect Local is getting a major update today. In the previous release, the interface was read-only, providing information about the printer without the ability for the user to change anything. Starting with this release, the web UI is getting more advanced, enabling the user to take control over the printer remotely.

![Prusa Connect Local - web UI](https://user-images.githubusercontent.com/31956337/110255559-ada39b80-7f94-11eb-8511-cc676fbe0c2e.png)

The user can browse and manage internal and USB storage, upload new projects simply by dragging the project file into the web page. Prusa Connect Local also supports the start of a new print from web UI and additional print management, such as changing the exposure times or resin refill requests.

The system is now equipped with an HTTP digest for improved printer security, which is set as a default option. In case the user wants to enter the web UI, they must provide login credentials. The username and password can be found and adjusted in the printer's menu -> Settings -> Login credentials.

Besides controlling the printer from the web UI, the user can also use the login credentials in PrusaSlicer 2.3.0 to connect the printer for direct upload of the sliced models. On the home screen called "Plater," click on the cogwheel icon next to the printer and select "Add physical printer." More information is provided in the Prusa knowledge base article.

The HTTP digest can be turned off, which causes the system to use a simpler version of an API key, popular with the OctoPrint solution. However, this option is not recommended due to the low security as the key is sent over the network unencrypted.

Prusa Connect Local is designed to be used on a local (internal) network. It is not recommended to access the printer from the internet over a port forward on the router. Instead, use an encrypted VPN.

### One-click print functionality
Similarly to the Prusa MINI+, the SL1 now offers a user-friendly functionality, where the printer automatically shows up the project ready for immediate print if:
- The USB flash drive with the project was inserted. 
- The project was successfully uploaded from Prusa Slicer or Prusa Connect Local.

![One Click Print](https://user-images.githubusercontent.com/31956337/110256093-49360b80-7f97-11eb-9e19-2ac636db2088.png)

This feature is supported by both touch UI and Prusa Connect Local.

### Top bar notifications
The firmware 1.5.0 introduces a new system of notifications regarding various events, shown in the top bar. There is an 'envelope' icon if any notification is waiting for the user to be displayed. To show all available notifications **swipe down from the top bar** (similar to smartphones). 

![Top bar notifications](https://user-images.githubusercontent.com/13433018/102616481-e96a5a00-4137-11eb-856d-85fb2e3de51e.png)

Currently, three notification types are supported:
- _New firmware update_ - When a new firmware is released, the SL1 automatically informs the user. The printer shows a page with simplified release notes if the user clicks on the notification. Note that your SL1 must be connected to the internet.
- _Project upload_ - if a new project is being uploaded into the printer (from PrusaSlicer or Prusa Connect Local), the notification informs the user about the upload progress. It displays an error if something goes wrong.
- _Result of the print job_ - The printer notifies the user about finished, canceled, or failed print jobs. The printer shows a "finished page" with statistics regarding a particular print job if the user clicks on the notification.

### Firmware upgrade & downgrade
The section of the Settings menu with Firmware update was redesigned and now offers more functionality. It informs the user about the currently installed version and enables a manual check with the Prusa update server.

#### Firmware upgrade
By default, the printer is set to check only for a stable version of the firmware. However, we would like to offer the community a chance to join us in testing and developing the firmware at earlier stages. Starting this release of the firmware, you will be able to switch ON the option "Receive Beta Updates."

* Stable versions
Final and thoroughly tested versions of the firmware recommended for the general audience and production environment. Original Prusa SL1 uses [semantic versioning](https://semver.org/). Stable versions include only three digits, for example, 1.4.2.

* Beta versions
This release is also tested and brings the latest features for the user to enjoy as soon as possible. However, there still might be some bugs and small glitches. It is important to note that even with the beta version, the printer is fully operational and ready to print. For example, the current version (1.5.0-RC.4) is a beta release.

![Firmware upgrade/downgrade page](https://user-images.githubusercontent.com/13433018/102614426-7f03ea80-4134-11eb-9495-880ba08040c8.png)

#### Firmware downgrade
There is a new option enabling the user to downgrade to the previous system version if the newly installed firmware is not working as expected. It is a safer and more convenient option than installing an older version.

The user can downgrade only once from the currently installed version. For example, from 1.5.0-RC.4 to 1.4.2, but further downgrades aren't possible.

### Backlight and screensaver control
On your SL1 printer, you can manually set the backlight intensity. Starting this release, you can also set a timeout used for the screensaver (turn off the display completely). This will come in handy for the overnight prints. The timer can be set from 10 seconds to 30 minutes. To turn ON the screen with an activated screensaver, simply tap the screen.

![Touchscreen backlight settings](https://user-images.githubusercontent.com/13433018/102618329-db6a0880-413a-11eb-8472-58f36b2b7f7c.png)

The default backlight intensity will now be set to 50 %, and the screensaver is by default turned off. The power button stays ON even when the display is turned OFF to indicate that the printer is running.

### Project files thumbnails
For a better user experience, the Original Prusa SL1 displays thumbnails of the project files.

![Project thumbnails](https://user-images.githubusercontent.com/13433018/102622103-df992480-4140-11eb-91c3-b1f1b4fcf50e.png)

### Log upload improved
Previous firmware releases brought an option to extract a system log from the printer, save it on a USB drive and send it to Prusa support for further investigation. This firmware release improves handling the logs one step further.

![Log_upload_finished](https://user-images.githubusercontent.com/31956337/110256422-1db42080-7f99-11eb-95ff-2bc81a111c6c.png)

If the printer is connected to the internet, the user is able to upload the log directly to a dedicated Prusa server. Once the system log is uploaded to the server, a "token" is sent back to the printer and displayed on the screen. Use this token while communicating with the support team. In case you forgot the token, the printer always remembers the one from the latest successful upload.

The structure of the logs archived was also improved. Now the archive contains a folder with the following content:
- _log.txt_ - standard system log split by reboots.
- _summary.json_ - all printer parameters and settings (only the current state)
- _user_data_ folder - contains records from wizards and calibrations in user mode
- _factory_data_ folder - contains records from wizards and calibrations while the printer was in factory mode

### Print statistics
This release extends the printer statistics, which now include items such as: 
- Counters of the usage for exposition display and also UV LED set.
- Statistics regarding prints such as total print time, number of started or successfully finished prints.
- Total amount of consumed resin.

![System information page - printer statistics](https://user-images.githubusercontent.com/13433018/102618741-9a262880-413b-11eb-8845-5b43af135804.png)

# Others
- New Wayland compositor (touch UI is now rotated in the code properly)
- Yocto LTS version Dunfell
- Using Open Source Lima GPU driver instead of BSP Mali blob
- System updated to Linux kernel 5.6.2
- New motion controller version 1.0.0 (older MC revisions discontinued)
- USB flash mounted rear-only and remounted only when needed
- Unboxing uses the new "wizard API." This API will be used to create all guides in the printer
- Cancelable FW download
- UV calibration unified into one guide. Apply boosted results to calibrate 410 um wavelength properly. Allow UV calibrator to reconnect after EMI surge.
- Virtual printer improved + unit tests added
- Preprint warnings are displayed ASAP.

# Fixed bugs
- After resin refill, the printer sometimes didn't ask the user to close the cover. This message is now fixed.
- High CPU usage by touch UI in some scenarios, the bug was fixed, and the usage is now optimized.
- Backlight of the touch display blinked upon the boot-up sequence. This is now fixed.
- Ambient temperature warning wasn't working correctly in some situations. This is now fixed.
- Reprint from USB was misbehaving. The current FW release saves the file to the printer and enables reprint.

# Version 1.4.2

## Summary
- UV calibration algorithm updated
- Fixed cover check after resin refill

## Detailed description

### UV calibration algorithm updated
The SL1 LED array has a wavelength of 405 nm, however, some units might have a slight manufacturing tolerance. This doesn't affect the print itself, but in some scenarios, the UV calibration process wouldn’t finish correctly. The calibration algorithm is now updated and this issue fixed.

### Fixed cover check after resin refill
After resin refilling, the printer should check whether the acrylic lid is closed and if not, inform the user. This feature wasn't always working correctly and is now fixed.

# Version 1.4.1

## Summary
- Fixed missing timezones
- Fixed UV calibrator disconnection
- Fixed data corruption on USB
- Fixed resin volume warning
- Fixed preprint warning delay
- Fixed message "printer is not calibrated" during calibration
- Fixed title overflow on some pages by sliding the title

## Detailed description

### Fixed missing timezones
Based on the customer feedback there were several missing major cities across the world. These cities were added to the list. Also, the underscore sign was removed from the city names.

### Fixed UV calibrator disconnection
In a rare situation, the USB bus had to reconnect the UV calibrator due to the EMI surge. Now, the firmware correctly reconnects the device and calibration will continue without any interruption.

### Fixed data corruption on the USB drive
This release brings improved behavior of handling the USB drives. It is now mounted with a `flush` flag which ensures data are written into the memory sooner. The potential cause of deleting of the files was eliminated by using standard `rmdir` command which will not delete the non-empty directory in any situation.

### Fixed resin volume warning
The Original Prusa SL1 printer is equipped with the resin level sensor, which measures the volume of the resin in the tank before the print is started and compares it to the amount required by the printed model.

During the printing process, the printer calculates the amount of the remaining resin and ensures a minimum level of the resin. If this level is reached, the print stops and the user is asked to refill the tank. This feature wasn't properly working in the 1.4.0 due to the major code refactoring and is fixed in this release.

### Fixed message "printer is not calibrated" during calibration
After replacing the print display and performing the UV calibration or during recalibration using the same display, the user was informed in the very beginning, that the printer is not calibrated, which wasn't true. The printer had at that moment the calibration information of the previous successful calibration. This is now fixed and the message is now not displayed.

## Others
- Fixed scrollbar is now shown only when needed
- Fixed race condition between warnings being displayed
- Fixed hang on cancel print page if HW button was used
- Fixed incorrect data displayed from previous print
- Fixed turn off motors, fans and UV when the exception occurs during print
- Fixed exposure time increment only dispayed for calibration projects
- Translations updated
- Updated company name and copyright
- Improvements to the Wi-Fi service (faster initialization)
- Printer summary printed at the end of the logs
- Silence avahi logs

# Version 1.4.0

## Summary
- User UV calibration
- Parallel pre-print checks
- Manual check for firmware updates
- Use project file name as project name
- Prague is the default timezone
- Simplified fan checks
- Increased allowed intensity deviation
- Improved factory reset
- Various UI improvements
- Upload notifications redesigned

## Detailed description

### User mode UV calibration
Starting this release the system allows for a user recalibration of the UV light intensity using an external UV sensor. Until now, this was possible only in the factory, where both the assembled SL1 printers and printer ktis were calibrated before being shipped. The recalibration is needed every time the 2K printing screen is replaced. The system must set a proper light intensity on the LED in order to achieve the correct level of exposure in the resin tank. Setting an incorrect intensity might under or overexpose the printed object leading to a possible print failure.
To calibrate the UV intensity of the SL1 outside the factory, the developers have created a brand new measuring device, which consists of 15 phototransistors, enabling the printer to measure the intensity in 15 areas and therefore provide a detailed map of the entire print area. First, the intensity is measured in central zones of the screen, then the outer edges are checked.
The calibration is fully automatic, the user only needs to plug the device to the front USB of the SL1, place it on the top of the print display and start the calibration. The entire process won't take more than several minutes.
The UV calibration device is currently being manufactured and will be soon available for preorder in a bundle with a new screen. However, since the device can be used multiple times, the print display will be also available for purchase separately.

### Parallel pre-print checks
To shorten the time before the print is started, some pre-checks now run in parallel. This adjustment saves some time as the hardware setup and logical checks are mostly not blocking each other. A new user interface was created to display all the checks in a unified view.

### Manual check for firmware update
Starting the firmware version 1.3.0 the printer is automatically checking for available updates on the server and displays a notification for the user. Starting this release there is a slight update as the “firmware check” can be also triggered manually from the printer's menu.
An alternative option is to use a USB flash drive and download the firmware using a computer. Stable firmware releases are always available at prusa3d.com/drivers.

## Fixed bugs
- Fixed ethernet reconnecting loop
- Fixed text size on Notification pages
- Fixed handling of malformed language strings
- Fixed reprint without flash drive
- Fixed touch screen refresh rate
- Fixed I/O scheduler setting
- Factory reset for locales and time zones
- Fixed frozen touch ui when FW download failed
- Fixed exception while flashing motion-contoller
- Fixed typos in Polish
- Fixed cyclic returns into to finished page

## Others
- Linux 5.5.10
- Zeus Yocto release
- Stable u-boot v2020.01
- DBus API for basic tasks
- Dependency cleanup: dhcp-client, pygame
- Log CPU, memory usage during print
- Significant code refactoring
- Lots of bug fixes
- Frontend audio play
- UInput support for power button, cover
- Deploy etc slot, change config migration
- Shutdown using systemd
- Distro version in update bundle
- 15 and 60 point UV meter support
- Overwrite few checks for internal use in production

# Version 1.3.3

## Summary
- Fixed UI crash on invalid language string

# Version 1.3.2

## Summary
- Fixed new calibration target UV intensity

# Version 1.3.1

## Summary
- New web interface
- Software licences updated
- Fixed bugs

## Detailed description

### New web interface
The web interface has been completely redesigned. Now, the page displays the printer's telemetry allowing the user to monitor:   
printer status
speed of the fans
status of the cover (lid)
currently printed layer
remaining time of the print
temperatures of the components

For the full list of the features please, access your 3D printer (connected to a local network) via an internet browser. Please note that the page is read-only and does not require any authorization. This enables access from the Apple devices, which were unable to load the page due to a bug in the WebKit browser engine (Safari). A secured version with the ability to control the printer is planned for the future release.

### Software licences updated
The list of the software licences was updated and contains information about the package used in the firmware, its version and respective licence.

## Fixed bugs
- Fixed projects named using numbers only (i.e. "123456789.sl1")
- Fixed fans being disabled during the print process in some cases
- Fixed release notes in new version announcement
- Improved response to network online event
- Fixed calibration model settings
- Fixed and updated translations
- Fixed kit factory calibration
- Fixed screenshots
- Random UI fixes

# Version 1.3.0

## Summary
- New network management
- Phantom click resistant UI
- New way of system update delivery
- Tower profile auto adjustment
- Final stats
- Updated translations (including plurals), images
- Various user interface improvements
- Adjusted fan limits
- Not calibrated warning at startup
- Display language select dialog at startup
- Tower and tilt home separately before print
- Added option to download examples, calibration model

## Detailed description

### New network management
The network is now managed by the NetworkManager. The user interface was updated in order to allow for static address/routes/DNS settings and hidden SSID WiFi networks. Also, the new user interface is more responsive and gives more details while working with WiFi connections.

### Phantom click resistant UI
Critical control paths in the user interface such as print start and print cancel were redesigned in order to be more resistant to phantom touches. This should prevent accidential print cancel on dirty displays.

### New way of system update delivery
As of 1.3 the system updates come with notfications promting the users to install them. This way everyone with the printer connected to the internet will be aware of new the system releases.

### Examples download
An option was added to download example models to the printers internal storage. This allows for downloading fresh examples package that now includes a resin calibration project. This one allows for exposure time optimalization for unknown resins.

## Fixed bugs
- Fixed web UI project name
- More mature fix for initial sound settings
- Fixed overflow in exposure timing
- Improved log export speed
- Tower profile auto adjust
- Fixed reset on cpuburn and wifi scans, raised current limit for ACIN to 3.5A
- Fixed unpacking not to display after user factory reset + do not do packing moves for kit

## Others
- Updated Linux kernel 4.16, fresh userspace
- Not calibrated warning at startup
- Network managed by NetworkManager
- Reworked configuration
- Legacy WiFi settings importer
- Image rendering using Pillow
- Separate user for slicer upload
- Less verbose logging
- Infrastructure for signing with production keys
- GPLv3 License
- Printer0 DBus API
- Client DBus notifications
- Python 3 only system
- Switched io scheduler to bfq

# Version 1.2.0
- Basic kit support (no factory mode, no missing defaults, one step unboxing)
- Export logs as xzipped text with distinguished boots
- Increased slicer upload reserved size
- Fixed screenshot saving
- Fan test as separate page
- Fixed speaker initialization on multiple wizard passes
- Fixed sl1fw part of dynamic language switch
- Fixed upload size limits, timeout period
- Fixed wrongly resized first item on TimeSettingsPage on language changes
- Removed the Up&Down button from print screen, it does not work correctly
- SysInfo LED counter time units changed  s -> hours
- Supressed repeated button presses
- Show project size for each project in source selection page
- Fixed remote_install script
- Suggest update on failed boot
- Top limit for tilt calibraion changed to 6000
- Ask for tank removal before displaytest
- Require lid closure before any tower movements in wizard and calibration
- Added speaker test to wizard
- Fixed disconnected resin sensor check
- Any fast tower movement uses homingFast profile
- Add timezone setup in wizard
- New photos
- Qt GUI cleaanups
- Fixed print display instability
- Translation support, currently only English and Czech are present
- Audio support
- Improved cooling capacity
- Fans RPM control, fan3 poweroff
- Build MC simulatior using CI
- Fixed depcrectaion warining in wizard SN check
- New integration test walking through non-print pages
- Fixed exceptions exposure unitest
- Wait page cleanup
- Fixed print startup error reporting
- MC firmware bump
- Fixed MC revision reading
- UV LED current stored as unitless PWM, rev 0.6c MC board support
- Yes/No replaces confirm where necessary
- Fixed calibration overlay clearing
- Pages separated to standalone source files
- Fixed flow on source select, preprint, print and some admin pages
- Fix print UI updates in case of time change
- Improved dummy Motion controller driver
- Fix save path in case of USB mount failure
- Basic libHardware unittests
- MC simulation for tests,
- Look for error using PyLint, fix all errors
- Fixed setenv usage during u-boot build
- Dockerfile addded to repository
- Redesigned continuous integration tasks
- Factory mode sotred in factory partition
- Fixed UV statistics displaying as NA
- Run the system using Python 3
- Added Dockerfile used for CI builds and tests
- Splited factory calibration and user calibration
- Fix UV statistics displaying as NA
- Added Dockerfile used for CI builds and tests
- Photoshoped pictures of one-screw cantilever
- MQTT: collect 'generic' data right before send
- MQTT: check calibration is done
- MQTT: success confirm in Factory mode wizard
- Test the black display throughput at the end of UV calibration 
- Do not ask whenever to show calibration data at the end of UV Calibration process
- Differentiated factory reset for factor mode and normal mode
- Factory mode indication
- Fixed the infinite test
- Updated photos and texts for calibration
- Fixed the web UI ??? values
- Fixed the web UI live preview loading
- Default tower offset +0.05 mm
- Less strict limits in resin sensor check
- Error injection action in admin menu
- Improved hard error page
- Display soft error page on hostname change error
- Automatic UV LED current calibration with uvledmeter
- Check content length when downloading
- Fixed false-positive change check in advanced settings
- Unittests in tests package
- Fixed crash on failed download of the examples
- Warn user that there are no factory defaults
- UV LED Power Counter & UV LED Time Counter
- Prevent user from setting an invalid hostname, restricted to [a-zA-Z0-9-_]+
- Fixed wrong carry in calculating the end of print time
- Fixed variable picture on the PicturePage

# Version 1.1.2
- Updated MC firmware, ?rev returnes two decimal numbers.
- Support for MC boards rev. 0.6c+
- UV LED current to PWM

# Version 1.1.1
- Fix UI updates at print page right after timezone change

# Version 1.1.0
- Fix UV statistics displaying as NA
- Added Dockerfile used for sl1fw CI builds and tests
- Photoshoped pictures of one-screw cantilever
- MQTT: collect 'generic' data right before send
- MQTT: check calibration is done
- MQTT: success confirm in Factory mode wizard
- Test the black display throughput at the end of UV calibration 
- Do not ask whenever to show calibration data at the end of UV Calibration process
- Different factory reset for factory mode and normal mode
- Factory mode indication in version string
- New MC firmware
- Fixed the infinite test
- Updated photos and texts for calibration
- Fixed the web UI ??? values
- Fixed the web UI live preview loading
- Default tower offset +0.05 mm
- Less strict limits in resin sensor check
- Error injection action in admin menu
- Improved hard error page
- Display soft error page on hostname change error
- Automatic UV LED current calibration with uvledmeter
- Check content length when downloading
- Code cleanup, partial Python 3 compatibility
- Fixed false-positive change check in advanced settings
- Unittests in tests package
- Fixed crash on failed download of the examples
- Warn user that there are no factory defaults
- UV LED Power Counter & UV LED Time Counter
- Prevent user from setting an invalid hostname, restricted to [a-zA-Z0-9-_]+
- Fixed wrong carry in calculating the end of print time
- Fixed variable picture on the PicturePage

# Version 1.0.5
- Increased factory mount timeout
- Disabled serial port
- Bootstrap build target

# Version 1.0.4
- Fixed wifi driver

# Version 1.0.3
- Changed minimal current for UV led in wizard
- Changed UV LED and fan PWMs

# Version 1.0.2
- Fixed wizard step 4 cancel message display
- Fixed wizard back in steps 4 and 5
- Less strict MC serial number check
- Language fixes
- Motion controller FW version 0.9.9-417
- Slicer upload free size limit
- Fixed debug web
- Fix/cleanup tower profiles settings
- More tolerant fan limits, averages in wizard
- Fix confirm lockup in factory reset
- Fix display test use yes/no page layout
- Fix UI being accessible after ended/canceled print
- Add warning for resin sensor and cover check to advanced settings
- Wizard texts updated, added images to unboxing wizard
- Motion controller FW bump to 0.9.9-409-rc0
- Continuous checks the A64 temperature. Starts fans or shutdown if needed.
- Extended factory reset (wifi, hostname, apikey, timezone, config)
- Fix time constant estimation for tower move
- Show tilt time results on calibration end
- YesNo <-- Confirm
- Shuffled rows on SysInfo Page
- Wificonfig uses python3 and supports unicode SSIDs
- New arm trusted firmware

# Version v1.0.0-r1
- Fix crash on empty SN
- Fix USB update
- Updated texts in calibration
- Fix stirring (do not verify positions when stirring but sync after every move)
- Notify user when last boot failed
- Correct YesNo labels

# Version v1.0.0-r0
- Reworked error page
- Page print and page print preview uses raw data
- +/- buttons have fixed swipe protection
- Fix flash mounted detection
- Fixes for wizard and calibration
- Redesigned ambient temperature sensor check
- Unboxing guide, wizard and calibration can be skipped
- New MC firmware (0.9.9-409)
- Wizard check serial numbers
- Changed error page UI
- New admin controls (mute, stirring moves, stirring delay)
- Improved tilt time average
- Warm up before print start no longer supported
- Support for setting first layer height
- Print start checks(ambient temperature, fans, project data, resin)
- Limited re-homing
- Print checks(tilt position, stuck, cover open)
- Improved and fixed page Feed Me
- Fixed print UI (percentage, restart/cancel/end of print)
- Exact time estimation (needs proper Slicer setup)
- Slow and fast tilting times for Slicer in SysInfo page
- Fans running only when needed
- Holding power button for 3 secs invoke power off/cancel print dialog
- Holding power button for 8 secs does hard power off
- Fan check, cover check, fans check and UV LED check added where needed
- Page callbacks
- Tilt sync can be called non-blocking
- Support for beeps in confirm page
- Fix missed leave confirm in advanced settings page
- Store led and fan defaults on factory partition
- Add option to reset fans and LEDs to default
- Project size included in backend data
- Printer serial number in exported log filename
- SysInfo - new look
- Changing size of items to achieve subtle 3D effect
- Reliable rolling of time and timezone
- Calendar font fix
- Removed "locale" from the space_key on the keyboard
- Disabled AutoUppercase for all TextFields
- Bigger QRCode on Videos and Manual page
- NetInfo QRCode is centered again
- New pictograms for wizard, motor sensitivity
- New storage layout, factory partition
- New USB mounting script
- Improved good boot detection

# Version v0.10.7-r3
- Fix wizard button in advanced settings
- Language fixes

# Version v0.10.7-r2
- CI build changes

# Version v0.10.7-r1
- Fix on fan&LEDs back button recover values from actual hwconfig

# Version v0.10.7-r0
- Language and formatting fixes
- New firmware download URLs
- Report serial and version while downloading firmware
- Entering admin protected by we request
- New admin behavior (Off after factory reset, off on start)
- New MC firmware
- Fixed layer profile for success full start print.
- UV led counter and system/user EEPROM space.
- Fans off by default
- Wizard in advanced settings

# Version v0.10.6-r0
- Layer Height -> Thickness
- ApiKey -> credentials fixes
- Client code cleanup
- Preset password for wifi
- Translations support
- New advanced settings page
- Unboxing guide improved
- Upload wizard data on MQTT broker
- Motor sensitivity tuning
- Updated network info page
- Filtered MC logging
- Partial /var fill mitigation
- Fixed apikey refresh
- Progress pages for network firmware download

# Version v0.10.5-r1
- QT App Translation support
- Confirm page swipe away fix
- MC firmware 389b, fixes in wizard and calibration

# Version v0.10.5-r0
- Persistent storage for Slic3r uploads
- New look of Firmware update Page
- Fixed button pictures on Control Page
- Added icon on FeedMe Page
- Removed white square after start
- Added project deletion
- Net update list downloaded from server
- Fixed crash on settings change during print
- Removed beeping during swipe
- Tank reset changed to use release algorithm
- Improved texts in wizard and calibration
- Added fan check before print
- Error reporting for missing USB drive when saving/importing settings
- Fixed number of directory items in project selection
- Fixed USB drive remove before failed homing
- Fixed crash when deleting a project twice
- Fix LED emergency stop
- Added admin menu for saving logs to USB
- Unified firmware and examples download code
- Platform lifted on resin sensor failure
- Motion controller firmware 389a
- Fixed wizard to show only once per boot

# Version v0.10.4-r3
- Fix LED, Temp & Fan names
- Keyboard Prusa Style
- Fix textFields positions when keyboard is pulled up
- Fix resetting date to 30.3.2019
- TurnOff -> Cancel
- Added rear fan check
- Fixed wizard
- Fixed project file sorting
- Fixed invalid project loading
- Fixed layer progress in resin calibrate mode
- Fixed FeedMe Page texts

# Version v0.10.4-r2
- Fixed date setting

# Version v0.10.4-r1
- Better SysInfo page
- Repaired pages Error & FeedMe
- Fixed confirm page swiping
- New calendar color scheme
- Cover check
- autooff settings
- wizard pictures
- "close cover" text on start page
- Wifi off by default
- Extended timezone data
- Fixed firewall, switch to nftables

# Version v0.10.4-r0
- Wizard
- Screenshots
- FeedMe Page in main
- Reduce web to print page only
- Wizard
- Slicer-upload, correct response codes
- Time layout changes
- Sysinfo hw info merged
- Apikey change in  admin -> networking
- New kernel config
- Banned \/ from apikey
- Removed hwclock service
- i18n support in sl1fw

# Version v0.10.3-r1
- Fixed admin enable

# Version v0.10.3-r0
- Initramfs v2
- wic based development images
- revised security
- Calibration fixes
- Fix timezone refresh
- Fix undefined values among the file attributes
- Confirm Page, photo is temporarily pulled to the right
- Conditional admin button
- openssl 1.1.1b - fixes random crashes on fw, examples download

# Version v0.10.2-r0
- Freescale arch timer fix (Linux and uboot)
- Firmware downloads on separate page
- Resize of live preview
- Fix calibration overlay + new overlay auto place
- Fix crash on flash drive remove
- .. directory sorting, fixes
- Small progress bar to indicate exposition
- Removed image version
- Corrected QRCode size
- Fixed timed progress
- Centered qrcode on video page

# Version v0.10.1-r8
- UI fixes, directory sorting

# Version v0.10.1-r7
- Admin examples download

# Version v0.10.1-r6
- Updated MC firmware

# Version v0.10.1-r5
- A bit more proper hot-spot mode fix
- Removing Admin and Network button from the About page, no longer supported
- Smoother swiping in PagePrint

# Version v0.10.1-r4
- Hot-fixed hot-spot mode

# Version v0.10.1-r3
- Advanced settings icons
- MC firmware update
- fixes

# Version v0.10.1-r2
- Fixed videos, handbook page items
- Added SetHostname, SetTimezone page
- Fixed hot-spot dependencies

# Version v0.10.1-r1
- Firmware update fix
- Correct videos, handbook, about URLs

# Version v0.10.1-r0
- text lines on page start
- random fixes
- set time
- new network page
- fix: too long project filenames
- setup split, exposure setup
- fix print buttons on web
- new network configuration
- global fw update
- pre-release UI changes
- page start with fill resin,...
- fix: do not display projects with invalid name
- manual, videos, about URLs 
- u-boot patch for arch timer erratum

# Version v0.10.0-r0
- Initramdisk, separate etc, var partitions

# Version v0.9.4-rc1
- Adjustable tilt
- Backed project sorting
- Fixed dir listing when using multiple sources
- Pictogram for Page Support, header, indicating page
- Sorting of project on the SourceSelect page
- Removing unused pages
- Better PageChange layout
- Working strength indicator for all wifi networks
- Generated images maned acording to system version
- System version based on the most recent tag
- Fixed panic service executable bit

# Version 0.9.4-rc0
- tiltWithStallDetection
- Fixed print cancel
- Layer preview is loaded asynchronously
- Fixed time
- Return to the main after 20s
- Fix api-keygen on first boot
- Removed default project image
- Fixed sysinfo page
- .dwz .sl1 project extension support
- Live preview
- Per partes exposure
- Switching buttons, visibility of the indicator on the confirm page
- Cosmetic changes, temporarily removed unavailable items from the settings page
- Using some of the new pictograms
- Calibration vs Move pages, PrintPage menu, new pictures
- Tower & Tilt move screens are now separate from the Calibration Move & Tilt
- Print page + Estimated End
- New progress bar
- Print - swipable, settings flickable
- Calibration v3
- Backed support for time, timezone, hostname, language
- Provide page data also as raw values where possible
- A64 and MC serial numbers
- cp210x serial support
- admin in print menu

# Version 0.9.3-rc4
- Fixed missing mmap
- Admin in print menu
- Live preview backend

# Version 0.9.3-rc3
- u-boot: mmc delay, legacy mmc mode
- PWM back-light
- python-bitstring included
- Sample multimedia files
- No default wifi configured
- Added power panic response service
- Fixed dev image boot on old systems

# Version 0.9.3-rc2
- Read eth0 MAC from fuses
- Disable MAC randomization
- Disable kernel locking self-test

# Version 0.9.3-rc1
- Calibration v2
- Fix calibration text overflow
- Fix virtual keyboard text size

# Version 0.9.3-rc0
- Fix: MC reset call
- Fix default fans speed
- MC communication in separate object
- Fix firmware update exception processing
- Fix warning while printing octoprint uploaded project
- wifi: signal and connected ssid
- Uboot 2019.1, mmc@26MHz, no boot delay
- Custom Virtual Keyboard style
- Fixes in Qt GUI
- Up&down and final move is to tower top (no homing)
- Resin stirring with fast tilt move
- FeedMe button in homeprint (manual resin fill)
- Fix max octoprint API upload size
- Added socat utility

# Version 0.9.2-rc1
- sl1fw code cleanup
- Patch u-boot environment on update (fixes setups with old environment)
- Replace mmc dev select u-boot walk-around by boot delay

# Version 0.9.2-rc0
- Calibration UI fixes
- Support for rev04 and rev05 MC boards
- Half - auto calibration + infinity test 
- Search for tilt homing profiles and tuning tilt while printing
- Fixed log access
- Added new UI screens (network, printing, support, ...)
- Updated uboot, memtest support
- Basic persistent configuration
- Journald web access on port 19532
