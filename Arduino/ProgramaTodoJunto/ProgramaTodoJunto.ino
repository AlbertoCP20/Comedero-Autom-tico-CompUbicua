// LIBRARIES

// Servomotor libraries
#include <Servo.h>

// Conexion libraries (WIFI / MQTT)
#include <ESP8266WiFi.h>
#include <PubSubClient.h>

// Weight sensor libraries 
#include <HX711_ADC.h>
#if defined(ESP8266)|| defined(ESP32) || defined(AVR)
#include <EEPROM.h>
#endif

#include "config.h"// Configuraton file
#include "MQTT.hpp"//MQTT functions
#include "ESP8266_Utils.hpp"//Wifi connection
#include "ESP8266_Utils_MQTT.hpp"//MQTT Connection

#include "PressureSensor.hpp" //PressureSensor functions

Servo servo;

// Pin intialitation
const int pinwaterBomb = 16; //D0
const int pinServo =5; //D1
const int pinBuzzer=4; //D2
const int pinInfrarojo=0; //D3
const int pinStrength = A0;


// Variables to manage weight
float maxFoodWeight = 1000; // The maximun amount of food that the bowl can hold
float maxWaterWeight = 200; // The maximum amount of water that the bowl can hold
float currentFoodWeight = 0;
float currentWaterWeight = 0;

void setup() {
  Serial.begin(115200);
   // Start conexion
  SPIFFS.begin();
  ConnectWiFi_STA();
  InitMqtt();

  // Setup pressure sensor
  initPressureSensor();

  // Establish pinMode
  pinMode(pinwaterBomb,OUTPUT);
  servo.attach(pinServo,500,2500); // Initialize servo, pin 5 from 500ms = 0º to 2500ms = 180º
  pinMode(pinBuzzer,OUTPUT);
  pinMode(pinInfrarojo,INPUT);

}

void loop() {
  HandleMqtt();

  // If the user set his/her own ration, then change the value of the maximun weight
  if(isTopicRacion()){
    maxFoodWeight = getContent().toFloat();
    setTopicDefault();
  }
  
  // If server wants to get the values of the weight and the infraredSensor
  if(getContent() == "1"){
    // Time to reset the weight
    for(int j =0; j <100;j++){
      currentFoodWeight = getPressureSensorValue();
    }
    // Avoid negative values
    if(currentFoodWeight < 0){
      currentFoodWeight = 0;
    }
    PublishMqtt("ComederoA30/Sensor/PressureF",currentFoodWeight);
    PublishMqtt("ComederoA30/Sensor/Infrared",infraredSensor());
    // Reset signals
    PublishMqtt("ComederoA30/Signals",0);
  }

  // If server send signal to refill food and water bowls
  if(getContent() == "2"){
    PublishMqtt("ComederoA30/Signals",0);
    
    Serial.println("SE HA ACTIVADO LA SEÑAL!!");

    // Servomotor working 
    float currentFoodWeight = getPressureSensorValue();
    // Time to reset the weight
    for(int j =0; j <100;j++){
      currentFoodWeight = getPressureSensorValue();
    }

    openServo();
    while(currentFoodWeight <= maxFoodWeight){
      currentFoodWeight = getPressureSensorValue();
      Serial.println(currentFoodWeight);
      // Keep data from MQTT (this is here because of a bug, if too much time were spent in this loop, the part of refill was called again and again)
      mqttClient.loop();
    }
    closeServo();

    // Waterbomb working 
    currentWaterWeight = analogRead(pinStrength);
    openBomb();
    while(currentWaterWeight <= maxWaterWeight){
      currentWaterWeight = analogRead(pinStrength);
      Serial.println(currentWaterWeight);
      // Keep data from MQTT (this is here because of a bug, if too much time were spent in this loop, the part of refill was called again and again)
      mqttClient.loop();

    }
    closeBomb();

    // Start buzzer
    playSong();
  }

  Serial.println("- - - - - - -");
  delay(1000);

}

// Servomotor open
void openServo(){
    servo.write(180);
}

// Servomotor close
void closeServo(){
    servo.write(90);
    
}

// Waterbomb behaviour
void openBomb(){
  digitalWrite(pinwaterBomb,HIGH); // TURN ON BOMB
}

void closeBomb(){
  digitalWrite(pinwaterBomb,LOW); // TURN OFF BOMB
}

// Infrared sensor behaviour
int infraredSensor(){
  int detection = 0; // We capture what is happening with the sensor
  detection = digitalRead(pinInfrarojo);
  if (detection == HIGH) {
      Serial.println("Se está vaciando...");

  }
  return detection;
}

// Reproduce caribbean pirates song.
void playSong(){
  tone(pinBuzzer,293.66,200);
  delay(100);
  tone(pinBuzzer,293.66,100);
  delay(100);
  tone(pinBuzzer,293.66,200);
  delay(200);
  tone(pinBuzzer,293.66,100);
  delay(100);
  tone(pinBuzzer,293.66,200);
  delay(200);
  tone(pinBuzzer,293.66,100);
  delay(100);
  tone(pinBuzzer,293.66,100);
  delay(100);
  tone(pinBuzzer,293.66,100);
  delay(100);
  tone(pinBuzzer,293.66,100);
  delay(100);
  tone(pinBuzzer,293.66,200);
  delay(200);
  tone(pinBuzzer,293.66,100);
  delay(100);
  tone(pinBuzzer,293.66,200);
  delay(200);
  tone(pinBuzzer,293.66,100);
  delay(100);
  tone(pinBuzzer,293.66,200);
  delay(200);
  tone(pinBuzzer,293.66,100);
  delay(100);
  tone(pinBuzzer,293.66,100);
  delay(100);
  tone(pinBuzzer,293.66,100);
  delay(100);
  tone(pinBuzzer,293.66,100);
  delay(100);
  tone(pinBuzzer,293.66,200);
  delay(200);
  tone(pinBuzzer,293.66,100);
  delay(100);
  tone(pinBuzzer,293.66,200);
  delay(200);
  tone(pinBuzzer,293.66,100);
  delay(100);
  tone(pinBuzzer,293.66,200);
  delay(200);
  tone(pinBuzzer,293.66,100);
  delay(100);
  tone(pinBuzzer,293.66,100);
  delay(100);
  tone(pinBuzzer,440,100);
  delay(100);
  tone(pinBuzzer,523.25,100);
  delay(100);
  tone(pinBuzzer,587.33,100);
  delay(200);
  tone(pinBuzzer,587.33,100);
  delay(200);
  tone(pinBuzzer,587.33,100);
  delay(100);
  tone(pinBuzzer,659.25,100);
  delay(100);
  tone(pinBuzzer,698.45,100);
  delay(200);
  tone(pinBuzzer,698.45,100);
  delay(200);
  tone(pinBuzzer,698.45,100);
  delay(100);
  tone(pinBuzzer,783.99,100);
  delay(100);
  tone(pinBuzzer,659.25,100);
  delay(200);
  tone(pinBuzzer,659.25,100);
  delay(200);
  tone(pinBuzzer,587.33,100);
  delay(100);
  tone(pinBuzzer,523.25,100);
  delay(100);
  tone(pinBuzzer,523.25,100);
  delay(100);
  tone(pinBuzzer,587.33,100);
  delay(300);
  tone(pinBuzzer,440,100);
  delay(100);
  tone(pinBuzzer,523.25,100);
  delay(100);
  tone(pinBuzzer,587.33,100);
  delay(200);
  tone(pinBuzzer,587.33,100);
  delay(200);
  tone(pinBuzzer,587.33,100);
  delay(100);
  tone(pinBuzzer,659.25,100);
  delay(100);
  tone(pinBuzzer,698.45,100);
  delay(200);
  tone(pinBuzzer,698.45,100);
  delay(200);
  tone(pinBuzzer,698.45,100);
  delay(100);
  tone(pinBuzzer,783.99,100);
  delay(100);
  tone(pinBuzzer,659.25,100);
  delay(200);
  tone(pinBuzzer,659.25,100);
  delay(200);
  tone(pinBuzzer,587.33,100);
  delay(100);
  tone(pinBuzzer,523.25,100);
  delay(100);
  tone(pinBuzzer,587.33,100);
  delay(400);
  tone(pinBuzzer,440,100);
  delay(100);
  noTone(pinBuzzer);
}