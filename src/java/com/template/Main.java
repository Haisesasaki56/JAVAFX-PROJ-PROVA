package com.template;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.InputStream;

/**
 * Classe principal de inicialização da Aplicação JavaFX.
 */
public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/template/main.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 900, 600);

        primaryStage.setTitle("Nutritional CRUD - Gerenciador Nutricional");

        // Carrega o ícone caso exista na pasta de recursos
        InputStream iconStream = getClass().getResourceAsStream("/com/template/icon.png");
        if (iconStream != null) {
            primaryStage.getIcons().add(new Image(iconStream));
        }

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}