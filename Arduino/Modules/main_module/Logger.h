/*
  Logger.h - Library for TODO
  Created by TODO, May 14, 2015.
  TODOReleased into the public domain.
*/
#ifndef Logger_h
#define Logger_h

#include "Arduino.h"
#include "Constants.h"

class Logger
{
  public:
    Logger(byte level = LOG_INFO, byte dest = LOG_SERIAL_ONLY, String destFile = "");
    void info(String msg);
    void warn(String msg);
    void debug(String msg);
  private:
    void log(String msg);
    byte _level;
    byte _dest;
    String _destFile;
};

#endif
