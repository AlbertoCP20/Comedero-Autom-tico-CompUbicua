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
time_t date = now();


// Variables to manage millis() in order to avoid delay
unsigned long previousMillisServo = 0;
unsigned long previousMillisWBomb = 0;
unsigned long servoInterval = 1000;
unsigned long wBombInterval = 1000;
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

  waterBomb();
  servoMotor();
  buzzer();
  infraredSensor();
  
  PublisMqtt(pressureSensor());

  delay(1000);
}

// Waterbomb behaviour
void waterBomb(){
  unsigned long currentMillis = millis();
  digitalWrite(pinwaterBomb,HIGH); // TURN ON BOMB
  if((unsigned long)(currentMillis - previousMillisWBomb) >= wBombInterval){
      digitalWrite(pinwaterBomb,LOW); // TURN OFF BOMB
      previousMillisWBomb = currentMillis;
    }
  //delay(10000); // Wait 10 secs
}

// Servomotor behaviour

// IDEA CAMBIAR HORA POR UNA SEÑAL
void servoMotor(){
  int h = (int) hour(date);
  int min = (int) minute(date);
  int sec = (int) second(date);
// Print the h
  Serial.print("h: ");
  Serial.print(h);
  Serial.print(":");
  Serial.print(min);
  Serial.print(":");
  Serial.println(sec);

  if(h == 17 && min == 25 && sec == 30){
  moverServo();
  }else if(h == 17 && min == 26 && sec == 0){
    moverServo();
  }else if(h == 17 && min == 26 && sec == 30){
    moverServo();
  }
}

// Servomotor movement
void moverServo(){
    unsigned long currentMillis = millis();
    servo.write(180);
    if((unsigned long)(currentMillis - previousMillisServo) >= servoInterval){
      servo.write(90);
      previousMillisServo = currentMillis;
    }
    //delay(1000);
    
}

// Buzzer behaviour
void buzzer(){
  int h = (int) hour(date);
  int min = (int) minute(date);
  int sec = (int) second(date);
// Print h
  Serial.print("h: ");
  Serial.print(h);
  Serial.print(":");
  Serial.print(min);
  Serial.print(":");
  Serial.println(sec);

  if(h == 18 && min == 1 && sec == 0){
    playSong();
  }else if(h == 17 && min == 26 && sec == 0){
    playSong();
  }else if(h == 17 && min == 26 && sec == 30){
    playSong();
  }
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
}
// Infrared sensor behaviour
void infraredSensor(){
  int detection = 0; // We capture what is happening with the sensor
  detection = digitalRead(pinInfrarojo);
  if (detection == HIGH) {
      Serial.println("CAREFULL!!!");
  }
}

// Pressure sensor behaviour
int pressureSensor(){
  int resRead = analogRead(A0); // Read sensor value
  int valorGramos = map(resRead,0,1023,30,10000); // We change the analog range by one in grams
  Serial.print("Sensor de presion: ");
  Serial.println(resRead); // Print sensor values
    Serial.print("Valor en gramos: ");
  Serial.println(valorGramos); // Print the conversion
  return valorGramos;
}