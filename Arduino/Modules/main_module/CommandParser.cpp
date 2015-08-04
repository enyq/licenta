/*
  CommandParser.cpp - Library for TODO
  Created by TODO, May 15, 2015.
  TODO: Released into the public domain.
*/

#include "Arduino.h"
#include "CommandParser.h"
#include "Logger.h"

CommandParser::CommandParser(Logger* logger){
  _logger = logger;
}

void CommandParser::parse(String command){
  validCommand = false;
  moduleSN = "";
  Serial.print("Command is: "); // TODO: delete
  Serial.println(command); // TODO: delete
  int strEnd = command.length();
  if (command.startsWith("GETDATA=")){
    Serial.println("It is GETDATA");
    if (command.indexOf('+') > 0){
      strEnd = command.indexOf('+');
      Serial.println("It has a module defined");
      moduleSN = command.substring(command.indexOf('+') + 1);
    } else {
      Serial.println("No specific module defined");
      moduleSN = "ALL";
    }    
    startYear = (command[8] - '0') * 1000 + (command[9] - '0') * 100 + (command[10] - '0') * 10 + (command[11] - '0');
    startMonth = (command[13] - '0') * 10 + (command[14] - '0');
    startDay = (command[16] - '0') * 10 + (command[17] - '0');
    if (command.indexOf('-') > 0){
      Serial.println("Date interval defined");
      endYear = (command[19] - '0') * 1000 + (command[20] - '0') * 100 + (command[21] - '0') * 10 + (command[22] - '0');
      endMonth = (command[24] - '0') * 10 + (command[25] - '0');
      endDay = (command[27] - '0') * 10 + (command[28] - '0');
    } else {
      Serial.println("Date interval undefined");
      endYear = startYear;
      endMonth = startMonth;
      endDay = startDay;
    }
    
    Serial.print("Start date: ");
    Serial.print(startYear);
    Serial.print(startMonth);
    Serial.println(startDay);    
    
    Serial.print("End date: ");
    Serial.print(endYear);
    Serial.print(endMonth);
    Serial.println(endDay);    
    
    return (startYear < endYear) || 
           ((startYear == endYear) && ((startMonth < endMonth) || 
                                      ((startMonth == endMonth) && (startDay <= endDay))));
  }
  return false;
}

boolean CommandParser::available(){
  return false;
}

char CommandParser::read(){
  return false;
}
