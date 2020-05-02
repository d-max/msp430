/*
 * This code uses energia c++ functions to control launchpad builtin LED.
 */

#include <energia.h>
#include "led.hpp"

#define LED RED_LED

Led::Led() {
    pinMode(LED, OUTPUT);
}

void Led::on() {
    digitalWrite(LED, HIGH);
}

void Led::off() {
    digitalWrite(LED, LOW);
}