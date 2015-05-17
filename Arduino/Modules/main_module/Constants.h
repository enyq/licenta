/*
  Constants.h - Linked library for TODO
  Created by TODO, May 13, 2015.
  TODO: Released into the public domain.
*/
#ifndef Constants_h
#define Constants_h

#include "Arduino.h"

const byte I2C_MAX_MESSAGE_LENGTH = 32;
const byte WARNING__LED_BLINKING_FREQUENCY = 1; // No of blinks every second
const byte MAX_MODULES_NUMBER = 109;
const byte MAX_PING_RETRIES = 3;

const byte I2C_MIN_ADDR = 15;
const byte I2C_MAX_ADDR = 110;
const byte NEW_MODULE_ID = 110;
const byte LOG_INFO = 0;
const byte LOG_WARN = 1;
const byte LOG_DEBUG = 5;
const byte LOG_SERIAL_ONLY = 0;

const byte SD_CARD_READER_CS_PIN = 48;

#endif
