package com.github.andremattheus.libraryapi.controller;

import com.github.andremattheus.libraryapi.controller.dto.AutorDTO;
import com.github.andremattheus.libraryapi.controller.dto.ErroResposta;
import com.github.andremattheus.libraryapi.controller.mappers.AutorMapper;
import com.github.andremattheus.libraryapi.exceptions.OperacaoNaoPermitidaException;
import com.github.andremattheus.libraryapi.exceptions.RegistroDuplicadoException;
import com.github.andremattheus.libraryapi.model.Autor;
import com.github.andremattheus.libraryapi.service.AutorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
// http://localhost:8080/autores
@RequestMapping("/autores")
// Anotação para o Lombok criar o construtor de "private FINAL" automaticamente
@RequiredArgsConstructor
public class AutorController {

    private final AutorService service;

    // Não mais necessário o construtor pois o Lombok faz a criação automatica
//    public AutorController(AutorService service) {
//        this.service = service;
//    }

    @PostMapping
    public ResponseEntity<Object> salvar(@RequestBody @Valid AutorDTO dto) {
        try {
            Autor autor = dto.mapearParaAutor();

            service.salvar(autor);


            // http://localhost:8080/autores/e6b81cb1-d045-450f-a846-0492dd9044f0
            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(autor.getId())
                    .toUri();

            return ResponseEntity.created(location).build();
            //new ResponseEntity("Autor salvo com sucesso! "+ dto, HttpStatus.CREATED);

            // Recebe o Exception(Erro) e manda como resposta a tratativa desse erro (Código e Mensagem)
        } catch (RegistroDuplicadoException e){
            var erroDTO = ErroResposta.conflito(e.getMessage());
            return ResponseEntity.status(erroDTO.status()).body(erroDTO);
        }
    }
    @GetMapping("/{id}")
    public ResponseEntity<AutorDTO> obterDetalhes(@PathVariable("id") String id){
        var idAutor = UUID.fromString(id);

        Optional<Autor> autorOptional = service.obterPorId(idAutor);

        if(autorOptional.isPresent()){
            Autor autor = autorOptional.get();
            // Forma de mapear sem o MapStruct
            AutorDTO dto = new AutorDTO(autor.getId(), autor.getNome(), autor.getDataNascimento(), autor.getNacionalidade());

            return ResponseEntity.ok(dto);
        } return ResponseEntity.notFound().build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Object> deletar(@PathVariable("id") String id){
        try {
            var idAutor = UUID.fromString(id);
            Optional<Autor> autorOptional = service.obterPorId(idAutor);

            if (autorOptional.isEmpty()) {
                return ResponseEntity.notFound().build();
            } else {
                service.deletar(autorOptional.get());
                return ResponseEntity.noContent().build();
            }
        } catch (OperacaoNaoPermitidaException e){
            var erroResposta = ErroResposta.respostaPadrao(e.getMessage());
            return ResponseEntity.status(erroResposta.status()).body(erroResposta);
        }

    }

    @GetMapping
    public ResponseEntity<List<AutorDTO>> pesquisar(@RequestParam(value = "nome", required = false) String nome,
                                                    @RequestParam(value = "nacionalidade", required = false) String nacionalidade){
        List<Autor> resultadoDaPesquisa = service.pesquisaByExample(nome, nacionalidade);
        List<AutorDTO> lista = resultadoDaPesquisa.stream()
                .map(autor -> new AutorDTO(autor.getId(),
                autor.getNome(),
                autor.getDataNascimento(),
                autor.getNacionalidade())
        ).collect(Collectors.toList());
        return ResponseEntity.ok(lista);
    }

    @PutMapping("{id}")
    public ResponseEntity<Object> atualizar(@PathVariable("id") String id,@RequestBody @Valid AutorDTO dto){
        try {
            var idAutor = UUID.fromString(id);
            Optional<Autor> autorOptional = service.obterPorId(idAutor);
            if (autorOptional.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            var autor = autorOptional.get();
            autor.setNome(dto.nome());
            autor.setNacionalidade(dto.nacionalidade());
            autor.setDataNascimento(dto.dataNascimento());

            service.atualizar(autor);

            return ResponseEntity.noContent().build();
        } catch (RegistroDuplicadoException e){
            var erroDTO = ErroResposta.conflito(e.getMessage());
            return ResponseEntity.status(erroDTO.status()).body(erroDTO);
        }
    }

}