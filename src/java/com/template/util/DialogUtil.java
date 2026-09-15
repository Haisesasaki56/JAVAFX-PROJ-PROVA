package com.template.util;

import javafx.scene.control.Alert;

public class DialogUtil {

    public static void mostrarSucesso(String mensagem) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Sucesso");
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }

    public static void mostrarErro(String titulo, String mensagem) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }

    public static void exibirInformacao(String titulo, String mensagem) {
        mostrarSucesso(mensagem);
    }

    public static void exibirErro(String titulo, String mensagem) {
        mostrarErro(titulo, mensagem);
    }
}