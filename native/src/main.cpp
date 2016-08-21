#include <Arduino.h>
#include <HardwareSerial.h>
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
    Serial1.begin(BT_BAUD_RATE);
    // initialize pwm
    pwmController.init();
    pwmController.setFrequency(SERVO_PWM_FREQUENCY);
}

void loop() {

    if (Serial1.available() > 0) {
        // read command from UART
        size_t read = Serial1.readBytes(buffer, BT_COMMAND_LENGTH);

        if (read == BT_COMMAND_LENGTH) {
            // set pwm value
            uint8_t angle = buffer[1];
            servo.setAngle(angle);
            // send response to UART
            Serial1.write(1);
        }
    }
}
