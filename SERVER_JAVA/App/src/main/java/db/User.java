package db;

/**
 *
 * @author mfran
 */
public class User {
    
    private int idUser;
    private String name;
    private String firstSurname;
    private String secondSurname;
    private String email;
    private String password;
    
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
    
    public String getEmail() {
        return email;
    }
    
    public String getPassword() {
        return password;
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
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }

}
