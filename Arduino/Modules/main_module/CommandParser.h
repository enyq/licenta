/*
  CommandParser.h - Library for TODO
  Created by TODO, Aug 4, 2015.
  TODO: Released into the public domain.
*/
#ifndef CommandParser_h
#define commandParser_h

#include "Arduino.h"
#include "Logger.h"
#include "Command.h"
#include "SD.h"

class CommandParser
{
  public:
    CommandParser(Logger* logger);
    void parse(String command);
    void sendData();
    boolean available();
    char read();
  private:
    Logger * _logger;
    Command *_command;
};

#endif
