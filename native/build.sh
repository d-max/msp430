#!/bin/bash
if [ ! -f build/Makefile ]; then
    mkdir build
fi

if [ ! -f build/Makefile ]; then
    cd build
    cmake -DCMAKE_TOOLCHAIN_FILE=../cmake/msp430.cmake ../
    cd ..
fi

cd build
make
