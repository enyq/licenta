////Arduino push notifications
//
// General code from http://www.pushingbox.com for Arduino + Ethernet Shield (official) v1.2
// Modified by Perilex 2014-05-02 for google cloud messaging (GCM)
// Send push notification when button is pressed
////

#include <SPI.h>
#include <UIPEthernet.h>
#include "Base64.h"
#include <XMPPClient.h>
#include <PubSubClient.h>
#include <avr/pgmspace.h>
  /////////////////
 // MODIFY HERE //
/////////////////
byte mac[] = { 0x00, 0xAA, 0xBB, 0xCC, 0xDE, 0x19 };   // Be sure this address is unique in your network

//Enter your secret API_key of your project, the RegID of the respective Android app on your specific device
//and your data
char API_key[] = "AIzaSyDBB_zjg-3x_zIBa-wpAIEdsDY2Kp99Fgg"; 
char RegID[] = "APA91bFiXv2f5ZMiYh4WcZ083ZHqEBOR_sLG9m6nVPybEUqaoL5Fdv_YkSPZz24diF2A5eUJe7UvjD9ZuV_pWblrIgTTzZGL8CRSAMN821T1m_E8EMs8H09fRa_oh2YxXWbJ6N0jy4IK3eP9-ysufPHpNhByKtN-_w";
char data[] ="data={\"Hello\" : \"Szia az Arduinorol\"}"; //data to send. obey GCM format!
char userName[] = "760334234140";
char password[] = "AIzaSyDBB_zjg-3x_zIBa-wpAIEdsDY2Kp99Fgg";

//Numeric Pin where you connect your switch
uint8_t ButtonPin = 14; // Example : the switch is connect to the Pin 14 and GND

// Debug mode
boolean DEBUG = true;
  //////////////
 //   End    //
//////////////


//char serverName[] = "android.googleapis.com"; //GCM server adress
char serverName[] = "gcm.googleapis.com"; //GCM server adress
boolean ButtonPinState = false;                // Save the last state of the Pin
boolean lastConnected = false;                 // State of the connection last time through the main loop

XMPPClient client(serverName, 5235); //5222

// Initialize the Ethernet client library
// with the IP address and port of the server 
// that you want to connect to (port 80 is default for HTTP):
//EthernetClient client;


void setup() {
  Serial.begin(9600);
  pinMode(ButtonPin, INPUT);
  digitalWrite(ButtonPin, HIGH); // activate internal pullup resistor
  
  // start the Ethernet connection:
  if (Ethernet.begin(mac) == 0) {
    Serial.println("Failed to configure Ethernet using DHCP");
    // no point in carrying on, so do nothing forevermore:
    while(true);
  }
  else{
    Serial.println("Ethernet ready");
    // print the Ethernet board/shield's IP address:
    Serial.print("My IP address: ");
    Serial.println(Ethernet.localIP());
  }
  // give the Ethernet shield a second to initialize:
  delay(7000);
}

void loop()
{
  delay(5000);
      ////
      // Listening for the ButtonPin state
      ////

      Serial.println("Sending to GCM");
      sendToGCM2(RegID);
   
      //DEBUG part
      // this write the respons from GCM Server.
      // You should see a "200 OK"
      if (client.available()) 
         {
         char c = client.read();
         if(DEBUG){Serial.print(c);}
         }
      
      // if there's no net connection, but there was one last time
      // through the loop, then stop the client:
      if (!client.connected() && lastConnected) 
         {
         if(DEBUG){Serial.println();}
         if(DEBUG){Serial.println("disconnecting.");}
         client.stop();
         }
      lastConnected = client.connected();
}

void sendToGCM2(char RegID[]){
  client.stop();
  if (client.connect(userName, serverName, API_key, password)) {
    if(DEBUG){Serial.println("connected");}

    if(DEBUG){Serial.println("sending request");}
    client.sendPresence();
    client.sendMessage(RegID, "<message><gcm xmlns=\"google:mobile:data\">{&quot;to&quot;: &quot;APA91bFiXv2f5ZMiYh4WcZ083ZHqEBOR_sLG9m6nVPybEUqaoL5Fdv_YkSPZz24diF2A5eUJe7UvjD9ZuV_pWblrIgTTzZGL8CRSAMN821T1m_E8EMs8H09fRa_oh2YxXWbJ6N0jy4IK3eP9-ysufPHpNhByKtN-_w&quot;, &quot;data&quot;: {&quot;message_destination&quot;: &quot;RegId&quot;, &quot;HELLO&quot;: 1234, &quot;message_id&quot;: &quot;C3M2DvTh&quot;}, &quot;message_id&quot;: &quot;reg_id&quot;}</gcm></message>");
    delay(2000);
    client.close();
  }
}

//Function for sending the request to GCM
void sendToGCM(char RegID[]){
  client.stop();
  if(DEBUG){Serial.println("connecting...");}

  if (client.connect(serverName, 5235)) {
    if(DEBUG){Serial.println("connected");}

    if(DEBUG){Serial.println("sending request");}
/*    client.print("POST /gcm/send"); // http POST request
    client.println(" HTTP/1.1");
    client.print("Host: ");
    client.println(serverName);
    client.println("User-Agent: Arduino");
    client.println("Content-Type: application/x-www-form-urlencoded;charset=UTF-8");
    client.print("Authorization:key=");
    client.println(API_key);
    client.print("Content-length: "); // has to be exactly the number of characters (bytes) in the POST body
    client.println(199+1+strlen(data)); // calculate content-length    
    client.println("Connection: close");
    client.println("");
    client.print("registration_id="); //199 characters
    client.print(RegID);
    client.print("&"); // 1 character
    client.print(data);*/
    client.println("<message><gcm xmlns=\"google:mobile:data\">{&quot;to&quot;: &quot;APA91bFiXv2f5ZMiYh4WcZ083ZHqEBOR_sLG9m6nVPybEUqaoL5Fdv_YkSPZz24diF2A5eUJe7UvjD9ZuV_pWblrIgTTzZGL8CRSAMN821T1m_E8EMs8H09fRa_oh2YxXWbJ6N0jy4IK3eP9-ysufPHpNhByKtN-_w&quot;, &quot;data&quot;: {&quot;message_destination&quot;: &quot;RegId&quot;, &quot;HELLO&quot;: 1234, &quot;message_id&quot;: &quot;C3M2DvTh&quot;}, &quot;message_id&quot;: &quot;reg_id&quot;}</gcm></message>");
    if(DEBUG){
    Serial.print(strlen(data));
    Serial.println(" bytes data");
    }
  } 
  else {
    if(DEBUG){Serial.println("connection failed");}
  }
delay(10);  
}
