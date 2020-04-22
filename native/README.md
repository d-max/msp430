### TI MSP430g2553 MCU firmware.

This firmware created in scope of SCARA robot project. The purpose is controlling several Servos.
MCU receives data over UART and sends over I2C channel number and PWM duty cycle to PCA9685 board.

Pure C implementation.

_Toolset:_
0. [msp430-gcc](http://www.ti.com/tool/MSP430-GCC-OPENSOURCE)
1. [mspdebug](https://dlbeer.co.nz/mspdebug/)
2. cmake
3. make

_Building:_
0. Create a build dir: `mkdir build; cd build`
1. Prepare build system `cmake -DCMAKE_TOOLCHAIN_FILE=../cmake/msp430.cmake ../cmake`
2. Compile and link firmware: `make firmware.elf`
3. Upload firmware to MCU: `make flash` 
4. More targets: `make help`

_Tutorials:_
[UART tutorial](https://www.embeddedrelated.com/showarticle/420.php)
[I2C LCD control example](http://dbindner.freeshell.org/msp430/lcd_i2c.html)
[I2C master example](http://www.kerrywong.com/2012/07/08/msp-exp430g2-i2c-master-examples/)
[I2C sample in russian](http://we.easyelectronics.ru/msp430/ispolzovanie-apparatnogo-i2c-msp430launchpad-i-ez430-f2012-dlya-podklyucheniya-segmentnogo-indikatora-melt-10.html)
