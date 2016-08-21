#ifndef PWM_HEADER
#define PWM_HEADER

#include <Energia.h>

#define PWM_TICKS 4096

class PwmController {
    public:
        PwmController(uint8_t address);
        void init(void);
        void setFrequency(float frequency);
        void setPwm(uint8_t pin, uint16_t number);

    private:
        uint8_t i2c_address;

        uint8_t readByte(uint8_t address);
        void writeByte(uint8_t address, uint8_t value);
};

#endif
