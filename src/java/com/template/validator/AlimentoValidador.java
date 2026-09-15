package com.template.validator;

import com.template.model.dto.AlimentoDTO;

public class AlimentoValidador {

    public static void validar(AlimentoDTO dto, String caloriasTexto) {
        if (dto == null) {
            throw new IllegalArgumentException("O objeto alimento não pode ser nulo.");
        }

        if (dto.getNome() == null || dto.getNome().trim().isEmpty()) {
            throw new IllegalArgumentException("O nome do alimento é obrigatório.");
        }

        try {
            double cal = Double.parseDouble(caloriasTexto);
            if (cal < 0) {
                throw new IllegalArgumentException("As calorias não podem ter valor negativo.");
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("As calorias devem ser um número válido.");
        }

        if (dto.getProteinas() != null && dto.getProteinas() < 0) {
            throw new IllegalArgumentException("Proteínas não podem ter valor negativo.");
        }

        if (dto.getCarboidratos() != null && dto.getCarboidratos() < 0) {
            throw new IllegalArgumentException("Carboidratos não podem ter valor negativo.");
        }

        if (dto.getGorduras() != null && dto.getGorduras() < 0) {
            throw new IllegalArgumentException("Gorduras não podem ter valor negativo.");
        }

        double proteinas = dto.getProteinas() != null ? dto.getProteinas() : 0.0;
        double carboidratos = dto.getCarboidratos() != null ? dto.getCarboidratos() : 0.0;
        double gorduras = dto.getGorduras() != null ? dto.getGorduras() : 0.0;

        double totalMacros = proteinas + carboidratos + gorduras;
        if (totalMacros > 100) {
            throw new IllegalArgumentException("A soma dos macronutrientes não pode ultrapassar 100g.");
        }
    }
}