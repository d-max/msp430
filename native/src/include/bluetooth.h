/*
 frequency = 12 Mhz
 baud rate (uart speed) = 9600

 from table (page 424), UCBR0 = 1250
 UCBR0 = UCA0BR0 + UCA0BR1 * 256
 1250 ~ 255 + 4 * 125 = 1279
*/

#include <msp430g2553.h>

#define BT_OUT P1OUT
#define BT_TX BIT1
#define BT_RX BIT2
#define BT_UART_SPEED 0xFF
#define BT_UART_CORRECTION 0x04
#define CMD_BUFFER_SIZE 8
#define BT_RESPONSE_OK 1
#define BT_RESPONSE_FAILED 2

void _configure_bluetooth();