/*
  Constants.h - Library for TODO
  Created by TODO, May 13, 2015.
  TODO: Released into the public domain.
*/
#ifndef Constants_h
#define Constants_h

#include "Arduino.h"

const byte I2C_MAX_MESSAGE_LENGTH = 150;
const byte WARNING__LED_BLINKING_FREQUENCY = 1; // No of blinks every second
const byte MAX_MODULES_NUMBER = 110;
const byte NEW_MODULE = 255;
const byte LOG_INFO = 0;
const byte LOG_WARN = 1;
const byte LOG_DEBUG = 5;
const byte LOG_SERIAL_ONLY = 0;

#endif
