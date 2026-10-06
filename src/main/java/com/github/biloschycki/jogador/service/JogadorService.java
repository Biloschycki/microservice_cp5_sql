package com.github.biloschycki.jogador.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.biloschycki.jogador.dto.request.JogadorRequestDTO;
import com.github.biloschycki.jogador.dto.response.JogadorResponseDTO;
import com.github.biloschycki.jogador.exception.ResourceNotFoundException;
import com.github.biloschycki.jogador.mapper.JogadorMapper;
import com.github.biloschycki.jogador.model.Jogador;
import com.github.biloschycki.jogador.repository.JogadorRepository;

/**
 * Camada de serviço responsável pelas regras de negócio do CRUD de Jogador.
 * Centraliza o uso do Mapper e do Repository, mantendo a Controller
 * enxuta e focada apenas em receber/responder requisições HTTP.
 */
@Service
public class JogadorService {

    @Autowired
    private JogadorRepository repository;

    @Autowired
    private JogadorMapper mapper;

    public JogadorResponseDTO create(JogadorRequestDTO dto) {
        Jogador jogador = mapper.toEntity(dto);
        Jogador salvo = repository.save(jogador);
        return mapper.toResponseDTO(salvo);
    }

    public List<JogadorResponseDTO> findAll() {
        return mapper.toResponseDTOList(repository.findAll());
    }

    public JogadorResponseDTO findById(Long id) {
        Jogador jogador = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Jogador não encontrado com id: " + id));
        return mapper.toResponseDTO(jogador);
    }

    public JogadorResponseDTO update(Long id, JogadorRequestDTO dto) {
        Jogador jogador = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Jogador não encontrado com id: " + id));

        mapper.updateEntityFromDto(dto, jogador);
        jogador.setId(id);

        Jogador atualizado = repository.save(jogador);
        return mapper.toResponseDTO(atualizado);
    }

    public void deleteById(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Jogador não encontrado com id: " + id);
        }
        repository.deleteById(id);
    }
}
