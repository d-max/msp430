#ifndef SERVO_HEADER
#define SERVO_HEADER

// #include <Energia.h>
#include <Arduino.h>
#include "PwmController.hpp"

#define SERVO_PWM_FREQUENCY 50

class Servo {
    public:
        Servo(uint8_t pin, PwmController *controller);
        void setAngle(uint8_t angle);
        uint8_t getAngle(void);
        boolean isAngleInRange(uint8_t angle);

    private:
        uint8_t pin;
        uint8_t angle;
        PwmController *pwm;

        void setPwm();
};

#endif
