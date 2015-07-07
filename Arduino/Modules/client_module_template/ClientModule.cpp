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

String (*ClientModule::_gatherInfo)(void);
void (*ClientModule::_interpretCommand)(String);

ClientModule::ClientModule(byte type, String (*gatherFunct)(void), void (*interpretFunct)(String), byte addr){
  _type = type;
  if (addr == 0) _addr = EEPROM.read(0);
  else _addr = addr;
  _gatherInfo = gatherFunct;
  _interpretCommand = interpretFunct;
}

void ClientModule::init(){
  digitalWrite(ADDR_RESET_PIN, LOW);
  pinMode(ADDR_RESET_PIN, INPUT);
  delay(200);
  if (digitalRead(ADDR_RESET_PIN) == HIGH) resetAddr();
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
  saveAddr();
}

byte ClientModule::getAddr(){
  return _addr;
}

// Will interpret internally the GET, PING and SET ADDR commands
void ClientModule::receiveEvent(int length){
   Serial.println("RECEIVE EVENT: ");
   String msg="";
   while (0 < Wire.available()) msg += char(Wire.read());
   Serial.println(msg);
   if (msg.startsWith("PING")) RESPONSE = "XY#1";
   else if (msg.startsWith("GET SN")) RESPONSE = SN; 
   else if (msg.startsWith("GET NAME")) RESPONSE = NAME;
   else if (msg.startsWith("GET")) RESPONSE = _gatherInfo();   
   else if (msg.startsWith("SET NAME")){
     NAME = "";
     for (byte i = 9; i < msg.length(); i++) NAME += msg[i];
     RESPONSE = "ACK";
   }
   else if (msg.startsWith("SET ADDR")){
       byte addr = 0;
       for (byte i = 0; i < msg.length(); i++){
         if ((msg[i] >= '0') && (msg[i] <= '9')) addr = addr * 10 + (msg[i] - '0');
       }
       setAddr(addr);
       RESPONSE = "ACK";
   } else _interpretCommand(msg);
}

void ClientModule::requestEvent(){
  Serial.println("REQUEST EVENT: ");
  RESPONSE = fillUp(RESPONSE);
  char resp[RESPONSE.length()];
  RESPONSE.toCharArray(resp, RESPONSE.length());
  Serial.print(RESPONSE);
  Serial.print("Len is: "); // TODO: Cleanup
  byte x = RESPONSE.length(); // TODO: Cleanup
  Serial.println(x); // TODO: Cleanup
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

void ClientModule::setAddr(byte addr){
   if ((addr >= I2C_MIN_ADDR) && (addr < I2C_MAX_ADDR)){
     Wire.begin(addr);
     EEPROM.write(0, addr);
   }
}
