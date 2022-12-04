package db;

/**
 *
 * @author mfran
 */
public class Sensor {
    
    private int idSensor;
    private String type;
    private int idFeeder;
    
    public Sensor() {
        
    }

    public int getIdSensor() {
        return idSensor;
    }

    public String getType() {
        return type;
    }

    public int getIdFeeder() {
        return idFeeder;
    }

    public void setIdSensor(int idSensor) {
        this.idSensor = idSensor;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setIdFeeder(int idFeeder) {
        this.idFeeder = idFeeder;
    }
    
}
