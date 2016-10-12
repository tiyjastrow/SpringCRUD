package com.theironyard.entities;

import javax.persistence.*;

/**
 * Created by joe on 11/10/2016.
 */
@Entity
@Table(name = "player_info")
public class Information implements Comparable {
    @Id
    @GeneratedValue
    int id;

    @Column(nullable = false)
    String first_name;

    @Column(nullable = false)
    String last_name;

    @Column(nullable = false)
    String country;

    @Column(nullable = false)
    String playing_style;

    @Column(nullable = false)
    int wins;

    @Column(nullable = false)
    int losses;

    @ManyToOne
    Player player;

    @Column(nullable = true)
    String display;

    public Information() {
    }

    public Information(String first_name, String last_name, String country, String playing_style, int wins, int losses, Player player, String display) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.country = country;
        this.playing_style = playing_style;
        this.wins = wins;
        this.losses = losses;
        this.player = player;
        this.display = display;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPlaying_style() {
        return playing_style;
    }

    public void setPlaying_style(String playing_style) {
        this.playing_style = playing_style;
    }

    public int getWins() {
        return wins;
    }

    public void setWins(int wins) {
        this.wins = wins;
    }

    public int getLosses() {
        return losses;
    }

    public void setLosses(int losses) {
        this.losses = losses;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public String getDisplay() {
        return display;
    }

    public void setDisplay(String display) {
        this.display = display;
    }

    @Override
    public String toString() {
        return  first_name+" "+last_name+" "+country+" "+playing_style +" "+ wins +" "+ losses;
    }

    @Override
    public int compareTo(java.lang.Object o) {
        Information m = (Information) o;
        return id - m.id;
    }
}
