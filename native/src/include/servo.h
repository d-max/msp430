/*
 frequency = 12 Mhz
 divider = 4
 clock rate = 3 000 000
 
 pwm frequency = 50 Hz (1/50 sec)
 pwm period = 3 000 000 / 50 = 60 000
 timer CCR0 = pwm period / servo count = 60 000 / 2 = 30 000
*/

#define SRV_COUNT 3
#define SRV_GROUP 2				// managed by timer A
#define PWM_MAX_TIME 7200		// 2400 microsec * 3
#define PWM_MIN_TIME 1500		// 500 microsec * 3
#define PWM_PERIOD_TIME_A 30000 // 2 servo
#define PWM_PERIOD_TIME_B 60000 // 1 servo
#define ONE_DEGREE_TIME 32 		// (7200 - 1500) / 180

enum group {
	GROUP_A,
	GROUP_B
};

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

//~

void _configure_servos();

struct servo * current_servo(enum group grp);

struct servo * next_servo(enum group grp);

int angle_to_time(int angle);
