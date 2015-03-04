#include <msp430g2553.h>
#include "servo.h"

void _configure_servos() {
	/* config timer A */
	// set time of pdm one iteration - 1/50 of second
	TACCR0 = PWM_PERIOD_TIME;
	// enable interruption caused by CCR0 and CCR1 values
	TACCTL0 = CCIE;
	TACCTL1 = CCIE;
	// sub-main clock + 4-divider + up mode + initialize
	TACTL = TASSEL_1 + ID_2 + MC_1 + TACLR;
	// enable interruptions
	_BIS_SR(GIE);
	
	// TODO: add here servos array init
	
	// TODO: add here pin dir setting
	// P1DIR = 1;
	// P1OUT = 0;
}

int srv_index = 0;
struct servo * nextServo() {
	if (++srv_index >= SRV_COUNT) srv_index = 0;
	return currentServo();
}

struct servo * currentServo() {
	return &servos[srv_index];
}

#pragma vector = TIMER0_A0_VECTOR
__interrupt void CCR0_ISR(void) {
    struct servo *s = nextServo();
    // set pwm duration time
    TACCR1 = (*s).pwm_time;
    // set out pin bit to 1 
    switch ((*s).port) {
		case PORT1:
			P1OUT |= (*s).bit;
			break;
		case PORT2:
			P2OUT |= (*s).bit;
			break;
    }
    // reset interruption flag
    TA0CCTL0 &= ~CCIFG;
}

#pragma vector = TIMER0_A1_VECTOR
__interrupt void CCR1_ISR(void) {
    struct servo *s = currentServo();
    // set out pin bit to 0 
    switch ((*s).port) {
		case PORT1:
			P1OUT &= ~(*s).bit;
			break;
		case PORT2:
			P2OUT &= ~(*s).bit;
			break;
    }
    // reset interruption flag
    TA0CCTL1 &= ~CCIFG;
}
