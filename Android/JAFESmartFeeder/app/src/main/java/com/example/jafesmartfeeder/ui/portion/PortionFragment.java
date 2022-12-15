package com.example.jafesmartfeeder.ui.portion;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;

import com.example.jafesmartfeeder.MainMenu;
import com.example.jafesmartfeeder.R;

import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.jafesmartfeeder.ServerConnectionThread;
import com.example.jafesmartfeeder.databinding.FragmentPortionBinding;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;

public class PortionFragment extends Fragment {

    private FragmentPortionBinding binding;
    private TableLayout tableLayout;
    private EditText hour;
    private EditText amount;
    private EditText id;
    private String[] header = {"ID", "Hora", "Gramos"};
    private ArrayList<String[]> rows = new ArrayList<>();
    private TableDynamic tableDynamic;
    private Button addButton;
    private Button deleteButton;
    private String respuestaServidor;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        PortionViewModel portionViewModel =
                new ViewModelProvider(this).get(PortionViewModel.class);

        binding = FragmentPortionBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        //Instanciamos los objetos que vamos a utilizar
        tableLayout = (TableLayout) root.findViewById(R.id.portionsTable);
        hour = (EditText) root.findViewById(R.id.inputHour);
        amount = (EditText) root.findViewById(R.id.inputAmount);
        id = (EditText) root.findViewById(R.id.inputID);
        addButton = (Button) root.findViewById(R.id.addButton);
        deleteButton = (Button) root.findViewById(R.id.deleteButton);

        //Necesitamos una tabla dinamica de la nueva clase que hemos creado y la personalizamos desde ahí
        tableDynamic = new TableDynamic(tableLayout, root.getContext().getApplicationContext());
        tableDynamic.addHeader(header);
        tableDynamic.addData(getPortions());


        //Para la funcionalidad del boton de añadir ración
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addPortion();
            }
        });

        //Para la funcionalidad de eliminar una ración
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deletePortion();
            }
        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private ArrayList<String[]> getPortions() {
        /*rows.add(new String[] {"1", "09:30h", "100g"});
        rows.add(new String[] {"2", "15:00h", "150g"});
        rows.add(new String[] {"3", "20:30h", "50g"});*/
        String servlet = "http://" + MainMenu.getIP() + ":8080/App/";
        String completeURL = servlet + "GetRationsUser?idUser=" + String.valueOf(MainMenu.getIdUser());
        System.out.println(completeURL);
        ServerConnectionThread thread = new ServerConnectionThread(this, completeURL);
        try {
            thread.join();
        } catch (InterruptedException e) {
            Toast.makeText(getContext(), "Hilo sin respuesta.", Toast.LENGTH_SHORT).show();
        }
        String[] splitAnswer = respuestaServidor.split("\n");
        String [] datosRaciones = splitAnswer[0].split(",");
        System.out.println(Arrays.toString(datosRaciones));
        ArrayList<String> idRations = new ArrayList<>();
        ArrayList<String> hoursRations = new ArrayList<>();
        ArrayList<String> cuantities = new ArrayList<>();
        for (int i = 0; i < datosRaciones.length; i++) {
            if (datosRaciones[i].contains("foodTime")) {
                String[] splitFoodTime = datosRaciones[i].split(":");
                String[] splitHour = splitFoodTime[1].split("");
                int hora = 0;
                int min = Integer.parseInt(splitFoodTime[2]);
                if (splitFoodTime[3].contains("p. m")) {
                    hora = Integer.parseInt(splitHour[1] + splitHour[2]) + 12;

                } else {
                    hora = Integer.parseInt(splitHour[1] + splitHour[2]);
                }
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                    LocalTime hRaction = LocalTime.of(hora, min);
                    hoursRations.add(hRaction.toString());
                }
            } else if (datosRaciones[i].contains("idRation")) {
                String[] splitidRation = datosRaciones[i].split(":");
                idRations.add(splitidRation[1]);
            } else if (datosRaciones[i].contains("weight")) {
                String[] splitWeight = datosRaciones[i].split(":");
                cuantities.add(splitWeight[1]);
            }
        }
        for (int i = 0; i < idRations.size(); i++) {
            rows.add(new String[] {idRations.get(i), hoursRations.get(i) + "h", cuantities.get(i) + "g"});
        }
        return rows;
    }

    //Añadimos una nueva ración
    private void addPortion() {
        String introducedHour = hour.getText().toString();
        String introducedAmount = amount.getText().toString();
        if (!introducedHour.equals("") && !introducedAmount.equals("")) {
            if (introducedHour.contains(":")) {
                String [] splitedIntroducedHour = introducedHour.split(":");
                int introdHour = Integer.parseInt(splitedIntroducedHour[0]);
                if ((0 <= introdHour) && (introdHour <= 23)) {
                    int introdMin = Integer.parseInt(splitedIntroducedHour[1]);
                    if ((0 <= introdMin) && (introdMin <= 59)) {
                        int introdAmount = Integer.parseInt(introducedAmount);
                        //Aqui habrá que cambiar para ajustar al tope que tendremos del cuenco.
                        if ((0 <= introdAmount) && (introdAmount <= 100)) {
                            ArrayList<String[]> data = tableDynamic.getData();
                            String servlet = "http://" + MainMenu.getIP() + ":8080/App/";
                            String completeURL = servlet + "PostRation?idUser=" + MainMenu.getIdUser() + "&time=" + hour.getText().toString() + ":00&weight=" + amount.getText().toString();
                            System.out.println(completeURL);
                            ServerConnectionThread thread = new ServerConnectionThread(this, completeURL);
                            try {
                                thread.join();
                            } catch (InterruptedException e) {
                                Toast.makeText(getContext(), "Hilo sin respuesta.", Toast.LENGTH_SHORT).show();
                            }
                            String[] splitAnswer = respuestaServidor.split("\n");
                            if (splitAnswer[0].equals("-1")) {
                                Toast.makeText(getActivity(), "Error al añadir ración.", Toast.LENGTH_SHORT).show();
                            } else {
                                String nextID = splitAnswer[0];
                                String[] item = new String[] {nextID, hour.getText().toString() + "h", amount.getText().toString() + "g"};
                                tableDynamic.addItems(item);
                                hour.setText("");
                                amount.setText("");
                                Toast.makeText(getActivity(), "Ración añadida.", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(getActivity(), "Porfavor, introduce una cantidad válida.", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getActivity(), "Porfavor, introduce un minuto válido.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getActivity(), "Porfavor, introduce una hora válida.", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getActivity(), "Porfavor, introduce una hora válida.", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getActivity(), "Introduce hora y cantidad.", Toast.LENGTH_SHORT).show();
        }
    }

    //Eliminamos una ración
    private void deletePortion () {
        String introducedID = id.getText().toString();
        if (!introducedID.equals("")) {
            String servlet = "http://" + MainMenu.getIP() + ":8080/App/";
            String completeURL = servlet + "DeleteRation?idUser=" + MainMenu.getIdUser() + "&idRation=" + introducedID;
            System.out.println(completeURL);
            ServerConnectionThread thread = new ServerConnectionThread(this, completeURL);
            try {
                thread.join();
            } catch (InterruptedException e) {
                Toast.makeText(getContext(), "Hilo sin respuesta.", Toast.LENGTH_SHORT).show();
            }
            String[] splitAnswer = respuestaServidor.split("\n");
            boolean removed = tableDynamic.removeRow(introducedID);
            if ((splitAnswer[0].equals("1")) &&  (removed)) {
                id.setText("");
                Toast.makeText(getActivity(), "Ración eliminada.", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getActivity(), "Introduce un ID válido.", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getActivity(), "Introduce un ID.", Toast.LENGTH_SHORT).show();
        }
    }

    public void setRespuestaServidor(String respuestaServidor) {
        this.respuestaServidor = respuestaServidor;
    }
}