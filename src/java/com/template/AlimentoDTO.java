package com.template;

public class AlimentoDTO {
    private int id;
    private String alimento;
    private double calorias;
    private boolean natural;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getAlimento() { return alimento; }
    public void setAlimento(String alimento) { this.alimento = alimento; }
    public double getCalorias() { return calorias; }
    public void setCalorias(double calorias) { this.calorias = calorias; }
    public boolean isNatural() { return natural; }
    public void setNatural(boolean natural) { this.natural = natural; }
}
