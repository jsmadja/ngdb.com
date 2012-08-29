#!/bin/bash
cat logs/catalina.* | grep searching | cut -d' ' -f 7,8,9,10,11,12,13,14,15,16 | sort | uniq

