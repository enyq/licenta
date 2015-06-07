/*  ARDUINO MEGA
    const byte SPI_SS   = 53;
    const byte SPI_MOSI = 51;
    const byte SPI_MISO = 50;
    const byte SPI_SCK  = 52;
    */
// ARDUINO UNO: SD card attached to SPI bus as follows: MOSI - pin 11 MISO - pin 12 CLK - pin 13 CS - pin 4
// ARDUINO UNO: Ethernet attached to pins 10, 11, 12, 13

// EPPROM will store the modules information, so in case of resetting the unit, information will remain. Storage in the following format:
// --------------------------------------
// |  Position  |        Value          |
// |------------|-----------------------|
// |     0      |  No. of modules       |
// | 1 + 2 * i  |    Module Addr        |
// | 2 + 2 * i  |   Module type no.     |
// --------------------------------------
// | i ranging from 0 to No. of modules |

#include "ModuleManager.h"
#include "StatusLed.h"
#include "Constants.h"
#include <Wire.h>
#include <SPI.h>
#include <Ethernet.h> // TODO: To be changed later to Ethernet module, for the shield
#include <EEPROM.h>
#include <SD.h>
#include "Logger.h"

//#include <RTC.h> // TODO: import correct RTC header file

String info;

StatusLed statusLed(1, 2, 3); // TODO: Set the correct values

Logger logger(LOG_DEBUG);

// TODO: This info will be read from the SD card, but will be set from here for now
byte mac[] = { 0x00, 0xAA, 0xBB, 0xCC, 0xDE, 0x22 };
IPAddress ip(192,168,1, 180);
IPAddress gateway(192,168,1, 1);
IPAddress subnet(255, 255, 255, 0);
EthernetServer server = EthernetServer(80);

File myFile;

char buff[50];

// Will use a 8 byte serial number, although it is not like the UUID, the probability to have the same S/n's is 1:18446744073709551616. 
// It could be assured from factory, that there will be no duplicates

ModuleManager moduleMgr(&logger);

void setup() {
  logger.init();
  //logger.info("Setting up system");
  Wire.begin();             // Initialize I2C communication with client modules
  Ethernet.begin(mac, ip);  // Initialize ethernet communication
  server.begin();           // Start the web server

  pinMode(10, OUTPUT);      // Hardware SS pin (DO NOT CHANGE)
//  logger.info("SYSTEM SETUP SUCCESSFULL");
//  EEPROM.write(0, 1);
//  EEPROM.write(1, 16);
//  EEPROM.write(2, 240);
  //moduleMgr.init();
  //Serial.begin(9600);
  //readFromCard();
  if (!SD.begin(4)) {
//    Serial.println("SD card initialization failed!");
    return;
  }
//  Serial.println("SD card initialization done.");  
//  logger.info("SYSTEM SETUP SUCCESSFULL");
  moduleMgr.updateModules();
}

void loop() {
//  logger.info("LOOP");
  listenForWebClients();
  moduleMgr.updateModules();
  //statusLed.update();
  //logger.info(moduleMgr.getInfo(0));
//  moduleMgr.updateModules();
  delay(5000);
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
        Serial.print(c);
        if (c == '\n' && currentLineIsBlank) {
          // Send HTTP header
          strcpy_P(buff, (char*)pgm_read_word(&(string_table[0])));
          client.print(buff);
          client.println();
          // Send JSON header
          strcpy_P(buff, (char*)pgm_read_word(&(string_table[1])));
          client.print(buff);

          // re-open the file for readig:
          char fileName[] = "00000000.XML";
          byte MODULES = 7;
          for (byte i = 1; i <= MODULES; i++){
            fileName[7] = i + '0';
            myFile = SD.open(fileName);
            if (myFile) {
              // read from the file until there's nothing else in it:
              while (myFile.available()) {
                client.write(myFile.read());
              }
              // close the file:
              myFile.close();
              client.println();
              if (i != MODULES) {
                strcpy_P(buff, (char*)pgm_read_word(&(string_table[2])));
                client.print(buff);              
              } else {
                strcpy_P(buff, (char*)pgm_read_word(&(string_table[3])));
                client.print(buff);              
              }
            }
          }
          
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

// TODO: Implement interpetMessage function
byte interpretMessage(String msg){
  return NEW_MODULE_ID;
}

