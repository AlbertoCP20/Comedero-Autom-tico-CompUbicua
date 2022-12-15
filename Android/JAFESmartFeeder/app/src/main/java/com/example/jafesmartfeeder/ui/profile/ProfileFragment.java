package com.example.jafesmartfeeder.ui.profile;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.jafesmartfeeder.MainActivity;
import com.example.jafesmartfeeder.MainMenu;
import com.example.jafesmartfeeder.R;
import com.example.jafesmartfeeder.ServerConnectionThread;
import com.example.jafesmartfeeder.databinding.FragmentProfileBinding;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.prefs.Preferences;

public class ProfileFragment extends Fragment {

    private FragmentProfileBinding binding;
    private String respuestaServidor;
    private TextView showName;
    private TextView showFSurname;
    private TextView showSSurname;
    private TextView showEmail;
    private TextView showPetName;
    private TextView showWeight;
    private TextView showIdFeeder;
    private TextView showPetGender;
    private TextView showPetType;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ProfileViewModel profileViewModel =
                new ViewModelProvider(this).get(ProfileViewModel.class);

        binding = FragmentProfileBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        //Iniciamos los objetos que vamos a usar en el flujo del fragment
        Button logoutButton = (Button) root.findViewById(R.id.logoutButton);
        Button deleteAccountButton = (Button) root.findViewById(R.id.deleteAccountButton);
        showName = (TextView) root.findViewById(R.id.name_text);
        showFSurname = (TextView) root.findViewById(R.id.fsurname_text);
        showSSurname = (TextView) root.findViewById(R.id.ssurname_text);
        showEmail = (TextView) root.findViewById(R.id.email_text);
        showPetName = (TextView) root.findViewById(R.id.pet_name_text);
        showWeight = (TextView) root.findViewById(R.id.pet_weight_text);
        showIdFeeder = (TextView) root.findViewById(R.id.id_feeder_text);
        showPetGender = (TextView) root.findViewById(R.id.pet_gender_text);
        showPetType = (TextView) root.findViewById(R.id.pet_type_text);

        //Pedimos todos los datos al servlet del usuario y los mostramos
        String servlet = "http://" + MainMenu.getIP() + ":8080/App/";
        String completeURL = servlet + "GetUserInfo?idUser=" + String.valueOf(MainMenu.getIdUser());
        System.out.println(completeURL);
        ServerConnectionThread thread = new ServerConnectionThread(this, completeURL);
        try {
            thread.join();
        } catch (InterruptedException e) {
            Toast.makeText(getContext(), "Hilo sin respuesta.", Toast.LENGTH_SHORT).show();
        }
        String[] splitAnswer = respuestaServidor.split("\n");
        String[] split2Answer = splitAnswer[0].split(",");
        showName.setText(split2Answer[1].split(":")[1].replaceAll("\"",""));
        showFSurname.setText(split2Answer[2].split(":")[1].replaceAll("\"",""));
        showSSurname.setText(split2Answer[3].split(":")[1].replaceAll("\"",""));
        showEmail.setText(split2Answer[4].split(":")[1].replaceAll("\"",""));
        showIdFeeder.setText(MainMenu.getIdFeeder());

        //Ahora de la mascota y los mostramos
        servlet = "http://" + MainMenu.getIP() + ":8080/App/";
        completeURL = servlet + "GetPetUser?idUser=" + String.valueOf(MainMenu.getIdUser());
        System.out.println(completeURL);
        thread = new ServerConnectionThread(this, completeURL);
        try {
            thread.join();
        } catch (InterruptedException e) {
            Toast.makeText(getContext(), "Hilo sin respuesta.", Toast.LENGTH_SHORT).show();
        }
        splitAnswer = respuestaServidor.split("\n");
        split2Answer = splitAnswer[0].split(",");
        System.out.println(Arrays.toString(split2Answer));
        showPetName.setText(split2Answer[1].split(":")[1].replaceAll("\"",""));
        showPetGender.setText(split2Answer[2].split(":")[1].replaceAll("\"",""));
        showWeight.setText(split2Answer[3].split(":")[1].replaceAll("\"",""));
        showPetType.setText(split2Answer[4].split(":")[1].replaceAll("\"",""));

        //Botón para cerrar la sesión
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logout();
            }
        });
        //Botón para eliminar cuenta
        deleteAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean del = delete();
                if (del) {
                    logout();
                } else {
                    Toast.makeText(getActivity(), "¡No se ha podido eliminar usuario!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void logout() {
        SharedPreferences preferences = getActivity().getSharedPreferences("session", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.remove("user_status");
        editor.apply();
        Toast.makeText(getActivity(), "¡Hasta pronto!", Toast.LENGTH_SHORT).show();
        Intent i = new Intent(requireActivity().getApplicationContext(), MainActivity.class);
        startActivity(i);
        getActivity().finish();
    }

    private boolean delete() {
        String servlet = "http://" + MainMenu.getIP() + ":8080/App/";
        String completeURL = servlet + "DeleteUser?idUser=" + MainMenu.getIdUser();
        System.out.println(completeURL);
        ServerConnectionThread thread = new ServerConnectionThread(this, completeURL);
        try {
            thread.join();
        } catch (InterruptedException e) {
            Toast.makeText(getContext(), "Hilo sin respuesta.", Toast.LENGTH_SHORT).show();
        }
        String[] splitAnswer = respuestaServidor.split("\n");
        if (splitAnswer[0].equals("1")) {
            return true;
        } else {
            return false;
        }
    }

    //Para obtener la respuesta del servidor
    public void setRespuestaServidor(String respuestaServidor) {
        this.respuestaServidor = respuestaServidor;
    }
}