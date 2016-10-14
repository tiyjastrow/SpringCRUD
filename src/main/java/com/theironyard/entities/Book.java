package com.theironyard.entities;

import javax.persistence.*;

/**
 * Created by halleyfroeb on 10/12/16.
 */
@Entity
@Table(name = "books")
public class Book implements Comparable{
    @Id
    @GeneratedValue
    int id;

    @Column(nullable = false)
    String author;


    @Column(nullable = false)
    String genre;

    @Column(nullable = false)
    String title;

    @ManyToOne
    User sender;

    @Column
    String viewable;

    public Book(String author, String genre, String title, User sender, String viewable) {
        this.author = author;
        this.genre = genre;
        this.title = title;
        this.sender = sender;
        this.viewable = viewable;
    }

    public Book() {
    }

    @Override
    public int compareTo(Object o){
        Book m = (Book) o;
        return id - m.id;
    }

    public String getViewable() {
        return viewable;
    }

    public void setViewable(String viewable) {
        this.viewable = viewable;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }
}
