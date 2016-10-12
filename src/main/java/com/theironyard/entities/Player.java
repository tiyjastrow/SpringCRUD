package com.theironyard.entities;

import javax.persistence.*;


@Entity
@Table(name = "players")
public class Player {
    @Id
    @GeneratedValue
    int id;

    @Column(nullable = false, unique = true)
    String userName;

    @Column(nullable = false)
    String password;

    public Player() {
    }

    public Player(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return userName;
    }
}
