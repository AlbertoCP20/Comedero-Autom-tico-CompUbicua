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
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.jafesmartfeeder.MainActivity;
import com.example.jafesmartfeeder.R;
import com.example.jafesmartfeeder.databinding.FragmentProfileBinding;

import java.util.Objects;
import java.util.prefs.Preferences;

public class ProfileFragment extends Fragment {

    private FragmentProfileBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ProfileViewModel profileViewModel =
                new ViewModelProvider(this).get(ProfileViewModel.class);

        binding = FragmentProfileBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        //Iniciamos los objetos que vamos a usar en el flujo del fragment
        Spinner changePetType = (Spinner) root.findViewById(R.id.change_select_pet_type);
        Button logoutButton = (Button) root.findViewById(R.id.logoutButton);

        //Reutilizamos el spinner auxiliar que ya teníamos
        String [] petTypes = {"PERRO", "GATO"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), R.layout.spinner_item_pet_type, petTypes);
        changePetType.setAdapter(adapter);

        //Botón para cerrar la sesión
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences preferences = getActivity().getSharedPreferences("session", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.remove("user_status");
                editor.apply();
                Toast.makeText(getActivity(), "Adios", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(requireActivity().getApplicationContext(), MainActivity.class);
                startActivity(i);
                getActivity().finish();
            }
        });

        //Falta el boton de guardar datos

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}