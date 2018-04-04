#include <OneWire.h>
#include <DallasTemperature.h>
#include <ESP8266WiFi.h>
#include <WiFiUDP.h>
#include"SoftwareSerial.h"
#include<String>
#define ONE_WIRE_BUS 0
OneWire oneWire(ONE_WIRE_BUS);
DallasTemperature sensors(&oneWire);
WiFiUDP Client;
WiFiClient client;
byte fromserver=0;
const char* ssid     = "Fallinlove";      // SSID
const char* password = "Toiyeu8438626";      // Password
const char* host = "10.0.128.167";  // IP serveur - Server IP
const char ip[]="10.0.128.167";  //IP UDP server
const int   tcp_port = 9882;            // Port serveur - Server Port
String ID="-1";
char* serdata="connection";

void setup() 
{
  // put your setup code here, to run once:
  Serial.begin(115200);
  WiFi.mode(WIFI_STA); 
  WiFi.begin(ssid,password);
  Serial.println();
  Serial.println();
  Serial.print("Wait for WiFi... ");
    
  while(WiFi.status() != WL_CONNECTED) //check wifi connection, try to 
  {
    Serial.print(".");
        delay(500);
  }
     Serial.println("");
    Serial.println("WiFi connected");
    Serial.println("IP address: ");
    Serial.println(WiFi.localIP());   
 if (!client.connect(host, tcp_port)) {
  Serial.println("connection failed"); 
  return;
  }
else{
  Serial.println("connected");
  return;
 }
}
float temp=sensors.getTempCByIndex(0);
void loop() 
{
 sensors.requestTemperatures();  
 temp=sensors.getTempCByIndex(0);
const int udp_port=9870;
 tcpsend();
 if(ID!="0"){
    }
   /*Serial.print("connecting to ");
    Serial.println(ip);*/
    String s=ID+(String)temp;
    s.toCharArray(serdata,s.length());
    udpsend();    
    delay(3000);
}

void udpsend()
  {
  Client.beginPacket(ip,9870);
  Client.write(serdata);
  Client.endPacket();
  delay(1000);
  return;
  }
void tcpsend()
{
  client.connect(host, tcp_port);
  if(ID=="-1"){
    client.println("-1");
    client.flush();  // clear buff
    String line = client.readStringUntil('\n');
    ID=line;
    Serial.println("receive id "+ line);
  }
  else {
    client.println(ID);
    client.flush();  // clear buff
  }
  client.stop();
  return;
}

