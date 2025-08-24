package com.codeartify.matching.repository;

import com.codeartify.matching.domain.Freelancer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FreelancerRepository extends JpaRepository<Freelancer, Long> {
}
