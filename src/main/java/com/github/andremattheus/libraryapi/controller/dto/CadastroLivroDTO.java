package com.github.andremattheus.libraryapi.controller.dto;

import com.github.andremattheus.libraryapi.model.GeneroLivro;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import org.hibernate.validator.constraints.ISBN;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record CadastroLivroDTO(@NotBlank(message = "Campo Obrigatório!")@ISBN String isbn,
                               @NotBlank(message = "Campo Obrigatório!") String titulo,
                               @NotNull(message = "Campo Obrigatório!")
                               @Past(message = "Não pode ser uma data futura.") LocalDate dataPublicacao,
                               GeneroLivro genero, BigDecimal preco,
                               @NotNull(message = "Campo Obrigatório!") UUID idAutor) {
}
