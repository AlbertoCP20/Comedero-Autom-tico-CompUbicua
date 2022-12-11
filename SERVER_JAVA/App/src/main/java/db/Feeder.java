package db;

/**
 *
 * @author mfran
 */
public class Feeder {
    private String idFeeder;
    private float food;
    private float water;
    private int idUser;
    
    public Feeder() {
        
    }

    public String getIdFeeder() {
        return idFeeder;
    }

    public int getIdUser() {
        return idUser;
    }

    public float getFood() {
        return food;
    }

    public float getWater() {
        return water;
    }

    public void setIdFeeder(String idFeeder) {
        this.idFeeder = idFeeder;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public void setFood(float food) {
        this.food = food;
    }

    public void setWater(float water) {
        this.water = water;
    }
    
}
