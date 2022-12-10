package com.example.jafesmartfeeder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.HashMap;

public class Register extends AppCompatActivity {

    //Declaración de los objetos
    private EditText newName;
    private EditText newFirstSurname;
    private EditText newSecondSurname;
    private EditText newEmail;
    private EditText newPassword;
    private EditText confirmNewPassword;
    private Spinner selectPetType; //Necesitaria otro objeto donde almacenar el getSelectedItem para poder pasarselo a la base de datos
    private EditText petWeight;
    private EditText IDFeeder;
    private HashMap<String, String> base;
    private Button saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //Creamos y damos valor a los objetos
        newName = (EditText) findViewById(R.id.NewName);
        newFirstSurname = (EditText) findViewById(R.id.NewFirstSurname);
        newSecondSurname = (EditText) findViewById(R.id.NewSecondSurname);
        newEmail = (EditText) findViewById(R.id.NewEmail);
        newPassword = (EditText) findViewById(R.id.NewPassword);
        confirmNewPassword = (EditText) findViewById(R.id.ConfirmNewPassword);
        selectPetType = (Spinner) findViewById(R.id.SelectPetType);
        petWeight = (EditText) findViewById(R.id.PetWeight);
        IDFeeder = (EditText) findViewById(R.id.IDFeeder);
        saveButton = (Button) findViewById(R.id.SaveButton);

        base = new HashMap<>();
        base.put("elena.pena.2000@gmail.com", "elena1204");

        //Llamamos a los datos del spinner auxiliar que hemos creado
        String [] petTypes = {"PERRO", "GATO"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.spinner_item_pet_type, petTypes);
        selectPetType.setAdapter(adapter);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveNewUser();
            }
        });

    }

    //Para registrar un nuevo usuario
    public void saveNewUser () {
        //Comprobaremos que efectivamente el usuario no está ya dado de alta en el sistema
        String introducedEmail = newEmail.getText().toString();
        String introducedPassword = newPassword.getText().toString();
        String confirmIntroducedPassword = confirmNewPassword.getText().toString();

        if ((newName.getText().toString().equals("")) ||
                (newFirstSurname.getText().toString().equals("")) ||
                (newSecondSurname.getText().toString().equals("")) ||
                (newEmail.getText().toString().equals("")) ||
                (newPassword.getText().toString().equals("")) ||
                (confirmNewPassword.getText().toString().equals("")) ||
                (petWeight.getText().toString().equals("")) ||
                (IDFeeder.getText().toString().equals(""))) {
            Toast.makeText(this, "Debes rellenar todos los campos.", Toast.LENGTH_SHORT).show();
        } else {
            if (base.containsKey(introducedEmail)) {
                Toast.makeText(this, "Usuario existente.", Toast.LENGTH_SHORT).show();
            } else {
                if (introducedEmail.contains("@")){
                    if (introducedPassword.equals(confirmIntroducedPassword)) {
                        //Habrá que comprobar también que el id del comedero coincide con el existente y claro introducir en la base de datos el resto de información jajajaja
                        base.put (introducedEmail, introducedPassword);
                        Toast.makeText(this, "Usuario dado de alta con éxito.", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(this, MainActivity.class);
                        startActivity(i);
                        finish();
                    } else {
                        Toast.makeText(this, "Las contraseñas no coinciden.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, "Introduce un correo electrónico válido.", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}