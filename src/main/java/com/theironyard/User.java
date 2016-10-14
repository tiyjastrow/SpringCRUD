package com.theironyard;

import javax.persistence.*;

/**
 * Created by Zach on 10/10/16.
 */
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue
    int id;

    @Column(nullable = false, unique = true)
    String name;

    @Column(nullable = false, unique = true)
    String password;

    public User() {
    }

    public User(String name, String password) {

        this.name = name;
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
