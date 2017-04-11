package com.theironyard.entities;

import com.theironyard.utilities.PasswordStorage;

import javax.persistence.*;

@Entity
@Table(name = "chefs")
public class Chef {
    @Id
    @GeneratedValue
    int id;

    @Column(nullable = false, unique = true)
    private String userName;

    @Column(nullable = false)
    private String password;

    public Chef() {
    }

    public Chef(String userName, String password) throws PasswordStorage.CannotPerformOperationException {
        this.userName = userName;
        setPassword(password);
    }

    public String getName() {
        return userName;
    }
    private String getPasswordHash(){
        return password;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) throws PasswordStorage.CannotPerformOperationException {
        this.password = PasswordStorage.createHash(password);
    }

    public boolean verifyPassword(String password) throws PasswordStorage.InvalidHashException, PasswordStorage.CannotPerformOperationException {
        return PasswordStorage.verifyPassword(password, getPasswordHash());
    }


}