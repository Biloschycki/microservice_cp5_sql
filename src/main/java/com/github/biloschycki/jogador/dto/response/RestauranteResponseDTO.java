package com.github.biloschycki.jogador.dto.response;

/**
 * DTO utilizado para representar os dados de um pedido (Restaurante)
 * retornados pela API nas operações de criação, consulta, atualização
 * e listagem.
 */
public record RestauranteResponseDTO(
        Long id,
        String nome,
        int numero,
        int quantidade,
        double valor
) {
}
