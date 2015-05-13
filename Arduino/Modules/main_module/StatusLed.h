/*
  StatusLed.h - Library for TODO
  Created by TODO, May 13, 2015.
  TODOReleased into the public domain.
*/
#ifndef StatusLed_h
#define StatusLed_h

#include "Arduino.h"

class StatusLed
{
  public:
    StatusLed(byte green_pin = -1, byte yellow_pin = -1, byte red_pin = -1);
    void setWarningType(byte wt);
    void update();
  private:
    byte _ledPin[3];
    byte _warningType = 0;
    unsigned long _lastUpdate = 0;
    byte _state = HIGH;
    byte _selectedLed = 0;
    byte _blinking = 0;
};

#endif
