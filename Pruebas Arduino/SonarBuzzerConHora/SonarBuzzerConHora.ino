#include <Time.h>
#include <TimeLib.h>
int sound = 12;// definir pin
time_t fecha;

void setup() {
  setTime(18, 1, 0, 9, 11, 2022);
  Serial.begin(9600);
  // Obtenemos la fecha actual
  fecha = now();

  pinMode (sound, OUTPUT); // Pin como salida
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

  if(hora == 18 && minuto == 1 && segundo == 0){
    reproducirCancion();
  }else if(hora == 17 && minuto == 26 && segundo == 0){
    reproducirCancion();
  }else if(hora == 17 && minuto == 26 && segundo == 30){
    reproducirCancion();
  }
  delay(1000);
}

void reproducirCancion(){
  /* Programa que reproduce la melodia de "Piratas del Caribe */
  tone(sound,293.66,200);
  delay(100);
  tone(sound,293.66,100);
  delay(100);
  tone(sound,293.66,200);
  delay(200);
  tone(sound,293.66,100);
  delay(100);
  tone(sound,293.66,200);
  delay(200);
  tone(sound,293.66,100);
  delay(100);
  tone(sound,293.66,100);
  delay(100);
  tone(sound,293.66,100);
  delay(100);
  tone(sound,293.66,100);
  delay(100);
  tone(sound,293.66,200);
  delay(200);
  tone(sound,293.66,100);
  delay(100);
  tone(sound,293.66,200);
  delay(200);
  tone(sound,293.66,100);
  delay(100);
  tone(sound,293.66,200);
  delay(200);
  tone(sound,293.66,100);
  delay(100);
  tone(sound,293.66,100);
  delay(100);
  tone(sound,293.66,100);
  delay(100);
  tone(sound,293.66,100);
  delay(100);
  tone(sound,293.66,200);
  delay(200);
  tone(sound,293.66,100);
  delay(100);
  tone(sound,293.66,200);
  delay(200);
  tone(sound,293.66,100);
  delay(100);
  tone(sound,293.66,200);
  delay(200);
  tone(sound,293.66,100);
  delay(100);
  tone(sound,293.66,100);
  delay(100);
  tone(sound,440,100);
  delay(100);
  tone(sound,523.25,100);
  delay(100);
  tone(sound,587.33,100);
  delay(200);
  tone(sound,587.33,100);
  delay(200);
  tone(sound,587.33,100);
  delay(100);
  tone(sound,659.25,100);
  delay(100);
  tone(sound,698.45,100);
  delay(200);
  tone(sound,698.45,100);
  delay(200);
  tone(sound,698.45,100);
  delay(100);
  tone(sound,783.99,100);
  delay(100);
  tone(sound,659.25,100);
  delay(200);
  tone(sound,659.25,100);
  delay(200);
  tone(sound,587.33,100);
  delay(100);
  tone(sound,523.25,100);
  delay(100);
  tone(sound,523.25,100);
  delay(100);
  tone(sound,587.33,100);
  delay(300);
  tone(sound,440,100);
  delay(100);
  tone(sound,523.25,100);
  delay(100);
  tone(sound,587.33,100);
  delay(200);
  tone(sound,587.33,100);
  delay(200);
  tone(sound,587.33,100);
  delay(100);
  tone(sound,659.25,100);
  delay(100);
  tone(sound,698.45,100);
  delay(200);
  tone(sound,698.45,100);
  delay(200);
  tone(sound,698.45,100);
  delay(100);
  tone(sound,783.99,100);
  delay(100);
  tone(sound,659.25,100);
  delay(200);
  tone(sound,659.25,100);
  delay(200);
  tone(sound,587.33,100);
  delay(100);
  tone(sound,523.25,100);
  delay(100);
  tone(sound,587.33,100);
  delay(400);
  tone(sound,440,100);
  delay(100);


}