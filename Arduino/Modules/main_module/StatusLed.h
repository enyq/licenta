/*
  StatusLed.h - Library for TODO
  Created by TODO, May 13, 2015.
  TODO: Released into the public domain.
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
    void turnOffAll();
    byte _ledPin[3];
    byte _warningType;
    unsigned long _lastUpdate;
    byte _state;
    byte _selectedLed;
    byte _blinking;
};

#endif
