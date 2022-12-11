package db;

/**
 *
 * @author mfran
 */
public class Pet {
    
    private int id;
    private String name;
    private char gender;
    private float weight;
    private String type;
    private boolean status;
    private int idOwner;
    
    public Pet() {
        
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
    
    public char getGender() {
        return gender;
    }

    public int getIdOwner() {
        return idOwner;
    }

    public float getWeight() {
        return weight;
    }

    public String getType() {
        return type;
    }

    public boolean isStatus() {
        return status;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setGender(char gender) {
        this.gender = gender;
    }
    
    public void setIdOwner(int idOwner) {
        this.idOwner = idOwner;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
    
}
