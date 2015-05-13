/*
  Module.h - Library for TODO
  Created by TODO, May 13, 2015.
  TODOReleased into the public domain.
*/
#ifndef Module_h
#define Module_h

#include "Arduino.h"

class Module
{
  public:
    Module(byte addr = 0, byte type = 0);
    void send_message(String msg);
    String get_info();
    void get_type();
  private:
    byte _addr;
    byte _type;
};

#endif
