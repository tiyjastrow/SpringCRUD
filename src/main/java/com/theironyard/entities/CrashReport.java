package com.theironyard.entities;

import javax.persistence.*;

@Entity
@Table(name = "crashReports")
public class CrashReport {
    @Id
    @GeneratedValue
    int id;

    @Column(nullable = false)
    int interstate;

    @Column(nullable = false)
    int mileMarker;

    @Column(nullable = false)
    int numberOfVehicles;

    @Column(nullable = false)
    String description;

    @ManyToOne
    User user;

    public CrashReport(int interstate, int mileMarker, int numberOfVehicles, String description, User user) {
        this.interstate = interstate;
        this.mileMarker = mileMarker;
        this.numberOfVehicles = numberOfVehicles;
        this.description = description;
        this.user = user;
    }

    public CrashReport() {}
}
