package com.cardapio_digital;

import com.cardapio_digital.model.Prato;
import com.cardapio_digital.model.HashTablePratos;
import com.cardapio_digital.model.Prato;
import com.cardapio_digital.utils.ComparadorPreco;
import com.cardapio_digital.utils.Ordenadores;
import com.cardapio_digital.view.telas.InicioView;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        //Inicia a tela inicial do sistema
        InicioView inicio = new InicioView();
        inicio.start(primaryStage);
    }

    public static void main(String[] args) {
        //Inicia a aplicação JavaFX
        launch(args);

        /**
        // 🔹 Cria a tabela hash de pratos
        HashTablePratos tabela = new HashTablePratos();

        // 🔹 Cria alguns pratos para testar
        Prato p1 = new Prato("Lasanha", 35, 25, "lala");
        Prato p2 = new Prato("Macarrão", 28, 20, "alla");
        Prato p3 = new Prato("Pizza", 50, 30, "lal");
        Prato p4 = new Prato("Arroz", 20, 15, "lal");
        Prato p5 = new Prato("Feijão", 15, 10, "lal");

        // ---------------------------
        // 🔸 INSERÇÃO DE PRATOS
        // ---------------------------
        System.out.println("---- INSERINDO PRATOS ----");
        tabela.inserirPrato(p1);
        tabela.inserirPrato(p2);
        tabela.inserirPrato(p3);
        tabela.inserirPrato(p4);
        tabela.inserirPrato(p5);

        // ---------------------------
        // 🔸 EXPORTAÇÃO DOS PRATOS
        // ---------------------------
        System.out.println("\n---- EXPORTANDO PRATOS ----");
        Prato[] vetor = tabela.exportarPratos();

        System.out.println("Lista original de pratos:");
        for (Prato p : vetor) {
            System.out.println("🍽️ " + p.getNome() + " - R$" + p.getPreco() + " - " + p.getTempoPreparo() + "min");
        }

        // ---------------------------
        // 🔸 TESTANDO ORDENAÇÃO POR PREÇO
        // ---------------------------
        System.out.println("\n---- ORDENANDO POR PREÇO (insertionSort) ----");

        // Chama o método de ordenação usando o comparador de preço
        Ordenadores.insertionSort(vetor, new ComparadorPreco());

        // Exibe o resultado da ordenação
        System.out.println("Lista ordenada por preço (crescente):");
        for (Prato p : vetor) {
            System.out.println("💲 " + p.getNome() + " - R$" + p.getPreco());
        }

        // ---------------------------
        // 🔸 TESTANDO OUTRO MÉTODO (opcional)
        // ---------------------------
        System.out.println("\n---- ORDENANDO POR PREÇO (quickSort) ----");
        Prato[] vetor2 = tabela.exportarPratos(); // exporta de novo, para não alterar o original
        Ordenadores.quickSort(vetor2, 0, vetor2.length - 1, "preco");
         **/
    }
}