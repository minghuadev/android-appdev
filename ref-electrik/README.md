
# Adb Setup on Linux


## /etc/udev/rules.d/51-android.rules :

    SUBSYSTEM=="usb",ATTRS{idVendor}=="18d1",MODE="0666"


## Official Link :

http://developer.android.com/tools/device.html 


    SUBSYSTEM=="usb", ATTR{idVendor}=="0bb4", MODE="0666", GROUP="plugdev"


## StackOverflow Link :

http://stackoverflow.com/questions/14460656/android-debug-bridge-adb-device-no-permissions

may needed to add OWNER="myuser" to this line in the UDEV


# Boot Animation 


## Creating bootanimation.zip : 

http://forum.xda-developers.com/showthread.php?t=1852621 how to create and install 
android bootanimations from scratch

To create the file, cd into the data directory and run :

    find . -type f | sort | xargs zip -0 -r ../bootanimation.zip

Or simply 

    zip -0 -r ../bootanimation.zip *

In an example "desc.txt" :

    320 480 8
    p 1 0 part0
    p 1 24 part1
    p 0 0 part2

In the example: 

* 320 480  is the resolution
* 8  is the fps. it shoud not exceed 30
* part0, part1, part2  are the folders which contains images
* p  is a poiter to tell it to look at new commands
* 1, 1, 0  the column specifies the number of times this section will be played. 0 infinite. 
* 0, 24, 0  the column defines pause in seconds before repeating or movingon next line. 
* c  if replacing p means the entire part of the animation will be played even if bootup completes.

To apply, use 

    ./adb push bootanimation.zip /system/media


# Google Play : 

http://getandroidstuff.com/download-google-play-store-designed-jelly-bean/
Download Latest Google Play Store apk v4.5.10
Dec 7 2013 by Khaled Md. Shariar



