/*
 frequency = 12 Mhz
 i2c speed = 100kHz

 divider = 12
*/

#define BOARD_ADDRESS 0x40;
#define PWM_OUT P1OUT
#define PWM_SDA BIT6
#define PWM_SCL BIT7
#define PWM_DIVIDER 12
#define PWM_BUFFER_SIZE 8

void _configure_i2c();

void transmit();