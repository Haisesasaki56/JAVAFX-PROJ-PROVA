package com.template.validator;

public class AlimentoValidador {
    public static void validar(String alimento, String calorias) {
        if (alimento == null || alimento.trim().isEmpty()) {
            throw new IllegalArgumentException("O nome do alimento é obrigatório!");
        }
        try {
            Double.parseDouble(calorias);
        } catch (Exception e) {
            throw new IllegalArgumentException("As calorias devem ser um número válido!");
        }
    }
}