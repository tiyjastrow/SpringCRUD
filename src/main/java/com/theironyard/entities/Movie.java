package com.theironyard.entities;


import javax.persistence.*;

@Entity
@Table(name = "movies")
public class Movie {


    public Movie() {
        this.setName(getName());
        this.rating = rating;
        this.genre = genre;
        this.releaseYear = releaseYear;
        this.user = user;

    }

    public Movie(String name, String rating, String genre, int releaseYear, User user) {
        this.name = name;
        this.rating = rating;
        this.genre = genre;
        this.releaseYear = releaseYear;
        this.user = user;
    }

    @Id
    @GeneratedValue
    public
    int id;

    @Column(nullable = false)
    public
    String name;

    @Column(nullable = false)
    public
    String rating;

    @Column(nullable = false)
    public
    String genre;

    @Column(nullable = false)
    public
    int releaseYear;

    public String show = null;

    @ManyToOne
    User user;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getShow() {
        return show;
    }

    public void setShow(String show) {
        this.show = show;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
