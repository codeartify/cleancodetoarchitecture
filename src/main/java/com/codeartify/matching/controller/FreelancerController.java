package com.codeartify.matching.controller;

import com.codeartify.matching.domain.Freelancer;
import com.codeartify.matching.repository.FreelancerRepository;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/freelancers")
public class FreelancerController {
    private final FreelancerRepository repo;

    public FreelancerController(FreelancerRepository repo) {
        this.repo = repo;
    }

    @GetMapping
    public List<Freelancer> all() {
        return repo.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Freelancer> one(@PathVariable Long id) {
        return repo.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Freelancer create(@Valid @RequestBody Freelancer f) {
        return repo.save(f);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Freelancer> update(@PathVariable Long id, @Valid @RequestBody Freelancer f) {
        return repo.findById(id).map(existing -> {
                    existing.setName(f.getName());
                    existing.setHourlyRate(f.getHourlyRate());
                    existing.setRating(f.getRating());
                    existing.setTimeZone(f.getTimeZone());
                    existing.setAvailableFrom(f.getAvailableFrom());
                    existing.setAvailableTo(f.getAvailableTo());
                    existing.setSkills(f.getSkills());
                    return ResponseEntity.ok(repo.save(existing));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (repo.existsById(id)) {
            repo.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
