package com.template.model.dto;

public class AlimentoDTO {

    private Integer id;
    private String nome;
    private Double calorias;
    private Double proteinas;
    private Double carboidratos;
    private Double gorduras;
    private Boolean natural;

    public AlimentoDTO() {
    }

    public AlimentoDTO(Integer id, String nome, Double calorias, Boolean natural) {
        this.id = id;
        this.nome = nome;
        this.calorias = calorias;
        this.natural = natural;
    }

    public AlimentoDTO(Integer id, String nome, Double calorias, Double proteinas, Double carboidratos, Double gorduras, Boolean natural) {
        this.id = id;
        this.nome = nome;
        this.calorias = calorias;
        this.proteinas = proteinas;
        this.carboidratos = carboidratos;
        this.gorduras = gorduras;
        this.natural = natural;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Double getCalorias() {
        return calorias;
    }

    public void setCalorias(Double calorias) {
        this.calorias = calorias;
    }

    public Double getProteinas() {
        return proteinas;
    }

    public void setProteinas(Double proteinas) {
        this.proteinas = proteinas;
    }

    public Double getCarboidratos() {
        return carboidratos;
    }

    public void setCarboidratos(Double carboidratos) {
        this.carboidratos = carboidratos;
    }

    public Double getGorduras() {
        return gorduras;
    }

    public void setGorduras(Double gorduras) {
        this.gorduras = gorduras;
    }

    public Boolean getNatural() {
        return natural;
    }

    public Boolean isNatural() {
        return natural;
    }

    public void setNatural(Boolean natural) {
        this.natural = natural;
    }
}