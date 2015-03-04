#include <msp430g2553.h>
#include "servo.h"
//#include "bluetooth.h"

//~

void _configure();

//~

int main(void) {
	// initial mcu config
    _configure();
    
    // config periphery
    _configure_servos();

    // low power mode
    _BIS_SR(LPM0_bits);
}

void _configure() {
	// turn off watchdog
    WDTCTL = WDTPW + WDTHOLD;
    
    // use frequency 12 MHz
    BCSCTL1 = CALBC1_12MHZ;
    DCOCTL = CALDCO_12MHZ;
}
