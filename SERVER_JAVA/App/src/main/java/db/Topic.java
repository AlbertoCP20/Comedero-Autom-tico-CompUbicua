package db;

/**
 *
 * @author mfran
 */
public class Topic {
    
    private String idFeeder;
    private int idSensor;
    private float valor;
    
    public Topic(String idFeeder, int idSensor, int valor) {
        this.idFeeder = idFeeder;
        this.idSensor = idSensor;
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

    public void setIdFeeder(String idFeeder) {
        this.idFeeder = idFeeder;
    }

    public void setIdSensor(int idSensor) {
        this.idSensor = idSensor;
    }

    public void setValor(float valor) {
        this.valor = valor;
    }
    
}
