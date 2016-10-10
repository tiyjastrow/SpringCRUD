package com.theironyard.entities;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Created by lee on 10/10/16.
 */
@Entity
@Table(name = "events")
public class Event {
    @Id
    @GeneratedValue
    private int id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private LocalDateTime dateTime;

    @Column(nullable = false)
    private boolean editorHidden = true;

    @ManyToOne
    private User user;

    public Event() {
    }

    public Event(String name, String description, LocalDateTime dateTime, User user) {
        this.name = name;
        this.description = description;
        this.dateTime = dateTime;
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public boolean isEditorHidden() {
        return editorHidden;
    }

    public User getUser() {
        return user;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void toggleIsEditable() {
        this.editorHidden = !editorHidden;
    }
}
