/*
 frequency = 12 Mhz
 divider = 4
 clock rate = 3 000 000

 pwm frequency = 50 Hz (1/50 sec)
 pwm period = 3 000 000 / 50 = 60 000
 timer CCR0 = pwm period / servo count = 60 000 / 2 = 30 000
*/

#include <msp430g2553.h>
#include "servo.h"

#define SRV_COUNT 1
#define PWM_MAX_TIME 7200           // 2400 microsec * 3
#define PWM_MIN_TIME 1500           // 500 microsec * 3
#define PWM_PERIOD_TIME_A 60000     // 1 servo
#define ONE_DEGREE_TIME 32          // (7200 - 1500) / 180

enum out {
    PORT1,
    PORT2
};

struct servo {
    enum out port;
    int bit;
    int pwm_time;
};

struct servo servos[SRV_COUNT];

int cursor = 0;

void configure_servos() {
    // config servos
    servos[0].port = PORT1;
    servos[0].bit = BIT4;
    servos[0].pwm_time = 1000;

    // config pins
    P1DIR |= servos[0].bit;

    // config timer A
    // sub-main clock + 4-divider + up mode + initialize
    TA0CTL = TASSEL_2 + ID_2 + MC_1 + TACLR;
    // set time of pdm one iteration - 1/50 of second
    TA0CCR0 = PWM_PERIOD_TIME_A;
    // enable interruption caused by CCR0 and CCR1 values
    TA0CCTL0 = CCIE;
    TA0CCTL1 = CCIE;

    // enable interruptions
    _BIS_SR(GIE);
}

void set_servo_angle(unsigned char servo_id, unsigned char angle) {
    int time = angle * ONE_DEGREE_TIME + PWM_MIN_TIME;
    servos[servo_id].pwm_time = time;
}

int check_data_range(unsigned char servo_id, unsigned char angle) {
    if (servo_id < 0 || servo_id >= SRV_COUNT) return 0;
    if (angle < 0 || angle > 180) return 0;
    return 1;
}

struct servo * current_servo() {
    return &servos[cursor];
}

struct servo * next_servo() {
    cursor++;
    if (cursor >= SRV_COUNT)
        cursor = 0;

    return current_servo();
}

void servo_pin_on(struct servo *s) {
    switch ((*s).port) {
        case PORT1: P1OUT |= (*s).bit;
            break;
        case PORT2: P2OUT |= (*s).bit;
            break;
    }
}

void servo_pin_off(struct servo *s) {
    switch ((*s).port) {
        case PORT1: P1OUT &= ~(*s).bit;
            break;
        case PORT2: P2OUT &= ~(*s).bit;
            break;
    }
}

#pragma vector = TIMER0_A0_VECTOR
__interrupt void TA_CCR0_ISR(void) {
    struct servo *s = next_servo();
    servo_pin_on(s);
    // set pwm duration time
    TA0CCR1 = (*s).pwm_time;
}

#pragma vector = TIMER0_A1_VECTOR
__interrupt void TA_CCR1_ISR(void) {
    struct servo *s = current_servo();
    // set out pin bit to 0
    servo_pin_off(s);
    // reset interruption flag
    TA0CCTL1 &= ~CCIFG;
}
