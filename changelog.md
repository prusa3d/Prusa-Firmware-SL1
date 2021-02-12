# Version 1.5.0-rc.2

## Summary (relative to 1.5.0-rc.1)
- Fix unboxing stucked on finished page

# Version 1.5.0-rc.1

## Summary (relative to 1.5.0-rc.0)
- Fix uvcalibration cleanup on factory reset

# Version 1.5.0-rc.0

## Summary (relative to 1.5.0-beta.0)
- translations update
- Fix upload log with new token
- properly handle the close button on the notification list
- Fix acces to the wifi settings page

# Version 1.5.0-beta.0

## Summary (relative to 1.5.0-alpha.8)
- default backlight 50 %
- fixed strings in slicer and web-ui regarding printer security
- refreshed Dockerfile and gitlab CI scripts (new buildserver in use)
- fixes in web-ui (oneclick is displayed everywhere, show popup window before start print, fixed texts where to find credentials)
- add licences for web-ui
- fix timezone offset in time estimation for web-ui
- New Errors are used to generate unique Prusa style error screens
- printer identification in QR codes
- delete only wifi connections on ethernet (bugfix)
- Open projects are deleted, but still occupy space (bugfix)
- improve reliability due to forking of the preloader (bugfix)
- error codes available in new admin menu
- Error apge new (hopefully final) design with smartphone icon and red
- Avoid repeatedly showing Finishewd page
- Advanced settings merged with settings and deleted (new design)
- notifications are white
- finsihed page and new firmware pages are normal pages. Not in top bar
- do not show notifications on splashscreen
- touch button behaviour unifications
- fixed preloader process -> thread
- fixed saving data into factory partition un uvcalibration

# Version 1.5.0-alpha.8

## Summary (relative to 1.5.0-alpha.7)
- fix firstboot service (dependent on machine-id)
- fix touch-ui can bootup after bootstrap
- fix bootstrap partition order
- New ssh, uart handling
- fix selective silence of avahi logs
- fix default brightness to 100 %
- fix exposure time change (isn't this introducing the delay?)
- new merge setting and advanced settings into one page
- fix sl1fw.service failed to start due to weston framebuffer not started
- new exception page redesing
- fix refresh raucb files on FW update page
- fix unify design QR code pages
- new prusa-errors using yaml

# Version 1.5.0-alpha.7

## Summary (relative to 1.5.0-alpha.6)
- Changed set login button property invisible to disable
- Removed Prusa Connect and filemanager objects
- Fixed reprint for out of range exposure times
- Fixed fixing out of range exposure values
- Fixed project load error handling
- New factory mode switch support
- Updated remote services
- Bump Yocto layers - fixes haveged boot issue
- touch-ui.service: change WorkingDirectory & HOME to /run/touch-ui

# Version 1.5.0-alpha.6

## Summary (relative to 1.5.0-alpha.5)
- Fixed admin based loglevel switching
- Fixed general pritner failure on broken project open
- Make a64-fw a DBus service, improve startup stability
- Fixed http digest enable
- Refactored a64-fw logs
- YAML based prusa error codes
- Final logs upload url
- Fixed unknown preprint warning display
- Fixed a64-fw documentation
- filemanager refactoring
- Fixed Remote API without internet
- Fixed touch-ui: forgotten references to pages that are no longer instantiated
- Fixed touch-ui: automatically pushing PageWizard to the stack if printer0.state changes to WIZARD

# Version 1.5.0-alpha.5

## Summary (relative to 1.5.0-alpha.4)
- Yocto updated to Dunfell (LTS)
- Fix UV calibration boost
- New a64-fw wizard API
- New a64-fw uv display counter
- New a64-fw add statistics
- New a64-fw store old exposure objects. It may allow reprinting 
- Overall codebase cleanup
- Using Lima (gpu driver) instead of mali blob
- Fixed shallow git fetch
- Using Wayland compositor (Weston)
- Prusa connect via Dbus (registration, status reporting and telemetry)
- Fix web-ui one-click (works only if you are ok "Projects" page. To work everywhere needs refactoring)
- Fix slicer upload texts (slices displays more descriptive texts on error)
- Fix web-ui feed me response and project cancel
- Fix web-ui works properly with both http digest and API key
- Fix web-ui notifies when printer is busy or uncalibrated
- New filemanager0 dbus service. It should handle all the files management (for now lists the projects and extracts metadata)
- New admin API (new admin using dbus API. Now works in parallel with the old one)
- Fix a64-fw wait for dbus event from Weston. Still some delay before the picture is shown, but should not be a problem for SL1.
- New a64-fw project format support
- Fix a64-fw deleting projects, wizard resin volume, uv calibration save statistics
- New pages handled by touch-ui (finished, time and date pages, advanced settings, )
- Fix a64-fw cover check while printing
- New logs upload API
older projects. Not used now.
- Fix a64-fw force cover close after resin refill
- Fix uv calibration only one guide (needs to be fixed in touch-ui)
- Fix mc-fw as a submodule of a64-fw
- New mc-fw version 1.0.0 (older MC revisions discontinued)
- Fix a64-fw turn on the NTP on factory reset
- Fix touch-ui rotated since we use Weston
- Fix touch-ui dbus service name
- New touch-ui advanced settings squash
- Fix touch-ui high cpu consumption
- Fix display backlight blink
- Fix web-ui modal window timeout
 
# Version 1.5.0-alpha.4

## Summary (relative to 1.5.0-alpha.3)
- Touch screen blink removal
- Screen saver
- Cancelable log upload/export and FW download
- Harmonize names for Qt (touch-ui)
- Prusa errors specified in separate repository
- RTC tested
- mount USB read only and remount only when needed
- Omaha statistics displays OS version instread of UUID
- Printer summary is exported at the end of the logs
- UV calibration in only one guide
- Printer statistics (total print time, resin consumed, ...)
- Fixed covercheck after resin refill
- Increase serial port write timeout
- Exposition display counter
- Counters of replacements for LED set and expo display
- Fixed http digest
- Add mc-fw and other components as submodules
- wizard API (new unboxing)
- Exposure objects being pickled (can be used after restart)
- Various fixes on dbus API
- Virtual printer moved into folder
- Scripts for tests
- Finished page displayed as notification
- Nemo removal

# Version 1.5.0-alpha.3

## Summary (relative to 1.5.0-alpha.2)
- Fixed ambient temp warning confirm
- Don't hightlight advanced settings items on press
- Shorter timeout for properties reload
- Fixed short flicker back to advancedsettings after save cofiguration page
- Removed reading of config0 value limits from backend
- Disabled switch from firmwareupdate to networksetting page
- Touch-UI code refactoring
- Fixed beeping and highlighting for DelegateRef items
- Hotfix log export/upload
- Fixed beeping on text buttons, notification list
- FIxed caching of PageConfirm messed up with rollUp header->disabled
- Fixed double logs upload call

# Version 1.5.0-alpha.2

## Summary (relative to 1.5.0-alpha.1)
- flush mount option for USB
- custom rm_dir replaced by rmdir (should prevent for deleting files on USB)
- wifi correctly loads nvram and uses host interrupt
- added firstboot recipe (RTC can be tested)
- One click print + model preview
- automatic growfs on dev image
- reading of revision GPIOs rewritten
- prepared support in Yocto for sftp/webDAV/cifs fuse modules
- improved error displayed by slicer when http digest is ON + bugfix
- change log level to debug if admin or factory mode detected
- cancel print does not hang in confirm page
- show correct data (layer num/height, print times, ...) on next print, if previous print was canceled
- changed lower limit of UV PWM since some printers failed in UV claibration
- allow UV meter to reconnect due to EMI surge
- fixed config export in admin
- support for 2mm screw (couldn't home in calibration)
- virtual printer fixed + tests for virtual printer added
- logs upload to sever (only development version TODO: change to production server)
- notification are on correct dbus name cz.prusa3d.sl1.Notify1
- Fixed timezone names
- Fixed text overflow on error page
- show admin while printing
- Rotating clock on wait page
- log upload, export and poweroff via API
- Advanced settings using API
- Added counter for exposition display (both counters can be erased in admin->display)
- Integrated prusa errors
- Fixed preprint check warnings (thrown ASAP)

# Version 1.5.0

## Summary
- Linux 5.6.4
- Patches cleanup regarding HDMI hotplug and eFUSES
- Errors defined in separate repository
- Prevent downgrades (if not in factory mode)
- Set update channel without restart
- Firmware update page facelift
- Option to select stable or beta update channel
- New web UI (upload project, browse both storages, manage print process)
- http digest security (recomended only for LAN)
- New richer remote API
- Popup notifications on touch display
- Backlight control
- Dump printers config while saving the logs

## Fixed bugs
- Reprint from USB

## Others
- Show precise resin volume in resin sensor test


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
