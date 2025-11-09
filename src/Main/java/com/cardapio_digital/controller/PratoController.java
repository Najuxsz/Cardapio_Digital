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

    public Prato[] exportarPratos() {
        return hashTable.exportarPratos();
    }

    public int getQuantidadePratos() {
        return pratosObservable.size();
    }
}