package com.bar.sistemabar.internal.produto.entity;

import com.bar.sistemabar.internal.categoria.entity.CategoriaEntity;
import jakarta.persistence.*;

@Entity
@Table(name = "TB_PRODUTO")
public class ProdutoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private Double preco;

    private Boolean controlaEstoque;

    private String tipoLancamento;

    private String status;

    @ManyToOne
    @JoinColumn(name = "categoria_id")
    private CategoriaEntity categoria;

    public ProdutoEntity() {
    }

    public ProdutoEntity(String nome, Double preco, Boolean controlaEstoque, String tipoLancamento, String status, CategoriaEntity categoria) {
        this.nome = nome;
        this.preco = preco;
        this.controlaEstoque = controlaEstoque;
        this.tipoLancamento = tipoLancamento;
        this.status = status;
        this.categoria = categoria;
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public Double getPreco() {
        return preco;
    }

    public Boolean getControlaEstoque() {
        return controlaEstoque;
    }

    public String getTipoLancamento() {
        return tipoLancamento;
    }

    public String getStatus() {
        return status;
    }

    public CategoriaEntity getCategoria() {
        return categoria;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setPreco(Double preco) {
        this.preco = preco;
    }

    public void setControlaEstoque(Boolean controlaEstoque) {
        this.controlaEstoque = controlaEstoque;
    }

    public void setTipoLancamento(String tipoLancamento) {
        this.tipoLancamento = tipoLancamento;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setCategoria(CategoriaEntity categoria) {
        this.categoria = categoria;
    }
}