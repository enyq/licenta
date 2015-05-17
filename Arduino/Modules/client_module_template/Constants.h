/*
  Constants.h - Linked library for TODO
  Created by TODO, May 13, 2015.
  TODO: Released into the public domain.
*/
#ifndef Constants_h
#define Constants_h

#include "Arduino.h"

const byte I2C_MAX_MESSAGE_LENGTH = 32;
#define WARNING__LED_BLINKING_FREQUENCY = 1; // No. of blinks every second
const byte I2C_MIN_ADDR = 15;
const byte I2C_MAX_ADDR = 110;
const byte ADDR_RESET_PIN = 9;
#define MAX_MODULES_NUMBER = 110;
const byte NEW_MODULE_ID = 110;
#define LOG_INFO = 0;
#define LOG_WARN = 1;
#define LOG_DEBUG = 5;
#define LOG_SERIAL_ONLY = 0;

#endif
