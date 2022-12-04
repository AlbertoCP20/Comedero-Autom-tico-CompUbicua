package logic;

import com.google.gson.Gson;
import db.Schedule;
import static java.lang.Thread.sleep;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import mqtt.MQTTBroker;
import mqtt.MQTTPublisher;

/**
 *
 * @author mfran
 */
public class ThreadTime extends Thread {
    
    private int TIME = 5;
    private int TICKS = 3;
    
    public ThreadTime() {
        start();
    }
    
    public void run() {
        List<String> feeders = new ArrayList<>();
        List<Integer> counter = new ArrayList<>();
        List<String> deletes = new ArrayList<>();
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss"); 
        LocalTime dateTime; 
        long dif;
        
        MQTTBroker broker = new MQTTBroker();
        MQTTPublisher.publish(broker, "TOCK/TOCK", "Prueba");
        
        while (true) {
            
            Log.log.info("--Search Schedule--");
            ArrayList<Schedule> times = Logic.getSchedulesFromDB();
            String jsonSchedules = new Gson().toJson(times);
            Log.log.info("JSON Values=> {}", jsonSchedules);
            
            for (int i = 0; i < times.size(); i ++) {
                dateTime = LocalTime.parse(String.valueOf(times.get(i).getFoodTime()));
                dif = ChronoUnit.MINUTES.between(dateTime, LocalTime.now());
                
                String idFeeder = times.get(i).getIdFeeder();
                System.out.println("hola " + dif);
                
                if (dif <= TIME && !feeders.contains(idFeeder)) {
                    MQTTPublisher.publish(broker, "Comedero" + idFeeder + "/Actuador" + 1, String.valueOf(times.get(i).getWeight()));
                    feeders.add(idFeeder);
                    counter.add(0);
                }
            }
            
            System.out.println(feeders);
            for (int i = 0; i < feeders.size(); i++) {
                counter.set(i, counter.get(i) + 1);

                if (counter.get(i) == TICKS) {
                    MQTTPublisher.publish(broker, "Comedero" + feeders.get(i) + "/Sensor" + 1, String.valueOf(1));
                    deletes.add(feeders.get(i));
                }
            }
            System.out.println(deletes);
            for (int i = 0; i < deletes.size(); i++) {
                feeders.remove(deletes.get(i));
                counter.remove(TICKS);
            }
            System.out.println(feeders);
            
            deletes.clear();
            
            try {
                sleep(60000);
            } catch (InterruptedException ex) {
                Logger.getLogger(ThreadTime.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
