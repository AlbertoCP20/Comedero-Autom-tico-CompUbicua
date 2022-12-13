// Frecuencias de las notas
#define DO4  262
#define RE4  294
#define MI4  330
#define FA4  349
#define FAS4  370
#define SOL4  392
#define LA4  440
#define LAS4  466
#define SI4  494
#define DO5  523
#define RE5  587

// Pins de I/O
#define SPKPIN 4



// Duracion inicial de la nota
int noteDuration = 500;
// Delay para tocar la siguiente nota
int noteDelay = noteDuration + 50;

// Valores para shuffle de la duracion de notas
int staccato = 0;
int shuffle = 0;

// Variables para hacer transpose a las notas
int transposedNote;
int transpose = 0;

// Array de notas para We Wish You A Merry Christmas
int notes[54] = {
  DO4, FA4, FA4, SOL4, FA4, MI4, RE4, RE4, RE4, SOL4, SOL4, LA4, SOL4, FA4, MI4, DO4, DO4,
  LA4, LA4, LAS4, LA4, SOL4, FA4, RE4, DO4, DO4, RE4, SOL4, MI4, FA4, DO4, FA4, FA4, FA4,
  MI4, MI4, FA4, MI4, RE4, DO4, SOL4, LA4, SOL4, SOL4, FA4, FA4, DO5, DO4, DO4, DO4,
  RE4, SOL4, MI4, FA4
};

// Array de duracion de las notas
// Entre mas alto el valor mas corta la nota
int durations[54] = {
  2, 2, 3, 3, 3, 3, 2, 2, 2, 2, 3, 3, 3, 3, 2, 2, 2,
  2, 3, 3, 3, 3, 2, 2, 3, 3, 2, 2, 2, 1, 2, 2, 2, 2,
  1, 2, 2, 2, 2, 1, 2, 2, 3, 3, 3, 3, 2, 2, 3, 3,
  2, 2, 2, 1
};

void setup() {
  // Inicializar los pins como OUTPUT
  pinMode(4, OUTPUT);


  // Inicializar el puerto serial para imprimir el valor de la nota
  // en el monitor serial
  Serial.begin(9600);
}

void loop() {
  // hacemos un for loop de todas las notas en el array notes
  for (int i=0; i < sizeof(notes) / sizeof(int); i++) {


    // leemos el valor del potenciometro en el SPEEDPIN
    // y lo mapeamos de 800 a 100
    noteDuration = 500;

    // Dividimos el valor de noteDuration entre la duracion de la nota actual
    // y le sumamos el staccato (shuffle)
    noteDelay = (noteDuration / durations[i]) + staccato;

    // Enviamos al buzzer conectado al SPKPIN, la frecuencia de la nota
    // por una duracion de noteDuration entre la duracion de la nota actual
    // menos el valor de staccato
    tone(4, transposedNote, (noteDuration / durations[i]) - staccato);
    // detenemos el programa con la funcion delay() por la duracion
    // guardada en noteDelay (milisegundos: 1000 = 1 segundo)
    delay(noteDelay);
    
  }
  // Terminamos el for loop!

  // Esperamos para repetir todo el programa
  // la cantidad de noteDelay * 1 (milisegundos)
  delay(noteDelay * 2);
}