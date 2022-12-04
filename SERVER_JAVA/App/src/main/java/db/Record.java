package db;

import java.sql.Time;
import java.util.Date;

/**
 *
 * @author mfran
 */
public class Record {
    
    private Date date;
    private Time time;
    private float value;
    private int idSensor;
    
    public Record() {
        
    }

    public Date getDate() {
        return date;
    }

    public Time getTime() {
        return time;
    }

    public float getValue() {
        return value;
    }

    public int getIdSensor() {
        return idSensor;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    public void setValue(float value) {
        this.value = value;
    }

    public void setIdSensor(int idSensor) {
        this.idSensor = idSensor;
    }
    
}
