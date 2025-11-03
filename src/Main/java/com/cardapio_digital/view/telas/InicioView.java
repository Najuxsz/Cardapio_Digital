package com.cardapio_digital.view;

import com.cardapio_digital.view.telas.CardapioView;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class InicioView {

    public void start(Stage stage) {
        // Título grande
        Label titulo = new Label("Bem-vindo ao Cardápio Digital");
        titulo.setFont(Font.font("Arial", 36));
        titulo.setTextFill(Color.web("#4E342E"));
        titulo.setEffect(new DropShadow(3, Color.GRAY));

        // Introdução pequena
        Label subtitulo = new Label("Explore nossos pratos e descubra sabores incríveis!");
        subtitulo.setFont(Font.font("Arial", 18));
        subtitulo.setTextFill(Color.web("#6D4C41"));

        // Botão central
        Button btnAbrir = new Button("Abrir Cardápio");
        btnAbrir.setFont(Font.font("Arial", 20));
        btnAbrir.setTextFill(Color.WHITE);
        btnAbrir.setStyle("-fx-background-color: #FFB74D; -fx-background-radius: 15; -fx-padding: 12 30;");
        btnAbrir.setEffect(new DropShadow(5, Color.GRAY));

        // Aqui será conectado o Controller depois
        btnAbrir.setOnAction(e -> {
            CardapioView cardapio = new CardapioView();
            cardapio.start((Stage) btnAbrir.getScene().getWindow());
        });


        // Layout principal
        StackPane root = new StackPane();
        root.setBackground(new Background(new BackgroundFill(Color.web("#FFF7E6"), CornerRadii.EMPTY, null)));
        root.setAlignment(Pos.CENTER);

        // Organizando os elementos verticalmente
        javafx.scene.layout.VBox box = new javafx.scene.layout.VBox(25, titulo, subtitulo, btnAbrir);
        box.setAlignment(Pos.CENTER);

        root.getChildren().add(box);

        Scene scene = new Scene(root, 900, 700); // Tela grande mas não full screen

        stage.setTitle("Cardápio Digital - Início");
        stage.setScene(scene);
        stage.show();
    }
}
