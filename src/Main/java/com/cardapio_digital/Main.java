package com.cardapio_digital;

import com.cardapio_digital.view.telas.InicioView;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Inicia a tela inicial do sistema
        InicioView inicio = new InicioView();
        inicio.start(primaryStage);
    }

    public static void main(String[] args) {
        // Inicia a aplicação JavaFX
        launch(args);
    }
}
