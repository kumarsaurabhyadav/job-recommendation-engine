package com.example.jobrecommendation.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.jobrecommendation.entity.Candidate;
import com.example.jobrecommendation.repository.CandidateRepository;

@RestController
@RequestMapping("/candidates")
public class CandidateController {

    private final CandidateRepository candidateRepository;

    public CandidateController(CandidateRepository candidateRepository) {
        this.candidateRepository = candidateRepository;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Candidate createCandidate(@RequestBody Candidate candidate) {
        return candidateRepository.save(candidate);
    }

    @GetMapping("/{id}")
    public Candidate getCandidate(@PathVariable Long id) {
        return candidateRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Candidate not found"));
    }

    @GetMapping
    public List<Candidate> getAllCandidates() {
        return candidateRepository.findAll();
    }
}