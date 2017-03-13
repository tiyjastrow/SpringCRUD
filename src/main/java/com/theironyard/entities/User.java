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
    String username;

    @Column(nullable = false)
    String password;

    public User(String username, String password) throws PasswordStorage.CannotPerformOperationException {
        this.username = username;
        setPassword(password);
    }

    public User() {}

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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
