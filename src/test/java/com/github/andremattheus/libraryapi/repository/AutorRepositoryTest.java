package com.github.andremattheus.libraryapi.repository;

import com.github.andremattheus.libraryapi.model.Autor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@SpringBootTest
public class AutorRepositoryTest {

    @Autowired
    AutorRepository repository;

    @Test
    public void salvarTest(){
        Autor autor = new Autor();
        autor.setNome("Maria");
        autor.setNacionalidade("Brasileira");
        autor.setDataNascimento(LocalDate.of(1951,4,18));

        var autorSalvo = repository.save(autor);
        System.out.println("Autor salvo com sucesso: " + autorSalvo);
    }

    @Test
    public void atualizarTest(){
        var id = UUID.fromString("e6b81cb1-d045-450f-a846-0492dd9044f0");

        Optional<Autor> possivelAutor = repository.findById(id);

        if(possivelAutor.isPresent()){
            Autor autorEnconstrado = possivelAutor.get();
            System.out.println("Dados do Autor:");
            System.out.println(possivelAutor.get());

            autorEnconstrado.setDataNascimento(LocalDate.of(1960,1,30));

            repository.save(autorEnconstrado);
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
        var id = UUID.fromString("e6b81cb1-d045-450f-a846-0492dd9044f0");
        repository.deleteById(id);
    }

    @Test
    public void deleteTest(){
        var id = UUID.fromString("e6e36443-8891-460d-9d5c-fac930548187");
        var maria = repository.findById(id).get();
        repository.deleteById(id);
    }
}
