#include "Energia.h"
#include "SoftwareSerial.h"
#include "msp430g2553.h"

#define BAUD_RATE 9600
#define COMMAND_LENGTH 2

// most launchpads have a red LED
#define LED P2_1

//see pins_energia.h for more LED definitions
//#define LED GREEN_LED

// the setup routine runs once when you press reset:
void setup() {
    // use frequency 12 MHz
    // BCSCTL1 = CALBC1_12MHZ;
    // DCOCTL = CALDCO_12MHZ;

    // initialize UART
    Serial.begin(BAUD_RATE);


    // initialize the digital pin as an output.
    pinMode(LED, OUTPUT);

    analogWrite(LED, 0);
}

// the loop routine runs over and over again forever:
void loop() {

    // while (Serial.available() <= 0);

    char buffer[COMMAND_LENGTH];

    if (Serial.available() > 0) {
        // read pwm value from UART
        size_t read = Serial.readBytes(buffer, COMMAND_LENGTH);

        if (read == COMMAND_LENGTH) {
            Serial.write(1);
            // ser PWM
            analogWrite(LED, buffer[1]);
        }
    }
}
