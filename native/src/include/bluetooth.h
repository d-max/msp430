#include <msp430g2553.h>

#define BT_OUT P1OUT
#define TX BIT1
#define RX BIT2
#define CMD_BUFFER_SIZE 8
#define ID_BUFFER_SIZE 3
#define ANGLE_BUFFER_SIZE 4

void _configure_bluetooth();

