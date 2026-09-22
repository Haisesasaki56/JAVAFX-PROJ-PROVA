package com.template.validator;

import java.util.ArrayList;
import java.util.List;

public class RefeicaoValidador implements IRefeicaoValidador {

    @Override
    public void validarCamposRefeicao(String nome, String horario) {
        // Lista de validadores que serão aplicados sequencialmente
        List<Validador<String>> validadores = new ArrayList<>();

        // 1. Adicionando validadores de campos obrigatórios
        validadores.add(new CampoObrigatorioValidador("Nome da Refeição", nome));
        validadores.add(new CampoObrigatorioValidador("Horário", horario));

        // 2. Adicionando validação para impedir números no nome da refeição
        validadores.add(new TextoSemNumeroValidador("Nome da Refeição", nome));

        // Itera sobre a lista de validadores
        for (Validador<String> validador : validadores) {
            if (!validador.validar(validador.getValor())) {
                throw new IllegalArgumentException(validador.getMensagemErro());
            }
        }
    }
}