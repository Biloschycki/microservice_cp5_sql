package com.github.biloschycki.jogador.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

/**
 * DTO utilizado para receber dados de entrada nas operações de
 * criação (POST) e atualização (PUT) de um Jogador.
 *
 * O campo "id" é opcional e só é relevante na criação, pois a entidade
 * Jogador não possui geração automática de identificador (sem @GeneratedValue),
 * preservando o comportamento original da aplicação (Checkpoint 2), em que o
 * cliente informa o id manualmente. Em operações de atualização, o id do
 * caminho (path variable) sempre prevalece sobre o valor enviado no corpo.
 */
public record JogadorRequestDTO(

        Long id,

        @NotBlank(message = "O nome do jogador é obrigatório")
        String nome,

        @NotBlank(message = "O time do jogador é obrigatório")
        String time,

        @NotNull(message = "O número do jogador é obrigatório")
        @Positive(message = "O número do jogador deve ser positivo")
        Integer numero,

        @NotBlank(message = "A posição do jogador é obrigatória")
        String posicao
) {
}
