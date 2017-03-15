package com.theironyard.entities;

import com.theironyard.utilities.PasswordStorage;

import javax.persistence.*;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue
    int id;

    @Column(nullable = false, unique = true)
    String name;

    @Column(nullable = false)
    private String password;


    public User() {
    }

    public User(String name, String password) throws PasswordStorage.CannotPerformOperationException {
        this.name = name;
        setPassword(password);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPasswordHash() {
        return password;
    }

    public void setPassword(String password) throws PasswordStorage.CannotPerformOperationException {
        this.password = PasswordStorage.createHash(password);
    }

    public boolean verifyPassword(String password) throws PasswordStorage.InvalidHashException, PasswordStorage.CannotPerformOperationException {
        return PasswordStorage.verifyPassword(password, getPasswordHash());
    }

}
