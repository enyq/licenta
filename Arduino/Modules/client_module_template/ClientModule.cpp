/*
  ClientModule.cpp - Library for TODO
  Created by TODO, May 16, 2015.
  TODO: Released into the public domain.
*/

#include "Arduino.h"
#include "ClientModule.h"
#include "Constants.h"
#include "Wire.h"
#include <EEPROM.h>

String RESPONSE;

ClientModule::ClientModule(byte type, byte addr){
  _type = type;
  if (addr == 0) _addr = EEPROM.read(0);
  else _addr = addr;
}

void ClientModule::init(){
  Serial.begin(9600);
  Serial.println("Client module started");
  Serial.print("I2C address is: ");
  Serial.println(_addr);
  Wire.begin(_addr);
  Wire.onReceive(ClientModule::receiveEvent);
  Wire.onRequest(ClientModule::requestEvent);
}

void ClientModule::resetAddr(){
  _addr = NEW_MODULE_ID;
}

byte ClientModule::getAddr(){
  return _addr;
}

void ClientModule::receiveEvent(int length){
   Serial.println("RECEIVE EVENT: ");
   String msg="";
   while (0 < Wire.available()) msg += char(Wire.read());
   Serial.println(msg);
   String messag = "XY#1";
   if (msg == "PING") RESPONSE = fillUp(messag);
   if (msg.startsWith("GET")) RESPONSE = fillUp("GET WAS RECEIVED");
   if (msg.startsWith("SET")){
       RESPONSE = fillUp("SET WAS RECEIVED");
       // TODO: Correct it later
       Wire.begin(25);
       EEPROM.write(0, 25);
   }
//   if (msg.startsWith("RESET")) resetAddr();
   //   requestEvent();
   //TODO: interpret(msg);
}

void ClientModule::requestEvent(){
  Serial.println("REQUEST EVENT: ");
  char resp[RESPONSE.length()];
  RESPONSE.toCharArray(resp, RESPONSE.length());
  Serial.print("Len is: ");
  byte x = RESPONSE.length();
  Serial.println(x);
  Wire.write(resp);
}

String ClientModule::fillUp(String msg){
  String resp = msg;
  for (byte i = msg.length(); i <= I2C_MAX_MESSAGE_LENGTH; i++) resp += " ";
  return resp;
}

void ClientModule::saveAddr(){
  EEPROM.write(0, _addr);
}
