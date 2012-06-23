#!/bin/bash

###########################################
# NAME:		wm-new
# AUTHOR:	Linerd (http://tuxtweaks.com), Copyright 2009
# LICENSE:	Creative Commons Attribution - Share Alike 3.0 http://creativecommons.org/licenses/by-sa/3.0/
#		You are free to use and/or modify this script. If you choose to distribute this script, with or
#		without changes, you must attribute credit to the author listed above.
# REQUIRES:	ImageMagick, coreutils
# VERSION:	1.0
# DESCRIPTION:	A script to add a watermark to all images in a directory.
#		Original images will be retained and new watermarked images will be created.
#
###########################################

# Initialize variables
WM=/tmp/Watermark.png  # This is the path to your watermark image
SCALE=100                          # This sets the scale % of your watermark image

# Find all image files in current directory and watermark.
file -i * | grep image | awk -F':' '{ print $1 }' | while read IMAGE
	do
		echo Watermarking $IMAGE
		NAME=`echo $IMAGE | cut -f1 -d.`
		EXT=`echo $IMAGE | cut -f2 -d.`
		composite -dissolve 40% -quality 100 \( $WM -resize $SCALE% \) "$IMAGE" "${NAME}_wm.${EXT}"
	done
exit 0
