#include "Servo.hpp"

#define ANGLE_MIN 0
#define ANGLE_MAX 180
#define PWM_TICKS 4095;


uint16_t angleToPwmValue(uint8_t angle) {
    return 4095;
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
}

boolean Servo::isAngleInRange(uint8_t angle) {
    return angle >= ANGLE_MIN && angle <= ANGLE_MAX;
}

void Servo::setPwm() {
    uint16_t pwmValue = angleToPwmValue(this->angle);
    pwm->setPwm(this->pin, pwmValue);
}
