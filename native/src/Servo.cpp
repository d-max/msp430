/*
min servo time = 500 us
max servo time = 2400 us
ticks per second = 4096 * 50 = 204800
min ticks = 204800 / 1000000 * 500 = 103
max ticks = 204800 / 1000000 * 2400 = 492
one degree time = (492 - 103) / 180 = ~2
*/

#include "Servo.hpp"

#define MIN_TIME 103
#define MAX_TIME 492
#define ONE_DEGREE_TIME 2

uint16_t angleToPwmValue(uint8_t angle) {
    return MIN_TIME + ONE_DEGREE_TIME * angle;
}

Servo::Servo(uint8_t pin, PwmController *controller) {
    this->pin = pin;
    this->pwm = controller;
}

uint8_t Servo::getAngle(void) {
    return angle;
}

void Servo::setAngle(uint8_t angle) {
    this->angle = angle;
    this->setPwm();
}

void Servo::setPwm() {
    uint16_t pwmValue = angleToPwmValue(this->angle);
    pwm->setPwm(this->pin, pwmValue);
}
