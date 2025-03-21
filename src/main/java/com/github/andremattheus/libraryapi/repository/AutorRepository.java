package com.github.andremattheus.libraryapi.repository;

import com.github.andremattheus.libraryapi.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AutorRepository extends JpaRepository<Autor, UUID> {

    List<Autor> findByNome(String nome);
    List<Autor> findByNacionalidade(String nacionalidade);
    List<Autor> findByNomeAndNacionalidade(String nome, String nacionalidade);

    // Para o validador de existência do autor no repositório
    Optional<Autor> findByNomeAndDataNascimentoAndNacionalidade(
            String nome, LocalDate dataNascimento, String nacionalidade);
}
