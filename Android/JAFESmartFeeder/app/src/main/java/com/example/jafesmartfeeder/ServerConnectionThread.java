package com.example.jafesmartfeeder;

import android.util.Log;
import android.widget.Toast;

import com.example.jafesmartfeeder.MainActivity;
import com.example.jafesmartfeeder.ui.intro.IntroFragment;
import com.example.jafesmartfeeder.ui.portion.PortionFragment;
import com.example.jafesmartfeeder.ui.profile.ProfileFragment;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ServerConnectionThread extends Thread {
    private MainActivity mainActivity;
    private Register register;
    private IntroFragment introFragment;
    private ProfileFragment profileFragment;
    private PortionFragment portionFragment;
    private String tag = "ServerConnectionThread";
    private String urlStr;
    private String activType;

    public ServerConnectionThread(MainActivity activ, String url) {
        mainActivity = activ;
        urlStr = url;
        activType = "mainActivity";
        start();
    }

    public ServerConnectionThread(Register activ, String url) {
        register = activ;
        urlStr = url;
        activType = "register";
        start();
    }

    public ServerConnectionThread(IntroFragment frag, String url) {
        introFragment = frag;
        urlStr = url;
        activType = "introFragment";
        start();
    }

    public ServerConnectionThread(ProfileFragment frag, String url) {
        profileFragment = frag;
        urlStr = url;
        activType = "profileFragment";
        start();
    }

    public ServerConnectionThread(PortionFragment frag, String url) {
        portionFragment = frag;
        urlStr = url;
        activType = "portionFragment";
        start();
    }

    @Override
    public void run()    {
        String response;
        try {
            URL url = new URL(urlStr);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            //Get the information from the url
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            response = convertStreamToString(in);
            if (response.equals("-1")){
                response = "No";
            }
            Log.d(tag, "get json: " + response);
            setResponse(response);

        }
        catch (IOException e) {
            e.printStackTrace();
            response = "No";
            Log.d(tag, "get json: " + response);
            setResponse(response);

        }
    }
    private void setResponse(String response) {
        if (activType.equals("mainActivity")) {
            mainActivity.setRespuestaServidor(response);
        } else if (activType.equals("register")){
            register.setRespuestaServidor(response);
        } else if (activType.equals("introFragment")){
            introFragment.setRespuestaServidor(response);
        } else if (activType.equals("profileFragment")){
            profileFragment.setRespuestaServidor(response);
        } else {
            portionFragment.setRespuestaServidor(response);
        }
    }
    //Get the input stream and convert into String
    private String convertStreamToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line).append('\n');
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }


}
