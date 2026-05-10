package com.bar.sistemabar.internal.fiado.entity;

import com.bar.sistemabar.internal.movimentoDia.entity.MovimentoDiaEntity;
import com.bar.sistemabar.internal.produto.entity.ProdutoEntity;
import com.bar.sistemabar.internal.usuario.entity.UsuarioEntity;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "TB_REGISTRO_FIADO")
public class RegistroFiadoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer quantidade;

    private Double valor;

    private LocalDateTime dataHora;

    private String observacao;

    @Enumerated(EnumType.STRING)
    private StatusFiado status;

    @ManyToOne
    @JoinColumn(name = "pessoa_fiado_id")
    private PessoaFiadoEntity pessoaFiado;

    @ManyToOne
    @JoinColumn(name = "produto_id")
    private ProdutoEntity produto;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private UsuarioEntity usuario;

    @ManyToOne
    @JoinColumn(name = "movimento_dia_id")
    private MovimentoDiaEntity movimentoDia;

    public RegistroFiadoEntity() {
    }

    public RegistroFiadoEntity(Integer quantidade,
                               Double valor,
                               LocalDateTime dataHora,
                               String observacao,
                               StatusFiado status,
                               PessoaFiadoEntity pessoaFiado,
                               ProdutoEntity produto,
                               UsuarioEntity usuario,
                               MovimentoDiaEntity movimentoDia) {
        this.quantidade = quantidade;
        this.valor = valor;
        this.dataHora = dataHora;
        this.observacao = observacao;
        this.status = status;
        this.pessoaFiado = pessoaFiado;
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

    public Double getValor() {
        return valor;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public String getObservacao() {
        return observacao;
    }

    public StatusFiado getStatus() {
        return status;
    }

    public PessoaFiadoEntity getPessoaFiado() {
        return pessoaFiado;
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

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public void setStatus(StatusFiado status) {
        this.status = status;
    }

    public void setPessoaFiado(PessoaFiadoEntity pessoaFiado) {
        this.pessoaFiado = pessoaFiado;
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
