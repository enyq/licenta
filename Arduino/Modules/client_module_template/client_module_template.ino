/*
  This is the client module template. All the clients should implement this template.
  
  It is designed as a plug&play module, so at first it will have the starting address of 255, which will be later changed to one 
  assigned from the main module
*/

#include <Wire.h>
#include <EEPROM.h>
#include "ClientModule.h"
#include "Constants.h"

const byte MODULE_TYPE = 255; // Depending on module type it should have the corresponding value

ClientModule clientModule(MODULE_TYPE);//, NEW_MODULE_ID);

void setup() {
  Serial.begin(9600);
  Serial.println("SERIAL PORT STARTED!");
  clientModule.init();
}

void loop() {
}

