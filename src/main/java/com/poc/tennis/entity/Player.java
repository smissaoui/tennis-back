package com.poc.tennis.entity;

import com.poc.tennis.model.Players;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Player {

    @Id
    @EqualsAndHashCode.Include
    private String id;
    private String name;
    @ManyToMany(mappedBy = "players", fetch = FetchType.LAZY)
    private List<Match> matches;
    @ManyToMany(mappedBy = "playersInGame", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Game> games;
    private Players tag;
}
