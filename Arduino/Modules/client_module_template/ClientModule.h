/*
  ClientModule.h - Library for TODO
  Created by TODO, May 16, 2015.
  TODO: Released into the public domain.
  
  Provides basic communication over I2C with the main module.
  Accepted commands are:   PING - will respond with XY#1
                           GET  - will send the module data (data having the following setup: XY#INFO where X is the type char of the module, Y the address, # = separator, and INFO the actual information)
                           SET <params> - will set params of the module.
                               <params> - the actual parameter set, like: ADDR or other module related parameters
*/
#ifndef ClientModule_h
#define ClientModule_h

#include "Arduino.h"

extern const String SN;
extern String NAME;

class ClientModule{
  public:
    ClientModule(byte type, String (*gatherFunct)(void), void (*interpretFunct)(String), byte addr = 0);
    void resetAddr();
    byte getAddr();
    void init();

private:
  static String (*_gatherInfo)(void);
  static void (*_interpretCommand)(String);
  static String fillUp(String msg);
  static void receiveEvent(int length);
  static void requestEvent();
  void saveAddr();
  static void setAddr(byte addr);
  byte _addr;
  byte _type;
};

#endif


