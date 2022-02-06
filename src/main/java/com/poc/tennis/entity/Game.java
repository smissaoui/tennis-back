package com.poc.tennis.entity;

import com.poc.tennis.model.Score;
import com.poc.tennis.model.Status;
import lombok.*;

import javax.persistence.*;
import java.util.List;
import java.util.Map;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Game {

    @Id
    @EqualsAndHashCode.Include
    private String id;
    @Enumerated(EnumType.STRING)
    private Status gameStatus;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "set_id")
    private Set set;
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(
            name = "GAME_PLAYERS",
            joinColumns = @JoinColumn(name = "game_id"),
            inverseJoinColumns = @JoinColumn(name = "player_id")
    )
    private List<Player> playersInGame;
    @ElementCollection
    @Enumerated(EnumType.STRING)
    private Map<String,Score> scorePlayer;
    @ElementCollection
    private Map<String, Integer> scorePlayerTieBreak;
}
