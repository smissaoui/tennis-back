package com.poc.tennis.entity;

import com.poc.tennis.model.Status;
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
public class Set {

    @Id
    @EqualsAndHashCode.Include
    private String id;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "match_id")
    private Match match;
    @OneToMany(mappedBy = "set", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Game> game;
    @Enumerated(EnumType.STRING)
    private Status setStatus;
    private int setNumber;
    private boolean tieBreak;
}
