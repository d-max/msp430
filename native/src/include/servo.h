 
#define PDM_MAX_TIME 2400 // nano sec
#define PDM_MIN_TIME 500  // nano sec
#define PDM_PERIOD_TIME 20000 // ??? need to calculate

struct servo {
	int out_port;
	int pdm_time;
}
	 
void _configure_servos();
