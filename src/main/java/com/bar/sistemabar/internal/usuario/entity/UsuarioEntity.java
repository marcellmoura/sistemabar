package com.bar.sistemabar.internal.usuario.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "TB_USUARIO")
public class UsuarioEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "NOME", nullable = false)
    private String nome;

    @Column(name = "EMAIL", nullable = false, unique = true)
    private String email;

    @Column(name = "SENHA", nullable = false)
    private String senha;

    @Column(name = "PERFIL", nullable = false)
    private String perfil;

    @Column(name = "STATUS", nullable = false)
    private String status;

    public UsuarioEntity() {
    }

    public UsuarioEntity(Long id, String nome, String email, String senha, String perfil, String status) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.perfil = perfil;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public String getSenha() {
        return senha;
    }

    public String getPerfil() {
        return perfil;
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

    public void setEmail(String email) {
        this.email = email;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public void setPerfil(String perfil) {
        this.perfil = perfil;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}