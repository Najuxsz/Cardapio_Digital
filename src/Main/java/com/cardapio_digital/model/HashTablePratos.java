package com.cardapio_digital.model;
import java.util.LinkedList;

public class HashTablePratos {
    private LinkedList<Prato>[] tabela;
    private int tamanho = 10;

    public HashTablePratos() {
        //tabela é um vetor de listas — cada posição guarda vários pratos (para colisões)
        tabela = new LinkedList[tamanho];
        for (int i = 0; i < tamanho; i++) {
            tabela[i] = new LinkedList<>();
        }
    }

    private int hash(String chave) {
        //hash() calcula o índice onde cada prato deve ficar
        return Math.abs(chave.hashCode()) % tamanho;
    }

    public void inserir(Prato p) {
        //logica
    }

    public Prato buscar(String nome) {
        //logica
        return null;
    }

    //método para remover prato
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
        //lógica aq
        return null;
    }
}
