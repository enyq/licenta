/*
  PostDataCommand.h - Library for TODO
  Created by TODO, Aug 27, 2015.
  TODO: Released into the public domain.
*/
#ifndef PostDataCommand_h
#define PostDataCommand_h

#include "Command.h"

class PostDataCommand : public Command {
public:
  PostDataCommand(Logger *logger) : Command(logger) {};
  void parse(String command);
  void sendData();
  boolean available();
  char read();
private:
  String moduleSN;
  String message;
  int index;
  String response; 
};

#endif
