#include <Time.h>
#include <TimeLib.h>
#include <Servo.h>

Servo servo;

void setup() {
  servo.attach(12,500,2500); // Inicializamos el servo, en el pin 2 500ms = 0º a 2500ms = 180º
  servo.write(90);

  // Obtenemos la fecha actual
}
 
void loop() {
  moverServo();

}


void moverServo(){
    servo.write(180);
    delay(2000);
    servo.write(90);
    delay(2000);
}