package com.template.validator;

import static com.template.util.DialogUtil.*;
import javafx.scene.control.Alert.AlertType;

public class AlimentoValidador implements IAlimentoValidador {

    @Override
    public boolean validarCamposAlimento(String nome, String calorias) {
        if (nome == null || nome.trim().isEmpty()) {
            mostrarAlerta("Erro de Validação", "Campo Obrigatório",
                    "O nome do alimento é obrigatório.", AlertType.WARNING);
            return false;
        }

        if (calorias == null || calorias.trim().isEmpty()) {
            mostrarAlerta("Erro de Validação", "Campo Obrigatório",
                    "O campo de calorias é obrigatório.", AlertType.WARNING);
            return false;
        }

        try {
            double cal = Double.parseDouble(calorias.replace(",", "."));
            if (cal < 0) {
                mostrarAlerta("Erro de Validação", "Valor Inválido",
                        "As calorias não podem ter valor negativo.", AlertType.WARNING);
                return false;
            }
        } catch (NumberFormatException e) {
            mostrarAlerta("Erro de Validação", "Formato Inválido",
                    "As calorias devem ser um número válido.", AlertType.ERROR);
            return false;
        }

        return true;
    }
}