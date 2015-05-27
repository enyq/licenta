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

#include <SPI.h>
#include <Ethernet.h> // TODO: To be changed later to Ethernet module, for the shield

String info;

// TODO: This info will be read from the SD card, but will be set from here for now
byte mac[] = { 0x00, 0xAA, 0xBB, 0xCC, 0xDE, 0x22 };
IPAddress ip(192,168,1, 180);
IPAddress gateway(192,168,1, 1);
IPAddress subnet(255, 255, 255, 0);
EthernetServer server = EthernetServer(1000);

void setup() {
  Ethernet.begin(mac, ip);  // Initialize ethernet communication
  server.begin();           // Start the web server
  Serial.begin(9600);
  Serial.println("Setting up system");
  // On the Ethernet Shield, CS is pin 4. It's set as an output by default.
  // Note that even if it's not used as the CS pin, the hardware SS pin
  // (10 on most Arduino boards, 53 on the Mega) must be left as an output
  // or the SD library functions will not work.
  pinMode(10, OUTPUT);
  Serial.println("SYSTEM SETUP SUCCESSFULL");
}

void loop() {
  listenForWebClientsXML();
  delay(500);
}

void listenForWebClientsXML(){
  EthernetClient client = server.available();
  if (client) {
    Serial.println("Client connected!");
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
          client.println("<ModuleInfo>");
          for (int i = 0; i < 3; i++){
            client.println("  <MODULE>");
            client.print("    <ModuleID>");
            client.print(getID(i));
            client.println("</ModuleID>");
            client.print("    <ModuleName>");
            client.print(getName(i));
            client.println("</ModuleName>");
            client.print("    <DataTime>");
            client.print(getTime(i));
            client.println("</DataTime>");
            client.print("    <ModuleData>");
            client.print(getInfo(i));
            client.println("</ModuleData>");
            client.println("  </MODULE>");
          }
          client.println("</ModuleInfo>");
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


// TODO: format sent data to something friendly
void listenForWebClients(){
  EthernetClient client = server.available();
  if (client) {
    Serial.println("Client connected!");
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
          client.print("Number of active modules connected: ");
          client.print(getModuleNumber());
          client.println("<br />");
          for (byte i = 0; i <= 2; i++){// moduleMgr.getModuleNumber(); i++){
            client.print("Info from client ");
            client.print(i);
            client.print(": ");
            client.print(getInfo(i));
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

// TODO: Implement interpetMessage function
/*byte interpretMessage(String msg){
  return NEW_MODULE_ID;
}*/

int getModuleNumber(){
  return 3;
}

String getInfo(int x){
  switch (x){
    case 0:
      return "ABCDEFGHIJKLMNOPQRSTUVWZ";
    case 1:
      return "12345678901234567890123456789012";
    case 2:
      return "abcd12345efghjijsalkjdaljd";
    default:
      return "No message!";
  }
}

String getTime(int x){
  switch (x){
    case 0:
      return "2015-05-26T19:20:30.45";
    case 1:
      return "2015-05-26T19:33:30.45";
    case 2:
      return "2015-05-26T19:49:30.45";
    default:
      return "No message!";
  }  
}

String getID(int x){
  switch (x){
    case 0:
      return "8f739c7a0ea44e4e8ca4ac448e57b1c8";
    case 1:
      return "e444a000558c4a978468917a49cac757";
    case 2:
      return "8c33b65dcc2948bfbfe711776b3afa9e";
    default:
      return "INVALID!";
  } 
}

String getName(int x){
  switch (x){
    case 0:
      return "Kitchen Security";
    case 1:
      return "Living temperature";
    case 2:
      return "Light control for bedroom";
    default:
      return "INVALID!";
  } 
}
