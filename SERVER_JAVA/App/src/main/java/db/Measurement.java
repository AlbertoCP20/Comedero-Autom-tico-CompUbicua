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
    private int idPet;
    private boolean status;
    
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

    public int getIdPet() {
        return idPet;
    }

    public boolean isStatus() {
        return status;
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

    public void setIdPet(int idPet) {
        this.idPet = idPet;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
    
}
