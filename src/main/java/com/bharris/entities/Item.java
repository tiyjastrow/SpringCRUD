package com.bharris.entities;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="items")
public class Item {
    @Id
    @GeneratedValue
    int id;

    @Column(nullable = false)
    String description;

    @Column(nullable = false)
    LocalDateTime localDate;


    @ManyToOne
    User user;

    public Item(){}

    public Item(String description, LocalDateTime localDate, User user) {
        this.description = description;
        this.localDate = localDate;
        this.user = user;
    }

}
