package com.template.util;

import javafx.scene.control.TextField;
import javafx.scene.control.TextInputControl;

public class FormUtil {

    public static void limparCampos(TextInputControl... campos) {
        for (TextInputControl campo : campos) {
            campo.clear();
        }
    }

    public static Double extrairDouble(TextField campo) {
        if (campo.getText() == null || campo.getText().trim().isEmpty()) {
            return 0.0;
        }
        try {
            return Double.parseDouble(campo.getText().replace(",", "."));
        } catch (NumberFormatException e) {
            return null;
        }
    }
}