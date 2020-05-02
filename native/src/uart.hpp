/*
 * UART transmission functions
 */

#include <stdint.h>
#include <stdbool.h>

#define UART_PACKAGE_LENGTH 2

// typedef struct {
//     unsigned char data[UART_PACKAGE_LENGTH];
// } uart_package;

class Uart {
    public:
        void setup();
        bool data_available();
        uint8_t read();
        void write();
    private:
        uint8_t buffer[UART_PACKAGE_LENGTH];
};
