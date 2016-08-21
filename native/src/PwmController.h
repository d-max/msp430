#include <Energia.h>

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
