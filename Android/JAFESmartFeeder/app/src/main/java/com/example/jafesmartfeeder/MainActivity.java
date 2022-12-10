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
    private HashMap<String, String> base;
    private EditText email;
    private EditText password;
    private Button accessButton;
    private Button forgotPassButton;
    private Button registerButton;
    private MqttAndroidClient client;
    private String tag = "MainActivity";
    private String respuestaServidor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Creamos y damos valor a los objetos
        base = new HashMap<>();
        base.put("elena.pena.2000@gmail.com", "elena1204");
        email = (EditText) findViewById(R.id.EmailAccess);
        password = (EditText) findViewById(R.id.PasswordAccess);
        accessButton = (Button) findViewById(R.id.AccessButton);
        forgotPassButton = (Button) findViewById(R.id.ForgottenPassButton);
        registerButton = (Button) findViewById(R.id.RegistrarButton);

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

        accessButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                comprobarDatos();
            }
        });

        forgotPassButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rememberPassword();
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                register();
            }
        });

        //Para las conexiones
        /*String clientId = MqttClient.generateClientId();
        client = new MqttAndroidClient(this.getApplicationContext(), "tcp://192.168.1.67:1883", clientId);
        try {
            IMqttToken token = client.connect();
            token.setActionCallback(new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    //If the connection is ok
                    Log.i(tag, "MQTT connected");
                    Toast.makeText(MainActivity.this, "Conectado a MQTT.", Toast.LENGTH_SHORT).show();
                    //Suscribe the topics
                    suscripcionTopics(email.getText().toString());
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    // Something went wrong e.g. connection timeout or firewall problems
                    Log.i(tag, "Error connecting MQTT");
                    Toast.makeText(MainActivity.this, "Error en conexion mqtt2.", Toast.LENGTH_SHORT).show();
                }
            });
        } catch (MqttException e) {
            e.printStackTrace();
            Toast.makeText(this, "Error en conexion mqtt.", Toast.LENGTH_SHORT).show();
        }
        //Callback of MQTT to process the information received by MQTT
        client.setCallback(new MqttCallback() {
            @Override
            public void connectionLost(Throwable cause) {}
            @Override
            public void messageArrived(String topic, MqttMessage message) throws Exception
            {
                //New alert from the wheater station
                if(topic.contains("alert")){
                    String mqttText = new String(message.getPayload());
                    //Log.i(tag, "New Alert: + " + (new String(message.getPayload())));
                    Toast.makeText(MainActivity.this, mqttText, Toast.LENGTH_SHORT).show();

                    //Create a notification with the alert
                    //createNotificationChannel();
                    //createNotification("Alert", mqttText);
                }else{
                    //The message is about a sensor type
                    String mqttText = new String(message.getPayload());
                    //Log.i(tag, "New Message: + " + (new String(message.getPayload())));
                    Toast.makeText(MainActivity.this, mqttText, Toast.LENGTH_SHORT).show();

                    //tvstationinfo.setText(mqttText);
                }
            }
            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {}
        });*/

    }

    //Publish a new MQTT message in a topic
    private void publish(String topic, String payload){
        byte[] encodedPayload = new byte[0];
        try {
            encodedPayload = payload.getBytes("UTF-8");
            MqttMessage message = new MqttMessage(encodedPayload);
            client.publish(topic, message);
        } catch (UnsupportedEncodingException | MqttException e) {
            Log.e(tag, "Error mqtt "+ e);
        }
    }

    public void prueba (String a) {
        Toast.makeText(this, a, Toast.LENGTH_SHORT).show();
    }

    //Evaluación para poder iniciar sesión
    public void login () {
        //Comprobaremos si el correo está dado de alta y si corresponde con la contraseña
        String introducedEmail = email.getText().toString();
        String introducedPassword = password.getText().toString();

        if ((introducedEmail.equals("")) || (introducedPassword.equals(""))) {
            Toast.makeText(this, "Porfavor, introduce correo y contraseña.", Toast.LENGTH_SHORT).show();
        } else {
            if (base.containsKey(introducedEmail)) {
                if (Objects.equals(base.get(introducedEmail), introducedPassword)) {
                    //Acceder a la siguiente pantalla
                    Toast.makeText(this, "¡Hola de nuevo!", Toast.LENGTH_SHORT).show();
                    saveSession();
                    //finish();

                    Intent i = new Intent(this, MainMenu.class);
                    startActivity(i);
                    finish();
                } else {
                    Toast.makeText(this, "Contraseña no válida.", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Correo no válido.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void comprobarDatos() {
        String servlet = "http://192.168.37.1:8080/App/";
        String urlStr = servlet+"Login?email=" + email.getText().toString() + "&password=" +
                password.getText().toString();
        ServerConnectionThread thread = new ServerConnectionThread(this, urlStr);
        try {
            thread.join();
        } catch (InterruptedException e) {
        }
        if (respuestaServidor.contains("1")) {
            String[] respuesta_separada = respuestaServidor.split("\n");
            if (respuesta_separada[1].equals("true")) {
                Intent i = new Intent(MainActivity.this, MainMenu.class);
                i.putExtra("cod_sistema",respuesta_separada[0]);
                i.putExtra("usuario", email.getText().toString());

                startActivity(i);
                finish();
            } else {
                Intent i = new Intent(MainActivity.this, MainMenu.class);
                i.putExtra("cod_sistema",respuesta_separada[0]);
                i.putExtra("usuario", email.getText().toString());
                startActivity(i);
                finish();
            }

        } else {
            //mensaje de error en el usuario y contraseña introducidos
            Toast.makeText(this, "El usuario o contraseña introducidos no son correctos.", Toast.LENGTH_SHORT).show();
            //mensajeError.setText("El usuario o contraseña introducidos no son correctos.");
        }
    }

    public void setRespuestaServidor(String respuestaServidor) {
        this.respuestaServidor = respuestaServidor;
    }

    private void suscripcionTopics(String email){
        try{
            Log.i(tag, "email = " + email);
            client.subscribe(email,0);
            client.subscribe(email + "/alert",0);
            client.subscribe(email + "/sensor",0);

        }catch (MqttException e){
            e.printStackTrace();
        }
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
            if (base.containsKey(introducedEmail)) {
                String text = "Tu contraseña es: " + base.get(introducedEmail);
                Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Correo no encontrado, registrate.", Toast.LENGTH_SHORT).show();
            }
        }
    }
}