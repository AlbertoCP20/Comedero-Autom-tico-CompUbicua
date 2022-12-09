package com.example.jafesmartfeeder.ui.portion;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class TableDynamic {
    private TableLayout tableLayout;
    private Context context;
    private String[] header;
    private ArrayList<String[]> data;
    private TableRow tableRow;
    private TextView txtCell;
    private int indexR;
    private int indexC;
    private boolean multicolor = false;
    private int firstColor;
    private int secondColor;

    public TableDynamic (TableLayout tableLayout, Context context) {
        this.tableLayout = tableLayout;
        this.context = context;
    }

    //Añadimos el encabezado de la tabla
    public void addHeader (String[] header) {
        this.header = header;
        createHeader();
    }

    //Añadimos los datos que contendrá la tabla
    public void addData (ArrayList<String[]>data) {
        this.data = data;
        createDataTable();
    }

    //Para poder obtener los datos de la tabla
    public ArrayList<String[]> getData () {
        return data;
    }

    //Añadir una nueva fila
    private void newRow() {
        tableRow = new TableRow(context);
    }

    //Las celdas de las columnas van a set TextView que se muestran en el layout
    private void newCell() {
        txtCell = new TextView(context);
        txtCell.setGravity(Gravity.CENTER);
        txtCell.setTextSize(30);
    }

    //Creamos en la fila inicial el encabezado con los contenidos de la tabla
    private void createHeader() {
        indexC = 0;
        newRow();
        while (indexC < header.length) {
            newCell();
            txtCell.setText(header[indexC++]);
            tableRow.addView(txtCell, newTableRowParams());
        }
        tableLayout.addView(tableRow);
    }

    //Creamos la tabla en sí
    private void createDataTable() {
        String info;
        for (indexR = 1; indexR <= data.size(); indexR++) {
            newRow();
            for (indexC = 0; indexC < header.length; indexC++) {
                newCell();
                String[] row = data.get(indexR - 1);
                info = (indexC < row.length) ? row[indexC] : "";
                txtCell.setText(info);
                tableRow.addView(txtCell, newTableRowParams());
            }
            tableLayout.addView(tableRow);
        }
        reColoring();
    }

    //Para añadir una nueva fila con nuevos valores
    public void addItems (String[] item) {
        String info;
        data.add(item);
        indexC = 0;
        newRow();
        while (indexC < header.length) {
            newCell();
            info = (indexC < item.length)?item[indexC++]:"";
            txtCell.setText(info);
            tableRow.addView(txtCell, newTableRowParams());
        }
        tableLayout.addView(tableRow, data.size());
        reColoring ();

    }

    //Para eliminar una fila indicada
    public Boolean removeRow (String id) {
        boolean removed = false;
        int coincidedIndex = -1;
        for (indexR = 0; indexR < data.size(); indexR++) {
            if (data.get(indexR)[0].equals(id)) {
                coincidedIndex = indexR;
            }
        }
        if (coincidedIndex != -1) {
            data.remove(coincidedIndex);
            tableLayout.removeAllViews();
            createHeader();
            createDataTable();
            removed = true;
        }

        return removed;
    }

    //Para colorear el fondo del encabezado
    private void backgroundHeader (int color) {
        indexC = 0;
        while (indexC < header.length) {
            txtCell = getCell(0, indexC++);
            txtCell.setBackgroundColor(color);
        }
    }

    //Para colorear el fondo de los datos
    private void backgroundData (int firstColor, int secondColor) {
        for (indexR = 0; indexR <= data.size(); indexR++) {
            multicolor = !multicolor;
            for (indexC = 0; indexC < header.length; indexC++) {
                txtCell = getCell(indexR, indexC);
                txtCell.setBackgroundColor((multicolor)?firstColor:secondColor);
            }
        }
        multicolor = false;
        this.firstColor = firstColor;
        this.secondColor = secondColor;
    }

    //Para cambiarle el color al encabezado, al resto no porque se lee bien como está
    private void textColorHeader (int color) {
        indexC = 0;
        while (indexC < header.length) {
            getCell(0, indexC++).setTextColor(color);
        }
    }

    //Para colorear la tabla cada vez que haya una modificación
    private void reColoring () {
        backgroundData (Color.rgb(251, 221, 181), Color.rgb(250, 235, 215));
        backgroundHeader(Color.rgb(251, 177, 75));
        textColorHeader(Color.rgb(74, 72, 72));
    }

    //Obtener una fila
    private TableRow getRow(int index) {
        return (TableRow) tableLayout.getChildAt(index);
    }

    //Obtener una celda
    private TextView getCell (int rowIndex, int columIndex) {
        tableRow = getRow(rowIndex);
        return (TextView) tableRow.getChildAt(columIndex);
    }

    //Ajustar los parametros dentro de una celda para que queden centrados.
    private TableRow.LayoutParams newTableRowParams() {
        TableRow.LayoutParams params = new TableRow.LayoutParams();
        params.weight = 1;
        return params;
    }
}
