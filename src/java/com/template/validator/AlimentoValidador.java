package com.template.validator;

import java.util.ArrayList;
import java.util.List;

import static com.template.util.DialogUtil.mostrarErro;

public class AlimentoValidador implements IAlimentoValidador {

    @Override
    public void validarCamposAlimento(String nome, String calorias) {
        // Lista de validadores que serão aplicados sequencialmente
        List<Validador<String>> validadores = new ArrayList<>();

        // Adicionando os validadores de campos obrigatórios
        validadores.add(new CampoObrigatorioValidador("Nome do Alimento", nome));
        validadores.add(new CampoObrigatorioValidador("Calorias", calorias));

        // Adicionando os validadores específicos de formato
        validadores.add(new TextoSemNumeroValidador("Nome do Alimento", nome));
        validadores.add(new ApenasNumeroValidador("Calorias", calorias));

        // Itera sobre a lista de validadores
        for (Validador<String> validador : validadores) {
            // Cada validador testa seu valor específico
            if (!validador.validar(validador.getValor())) {
                // Lança a exceção na primeira falha de validação
                mostrarErro("Erro",validador.getMensagemErro());
            }
        }
    }
}