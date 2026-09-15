package com.example.jobrecommendation.dto;

import com.example.jobrecommendation.entity.Job;

public class RecommendationResult {

    private Job job;
    private double score;

    public RecommendationResult() {
    }

    public RecommendationResult(Job job, double score) {
        this.job = job;
        this.score = score;
    }

    public Job getJob() {
        return job;
    }

    public double getScore() {
        return score;
    }

    public void setJob(Job job) {
        this.job = job;
    }

    public void setScore(double score) {
        this.score = score;
    }
}