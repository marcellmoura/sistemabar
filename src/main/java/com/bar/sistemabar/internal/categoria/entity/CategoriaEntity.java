package com.bar.sistemabar.internal.categoria.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "TB_CATEGORIA")
public class CategoriaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "NOME", nullable = false, unique = true)
    private String nome;

    @Column(name = "STATUS", nullable = false)
    private String status;

    public CategoriaEntity() {
    }

    public CategoriaEntity(Long id, String nome, String status) {
        this.id = id;
        this.nome = nome;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getStatus() {
        return status;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}