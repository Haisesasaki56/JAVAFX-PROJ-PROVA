package com.template;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application
{
    @Override
    public void start(Stage stage) throws Exception
    {
        // Carrega o arquivo FXML
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("main.fxml"));

        // CORREÇÃO: Removemos o ", 600, 400" para o JavaFX usar o tamanho real do FXML (747x560)
        Scene scene = new Scene(loader.load());

        stage.setTitle("Tabela Nutricional"); // Aproveitei para dar um título melhor que "Hello!"
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args)
    {
        // Inicia a aplicação passando os argumentos
        launch(args);
    }
}