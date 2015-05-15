/*
  Module.h - Library for TODO
  Created by TODO, May 13, 2015.
  TODO: Released into the public domain.
*/
#ifndef Module_h
#define Module_h

#include "Arduino.h"

class Module
{
  public:
    Module(byte addr = 0, byte type = 0);
    void sendMessage(String msg);
    String getInfo();
    byte getType();
    byte getAddr();
  private:
    byte _addr;
    byte _type;
};

#endif

