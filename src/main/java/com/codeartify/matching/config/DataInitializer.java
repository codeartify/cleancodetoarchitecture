package com.codeartify.matching.config;

import com.codeartify.matching.domain.Freelancer;
import com.codeartify.matching.domain.Project;
import com.codeartify.matching.domain.Skill;
import com.codeartify.matching.repository.FreelancerRepository;
import com.codeartify.matching.repository.ProjectRepository;
import com.codeartify.matching.repository.SkillRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.util.Set;

@Configuration
public class DataInitializer {
    @Bean
    CommandLineRunner seed(SkillRepository skills, FreelancerRepository freelancers, ProjectRepository projects) {
        return args -> {
            Skill java = skills.save(new Skill("Java"));
            Skill spring = skills.save(new Skill("Spring"));
            Skill angular = skills.save(new Skill("Angular"));
            Skill react = skills.save(new Skill("React"));
            Skill sql = skills.save(new Skill("SQL"));
            Skill aws = skills.save(new Skill("AWS"));
            Freelancer f1 = new Freelancer("Alice Dev", 100.0, 4.8, "Europe/Zurich", LocalDate.now().plusDays(2), LocalDate.now().plusMonths(6));
            f1.getSkills().addAll(Set.of(java, spring, sql));
            Freelancer f2 = new Freelancer("Bob Front", 80.0, 4.5, "Europe/Zurich", LocalDate.now().plusDays(10), null);
            f2.getSkills().addAll(Set.of(react, angular));
            Freelancer f3 = new Freelancer("Cloud Carol", 120.0, 4.9, "Europe/London", LocalDate.now(), LocalDate.now().plusMonths(3));
            f3.getSkills().addAll(Set.of(java, spring, aws));
            freelancers.save(f1);
            freelancers.save(f2);
            freelancers.save(f3);
            Project p1 = new Project("Back-end revamp", "Refactor payment service", 110.0, "Europe/Zurich", LocalDate.now().plusDays(5), LocalDate.now().plusMonths(2));
            p1.getRequiredSkills().addAll(Set.of(java, spring, sql));
            Project p2 = new Project("SPA upgrade", "Migrate to latest Angular", 90.0, "Europe/Zurich", LocalDate.now().plusDays(14), LocalDate.now().plusMonths(1));
            p2.getRequiredSkills().add(angular);
            Project p3 = new Project("Cloud hardening", "IaC + observability", 130.0, "Europe/London", LocalDate.now().plusDays(1), LocalDate.now().plusMonths(6));
            p3.getRequiredSkills().addAll(Set.of(java, aws));
            projects.save(p1);
            projects.save(p2);
            projects.save(p3);
        };
    }
}
