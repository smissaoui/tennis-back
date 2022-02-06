package com.poc.tennis.repository;

import com.poc.tennis.entity.Match;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MatchJpaRepository extends JpaRepository<Match, String> {
}
