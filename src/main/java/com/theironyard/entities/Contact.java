package com.theironyard.entities;

import javax.persistence.*;

@Entity
@Table(name = "contacts")
public class Contact {
    @Id
    @GeneratedValue
    Integer id;

    @Column(nullable = false)
    String name;

    @Column(nullable = false)
    String email;

    @Column(nullable = false)
    String phone;

    @ManyToOne
    User user;

    public Contact() {
    }

    public Contact(String name, String email, String phone, User user) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.user = user;
    }
    public Contact(Integer id, String name, String email, String phone, User user) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.user = user;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
