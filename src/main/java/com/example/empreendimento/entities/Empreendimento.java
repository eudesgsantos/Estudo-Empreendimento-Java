package com.example.empreendimento.entities;

import javax.persistence.*;
import java.util.ArrayList;

@Entity
public class Empreendimento {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private long id;
    @Column(length = 12, nullable = false)
    private String codigo;
    @Column(length = 50, nullable = false)
    private String descricao;
    @Column(nullable = false)
    private float taxa;
    @Column(nullable = false)

    private float aliquota;

    public static ArrayList<String> getFields(){
        ArrayList<String> result = new ArrayList<String>();
        result.add("id");
        result.add("codigo");
        result.add("descricao");
        result.add("taxa");
        result.add("aliquota");
        return result;
    }

    public void setId(long id) {
        this.id = id;
    }
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void setTaxa(float taxa) {
        this.taxa = taxa;
    }

    public void setAliquota(float aliquota) {
        this.aliquota = aliquota;
    }

    public long getId() {
        return id;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public float getTaxa() {
        return taxa;
    }

    public float getAliquota() {
        return aliquota;
    }
}

