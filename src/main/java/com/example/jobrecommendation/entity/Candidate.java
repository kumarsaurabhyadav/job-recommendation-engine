package com.example.jobrecommendation.entity;

import java.util.List;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "candidates")
public class Candidate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ElementCollection
    private List<String> skills;

    private double yearsOfExperience;

    private String location;

    private double expectedSalary;

    public Candidate() {
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<String> getSkills() {
        return skills;
    }

    public double getYearsOfExperience() {
        return yearsOfExperience;
    }

    public String getLocation() {
        return location;
    }

    public double getExpectedSalary() {
        return expectedSalary;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSkills(List<String> skills) {
        this.skills = skills;
    }

    public void setYearsOfExperience(double yearsOfExperience) {
        this.yearsOfExperience = yearsOfExperience;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setExpectedSalary(double expectedSalary) {
        this.expectedSalary = expectedSalary;
    }
}