/*
  Command.h - Library for TODO
  Created by TODO, Aug 5, 2015.
  TODO: Released into the public domain.
*/
#ifndef Command_h
#define Command_h

#include "Arduino.h"
#include "Logger.h"
#include "SD.h"

class Command {
public:
    Command();
    Command(Logger* logger);
    virtual void parse(String command) = 0;
    virtual void sendData() = 0;
    virtual boolean available() = 0;
    virtual char read() = 0;
protected:
    Logger * _logger;
    boolean validCommand = false;    
};

#endif
