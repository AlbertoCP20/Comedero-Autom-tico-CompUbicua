package db;

import java.util.Date;

/**
 *
 * @author mfran
 */
public class Pet {
    
    private int id;
    private String name;
    private Date birthday;
    private char gender;
    private String breed;
    private float weight;
    private byte photo;
    private int idOwner;
    
    public Pet() {
        
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Date getBirthday() {
        return birthday;
    }

    public char getGender() {
        return gender;
    }

    public String getBreed() {
        return breed;
    }

    public int getIdOwner() {
        return idOwner;
    }

    public float getWeight() {
        return weight;
    }

    public byte getPhoto() {
        return photo;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public void setGender(char gender) {
        this.gender = gender;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public void setIdOwner(int idOwner) {
        this.idOwner = idOwner;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public void setPhoto(byte photo) {
        this.photo = photo;
    }
    
}
