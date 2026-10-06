package com.github.biloschycki.jogador.mapper;

import java.util.List;

import org.springframework.stereotype.Component;

import com.github.biloschycki.jogador.dto.request.JogadorRequestDTO;
import com.github.biloschycki.jogador.dto.response.JogadorResponseDTO;
import com.github.biloschycki.jogador.model.Jogador;

/**
 * Responsável por centralizar toda a lógica de conversão entre a entidade
 * Jogador e seus respectivos DTOs, evitando que essa responsabilidade
 * fique espalhada pelas Controllers/Services.
 */
@Component
public class JogadorMapper {

    /**
     * Converte um DTO de request em uma nova entidade Jogador.
     * Utilizado na criação de um novo registro.
     */
    public Jogador toEntity(JogadorRequestDTO dto) {
        if (dto == null) {
            return null;
        }

        Jogador jogador = new Jogador();
        if (dto.id() != null) {
            jogador.setId(dto.id());
        }
        jogador.setNome(dto.nome());
        jogador.setTime(dto.time());
        jogador.setNumero(dto.numero());
        jogador.setPosicao(dto.posicao());
        return jogador;
    }

    /**
     * Atualiza os campos de uma entidade Jogador já existente com os
     * dados vindos do DTO de request. O id não é alterado por este método,
     * pois em atualizações o identificador é sempre definido a partir do
     * path variable da requisição.
     */
    public void updateEntityFromDto(JogadorRequestDTO dto, Jogador jogador) {
        jogador.setNome(dto.nome());
        jogador.setTime(dto.time());
        jogador.setNumero(dto.numero());
        jogador.setPosicao(dto.posicao());
    }

    /**
     * Converte uma entidade Jogador em seu DTO de response.
     */
    public JogadorResponseDTO toResponseDTO(Jogador jogador) {
        if (jogador == null) {
            return null;
        }

        return new JogadorResponseDTO(
                jogador.getId(),
                jogador.getNome(),
                jogador.getTime(),
                jogador.getNumero(),
                jogador.getPosicao()
        );
    }

    /**
     * Converte uma lista de entidades Jogador em uma lista de DTOs de response.
     */
    public List<JogadorResponseDTO> toResponseDTOList(List<Jogador> jogadores) {
        return jogadores.stream()
                .map(this::toResponseDTO)
                .toList();
    }
}
