package com.example.jobrecommendation.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.jobrecommendation.entity.Job;

public interface JobRepository extends JpaRepository<Job, Long> {
}