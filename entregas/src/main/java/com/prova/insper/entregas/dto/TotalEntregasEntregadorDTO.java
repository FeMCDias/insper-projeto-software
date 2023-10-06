package com.prova.insper.entregas.dto;

import java.util.List;

public class TotalEntregasEntregadorDTO {
    private String cpfEntregador;
    private List<EntregaDTO> entregas;

    public TotalEntregasEntregadorDTO(String cpfEntregador, List<EntregaDTO> entregas) {
        this.cpfEntregador = cpfEntregador;
        this.entregas = entregas;
    }

    public String getCpfEntregador() {
        return cpfEntregador;
    }

    public List<EntregaDTO> getEntregas() {
        return entregas;
    }
}




