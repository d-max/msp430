#include <msp430g2553.h>

#define RXLED BIT6
#define TX BIT1
#define RX BIT2
#define BUFFER_SIZE 10
#define MSG_LEG 'L'
#define MSG_SERVO 'S'
#define MSG_TIME 'T'
#define MSG_PERFORM 'P'


void _configure_bluetooth();

