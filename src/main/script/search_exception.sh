#!/bin/bash
for line in $(cat urls.txt); do echo "$line" && curl -s "$line" | grep Exception |wc -l ; done

