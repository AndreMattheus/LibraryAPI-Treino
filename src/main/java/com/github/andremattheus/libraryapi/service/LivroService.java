package com.github.andremattheus.libraryapi.service;

import com.github.andremattheus.libraryapi.repository.LivroRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
// Anotação para o Lombok criar o construtor de "private FINAL" automaticamente
@RequiredArgsConstructor
public class LivroService {

    private final LivroRepository repository;

}
