# robotics-native

This is software for scara robot.
Contains native firmware and android application (bluetooth controller).


### Hardware:

Servo: SG90
Bluetooth dongle: HC-06 (UART)
Pwm controller: PCA9685 (I2C)
Main board: TI MSP430G2553


### Firmware:

There are 3 attempts of native firmware implementation
- pure C msp430 register based code
- Arduino code for arduiono micro
- C++ high level code for msp430

These versions are available in different branches.
Pure C implementation is very low level and UART barely works there. Toolchain and firmware is very efficient.
Arduino works well, but requires 7V power.
C++ code for msp430 is using platformio build tools which is arduino/energia port for msp430. Huge binary.

_Last option is chosen as main toolset for alpha version_


### Software:

Currently android application contains main logic of robotic movements (inverse kinematics, commands queue).
Firmware is quite low level and contains code for controlling angle of particular servo.
The first version fo communication protocol is very simple. Package contains 2 unsigned bytes: servo id and angle.
So android app sends over bluetooth packages, mcu receives this data over uart and set pwm time over i2c.
