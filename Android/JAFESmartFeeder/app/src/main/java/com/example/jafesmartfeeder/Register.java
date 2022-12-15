package com.example.jafesmartfeeder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;


import java.util.Arrays;
import java.util.HashMap;

public class Register extends AppCompatActivity {

    //Declaración de los objetos
    private EditText newName;
    private EditText newFirstSurname;
    private EditText newSecondSurname;
    private EditText newEmail;
    private EditText newPassword;
    private EditText confirmNewPassword;
    private Spinner selectPetType;
    private Spinner selectPetGender;
    private EditText petWeight;
    private EditText IDFeeder;
    private EditText newPetName;
    private Button saveButton;
    private String respuestaServidor;
    private String idUser;
    private String ip = "192.168.1.86";
    private String [] petTypes = {"Perro", "Gato"};
    private String [] petGenders = {"M", "F"};

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
        selectPetGender = (Spinner) findViewById(R.id.selectPetGender);
        petWeight = (EditText) findViewById(R.id.PetWeight);
        IDFeeder = (EditText) findViewById(R.id.IDFeeder);
        newPetName = (EditText) findViewById(R.id.newPetName);
        saveButton = (Button) findViewById(R.id.SaveButton);


        //Llamamos a los datos del spinner auxiliar que hemos creado y lo reutilizamos para el nuevo spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.spinner_item_pet_type, petTypes);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this, R.layout.spinner_item_pet_type, petGenders);
        selectPetType.setAdapter(adapter);
        selectPetGender.setAdapter(adapter2);

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
        String introducedIdFeeder = IDFeeder.getText().toString();

        if ((newName.getText().toString().equals("")) ||
                (newFirstSurname.getText().toString().equals("")) ||
                (newSecondSurname.getText().toString().equals("")) ||
                (newEmail.getText().toString().equals("")) ||
                (newPassword.getText().toString().equals("")) ||
                (confirmNewPassword.getText().toString().equals("")) ||
                (newPetName.getText().toString().equals("")) ||
                (petWeight.getText().toString().equals("")) ||
                (IDFeeder.getText().toString().equals(""))) {
            Toast.makeText(this, "Debes rellenar todos los campos.", Toast.LENGTH_SHORT).show();
        } else {
            //Nos conectamos al servlet y evaluamos la respuesta obtenida
            String servlet = "http://" + ip + ":8080/App/";
            String completeURL = servlet+"Login?email=" + introducedEmail + "&password=" + introducedPassword;
            ServerConnectionThread thread = new ServerConnectionThread(this, completeURL);
            try {
                thread.join();
            } catch (InterruptedException e) {
                Toast.makeText(this, "Hilo sin respuesta.", Toast.LENGTH_SHORT).show();
            }
            String[] splitAnswer = respuestaServidor.split("\n");
            if (!splitAnswer[0].equals("No")) {
                if (Integer.parseInt(splitAnswer[0]) > 0 || splitAnswer[0].equals("-2")) {
                    Toast.makeText(this, "Usuario existente.", Toast.LENGTH_SHORT).show();
                } else {
                    if (introducedEmail.contains("@")) {
                        if (introducedPassword.equals(confirmIntroducedPassword)) {
                            //Compruebo que el comedero está dado de alta
                            completeURL = servlet+"ValidateFeeder?idFeeder=" + introducedIdFeeder;
                            System.out.println(completeURL);
                            thread = new ServerConnectionThread(this, completeURL);
                            try {
                                thread.join();
                            } catch (InterruptedException e) {
                                Toast.makeText(this, "Hilo sin respuesta.", Toast.LENGTH_SHORT).show();
                            }
                            splitAnswer = respuestaServidor.split("\n");
                            boolean res;
                            try {
                                Integer.parseInt(splitAnswer[0]);
                                res = true;
                            } catch (NumberFormatException e){
                                res = false;
                            }
                            if (res) {
                                if (Integer.parseInt(splitAnswer[0]) > 0) {
                                    //Comedero ya siendo utilizado
                                    Toast.makeText(this, "El id del comedero ya pertenece a otro usuario.", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(this, "El id del comedero introducido no existe.", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                SharedPreferences preferences = getSharedPreferences("session", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = preferences.edit();
                                editor.putString("feeder_id", splitAnswer[0].replaceAll("\"",""));
                                editor.commit();
                                //Puede darse de alta al usuario
                                String introducedName = newName.getText().toString();
                                String introducedFSurname = newFirstSurname.getText().toString();
                                String introducedSSurname = newSecondSurname.getText().toString();
                                completeURL = servlet+"PostNewUser?name=" + introducedName + "&first=" + introducedFSurname + "&second=" + introducedSSurname + "&email=" + introducedEmail + "&password=" + introducedPassword + "&idFeeder=" + introducedIdFeeder;
                                System.out.println(completeURL);
                                thread = new ServerConnectionThread(this, completeURL);
                                try {
                                    thread.join();
                                } catch (InterruptedException e) {
                                    Toast.makeText(this, "Hilo sin respuesta.", Toast.LENGTH_SHORT).show();
                                }
                                splitAnswer = respuestaServidor.split("\n");
                                if (splitAnswer[0].equals("1")) {
                                    //Añado la mascota
                                    String introducedPetName = newPetName.getText().toString();
                                    String introducedPetWeight = petWeight.getText().toString();
                                    String introducedPetType = selectPetType.getSelectedItem().toString();
                                    String introducedPetGender = selectPetGender.getSelectedItem().toString();
                                    //Compruebo que ya existe el usuario creado y obtengo su id
                                    completeURL = servlet+"Login?email=" + introducedEmail + "&password=" + introducedPassword;
                                    thread = new ServerConnectionThread(this, completeURL);
                                    try {
                                        thread.join();
                                    } catch (InterruptedException e) {
                                        Toast.makeText(this, "Hilo sin respuesta.", Toast.LENGTH_SHORT).show();
                                    }
                                    splitAnswer = respuestaServidor.split("\n");
                                    idUser = splitAnswer[0];
                                    completeURL = servlet+"PostNewPet?name=" + introducedPetName + "&gender=" + introducedPetGender + "&weight=" + introducedPetWeight + "&type=" + introducedPetType + "&status=true&idUser=" + idUser;
                                    System.out.println(completeURL);
                                    thread = new ServerConnectionThread(this, completeURL);
                                    try {
                                        thread.join();
                                    } catch (InterruptedException e) {
                                        Toast.makeText(this, "Hilo sin respuesta.", Toast.LENGTH_SHORT).show();
                                    }
                                    splitAnswer = respuestaServidor.split("\n");
                                    if (splitAnswer[0].equals("1")) {
                                        preferences = getSharedPreferences("session", Context.MODE_PRIVATE);
                                        editor = preferences.edit();
                                        editor.putString("user_id", splitAnswer[0]);
                                        editor.commit();
                                        Toast.makeText(this, "Usuario dado de alta con éxito.", Toast.LENGTH_SHORT).show();
                                        Intent i = new Intent(this, MainActivity.class);
                                        startActivity(i);
                                        finish();
                                    } else {
                                        Toast.makeText(this, "Error al dar de alta, intentalo más tarde.", Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    Toast.makeText(this, "Error al dar de alta, intentalo más tarde.", Toast.LENGTH_SHORT).show();
                                }

                            }
                        } else {
                            Toast.makeText(this, "Las contraseñas no coinciden.", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(this, "Introduce un correo electrónico válido.", Toast.LENGTH_SHORT).show();
                    }
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
}