#include <Time.h>
#include <TimeLib.h>
#include <Servo.h>

Servo servo;

void setup() {
  setTime(17, 17, 0, 9, 11, 2022);
  servo.attach(16,500,2500); // Inicializamos el servo, en el pin 2 500ms = 0º a 2500ms = 180º
  servo.write(90);

  // Obtenemos la fecha actual
  fecha = now();
}
 
void loop() {
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


void moverServo(){
    servo.write(180);
    delay(1000);
    servo.write(90)
}