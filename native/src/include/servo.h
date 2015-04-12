/* 
 frequency = 12 Mhz
 divider = 4
 clock rate = 3 000 000
 
 pwm frequency = 50 Hz (1/50 sec)
 pwm period = 3 000 000 / 50 = 60 000
 timer CCR0 = pwm period / servo count = 60 000 / 2 = 30 000
*/

#define SRV_COUNT 2
#define PWM_MAX_TIME 7200	// 2400 microsec * 3
#define PWM_MIN_TIME 1500	// 500 microsec * 3
#define PWM_PERIOD_TIME 30000

enum out {
	PORT1 = 1,
	PORT2 = 2
};

struct servo {
	int port; // out enum values
	int bit;
	int pwm_time;
};
struct servo servos[SRV_COUNT];

void _configure_servos();

struct servo * currentServo();

struct servo * nextServo();
