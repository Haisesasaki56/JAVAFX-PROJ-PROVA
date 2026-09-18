package com.template.util;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class DialogUtil {

    public static void mostrarAlerta(String titulo, String cabecalho, String mensagem, AlertType tipo) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(cabecalho);
        alerta.setContentText(mensagem);
        alerta.showAndWait();
    }

    public static void mostrarSucesso(String mensagem) {
        mostrarAlerta("Sucesso", null, mensagem, AlertType.INFORMATION);
    }

    public static void mostrarErro(String titulo, String mensagem) {
        mostrarAlerta(titulo, null, mensagem, AlertType.ERROR);
    }

    public static void exibirInformacao(String titulo, String mensagem) {
        mostrarAlerta(titulo, null, mensagem, AlertType.INFORMATION);
    }

    public static void exibirErro(String titulo, String mensagem) {
        mostrarAlerta(titulo, null, mensagem, AlertType.ERROR);
    }
}