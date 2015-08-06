/*
  GetDataCommand.h - Library for TODO
  Created by TODO, Aug 5, 2015.
  TODO: Released into the public domain.
*/
#ifndef GetDataCommand_h
#define GetDataCommand_h

#include "Command.h"
#include "Date.h"

class GetDataCommand : public Command {
public:
  GetDataCommand(Logger *logger) : Command(logger) {};
  void parse(String command);
  void sendData();
  boolean available();
  char read();
private:
  Date *startDate, *endDate;
  String moduleSN;
  File jsonFile;
  File root;
  boolean noFiles = false;
  char getChar();
  char getHeader();
  char charBuff[50];
  boolean nextFile();
  int status = 0;
};


#endif
