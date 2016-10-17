package com.theironyard.entities;

import javax.persistence.*;

/**
 * Created by joshuakeough on 10/11/16.
 */
@Entity
@Table(name = "jobs")
public class Job {
    @Id
    @GeneratedValue
    private int id;

    @Column(nullable = false)
    private int salary;

    @Column(nullable = false)
    private String position;

    @Column(nullable = false)
    private int yearsOfExperienceNeeded;

    @Column(nullable = false)
    private String cityLocated;

    @ManyToOne
    private User user;

    private String show = null;

    public Job() {
    }

    public Job(int salary, String position, int yearsOfExperienceNeeded, String cityLocated, User user, String show) {
        this.salary = salary;
        this.position = position;
        this.yearsOfExperienceNeeded = yearsOfExperienceNeeded;
        this.cityLocated = cityLocated;
        this.user = user;
        this.show = show;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public int getYearsOfExperienceNeeded() {
        return yearsOfExperienceNeeded;
    }

    public void setYearsOfExperienceNeeded(int yearsOfExperienceNeeded) {
        this.yearsOfExperienceNeeded = yearsOfExperienceNeeded;
    }

    public String getCityLocated() {
        return cityLocated;
    }

    public void setCityLocated(String cityLocated) {
        this.cityLocated = cityLocated;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getShow() {
        return show;
    }

    public void setShow(String show) {
        this.show = show;
    }
}
