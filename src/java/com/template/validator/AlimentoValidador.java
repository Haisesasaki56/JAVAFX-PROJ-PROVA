package com.template.validator;

public class AlimentoValidador {

    public static boolean validarCampos(String alimento, String caloriasStr) {
        if (alimento == null || alimento.trim().isEmpty()) {
            return false;
        }
        if (caloriasStr == null || caloriasStr.trim().isEmpty()) {
            return false;
        }
        try {
            Double.parseDouble(caloriasStr);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}