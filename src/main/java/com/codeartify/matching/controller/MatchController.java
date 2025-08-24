package com.codeartify.matching.controller;

import com.codeartify.matching.dto.MatchResultDTO;
import com.codeartify.matching.service.MatchingService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/match")
public class MatchController {
    private final MatchingService matchingService;

    public MatchController(MatchingService ms) {
        this.matchingService = ms;
    }

    @GetMapping("/projects/{projectId}/freelancers")
    public List<MatchResultDTO> matchFreelancers(@PathVariable Long projectId) {
        return matchingService.matchFreelancersForProject(projectId);
    }

    @GetMapping("/freelancers/{freelancerId}/projects")
    public List<MatchResultDTO> matchProjects(@PathVariable Long freelancerId) {
        return matchingService.matchProjectsForFreelancer(freelancerId);
    }
}
