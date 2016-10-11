package com.theironyard.entities;

import javax.persistence.*;

/**
 * Created by jakefroeb on 10/10/16.
 */
@Entity
@Table (name = "concerts")
public class Concert {

    @Id
    @GeneratedValue
    int id;

    @Column (nullable = false)
    String band;

    @Column (nullable = false)
    String venue;

    @Column (nullable = false)
    int year;

    @Column (nullable = true)
    String sameUser;

    @ManyToOne
    public
    User user;

    public Concert(String band, String venue, int year, User user) {
        this.band = band;
        this.venue = venue;
        this.year = year;
        this.user = user;
    }

    public Concert() {
    }

    public void setSameUser(String sameUser) {
        this.sameUser = sameUser;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBand() {
        return band;
    }

    public void setBand(String band) {
        this.band = band;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String isSameUser() {
        return sameUser;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
