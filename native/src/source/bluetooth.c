/* 
 frequency = 12 Mhz
 baud rate (uart speed) = 9600

 from table (page 424), UCBR0 = 1250
 UCBR0 = UCA0BR0 + UCA0BR1 * 256
 1250 ~ 255 + 4 * 125 = 1279
*/

#include "bluetooth.h"
#include "servo.h"

struct bt_command {
	int leg;
	int srv;
	int time;
} command;

int head = 0;
char rx_buffer[BUFFER_SIZE];

void message_ready();

void perform_command();

void _configure_bluetooth() {
	// use pins as UART
	P1SEL |= RX + TX;
	P1SEL2 |= RX + TX;
	// init pins
	P1OUT &= ~RX;
	P1OUT &= ~TX;
	/* init UART */
	// use sub-main clock
	UCA0CTL1 |= UCSSEL_2;
	// set uart speed
    UCA0BR0 = 0xFF;
    UCA0BR1 = 0x04;
    // init uart state machine
    UCA0CTL1 &= ~UCSWRST;
    // enable RX interruption
    UC0IE |= UCA0RXIE;
}

#pragma vector = USCIAB0RX_VECTOR
__interrupt void UART_RECEIVE(void) {
	char data = UCA0RXBUF;
	rx_buffer[head++] = data;
    if (data == '\n') {
        int value = atoi(&rx_buffer[0]);
         servos[0].pwm_time = value;
         head = 0;
    }
}
//
//
//	// turn on RX led
//	P1OUT |= RXLED;
//
//	char data = UCA0RXBUF;
//	rx_buffer[head++] = data;
//	if (data == '\n') {
//		// handle message
//		message_ready();
//		// reset buffer
//		head = 0;
//		// send 'ok'
//		UCA0TXBUF = 1;
//	}
//
//	// turn off RX led
//	P1OUT &= ~RXLED;
//}
//
//void message_ready() {
//	char key = rx_buffer[0];
//	int value = atoi(&rx_buffer[1]);
//
//	int value = atoi(&rx_buffer[0]);
//    servos[0].pwm_time = value;

//	switch (key) {
//		case MSG_LEG:
//			command.leg = value;
//			break;
//		case MSG_SERVO:
//			command.srv = value;
//			break;
//		case MSG_TIME:
//			command.time = value;
//			break;
//		case MSG_PERFORM:
//			perform_command();
//			break;
//	}
//}
//
//void perform_command() {
//	servos[command.srv].pwm_time = command.time;
//}

//~ ***********************************

/*
#pragma vector=USCIAB0RX_VECTOR
__interrupt void UART_RECEIVE(void)
{
   if (UCA0RXBUF == 'u') // 'a' received?
   {
      i = 0;
      UC0IE |= UCA0TXIE; // Enable USCI_A0 TX interrupt
      UCA0TXBUF = string[i++];
   }
}

#pragma vector=USCIAB0TX_VECTOR
__interrupt void UART_SEND(void)
{
   UCA0TXBUF = string[i++]; // TX next character
   if (i == sizeof string - 1) // TX over?
      UC0IE &= ~UCA0TXIE; // Disable USCI_A0 TX interrupt
}
*/
