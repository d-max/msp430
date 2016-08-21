/*
 frequency = 12 Mhz
 baud rate (uart speed) = 9600

 from table (page 424), UCBR0 = 1250
 UCBR0 = UCA0BR0 + UCA0BR1 * 256
 1250 ~ 255 + 4 * 125 = 1279
*/

#include <msp430g2553.h>
#include "uart.h"
#include "servo.h"

#define BT_OUT P1OUT
#define BT_TX BIT1
#define BT_RX BIT2
#define BT_UART_SPEED 0xFF
#define BT_UART_CORRECTION 0x04
#define CMD_BUFFER_SIZE 8
#define BT_RESPONSE_OK 1
#define BT_RESPONSE_FAILED 2

void configure_uart() {
    // use pins as UART
    P1SEL |= BT_RX + BT_TX;
    P1SEL2 |= BT_RX + BT_TX;
    // init pins
    BT_OUT &= ~BT_RX;
    BT_OUT &= ~BT_TX;
    /* init UART */
    // use sub-main clock
    UCA0CTL1 |= UCSSEL_2;
    // set uart speed
    UCA0BR0 = BT_UART_SPEED;
    UCA0BR1 = BT_UART_CORRECTION;
    // init uart state machine
    UCA0CTL1 &= ~UCSWRST;
    // enable RX interruption
    UC0IE |= UCA0RXIE;
}

int head = 0;
char rx_buffer[CMD_BUFFER_SIZE];

void message_ready() {
    int servo_id, angle, *current;
    int begin, end, cursor = 0;
    while (cursor < CMD_BUFFER_SIZE) {
        switch (rx_buffer[cursor++]) {
            case 'S':
                // next chars are servo id
                current = &servo_id;
                begin = cursor;
                end = cursor;
                break;
            case 'A':
                // end of servo id - convert to int
                *current = str_to_int(rx_buffer, begin, end);
                // next chars are angle value
                current = &angle;
                begin = cursor;
                end = cursor;
                break;
            case '\n':
                // end of angle value - convert to int
                *current = str_to_int(rx_buffer, begin, end);
                break;
            default:
                end++;
                break;
        }
    }
    if (check_data_range(servo_id, angle)) {
        // update servo configuration
        set_servo_angle(servo_id, angle);
        // send response
        UCA0TXBUF = BT_RESPONSE_OK;
    } else {
        UCA0TXBUF = BT_RESPONSE_FAILED;
    }
    // enable TX interruption
    UC0IE |= UCA0TXIE;
}

/* converts part of string into integer. range is from begin(inclusive) to end(exclusive) */
int str_to_int(char *string, int begin, int end) {
    int result = 0;
    int count = 0;
    char *p;
    while (begin <= --end) {
        p = &string[end];
        result += (*p - '0') * pow_decimal(count++);
    }
    return result;
}

/* returns 10 ^ degree */
int pow_decimal(int degree) {
    int result = 1;
    int i = 0;
    while (i++ < degree) {
        result = result * 10;
    }
    return result;
}


void uart_data_received(char data) {
    // collect received byte into buffer
    rx_buffer[head++] = data;
    // end of command message
    if (data == '\n') {
        message_ready();
        head = 0;
    }
}

char uart_data_tosend() {

}


// #pragma vector=USCIAB0TX_VECTOR
// __interrupt void USCI0TX_ISR(void) {
//     // disable TX interruptions
//     UC0IE &= ~UCA0TXIE;
// }
