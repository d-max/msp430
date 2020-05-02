#include <energia.h>
#include "system.hpp"
#include "uart.hpp"
#include "led.hpp"

Led led;
Uart uart;

void setup() {
    led.setup();
    uart.setup();
}

void loop() {
    if (uart.data_available()) {
        int data = uart.read();
        if (data > 90) led.on(); else led.off();
        uart.write();
    }
}
