package com.prova.insper.entregas;

import com.prova.insper.entregas.dto.TotalEntregasEntregadorDTO;
import com.prova.insper.entregas.dto.EntregaDTO;
import com.prova.insper.entregas.erros.EntregaNaoEncontradaException;
import com.prova.insper.entregas.erros.DadosInvalidosException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
// import java.util.UUID; Tiramos por questoes de escalabilidade
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EntregasService {

    @Autowired
    private EntregasRepository entregasRepository;

    private int idCounter = 1;

    public EntregaDTO saveEntrega(EntregaDTO entregaDTO) {
        // Validação de dados de entrada
        if (entregaDTO.getCpfEntregador() == null || entregaDTO.getDistancia() == null) {
            throw new DadosInvalidosException("Campos obrigatórios não foram fornecidos.");
        }

        entregaDTO.setIdentifier(Integer.toString(idCounter++));
        Entrega entrega = Entrega.convert(entregaDTO);
        return EntregaDTO.convert(entregasRepository.save(entrega));
    }

    @Cacheable("entregasCache") //Implementando cache
    public List<EntregaDTO> getEntregas() {
        return entregasRepository
                .findAll()
                .stream()
                .map(v -> EntregaDTO.convert(v))
                .collect(Collectors.toList());
    }

    public EntregaDTO getEntrega(String identifier) {
        List<EntregaDTO> entregas = entregasRepository
                .findAll()
                .stream()
                .filter(v -> v.getIdentifier().equals(identifier))
                .map(v -> EntregaDTO.convert(v))
                .collect(Collectors.toList());
        if (!entregas.isEmpty()) {
            return entregas.get(0);
        } else {
            return null;
        }
    }

    public void deleteEntregas(String identifier) {
        List<Entrega> entregas = entregasRepository
                .findAll()
                .stream()
                .filter(v -> v.getIdentifier().equals(identifier))
                .collect(Collectors.toList());
    
        if (entregas.size() > 0) {
            entregasRepository.delete(entregas.get(0));
        } else {
            throw new EntregaNaoEncontradaException("Entrega com identificador " + identifier + " não encontrada.");
        }
    }

    @Cacheable("entregasMaiorMinimoCache")
    public List<EntregaDTO> getEntregasMaiorMinimo(Float minimo) {
        List<EntregaDTO> entregas = entregasRepository
                .findAll()
                .stream()
                .filter(v -> v.getDistancia() > minimo)
                .map(v -> EntregaDTO.convert(v))
                .collect(Collectors.toList());
        return entregas;
    }

    @Cacheable("entregasMaiorMinimoEntregadorCache")
    public List<TotalEntregasEntregadorDTO> getEntregasMaiorMinimoPorEntregador(Float minimo, List<String> cpfs) {
        List<Entrega> entregas = entregasRepository.findByDistanciaGreaterThanAndCpfEntregadorIn(minimo, cpfs);
        return entregas.stream()
                .filter(entrega -> entrega.getDistancia() > minimo) // Filtrando por minimos
                .collect(Collectors.groupingBy(Entrega::getCpfEntregador))
                .entrySet()
                .stream()
                .map(entry -> new TotalEntregasEntregadorDTO(entry.getKey(), entry.getValue().stream().map(EntregaDTO::convert).collect(Collectors.toList())))
                .collect(Collectors.toList());
    }
}
