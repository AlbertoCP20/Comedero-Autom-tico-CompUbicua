package com.example.jafesmartfeeder.ui.intro;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.jafesmartfeeder.MainMenu;
import com.example.jafesmartfeeder.R;
import com.example.jafesmartfeeder.databinding.FragmentIntroBinding;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LegendEntry;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.DataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.PercentFormatter;

import java.util.ArrayList;


public class IntroFragment extends Fragment {

    private FragmentIntroBinding binding;
    private TextView countdownText;
    private BarChart bowlStatus;
    private PieChart feederStatus;
    private String [] portions;
    private String [] capacity;
    private int [] percentPortions;
    private int [] percentCapacity;
    private int barColor;
    private int [] pieColors;
    private Button notiButton;
    private PendingIntent pendingIntent;
    private final static String CHANNEL_ID = "NOTIFICACIÓN";
    private final static int NOTIFICATION_ID = 0;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        IntroViewModel introViewModel =
                new ViewModelProvider(this).get(IntroViewModel.class);

        binding = FragmentIntroBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        countdownText = root.findViewById(R.id.text_countdown);
        TextView advise = root.findViewById(R.id.advise_text);

        //Configuramos las gráficas
        bowlStatus = root.findViewById(R.id.bowlBarChart);
        feederStatus = root.findViewById(R.id.feederPieChart);
        portions = new String[] {"Ración 1", "Ración 2", "Ración 3"};
        capacity = new String[] {"Vacío", "Lleno"};
        percentPortions = new int[] {90, 65, 20};
        percentCapacity = new int[] {30, 70};
        barColor = Color.rgb(253, 174, 58);
        pieColors = new int[] {Color.rgb(102, 102, 102), Color.rgb(253, 174, 58)};
        createCharts();

        notiButton = root.findViewById(R.id.notificationButton);
        notiButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //setPendingIntent();
                createNotificationChannel();
                createNotification();
            }
        });

        //Esto tendré que mirarlo para hacerlo de forma decente con horas
        int hora= 0;
        int minu = (2 * 60) * 1000;
        int seg = 50 * 1000;
        long valor = hora + minu + seg;
        CountDownTimer countdown = new CountDownTimer(valor, 1000) {
            @Override
            public void onTick(long l) {
                long tiempo = l/1000;
                int horas = (int) (tiempo/3600);
                int minutos = (int) (tiempo/60);
                int segundos = (int) (tiempo % 60);
                String showHours = String.format("%02d", horas);
                String showMins = String.format("%02d", minutos);
                String showSegs = String.format("%02d", segundos);
                String text = showHours + ":" + showMins + ":" + showSegs;

                countdownText.setText(text);
            }

            @Override
            public void onFinish() {
                String finalCountDown = "00:00:00";
                countdownText.setText(finalCountDown);
            }
        }.start();

        //Set tip text
        String tip = "Según la web Dog First, un perro adulto 'debe comer entre el 2-2.5% de su peso corporal por día en alimentos'.";
        advise.setText(tip);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    /*private void setPendingIntent (){
        Intent intent = new Intent(getActivity().getApplicationContext(), IntroFragment.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(getActivity());
        stackBuilder.addNextIntent(intent);
        pendingIntent = stackBuilder.getPendingIntent(1, PendingIntent.FLAG_UPDATE_CURRENT);
    }*/

    //Pâra versiones superiores a Android Oreo
    private void createNotificationChannel (){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Notificacion";
            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, name, NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager notificationManager = (NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(notificationChannel);
        }
    }

    //Para versiones anteriores a Android Oreo
    private void createNotification () {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getActivity().getApplicationContext(), CHANNEL_ID);
        builder.setSmallIcon(R.drawable.icono_notificaciones);
        builder.setContentTitle("¡Queda poca comida!");
        builder.setContentText("Por favor, revisa y rellena los compartimentos del comedero");
        builder.setColor(Color.rgb(253, 174, 58));
        builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);
        builder.setDefaults(Notification.DEFAULT_LIGHTS);
        builder.setDefaults(Notification.DEFAULT_SOUND);
        builder.setDefaults(Notification.DEFAULT_VIBRATE);

        builder.setContentIntent(pendingIntent);

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(getActivity().getApplicationContext());
        notificationManagerCompat.notify(NOTIFICATION_ID, builder.build());
    }

    //Para obtener información sobre el gráfico indicado
    private Chart getSameChart (Chart chart, String description, int background, int animateY) {
        chart.getDescription().setText(description);
        chart.setBackgroundColor(background);
        chart.animateY(animateY);
        pieLegend(chart);
        return chart;
    }

    //Obtener los datos del grafico de barras
    private ArrayList<BarEntry>getBarEntries(){
        ArrayList<BarEntry> entries = new ArrayList<>();
        for (int i = 0; i < percentPortions.length; i++) {
            entries.add(new BarEntry(i, percentPortions[i]));
        }
        return entries;
    }

    //Obtener los datos del grafico pastel
    private ArrayList<PieEntry>getPieEntries(){
        ArrayList<PieEntry> entries = new ArrayList<>();
        for (int i = 0; i < percentCapacity.length; i++) {
            entries.add(new PieEntry(percentCapacity[i]));
        }
        return entries;
    }

    //Para el grafico de barras, referencia al eje horizontal (X)
    private void axisX (XAxis axis) {
        axis.setGranularityEnabled(true);
        axis.setPosition(XAxis.XAxisPosition.BOTTOM);
        axis.setValueFormatter(new IndexAxisValueFormatter(portions));
    }

    //Para el grafico de barras, referencia al eje vertical (Y) en el lado izquierdo
    private void axisLeft (YAxis axis) {
        axis.setSpaceTop(10);
        axis.setAxisMinimum(0);
        axis.setGranularity(25);
    }
    //Para el grafico de barras, referencia al eje vertical (Y) en el lado derecho
    private void axisRight (YAxis axis) {
        axis.setEnabled(false);
    }

    //Para crear los gráficos
    public void createCharts() {
        getSameChart(bowlStatus, "", Color.TRANSPARENT, 500);
        bowlStatus.setDrawGridBackground(true);
        bowlStatus.setDrawBarShadow(false);
        bowlStatus.setData(getBarData());
        bowlStatus.invalidate();
        axisX(bowlStatus.getXAxis());
        axisLeft(bowlStatus.getAxisLeft());
        axisRight(bowlStatus.getAxisRight());
        bowlStatus.getLegend().setEnabled(false);

        getSameChart(feederStatus, "", Color.TRANSPARENT, 500);
        feederStatus.setData(getPieData());
        feederStatus.invalidate();
        feederStatus.setDrawHoleEnabled(false);
        feederStatus.getLegend().setEnabled(true);
    }

    //Para obtener los datos del gráfico
    private DataSet getData(DataSet dataSet) {
        dataSet.setColor(barColor);
        dataSet.setValueTextColor(Color.BLACK);
        dataSet.setValueTextSize(10);
        return dataSet;
    }

    //Para obtener los datos de la grafica de barras
    private BarData getBarData() {
        BarDataSet barDataSet = (BarDataSet) getData(new BarDataSet(getBarEntries(), ""));
        BarData barData = new BarData(barDataSet);
        barData.setBarWidth(0.45f);
        return barData;
    }

    //Para obtener los datos del gráfico pastel
    private PieData getPieData() {
        PieDataSet pieDataSet = (PieDataSet) getData(new PieDataSet(getPieEntries(), ""));
        pieDataSet.setSliceSpace(2);
        pieDataSet.setColors(pieColors);
        pieDataSet.setValueFormatter(new PercentFormatter());
        return new PieData(pieDataSet);
    }

    //Para añadir una leyenda al gráfico pastel
    private void pieLegend (Chart chart) {
        Legend legend = chart.getLegend();
        legend.setForm(Legend.LegendForm.CIRCLE);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.CENTER);
        ArrayList<LegendEntry> entries = new ArrayList<>();
        for (int i = 0; i < capacity.length; i++) {
            LegendEntry entry = new LegendEntry();
            entry.formColor = pieColors[i];
            entry.label = capacity[i];
            entries.add(entry);
        }
        legend.setCustom(entries);
    }
}