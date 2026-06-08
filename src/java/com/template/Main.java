package com.template;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image; // Importado para gerenciar o ícone
import javafx.stage.Stage;

public class Main extends Application
{
    @Override
    public void start(Stage stage) throws Exception
    {
        // Carrega o arquivo FXML
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("main.fxml"));

        // Cria a cena com o tamanho real definido no seu FXML
        Scene scene = new Scene(loader.load());

        stage.setTitle("Tabela Nutricional");

        // CORREÇÃO AQUI: Aplica o ícone diretamente na barra de título do sistema operacional
        stage.getIcons().add(new Image(Main.class.getResourceAsStream("icon.png")));

        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args)
    {
        // Inicia a aplicação passando os argumentos
        launch(args);
    }
}