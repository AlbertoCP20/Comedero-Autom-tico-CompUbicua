int sound = 12;// definir pin
bool test = true;
void setup() {
  Serial.begin(115200);
  // Obtenemos la fecha actual

  pinMode (sound, OUTPUT); // Pin como salida
}
 
void loop() {
  
  Serial.println("Empiezo");
  if(test){
    reproducirCancion();
    test = false;
  }else{
    noTone(sound);
  }
  Serial.println("Acabo");
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