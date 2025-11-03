package com.cardapio_digital.view.telas;

/*
 * Classe responsável por exibir a TELA INICIAL do sistema (VIEW)
 * Aqui construímos apenas a interface gráfica com JavaFX.
 * Esta classe NÃO deve conter lógica de negócio nem acesso ao banco.
 * Ela apenas chama a próxima tela, que depois conversará com o Controller.
 * Para criar outra tela, é so criar outra classe view e chamar aqui no botão
 */

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

    /*
     * Método start: responsável por montar e exibir os componentes da tela.
     * Quando essa tela é chamada, criamos a interface e carregamos os elementos visuais.
     */
    public void start(Stage stage) {

        // --- Título principal da aplicação (UI) ---
        Label titulo = new Label("Bem-vindo ao Cardápio Digital");
        titulo.setFont(Font.font("Arial", 36));
        titulo.setTextFill(Color.web("#4E342E"));
        titulo.setEffect(new DropShadow(3, Color.GRAY));

        // --- Texto de introdução (UI) ---
        Label subtitulo = new Label("Explore nossos pratos e descubra sabores incríveis!");
        subtitulo.setFont(Font.font("Arial", 18));
        subtitulo.setTextFill(Color.web("#6D4C41"));

        // --- Botão que leva para o cardápio (evento de clique) ---
        Button btnAbrir = new Button("Abrir Cardápio");
        btnAbrir.setFont(Font.font("Arial", 20));
        btnAbrir.setTextFill(Color.WHITE);
        btnAbrir.setStyle("-fx-background-color: #FFB74D; -fx-background-radius: 15; -fx-padding: 12 30;");
        btnAbrir.setEffect(new DropShadow(5, Color.GRAY));

        /*
         * Ação do botão:
         * Ao clicar, abrimos a tela do cardápio.
         * Esse é o ponto onde fazemos a transição de telas dentro da camada View.
         * Posteriormente o Controller será ligado aqui para integração com a lógica.
         */
        btnAbrir.setOnAction(e -> {
            CardapioView cardapio = new CardapioView();
            cardapio.start((Stage) btnAbrir.getScene().getWindow());
        });

        // --- Layout principal (organização dos elementos) ---
        StackPane root = new StackPane();
        root.setBackground(new Background(new BackgroundFill(Color.web("#FFF7E6"), CornerRadii.EMPTY, null)));
        root.setAlignment(Pos.CENTER);

        // Caixa vertical para organizar os elementos centralizados
        javafx.scene.layout.VBox box = new javafx.scene.layout.VBox(25, titulo, subtitulo, btnAbrir);
        box.setAlignment(Pos.CENTER);
        root.getChildren().add(box);

        // --- Criação da cena e exibição da janela ---
        Scene scene = new Scene(root, 900, 700);

        stage.setTitle("Cardápio Digital - Início");
        stage.setScene(scene);
        stage.show();
    }
}