#include "bluetooth.h"
#include "servo.h"

void _configure_bluetooth() {
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

int check_data_range(int *servo_id, int *angle) {
    if (*servo_id < 0 || *servo_id >= SRV_COUNT) return 0;
    if (*angle < 0 || *angle > 180) return 0;
    return 1;
}

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
    if (check_data_range(&servo_id, &angle)) {
        // update servo configuration
        servos[servo_id].pwm_time = angle_to_time(angle);
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

//~

#pragma vector = USCIAB0RX_VECTOR
__interrupt void UART_RECEIVE(void) {
    // collect received byte into buffer
    char data = UCA0RXBUF;
    rx_buffer[head++] = data;

    // end of command message
    if (data == '\n') {
        message_ready();
        head = 0;
    }
}

#pragma vector=USCIAB0TX_VECTOR
__interrupt void USCI0TX_ISR(void) {
    // disable TX interruptions
    UC0IE &= ~UCA0TXIE;
}
