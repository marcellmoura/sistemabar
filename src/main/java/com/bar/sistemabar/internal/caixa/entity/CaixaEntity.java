package com.bar.sistemabar.internal.caixa.entity;

import com.bar.sistemabar.internal.movimentoDia.entity.MovimentoDiaEntity;
import com.bar.sistemabar.internal.usuario.entity.UsuarioEntity;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "TB_CAIXA")
public class CaixaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double valorVendidoCalculado;

    private Double valorFiadoAberto;

    private Double valorFiadoPago;

    private Double valorTotalFiado;

    private Double valorRecebidoEstimado;

    private Double valorInformado;

    private Double diferenca;

    private LocalDateTime dataHoraConferencia;

    private String observacao;

    @Enumerated(EnumType.STRING)
    private StatusCaixa status;

    @OneToOne
    @JoinColumn(name = "movimento_dia_id")
    private MovimentoDiaEntity movimentoDia;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private UsuarioEntity usuario;

    public CaixaEntity() {
    }

    public Long getId() {
        return id;
    }

    public Double getValorVendidoCalculado() {
        return valorVendidoCalculado;
    }

    public void setValorVendidoCalculado(Double valorVendidoCalculado) {
        this.valorVendidoCalculado = valorVendidoCalculado;
    }

    public Double getValorFiadoAberto() {
        return valorFiadoAberto;
    }

    public void setValorFiadoAberto(Double valorFiadoAberto) {
        this.valorFiadoAberto = valorFiadoAberto;
    }

    public Double getValorFiadoPago() {
        return valorFiadoPago;
    }

    public void setValorFiadoPago(Double valorFiadoPago) {
        this.valorFiadoPago = valorFiadoPago;
    }

    public Double getValorTotalFiado() {
        return valorTotalFiado;
    }

    public void setValorTotalFiado(Double valorTotalFiado) {
        this.valorTotalFiado = valorTotalFiado;
    }

    public Double getValorRecebidoEstimado() {
        return valorRecebidoEstimado;
    }

    public void setValorRecebidoEstimado(Double valorRecebidoEstimado) {
        this.valorRecebidoEstimado = valorRecebidoEstimado;
    }

    public Double getValorInformado() {
        return valorInformado;
    }

    public void setValorInformado(Double valorInformado) {
        this.valorInformado = valorInformado;
    }

    public Double getDiferenca() {
        return diferenca;
    }

    public void setDiferenca(Double diferenca) {
        this.diferenca = diferenca;
    }

    public LocalDateTime getDataHoraConferencia() {
        return dataHoraConferencia;
    }

    public void setDataHoraConferencia(LocalDateTime dataHoraConferencia) {
        this.dataHoraConferencia = dataHoraConferencia;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public StatusCaixa getStatus() {
        return status;
    }

    public void setStatus(StatusCaixa status) {
        this.status = status;
    }

    public MovimentoDiaEntity getMovimentoDia() {
        return movimentoDia;
    }

    public void setMovimentoDia(MovimentoDiaEntity movimentoDia) {
        this.movimentoDia = movimentoDia;
    }

    public UsuarioEntity getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioEntity usuario) {
        this.usuario = usuario;
    }
}
