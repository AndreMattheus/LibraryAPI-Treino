package com.github.andremattheus.libraryapi.service;

import com.github.andremattheus.libraryapi.exceptions.OperacaoNaoPermitidaException;
import com.github.andremattheus.libraryapi.model.Autor;
import com.github.andremattheus.libraryapi.repository.AutorRepository;
import com.github.andremattheus.libraryapi.repository.LivroRepository;
import com.github.andremattheus.libraryapi.validator.AutorValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
// Anotação para o Lombok fazer a criação automática de construtores em argumentos finais(RequiredArgs)
@RequiredArgsConstructor
public class AutorService {

    private final AutorRepository repository;
    private final AutorValidator validator;
    private final LivroRepository livroRepository;

    // Não mais necessário pois estamos usando Anotação do Lombok para fazer automaticamente
    // Construtor para as injeções
//    public AutorService(AutorRepository repository, AutorValidator validator, LivroRepository livroRepository) {
//        this.repository = repository;
//        this.validator = validator;
//        this.livroRepository = livroRepository;
//    }

    public Autor salvar(Autor autor) {
        validator.validar(autor);
        return repository.save(autor);
    }

    public void atualizar(Autor autor) {
        if(autor.getId() == null){
            throw new IllegalArgumentException("Para atualizar é necessário que o autor já esteja salvo no banco de dados");
        }
        validator.validar(autor);
        repository.save(autor);
    }

    public Optional<Autor> obterPorId(UUID id){
        return repository.findById(id);
    }

    public void deletar(Autor autor) {
        if(possuiLivro(autor)){
            throw new OperacaoNaoPermitidaException("Não é permitido excluir um autor que possui livros cadastrados.");
        }
        repository.delete(autor);
    }

    public List<Autor> pesquisar(String nome, String nacionalidade){
        if(nome != null && nacionalidade != null){
            return repository.findByNomeAndNacionalidade(nome, nacionalidade);
        } else if(nome != null && nacionalidade == null){
            return repository.findByNome(nome);
        } else if(nome == null && nacionalidade != null) {
            return repository.findByNacionalidade(nacionalidade);
        } else{
            return repository.findAll();
        }
    }

    //Estratégia de Pesquisa mais simples utilizando exemplos, sem considerar CAPS e apenas contendo partes para match
    public List<Autor> pesquisaByExample (String nome, String nacionalidade){
        var autor = new Autor();
        autor.setNome(nome);
        autor.setNacionalidade(nacionalidade);

        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreNullValues()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example<Autor> autorExample = Example.of(autor, matcher);
        return repository.findAll(autorExample);
    }

    public boolean possuiLivro(Autor autor){
        return livroRepository.existsByAutor(autor);
    }
}
