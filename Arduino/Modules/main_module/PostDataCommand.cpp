/*
  PostDataCommand.cpp - Library for TODO
  Created by TODO, Aug 27, 2015.
  TODO: Released into the public domain.
*/

#include "PostDataCommand.h"
#include "ModuleManager.h"

void PostDataCommand::parse(String command){
  int strEnd = command.length();
//  Serial.println("It is POSTDATA");
//  Serial.println(command);
  if (command.indexOf('+') > 0){
    moduleSN = command.substring(command.indexOf('+') + 1);
    //message = command.substring(command.indexOf(":") + 1, command.indexOf("+"));
    message = "";
    String msg = command.substring(command.indexOf(":") + 1, command.indexOf("+"));
    for (int i = 0; i < msg.length() && i < 32; i++) {
      message += msg[i];
    }
    
    //htomessage.setCharAt(32, '\0');
  } else {
    moduleSN = "";
  }
  Serial.println("Specified module is: " + moduleSN);
  Serial.println("Message is: " + message);
  validCommand = (moduleSN != "" && message.length() <= 32);
  Serial.println(validCommand);
  index = 0;
  sendData();
}

void PostDataCommand::sendData() {
  if (validCommand) {
   ModuleManager moduleMgr = ModuleManager::getInstance();
   moduleMgr.sendToModule(moduleSN, message);
   response = "{\n\t\"Response\" : \"Acknowledged\"\n}";
  } else {
       response = "{\n\t\"Response\" : \"Failed\"\n}";
  }
}; 

boolean PostDataCommand::available() {
  return index < response.length();
};

char PostDataCommand::read() {
  return response[index++];
}

