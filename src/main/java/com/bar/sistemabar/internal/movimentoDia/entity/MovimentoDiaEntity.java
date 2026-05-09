package com.bar.sistemabar.internal.movimentoDia.entity;

import com.bar.sistemabar.internal.usuario.entity.UsuarioEntity;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "TB_MOVIMENTO_DIA")
public class MovimentoDiaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate dataMovimento;

    private LocalDateTime dataHoraAbertura;

    private LocalDateTime dataHoraFechamento;

    private BigDecimal trocoInicial;

    @Enumerated(EnumType.STRING)
    private StatusMovimentoDia status;

    @ManyToOne
    @JoinColumn(name = "usuario_responsavel_id")
    private UsuarioEntity usuarioResponsavel;

    public MovimentoDiaEntity() {
    }

    public MovimentoDiaEntity(LocalDate dataMovimento, LocalDateTime dataHoraAbertura, BigDecimal trocoInicial, StatusMovimentoDia status, UsuarioEntity usuarioResponsavel) {
        this.dataMovimento = dataMovimento;
        this.dataHoraAbertura = dataHoraAbertura;
        this.trocoInicial = trocoInicial;
        this.status = status;
        this.usuarioResponsavel = usuarioResponsavel;
    }

    public Long getId() {
        return id;
    }

    public LocalDate getDataMovimento() {
        return dataMovimento;
    }

    public void setDataMovimento(LocalDate dataMovimento) {
        this.dataMovimento = dataMovimento;
    }

    public LocalDateTime getDataHoraAbertura() {
        return dataHoraAbertura;
    }

    public void setDataHoraAbertura(LocalDateTime dataHoraAbertura) {
        this.dataHoraAbertura = dataHoraAbertura;
    }

    public LocalDateTime getDataHoraFechamento() {
        return dataHoraFechamento;
    }

    public void setDataHoraFechamento(LocalDateTime dataHoraFechamento) {
        this.dataHoraFechamento = dataHoraFechamento;
    }

    public BigDecimal getTrocoInicial() {
        return trocoInicial;
    }

    public void setTrocoInicial(BigDecimal trocoInicial) {
        this.trocoInicial = trocoInicial;
    }

    public StatusMovimentoDia getStatus() {
        return status;
    }

    public void setStatus(StatusMovimentoDia status) {
        this.status = status;
    }

    public UsuarioEntity getUsuarioResponsavel() {
        return usuarioResponsavel;
    }

    public void setUsuarioResponsavel(UsuarioEntity usuarioResponsavel) {
        this.usuarioResponsavel = usuarioResponsavel;
    }
}