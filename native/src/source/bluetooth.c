/* 
 frequency = 12 Mhz
 baud rate (uart speed) = 9600

 from table (page 424), UCBR0 = 1250
 UCBR0 = UCA0BR0 + UCA0BR1 * 256
 1250 ~ 255 + 4 * 125 = 1279
*/


#include "bluetooth.h"
#include "servo.h"

void _configure_bluetooth() {
	/* from previous */
	// P2DIR = 0xFF;           // All P2.x outputs
    // P2OUT &= 0x00;          // All P2.x reset
	
	
	// use pins as UART
	P1SEL |= RXD + TXD;
	P1SEL2 |= RXD + TXD;
	// init pins
	P1DIR |= RXLED + TXLED;
	P1OUT &= 0;
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

#pragma vector=USCIAB0RX_VECTOR
__interrupt void UART_RECEIVE(void) {
	// turn on RX led
	P1OUT |= RXLED;
	
	
	
	// turn off RX led
	P1OUT &= ~RXLED;
}

#pragma vector=USCIAB0TX_VECTOR
__interrupt void UART_SEND(void) {
	// turn on TX led
	P1OUT |= TXLED;
	
	
	
	// turn off TX led
	P1OUT &= ~TXLED;
}


//~ ***********************************


// Check buffer
// http://www.ti.com/lit/ug/slau144j/slau144j.pdf
// 434 page 
// UCA1TXIFG
// UCA1RXIFG 

//  buffer
// UCA0TXBUF
// UCA0TXBUF
