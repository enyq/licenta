/*
  This is the client module template. All the clients should implement this template.
  
  It is designed as a plug&play module, so at first it will have the starting address of 255, which will be later changed to one 
  assigned from the main module
*/

#include <Wire.h>
#include <EEPROM.h>
#include "ClientModule.h"
#include "Constants.h"
#include "DHT.h"

#define DHTPIN 10     // what pin we're connected to
#define DHTTYPE DHT11
DHT dht(DHTPIN, DHTTYPE);

String SENSOR_NAME_D[13] = {"D1", "D2", "D3", "D4", "D5", "D6", "D7", "D8", "D9", "D10", "D11", "D12", "D13"};
byte SENSOR_DIR_D[13] = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}; // 0=Input, 1=Output
String SENSOR_NAME_A[4] = {"A0", "A1", "A2", "A3"}; // A4 and A5 are reserved for communication over I2C

String VALUE = "";

const byte MODULE_TYPE = 255; // Depending on module type it should have the corresponding value
const char SN[] = "00000002";
ClientModule clientModule(MODULE_TYPE, gatherClientInfo, commandInterpreter);

void setup() {
  Serial.begin(9600);
  Serial.println("SERIAL PORT STARTED!");
  dht.begin();
  clientModule.init();


}

void loop() {

  Serial.println(VALUE);
  float h = dht.readHumidity();
  float t = dht.readTemperature();
  float f = dht.readTemperature(true);
  
  if (isnan(h) || isnan(t) || isnan(f)) {
    Serial.println("Failed to read from DHT sensor!");
    VALUE = "ERROR";
  }

  VALUE = "Mod2: Hum:" + String(h) + " %\t Temp:" + String(t);
//  Serial.println(VALUE);
  delay(2000);
  
}

// This function will read the sensors and create a 32 byte long message that will be sent to the main module
String gatherClientInfo(){

  return VALUE;
}

// Will interpret some of the SET commands (Internal commands that should not be interpreted by: PING, SET ADDR, GET)
void commandInterpreter(String command){
}

