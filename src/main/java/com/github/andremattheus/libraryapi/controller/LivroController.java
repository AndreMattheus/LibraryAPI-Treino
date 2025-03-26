package com.github.andremattheus.libraryapi.controller;

import com.github.andremattheus.libraryapi.controller.dto.CadastroLivroDTO;
import com.github.andremattheus.libraryapi.controller.dto.ErroResposta;
import com.github.andremattheus.libraryapi.exceptions.RegistroDuplicadoException;
import com.github.andremattheus.libraryapi.service.LivroService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/livros")
// Anotação para o Lombok criar o construtor de "private FINAL" automaticamente
@RequiredArgsConstructor
public class LivroController {

    private final LivroService service;

    @PostMapping
    public ResponseEntity<Object> salvar(@RequestBody @Valid CadastroLivroDTO dto){
        try {
            // Mapear DTO para entidade
            // Enviar a entidade para o service validar e salvar na base
            // Criar URL para acesso dos dados do livro
            // Retornar código created com o header location

            return ResponseEntity.ok(dto);
        } catch (RegistroDuplicadoException e){
            var erroDTO = ErroResposta.conflito(e.getMessage());
            return ResponseEntity.status(erroDTO.status()).body(erroDTO);
        }
    }
}
