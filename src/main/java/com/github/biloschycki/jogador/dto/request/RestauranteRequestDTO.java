package com.github.biloschycki.jogador.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

/**
 * DTO utilizado para receber dados de entrada nas operações de
 * criação (POST) e atualização (PUT) de um pedido (Restaurante).
 *
 * O campo "id" é opcional e só é relevante na criação, pois a entidade
 * Restaurante não possui geração automática de identificador
 * (sem @GeneratedValue), preservando o comportamento original da aplicação
 * (Checkpoint 2). Em operações de atualização, o id do caminho (path variable)
 * sempre prevalece sobre o valor enviado no corpo.
 */
public record RestauranteRequestDTO(

        Long id,

        @NotBlank(message = "O nome do prato é obrigatório")
        String nome,

        @NotNull(message = "O número da mesa é obrigatório")
        @Positive(message = "O número da mesa deve ser positivo")
        Integer numero,

        @NotNull(message = "A quantidade do prato é obrigatória")
        @Positive(message = "A quantidade do prato deve ser positiva")
        Integer quantidade,

        @NotNull(message = "O valor do pedido é obrigatório")
        @PositiveOrZero(message = "O valor do pedido não pode ser negativo")
        Double valor
) {
}
