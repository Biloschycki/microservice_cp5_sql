package com.github.biloschycki.jogador.mapper;

import java.util.List;

import org.springframework.stereotype.Component;

import com.github.biloschycki.jogador.dto.request.RestauranteRequestDTO;
import com.github.biloschycki.jogador.dto.response.RestauranteResponseDTO;
import com.github.biloschycki.jogador.model.Restaurante;

/**
 * Responsável por centralizar toda a lógica de conversão entre a entidade
 * Restaurante e seus respectivos DTOs, evitando que essa responsabilidade
 * fique espalhada pelas Controllers/Services.
 */
@Component
public class RestauranteMapper {

    /**
     * Converte um DTO de request em uma nova entidade Restaurante.
     * Utilizado na criação de um novo registro.
     */
    public Restaurante toEntity(RestauranteRequestDTO dto) {
        if (dto == null) {
            return null;
        }

        Restaurante restaurante = new Restaurante();
        if (dto.id() != null) {
            restaurante.setId(dto.id());
        }
        restaurante.setNome(dto.nome());
        restaurante.setNumero(dto.numero());
        restaurante.setQuantidade(dto.quantidade());
        restaurante.setValor(dto.valor());
        return restaurante;
    }

    /**
     * Atualiza os campos de uma entidade Restaurante já existente com os
     * dados vindos do DTO de request. O id não é alterado por este método,
     * pois em atualizações o identificador é sempre definido a partir do
     * path variable da requisição.
     */
    public void updateEntityFromDto(RestauranteRequestDTO dto, Restaurante restaurante) {
        restaurante.setNome(dto.nome());
        restaurante.setNumero(dto.numero());
        restaurante.setQuantidade(dto.quantidade());
        restaurante.setValor(dto.valor());
    }

    /**
     * Converte uma entidade Restaurante em seu DTO de response.
     */
    public RestauranteResponseDTO toResponseDTO(Restaurante restaurante) {
        if (restaurante == null) {
            return null;
        }

        return new RestauranteResponseDTO(
                restaurante.getId(),
                restaurante.getNome(),
                restaurante.getNumero(),
                restaurante.getQuantidade(),
                restaurante.getValor()
        );
    }

    /**
     * Converte uma lista de entidades Restaurante em uma lista de DTOs de response.
     */
    public List<RestauranteResponseDTO> toResponseDTOList(List<Restaurante> restaurantes) {
        return restaurantes.stream()
                .map(this::toResponseDTO)
                .toList();
    }
}
