#include <energia.h>
#include "hardware.h"
#include "system.h"
#include "servo.h"
#include "uart.h"
#include "led.h"

Led led;
Uart uart;
Pwm pwm(PCA9685_I2C_ADDRESS, SERVO_PWM_FREQUENCY);
Servo servo0 = Servo(0, &pwm);
Servo servo1 = Servo(1, &pwm);
Servo servo2 = Servo(2, &pwm);

void setup() {
    led.setup();
    pwm.setup();
    uart.setup();
}

void loop() {
//    if (uart.data_available()) {
//        int data = uart.read();
//        if (data > 90) led.on(); else led.off();
//        uart.write();
//    }

    servo0.min_angle();
    System::wait(1000);
    servo0.max_angle();
    System::wait(1000);
    servo0.min_angle();
    System::wait(1000);
    servo0.max_angle();

}
