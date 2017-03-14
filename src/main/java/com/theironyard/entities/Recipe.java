package com.theironyard.entities;

import javax.persistence.*;

@Entity
@Table(name = "recipes")
public class Recipe {
    @Id
    @GeneratedValue
    int id;

    @Column(nullable = false)
    String name; //name of the dish

    @Column(nullable = false)
    String type; //entree or side

    @Column(nullable = false)
    int temp; //cooking temp

    @ManyToOne
    Chef chef;


    public Recipe(String name, String type, int temp, String chef) {
    }

    public Recipe(int id, String name, String type, int temp, Chef chef) {
        this.name = name;
        this.type = type;
        this.temp = temp;
        this.chef = chef;
    }
}
