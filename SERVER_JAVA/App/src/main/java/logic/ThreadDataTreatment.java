package logic;

import db.Measurement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;

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
        
        ArrayList<Measurement> measurements = new ArrayList<>();
        LocalDate today;
        String formattedDate;

        
        while (true) {
            Log.log.info("--Treatment--");
            
            today = LocalDate.now();
            formattedDate = today.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM));
            
            measurements = Logic.getMeasurementsFromDB(formattedDate);
            
            /*for (int i = 0; i < idFeeders.size(); i++) {
                
            }*/
            
            
            
            
            
            
        }
    }
}
