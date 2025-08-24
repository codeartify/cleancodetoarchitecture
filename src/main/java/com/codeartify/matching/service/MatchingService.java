package com.codeartify.matching.service;

import com.codeartify.matching.domain.Freelancer;
import com.codeartify.matching.domain.Project;
import com.codeartify.matching.domain.Skill;
import com.codeartify.matching.dto.MatchResultDTO;
import com.codeartify.matching.repository.FreelancerRepository;
import com.codeartify.matching.repository.ProjectRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class MatchingService {
    private final FreelancerRepository freelancerRepository;
    private final ProjectRepository projectRepository;

    public MatchingService(FreelancerRepository f, ProjectRepository p) {
        this.freelancerRepository = f;
        this.projectRepository = p;
    }

    private static double availabilityOverlapScore(LocalDate fa, LocalDate fb, LocalDate pa, LocalDate pb) {
        if (pa == null && pb == null) return 1.0;
        if (fa == null && fb == null) return 1.0;
        LocalDate fStart = fa, fEnd = fb != null ? fb : (fa != null ? fa.plusYears(100) : null);
        LocalDate pStart = pa, pEnd = pb != null ? pb : (pa != null ? pa.plusYears(100) : null);
        if (fStart == null && fEnd == null) return 1.0;
        if (pStart == null && pEnd == null) return 1.0;
        if (fStart == null) fStart = LocalDate.MIN.plusYears(10);
        if (pStart == null) pStart = LocalDate.MIN.plusYears(10);
        if (fEnd == null) fEnd = LocalDate.MAX.minusYears(10);
        if (pEnd == null) pEnd = LocalDate.MAX.minusYears(10);
        LocalDate start = fStart.isAfter(pStart) ? fStart : pStart;
        LocalDate end = fEnd.isBefore(pEnd) ? fEnd : pEnd;
        if (end.isBefore(start)) return 0.0;
        long overlap = java.time.temporal.ChronoUnit.DAYS.between(start, end) + 1;
        long span = java.time.temporal.ChronoUnit.DAYS.between(fStart.isBefore(pStart) ? fStart : pStart, fEnd.isAfter(pEnd) ? fEnd : pEnd) + 1;
        if (span <= 0) return 0.0;
        return clamp01((double) overlap / (double) span);
    }

    private static double clamp01(double x) {
        return Math.max(0.0, Math.min(1.0, x));
    }

    private static double round2(double x) {
        return Math.round(x * 100.0) / 100.0;
    }

    public java.util.List<MatchResultDTO> matchFreelancersForProject(Long projectId) {
        Project project = projectRepository.findById(projectId).orElseThrow();
        return freelancerRepository.findAll().stream()
                .map(f -> new AbstractMap.SimpleEntry<>(f, scoreFreelancerForProject(f, project)))
                .filter(e -> e.getValue().score >= 0.0001)
                .sorted((a, b) -> Double.compare(b.getValue().score, a.getValue().score))
                .map(e -> new MatchResultDTO(e.getKey().getId(), e.getKey().getName(), round2(e.getValue().score), e.getValue().reasons))
                .collect(Collectors.toList());
    }

    public java.util.List<MatchResultDTO> matchProjectsForFreelancer(Long freelancerId) {
        Freelancer f = freelancerRepository.findById(freelancerId).orElseThrow();
        return projectRepository.findAll().stream()
                .map(p -> new AbstractMap.SimpleEntry<>(p, scoreProjectForFreelancer(p, f)))
                .filter(e -> e.getValue().score >= 0.0001)
                .sorted((a, b) -> Double.compare(b.getValue().score, a.getValue().score))
                .map(e -> new MatchResultDTO(e.getKey().getId(), e.getKey().getTitle(), round2(e.getValue().score), e.getValue().reasons))
                .collect(Collectors.toList());
    }

    private ScoreReasons scoreFreelancerForProject(Freelancer f, Project p) {
        ScoreReasons sr = new ScoreReasons();
        double score = 0.0;
        double W_SKILLS = 0.5, W_BUDGET = 0.2, W_AVAIL = 0.2, W_RATING = 0.1, BONUS_TZ = 0.05;

        var req = p.getRequiredSkills().stream().map(Skill::getName).collect(Collectors.toSet());

        var has = f.getSkills().stream().map(Skill::getName).collect(Collectors.toSet());

        long matched = req.stream().filter(has::contains).count();

        double skillRatio = req.isEmpty() ? 1.0 : (double) matched / (double) req.size();

        score += W_SKILLS * skillRatio;

        sr.reasons.add(String.format("Skills: %d/%d required matched (%.0f%%)", matched, req.size(), 100 * skillRatio));

        if (p.getMaxHourlyRate() != null && f.getHourlyRate() != null) {
            if (f.getHourlyRate() <= p.getMaxHourlyRate()) {
                double budgetFit = (p.getMaxHourlyRate() - f.getHourlyRate()) / p.getMaxHourlyRate();
                score += W_BUDGET * clamp01(budgetFit);
                sr.reasons.add(String.format("Budget fit: rate %.2f <= max %.2f", f.getHourlyRate(), p.getMaxHourlyRate()));
            } else {
                sr.reasons.add(String.format("Over budget: rate %.2f > max %.2f", f.getHourlyRate(), p.getMaxHourlyRate()));
                sr.score = 0.0;
                return sr;
            }
        }

        double availScore = availabilityOverlapScore(f.getAvailableFrom(), f.getAvailableTo(), p.getStartDate(), p.getEndDate());

        score += W_AVAIL * availScore;

        sr.reasons.add(String.format("Availability overlap: %.0f%%", 100 * availScore));

        double ratingNorm = f.getRating() == null ? 0.0 : clamp01(f.getRating() / 5.0);
        score += W_RATING * ratingNorm;

        if (f.getRating() != null) {
            sr.reasons.add(String.format("Rating: %.1f/5", f.getRating()));
        }

        if (p.getTimeZone() != null && f.getTimeZone() != null && p.getTimeZone().equalsIgnoreCase(f.getTimeZone())) {
            score += BONUS_TZ;
            sr.reasons.add("Same time zone bonus");
        }

        sr.score = score;
        return sr;
    }

    private ScoreReasons scoreProjectForFreelancer(Project p, Freelancer f) {
        return scoreFreelancerForProject(f, p);
    }

    private static class ScoreReasons {
        double score;
        List<String> reasons = new ArrayList<>();
    }
}
