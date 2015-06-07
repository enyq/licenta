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
const byte MAX_MODULES_NUMBER = 20;
const byte MAX_PING_RETRIES = 3;

const byte I2C_MIN_ADDR = 15;
const byte I2C_MAX_ADDR = 110;
const byte NEW_MODULE_ID = 110;
const byte LOG_INFO = 0;
const byte LOG_WARN = 1;
const byte LOG_DEBUG = 5;
const byte LOG_SERIAL_ONLY = 0;

const byte SD_CARD_READER_CS_PIN = 48;

const char HTML_HEADER[] PROGMEM = "HTTP/1.1 200 OK \r\nContent-Type: application/json \r\nConnection: close \r\n";
const char JSON_HEADER[] PROGMEM = "{\r\n\t\"ModuleData\" : [\r\n\t{";
const char MODULE_SEPARATOR[] PROGMEM = "\t\t]\r\n\t},{";
const char MODULE_END[] PROGMEM = "\t]\r\n\t}\r\n\t]\r\n}";

const char MODULE_START[] PROGMEM = "\t{";
const char UUID_START[] PROGMEM = "\t\t\"UUID\": \"";
const char UUID_END[] PROGMEM = "\",";
const char TYPE_START[] PROGMEM = "\t\t\"Type\": \"";
const char TYPE_END[] PROGMEM = ",";
const char NAME_START[] PROGMEM = "\t\t\"Name\": \"";
const char NAME_END[] PROGMEM = "\",";
const char DATA_START[] PROGMEM = "\t\t\"Data\": [\r\n\t\t{";
const char TIME_START[] PROGMEM = "\t\t\t\"Time\": \"";
const char TIME_END[] PROGMEM = "\",";
const char VALUE_START[] PROGMEM = "\t\t\t\"Values\": \"";
const char VALUE_END[] PROGMEM = "\"\r\n}";
const char NEXT_DATA[] PROGMEM = ",";
const char DATA_END[] PROGMEM = "\t\t]\r\n\t}";
const char NEXT_MODULE[] PROGMEM = ",";
const char JSON_FOOTER[] PROGMEM = "}";

const char* const string_table[] PROGMEM = {HTML_HEADER, JSON_HEADER, MODULE_SEPARATOR, MODULE_END, UUID_START, UUID_END, TYPE_START, TYPE_END, NAME_START, NAME_END, DATA_START, 
                                            TIME_START, TIME_END, VALUE_START, VALUE_END, NEXT_DATA, DATA_END, NEXT_MODULE, JSON_FOOTER};



#endif
