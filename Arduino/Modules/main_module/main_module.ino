// SD card attached to SPI bus as follows: MOSI - pin 11 MISO - pin 12 CLK - pin 13 CS - pin 4
// Ethernet attached to pins 10, 11, 12, 13


#include "Module.h"
#include "StatusLed.h"
#include <Wire.h>
#include <SPI.h>
#include <Ethernet.h>
#include <SD.h>

String info;

StatusLed statusLed(1, 2, 3); // TODO: Set the correct values

// TODO: This info will be read from the SD card, but will be set from here for now
byte mac[] = {
  0x00, 0xAA, 0xBB, 0xCC, 0xDE, 0x02 };
IPAddress ip(192,168,1, 177);
IPAddress gateway(192,168,1, 1);
IPAddress subnet(255, 255, 0, 0);
EthernetServer server(80);

Module mod[110];

void setup() {
  Wire.begin(); // Initialize I2C communication with client modules
  Ethernet.begin(mac, ip); // Initialize ethernet communication
  server.begin(); // Start the web server
  
  // On the Ethernet Shield, CS is pin 4. It's set as an output by default.
  // Note that even if it's not used as the CS pin, the hardware SS pin
  // (10 on most Arduino boards, 53 on the Mega) must be left as an output
  // or the SD library functions will not work.
  pinMode(10, OUTPUT);
  
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
          for (int analogChannel = 0; analogChannel < 6; analogChannel++) {
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
