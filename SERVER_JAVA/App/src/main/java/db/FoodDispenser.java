package db;

/**
 *
 * @author mfran
 */
public class FoodDispenser {
    
    private float percent;
    private boolean empty;
    private int idFeeder;
    private int idSensor;
    
    public FoodDispenser() {
        
    }

    public float getPercent() {
        return percent;
    }

    public boolean isEmpty() {
        return empty;
    }

    public int getIdFeeder() {
        return idFeeder;
    }

    public int getIdSensor() {
        return idSensor;
    }

    public void setPercent(float percent) {
        this.percent = percent;
    }

    public void setEmpty(boolean empty) {
        this.empty = empty;
    }

    public void setIdFeeder(int idFeeder) {
        this.idFeeder = idFeeder;
    }

    public void setIdSensor(int idSensor) {
        this.idSensor = idSensor;
    }
    
}
