/*
  Command.cpp - Library for TODO
  Created by TODO, Aug 5, 2015.
  TODO: Released into the public domain.
*/

#include "Arduino.h"
#include "Command.h"
#include "Logger.h"
#include "SD.h"

Command::Command(Logger* logger){
  _logger = logger;
}
