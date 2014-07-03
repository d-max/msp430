#!/bin/bash
src_dir=src/source
bin_dir=build

rm -rf $bin_dir
mkdir $bin_dir

# compile
msp430-gcc -g -mmcu=msp430g2553 -c ${src_dir}/main.c -o ${bin_dir}/main.o
msp430-gcc -g -mmcu=msp430g2553 -o ${bin_dir}/main.elf ${bin_dir}/main.o
# assembler output
#msp430-objdump -DS ${bin_dir}/main.elf > ${bin_dir}/main.lst
#msp430-objcopy -O ihex ${bin_dir}/main.elf ${bin_dir}/main.hex

# old version
#msp430-gcc -mmcu=msp430g2553 ${src_dir}/main.c
