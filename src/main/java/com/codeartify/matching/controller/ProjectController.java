package com.codeartify.matching.controller;

import com.codeartify.matching.domain.Project;
import com.codeartify.matching.repository.ProjectRepository;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {
    private final ProjectRepository repo;

    public ProjectController(ProjectRepository repo) {
        this.repo = repo;
    }

    @GetMapping
    public List<Project> all() {
        return repo.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Project> one(@PathVariable Long id) {
        return repo.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Project create(@Valid @RequestBody Project p) {
        return repo.save(p);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Project> update(@PathVariable Long id, @Valid @RequestBody Project p) {
        return repo.findById(id).map(existing -> {
            existing.setTitle(p.getTitle());
            existing.setDescription(p.getDescription());
            existing.setMaxHourlyRate(p.getMaxHourlyRate());
            existing.setTimeZone(p.getTimeZone());
            existing.setStartDate(p.getStartDate());
            existing.setEndDate(p.getEndDate());
            existing.setRequiredSkills(p.getRequiredSkills());
            return ResponseEntity.ok(repo.save(existing));
        }).orElse(ResponseEntity.notFound().build());
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
