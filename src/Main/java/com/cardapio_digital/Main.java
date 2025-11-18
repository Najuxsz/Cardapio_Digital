package com.cardapio_digital;
import com.cardapio_digital.controller.PratoController;
import com.cardapio_digital.model.HashTablePratos;
import com.cardapio_digital.model.Prato;
import com.cardapio_digital.utils.Ordenadores;
import com.cardapio_digital.view.telas.InicioView;
import javafx.application.Application;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.List;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        InicioView inicio = new InicioView();
        inicio.start(primaryStage);
    }

    public static void main(String[] args) {
        launch(args);

    }
}