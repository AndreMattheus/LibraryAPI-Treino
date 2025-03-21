package com.github.andremattheus.libraryapi.repository;

import com.github.andremattheus.libraryapi.model.Autor;
import com.github.andremattheus.libraryapi.model.GeneroLivro;
import com.github.andremattheus.libraryapi.model.Livro;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@SpringBootTest
class LivroRepositoryTest {

    @Autowired
    LivroRepository repository;

    @Autowired
    AutorRepository autorRepository;

    // Salvando o autor buscando no banco de dados um já existente
    @Test
    void salvarTest() {
        Livro livro = new Livro();
        livro.setIsbn("6513-1546");
        livro.setPreco(BigDecimal.valueOf(150));
        livro.setGenero(GeneroLivro.FANTASIA);
        livro.setTitulo("Harry Potter");
        livro.setDataPublicacao(LocalDate.of(1995, 6, 15));

        Autor autor = autorRepository.findById(UUID.fromString("33239c63-7b96-44e8-8ded-f20732e50adc")).orElse(null);

        livro.setAutor(autor);
        repository.save(livro);
    }

    // Sem cascade (Precisa comentar o cascade em livro.class para funcionar)
    @Test
    void salvarAutorELivroTest() {
        Livro livro = new Livro();
        livro.setIsbn("6513-1546");
        livro.setPreco(BigDecimal.valueOf(100));
        livro.setGenero(GeneroLivro.FICCAO);
        livro.setTitulo("UFO");
        livro.setDataPublicacao(LocalDate.of(1982, 1, 15));

        Autor autor = new Autor();
        autor.setNome("Maria");
        autor.setNacionalidade("Brasileira");
        autor.setDataNascimento(LocalDate.of(1951, 4, 18));

        autorRepository.save(autor);

        livro.setAutor(autor);

        repository.save(livro);
    }

    // Salvando o autor junto com o livro em CASCATA (cascade)
    @Test
    void salvarCascadeTest() {
        Livro livro = new Livro();
        livro.setIsbn("6513-1546");
        livro.setPreco(BigDecimal.valueOf(100));
        livro.setGenero(GeneroLivro.FICCAO);
        livro.setTitulo("UFO");
        livro.setDataPublicacao(LocalDate.of(1982, 1, 15));

        Autor autor = new Autor();
        autor.setNome("Maria");
        autor.setNacionalidade("Brasileira");
        autor.setDataNascimento(LocalDate.of(1951, 4, 18));

        livro.setAutor(autor);

        repository.save(livro);
    }

    @Test
    void atualizarAutorDoLivro() {
        UUID id = UUID.fromString("ondwknnabulblonawf");
        var livroParaAtualizar = repository.findById(id).orElse(null);

        UUID idAutor = UUID.fromString("35153154635");
        Autor maria = autorRepository.findById(idAutor).orElse(null);

        livroParaAtualizar.setAutor(maria);

        repository.save(livroParaAtualizar);
    }

    @Test
    void deletar() {
        UUID id = UUID.fromString("46354631464635");
        repository.deleteById(id);

    }

    @Test
    @Transactional
        //Precisa do @Transient em List do Autor.class e remover @OneToMany
    void buscarLivroTest() {
        UUID id = UUID.fromString("a83b5105-5824-4a4e-ab4c-ee49497a2b2f");
        var livro = repository.findById(id).orElse(null);
        System.out.println("Livro buscado: " + livro.getTitulo());
        System.out.println("Autor: " + livro.getAutor().getNome());

    }

    @Test
    void pesquisaPorTituloTest() {
        List<Livro> lista = repository.findByTitulo("Amor de Verão");
        lista.forEach(System.out::println);
    }

    @Test
    void pesquisaPorIsbnTest() {
        List<Livro> lista = repository.findByIsbn("4002-8922");
        lista.forEach(System.out::println);
    }
}