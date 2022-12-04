const int sensorInfrarojo = 0; // Definción del pin



void setup() {
  Serial.begin(9600);
  pinMode(sensorInfrarojo,INPUT); // Establecemos el pin
}

void loop() {
  int deteccion = 0; // Variable para detectar lo que está ocurriendo con el sensor 
  if (value == HIGH) {
      Serial.println("OBSTACULO!!!");
  }
  delay(1000s);

}

 