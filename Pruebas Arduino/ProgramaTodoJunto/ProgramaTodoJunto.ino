#include <Time.h>
#include <TimeLib.h>
#include <Servo.h>

Servo servo;

// Establecimiento de pines
const int pinBombaAgua = 16;
const int pinServo =5;
const int pinBuzzer=4;
const int pinInfrarojo=0;
const int pinPresion=A0;
time_t fecha = now();

void setup() {
  Serial.begin(9600);
  // Definimos el modo de los pines
  pinMode(pinBombaAgua,OUTPUT);
  servo.attach(pinServo,500,2500); // Inicializamos el servo, en el pin 5 500ms = 0º a 2500ms = 180º
  pinMode(pinBuzzer,OUTPUT);
  pinMode(pinInfrarojo,INPUT);

}

void loop() {
  

  bombaAgua();
  servoMotor();
  buzzer();
  sensorInfrarojo();
  sensorPresion();
  delay(1000);
  
}

// Función que almacena el comportamiento de la bomba de agua
void bombaAgua(){
  digitalWrite(pinBombaAgua,HIGH); // ENCENDER BOMBA
  delay(10000); // Esperamos 10 segs
  digitalWrite(pinBombaAgua,LOW); // APAGAR BOMBA
  delay(10000);
}

// Función que almacena el comportamiento del servomotor
void servoMotor(){
    int hora = (int) hour(fecha);
  int minuto = (int) minute(fecha);
  int segundo = (int) second(fecha);
// Imprimimos la hora
  Serial.print("Hora: ");
  Serial.print(hora);
  Serial.print(":");
  Serial.print(minuto);
  Serial.print(":");
  Serial.println(segundo);

  if(hora == 17 && minuto == 25 && segundo == 30){
  moverServo();
  }else if(hora == 17 && minuto == 26 && segundo == 0){
    moverServo();
  }else if(hora == 17 && minuto == 26 && segundo == 30){
    moverServo();
  }
}

// Función para mover el servo motor
void moverServo(){
    servo.write(180);
    delay(1000);
    servo.write(90);
}

// Función que almacena el comportamiento del buzzer
void buzzer(){
  int hora = (int) hour(fecha);
  int minuto = (int) minute(fecha);
  int segundo = (int) second(fecha);
// Imprimimos la hora
  Serial.print("Hora: ");
  Serial.print(hora);
  Serial.print(":");
  Serial.print(minuto);
  Serial.print(":");
  Serial.println(segundo);

  if(hora == 18 && minuto == 1 && segundo == 0){
    reproducirCancion();
  }else if(hora == 17 && minuto == 26 && segundo == 0){
    reproducirCancion();
  }else if(hora == 17 && minuto == 26 && segundo == 30){
    reproducirCancion();
  }
}

// Función que reproduce piratas del caribe
void reproducirCancion(){
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
// Función que almacena el comportamiento del sensor infrarrojo
void sensorInfrarojo(){
  int deteccion = 0; // Variable para detectar lo que está ocurriendo con el sensor 
  deteccion = digitalRead(pinInfrarojo);
  if (deteccion == HIGH) {
      Serial.println("OBSTACULO!!!");
  }
}

// Función que almacena el comportamiento del sensor de presión
void sensorPresion(){
  int resRead = analogRead(A0); // Leemos el valor del sensor
  int valorGramos = map(resRead,0,1023,30,10000); // Función para cambiar el rango del valor analógico a gramos. 
  Serial.print("Sensor de presion: ");
  Serial.println(resRead); // Imprimos en la terminal que valores aparecen
    Serial.print("Valor en gramos: ");
  Serial.println(valorGramos); // Imprimos en la terminal que valores aparecen
}