package com.cardapio_digital.model;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Classe que representa uma Tabela Hash de pratos.
 */
public class HashTablePratos {

    /** Vetor de listas encadeadas que armazena os pratos */
    private LinkedList<Prato>[] tabelaHash;

    /**
     * Tamanho do vetor da tabela hash.
     * - Define a quantidade de posições na tabela hash.
     * - Cada posição guarda uma lista encadeada (LinkedList) de pratos,
     *   que permite armazenar múltiplos pratos no mesmo índice (encadeamento externo).
     * - Importante: esse número **não limita a quantidade de pratos** que podem ser
     *   armazenados na tabela; cada lista encadeada pode crescer dinamicamente.
     * - Inicialmente definido como 10, mas pode ser aumentado para reduzir colisões
     *   se o número de pratos aumentar significativamente.
     */
    private int tamanhoVetor = 10;
    /**
     * Construtor da tabela hash.
     *
     * - Inicializa o vetor de listas encadeadas.
     * - Cada posição do vetor é uma LinkedList vazia (colisões são armazenadas nelas).
     */
    public HashTablePratos() {
        tabelaHash = new LinkedList[tamanhoVetor];
        for (int i = 0; i < tamanhoVetor; i++) {
            tabelaHash[i] = new LinkedList<>();
        }
    }
    /**
     * funcaoHash : Calcula o índice da tabela hash a partir do nome do prato.
     *
     * <p>Como estamos lidando com strings como chave, usamos o método hashCode()
     * do Java para gerar um valor inteiro a partir da string. O hashCode() pode
     * gerar números negativos, então usamos Math.abs() (ou a operação com
     * 0x7fffffff) para garantir que o índice final seja sempre positivo e esteja
     * dentro do intervalo válido da tabela hash.</p>
     *
     * @return índice da tabela hash (entre 0 e tamanho_vetor - 1)
     */
    private int funcaoHash(String nome) {
        // Gera um número a partir do nome
        int hash = nome.hashCode();

        // Garante que o índice será sempre positivo
        return Math.abs(hash % tabelaHash.length);
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
     */
    public void inserirPrato(Prato p) {
        int indice = funcaoHash(p.getNome());

        // Acessa a lista encadeada naquele índice
        LinkedList<Prato> lista = tabelaHash[indice];

        // Verifica se já existe um prato com o mesmo nome
        for (Prato existente : lista) {
            if (existente.getNome().equalsIgnoreCase(p.getNome())) {
                System.out.println("Prato duplicado! Não será inserido.");
                return;
            }
        }
        // Adiciona o prato à lista encadeada
        lista.add(p);
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
     */

    public Prato buscarPrato(String nomePrato) {
        int chaveHash = funcaoHash(nomePrato);
        LinkedList<Prato> lista = tabelaHash[chaveHash];
        for (Prato p : lista) {
            if (p.getNome().equalsIgnoreCase(nomePrato)) {
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
     */

    public boolean removerPrato(String nome) {
        int indice = funcaoHash(nome);
        LinkedList<Prato> lista = tabelaHash[indice];
        for (Prato p : lista) {
            if (p.getNome().equalsIgnoreCase(nome)) {
                lista.remove(p);
                return true;
            }
        }
        return false;
    }

    /**
     * Exporta todos os pratos da tabela hash para um vetor.
     *
     * - Percorre cada posição do vetor da tabela hash .
     * - Cada posição contém uma lista encadeada de pratos, que armazena possíveis colisões
     *   (mais de um prato com índice hash igual).
     * - Para não precisar calcular antes a quantidade total de pratos,
     *   usamos um ArrayList dinâmico, que cresce automaticamente conforme adicionamos os pratos.
     * - Adiciona todos os pratos de cada lista encadeada no ArrayList.
     * - Por fim, converte o ArrayList para um vetor fixo (Prato[]),
     *   que é mais fácil de usar em métodos de ordenação ou outras operações posteriores.
     *
     * @return vetor contendo todos os pratos armazenados na tabela hash
     */

    public Prato[] exportarPratos() {
        ArrayList<Prato> listaDinamicaPratos = new ArrayList<>();

        for (int i = 0; i < tamanhoVetor; i++) {
            for (Prato p : tabelaHash[i]) {
                listaDinamicaPratos.add(p);
            }
        }
        return listaDinamicaPratos.toArray(new Prato[0]);
    }

}
