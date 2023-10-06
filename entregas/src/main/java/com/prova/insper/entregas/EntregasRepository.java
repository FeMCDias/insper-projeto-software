package com.prova.insper.entregas;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface EntregasRepository extends JpaRepository<Entrega, Integer> {
    List<Entrega> findByDistanciaGreaterThanAndCpfEntregadorIn(Float minimo, List<String> cpfs);
}