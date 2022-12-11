package db;

import java.sql.Time;
import java.sql.Date;

/**
 *
 * @author mfran
 */
public class RecordJafe {
    
    private int idRecord;
    private Date date;
    private Time time;
    private float value;
    private int idRation;
    private int idSensor;
    private String idFeeder;
    
    public RecordJafe() {
        
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

    public String getIdFeeder() {
        return idFeeder;
    }

    public int getIdRecord() {
        return idRecord;
    }

    public int getIdRation() {
        return idRation;
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

    public void setIdFeeder(String idFeeder) {
        this.idFeeder = idFeeder;
    }

    public void setIdRecord(int idRecord) {
        this.idRecord = idRecord;
    }

    public void setIdRation(int idRation) {
        this.idRation = idRation;
    }
    
}
