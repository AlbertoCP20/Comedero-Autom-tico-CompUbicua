package com.example.jafesmartfeeder;

import android.util.Log;
import android.widget.Toast;

import com.example.jafesmartfeeder.MainActivity;

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
    private String tag = "ServerConnectionThread";
    private String urlStr;

    public ServerConnectionThread(MainActivity activ, String url) {
        mainActivity = activ;
        urlStr = url;
        start();
        //run();
    }

    @Override
    public void run()    {
        String response;
        try {
            URL url = new URL(urlStr);
            System.out.println(url);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            //Get the information from the url
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            response = convertStreamToString(in);
            Log.d(tag, "get json: " + response);
            mainActivity.setRespuestaServidor(response);

        }
        catch (IOException e) {
            e.printStackTrace();
            response = "No";
            Log.d(tag, "get json: " + response);
            mainActivity.setRespuestaServidor(response);

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
