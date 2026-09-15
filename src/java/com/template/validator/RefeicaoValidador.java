package com.template.validator;

public class RefeicaoValidador {

    public static void validar(String nome, String horario) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("O nome da refeição é obrigatório!");
        }
        if (horario == null || horario.trim().isEmpty()) {
            throw new IllegalArgumentException("O horário da refeição é obrigatório!");
        }
    }
}