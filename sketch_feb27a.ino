
#include <ESP8266WiFi.h>
#include <WiFiUDP.h>

WiFiUDP Client;
WiFiClient client;
char* serdata="connect node";
byte fromserver=0;
const char* ssid     = "Fallinlove";      // SSID
const char* password = "Toiyeu8438626";      // Password
const char* host = "10.0.128.164";  // IP serveur - Server IP
const char ip[]="10.0.128.164";  //IP UDP server
const int   tcp_port = 9882;            // Port serveur - Server Port


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

void loop() 
{
const int udp_port=9870;
int i=0;  
 tcpsend();
 if(i==0){
    }
   /*Serial.print("connecting to ");
    Serial.println(ip);*/
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
  Serial.println("request id");
  client.println("this is meesage from node");
  client.flush();  // Bufffer löschen
  String line = client.readStringUntil('\n');
  Serial.println(line);
  client.stop();
  return;
}

