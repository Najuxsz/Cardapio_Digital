package com.cardapio_digital.view.telas;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class InicioView {

    public void start(Stage stage) {
        // Título
        Label titulo = new Label("Bem-vindo ao Cardápio Digital");
        titulo.setFont(Font.font("Arial", FontWeight.BOLD, 36));
        titulo.setTextFill(Color.web("#4E342E"));
        titulo.setEffect(new DropShadow(3, Color.GRAY));

        // Subtítulo
        Label subtitulo = new Label("Gerencie e atualize o cardápio do seu restaurante de forma prática e rápida!");
        subtitulo.setFont(Font.font("Arial", 18));
        subtitulo.setTextFill(Color.web("#6D4C41"));
        subtitulo.setWrapText(true);
        subtitulo.setMaxWidth(600);
        subtitulo.setAlignment(Pos.CENTER);

        // Botão para abrir o cardápio
        Button btnAbrir = new Button("Abrir Cardápio");
        btnAbrir.setFont(Font.font("Arial", 20));
        btnAbrir.setTextFill(Color.WHITE);
        btnAbrir.setStyle("-fx-background-color: #FFB74D; -fx-background-radius: 15; -fx-padding: 12 30;");
        btnAbrir.setEffect(new DropShadow(5, Color.GRAY));
        btnAbrir.setCursor(javafx.scene.Cursor.HAND);

        // Efeito hover
        btnAbrir.setOnMouseEntered(e ->
                btnAbrir.setStyle("-fx-background-color: #FFA726; -fx-background-radius: 15; -fx-padding: 12 30;")
        );
        btnAbrir.setOnMouseExited(e ->
                btnAbrir.setStyle("-fx-background-color: #FFB74D; -fx-background-radius: 15; -fx-padding: 12 30;")
        );

        // Ação do botão
        btnAbrir.setOnAction(e -> {
            CardapioView cardapio = new CardapioView();
            Stage currentStage = (Stage) btnAbrir.getScene().getWindow();
            cardapio.start(currentStage);
        });

        // Layout principal
        VBox box = new VBox(25, titulo, subtitulo, btnAbrir);
        box.setAlignment(Pos.CENTER);

        StackPane root = new StackPane(box);
        root.setBackground(new Background(new BackgroundFill(Color.web("#FFF7E6"), CornerRadii.EMPTY, null)));
        root.setAlignment(Pos.CENTER);

        // Cena
        Scene scene = new Scene(root, 900, 700);
        stage.setTitle("Cardápio Digital - Início");
        stage.setScene(scene);
        stage.show();
    }
}