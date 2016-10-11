package com.theironyard;

import javax.persistence.*;

/**
 * Created by Zach on 10/10/16.
 */
@Entity
@Table(name = "elements")
public class PeriodicElement {

    @Id
    @GeneratedValue
    int id;

    @Column(nullable = false, unique = true)
    String name;

    @Column(nullable = false, unique = true)
    String symbol;

    @Column(nullable = false, unique = true)
    int atomicNumber;

    @Column(nullable = false, unique = true)
    float atomicWeight;

    String author;

    @ManyToOne
    User user;

    public PeriodicElement() {
    }

    public PeriodicElement(String name, String symbol, int atomicNumber, float atomicWeight, User user) {
        this.name = name;
        this.symbol = symbol;
        this.atomicNumber = atomicNumber;
        this.atomicWeight = atomicWeight;
        this.user = user;
    }
}
