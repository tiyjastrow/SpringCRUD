package com.bharris.entities;

import com.bharris.utilities.PasswordStorage;

import javax.persistence.*;

@Entity
@Table(name="users")
public class User {
    @Id
    @GeneratedValue
    int id;

    @Column(nullable = false)
    String name;

    @Column(nullable = false)
    String password;

    public User(){}

    public User(String name, String password) throws PasswordStorage.CannotPerformOperationException {
        this.name = name;
        setPassword(password);
    }

    public void setPassword(String password) throws PasswordStorage.CannotPerformOperationException {
        this.password = PasswordStorage.createHash(password);
    }

    public boolean verifyPassword(String password) throws PasswordStorage.InvalidHashException, PasswordStorage.CannotPerformOperationException {

        return PasswordStorage.verifyPassword(password, getPasswordHash());
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


}
