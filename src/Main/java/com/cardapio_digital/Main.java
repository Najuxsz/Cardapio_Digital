package com.cardapio_digital;

import com.cardapio_digital.view.telas.InicioView;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        //A MainApp chama apenas a View
        InicioView inicio = new InicioView();
        inicio.start(primaryStage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
