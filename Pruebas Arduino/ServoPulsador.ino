#include <Servo.h>

Servo servo;

int grados = 90; // Inicializamos la posición del servo
int suma = 8; // Botón para sumar
int resta = 7; // Botón para restar

void setup(){
  servo.attach(2,500,2500); // Inicializamos el servo, en el pin 2 500ms = 0º a 2500ms = 180º
  pinMode(suma, INPUT);
  pinMode(resta, INPUT);
  servo.write(90);
 
}

void loop(){
   if (digitalRead(suma) == LOW) // Pregunto por el boton suma = 12 Presionado, se activa con estado Bajo
  {
    grados++;   // Suma grados
    
    if (grados >= 180) // Proteje el motor, para que no exceda los 180°, se puede dañar el motor
    {
      grados = 180;      
    }
  }

 
  if (digitalRead(resta) == LOW)
  {
    grados--; // Resta grados
    if (grados <= 0)// Proteje el motor, para que no descienda de los 0°, se puede dañar el motor
    {
      grados = 0;      
    }
    
  }
     servo.write(grados);
  	 delay(10);


}