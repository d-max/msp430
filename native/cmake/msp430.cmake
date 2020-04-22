include(CMakeForceCompiler)

# The name of the target operating system
set(CMAKE_SYSTEM_NAME Generic)
set(MSP_TOOLCHAIN 1)

# The location of the msp430-elf-gcc toolchain. This may vary and need to be modified.
set(MSP430_PATH /Users/maksym/bin/ti/msp430-gcc)

# Specify the cross compiler
set(CMAKE_C_COMPILER ${MSP430_PATH}/bin/msp430-elf-gcc)
set(CMAKE_CXX_COMPILER ${MSP430_PATH}/bin/msp430-elf-g++)
set(CMAKE_C_COMPILER_ENV_VAR CC)
set(CMAKE_CXX_COMPILER_ENV_VAR CXX)

# Include The header directory
include_directories(SYSTEM ${MSP430_PATH}/include ${MSP430_PATH}/msp430-elf/include)

# Specify linker script folder
set(CMAKE_EXE_LINKER_FLAGS "-L ${MSP430_PATH}/include")

# Create a function that will instantiate a flash target command using mspdebug.
# you may have to set the path to mspdebug if it is different. In this case we are
# assuming mspdebug is in the system PATH
set(MSPDEBUG_PATH mspdebug)

function(setup_flash_target TARGET_NAME DRIVER)
    add_custom_target(flash
        COMMAND ${MSPDEBUG_PATH} ${DRIVER} --allow-fw-update 'prog ${TARGET_NAME}'
        DEPENDS ${TARGET_NAME}
    )
endfunction()