#!/bin/sh

cd "$(dirname "$0")" || exit

avconv -i "$1/images/img-%04d.png" -r 30 -s:v 1280x720 $1/a.mkv

# OR YOU CAN RUN
#avconv -i "img-%4d.png" -r 25 -c:v libx264 -crf 20  -pix_fmt yuv420p ../movie.mov
