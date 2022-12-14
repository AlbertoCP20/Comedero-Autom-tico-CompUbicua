package logic;

import db.Measurement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author mfran
 */
public class ThreadDataTreatment extends Thread {
    
    public ThreadDataTreatment() {
        start();
    }
    
    @Override
    public void run() {
        
        ArrayList<Measurement> measurements;
        LocalDate today;
        DateTimeFormatter dateTimeFormatter;
        String formattedDate;
        
        while (true) {
            Log.log.info("--Treatment--");
            
            today = LocalDate.now();
            dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            formattedDate = today.format(dateTimeFormatter);
            System.out.println("HILO PROCESADO DE DATOS " + formattedDate);
            
            measurements = Logic.getMeasurementsFromDB(formattedDate);
            System.out.println("lista hilo " + measurements);
            Log.log.info(measurements);
            Measurement measurement;
            for (int i = 0; i < measurements.size(); i++) {
                measurement = measurements.get(i);
                System.out.println(measurement.getWeightEnd());
                System.out.println(measurement.getWeightIni());
                
                if (measurement.getWeightEnd() < (measurement.getWeightIni() / 2)) {
                    System.out.println("Entro");
                    if (measurement.isStatus()) {
                        Logic.updateStatusPetFromDB(measurement.getIdUser(), false);
                    }
                }
                else {
                    System.out.println("Entro abajo");
                    if (!measurement.isStatus()) {
                        Logic.updateStatusPetFromDB(measurement.getIdUser(), true);
                    }
                }
            }
            
            try {
                sleep(6000 * 5);
            } catch (InterruptedException ex) {
                Logger.getLogger(ThreadDataTreatment.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
    }
}
