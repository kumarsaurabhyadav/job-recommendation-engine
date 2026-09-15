package com.example.jobrecommendation.service;

import java.util.Comparator;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.jobrecommendation.dto.RecommendationResult;
import com.example.jobrecommendation.entity.Candidate;
import com.example.jobrecommendation.entity.Job;
import com.example.jobrecommendation.repository.CandidateRepository;
import com.example.jobrecommendation.repository.JobRepository;

@Service
public class RecommendationService {

    private final CandidateRepository candidateRepository;
    private final JobRepository jobRepository;
    private final ScoringService scoringService;

    public RecommendationService(
            CandidateRepository candidateRepository,
            JobRepository jobRepository,
            ScoringService scoringService) {

        this.candidateRepository = candidateRepository;
        this.jobRepository = jobRepository;
        this.scoringService = scoringService;
    }

    public List<RecommendationResult> getRecommendations(
            Long candidateId,
            int limit) {

        Candidate candidate = candidateRepository.findById(candidateId)
                .orElseThrow(() ->
                        new RuntimeException("Candidate not found"));

               List<Job> jobs = jobRepository.findAll();

               return jobs.stream()
                .map(job -> new RecommendationResult(
                        job,
                        scoringService.calculateTotalScore(candidate, job)
                ))
        .sorted(Comparator.comparingDouble(
                RecommendationResult::getScore
        ).reversed())
        .limit(limit)
        .toList();
    }
}