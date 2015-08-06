/*
  WipeCommand.h - Library for TODO
  Created by TODO, Aug 5, 2015.
  TODO: Released into the public domain.
*/
#ifndef WipeCommand_h
#define WipeCommand_h

#include "Command.h"

class WipeCommand : public Command {
public:
  WipeCommand(Logger *logger) : Command(logger) {};
  void parse(String command);
  void sendData();
  boolean available();
  char read();
private:
  boolean firstTime = true;
  void rm(File dir, String tempPath);
};

#endif

