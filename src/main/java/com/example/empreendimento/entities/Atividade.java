package com.example.empreendimento.entities;

import javax.persistence.*;

@Entity
public class Atividade {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private long id;

    @Column(length = 50, nullable = false)
    private long empreendimentoId;

    @Column(nullable = false)
    private float valorAtividade;

    @Column(nullable = false)
    private Integer prazo;

    @Column(nullable = false)
    private float valorSeguro;

    @Column(nullable = false)
    private float montante;

    private long creditoId;

    public void setEmpreendimentoId(long empreendimentoId) {
        this.empreendimentoId = empreendimentoId;
    }

    public void setCreditoId(long creditoId) {
        this.creditoId = creditoId;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setValorAtividade(float valorAtividade) {
        this.valorAtividade = valorAtividade;
    }

    public void setPrazo(Integer prazo) {
        this.prazo = prazo;
    }

    public void setValorSeguro(float valorSeguro) {
        this.valorSeguro = valorSeguro;
    }

    public void setMontante(float montante) {
        this.montante = montante;
    }

    public long getId() {
        return id;
    }

    public long getEmpreendimentoId() {
        return empreendimentoId;
    }

    public long getCreditoId() {
        return creditoId;
    }

    public float getValorAtividade() {
        return valorAtividade;
    }

    public Integer getPrazo() {
        return prazo;
    }

    public float getValorSeguro() {
        return valorSeguro;
    }

    public float getMontante() {
        return montante;
    }
}
