/*
  This is the client module template. All the clients should implement this template.
  
  It is designed as a plug&play module, so at first it will have the starting address of 255, which will be later changed to one 
  assigned from the main module
*/

#include <Wire.h>
#include <EEPROM.h>

const byte INITIAL_ADDR = 255; // Depending on module type it should have the corresponding value
const byte ADDR_LOCATION = 0; // In EEPROM, the first slot will store the module address

byte addr = EEPROM.read(ADDR_LOCATION);

void setup() {
  if (addr == 0) addr = INITIAL_ADDR;
  Wire.begin(addr);
  Wire.onReceive(receiveEvent);
  Wire.onRequest(requestEvent);
  
  // If the address was reset, will ask for a new one from the main module
  if (addr == INITIAL_ADDR) { 
    addr = getNewAddress();
    EEPROM.write(ADDR_LOCATION, addr);
    Wire.begin(addr);
  }
}

void loop() {

}

// TODO: To be implemented later
byte getNewAddress(){
  return 0;
}

void receiveEvent(int length){
   String msg="";
   while (1 < Wire.available()) msg += Wire.read();
   //interpret(msg);
}

void requestEvent(){
  String msg;// = gatherInfo();
  byte i = 0;
  while (msg[i] != '\0') Wire.write(msg[i++]);
}
