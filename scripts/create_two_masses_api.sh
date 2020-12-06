#!/bin/sh

cd "$(dirname "$0")" || exit

OUT=../build/two_masses_4.mov

rm -f ${OUT}

avconv -i "../build/two_masses/img_%5d.png" -r 25 -c:v libx264 -crf 20 -pix_fmt yuv420p ${OUT}

rm -rf ../build/two_masses/
