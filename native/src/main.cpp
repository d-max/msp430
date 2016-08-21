/*
UART:
    TX - P1.1, RX - P1.2
I2C:
    mode1: SDA - P2.2, SCL - P2.1
    mode0: SDA - P1.7, SCL - P1.6
*/

#include <Energia.h>
#include <SoftwareSerial.h>
#include <msp430g2553.h>
#include "Servo.hpp"
#include "PwmController.hpp"

#define BT_BAUD_RATE 9600
#define BT_COMMAND_LENGTH 2
#define PWM_BORAD_ADDRESS 0x40

PwmController pwmController = PwmController(PWM_BORAD_ADDRESS);
Servo servo = Servo(0, &pwmController);
char buffer[BT_COMMAND_LENGTH];

void setup() {
    // initialize UART
    Serial.begin(BT_BAUD_RATE);
    // initialize pwm
    pwmController.init();
    pwmController.setFrequency(SERVO_PWM_FREQUENCY);
}

void loop() {

    if (Serial.available() > 0) {
        // read command from UART
        size_t read = Serial.readBytes(buffer, BT_COMMAND_LENGTH);

        if (read == BT_COMMAND_LENGTH) {
            // set pwm value
            uint8_t angle = buffer[1];
            servo.setAngle(angle);
            // send response to UART
            Serial.write(1);
        }
    }
}
