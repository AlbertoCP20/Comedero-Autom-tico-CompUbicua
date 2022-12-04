package db;

import java.util.Date;

/**
 *
 * @author mfran
 */
public class User {
    
    private int idUser;
    private String name;
    private String firstSurname;
    private String secondSurname;
    private Date birthday;
    private char gender;
    private String email;
    private String password;
    private byte photo;
    
    public User() {
        
    }
    
    public int getIdUSer() {
        return idUser;
    }
    
    public String getName() {
        return name;
    }

    public String getFirstSurname() {
        return firstSurname;
    }

    public String getSecondSurname() {
        return secondSurname;
    }
    
    public Date getBirthday() {
        return birthday;
    }
    
    public char getGender() {
        return gender;
    }
    
    public String getEmail() {
        return email;
    }
    
    public String getPassword() {
        return password;
    }
    
    public byte getPhoto() {
        return photo;
    }
    
    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }
    
    public void setName(String name) {
        this.name = name;
    }

    public void setFirstSurname(String firstSurname) {
        this.firstSurname = firstSurname;
    }

    public void setSecondSurname(String secondSurname) {
        this.secondSurname = secondSurname;
    }
    
    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }
    
    public void setGender(char gender) {
        this.gender = gender;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    public void setPhoto(byte photo) {
        this.photo = photo;
    }
}
