int resRead; // Variable para leer la resistencia del sensor de presión
const double propVF = 45/94; // Proporción del valor leído en el pin analógico respecto a la fuerza que estamos ejerciendo
void setup(){
  Serial.begin(9600);// Envío de info a través del monitor
}

void loop(){
  resRead = analogRead(A0); // Leemos el valor del sensor
  int valorGramos = map(resRead,0,1023,30,10000); // Función para cambiar el rango del valor analógico a gramos. 
  Serial.print("Sensor de presion: ");
  Serial.println(resRead); // Imprimos en la terminal que valores aparecen
    Serial.print("Valor en gramos: ");
  Serial.println(valorGramos); // Imprimos en la terminal que valores aparecen
}


/* Función para cambiar el valor analógico a gramos */
/*int valorLeidoAGramos(int valor){
  int gramos = valor*propVF;
  return gramos;
}*/