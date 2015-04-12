/* 
 frequency = 12 Mhz
 baud rate (uart speed) = 9600

 from table (page 424), UCBR0 = 1250
 UCBR0 = UCA0BR0 + UCA0BR1 * 256
 1250 ~ 255 + 4 * 125 = 1279
*/

#include "bluetooth.h"
#include "servo.h"

int head = 0;
char rx_buffer[CMD_BUFFER_SIZE];

void message_ready();

void _configure_bluetooth() {
	// use pins as UART
	P1SEL |= RX + TX;
	P1SEL2 |= RX + TX;
	// init pins
	BT_OUT &= ~RX;
	BT_OUT &= ~TX;
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
		message_ready();
		head = 0;
	}
}

void message_ready() {
	// TODO optimize with pointers
	char id[ID_BUFFER_SIZE];
	char angle[ANGLE_BUFFER_SIZE];
	
	int ptr = 0;
	while (rx_buffer[ptr] != '\n') {
		switch (rx_buffer[ptr]) {
			case 'S':
				id[0] = rx_buffer[ptr + 1];
				id[1] = rx_buffer[ptr + 2];
				id[2] = '\n';
				ptr += 3;
				break;	
			case 'A':
				angle[0] = rx_buffer[ptr + 1];
				angle[1] = rx_buffer[ptr + 2];
				angle[2] = rx_buffer[ptr + 3];
				angle[3] = '\n';
				ptr += 4;
				break;
		}
	}
	int srv_id = atoi(&id[0]);
	int time = atoi(&angle[0]) * 32 + PWM_MIN_TIME;
	
	servos[srv_id].pwm_time = time;
}

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
