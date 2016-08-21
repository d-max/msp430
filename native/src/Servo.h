#include <Energia.h>

class Servo {
    public:
        Servo(uint8_t pin);
        void setAngle(uint8_t angle);
        uint8_t getAngle();
        boolean isAngleInRange(uint8_t angle);

    private:
        uint8_t pin;
        uint8_t angle;
};
