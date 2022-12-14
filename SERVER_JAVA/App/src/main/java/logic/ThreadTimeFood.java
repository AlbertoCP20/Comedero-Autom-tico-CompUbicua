package logic;

import com.google.gson.Gson;
import db.Ration;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import mqtt.MQTTBroker;
import mqtt.MQTTPublisher;

/**
 *
 * @author mfran
 */
public class ThreadTimeFood extends Thread {
    private int TIME = 1;
    private int TICKS = 3;
    private MQTTBroker broker;
    
    public ThreadTimeFood(MQTTBroker broker) {
        this.broker = broker;
        start();
    }
    
    @Override
    public void run() {
        HashMap<String, Integer> feeders = new HashMap<>();
        ArrayList<String> idDelete = new ArrayList<>();
        Set<String> keySet;

        LocalTime dateTime; 
        long dif;
        
        MQTTPublisher.publish(broker, "TOCK/TOCK", "Prueba");
        
        while (true) {
            Log.log.info("--Search Ration--");
            ArrayList<Ration> schedules = Logic.getSchedulesFromDB();
            String jsonSchedules = new Gson().toJson(schedules);
            Log.log.info("JSON Values=> {}", jsonSchedules);
            
            for (int i = 0; i < schedules.size(); i ++) {
                dateTime = LocalTime.parse(String.valueOf(schedules.get(i).getFoodTime()));
                dif = ChronoUnit.MINUTES.between(dateTime, LocalTime.now());
                
                String idFeeder = schedules.get(i).getIdFeeder();
                System.out.println("diferencia - " + idFeeder);
                
                if (dif >= 0 && dif <= TIME && !feeders.containsKey(idFeeder)) {
                    MQTTPublisher.publish(broker, "Comedero" + idFeeder + "/Horario", String.valueOf(schedules.get(i).getIdRation()));
                    MQTTPublisher.publish(broker, "Comedero" + idFeeder + "/Racion", String.valueOf(schedules.get(i).getWeight()));
                    MQTTPublisher.publish(broker, "Comedero" + idFeeder + "/Signals", String.valueOf(2));
                    feeders.put(idFeeder, 0);
                }
            }
            
            keySet = feeders.keySet();
            for (String key : keySet) {
                int contador = feeders.get(key);
                feeders.put(key, (contador + 1));
                
                if (contador == TICKS) {
                    idDelete.add(key);
                }
            }
            
            for (int i = 0; i < idDelete.size(); i++) {
                feeders.remove(idDelete.get(i));
                MQTTPublisher.publish(broker, "Comedero" + idDelete.get(i) + "/Signals", String.valueOf(1));
            }
            System.out.println(feeders);

            idDelete.clear();
            
            try {
                sleep(60000);
            } catch (InterruptedException ex) {
                Logger.getLogger(ThreadTimeFood.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
}
