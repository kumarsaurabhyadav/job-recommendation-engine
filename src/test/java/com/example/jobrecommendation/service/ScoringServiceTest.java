package com.example.jobrecommendation.service;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import com.example.jobrecommendation.entity.Candidate;
import com.example.jobrecommendation.entity.Job;
import com.example.jobrecommendation.entity.JobSkill;
import com.example.jobrecommendation.enums.SkillType;

class ScoringServiceTest {

    private final ScoringService scoringService = new ScoringService();

    private JobSkill createJobSkill(String skill, SkillType type) {
    JobSkill jobSkill = new JobSkill();
    jobSkill.setSkill(skill);
    jobSkill.setType(type);
    return jobSkill;
}

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

    @Test
void shouldGiveFullSalaryScoreWhenExpectedSalaryIsWithinJobRange() {
    Candidate candidate = new Candidate();
    candidate.setExpectedSalary(700000);

    Job job = new Job();
    job.setSalaryMin(600000);
    job.setSalaryMax(1000000);

    double score = scoringService.calculateSalaryScore(candidate, job);

    assertEquals(15.0, score);
}

@Test
void shouldGiveZeroSalaryScoreWhenJobMaximumSalaryIsBelowExpectation() {
    Candidate candidate = new Candidate();
    candidate.setExpectedSalary(1200000);

    Job job = new Job();
    job.setSalaryMin(600000);
    job.setSalaryMax(1000000);

    double score = scoringService.calculateSalaryScore(candidate, job);

    assertEquals(0.0, score);
}

@Test
void shouldGiveFullSalaryScoreWhenJobMinimumSalaryIsAboveExpectation() {
    Candidate candidate = new Candidate();
    candidate.setExpectedSalary(500000);

    Job job = new Job();
    job.setSalaryMin(600000);
    job.setSalaryMax(1000000);

    double score = scoringService.calculateSalaryScore(candidate, job);

    assertEquals(15.0, score);
}

@Test
void shouldGiveZeroSalaryScoreForInvalidSalaryRange() {
    Candidate candidate = new Candidate();
    candidate.setExpectedSalary(700000);

    Job job = new Job();
    job.setSalaryMin(1000000);
    job.setSalaryMax(600000);

    double score = scoringService.calculateSalaryScore(candidate, job);

    assertEquals(0.0, score);
}

@Test
void shouldGiveZeroSkillScoreWhenMustHaveSkillIsMissing() {
    Candidate candidate = new Candidate();
    candidate.setSkills(List.of("Java"));

    Job job = new Job();
    job.setRequiredSkills(List.of(
            createJobSkill("Java", SkillType.MUST_HAVE),
            createJobSkill("Spring Boot", SkillType.MUST_HAVE)
    ));

    double score = scoringService.calculateSkillScore(candidate, job);

    assertEquals(0.0, score);
}

@Test
void shouldGivePartialSkillScoreWhenSomeNiceToHaveSkillsAreMissing() {
    Candidate candidate = new Candidate();
    candidate.setSkills(List.of("Java", "Spring Boot"));

    Job job = new Job();
    job.setRequiredSkills(List.of(
            createJobSkill("Java", SkillType.MUST_HAVE),
            createJobSkill("Spring Boot", SkillType.MUST_HAVE),
            createJobSkill("PostgreSQL", SkillType.NICE_TO_HAVE),
            createJobSkill("Docker", SkillType.NICE_TO_HAVE)
    ));

    double score = scoringService.calculateSkillScore(candidate, job);

    assertEquals(40.0, score);
}

@Test
void shouldGiveFullSkillScoreWhenAllSkillsMatch() {
    Candidate candidate = new Candidate();
    candidate.setSkills(List.of(
            "Java",
            "Spring Boot",
            "PostgreSQL",
            "Docker"
    ));

    Job job = new Job();
    job.setRequiredSkills(List.of(
            createJobSkill("Java", SkillType.MUST_HAVE),
            createJobSkill("Spring Boot", SkillType.MUST_HAVE),
            createJobSkill("PostgreSQL", SkillType.NICE_TO_HAVE),
            createJobSkill("Docker", SkillType.NICE_TO_HAVE)
    ));

    double score = scoringService.calculateSkillScore(candidate, job);

    assertEquals(50.0, score);
}

@Test
void shouldCalculateTotalScoreOutOf100() {
    Candidate candidate = new Candidate();

    candidate.setSkills(List.of(
            "Java",
            "Spring Boot",
            "PostgreSQL",
            "Docker"
    ));

    candidate.setYearsOfExperience(2.0);
    candidate.setLocation("Delhi");
    candidate.setExpectedSalary(700000);

    Job job = new Job();

    job.setRequiredSkills(List.of(
            createJobSkill("Java", SkillType.MUST_HAVE),
            createJobSkill("Spring Boot", SkillType.MUST_HAVE),
            createJobSkill("PostgreSQL", SkillType.NICE_TO_HAVE),
            createJobSkill("Docker", SkillType.NICE_TO_HAVE)
    ));

    job.setMinYearsExperience(2.0);
    job.setLocation("Delhi");
    job.setSalaryMin(600000);
    job.setSalaryMax(1000000);
    job.setRemoteAllowed(true);

    double score = scoringService.calculateTotalScore(candidate, job);

    assertEquals(100.0, score);
}

@Test
void shouldMarkJobIneligibleWhenMustHaveSkillIsMissing() {
    Candidate candidate = new Candidate();
    candidate.setSkills(List.of("Java"));

    Job job = new Job();
    job.setRequiredSkills(List.of(
            createJobSkill("Java", SkillType.MUST_HAVE),
            createJobSkill("Spring Boot", SkillType.MUST_HAVE)
    ));

    boolean eligible = scoringService.isEligible(candidate, job);

    assertEquals(false, eligible);
}

@Test
void shouldMarkJobEligibleWhenAllMustHaveSkillsArePresent() {
    Candidate candidate = new Candidate();
    candidate.setSkills(List.of(
            "Java",
            "Spring Boot"
    ));

    Job job = new Job();
    job.setRequiredSkills(List.of(
            createJobSkill("Java", SkillType.MUST_HAVE),
            createJobSkill("Spring Boot", SkillType.MUST_HAVE),
            createJobSkill("Docker", SkillType.NICE_TO_HAVE)
    ));

    boolean eligible = scoringService.isEligible(candidate, job);

    assertEquals(true, eligible);
}

}
