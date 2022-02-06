package com.poc.tennis.repository;

import com.poc.tennis.entity.Match;
import com.poc.tennis.entity.Set;
import com.poc.tennis.model.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SetJpaRepository extends JpaRepository<Set, String> {

    @Query(value = "select max(setNumber) " +
            "from Set s " +
            "where s.match = :match ")
    public int computeSetTotalNumber(Match match);

    @Query(value = "select count(*) " +
            "from Set s " +
            "join s.game g " +
            "where s.setNumber = :setNumber " +
            "and g.gameStatus = :status " +
            "and s.match = :match")
    public int computeGamesWonByPlayer(int setNumber, Status status, Match match);
}
