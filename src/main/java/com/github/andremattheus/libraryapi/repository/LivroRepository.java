package com.github.andremattheus.libraryapi.repository;

import com.github.andremattheus.libraryapi.model.Autor;
import com.github.andremattheus.libraryapi.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface LivroRepository extends JpaRepository<Livro, UUID> {

    // Query Methods (Forma que o JPA faz ações no banco de dados sem precisar de SQL
    // select * from livro where id_autor = id
    List<Livro> findByAutor(Autor autor);

    // select * from livro where isbn = isbn
    List<Livro> findByIsbn(String isbn);

    // select * from livro where titulo = titulo
    List<Livro> findByTitulo(String titulo);
}
