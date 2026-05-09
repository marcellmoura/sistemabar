package com.bar.sistemabar.internal.contagemEstoqueDia.entity;

import com.bar.sistemabar.internal.movimentoDia.entity.MovimentoDiaEntity;
import com.bar.sistemabar.internal.produto.entity.ProdutoEntity;
import jakarta.persistence.*;

@Entity
@Table(name = "TB_CONTAGEM_ESTOQUE_DIA")
public class ContagemEstoqueDiaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer quantidadeInicial;

    private Integer quantidadeFinal;

    private Integer quantidadeVendidaCalculada;

    private Double valorVendidoCalculado;

    @ManyToOne
    @JoinColumn(name = "produto_id")
    private ProdutoEntity produto;

    @ManyToOne
    @JoinColumn(name = "movimento_dia_id")
    private MovimentoDiaEntity movimentoDia;

    public ContagemEstoqueDiaEntity() {
    }

    public ContagemEstoqueDiaEntity(Long id,
                                    Integer quantidadeInicial,
                                    Integer quantidadeFinal,
                                    Integer quantidadeVendidaCalculada,
                                    Double valorVendidoCalculado,
                                    ProdutoEntity produto,
                                    MovimentoDiaEntity movimentoDia) {

        this.id = id;
        this.quantidadeInicial = quantidadeInicial;
        this.quantidadeFinal = quantidadeFinal;
        this.quantidadeVendidaCalculada = quantidadeVendidaCalculada;
        this.valorVendidoCalculado = valorVendidoCalculado;
        this.produto = produto;
        this.movimentoDia = movimentoDia;
    }

    public Long getId() {
        return id;
    }

    public Integer getQuantidadeInicial() {
        return quantidadeInicial;
    }

    public void setQuantidadeInicial(Integer quantidadeInicial) {
        this.quantidadeInicial = quantidadeInicial;
    }

    public Integer getQuantidadeFinal() {
        return quantidadeFinal;
    }

    public void setQuantidadeFinal(Integer quantidadeFinal) {
        this.quantidadeFinal = quantidadeFinal;
    }

    public Integer getQuantidadeVendidaCalculada() {
        return quantidadeVendidaCalculada;
    }

    public void setQuantidadeVendidaCalculada(Integer quantidadeVendidaCalculada) {
        this.quantidadeVendidaCalculada = quantidadeVendidaCalculada;
    }

    public Double getValorVendidoCalculado() {
        return valorVendidoCalculado;
    }

    public void setValorVendidoCalculado(Double valorVendidoCalculado) {
        this.valorVendidoCalculado = valorVendidoCalculado;
    }

    public ProdutoEntity getProduto() {
        return produto;
    }

    public void setProduto(ProdutoEntity produto) {
        this.produto = produto;
    }

    public MovimentoDiaEntity getMovimentoDia() {
        return movimentoDia;
    }

    public void setMovimentoDia(MovimentoDiaEntity movimentoDia) {
        this.movimentoDia = movimentoDia;
    }
}