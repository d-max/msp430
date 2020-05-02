#include "energia.h"
#include "system.hpp"
#include "uart.hpp"
#include "led.hpp"

Led led;

void blink();

void setup() {
    led = Led();
}

void loop() {

    blink();
    blink();
    blink();

}

void blink() {
    led.on();
    System::wait(500);

    led.off();
    System::wait(500);
}
