package com.example.jafesmartfeeder;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.jafesmartfeeder.ui.intro.IntroFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.jafesmartfeeder.databinding.ActivityMainMenuBinding;

public class MainMenu extends AppCompatActivity {

    private com.example.jafesmartfeeder.databinding.ActivityMainMenuBinding binding;
    private static String idUser;
    private static String idFeeder;
    private static String ip = "192.168.1.86";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainMenuBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_portion, R.id.navigation_profile)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main2);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);

        SharedPreferences preferences = getSharedPreferences("session", Context.MODE_PRIVATE);
        idUser = preferences.getString("user_id", "No almacenado");
        idFeeder = preferences.getString("feeder_id", "No encontrado");
    }

    public static String getIdUser () {
        return idUser;
    }

    public static String getIP () {
        return ip;
    }

    public static String getIdFeeder () {
        return idFeeder;
    }

}