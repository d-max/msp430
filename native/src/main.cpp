#include <energia.h>
#include "hardware.h"
#include "system.h"
#include "servo.h"
#include "uart.h"

#define ADDRESS     PCA9685_I2C_ADDRESS
#define FREQUENCY   SERVO_PWM_FREQUENCY
#define SIZE        SERVO_QUANTITY

Uart uart;
Pwm pwm(ADDRESS, FREQUENCY);
Servo * servos[SIZE];
Package package;

void setup() {
    // periphery controllers
    pwm.setup();
    uart.setup();
    // servos controllers
    for (char i = 0; i < SERVO_QUANTITY; i ++) {
        servos[i] = new Servo(i, &pwm);
    }
}

void loop() {
    if (uart.data_available()) {
        uint8_t result = uart.read(&package);
        if (result != 0) {
            servos[package.servo] -> set_angle(package.angle);
        }
        uart.write();
    }
}
