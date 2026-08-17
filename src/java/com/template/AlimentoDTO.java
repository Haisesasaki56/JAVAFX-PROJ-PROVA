package com.template;

public class AlimentoDTO {
    private Integer id;
    private String alimento;
    private Double calorias;
    private boolean natural;

    public AlimentoDTO() {}

    public AlimentoDTO(Integer id, String alimento, Double calorias, boolean natural) {
        this.id = id;
        this.alimento = alimento;
        this.calorias = calorias;
        this.natural = natural;
    }

    // Getters e Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getAlimento() { return alimento; }
    public void setAlimento(String alimento) { this.alimento = alimento; }
    public Double getCalorias() { return calorias; }
    public void setCalorias(Double calorias) { this.calorias = calorias; }
    public boolean isNatural() { return natural; }
    public void setNatural(boolean natural) { this.natural = natural; }
}