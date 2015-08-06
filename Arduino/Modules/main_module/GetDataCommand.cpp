/*
  GetDataCommand.cpp - Library for TODO
  Created by TODO, Aug 5, 2015.
  TODO: Released into the public domain.
*/

#include "GetDataCommand.h"

char cmdBuff[50];

void GetDataCommand::parse(String command){

  int strEnd = command.length();
//  Serial.println("It is GETDATA");
  if (command.indexOf('+') > 0){
    strEnd = command.indexOf('+');
    moduleSN = command.substring(command.indexOf('+') + 1) + ".jsn";
  } else {
    moduleSN = "";
  }
  if (command.indexOf('-') > 0){
    startDate = new Date(command.substring(command.indexOf('=') + 1, command.indexOf('-')));
    endDate = new Date(command.substring(command.indexOf('-') + 1, strEnd));
  } else {
    startDate = new Date(command.substring(command.indexOf('=') + 1, strEnd));
    endDate = startDate;
  }
 
  validCommand = (startDate < endDate);

  noFiles = true;

  if (moduleSN.endsWith(".jsn")){
    ("/" + startDate->toStr() + moduleSN).toCharArray(cmdBuff, ("/" + startDate->toStr() + moduleSN).length());
    jsonFile = SD.open(cmdBuff);
    if (jsonFile) noFiles = false;    
  } else {
    ("/" + startDate->toStr()).toCharArray(cmdBuff, ("/" + startDate->toStr()).length());
    File root = SD.open(cmdBuff);
    root.rewindDirectory();
    jsonFile = root.openNextFile();
  }
}

void GetDataCommand::sendData() {}; // Will not do anything, as this is a GET command

boolean GetDataCommand::nextFile(){
  if (jsonFile.available()) return false;
  else {
    jsonFile.close();
    if (moduleSN == ""){
      jsonFile = root.openNextFile();
      if (jsonFile) return true;
      else {
        while ((startDate < endDate) && !(jsonFile)) {
          startDate++;
          ("/" + startDate->toStr()).toCharArray(cmdBuff, ("/" + startDate->toStr()).length());
          root = SD.open(cmdBuff);
          root.rewindDirectory();
          jsonFile = root.openNextFile();
        }
      }
    } else {
      while ((startDate < endDate) && !(jsonFile)) {
        startDate++;
        ("/" + startDate->toStr() + moduleSN).toCharArray(cmdBuff, ("/" + startDate->toStr() + moduleSN).length());
        jsonFile = SD.open(cmdBuff);
      }
    }
    if (jsonFile) return true;
    else return false;  
  }
}

boolean GetDataCommand::available() {
  
  if (validCommand){
     if (jsonFile.available()) return true;
     else {
        return false;
     }
  } else {
    return false;
  }
};

char GetDataCommand::getChar(){
  static byte index;
  static boolean connectionTag;
  if (jsonFile.available() && !connectionTag) {
    index = 0;
    noFiles = false;
    return jsonFile.read();
  } else {
//      jsonFile.close();
      if (connectionTag || nextFile()) {
        strcpy_P(charBuff, (char*)pgm_read_word(&(string_table[4])));
        connectionTag = true;
      } else if (noFiles) {
        strcpy_P(charBuff, (char*)pgm_read_word(&(string_table[6])));
      } else {
        strcpy_P(charBuff, (char*)pgm_read_word(&(string_table[5])));
      }
      if (index < strlen(cmdBuff)) return charBuff[index++];
      else {
        connectionTag = false;
        index = 0;
        return '\0';
      }
  }
}

char GetDataCommand::read() {
  if (status == 0) return getHeader();
  
  if (available()) return getChar();
  return '\0';
}

char GetDataCommand::getHeader(){
  static int index = 0;
  strcpy_P(cmdBuff, (char*)pgm_read_word(&(string_table[1])));
  if (index < strlen(cmdBuff)) return cmdBuff[index++];
  else {
    status = 1;
    return '\0';
  }
  //client.print(buff);
}





