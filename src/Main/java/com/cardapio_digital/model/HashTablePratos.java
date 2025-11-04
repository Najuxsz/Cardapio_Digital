package com.cardapio_digital.model;

import java.util.LinkedList;

public class HashTablePratos {
    private LinkedList<Prato>[] tabela;
    private int tamanho = 10;

    public HashTablePratos() {
        // tabela é um vetor de listas — cada posição guarda vários pratos (para colisões)
        tabela = new LinkedList[tamanho];
        for (int i = 0; i < tamanho; i++) {
            tabela[i] = new LinkedList<>();
        }
    }

    private int hash(String chave) {
        // Gera um índice válido (0 até tamanho-1) a partir da chave usando hashCode (Chave = Nome do Prato)
        return Math.abs(chave.hashCode()) % tamanho;
    }

    public void inserir(Prato p) {
        // lógica
    }

    // método para buscar prato
    public Prato buscar(String nomePrato) {
        int chaveHash = hash(nomePrato); // calcula o índice (Chave)
        LinkedList<Prato> lista = tabela[chaveHash]; // pega a lista naquela posição da tabela
        for (Prato p : lista) {
            if (p.getNome().equalsIgnoreCase(nomePrato)) {
                return p; // encontrou o prato
            }
        }
        return null; // se não achou
    }

    // método para remover prato
    public boolean remover(String nome) {
        int indice = hash(nome);
        LinkedList<Prato> lista = tabela[indice];

        for (Prato p : lista) {
            if (p.getNome().equalsIgnoreCase(nome)) {
                lista.remove(p);
                return true;
            }
        }
        return false;
    }

    public Prato[] exportar() {
        // lógica aq
        return null;
    }
}
