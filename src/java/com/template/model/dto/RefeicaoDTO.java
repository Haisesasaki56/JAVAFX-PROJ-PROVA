package com.template.model.dto;

public class RefeicaoDTO {
    private int id;
    private String nome;
    private String horario;

    public RefeicaoDTO() {}

    public RefeicaoDTO(int id, String nome, String horario) {
        this.id = id;
        this.nome = nome;
        this.horario = horario;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getHorario() { return horario; }
    public void setHorario(String horario) { this.horario = horario; }
}