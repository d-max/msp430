#include <msp430g2553.h>
#include "servo.h"

void _configure_servos() {
	/* config servos */
	servos[0].port = PORT1;
	servos[0].bit = BIT7;
	servos[0].pwm_time = PWM_MIN_TIME;
	
	servos[1].port = PORT1;
	servos[1].bit = BIT6;
	servos[1].pwm_time = PWM_MIN_TIME;

	servos[2].port = PORT2;
	servos[2].bit = BIT5;
	servos[2].pwm_time = PWM_MAX_TIME;

	// config pins
	P1DIR |= servos[0].bit + servos[1].bit;
	P2DIR |= servos[2].bit;

	/* config timer A */
	// sub-main clock + 4-divider + up mode + initialize
	TA0CTL = TASSEL_2 + ID_2 + MC_1 + TACLR;
	// set time of pdm one iteration - 1/50 of second
	TA0CCR0 = PWM_PERIOD_TIME_A;
	// enable interruption caused by CCR0 and CCR1 values
	TA0CCTL0 = CCIE;
	TA0CCTL1 = CCIE;

	/* config timer B */
	// sub-main clock + 4-divider + up mode + initialize
	TA1CTL = TASSEL_2 + ID_2 + MC_1 + TACLR;
	// set time of pdm one iteration - 1/50 of second
	TA1CCR0 = PWM_PERIOD_TIME_B;
	// enable interruption caused by CCR0 and CCR1 values
	TA1CCTL0 = CCIE;
	TA1CCTL1 = CCIE;
	
	// enable interruptions
	_BIS_SR(GIE);
}

//~

int srv_a_index = 0;
int srv_b_index = SRV_GROUP;

struct servo * next_servo(enum group grp) {
	switch (grp) {
		case GROUP_A:
			srv_a_index ++;
			if (srv_a_index >= SRV_GROUP) srv_a_index = 0;
			break;
		case GROUP_B:
			srv_b_index ++;
			if (srv_b_index >= SRV_COUNT) srv_b_index = SRV_GROUP;
			break;
	}
	return current_servo(grp);
}

struct servo * current_servo(enum group grp) {
	int *index;
	switch (grp) {
		case GROUP_A: index = &srv_a_index; break;
		case GROUP_B: index = &srv_b_index; break;
	}
	return &servos[*index];
}

int angle_to_time(int angle) {
	return angle * ONE_DEGREE_TIME + PWM_MIN_TIME;
}

void servo_pin_on(struct servo *s) {
	switch ((*s).port) {
		case PORT1: P1OUT |= (*s).bit; break;
		case PORT2: P2OUT |= (*s).bit; break;
    }
}

void servo_pin_off(struct servo *s) {
	switch ((*s).port) {
		case PORT1: P1OUT &= ~(*s).bit; break;
		case PORT2: P2OUT &= ~(*s).bit; break;
    }
}

//~

#pragma vector = TIMER0_A0_VECTOR
__interrupt void TA_CCR0_ISR(void) {
    struct servo *s = next_servo(GROUP_A);
    servo_pin_on(s);
	// set pwm duration time
    TA0CCR1 = (*s).pwm_time;
}

#pragma vector = TIMER0_A1_VECTOR
__interrupt void TA_CCR1_ISR(void) {
    struct servo *s = current_servo(GROUP_A);
    // set out pin bit to 0
    servo_pin_off(s);
    // reset interruption flag
    TA0CCTL1 &= ~CCIFG;
}

#pragma vector = TIMER1_A0_VECTOR
__interrupt void TB_CCR0_ISR(void) {
    struct servo *s = next_servo(GROUP_B);
    servo_pin_on(s);
	// set pwm duration time
    TA1CCR1 = (*s).pwm_time;
}

#pragma vector = TIMER1_A1_VECTOR
__interrupt void TB_CCR1_ISR(void) {
    struct servo *s = current_servo(GROUP_B);
    // set out pin bit to 0
    servo_pin_off(s);
    // reset interruption flag
    TA1CCTL1 &= ~CCIFG;
}
