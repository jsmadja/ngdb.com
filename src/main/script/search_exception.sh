#!/bin/bash
for line in $(cat src/main/script/urls.txt); do echo "$line" && curl -s "$line" | grep Exception |wc -l ; done

