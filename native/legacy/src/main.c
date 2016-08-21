#include <msp430g2553.h>
#include "servo.h"
#include "uart.h"
#include "i2c.h"

void configure() {
    // turn off watchdog
    WDTCTL = WDTPW + WDTHOLD;

    // use frequency 12 MHz
    BCSCTL1 = CALBC1_12MHZ;
    DCOCTL = CALDCO_12MHZ;

    P1OUT = 0;
    P2OUT = 0;
}

int main(void) {
    // initial mcu config
    configure();

    // config periphery
    // configure_uart();
    // configure_servos();
    configure_i2c();

    // for test purpose
    i2c_init_pwm();
    i2c_start_transmition();

    // enable interruptions
    _BIS_SR(GIE);

    // low power mode
    _BIS_SR(LPM0_bits);
}

#pragma vector = USCIAB0RX_VECTOR
__interrupt void usci_rx_isr(void) {
    // uart_data_received();
}

#pragma vector = USCIAB0TX_VECTOR
__interrupt void usci_tx_isr(void) {
    // uart_data_send();
    i2c_data_send();
}

// /* USCI_A0 UART interrupt? */
// if (UC0IFG & UCA0TXIFG)
//         uart_tx_isr();
//
// /* USCI_B0 I2C TX RX interrupt. */
// if ((UCB0CTL0 & UCMODE_3) == UCMODE_3 && (UC0IFG & (UCB0TXIFG | UCB0RXIFG)) != 0)
//         i2c_txrx_isr();
