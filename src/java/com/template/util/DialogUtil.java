package com.template.util;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class DialogUtil {
    public static void showInfo(String msg) {
        new Alert(Alert.AlertType.INFORMATION, msg).showAndWait();
    }
    public static void showError(String msg) {
        new Alert(Alert.AlertType.ERROR, msg).showAndWait();
    }
    public static boolean confirm(String msg) {
        return new Alert(Alert.AlertType.CONFIRMATION, msg, ButtonType.YES, ButtonType.NO)
                .showAndWait().get() == ButtonType.YES;
    }
}