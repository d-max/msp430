/*
 * This code uses software serial library for UART transmission
 * TX - P1.1, RX - P1.2
 */

#include <energia.h>
#include <SoftwareSerial.h>
#include "hardware.h"
#include "uart.h"

void Uart::setup() {
    Serial.begin(BLUETOOTH_BAUD_RATE);
}

bool Uart::data_available() {
    return Serial.available() > 0;
}

uint8_t Uart::read() {
    size_t size = Serial.readBytes(buffer, UART_PACKAGE_LENGTH);
    if (size == UART_PACKAGE_LENGTH) {
        uint8_t angle = buffer[1];
        return angle;
    } else {
        return -1;
    }
}

void Uart::write() {
    Serial.write(1);
}
