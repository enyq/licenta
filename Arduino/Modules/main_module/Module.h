/*
  Module.h - Library for TODO
  Created by TODO, May 13, 2015.
  TODO: Released into the public domain.
  
  Provides basic communication over I2C with the modules.
  Commands are: PING - to verify if module is still active
                GET  - get module data (data having the following setup: XY#INFO where X is the type char of the module, Y the address, # = separator, and INFO the actual information)
                SET <params> - set params of the module.
                <params> - the actual parameter set, like: ADDR or other module related parameters
*/
#ifndef Module_h
#define Module_h

#include "Arduino.h"

class Module
{
  public:
    Module(byte addr = 0, byte type = 0);
    String sendMessage(String msg, byte response = 0);
    byte ping();  // Returns 1 if module is alive
    String getInfo();
    byte getType();
    byte getAddr();
    void setAddr(byte addr);
    byte getRetries();
//    String getMessage(); // TODO: move to private when finished testing
  private:
    String getMessage();
    byte _addr;
    byte _type;
    byte _pingRetries;
};

#endif

