package com.bar.sistemabar.internal.saidaProduto.entity;

import com.bar.sistemabar.internal.movimentoDia.entity.MovimentoDiaEntity;
import com.bar.sistemabar.internal.produto.entity.ProdutoEntity;
import com.bar.sistemabar.internal.usuario.entity.UsuarioEntity;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "TB_SAIDA_PRODUTO")
public class SaidaProdutoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer quantidade;

    @Enumerated(EnumType.STRING)
    private TipoSaidaProduto tipoSaida;

    private LocalDateTime dataHora;

    @ManyToOne
    @JoinColumn(name = "produto_id")
    private ProdutoEntity produto;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private UsuarioEntity usuario;

    @ManyToOne
    @JoinColumn(name = "movimento_dia_id")
    private MovimentoDiaEntity movimentoDia;

    public SaidaProdutoEntity() {
    }

    public SaidaProdutoEntity(Integer quantidade,
                              TipoSaidaProduto tipoSaida,
                              LocalDateTime dataHora,
                              ProdutoEntity produto,
                              UsuarioEntity usuario,
                              MovimentoDiaEntity movimentoDia) {
        this.quantidade = quantidade;
        this.tipoSaida = tipoSaida;
        this.dataHora = dataHora;
        this.produto = produto;
        this.usuario = usuario;
        this.movimentoDia = movimentoDia;
    }

    public Long getId() {
        return id;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public TipoSaidaProduto getTipoSaida() {
        return tipoSaida;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public ProdutoEntity getProduto() {
        return produto;
    }

    public UsuarioEntity getUsuario() {
        return usuario;
    }

    public MovimentoDiaEntity getMovimentoDia() {
        return movimentoDia;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public void setTipoSaida(TipoSaidaProduto tipoSaida) {
        this.tipoSaida = tipoSaida;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }

    public void setProduto(ProdutoEntity produto) {
        this.produto = produto;
    }

    public void setUsuario(UsuarioEntity usuario) {
        this.usuario = usuario;
    }

    public void setMovimentoDia(MovimentoDiaEntity movimentoDia) {
        this.movimentoDia = movimentoDia;
    }
}