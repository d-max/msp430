#include <msp430g2553.h>
#include "i2c.h"

void _configure_i2c() {
    // use pins as UART
    P1SEL |= PWM_SCL + PWM_SDA;
    P1SEL2 |= PWM_SCL + PWM_SDA;
    // init pins
    PWM_OUT &= ~PWM_SCL;
    PWM_OUT &= ~PWM_SDA;
    // I2C Master, synchronous mode
    UCB0CTL0 = UCMST + UCMODE_3 + UCSYNC;
    // Use SMCLK & SW reset
    UCB0CTL1 = UCSSEL_2 + UCSWRST;
    UCB0BR0 = PWM_DIVIDER;
    UCB0BR1 = 0;
    UCB0I2CSA = BOARD_ADDRESS;
    // clear SW reset
    UCB0CTL1 &= ~UCSWRST;
    // enable RX/TX interruptions
    IE2 |= UCB0RXIE + UCB0TXIE;
}

int head = 0;
char tx_buffer[PWM_BUFFER_SIZE];

void transmit() {
    // ensure stop condition got sent
    while (UCB0CTL1 & UCTXSTP);
    // i2c TX, start condition
    UCB0CTL1 |= UCTR + UCTXSTT;
}

#pragma vector = USCIAB0TX_VECTOR
__interrupt void USCIAB0TX_ISR(void) {
    
//    if (Rx == 1) { // Master Recieve?
//        PRxData = UCB0RXBUF; // Get RX data
//        __bic_SR_register_on_exit(CPUOFF); // Exit LPM0
//    }
//    else { // Master Transmit
//        if (TXByteCtr) // Check TX byte counter
//        {
//            UCB0TXBUF = WHO_AM_I; // Load TX buffer
//            TXByteCtr--; // Decrement TX byte counter
//        } else {
//            UCB0CTL1 |= UCTXSTP; // I2C stop condition
//            IFG2 &= ~UCB0TXIFG; // Clear USCI_B0 TX int flag
//            __bic_SR_register_on_exit(CPUOFF); // Exit LPM0
//        }
//    }
}


//void Transmit(void){
//    while (UCB0CTL1 & UCTXSTP);             // Ensure stop condition got sent
//    UCB0CTL1 |= UCTR + UCTXSTT;             // I2C TX, start condition
//    __bis_SR_register(CPUOFF + GIE);        // Enter LPM0 w/ interrupts
//}
//void Receive(void){
//        while (UCB0CTL1 & UCTXSTP);             // Ensure stop condition got sent
//        UCB0CTL1 &= ~UCTR ;                     // Clear UCTR
//        UCB0CTL1 |= UCTXSTT;                    // I2C start condition
//        while (UCB0CTL1 & UCTXSTT);             // Start condition sent?
//        UCB0CTL1 |= UCTXSTP;                    // I2C stop condition
//        __bis_SR_register(CPUOFF + GIE);        // Enter LPM0 w/ interrupts
//}