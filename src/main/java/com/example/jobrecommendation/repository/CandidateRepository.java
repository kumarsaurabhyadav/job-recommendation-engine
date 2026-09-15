package com.example.jobrecommendation.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.jobrecommendation.entity.Candidate;

public interface CandidateRepository extends JpaRepository<Candidate, Long> {
}