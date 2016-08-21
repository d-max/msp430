#ifndef SERVO_HEADER
#define SERVO_HEADER

#include <Energia.h>
#include "PwmController.hpp"

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
