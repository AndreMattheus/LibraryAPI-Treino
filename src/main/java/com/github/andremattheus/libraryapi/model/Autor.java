package com.github.andremattheus.libraryapi.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "autor", schema = "public")
@Getter
@Setter
@ToString
public class Autor {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "nome", nullable = false, length = 100)
    private String nome;

    @Column(name = "data_nascimento", nullable = false)
    private LocalDate dataNascimento;

    @Column(name = "nacionalidade", nullable = false, length = 50)
    private String nacionalidade;

    //@OneToMany(mappedBy = "autor")
    @Transient
    private List<Livro> livros;

//    Construtor vazio para ser usado pelo framework para instanciar
//    @Deprecated
//    public Autor() {
//    Ao não ser criado um construtor, o Java automaticamente identifica commo um construtor vazio existente
//    }
}