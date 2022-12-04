const pinBomba = 7; // Cambiar

void setup() {
  Serial.begin(9600);
  pinMode(pinBomba,OUTPUT);

}

void loop() {
  digitalWrite(pinBomba,HIGH); // ENCENDER BOMBA
  delay(10000); // Esperamos 10 segs
  digitalWrite(pinBomba,LOW); // APAGAR BOMBA
  delay(10000);

}
