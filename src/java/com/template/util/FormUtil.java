package com.template.util;

import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputControl;

public class FormUtil {

    public static String[] extrairCampos(TextField... campos) {
        String[] valores = new String[campos.length];
        for (int i = 0; i < campos.length; i++) {
            valores[i] = (campos[i] != null && campos[i].getText() != null)
                    ? campos[i].getText().trim()
                    : "";
        }
        return valores;
    }

    public static void limparCampos(TextInputControl... campos) {
        for (TextInputControl campo : campos) {
            if (campo != null) {
                campo.clear();
            }
        }
    }

    public static void limparCampos(TableView<?> tabela, TextInputControl... campos) {
        if (tabela != null) {
            tabela.getSelectionModel().clearSelection();
        }
        limparCampos(campos);
    }

    public static Double extrairDouble(TextField campo) {
        if (campo == null || campo.getText() == null || campo.getText().trim().isEmpty()) {
            return 0.0;
        }
        try {
            return Double.parseDouble(campo.getText().replace(",", ".").trim());
        } catch (NumberFormatException e) {
            return null;
        }
    }
}