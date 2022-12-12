package com.example.jafesmartfeeder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.io.UnsupportedEncodingException;
import java.util.*;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    //Declaración de los objetos
    private EditText email;
    private EditText password;
    //private String tag = "MainActivity";
    private String respuestaServidor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Creamos y damos valor a los objetos
        email = findViewById(R.id.EmailAccess);
        password = findViewById(R.id.PasswordAccess);
        Button accessButton = findViewById(R.id.AccessButton);
        Button forgotPassButton = findViewById(R.id.ForgottenPassButton);
        Button registerButton = findViewById(R.id.RegistrarButton);

        //Para mantener la sesión abierta en el móvil
        SharedPreferences preferences = getSharedPreferences("session", Context.MODE_PRIVATE);
        if (preferences.getBoolean("user_status", false)) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent i = new Intent(getApplicationContext(), MainMenu.class);
                    startActivity(i);
                    finish();
                }
            }, 0);
        }

        //Método que se ejecuta con pulsando el boton Acceder para iniciar sesión
        accessButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });

        //Método que se ejecuta con pulsando el boton Recordar para ver cual era tu contraseña
        forgotPassButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rememberPassword();
            }
        });

        //Método que se ejecuta con pulsando el boton Registrar para registrar un nuevo usuario
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                register();
            }
        });
    }

    //Evaluación para poder iniciar sesión
    public void login () {
        //Comprobaremos si el correo está dado de alta y si corresponde con la contraseña
        String introducedEmail = email.getText().toString();
        String introducedPassword = password.getText().toString();

        if ((introducedEmail.equals("")) || (introducedPassword.equals(""))) {
            Toast.makeText(this, "Porfavor, introduce correo y contraseña.", Toast.LENGTH_SHORT).show();
        } else {
            //Nos conectamos al servlet y evaluamos la respuesta obtenida
            String servlet = "http://192.168.1.63:8080/App/";
            String completeURL = servlet+"Login?email=" + email.getText().toString() + "&password=" + password.getText().toString();
            ServerConnectionThread thread = new ServerConnectionThread(this, completeURL);
            try {
                thread.join();
            } catch (InterruptedException e) {
                Toast.makeText(this, "Hilo sin respuesta.", Toast.LENGTH_SHORT).show();
            }
            Toast.makeText(this, "Respuesta:" + respuestaServidor, Toast.LENGTH_SHORT).show();
            String[] splitAnswer = respuestaServidor.split("\n");
            if (!splitAnswer[0].equals("No")) {
                if (splitAnswer[0].equals("1")) {
                    //Acceder a la siguiente pantalla
                    Toast.makeText(this, "¡Hola de nuevo!", Toast.LENGTH_SHORT).show();
                    saveSession();

                    Intent i = new Intent(this, MainMenu.class);
                    startActivity(i);
                    finish();
                } else if (splitAnswer[0].equals("2")) {
                    Toast.makeText(this, "Contraseña no válida.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Correo no válido.", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Servidor desconectado.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    //Para obtener la respuesta del servidor
    public void setRespuestaServidor(String respuestaServidor) {
        this.respuestaServidor = respuestaServidor;
    }

    //Para mantener la sesión iniciada en el dispositivo
    public void saveSession() {
        SharedPreferences preferences = getSharedPreferences("session", Context.MODE_PRIVATE);
        boolean status = true;
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("user_status", status);
        editor.commit();
    }

    //Pasar a registrar un nuevo usuario
    public void register () {
        Intent i = new Intent(this, Register.class);
        startActivity(i);
    }

    //Para recordar la contraseña de un usuario
    public void rememberPassword () {
        String introducedEmail = email.getText().toString();
        if (introducedEmail.equals("")) {
            Toast.makeText(this, "Porfavor, introduce correo.", Toast.LENGTH_SHORT).show();
        } else {
            String servlet = "http://192.168.1.63:8080/App/";
            String completeURL = servlet+"PasswordReminder?email=" + email.getText().toString();
            ServerConnectionThread thread = new ServerConnectionThread(this, completeURL);
            try {
                thread.join();
            } catch (InterruptedException e) {
                Toast.makeText(this, "Hilo sin respuesta.", Toast.LENGTH_SHORT).show();
            }
            Toast.makeText(this, "Respuesta:" + respuestaServidor, Toast.LENGTH_SHORT).show();
            String[] splitAnswer = respuestaServidor.split("\n");
            String respuesta = splitAnswer[0];
            System.out.println(respuesta);
            if (!respuesta.equals("\"0\"")) {
                String text = "Tu contraseña es: " + splitAnswer[0];
                Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Correo no encontrado, registrate.", Toast.LENGTH_SHORT).show();
            }
        }
    }
}