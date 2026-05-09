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
    private String descricao;
    private Double preco;
    private Boolean controlaEstoque;
    private Integer quantidadeEstoque;

    @Enumerated(EnumType.STRING)
    private TipoLancamentoProduto tipoLancamento;

    @Enumerated(EnumType.STRING)
    private StatusProduto status;

    @ManyToOne
    @JoinColumn(name = "categoria_id")
    private CategoriaEntity categoria;

    public ProdutoEntity() {
    }

    public ProdutoEntity(String nome, String descricao, Double preco, Boolean controlaEstoque,
                         Integer quantidadeEstoque, TipoLancamentoProduto tipoLancamento,
                         StatusProduto status, CategoriaEntity categoria) {
        this.nome = nome;
        this.descricao = descricao;
        this.preco = preco;
        this.controlaEstoque = controlaEstoque;
        this.quantidadeEstoque = quantidadeEstoque;
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

    public String getDescricao() {
        return descricao;
    }

    public Double getPreco() {
        return preco;
    }

    public Boolean getControlaEstoque() {
        return controlaEstoque;
    }

    public Integer getQuantidadeEstoque() {
        return quantidadeEstoque;
    }

    public TipoLancamentoProduto getTipoLancamento() {
        return tipoLancamento;
    }

    public StatusProduto getStatus() {
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

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void setPreco(Double preco) {
        this.preco = preco;
    }

    public void setControlaEstoque(Boolean controlaEstoque) {
        this.controlaEstoque = controlaEstoque;
    }

    public void setQuantidadeEstoque(Integer quantidadeEstoque) {
        this.quantidadeEstoque = quantidadeEstoque;
    }

    public void setTipoLancamento(TipoLancamentoProduto tipoLancamento) {
        this.tipoLancamento = tipoLancamento;
    }

    public void setStatus(StatusProduto status) {
        this.status = status;
    }

    public void setCategoria(CategoriaEntity categoria) {
        this.categoria = categoria;
    }
}