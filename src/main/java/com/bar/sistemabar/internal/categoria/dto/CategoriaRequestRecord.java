package com.bar.sistemabar.internal.categoria.dto;

import jakarta.validation.constraints.NotBlank;

public record CategoriaRequestRecord(

        @NotBlank(message = "O nome da categoria é obrigatório")
        String nome,

        @NotBlank(message = "O status da categoria é obrigatório")
        String status

) {
}