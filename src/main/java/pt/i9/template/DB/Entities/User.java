package pt.i9.template.DB.Entities;

import org.apache.commons.codec.digest.DigestUtils;
import javax.persistence.*;

@Entity
@Table(name = "users")
public class User {
    @Id
    private String email;
    private String name;
    private String password;

    //Constructor
    public User(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = encodePassword(password);
    }

    private String encodePassword(String password){
        return DigestUtils.sha256Hex(password)  ;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    //No sense for getPassword method

    public void setPassword(String password) {
        this.password = encodePassword(password);
    }

    //Default constructor
    public User() {
    }
}