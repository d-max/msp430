#include <Energia.h>
#include <SoftwareSerial.h>
#include <msp430g2553.h>
#include "Servo.hpp"
#include "PwmController.hpp"

#define BT_BAUD_RATE 9600
#define BT_COMMAND_LENGTH 2

#define PWM_BORAD_ADDRESS 0x40

// #define LED P2_1

PwmController pwmController = PwmController(PWM_BORAD_ADDRESS);
Servo servo = Servo(0, &pwmController);

// the setup routine runs once when you press reset:
void setup() {
    // use frequency 12 MHz
    // BCSCTL1 = CALBC1_12MHZ;
    // DCOCTL = CALDCO_12MHZ;

    // initialize UART
    Serial.begin(BT_BAUD_RATE);

    // initialize pwm
    pwmController.init();

    // // initialize the digital pin as an output.
    // pinMode(LED, OUTPUT);
    //
    // analogWrite(LED, 0);
}

// the loop routine runs over and over again forever:
void loop() {

    pwmController.setPwm(0, 0);

    // while (Serial.available() <= 0);

    // char buffer[BT_COMMAND_LENGTH];
    //
    // if (Serial.available() > 0) {
    //     // read pwm value from UART
    //     size_t read = Serial.readBytes(buffer, BT_COMMAND_LENGTH);
    //
    //     if (read == BT_COMMAND_LENGTH) {
    //         Serial.write(1);
    //         // set PWM
    //         servo.setAngle(buffer[1]);
    //
    //         // analogWrite(LED, buffer[1]);
    //
    //     }
    // }
}
