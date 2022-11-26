package db;

import java.util.Date;

/**
 *
 * @author mfran
 */
public class User {
    
    private int id_user;
    private String name;
    private String last_name;
    private Date birthday;
    private char gender;
    private String email;
    private String password;
    private byte photo;
    
    public User() {
        
    }
    
    public int getId() {
        return id_user;
    }
    
    public String getName() {
        return name;
    }
    
    public String getLastName() {
        return last_name;
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
    
    public void setId(int id_user) {
        this.id_user = id_user;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public void setLastName(String last_name) {
        this.last_name = last_name;
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
