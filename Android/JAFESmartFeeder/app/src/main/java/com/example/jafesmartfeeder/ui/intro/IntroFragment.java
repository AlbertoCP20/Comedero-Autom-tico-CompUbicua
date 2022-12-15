package com.example.jafesmartfeeder.ui.intro;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.SoundEffectConstants;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.jafesmartfeeder.MainMenu;
import com.example.jafesmartfeeder.R;
import com.example.jafesmartfeeder.ServerConnectionThread;
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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;


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
    private PendingIntent pendingIntent;
    private final static String CHANNEL_ID = "NOTIFICACIÓN";
    private final static int NOTIFICATION_ID = 0;
    private final static String CHANNEL_ID2 = "NOTIFICACIÓN2";
    private final static int NOTIFICATION_ID2 = 1;
    private String respuestaServidor;
    private boolean isActive;
    private CountDownTimer countDown;
    private String [] tips = {"Según la web Dog First, un perro adulto 'debe comer entre el 2-2.5% de su peso corporal por día en alimentos'.",
                                "Dale a tu mascota ingredientes 100% naturales, porciones personalizadas, sin conservantes ni cereales y elaborado por nutricionistas.",
                                "Un gato adulto activo y saludable suele consumir de 15 a 20 gramos de comida por cada kilo de su peso corporal.",
                                "Acostumbra a tu mascota a comer entre 2 y 5 veces al día"};


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
        portions = new String[] {"Sin Raciones"};
        capacity = new String[] {"Vacío", "Lleno"};
        percentPortions = new int[] {0};
        percentCapacity = new int[] {100, 0};
        barColor = Color.rgb(253, 174, 58);
        pieColors = new int[] {Color.rgb(102, 102, 102), Color.rgb(253, 174, 58)};
        isActive = false;

        //Consigo los datos de las raciones
        String servlet = "http://" + MainMenu.getIP() + ":8080/App/";
        String completeURL = servlet+"GetRationsUser?idUser=" + MainMenu.getIdUser();
        ServerConnectionThread thread = new ServerConnectionThread(this, completeURL);
        try {
            thread.join();
        } catch (InterruptedException e) {
            Toast.makeText(getContext(), "Hilo sin respuesta.", Toast.LENGTH_SHORT).show();
        }
        String [] splitAnswer = respuestaServidor.split("\n");
        if (!splitAnswer[0].contains("idRation")) {
            countdownText.setText("Añade raciones");
            createCharts();
        } else {
            String[] splitRations = splitAnswer[0].split(",");
            //Para la cuenta atrás
            ArrayList<LocalTime> horasRacion = new ArrayList<>();
            LocalTime local = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                local = LocalTime.now();
            }
            System.out.println(local);
            for (int i = 0; i < splitRations.length; i++) {
                if (splitRations[i].contains("foodTime")) {
                    String[] splitFoodTime = splitRations[i].split(":");
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
                        horasRacion.add(hRaction);
                    }
                }
            }
            int pos = 0;
            long hmin = 25;
            System.out.println(horasRacion);
            for (int i = 0; i < horasRacion.size(); i++) {
                long dif = 0;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                    dif = ChronoUnit.MINUTES.between(local, horasRacion.get(i));
                }
                if (dif < hmin) {
                    hmin = dif;
                    pos = i;
                }
            }

            int hora = 0;
            int minu = 0;
            int seg = 0;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                hora = local.getHour() - horasRacion.get(pos).getHour();
                minu = ((local.getMinute() - horasRacion.get(pos).getMinute()) * 60) * 1000;
                seg = (local.getSecond() - horasRacion.get(pos).getSecond()) * 100;
            }
            long valor = hora + minu + seg;
            if (!isActive) {
                activeCountDown (countDown, valor);
            }

            //Para el diagrama de barras
            int contRations = 0;
            for (int i = 0; i < splitRations.length; i++) {
                if (splitRations[i].contains("idRation")) {
                    contRations++;
                }
            }

            String racionS = "";
            for (int i = 0; i < contRations; i++) {
                racionS = "Ración " + (i+1);
                if (i == 0) {
                    portions[0] = racionS;
                } else {
                    String[] racion = new String[]{racionS};
                    String[] aux = new String[portions.length + racion.length];

                    System.arraycopy(portions, 0, aux, 0, portions.length);
                    System.arraycopy(racion, 0, aux, portions.length, racion.length);

                    portions = aux;
                }
            }
            System.out.println(Arrays.toString(portions));
            String fecha = "";
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                LocalDate date = LocalDate.now();
                int year = date.getYear();
                int month = date.getMonthValue();
                int day = date.getDayOfMonth();
                fecha = year + "-" + month + "-" + day;
            }
            for (int i = 0; i < splitRations.length; i++) {
                String idRation = "";
                if (splitRations[i].contains("idRation")) {
                    String [] splitId = splitRations[i].split(":");
                    idRation = splitId[1];
                    servlet = "http://" + MainMenu.getIP() + ":8080/App/";
                    completeURL = servlet+"GetRecordsPortion?idUser=" + MainMenu.getIdUser() + "&idPortion=" + idRation + "&date=" + fecha;
                    System.out.println(completeURL);
                    thread = new ServerConnectionThread(this, completeURL);
                    try {
                        thread.join();
                    } catch (InterruptedException e) {
                        Toast.makeText(getContext(), "Hilo sin respuesta.", Toast.LENGTH_SHORT).show();
                    }
                    splitAnswer = respuestaServidor.split("\n");
                    if (!splitAnswer[0].equals("0")) {
                        String [] splitWeight = splitRations[i+2].split(":");
                        String pesoRacion = splitWeight[1];
                        String [] splitRecord = splitAnswer[0].split(",");
                        String [] splitRecordWeight = splitRecord[4].split(":");
                        String pesoRecord = splitRecordWeight[1];
                        double perSobra = 100 - ((Double.parseDouble(pesoRacion) - Double.parseDouble(pesoRecord))*100)/Double.parseDouble(pesoRacion);
                        if (i == 0) {
                            percentPortions[0] = (int) Math.round(perSobra);
                        } else {
                            int[] sobras = new int[]{(int) Math.round(perSobra)};
                            int[] aux = new int[percentPortions.length + sobras.length];

                            System.arraycopy(percentPortions, 0, aux, 0, percentPortions.length);
                            System.arraycopy(sobras, 0, aux, percentPortions.length, sobras.length);

                            percentPortions = aux;
                        }
                        //}
                    } else {
                        double perSobra = 0;
                        //for (int s = 0; s < contRations; s++) {
                        if (i == 0) {
                            percentPortions[0] = (int) Math.round(perSobra);
                        } else {
                            int[] sobras = new int[]{(int) Math.round(perSobra)};
                            int[] aux = new int[percentPortions.length + sobras.length];

                            System.arraycopy(percentPortions, 0, aux, 0, percentPortions.length);
                            System.arraycopy(sobras, 0, aux, percentPortions.length, sobras.length);

                            percentPortions = aux;
                        }
                    }
                }
            }

            //Para el porcentaje de lleno/vacío
            servlet = "http://" + MainMenu.getIP() + ":8080/App/";
            completeURL = servlet+"GetPercentageFood?idUser=" + MainMenu.getIdUser();
            System.out.println(completeURL);
            thread = new ServerConnectionThread(this, completeURL);
            try {
                thread.join();
            } catch (InterruptedException e) {
                Toast.makeText(getContext(), "Hilo sin respuesta.", Toast.LENGTH_SHORT).show();
            }
            splitAnswer = respuestaServidor.split("\n");
            System.out.println(splitAnswer[0]);
            double percent = Double.parseDouble(splitAnswer[0]);
            //Redondeamos
            int porcentajeLleno = (int) Math.round(percent);
            int porcentajeVacio = 100 - porcentajeLleno;
            System.out.println(Arrays.toString(percentCapacity));
            percentCapacity[0] = porcentajeVacio;
            percentCapacity[1] = porcentajeLleno;
            System.out.println(Arrays.toString(percentCapacity));
            createCharts();
            if (porcentajeLleno <= 30) {
                createNotificationChannel();
                createNotification();
            }
        }

        //Set tip text
        servlet = "http://" + MainMenu.getIP() + ":8080/App/";
        completeURL = servlet+"GetStatusPet?idUser=" + MainMenu.getIdUser();
        System.out.println(completeURL);
        thread = new ServerConnectionThread(this, completeURL);
        try {
            thread.join();
        } catch (InterruptedException e) {
            Toast.makeText(getContext(), "Hilo sin respuesta.", Toast.LENGTH_SHORT).show();
        }
        splitAnswer = respuestaServidor.split("\n");
        System.out.println(splitAnswer[0]);

        if (splitAnswer[0].equals("1")) {
            int tipRandom = (int) (Math.random() * (tips.length));
            advise.setText(tips[tipRandom]);
        } else {
            String tip = "Tu mascota no esta comiendo correctamente, por favor vigila, cambia la comida o las raciones o llevalo al veterinario.";
            advise.setText(tip);
        }

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void activeCountDown(CountDownTimer countDown, long valor){
        countDown = new CountDownTimer(valor, 1000) {
            @Override
            public void onTick(long l) {
                long tiempo = l/1000;
                int horas = (int) (tiempo/3600);
                int minutos = (int) (tiempo/60);
                int segundos = (int) (tiempo % 60);
                String show = String.format("%02d:%02d:%02d", horas, minutos, segundos);
                countdownText.setText(show);
            }

            @Override
            public void onFinish() {
                isActive = false;
                String finalCountDown = "00:00:00";
                countdownText.setText(finalCountDown);
                createNotificationChannel2();
                createNotification2();
            }
        }.start();
        isActive = true;
    }

    /*private void setPendingIntent (){
        Intent intent = new Intent(getActivity().getApplicationContext(), IntroFragment.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(getActivity());
        stackBuilder.addNextIntent(intent);
        pendingIntent = stackBuilder.getPendingIntent(1, PendingIntent.FLAG_UPDATE_CURRENT);
    }*/

    //Para versiones superiores a Android Oreo
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
        builder.setContentText("Por favor, revisa y rellena los compartimentos del comedero.");
        builder.setColor(Color.rgb(253, 174, 58));
        builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);
        builder.setDefaults(Notification.DEFAULT_LIGHTS);
        builder.setDefaults(Notification.DEFAULT_SOUND);
        builder.setDefaults(Notification.DEFAULT_VIBRATE);

        builder.setContentIntent(pendingIntent);

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(getActivity().getApplicationContext());
        notificationManagerCompat.notify(NOTIFICATION_ID, builder.build());
    }

    //Para versiones superiores a Android Oreo
    private void createNotificationChannel2 (){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Notificacion2";
            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID2, name, NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager notificationManager = (NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(notificationChannel);
        }
    }

    //Para versiones anteriores a Android Oreo
    private void createNotification2 () {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getActivity().getApplicationContext(), CHANNEL_ID2);
        builder.setSmallIcon(R.drawable.icono_notificaciones);
        builder.setContentTitle("¡Comida dispensada!");
        builder.setContentText("Ya puede ir tu mascota a comer.");
        builder.setColor(Color.rgb(253, 174, 58));
        builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);
        builder.setDefaults(Notification.DEFAULT_LIGHTS);
        builder.setDefaults(Notification.DEFAULT_SOUND);
        builder.setDefaults(Notification.DEFAULT_VIBRATE);

        builder.setContentIntent(pendingIntent);

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(getActivity().getApplicationContext());
        notificationManagerCompat.notify(NOTIFICATION_ID2, builder.build());
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

    //Para obtener la respuesta del servidor
    public void setRespuestaServidor(String respuestaServidor) {
        this.respuestaServidor = respuestaServidor;
    }
}