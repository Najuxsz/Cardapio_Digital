package com.cardapio_digital.model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


/**
 * Classe que representa uma Tabela Hash de pratos.
 */

public class HashTablePratos {

    private static final int TAMANHO_INICIAL = 10;
    private static final double FATOR_CARGA_MAXIMO = 0.75;
    /** Vetor de listas encadeadas que armazena os pratos */

    private LinkedList<Prato>[] tabelaHash;
    /**
     * Tamanho do vetor da tabela hash.
     * - Define a quantidade de posições na tabela hash.
     * - Cada posição guarda uma lista encadeada (LinkedList) de pratos,
     *   que permite armazenar múltiplos pratos no mesmo índice (encadeamento externo).
     * - Importante: esse número **não limita a quantidade de pratos** que podem ser
     *   armazenados na tabela; cada lista encadeada pode crescer dinamicamente.
     */
    private int tamanhoVetor;
    private int quantidadeElementos;

    /**
     * Construtor da tabela hash.
     *
     * - Inicializa o vetor de listas encadeadas.
     * - Cada posição do vetor é uma LinkedList vazia (colisões são armazenadas nelas).
     */

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
     * Insere um prato na tabela hash.
     *
     * Este método recebe um objeto Prato e o adiciona na tabela hash.
     * O índice na tabela é calculado a partir do nome do prato, mas o
     * objeto completo é armazenado na lista encadeada para preservar todos
     * os atributos do prato (preço, tempo de preparo, descrição etc.).
     *
     * Segue os princípios de programação orientada a objetos:
     * - Encapsulamento: acessa atributos do prato apenas via getters.
     * - Coesão: o método tem apenas uma responsabilidade: inserir o prato (conferindo se o mesmo já existe).
     *
     * @param p Prato a ser inserido na tabela hash
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
     * Busca um prato na tabela hash pelo nome (chave).
     *
     * - Calcula o índice da chave.
     * - Percorre a lista naquela posição.
     * - Retorna o prato se encontrado ou null se não existir.
     *
     * @param nomePrato Nome do prato a buscar
     * @return Prato encontrado ou null
     * Busca um prato pelo nome
     * Retorna null se não encontrado.
     * */

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
     * Remove um prato da tabela hash pelo nome.
     *
     * - Calcula o índice da chave.
     * - Percorre a lista naquela posição.
     * - Remove o prato se encontrado.
     *
     * @param nome Nome do prato a remover
     * @return true se o prato foi removido, false caso contrário
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
     * Exporta todos os pratos armazenados na tabela hash para um array.
     *
     * - Este método percorre toda a tabela hash (cada lista encadeada em cada posição).
     * - Todos os pratos encontrados são adicionados a uma lista auxiliar (ArrayList),
     *   que é posteriormente convertida em um array de objetos do tipo Prato.
     *
     * Objetivo: permitir operações externas (como salvar em arquivo ou exibir no sistema)
     * sem expor diretamente a estrutura interna da tabela hash.
     *
     * Princípios de POO aplicados:
     * - Encapsulamento: o método não expõe a estrutura interna da tabela.
     * - Coesão: tem uma única responsabilidade — converter e exportar os dados.
     *
     * @return Um array contendo todos os pratos armazenados na tabela hash.
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
     * Carrega uma lista de pratos na tabela hash.
     * - Este método é o inverso de exportar: ele recebe uma lista de pratos e os insere
     *   novamente na estrutura da tabela hash.
     * - Cada prato é reinserido utilizando o método inserirPrato(), garantindo que:
     *   → A função hash seja aplicada corretamente.
     *   → As regras de duplicação sejam respeitadas.
     * É útil, por exemplo, para restaurar dados salvos em arquivo.
     *
     * @param pratos Lista de objetos Prato a serem inseridos.
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
     * Retorna a quantidade total de elementos armazenados na tabela hash.
     * - Representa o número total de pratos, não o tamanho do vetor.
     * - Mantém o controle interno de quantos elementos estão realmente armazenados.
     *
     * @return Quantidade de elementos (pratos) atualmente na tabela.
     */
    public int getQuantidadeElementos() {
        return quantidadeElementos;
    }

    /**
     * Verifica se a tabela hash está vazia.
     * - Uma tabela está vazia se a contagem de elementos for zero.
     * - Método útil para verificações antes de realizar operações (como listar ou exportar).
     *
     * @return true se não houver elementos, false caso contrário.
     */
    public boolean isEmpty() {
        return quantidadeElementos == 0;
    }

    /**
     * Limpa todos os elementos armazenados na tabela hash.
     *
     * - Percorre todo o vetor e limpa cada lista encadeada individualmente.
     * - Após a limpeza, redefine a quantidade de elementos para zero.
     *
     * Importante: este método mantém o tamanho do vetor inalterado — apenas remove os dados.
     *
     * Padrão de projeto: método utilitário para reinicializar a estrutura.
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
     * Verifica se a tabela precisa ser redimensionada.
     * - O redimensionamento é necessário quando o fator de carga (load factor)
     *   ultrapassa o limite definido (FATOR_CARGA_MAXIMO).
     * - O fator de carga é a razão entre a quantidade de elementos e o tamanho do vetor.
     * @return true se o fator de carga ultrapassar o limite, false caso contrário.
     */
    private boolean precisaRedimensionar() {
        double fatorCarga = (double) quantidadeElementos / tamanhoVetor;
        return fatorCarga > FATOR_CARGA_MAXIMO;
    }

    /**
     * Redimensiona a tabela hash, dobrando seu tamanho atual.
     *
     * - Este processo é chamado automaticamente quando o fator de carga ultrapassa o limite.
     * - O novo vetor (tabela) tem o dobro do tamanho do anterior.
     * - Todos os pratos da tabela antiga são reinseridos (rehash) na nova estrutura,
     *   recalculando seus índices conforme o novo tamanho.
     * Observação:
     * - Este método é privado, pois o redimensionamento é uma operação interna da estrutura.
     */
    @SuppressWarnings("unchecked")
    private void redimensionar() {
        int novoTamanho = tamanhoVetor * 2;
        LinkedList<Prato>[] antigaTabela = tabelaHash;

        // Cria nova tabela com o dobro do tamanho
        tabelaHash = new LinkedList[novoTamanho];
        tamanhoVetor = novoTamanho;
        quantidadeElementos = 0;

        // Inicializa todas as listas encadeadas (buckets)
        for (int i = 0; i < tamanhoVetor; i++) {
            tabelaHash[i] = new LinkedList<>();
        }

        // Reinsere todos os elementos da tabela antiga na nova tabela
        for (LinkedList<Prato> bucket : antigaTabela) {
            if (bucket != null) {
                for (Prato p : bucket) {
                    inserirPrato(p);
                }
            }
        }
    }

    /**
     * Retorna o fator de carga atual da tabela.
     * - Mede o "nível de ocupação" da tabela, ajudando a avaliar o desempenho.
     * - Um fator de carga alto indica mais colisões e possíveis perdas de eficiência.
     * @return Valor do fator de carga atual.
     */
    public double getFatorCarga() {
        return (double) quantidadeElementos / tamanhoVetor;
    }

    /**
     * Retorna o tamanho atual da tabela hash (número de buckets).
     * - Este valor não indica quantos pratos existem, mas quantas posições (listas encadeadas)
     *   a tabela possui para armazenar os pratos.
     * @return Quantidade de posições (buckets) da tabela hash.
     */
    public int getTamanhoTabela() {
        return tamanhoVetor;
    }

    /**
     * Gera e retorna estatísticas sobre o estado atual da tabela hash.
     * - O objetivo é oferecer uma visão geral da eficiência da estrutura.
     * - Calcula:
     *   → Quantidade de buckets vazios (posições sem elementos);
     *   → Maior número de colisões (tamanho da lista mais cheia);
     *   → Fator de carga atual.
     *
     * @return String formatada com as informações principais da tabela.
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