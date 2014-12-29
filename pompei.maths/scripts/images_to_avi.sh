#!/bin/sh

avconv -i "$1/images/img-%04d.png" -r 30 -s:v 1280x720 $1/a.mkv



