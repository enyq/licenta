/*
  Module.cpp - Library for TODO
  Created by TODO, May 13, 2015.
  TODO Released into the public domain.
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
/* Client module should receive with the following code:
  Wire.begin(_addr);
  Wire.onReceive(receiveEvent);
  void receiveEvent(byte length){
    String msg="";
    while (1 < Wire.available()) msg += Wire.read();
    interpret(msg);
  }
*/
void Module::sendMessage(String msg)
{
  byte i = 0;
  Wire.beginTransmission(_addr);
  while (msg[i] != '\0') Wire.write(msg[i++]);
  Wire.endTransmission();
}

// Gets the info from the client module
/* Client module should respond with the following code:
  Wire.begin(_addr);)
  Wire.onRequest(requestEvent);
  void requestEvent(){
    Wire.write(msg);
  }
*/
String Module::getInfo()
{
  String msg="";
  byte msg_len = 0;
  Wire.requestFrom(_addr, I2C_MAX_MESSAGE_LENGTH);
  while (Wire.available() && (msg_len < I2C_MAX_MESSAGE_LENGTH)){
    msg = msg + Wire.read();
  }
  return msg;
}

byte Module::getType(){
  return _type;
}

byte Module::getAddr(){
  return _addr;
}
