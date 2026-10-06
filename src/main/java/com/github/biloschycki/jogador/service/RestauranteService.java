package com.github.biloschycki.jogador.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.biloschycki.jogador.dto.request.RestauranteRequestDTO;
import com.github.biloschycki.jogador.dto.response.RestauranteResponseDTO;
import com.github.biloschycki.jogador.exception.ResourceNotFoundException;
import com.github.biloschycki.jogador.mapper.RestauranteMapper;
import com.github.biloschycki.jogador.model.Restaurante;
import com.github.biloschycki.jogador.repository.RestauranteRepository;

/**
 * Camada de serviço responsável pelas regras de negócio do CRUD de
 * pedidos (Restaurante). Centraliza o uso do Mapper e do Repository,
 * mantendo a Controller enxuta e focada apenas em receber/responder
 * requisições HTTP.
 */
@Service
public class RestauranteService {

    @Autowired
    private RestauranteRepository repository;

    @Autowired
    private RestauranteMapper mapper;

    public RestauranteResponseDTO create(RestauranteRequestDTO dto) {
        Restaurante restaurante = mapper.toEntity(dto);
        Restaurante salvo = repository.save(restaurante);
        return mapper.toResponseDTO(salvo);
    }

    public List<RestauranteResponseDTO> findAll() {
        return mapper.toResponseDTOList(repository.findAll());
    }

    public RestauranteResponseDTO findById(Long id) {
        Restaurante restaurante = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pedido não encontrado com id: " + id));
        return mapper.toResponseDTO(restaurante);
    }

    public RestauranteResponseDTO update(Long id, RestauranteRequestDTO dto) {
        Restaurante restaurante = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pedido não encontrado com id: " + id));

        mapper.updateEntityFromDto(dto, restaurante);
        restaurante.setId(id);

        Restaurante atualizado = repository.save(restaurante);
        return mapper.toResponseDTO(atualizado);
    }

    public void deleteById(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Pedido não encontrado com id: " + id);
        }
        repository.deleteById(id);
    }
}
