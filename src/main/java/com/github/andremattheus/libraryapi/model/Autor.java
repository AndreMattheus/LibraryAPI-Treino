package com.github.andremattheus.libraryapi.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "autor", schema = "public")
@Getter
@Setter
@ToString
// Faz escutar a entidade para a auditoria atualizar
@EntityListeners(AuditingEntityListener.class)
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

    /** | Um Para Vários |
     * Significa que pode haver  Um algo (geralmente a classe que está em trabalho (nesse caso autor)
     * para vários <ENTIDADE RELACIONADA> (nesse caso, livros)
     *
     * | mappedBy |
     * Indica o lado da relação que é dono dela
     * Deve indicar o nome da CLASSE e não do campo no banco de dados!
     */
    @OneToMany(mappedBy = "autor")
    //@Transient
    private List<Livro> livros;

    @CreatedDate
    @Column(name = "data_cadastro")
    private LocalDateTime dataCadastro;

    @LastModifiedDate
    @Column(name = "data_atualizacao")
    private LocalDateTime dataAtualizacao;

    @Column(name = "id_usuario")
    private UUID idUsuario;

//    Construtor vazio para ser usado pelo framework para instanciar
//    @Deprecated
//    public Autor() {
//    Ao não ser criado um construtor, o Java automaticamente identifica como um construtor vazio existente
//    }
}