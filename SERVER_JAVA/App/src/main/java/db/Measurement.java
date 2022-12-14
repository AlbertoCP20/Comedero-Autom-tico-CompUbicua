package db;

/**
 *
 * @author mfran
 */
public class Measurement {
    private float weightEnd;
    private float weightIni;
    private String idFeeder;
    private int idUser;
    
    public Measurement() {
        
    }

    public float getWeightEnd() {
        return weightEnd;
    }

    public float getWeightIni() {
        return weightIni;
    }

    public String getIdFeeder() {
        return idFeeder;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setWeightEnd(float weightEnd) {
        this.weightEnd = weightEnd;
    }

    public void setWeightIni(float weightIni) {
        this.weightIni = weightIni;
    }

    public void setIdFeeder(String idFeeder) {
        this.idFeeder = idFeeder;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }
    
}
