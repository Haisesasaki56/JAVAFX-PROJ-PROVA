package com.template.model.dto;

public class PlanoAlimentarDTO {
    private int id;
    private String paciente;
    private double metaCalorias;

    public PlanoAlimentarDTO() {}

    public PlanoAlimentarDTO(int id, String paciente, double metaCalorias) {
        this.id = id;
        this.paciente = paciente;
        this.metaCalorias = metaCalorias;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getPaciente() { return paciente; }
    public void setPaciente(String paciente) { this.paciente = paciente; }

    public double getMetaCalorias() { return metaCalorias; }
    public void setMetaCalorias(double metaCalorias) { this.metaCalorias = metaCalorias; }
}
