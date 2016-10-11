package com.theironyard.entities;

import com.theironyard.utilities.PasswordStorage;

import javax.persistence.*;

/**
 * Created by jakefroeb on 10/10/16.
 */
@Entity
@Table (name = "users")
public class User {
    @Id
    @GeneratedValue
    int id;

    @Column(nullable = false)
    String name;

    @Column (nullable = false)
    String password;

    public User(String name, String password) throws Exception{
        this.name = name;
        this.password = PasswordStorage.createHash(password);
    }

    public User() {
    }

    public boolean isValid(String password) throws Exception{
        if(PasswordStorage.verifyPassword(password,getPassword())){
            return true;
        }
        else{
            return false;
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
