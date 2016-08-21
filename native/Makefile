SOURCES = src/source
HEADERS = src/include
OUT = build

export OBJ
OBJ += $(OUT)/servo.o
OBJ += $(OUT)/main.o
OBJ += $(OUT)/bluetooth.o
#OBJ += $(OUT)/i2c.o

CC = msp430-gcc
CFLAGS = -g -mmcu=msp430g2553
INCFLAGS = -I/usr/local/include -I$(HEADERS)

LDFLAGS += -T /usr/local/Cellar/msp430-mcu/20120406/msp430/lib/ldscripts/msp430g2553/memory.x

flash: $(OUT)/firmware.elf
	@echo "Flashing";\
	@echo $(IDFLAGS)
	mspdebug rf2500 "prog $(OUT)/firmware.elf"

build: $(OUT)/firmware.elf
	@echo "SUCCESSFUL";\

$(OUT)/firmware.elf: | build-dir $(OBJ)
	@echo "Link firmware";\
	$(CC) $(CFLAGS) $(LDFLAGS) $(OBJ) -o $(OUT)/firmware.elf

$(OUT)/servo.o:	$(SOURCES)/servo.c $(HEADERS)/servo.h
	@echo "Compile $<";\
	$(CC) $(CFLAGS) $(INCFLAGS) -c $(SOURCES)/servo.c -o $(OUT)/servo.o

$(OUT)/bluetooth.o: $(SOURCES)/bluetooth.c #$(HEADERS)/bluetooth.h
	@echo "Compile $<";\
	$(CC) $(CFLAGS) $(INCFLAGS) -c $(SOURCES)/bluetooth.c -o $(OUT)/bluetooth.o

$(OUT)/main.o: $(SOURCES)/main.c $(HEADERS)/*.h
	@echo "Compile $<";\
	$(CC) $(CFLAGS) $(INCFLAGS) -c $(SOURCES)/main.c -o $(OUT)/main.o

$(OUT)/i2c.o: $(SOURCES)/i2c.c $(HEADERS)/*.h
	@echo "Compile $<";\
	$(CC) $(CFLAGS) $(INCFLAGS) -c $(SOURCES)/i2c.c -o $(OUT)/i2c.o

clean:
	rm -rf build

build-dir:
	mkdir -p build

.SILENT: clean build-dir
.PHONY: clean build-dir flash build
