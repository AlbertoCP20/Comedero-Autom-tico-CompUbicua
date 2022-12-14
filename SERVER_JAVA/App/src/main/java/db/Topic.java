package db;

/**
 *
 * @author mfran
 */
public class Topic {
    
    private String idFeeder;
    private String type;
    private int idSensor;
    private int idRation;
    private float valor;
    
    public Topic(String idFeeder, String type, int idSensor, int idRation, int valor) {
        this.idFeeder = idFeeder;
        this.type = type;
        this.idSensor = idSensor;
        this.idRation = idRation;
        this.valor = valor;
    }
    
    public Topic() {
        
    }

    public String getIdFeeder() {
        return idFeeder;
    }

    public int getIdSensor() {
        return idSensor;
    }

    public float getValor() {
        return valor;
    }

    public String getType() {
        return type;
    }

    public int getIdRation() {
        return idRation;
    }

    public void setIdFeeder(String idFeeder) {
        this.idFeeder = idFeeder;
    }

    public void setIdSensor(int idSensor) {
        this.idSensor = idSensor;
    }

    public void setValor(float valor) {
        this.valor = valor;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setIdRation(int idRation) {
        this.idRation = idRation;
    }
    
}
