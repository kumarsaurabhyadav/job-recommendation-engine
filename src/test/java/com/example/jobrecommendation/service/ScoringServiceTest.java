package com.example.jobrecommendation.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import com.example.jobrecommendation.entity.Candidate;
import com.example.jobrecommendation.entity.Job;

class ScoringServiceTest {

    private final ScoringService scoringService = new ScoringService();

    @Test
    void shouldGiveFullScoreWhenCandidateMeetsExperienceRequirement() {

        Candidate candidate = new Candidate();
        candidate.setYearsOfExperience(3.0);

        Job job = new Job();
        job.setMinYearsExperience(2.0);

        double score = scoringService.calculateExperienceScore(candidate, job);

        assertEquals(20.0, score);
    }

    @Test
    void shouldReduceScoreWhenCandidateHasLessExperience() {

        Candidate candidate = new Candidate();
        candidate.setYearsOfExperience(1.0);

        Job job = new Job();
        job.setMinYearsExperience(2.0);

        double score = scoringService.calculateExperienceScore(candidate, job);

        assertEquals(10.0, score);
    }

    @Test
    void shouldGiveZeroWhenCandidateHasNoExperience() {

        Candidate candidate = new Candidate();
        candidate.setYearsOfExperience(0.0);

        Job job = new Job();
        job.setMinYearsExperience(2.0);

        double score = scoringService.calculateExperienceScore(candidate, job);

        assertEquals(0.0, score);
    }

        @Test
    void shouldGiveFullLocationScoreForExactMatch() {

        Candidate candidate = new Candidate();
        candidate.setLocation("Delhi");

        Job job = new Job();
        job.setLocation("Delhi");
        job.setRemoteAllowed(false);

        double score = scoringService.calculateLocationScore(candidate, job);

        assertEquals(15.0, score);
    }

    @Test
    void shouldGiveRemoteScoreWhenLocationDoesNotMatch() {

        Candidate candidate = new Candidate();
        candidate.setLocation("Delhi");

        Job job = new Job();
        job.setLocation("Bangalore");
        job.setRemoteAllowed(true);

        double score = scoringService.calculateLocationScore(candidate, job);

        assertEquals(10.0, score);
    }

    @Test
    void shouldGiveZeroLocationScoreWhenLocationDoesNotMatchAndRemoteIsNotAllowed() {

        Candidate candidate = new Candidate();
        candidate.setLocation("Delhi");

        Job job = new Job();
        job.setLocation("Bangalore");
        job.setRemoteAllowed(false);

        double score = scoringService.calculateLocationScore(candidate, job);

        assertEquals(0.0, score);
    }
}
