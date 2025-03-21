package com.github.andremattheus.libraryapi.validator;

import com.github.andremattheus.libraryapi.exceptions.RegistroDuplicadoException;
import com.github.andremattheus.libraryapi.model.Autor;
import com.github.andremattheus.libraryapi.repository.AutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AutorValidator {
    private AutorRepository repository;

    @Autowired
    public AutorValidator(AutorRepository repository) {
        this.repository = repository;
    }
    private boolean existeAutorCadastrado(Autor autor) {
        Optional<Autor> autorEncontrado = repository.findByNomeAndDataNascimentoAndNacionalidade(
                autor.getNome(), autor.getDataNascimento(), autor.getNacionalidade());
        if(autor.getId() == null){
            return autorEncontrado.isPresent();
        } return !autor.getId().equals(autorEncontrado.get().getId()) && autorEncontrado.isPresent();
    }

    public void validar(Autor autor) {
        if(existeAutorCadastrado(autor)){
            throw new RegistroDuplicadoException("Autor já cadastrado.");
        }
    }
}
