package com.github.andremattheus.libraryapi.repository;

import com.github.andremattheus.libraryapi.model.Autor;
import com.github.andremattheus.libraryapi.model.GeneroLivro;
import com.github.andremattheus.libraryapi.model.Livro;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@SpringBootTest
class LivroRepositoryTest {

    @Autowired
    LivroRepository repository;

    @Autowired
    AutorRepository autorRepository;

    // Salvando o autor buscando no banco de dados um já existente
    @Test
    void salvarTest(){
        Livro livro = new Livro();
        livro.setIsbn("6513-1546");
        livro.setPreco(BigDecimal.valueOf(100));
        livro.setGenero(GeneroLivro.FICCAO);
        livro.setTitulo("UFO");
        livro.setDataPublicacao(LocalDate.of(1982, 1, 15));

        Autor autor = autorRepository.findById(UUID.fromString("6516346351346")).orElse(null);

        livro.setAutor(autor);
    }

    // Sem cascade (Precisa comentar o cascade em livro.class para funcionar)
    @Test
    void salvarAutorELivroTest(){
        Livro livro = new Livro();
        livro.setIsbn("6513-1546");
        livro.setPreco(BigDecimal.valueOf(100));
        livro.setGenero(GeneroLivro.FICCAO);
        livro.setTitulo("UFO");
        livro.setDataPublicacao(LocalDate.of(1982, 1, 15));

        Autor autor = new Autor();
        autor.setNome("Maria");
        autor.setNacionalidade("Brasileira");
        autor.setDataNascimento(LocalDate.of(1951,4,18));

        autorRepository.save(autor);

        livro.setAutor(autor);

        repository.save(livro);
    }

    // Salvando o autor junto com o livro em CASCATA (cascade)
    @Test
    void salvarCascadeTest(){
        Livro livro = new Livro();
        livro.setIsbn("6513-1546");
        livro.setPreco(BigDecimal.valueOf(100));
        livro.setGenero(GeneroLivro.FICCAO);
        livro.setTitulo("UFO");
        livro.setDataPublicacao(LocalDate.of(1982, 1, 15));

        Autor autor = new Autor();
        autor.setNome("Maria");
        autor.setNacionalidade("Brasileira");
        autor.setDataNascimento(LocalDate.of(1951,4,18));

        livro.setAutor(autor);

        repository.save(livro);
    }

    @Test
    void atualizarAutorDoLivro(){
        UUID id = UUID.fromString("ondwknnabulblonawf");
        var livroParaAtualizar = repository.findById(id).orElse(null);

        UUID idAutor = UUID.fromString("35153154635");
        Autor maria = autorRepository.findById(idAutor).orElse(null);

        livroParaAtualizar.setAutor(maria);

        repository.save(livroParaAtualizar);
    }

    @Test
    void deletar(){
        UUID id = UUID.fromString("46354631464635");
        var livroParaDeletar = repository.deleteById(id);

    }
}