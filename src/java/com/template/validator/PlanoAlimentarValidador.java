package com.template.validator;

public class PlanoAlimentarValidador {

    public static void validar(String paciente, String metaCalorias) {
        if (paciente == null || paciente.trim().isEmpty()) {
            throw new IllegalArgumentException("O nome do paciente é obrigatório!");
        }
        try {
            double meta = Double.parseDouble(metaCalorias);
            if (meta <= 0) {
                throw new IllegalArgumentException("A meta de calorias deve ser maior que zero!");
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("A meta de calorias deve ser um número válido!");
        }
    }
}