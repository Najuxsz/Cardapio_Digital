package com.cardapio_digital.controller;

import com.cardapio_digital.model.HashTablePratos;
import com.cardapio_digital.model.Prato;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

public class PratoController {

    private final HashTablePratos hashTable;
    private final ObservableList<Prato> pratosObservable;
    private int idCounter;

    public PratoController() {
        this.hashTable = new HashTablePratos();
        this.pratosObservable = FXCollections.observableArrayList();
        this.idCounter = 1;
        carregarPratosIniciais();
    }

    private void carregarPratosIniciais() {
        adicionarPrato(new Prato("Pizza Margherita", 35, 20, "Deliciosa pizza com molho de tomate, mussarela e manjericão"));
        adicionarPrato(new Prato("Hambúrguer Artesanal", 28, 15, "Hambúrguer de carne angus com queijo cheddar"));
        adicionarPrato(new Prato("Sushi Combinado", 45, 25, "Seleção de sushis frescos e sashimis"));
        adicionarPrato(new Prato("Lasanha à Bolonhesa", 32, 30, "Lasanha tradicional com molho bolonhesa"));
        adicionarPrato(new Prato("Salada Caesar", 22, 10, "Salada com alface romana, croutons e molho caesar"));
    }

    public ObservableList<Prato> getPratosObservable() {
        return pratosObservable;
    }

    public void adicionarPrato(Prato p) {
        if (p == null) {
            throw new IllegalArgumentException("Prato não pode ser nulo");
        }

        p.setId(idCounter++);
        hashTable.inserirPrato(p);
        pratosObservable.add(p);
    }

    public boolean removerPrato(Prato p) {
        if (p == null) {
            return false;
        }

        boolean removido = hashTable.removerPrato(p.getNome());
        if (removido) {
            pratosObservable.remove(p);
        }
        return removido;
    }

    public Prato buscarPorNome(String nome) {
        return hashTable.buscarPrato(nome);
    }

    public Prato[] exportarPratos() {
        return hashTable.exportarPratos();
    }

    public int getQuantidadePratos() {
        return pratosObservable.size();
    }

    // Métodos de ordenação
    public void bubbleSort(List<Prato> lista, String criterio) {
        if (lista == null || lista.isEmpty()) {
            return;
        }

        for (int i = 0; i < lista.size() - 1; i++) {
            for (int j = 0; j < lista.size() - i - 1; j++) {
                if (comparar(lista.get(j), lista.get(j + 1), criterio) > 0) {
                    Prato temp = lista.get(j);
                    lista.set(j, lista.get(j + 1));
                    lista.set(j + 1, temp);
                }
            }
        }
    }

    public void insertionSort(List<Prato> lista, String criterio) {
        if (lista == null || lista.isEmpty()) {
            return;
        }

        for (int i = 1; i < lista.size(); i++) {
            Prato key = lista.get(i);
            int j = i - 1;

            while (j >= 0 && comparar(lista.get(j), key, criterio) > 0) {
                lista.set(j + 1, lista.get(j));
                j--;
            }
            lista.set(j + 1, key);
        }
    }

    public void quickSort(List<Prato> lista, int low, int high, String criterio) {
        if (lista == null || lista.isEmpty() || low >= high) {
            return;
        }

        if (low < high) {
            int pi = partition(lista, low, high, criterio);
            quickSort(lista, low, pi - 1, criterio);
            quickSort(lista, pi + 1, high, criterio);
        }
    }

    private int partition(List<Prato> lista, int low, int high, String criterio) {
        Prato pivot = lista.get(high);
        int i = low - 1;

        for (int j = low; j < high; j++) {
            if (comparar(lista.get(j), pivot, criterio) <= 0) {
                i++;
                Prato temp = lista.get(i);
                lista.set(i, lista.get(j));
                lista.set(j, temp);
            }
        }

        Prato temp = lista.get(i + 1);
        lista.set(i + 1, lista.get(high));
        lista.set(high, temp);
        return i + 1;
    }

    private int comparar(Prato a, Prato b, String criterio) {
        if (a == null || b == null) {
            return 0;
        }

        return switch (criterio) {
            case "Nome" -> a.getNome().compareToIgnoreCase(b.getNome());
            case "Preço" -> Integer.compare(a.getPreco(), b.getPreco());
            case "Tempo de Preparo" -> Integer.compare(a.getTempoPreparo(), b.getTempoPreparo());
            default -> 0;
        };
    }
}