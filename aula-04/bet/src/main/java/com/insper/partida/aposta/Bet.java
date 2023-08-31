package com.insper.partida.aposta;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.annotation.Id;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Document("bet")
public class Bet {

    @Id
    private String id;

    private BetStatus status;

    private BetResult result;

    private String gameIdentifier;

}
