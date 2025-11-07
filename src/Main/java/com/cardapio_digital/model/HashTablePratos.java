package com.cardapio_digital.model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class HashTablePratos {

    private static final int TAMANHO_INICIAL = 10;
    private static final double FATOR_CARGA_MAXIMO = 0.75;

    private LinkedList<Prato>[] tabelaHash;
    private int tamanhoVetor;
    private int quantidadeElementos;

    @SuppressWarnings("unchecked")
    public HashTablePratos() {
        this.tamanhoVetor = TAMANHO_INICIAL;
        this.quantidadeElementos = 0;
        this.tabelaHash = new LinkedList[tamanhoVetor];

        for (int i = 0; i < tamanhoVetor; i++) {
            tabelaHash[i] = new LinkedList<>();
        }
    }

    /**
     * Função hash que mapeia o nome do prato para um índice na tabela
     */
    private int funcaoHash(String nome) {
        if (nome == null || nome.isEmpty()) {
            return 0;
        }
        return Math.abs(nome.toLowerCase().hashCode() % tamanhoVetor);
    }

    /**
     * Insere um prato na tabela hash
     * Evita duplicatas baseado no nome do prato
     */
    public void inserirPrato(Prato p) {
        if (p == null || p.getNome() == null || p.getNome().isEmpty()) {
            return;
        }

        // Verifica se precisa redimensionar
        if (precisaRedimensionar()) {
            redimensionar();
        }

        int indice = funcaoHash(p.getNome());
        LinkedList<Prato> lista = tabelaHash[indice];

        // Verifica se já existe um prato com o mesmo nome
        for (Prato existente : lista) {
            if (existente.equals(p)) {
                return; // Não permite duplicatas
            }
        }

        lista.add(p);
        quantidadeElementos++;
    }

    /**
     * Busca um prato pelo nome
     * Retorna null se não encontrado
     */
    public Prato buscarPrato(String nome) {
        if (nome == null || nome.isEmpty()) {
            return null;
        }

        int indice = funcaoHash(nome);
        LinkedList<Prato> lista = tabelaHash[indice];

        for (Prato p : lista) {
            if (p.getNome().equalsIgnoreCase(nome)) {
                return p;
            }
        }
        return null;
    }

    /**
     * Remove um prato pelo nome
     * Retorna true se removido com sucesso, false caso contrário
     */
    public boolean removerPrato(String nome) {
        if (nome == null || nome.isEmpty()) {
            return false;
        }

        int indice = funcaoHash(nome);
        LinkedList<Prato> lista = tabelaHash[indice];

        for (Prato p : lista) {
            if (p.getNome().equalsIgnoreCase(nome)) {
                lista.remove(p);
                quantidadeElementos--;
                return true;
            }
        }
        return false;
    }

    /**
     * Exporta todos os pratos da tabela hash para um array
     */
    public Prato[] exportarPratos() {
        List<Prato> lista = new ArrayList<>();

        for (LinkedList<Prato> bucket : tabelaHash) {
            if (bucket != null) {
                lista.addAll(bucket);
            }
        }

        return lista.toArray(new Prato[0]);
    }

    /**
     * Carrega uma lista de pratos na tabela hash
     */
    public void carregarPratos(List<Prato> pratos) {
        if (pratos == null) {
            return;
        }

        for (Prato p : pratos) {
            if (p != null) {
                inserirPrato(p);
            }
        }
    }

    /**
     * Retorna a quantidade de elementos na tabela
     */
    public int getQuantidadeElementos() {
        return quantidadeElementos;
    }

    /**
     * Verifica se a tabela está vazia
     */
    public boolean isEmpty() {
        return quantidadeElementos == 0;
    }

    /**
     * Limpa todos os elementos da tabela
     */
    public void limpar() {
        for (int i = 0; i < tamanhoVetor; i++) {
            if (tabelaHash[i] != null) {
                tabelaHash[i].clear();
            }
        }
        quantidadeElementos = 0;
    }

    /**
     * Verifica se a tabela precisa ser redimensionada
     */
    private boolean precisaRedimensionar() {
        double fatorCarga = (double) quantidadeElementos / tamanhoVetor;
        return fatorCarga > FATOR_CARGA_MAXIMO;
    }

    /**
     * Redimensiona a tabela hash dobrando seu tamanho
     * Rehash de todos os elementos
     */
    @SuppressWarnings("unchecked")
    private void redimensionar() {
        int novoTamanho = tamanhoVetor * 2;
        LinkedList<Prato>[] antigaTabela = tabelaHash;

        // Cria nova tabela
        tabelaHash = new LinkedList[novoTamanho];
        tamanhoVetor = novoTamanho;
        quantidadeElementos = 0;

        // Inicializa buckets
        for (int i = 0; i < tamanhoVetor; i++) {
            tabelaHash[i] = new LinkedList<>();
        }

        // Reinsere todos os elementos
        for (LinkedList<Prato> bucket : antigaTabela) {
            if (bucket != null) {
                for (Prato p : bucket) {
                    inserirPrato(p);
                }
            }
        }
    }

    /**
     * Retorna o fator de carga atual da tabela
     */
    public double getFatorCarga() {
        return (double) quantidadeElementos / tamanhoVetor;
    }

    /**
     * Retorna o tamanho da tabela
     */
    public int getTamanhoTabela() {
        return tamanhoVetor;
    }

    /**
     * Retorna estatísticas da distribuição dos elementos
     */
    public String getEstatisticas() {
        int bucketsVazios = 0;
        int maxColisoes = 0;

        for (LinkedList<Prato> bucket : tabelaHash) {
            if (bucket.isEmpty()) {
                bucketsVazios++;
            } else {
                maxColisoes = Math.max(maxColisoes, bucket.size());
            }
        }

        return String.format(
                "Tamanho: %d | Elementos: %d | Fator de Carga: %.2f | Buckets Vazios: %d | Maior Colisão: %d",
                tamanhoVetor, quantidadeElementos, getFatorCarga(), bucketsVazios, maxColisoes
        );
    }
}