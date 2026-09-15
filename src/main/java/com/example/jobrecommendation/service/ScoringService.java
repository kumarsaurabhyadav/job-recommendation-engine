package com.example.jobrecommendation.service;

import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.example.jobrecommendation.entity.Candidate;
import com.example.jobrecommendation.entity.Job;
import com.example.jobrecommendation.entity.JobSkill;
import com.example.jobrecommendation.enums.SkillType;

@Service
public class ScoringService {

    private static final double SKILL_WEIGHT = 50.0;

    public double calculateSkillScore(Candidate candidate, Job job) {

        Set<String> candidateSkills = new HashSet<>();

        for (String skill : candidate.getSkills()) {
            candidateSkills.add(normalize(skill));
        }

        int mustHaveCount = 0;
        int mustHaveMatched = 0;

        int niceToHaveCount = 0;
        int niceToHaveMatched = 0;

        for (JobSkill jobSkill : job.getRequiredSkills()) {

            String requiredSkill = normalize(jobSkill.getSkill());

            if (jobSkill.getType() == SkillType.MUST_HAVE) {
                mustHaveCount++;

                if (candidateSkills.contains(requiredSkill)) {
                    mustHaveMatched++;
                }

            } else if (jobSkill.getType() == SkillType.NICE_TO_HAVE) {
                niceToHaveCount++;

                if (candidateSkills.contains(requiredSkill)) {
                    niceToHaveMatched++;
                }
            }
        }

        // Missing even one MUST_HAVE skill means job is not eligible.
        if (mustHaveMatched < mustHaveCount) {
            return 0.0;
        }

        double mustHaveScore = 0.0;

        if (mustHaveCount > 0) {
            mustHaveScore =
                    ((double) mustHaveMatched / mustHaveCount) * 40.0;
        }

        double niceToHaveScore = 0.0;

        if (niceToHaveCount > 0) {
            niceToHaveScore =
                    ((double) niceToHaveMatched / niceToHaveCount) * 10.0;
        }

        return mustHaveScore + niceToHaveScore;
    }

    public double calculateExperienceScore(Candidate candidate, Job job) {

        double candidateExperience = candidate.getYearsOfExperience();
        double requiredExperience = job.getMinYearsExperience();

        // No experience required by the job.
        if (requiredExperience <= 0) {
            return 20.0;
        }

        // Candidate meets or exceeds the requirement.
        if (candidateExperience >= requiredExperience) {
            return 20.0;
        }

        // Candidate has less experience, so reduce the score proportionally.
        double score =
                (candidateExperience / requiredExperience) * 20.0;

        return Math.max(0.0, score);
    }

    public double calculateLocationScore(Candidate candidate, Job job) {

    String candidateLocation = candidate.getLocation();
    String jobLocation = job.getLocation();

    // Exact location match gets the highest score.
    if (candidateLocation != null
            && jobLocation != null
            && candidateLocation.trim().equalsIgnoreCase(jobLocation.trim())) {
        return 15.0;
    }

    // If location does not match but remote work is allowed.
    if (job.isRemoteAllowed()) {
        return 10.0;
    }

    // Location mismatch and remote is not allowed.
    return 0.0;
}

    private String normalize(String skill) {
        return skill.trim().toLowerCase(Locale.ROOT);
    }
}