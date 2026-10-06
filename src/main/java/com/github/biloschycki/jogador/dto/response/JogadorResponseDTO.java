package com.github.biloschycki.jogador.dto.response;

/**
 * DTO utilizado para representar os dados de um Jogador retornados
 * pela API nas operações de criação, consulta, atualização e listagem.
 */
public record JogadorResponseDTO(
        Long id,
        String nome,
        String time,
        int numero,
        String posicao
) {
}
