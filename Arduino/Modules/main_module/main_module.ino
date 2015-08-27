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
#include "RealTime.h"
#include "CommandParser.h"

//#include <RTC.h> // TODO: import correct RTC header file

String info;

StatusLed statusLed(1, 2, 3); // TODO: Set the correct values

Logger logger(LOG_DEBUG);
RealTime RTC(40, 42, 44);
CommandParser commandParser(&logger);

// TODO: This info will be read from the SD card, but will be set from here for now
byte mac[] = { 0x00, 0xAA, 0xBB, 0xCC, 0xDE, 0x22 };
IPAddress ip(192,168,1, 180);
IPAddress gateway(192,168,0, 1);
IPAddress subnet(255, 255, 255, 0);
EthernetServer server = EthernetServer(1000);

File myFile;

char buff[50];

// Will use a 8 byte serial number, although it is not like the UUID, the probability to have the same S/n's is 1:18446744073709551616. 
// It could be assured from factory, that there will be no duplicates

ModuleManager moduleMgr;

void setup() {
  logger.init();
  //logger.info("Setting up system");
  Wire.begin();             // Initialize I2C communication with client modules
  Ethernet.begin(mac, ip);  // Initialize ethernet communication
  server.begin();           // Start the web server

  RTC.init();               // Initialize RTC module

  moduleMgr = ModuleManager::getInstance();
  moduleMgr.setLogger(&logger);
  moduleMgr.setRTC(&RTC);
  pinMode(10, OUTPUT);      // Hardware SS pin (DO NOT CHANGE)
  Serial.print("Time&date is: ");
  Serial.println(RTC.getDateTime());
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
  //moduleMgr.updateModules();
  //statusLed.update();
  //logger.info(moduleMgr.getInfo(0));
  moduleMgr.updateModules();
  delay(5000);
}

// TODO: format sent data to something friendly
void listenForWebClients(){
  EthernetClient client = server.available();
  String parameters="";
  String clientRequest;
  if (client) {
    // an http request ends with a blank line
    boolean currentLineIsBlank = true;
    while (client.connected()) {
      if (client.available()) {
        char c = client.read();
        clientRequest += c;        
//        Serial.print(c);
        if (c == '\n' && currentLineIsBlank) {
          Serial.print("PARAMETERS ARE: ");
          Serial.println(parameters);
          //if (parameters == "") Serial.println("No parameters");
//          Serial.println(parameters.indexOf('+'));
//          Serial.println(parameters.indexOf('-'));
          Serial.println("PARAMETERS END");
          if (parameters != "") commandParser.parse(parameters);            //REVERTED AS BREAKS FUNCTIONALITY AT THIS MOMENT
          // Send HTTP header
          strcpy_P(buff, (char*)pgm_read_word(&(string_table[0])));
          client.print(buff);
          client.println();
          // Send JSON header
//          strcpy_P(buff, (char*)pgm_read_word(&(string_table[1])));           //REVERTED AS BREAKS FUNCTIONALITY AT THIS MOMENT
//          client.print(buff);          //REVERTED AS BREAKS FUNCTIONALITY AT THIS MOMENT

          // Get the data and write directly to the client from CommandParser
          Serial.println("Now getting data from command parser");
          while (commandParser.available()){          //REVERTED AS BREAKS FUNCTIONALITY AT THIS MOMENT
            client.write(commandParser.read());          //REVERTED AS BREAKS FUNCTIONALITY AT THIS MOMENT
          }          //REVERTED AS BREAKS FUNCTIONALITY AT THIS MOMENT


          // Open JSON files for reading:
          
          //REVERTED AS BREAKS FUNCTIONALITY AT THIS MOMENT
//          File root = SD.open("/2002/01/29/"); // <- Ezt mododsitsd aszerint, hogy milyen datum van
//          root.rewindDirectory();
//          File jsonFile = root.openNextFile();
//          bool noFiles = true;

//          while (jsonFile){
//            noFiles = false;
//            while (jsonFile.available()) {
//              client.write(jsonFile.read());
//            }
            // close the file:
//            jsonFile.close();            
//            client.println();            
//            jsonFile = root.openNextFile();
//            if (jsonFile) {
//              strcpy_P(buff, (char*)pgm_read_word(&(string_table[4])));
//              client.print(buff);                            
//            }
//          }
          // If there are no files on the SD card, the JSON file still has to be created correctly
//          if (noFiles) strcpy_P(buff, (char*)pgm_read_word(&(string_table[6])));
//          else strcpy_P(buff, (char*)pgm_read_word(&(string_table[5])));
//          client.print(buff);
          // END - REVERTED AS BREAKS FUNCTIONALITY AT THIS MOMENT
          clientRequest = "";
          break;
        }
        if (c == '\n') {
          // you're starting a new line
          currentLineIsBlank = true;
          // Search and process parameters
          if (clientRequest.startsWith("GET /GET") || clientRequest.startsWith("GET /POST")) {
             for (int i = 5; i < clientRequest.length() - 11; i++){
               parameters += clientRequest[i];
             }
             clientRequest = "";
          }
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

