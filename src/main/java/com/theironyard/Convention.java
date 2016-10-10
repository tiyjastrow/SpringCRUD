package com.theironyard;

import javax.persistence.*;

/**
 * Created by jeremypitt on 10/10/16.
 */
@Entity
@Table(name = "conventions")
public class Convention {
    public Convention(String name, String location, int maxPop, String category, User user) {
        this.name = name;
        this.location = location;
        this.maxPop = maxPop;
        this.user = user;
        this.category = category;
    }

    public Convention() {
    }

    @Id
    @GeneratedValue
    int id;

    @Column(nullable = false)
    String name;

    @Column(nullable = false)
    String location;

    @Column(nullable = false)
    int maxPop;

    @ManyToOne
    User user;

    @Column(nullable = false)
    String category;
}
