#include <Time.h>
#include <TimeLib.h>
#include <Servo.h>
#include <ESP8266WiFi.h>
#include <PubSubClient.h>

#include "config.h"// Configuraton file
#include "MQTT.hpp"//MQTT functions
#include "ESP8266_Utils.hpp"//Wifi connection
#include "ESP8266_Utils_MQTT.hpp"//MQTT Connection

Servo servo;

// Pin intialitation
const int pinwaterBomb = 16;
const int pinServo =5;
const int pinBuzzer=4;
const int pinInfrarojo=0;
const int pinPresion=A0;


// Variables to manage weight
const int maxFoodWeight = 1000; // The maximun amount of food that the bowl can hold
const int maxWaterWeight = 1000; // The maximum amount of water the the bowl can hold

void setup() {
  Serial.begin(115200);
   // Start conexion
  SPIFFS.begin();
  ConnectWiFi_STA();
  InitMqtt();
  // Establish pinMode
  pinMode(pinwaterBomb,OUTPUT);
  servo.attach(pinServo,500,2500); // Initialize servo, pin 5 from 500ms = 0º to 2500ms = 180º
  pinMode(pinBuzzer,OUTPUT);
  pinMode(pinInfrarojo,INPUT);

}

void loop() {
  HandleMqtt();
  // If server wants to get the values of the weight and the infraredSensor
  if(getContent() == "1S"){
    PublishMqtt("Comedero1/Sensor/PressureF",pressureSensorF());
    PublishMqtt("Comedero1/Sensor/PressureW",pressureSensorW());
    PublishMqtt("Comedero1/Sensor/Infrared",infraredSensor());
  }
  // If server send signal to refill food and water bowls
  if(getContent() == "1A"){
    Serial.println("SE HA ACTIVADO LA SEÑAL!!");

    // Servomotor working 
    int currentFoodWeight = pressureSensorF();
    openServo();
    while(currentFoodWeight <= maxFoodWeight){
      currentFoodWeight = pressureSensorF();
    }
    closeServo();

    // Waterbomb working 
    int currentWaterWeight = pressureSensorW();
    openBomb();
    while(currentWaterWeight <= maxWaterWeight){
      currentFoodWeight = pressureSensorW();
    }
    closeBomb();

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
  if (detection == LOW) {
      Serial.println("Se está vaciando...");

  }
  return detection;
}

// Pressure sensor of food behaviour
int pressureSensorF(){
  int resRead = analogRead(A0); // Read sensor value
  int valorGramos = map(resRead,0,1023,30,10000); // We change the analog range by one in grams
  Serial.print("Sensor de presion: ");
  Serial.println(resRead); // Print sensor values
    Serial.print("Valor en gramos: ");
  Serial.println(valorGramos); // Print the conversion
  return valorGramos;
}

// Pressure sensor of water behaviour
int pressureSensorW(){
  int resRead = analogRead(A0); // Read sensor value
  int valorGramos = map(resRead,0,1023,30,10000); // We change the analog range by one in grams
  Serial.print("Sensor de presion: ");
  Serial.println(resRead); // Print sensor values
  Serial.print("Valor en gramos: ");
  Serial.println(valorGramos); // Print the conversion
  return valorGramos;
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