/*
  Module.cpp - Library for TODO
  Created by TODO, May 13, 2015.
  TODO: Released into the public domain.
*/

#include "Arduino.h"
#include "Module.h"
#include "Constants.h"
#include "Wire.h"

Module::Module(byte addr, byte type)
{
  _addr = addr;
  _type = type;
}

// Sends the message to the client module
String Module::sendMessage(String msg, byte response){
  byte i = 0;
  Wire.beginTransmission(_addr);
  Wire.write(msg.c_str());
  Wire.endTransmission();
  String resp = "NO RESP";  // TODO: delete NO RESP from here
  if (response == 1){
    delay(250);
    resp = getMessage();
  }
  return resp;
}

// Gets the info from the client module
String Module::getMessage(){
  String msg="";
  byte msg_len = 0;
  Wire.requestFrom(_addr, I2C_MAX_MESSAGE_LENGTH);
  while ((Wire.available()) && (msg_len++ <= I2C_MAX_MESSAGE_LENGTH)){
    msg += char(Wire.read());
  }
  Serial.print("getMessage: "); // TODO: Clean up!!!
  Serial.println(msg);
  return msg;  
}

byte Module::ping(){
  String msg = sendMessage("PING", 1);
  if (msg[3] != '1') _pingRetries++;
//  Serial.print("THIS IS PING "); //TODO: Clean up!!!
//  Serial.println(msg);
  return  (msg[3] == '1');
}

String Module::getInfo(){
  return sendMessage("GET", 1);
}

byte Module::getType(){
  return _type;
}

byte Module::getAddr(){
  return _addr;
}

byte Module::getRetries(){
  return _pingRetries;
}

void Module::setAddr(byte addr){
  if ((addr > 0) && (addr < MAX_MODULES_NUMBER)){
    _addr = addr;
    sendMessage("SET ADDR " + addr);
  }
}
