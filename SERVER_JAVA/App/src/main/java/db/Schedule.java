package db;

import java.sql.Time;

/**
 *
 * @author mfran
 */
public class Schedule {
    
    private int idSchedule;
    private Time foodTime;
    private float weight;
    private String idFeeder;
    
    public Schedule() {
        
    }

    public int getIdSchedule() {
        return idSchedule;
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

    public void setIdSchedule(int idSchedule) {
        this.idSchedule = idSchedule;
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
