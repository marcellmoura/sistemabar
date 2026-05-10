package com.bar.sistemabar.internal.fiado.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "TB_PESSOA_FIADO")
public class PessoaFiadoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private String telefone;

    @Enumerated(EnumType.STRING)
    private StatusPessoaFiado status;

    public PessoaFiadoEntity() {
    }

    public PessoaFiadoEntity(String nome,
                             String telefone,
                             StatusPessoaFiado status) {
        this.nome = nome;
        this.telefone = telefone;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public StatusPessoaFiado getStatus() {
        return status;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public void setStatus(StatusPessoaFiado status) {
        this.status = status;
    }
}
