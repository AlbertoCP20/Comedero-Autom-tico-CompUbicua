package db;

import java.sql.Time;

/**
 *
 * @author mfran
 */
public class Ration {
    
    private int idRation;
    private Time foodTime;
    private float weight;
    private String idFeeder;
    
    public Ration() {
        
    }

    public int getIdRation() {
        return idRation;
    }

    public Time getFoodTime() {
        return foodTime;
    }

    public String getIdFeeder() {
        return idFeeder;
    }
    
    public float getWeight() {
        return weight;
    }

    public void setIdRation(int idSchedule) {
        this.idRation = idSchedule;
    }

    public void setFoodTime(Time foodTime) {
        this.foodTime = foodTime;
    }

    public void setIdFeeder(String idFeeder) {
        this.idFeeder = idFeeder;
    }
    
    public void setWeight(float weight) {
        this.weight = weight;
    }
    
}
