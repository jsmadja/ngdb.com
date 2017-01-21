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
SCALE=150                          # This sets the scale % of your watermark image

# Find all image files in current directory and watermark.
file -i `find /ngdb/images/articles -type f` | grep -v "medium\|small\|high" | grep image | awk -F':' '{ print $1 }' | while read IMAGE
	do
		NAME=`echo $IMAGE | cut -f1 -d.`
		EXT=`echo $IMAGE | cut -f2 -d.`
		if [ -f ${NAME}_small.${EXT} ] ; then
			echo "skip $IMAGE"
		else
			echo Watermarking $IMAGE
			composite -dissolve 40% \( $WM -resize $SCALE% \) "$IMAGE" "${NAME}_high.${EXT}"
			convert ${IMAGE} -resize 15% ${NAME}_small.${EXT}
                	convert ${IMAGE} -resize 40% ${NAME}_medium.${EXT}
		fi
	done
exit 0



