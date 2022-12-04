#include <ESP8266WiFi.h>
#include <PubSubClient.h>

#include "config.h"// Configuraton file
#include "MQTT.hpp"//MQTT functions
#include "ESP8266_Utils.hpp"//Wifi connection
#include "ESP8266_Utils_MQTT.hpp"//MQTT Connection

void setup(void){
  Serial.begin(115200);
  SPIFFS.begin();
  ConnectWiFi_STA();
  InitMqtt();
}

void loop(){
  HandleMqtt();
  PublisMqtt(millis());
  delay(1000);
}