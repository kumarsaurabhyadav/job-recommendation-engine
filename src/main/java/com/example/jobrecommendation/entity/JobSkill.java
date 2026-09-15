package com.example.jobrecommendation.entity;

import com.example.jobrecommendation.enums.SkillType;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "job_skills")
public class JobSkill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String skill;

    @Enumerated(EnumType.STRING)
    private SkillType type;

    public JobSkill() {
    }

    public Long getId() {
        return id;
    }

    public String getSkill() {
        return skill;
    }

    public SkillType getType() {
        return type;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setSkill(String skill) {
        this.skill = skill;
    }

    public void setType(SkillType type) {
        this.type = type;
    }
}