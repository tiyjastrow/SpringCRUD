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

    @Column(nullable = false, unique = true)
    String email;

    @Column(nullable = false, unique = true)
    String phone;

    @ManyToOne
    User user;

    public Contact(){}

    public Contact(String name, String email, String phone, User user) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.user = user;
    }


}
