package com.insper.partida.aposta;

import org.springframework.stereotype.Repository;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

@Repository
public interface BetRespository extends MongoRepository<Bet, Integer> {
    List<Bet> findByGameIdentifier(String gameId);
}
