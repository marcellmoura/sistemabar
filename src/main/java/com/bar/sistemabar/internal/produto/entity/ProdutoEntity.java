package com.bar.sistemabar.internal.produto.entity;

import com.bar.sistemabar.internal.categoria.entity.CategoriaEntity;
import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "TB_PRODUTO")
public class ProdutoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "NOME", nullable = false)
    private String nome;

    @Column(name = "DESCRICAO")
    private String descricao;

    @Column(name = "PRECO", nullable = false)
    private BigDecimal preco;

    @Column(name = "CONTROLA_ESTOQUE", nullable = false)
    private Boolean controlaEstoque;

    @Column(name = "TIPO_LANCAMENTO", nullable = false)
    private String tipoLancamento;

    @Column(name = "STATUS", nullable = false)
    private String status;

    @ManyToOne
    @JoinColumn(name = "CATEGORIA_ID")
    private CategoriaEntity categoria;

    public ProdutoEntity() {
    }

    public ProdutoEntity(Long id, String nome, String descricao, BigDecimal preco,
                         Boolean controlaEstoque, String tipoLancamento,
                         String status, CategoriaEntity categoria) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
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

    public String getDescricao() {
        return descricao;
    }

    public BigDecimal getPreco() {
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

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void setPreco(BigDecimal preco) {
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