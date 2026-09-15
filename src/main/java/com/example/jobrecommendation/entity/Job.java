package com.example.jobrecommendation.entity;

import java.util.List;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "jobs")
public class Job {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @ElementCollection
    private List<String> requiredSkills;

    private double minYearsExperience;

    private String location;

    private double salaryMin;

    private double salaryMax;

    private boolean remoteAllowed;

    public Job() {
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public List<String> getRequiredSkills() {
        return requiredSkills;
    }

    public double getMinYearsExperience() {
        return minYearsExperience;
    }

    public String getLocation() {
        return location;
    }

    public double getSalaryMin() {
        return salaryMin;
    }

    public double getSalaryMax() {
        return salaryMax;
    }

    public boolean isRemoteAllowed() {
        return remoteAllowed;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setRequiredSkills(List<String> requiredSkills) {
        this.requiredSkills = requiredSkills;
    }

    public void setMinYearsExperience(double minYearsExperience) {
        this.minYearsExperience = minYearsExperience;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setSalaryMin(double salaryMin) {
        this.salaryMin = salaryMin;
    }

    public void setSalaryMax(double salaryMax) {
        this.salaryMax = salaryMax;
    }

    public void setRemoteAllowed(boolean remoteAllowed) {
        this.remoteAllowed = remoteAllowed;
    }
}