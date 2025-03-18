package com.github.andremattheus.libraryapi.repository;

import com.github.andremattheus.libraryapi.model.Autor;
import com.github.andremattheus.libraryapi.model.GeneroLivro;
import com.github.andremattheus.libraryapi.model.Livro;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@SpringBootTest
public class AutorRepositoryTest {

    @Autowired
    AutorRepository repository;

    @Autowired
    LivroRepository livroRepository;

    @Test
    public void salvarTest(){
        Autor autor = new Autor();
        autor.setNome("Beteliz");
        autor.setNacionalidade("Brasileira");
        autor.setDataNascimento(LocalDate.of(1978,9,18));

        var autorSalvo = repository.save(autor);
        System.out.println("Autor salvo com sucesso: " + autorSalvo);
    }

    @Test
    public void atualizarTest(){
        var id = UUID.fromString("e6b81cb1-d045-450f-a846-0492dd9044f0");

        Optional<Autor> possivelAutor = repository.findById(id);

        if(possivelAutor.isPresent()){
            Autor autorEncontrado = possivelAutor.get();
            System.out.println("Dados do Autor:");
            System.out.println(possivelAutor.get());

            autorEncontrado.setDataNascimento(LocalDate.of(1960,1,30));

            repository.save(autorEncontrado);
        }
    }

    @Test
    public void listarTest(){
        List<Autor> lista = repository.findAll();
        lista.forEach(System.out::println);
    }

    @Test
    public void countTest(){
        System.out.println("Contagem de autores: " + repository.count());
    }

    @Test
    public void deletePorIdTest(){
        var id = UUID.fromString("a9d1aa30-7d88-4373-8b9b-390fd544e502");
        repository.deleteById(id);
    }

    @Test
    public void deleteTest(){
        var id = UUID.fromString("e6e36443-8891-460d-9d5c-fac930548187");
        var maria = repository.findById(id).get();
        repository.delete(maria);
    }

    @Test
    void salvarAutorComLivrosTest(){
        Autor autor = new Autor();
        autor.setNome("Hope");
        autor.setNacionalidade("Canadense");
        autor.setDataNascimento(LocalDate.of(2006,7,5));

        Livro livro = new Livro();
        livro.setIsbn("4002-8922");
        livro.setPreco(BigDecimal.valueOf(346));
        livro.setGenero(GeneroLivro.ROMANCE);
        livro.setTitulo("Amor de Verão");
        livro.setDataPublicacao(LocalDate.of(2023, 10, 22));
        livro.setAutor(autor);

        Livro livro2 = new Livro();
        livro2.setIsbn("4002-1505");
        livro2.setPreco(BigDecimal.valueOf(470));
        livro2.setGenero(GeneroLivro.ROMANCE);
        livro2.setTitulo("Amor de Verão Pt2");
        livro2.setDataPublicacao(LocalDate.of(2024, 6, 2));
        livro2.setAutor(autor);

        autor.setLivros(new ArrayList<>());
        autor.getLivros().add(livro);
        autor.getLivros().add(livro2);

        repository.save(autor);
        livroRepository.saveAll(autor.getLivros());
    }
}
