/*
 * This code wraps energia c++ utility functions
 */

#include <energia.h>
#include "system.h"

void System::wait(uint8_t millis) {
    delay(millis);
}
