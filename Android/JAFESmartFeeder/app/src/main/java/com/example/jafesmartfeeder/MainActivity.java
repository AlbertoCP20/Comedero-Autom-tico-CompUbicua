package com.example.jafesmartfeeder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.*;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    //Declaración de los objetos
    private HashMap<String, String> base;
    private EditText email;
    private EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Creamos y damos valor a los objetos
        base = new HashMap<>();
        base.put("elena.pena.2000@gmail.com", "elena1204");
        email = (EditText) findViewById(R.id.EmailAccess);
        password = (EditText) findViewById(R.id.PasswordAccess);
    }

    //Creación de los métodos
    public void login (View view) {
        //Comprobaremos si el correo está dado de alta y si corresponde con la contraseña
        String introducedEmail = email.getText().toString();
        String introducedPassword = password.getText().toString();

        if ((introducedEmail.equals("")) || (introducedPassword.equals(""))) {
            Toast.makeText(this, "Porfavor, introduce correo y contraseña.", Toast.LENGTH_SHORT).show();
        } else {
            if (base.containsKey(introducedEmail)) {
                if (Objects.equals(base.get(introducedEmail), introducedPassword)) {
                    //Acceder a la siguiente pantalla
                    Toast.makeText(this, "Usuario existente.", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(this, MainMenu.class);
                    startActivity(i);
                } else {
                    Toast.makeText(this, "Contraseña no válida.", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Correo no válido.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void register (View view) {
        Intent i = new Intent(this, Register.class);
        startActivity(i);
        //finish() para terminar las activitys y que no se queden en segundo plano
    }

    public void testeo (View view) {
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