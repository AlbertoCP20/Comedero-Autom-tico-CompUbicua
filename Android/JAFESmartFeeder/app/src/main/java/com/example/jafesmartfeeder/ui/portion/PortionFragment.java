package com.example.jafesmartfeeder.ui.portion;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;

import com.example.jafesmartfeeder.R;

import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.jafesmartfeeder.databinding.FragmentPortionBinding;

import java.util.ArrayList;

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
        rows.add(new String[] {"1", "12:30h", "100g"});
        rows.add(new String[] {"2", "15:00h", "150g"});
        rows.add(new String[] {"3", "20:00h", "50g"});
        rows.add(new String[] {"4", "12:30h", "100g"});
        rows.add(new String[] {"5", "15:00h", "150g"});
        rows.add(new String[] {"6", "20:00h", "50g"});
        rows.add(new String[] {"7", "12:30h", "100g"});
        rows.add(new String[] {"8", "15:00h", "150g"});
        rows.add(new String[] {"9", "20:00h", "50g"});
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
                        if ((0 <= introdAmount) && (introdAmount <= 500)) {
                            ArrayList<String[]> data = tableDynamic.getData();
                            int nextID = Integer.parseInt(data.get(data.size()-1)[0]) + 1;
                            String[] item = new String[] {String.valueOf(nextID), hour.getText().toString() + "h", amount.getText().toString() + "g"};
                            tableDynamic.addItems(item);
                            hour.setText("");
                            amount.setText("");
                            Toast.makeText(getActivity(), "Ración añadida.", Toast.LENGTH_SHORT).show();
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
            boolean removed = tableDynamic.removeRow(introducedID);
            if (removed) {
                id.setText("");
                Toast.makeText(getActivity(), "Ración eliminada.", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getActivity(), "Introduce un ID válido.", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getActivity(), "Introduce un ID.", Toast.LENGTH_SHORT).show();
        }
    }
}