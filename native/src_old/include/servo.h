 
struct servo {
	int out_port;
	int pdm_time;
}
	 
#define PDM_MAX_TIME 2400
#define PDM_MIN_TIME 500
 


#define OUT_PORT    P1OUT
#define OUT_DIR     P1DIR
#define SERVO       BIT6

#define PERIOD      20000
#define TIME_MIN    500
#define TIME_MAX    2400
#define TIME_AVR    1450


void _configure_servos();

