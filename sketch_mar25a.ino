//Include thư viện
#include <ESP8266WiFi.h>
#include <WiFiClient.h>
#include <ESP8266WebServer.h>
#include"SoftwareSerial.h"
#include "line.h" //Our HTML webpage contents with javascripts
#include<String>
const char* ssid     = "Fallinlove";
const char* password = "Toiyeu8438626";
const char* host = "10.0.128.167";  // IP serveur - Server IP
const char ip[]="10.0.128.167";  //IP UDP server
const int   tcp_port = 9885;            // Port serveur - Server Port
WiFiClient client;
WiFiClient web;
ESP8266WebServer  server(80);
void handleRoot() {
 String s = MAIN_page; //Read HTML contents
 server.send(200, "text/html", s); //Send web page
}

void setup(void)
{
  Serial.begin(115200);
  Serial.println();
  Serial.print("Connecting to ");
  Serial.println(ssid);
  
  WiFi.begin(ssid, password);
  
  while (WiFi.status() != WL_CONNECTED) {
    delay(500);
    Serial.print(".");
  }
  Serial.println(ssid);
  Serial.println(password);
  Serial.println("");
  Serial.println("WiFi connected");  
  server.on("/", handleRoot);
  server.on("/readADC", handleADC);
  server.begin();
  Serial.println(WiFi.localIP());
  delay(1000);
}

String req="";
void handleADC() {
 String adcValue = req;
 server.send(200, "text/plane", adcValue); //Send ADC value only to client ajax request
}
void loop(void)
{ 
 if (!client.connect(host, tcp_port)) {
  Serial.println("connection failed");
  }
  else{
  req = client.readStringUntil('\r');
  req.replace("\n","\0");
  }
  Serial.println(req);
  server.handleClient();          //Handle client requests
  delay(1000);
}


