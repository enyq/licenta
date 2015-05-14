/*
  StatusLed.cpp - Library for TODO
  Created by TODO, May 13, 2015.
  TODO Released into the public domain.
  
  TODO: Define in more detail
  Warning type table:
  0 = Full green   = No errors
  1 = Blink green  = Intermittent issues (could be network/communication related)
  2 = Full yellow  = Issues that needs user attention 
  3 = Blink yellow = Issues that could be resolved with a system reset
  4 = Full red     = Major issue
  5 = Blinking red = System is down, no functionality (mostly when communication is interrupted with all significant modules)
*/

#include "Arduino.h"
#include "StatusLed.h"
#include "Constants.h"

StatusLed::StatusLed(byte greenPin, byte yellowPin, byte redPin){
  _ledPin[0] = greenPin;
  _ledPin[1] = yellowPin;
  _ledPin[2] = redPin;
  _warningType = 0;
  _lastUpdate = 0;
  _state = HIGH;
  _selectedLed = 0;
  _blinking = 0;
  for (int i = 0; i < 3; i++) pinMode(_ledPin[i], OUTPUT);
  digitalWrite(_ledPin[_selectedLed], _state);
}

void StatusLed::setWarningType(byte wt){
  if ((wt >= 0) && (wt <= 5)){
    turnOffAll();
    _warningType = wt;
    _state = HIGH;
    update();
  }
}

void StatusLed::update(){
  _selectedLed = _warningType / 2; // For 0 and 1 will be the green, for 2 and 3 will be the yellow and for 4 and 5 will be the red
  _blinking = _warningType % 2;
  if (_blinking == 1) {
    if (millis() - _lastUpdate >= 1000 / WARNING__LED_BLINKING_FREQUENCY) {
      _state = 1 xor _state;
      _lastUpdate = millis();
    }
  }
  digitalWrite(_ledPin[_selectedLed], _state);
}

void StatusLed::turnOffAll(){
  for (byte i = 0; i < 3; i++) digitalWrite(_ledPin[i], LOW);
}

