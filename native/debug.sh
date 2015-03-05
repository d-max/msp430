#!/bin/bash
mspdebug rf2500 "gdb" &
sleep 3s
msp430-gdb
#ddd --debugger msp430-gdb test.elf
#ddd msp430-gdb
