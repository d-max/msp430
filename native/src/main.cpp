#include <energia.h>
#include "system.h"
#include "uart.h"
#include "led.h"

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
