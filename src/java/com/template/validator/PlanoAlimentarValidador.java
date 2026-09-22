package com.template.validator;

import java.util.ArrayList;
import java.util.List;

public class PlanoAlimentarValidador implements IPlanoAlimentarValidador {

    @Override
    public void validarCamposPlanoAlimentar(String paciente, String metaCalorias) {
        // Lista de validadores que serão aplicados sequencialmente
        List<Validador<String>> validadores = new ArrayList<>();

        // 1. Adicionando validadores de campos obrigatórios
        validadores.add(new CampoObrigatorioValidador("Paciente", paciente));
        validadores.add(new CampoObrigatorioValidador("Meta de Calorias", metaCalorias));

        // 2. Adicionando validações de formato
        validadores.add(new TextoSemNumeroValidador("Paciente", paciente));
        validadores.add(new ApenasNumeroValidador("Meta de Calorias", metaCalorias));

        // Itera sobre a lista de validadores
        for (Validador<String> validador : validadores) {
            if (!validador.validar(validador.getValor())) {
                throw new IllegalArgumentException(validador.getMensagemErro());
            }
        }
    }
}