/*
  GetDataCommand.cpp - Library for TODO
  Created by TODO, Aug 5, 2015.
  TODO: Released into the public domain.
*/

#include "GetDataCommand.h"

char cmdBuff[50];

void GetDataCommand::parse(String command){
  status = 0;
  int strEnd = command.length();
  Serial.println("It is GETDATA");
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
 
 Serial.println(startDate->toStr());
 
  Serial.println(endDate->toStr());
  Serial.println(moduleSN);
  validCommand = (startDate <= endDate);

  noFiles = true;

  if (moduleSN.endsWith(".jsn")){
    ("/" + startDate->toStr() + "/" + moduleSN).toCharArray(cmdBuff, ("/" + startDate->toStr() + "/" + moduleSN).length() + 1);
    Serial.println(cmdBuff);
    jsonFile = SD.open(cmdBuff);
    if (jsonFile) noFiles = false;    
  } else {
    ("/" + startDate->toStr()).toCharArray(cmdBuff, ("/" + startDate->toStr()).length() + 1);
    Serial.println("WILL OPEN THIS DIRECTORY");
    Serial.println(cmdBuff);
    File root = SD.open(cmdBuff);
    root.rewindDirectory();
    jsonFile = root.openNextFile();
  }
}

void GetDataCommand::sendData() {}; // Will not do anything, as this is a GET command

boolean GetDataCommand::nextFile(){
  if (jsonFile.available()) return false;
  else {
    Serial.println("In the nextFile. else branch");
    jsonFile.close();
    if (moduleSN == ""){
      Serial.println("ModuleSN is null");
      jsonFile = root.openNextFile();
      if (jsonFile) return true;
      else {
        while ((startDate->smallerThan(endDate)) && !(jsonFile)) {
          startDate->inc();
          ("/" + startDate->toStr()).toCharArray(cmdBuff, ("/" + startDate->toStr()).length() + 1);
          root = SD.open(cmdBuff);
          root.rewindDirectory();
          jsonFile = root.openNextFile();
        }
      }
    } else {
      while ((startDate->smallerThan(endDate)) && (jsonFile.available() == 0)) {
        startDate->inc();
        Serial.println(startDate->toStr());
        ("/" + startDate->toStr() + "/" + moduleSN).toCharArray(cmdBuff, ("/" + startDate->toStr() + "/" + moduleSN).length() + 1);
        Serial.println("THIS IS THE FILENAME: ");
        Serial.println(cmdBuff);
        jsonFile = SD.open(cmdBuff);
      }
    }
    if (jsonFile) return true;
    else return false;  
  }
}

boolean GetDataCommand::available() {
  if (status < 4) return true;
  else return false;
/*
  if ((status < 4) || validCommand){
     if (jsonFile.available()) return true;
     else {
        return false;
     }
  } else {
    return false;
  }*/
};

char GetDataCommand::getChar(){
  if (jsonFile.available()) {
    noFiles = false;
    return jsonFile.read();
  } else {
        if (nextFile()) { status = 2; Serial.println("There is a next file coming"); }
        else { status = 3; Serial.println("There is no next file, printing footer"); }
        return '\0';
      }
  }

char GetDataCommand::read() {
  char ch = '\0';
  if (status == 0) ch = getHeader();
  if (status == 1) ch = getChar();
  if (status == 2) ch = getConnectionTag();
  if (status == 3) ch = getFooter();
//  if (available()) ch = getChar();
//  Serial.print(ch);
  return ch;
}

char GetDataCommand::getHeader(){
  static int index = 0;
  strcpy_P(cmdBuff, (char*)pgm_read_word(&(string_table[1])));
  if (index < strlen(cmdBuff)) return cmdBuff[index++];
  else {
    status = 1;
    index = 0;
    Serial.println("Getting body");
    return '\0';
  }
  //client.print(buff);
}

char GetDataCommand::getFooter(){
  static int index = 0;
  if (noFiles) strcpy_P(cmdBuff, (char*)pgm_read_word(&(string_table[6])));
  else strcpy_P(cmdBuff, (char*)pgm_read_word(&(string_table[5])));
  if (index < strlen(cmdBuff)) return cmdBuff[index++];
  else {
    Serial.println("Printed footer, resetting values");
    status = 4;
    index = 0;
    return '\n';
  }
  //client.print(buff);
}

char GetDataCommand::getConnectionTag(){
  static int index = 0;
  strcpy_P(cmdBuff, (char*)pgm_read_word(&(string_table[4])));
  if (index < strlen(cmdBuff)) return cmdBuff[index++];
  else {
    Serial.println("Printed connection tag, moving back to second file body");
    status = 1;
    index = 0;
    return ' ';
  }
}




