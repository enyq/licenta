/*
  CommandParser.cpp - Library for TODO
  Created by TODO, Aug 4, 2015.
  TODO: Released into the public domain.
*/

#include "Arduino.h"
#include "CommandParser.h"
#include "GetDataCommand.h"
#include "PostDataCommand.h"
#include "WipeCommand.h"
#include "Logger.h"
#include "SD.h"

CommandParser::CommandParser(Logger* logger){
  _logger = logger;
}

void CommandParser::parse(String command){
  Serial.print("Command is: "); // TODO: delete
  Serial.println(command); // TODO: delete
  if (_command != NULL) delete _command; 
  if (command.startsWith("GETDATA=")){
    _command = new GetDataCommand(_logger);
    _command->parse(command);
  } else if (command.startsWith("POST=DATA")) {
    _command = new PostDataCommand(_logger);
    _command->parse(command);
  } else if (command.startsWith("POST=WIPE")){
    _command = new WipeCommand(_logger);
    _command->parse(command);
  }
}

boolean CommandParser::available(){
  return _command->available();
}

char CommandParser::read(){
  return _command->read();
}
