project('usbmount', 'cpp', 'c',
        version : '1',
        license : 'LGPLv2+',
        default_options: [
                'c_std=gnu99',
		'cpp_std=c++17',
                'warning_level=2',
        ],
	meson_version : '>= 0.51',
       )
cc = meson.get_compiler('c')
add_global_arguments('-D_GNU_SOURCE', language : 'c')

libsystemd = dependency('libsystemd')
executable('usbmount', 'usbmount.c', dependencies: [libsystemd], install: true)
executable('usbremount', 'usbremount.cpp', dependencies: [libsystemd], install: true, install_mode: 'rwsr-sr-x')
