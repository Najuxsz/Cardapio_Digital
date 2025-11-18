package com.cardapio_digital.view.telas;
import com.cardapio_digital.controller.PratoController;
import com.cardapio_digital.model.*;
import com.cardapio_digital.utils.Ordenadores;
import java.util.*;

public class Test {
    public static void main(String[] args) {

        //Controller e lista inicial
        PratoController controller = new PratoController();
        System.out.println("Quantidade inicial de pratos: " + controller.getQuantidadePratos());

        //add prato manualmente no controller, que automaticamente vai p hash
        Prato p = new Prato("Tiramissu", 55, 20, "delicosa sbremsa");
        controller.adicionarPrato(p);

        List<Prato> listaController = new ArrayList<>(List.of(controller.exportarPratos()));
        System.out.println("\nLista de pratos no Controller antes de ordenar:");
        listaController.forEach(System.out::println);

        //ORDENAR (cada vez cria cópia nova)
        List<Prato> listaOrdenacao;

        // BubbleSort - Nome +  System.nanoTime();
        System.out.println("\n--- BubbleSort por Nome ---");
        listaOrdenacao = new ArrayList<>(listaController);

        long inicioBubble = System.nanoTime();
        Ordenadores.bubbleSort(listaOrdenacao, "Nome");
        long fimBubble = System.nanoTime();
        listaOrdenacao.forEach(System.out::println);
        System.out.println("Tempo BubbleSort (ns): " + (fimBubble - inicioBubble));

        // InsertSort - Preço +  System.nanoTime();
        System.out.println("\n--- InsertionSort por Preço ---");
        listaOrdenacao = new ArrayList<>(listaController);

        long inicioInsert = System.nanoTime();
        Ordenadores.insertionSort(listaOrdenacao, "Preço");
        long fimInsert = System.nanoTime();
        listaOrdenacao.forEach(System.out::println);
        System.out.println("Tempo InsertSort (ns): " + (fimInsert - inicioInsert));

        // QuickSort - Tempo +  System.nanoTime();
        System.out.println("\n--- QuickSort por Tempo de Preparo ---");
        listaOrdenacao = new ArrayList<>(listaController);

        Ordenadores.resetContMovimentosQuick();
        long inicioQuick = System.nanoTime();
        Ordenadores.quickSort(listaOrdenacao, 0, listaOrdenacao.size() - 1, "Tempo de Preparo");
        long fimQuick = System.nanoTime();
        listaOrdenacao.forEach(System.out::println);

        System.out.println("Tempo QuickSort (ns): " + (fimQuick - inicioQuick));
        System.out.println("Total de movimentações QuickSort: " + Ordenadores.getContMovimentosQuick());

        // buscar
        System.out.println("\nBuscar pizza de margherita:");
        System.out.println(controller.buscarPrato("Pizza Margherita"));
        //bsucar prato nao existente
        System.out.println(controller.buscarPrato("Xis Bacon"));


        // remover
        System.out.println("\nRemover pizza margherita");
        controller.removerPrato(controller.buscarPrato("Pizza Margherita"));

        // estatísticas
        System.out.println(controller.getTabelaHash().getEstatisticas());

        System.out.println("\nPratos restantes na HashTable:");
        Arrays.stream(controller.exportarPratos()).forEach(System.out::println);
    }
}
/**
 * System.out.println("\nPratos acima de R$40,00:");
 * Arrays.stream(controller.exportarPratos())
 *         .filter(prato -> prato.getPreco() > 40)
 *         .forEach(System.out::println);
 */