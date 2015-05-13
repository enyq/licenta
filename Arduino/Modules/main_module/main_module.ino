// SD card attached to SPI bus as follows: MOSI - pin 11 MISO - pin 12 CLK - pin 13 CS - pin 4
// Ethernet attached to pins 10, 11, 12, 13
// EPPROM will store the modules information, so in case of resetting the unit, information will remain. Storage in the following format:
// --------------------------------------
// |  Position  |        Value          |
// |------------|-----------------------|
// |     0      |  No. of modules       |
// | 1 + 2 * i  |    Module Addr        |
// | 2 + 2 * i  |   Module type no.     |
// --------------------------------------
// | i ranging from 0 to No. of modules |

#include "Module.h"
#include "StatusLed.h"
#include "Constants.h"
#include <Wire.h>
#include <SPI.h>
#include <Ethernet.h>
#include <SD.h>
#include <EEPROM.h>       
//#include <RTC.h> // TODO: import correct RTC header file

String info;

StatusLed statusLed(1, 2, 3); // TODO: Set the correct values

// TODO: This info will be read from the SD card, but will be set from here for now
byte mac[] = { 0x00, 0xAA, 0xBB, 0xCC, 0xDE, 0x02 };
IPAddress ip(192,168,1, 177);
IPAddress gateway(192,168,1, 1);
IPAddress subnet(255, 255, 0, 0);
EthernetServer server(80);

Module modules[MAX_MODULES_NUMBER];
byte activeModules = 1;

void setup() {
  Wire.begin();             // Initialize I2C communication with client modules
  Ethernet.begin(mac, ip);  // Initialize ethernet communication
  server.begin();           // Start the web server
  
  // On the Ethernet Shield, CS is pin 4. It's set as an output by default.
  // Note that even if it's not used as the CS pin, the hardware SS pin
  // (10 on most Arduino boards, 53 on the Mega) must be left as an output
  // or the SD library functions will not work.
  pinMode(10, OUTPUT);
  loadModulesFromEEPROM(); // TODO: Implement loadModulesFrom EEPROM  
  moduleCleanUp();
}

void loop() {
  listenForWebClients();
  statusLed.update();
  
}

// TODO: format sent data to something friendly
void listenForWebClients(){
  EthernetClient client = server.available();
  if (client) {
    // an http request ends with a blank line
    boolean currentLineIsBlank = true;
    while (client.connected()) {
      if (client.available()) {
        char c = client.read();
        // if you've gotten to the end of the line (received a newline
        // character) and the line is blank, the http request has ended,
        // so you can send a reply
        if (c == '\n' && currentLineIsBlank) {
          // send a standard http response header
          client.println("HTTP/1.1 200 OK");
          client.println("Content-Type: text/html");
          client.println("Connection: close");  // the connection will be closed after completion of the response
          client.println("Refresh: 5");  // refresh the page automatically every 5 sec
          client.println();
          client.println("<!DOCTYPE HTML>");
          client.println("<html>");
          // output the value of each analog input pin
          for (byte analogChannel = 0; analogChannel < 6; analogChannel++) {
            int sensorReading = analogRead(analogChannel);
            client.print("analog input ");
            client.print(analogChannel);
            client.print(" is ");
            client.print(sensorReading);
            client.println("<br />");
          }
          client.println("</html>");
          break;
        }
        if (c == '\n') {
          // you're starting a new line
          currentLineIsBlank = true;
        }
        else if (c != '\r') {
          // you've gotten a character on the current line
          currentLineIsBlank = false;
        }
      }
    }
    // give the web browser time to receive the data
    delay(1);
    // close the connection:
    client.stop();
  }
}

void loadModulesFromEEPROM(){
  modules[0] = Module(255, 255);
  activeModules = EEPROM.read(0);
  if (activeModules > MAX_MODULES_NUMBER){
    activeModules = MAX_MODULES_NUMBER;
  }
  for (byte i = 1; i < activeModules; i++){
    modules[i] = Module(EEPROM.read(1 + 2 * i),EEPROM.read(2 + 2 * i)); 
  }
}

void saveModulesToEEPROM(){
  EEPROM.write(0, activeModules);
  for (byte i = 0; i < activeModules; i++){
    EEPROM.write(1 + 2 * i, modules[i].getAddr());
    EEPROM.write(2 + 2 * i, modules[i].getType());
  }
}

// TODO: Implement moduleCleanUp function => Maybe there should be a counter of unsuccessfull retries, will figure out that later
void moduleCleanUp(){
  // This function will check for removed modules, and after several unsuccessfull retries will remove them from the list
  
  // Will save the new modules to EEPROM
  saveModulesToEEPROM();
}
// TODO: Implement interpetMessage function
byte interpretMessage(String msg){
  return NEW_MODULE;
}

void checkForNewModule(){
  String msg = modules[0].getInfo();
  byte moduleType = interpretMessage(msg);
  if (moduleType > 0){
    modules[++activeModules] = Module(activeModules, moduleType);
  }
}
