package com.example.jobrecommendation.service;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.example.jobrecommendation.dto.RecommendationResult;
import com.example.jobrecommendation.entity.Candidate;
import com.example.jobrecommendation.entity.Job;
import com.example.jobrecommendation.repository.CandidateRepository;
import com.example.jobrecommendation.repository.JobRepository;

class RecommendationServiceTest {

    @Test
    void shouldReturnJobsSortedByHighestScore() {

        CandidateRepository candidateRepository =
                Mockito.mock(CandidateRepository.class);

        JobRepository jobRepository =
                Mockito.mock(JobRepository.class);

        ScoringService scoringService =
                Mockito.mock(ScoringService.class);

        Candidate candidate = new Candidate();
        candidate.setId(1L);
        candidate.setName("Rahul");

        Job firstJob = new Job();
        firstJob.setId(1L);
        firstJob.setTitle("Java Developer");

        Job secondJob = new Job();
        secondJob.setId(2L);
        secondJob.setTitle("Backend Developer");

        Mockito.when(candidateRepository.findById(1L))
                .thenReturn(java.util.Optional.of(candidate));

        Mockito.when(jobRepository.findAll())
                .thenReturn(List.of(firstJob, secondJob));

        Mockito.when(
                scoringService.calculateTotalScore(candidate, firstJob)
        ).thenReturn(70.0);

        Mockito.when(
                scoringService.calculateTotalScore(candidate, secondJob)
        ).thenReturn(90.0);

        RecommendationService recommendationService =
                new RecommendationService(
                        candidateRepository,
                        jobRepository,
                        scoringService
                );

        List<RecommendationResult> results =
                recommendationService.getRecommendations(1L, 10);

        assertEquals(2, results.size());

        assertEquals(
                "Backend Developer",
                results.get(0).getJob().getTitle()
        );

        assertEquals(90.0, results.get(0).getScore());

        assertEquals(
                "Java Developer",
                results.get(1).getJob().getTitle()
        );

        assertEquals(70.0, results.get(1).getScore());
    }

    @Test
    void shouldRespectRecommendationLimit() {

        CandidateRepository candidateRepository =
                Mockito.mock(CandidateRepository.class);

        JobRepository jobRepository =
                Mockito.mock(JobRepository.class);

        ScoringService scoringService =
                Mockito.mock(ScoringService.class);

        Candidate candidate = new Candidate();
        candidate.setId(1L);

        Job job1 = new Job();
        job1.setId(1L);
        job1.setTitle("Job 1");

        Job job2 = new Job();
        job2.setId(2L);
        job2.setTitle("Job 2");

        Job job3 = new Job();
        job3.setId(3L);
        job3.setTitle("Job 3");

        Mockito.when(candidateRepository.findById(1L))
                .thenReturn(java.util.Optional.of(candidate));

        Mockito.when(jobRepository.findAll())
                .thenReturn(List.of(job1, job2, job3));

        Mockito.when(
                scoringService.calculateTotalScore(candidate, job1)
        ).thenReturn(90.0);

        Mockito.when(
                scoringService.calculateTotalScore(candidate, job2)
        ).thenReturn(80.0);

        Mockito.when(
                scoringService.calculateTotalScore(candidate, job3)
        ).thenReturn(70.0);

        RecommendationService recommendationService =
                new RecommendationService(
                        candidateRepository,
                        jobRepository,
                        scoringService
                );

        List<RecommendationResult> results =
                recommendationService.getRecommendations(1L, 2);

        assertEquals(2, results.size());

        assertEquals(
                "Job 1",
                results.get(0).getJob().getTitle()
        );

        assertEquals(
                "Job 2",
                results.get(1).getJob().getTitle()
        );
    }
}