package com.template;

import com.template.model.dao.AlimentoDAO;
import com.template.service.AlimentoService;
import com.template.validator.AlimentoValidador;
import com.template.validator.IAlimentoValidador;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.InputStream;
import java.net.URL;
import java.util.Objects;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        // 1. Instancia as dependências (passando dao e validador no service)
        IAlimentoValidador validador = new AlimentoValidador();
        AlimentoDAO dao = new AlimentoDAO();
        AlimentoService service = new AlimentoService(dao, validador);

        // 2. Localiza o arquivo FXML
        URL fxmlUrl = Objects.requireNonNull(
                getClass().getResource("main.fxml"),
                "Arquivo main.fxml não encontrado na pasta com/template!"
        );

        // 3. Configura o FXMLLoader e passa o validador e service para o MainController
        FXMLLoader loader = new FXMLLoader(fxmlUrl);
        loader.setControllerFactory(clazz -> new MainController(validador, service));

        Parent root = loader.load();

        primaryStage.setTitle("Nutritional CRUD");

        try {
            InputStream iconStream = getClass().getResourceAsStream("icon.png");
            if (iconStream != null) {
                primaryStage.getIcons().add(new Image(iconStream));
            }
        } catch (Exception e) {
            System.out.println("Ícone não encontrado, iniciando sem ícone...");
        }

        primaryStage.setScene(new Scene(root, 600, 450));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}