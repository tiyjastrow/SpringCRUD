package com.theironyard.entities;

import javax.persistence.*;
import java.time.LocalDateTime;


@Entity
@Table(name = "food_items")
public class FoodItem {
    @Id
    @GeneratedValue
    int id;

    @Column(nullable = false)
    String name;

    @Column(nullable = false)
    int calories;

    @Column
    LocalDateTime consumptionDateTime;

    @ManyToOne
    User user;

    public FoodItem(int id, String name, int calories, LocalDateTime consumptionDateTime, User user) {
        this.id = id;
        this.name = name;
        this.calories = calories;
        this.consumptionDateTime = consumptionDateTime;
        this.user = user;
    }


    public FoodItem(String name, int calories, LocalDateTime consumptionDateTime, User user) {
        this.name = name;
        this.calories = calories;
        this.consumptionDateTime = consumptionDateTime;
        this.user = user;
    }

    public FoodItem() {
    }
}
