#!/bin/sh

avconv -i "../build/images/img-%04d.png" -r 30 -s:v 1280x720 ../build/a.mkv



