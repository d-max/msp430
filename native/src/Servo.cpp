#include "Servo.h"

#define ANGLE_MIN 0
#define ANGLE_MAX 180

Servo::Servo(uint8_t pin) {
    this->pin = pin;
}

uint8_t Servo::getAngle() {
    return angle;
}

void Servo::setAngle(uint8_t angle) {
    this->angle = angle;
}

boolean isAngleInRange(uint8_t angle) {
    return angle >= ANGLE_MIN && angle <= ANGLE_MAX;
}
