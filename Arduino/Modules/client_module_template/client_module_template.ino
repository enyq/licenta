/*
  This is the client module template. All the clients should implement this template.
  
  It is designed as a plug&play module, so at first it will have the starting address of 255, which will be later changed to one 
  assigned from the main module
*/

#include <Wire.h>
#include <EEPROM.h>
#include "ClientModule.h"
#include "Constants.h"
//#include "DHT.h"

//#define DHTPIN 10     // what pin we're connected to
//#define DHTTYPE DHT11
//DHT dht(DHTPIN, DHTTYPE);

String VALUE = "";

const byte MODULE_TYPE = 255; // Depending on module type it should have the corresponding value
const String SN = "00000003";
String NAME = "Default client module";

ClientModule clientModule(MODULE_TYPE, gatherClientInfo, commandInterpreter);

void setup() {
  Serial.begin(9600);
  Serial.println("SERIAL PORT STARTED!");
//  dht.begin();
  clientModule.init();


}

void loop() {

  Serial.println(VALUE);
//  float h = dht.readHumidity();
//  float t = dht.readTemperature();
//  float f = dht.readTemperature(true);
  
//  if (isnan(h) || isnan(t) || isnan(f)) {
//    Serial.println("Failed to read from DHT sensor!");
//    VALUE = "ERROR";
//  }

//  VALUE = "Mod2: Hum:" + String(h) + " %\t Temp:" + String(t);
//  Serial.println(VALUE);
  delay(2000);
  
}

// This function will read the sensors and create a 32 byte long message that will be sent to the main module
String gatherClientInfo(){
  VALUE = "DUMMY INFO FROM THIS MODULE";
  return VALUE;
}

// Will interpret some of the SET commands (Internal commands that should not be interpreted by: PING, SET ADDR, GET)
void commandInterpreter(String command){
}

