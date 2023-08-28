package com.insper.partida.equipe;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.insper.partida.game.Game;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document("team")
public class Team {

    @Id
    private String id;

    private String identifier;

    private String name;

    private String stadium;

    private List<Game> away;

    private List<Game> home;
}
