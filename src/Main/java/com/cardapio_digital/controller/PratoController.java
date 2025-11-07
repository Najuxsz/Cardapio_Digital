/**
 * O PratoController é o intermediário entre a tela (View) e as classes de lógica/dados (DAO e HashTablePratos).
 *
 * Ele:
 *  - Recebe as ações da interface (ex: clicar em “Adicionar”, “Editar”, “Remover”);
 *  - Chama o PratoDAO para inserir, buscar, atualizar ou remover no banco;
 *  - Atualiza a tabela (ObservableList) que aparece na tela;
 *  - Mantém a HashTablePratos sincronizada (cache em memória).
 *
 * Além disso, pode ser usado para testes ou demonstrações em console (ex: ordenações, buscas, tempos).
 */

package com.cardapio_digital.controller;

import com.cardapio_digital.dao.PratoDAO;
import com.cardapio_digital.model.HashTablePratos;
import com.cardapio_digital.model.Prato;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

public class PratoController {

    private final PratoDAO pratoDAO;
    private final HashTablePratos hashTable;

    // Lista principal que será exibida na tela
    private final ObservableList<Prato> pratosObservable = FXCollections.observableArrayList();

    public PratoController() {
        this.pratoDAO = new PratoDAO();
        this.hashTable = new HashTablePratos();

        carregarPratos(); // inicializa tabela e hashtable
    }

    /**
     * Carrega todos os pratos do banco e atualiza a hash table.
     */
    public void carregarPratos() {
        List<Prato> lista = pratoDAO.listarPrato();
        pratosObservable.setAll(lista);
        hashTable.carregarPratos(lista);
        System.out.println("[INFO] Pratos carregados: " + lista.size());
    }

    public ObservableList<Prato> getPratosObservable() {
        return pratosObservable;
    }

    /**
     * Adiciona um novo prato ao banco, à hash table e à lista visível.
     */
    public void adicionarPrato(Prato p) {
        pratoDAO.CadastrarPrato(p);
        hashTable.inserirPrato(p);
        pratosObservable.add(p);
        System.out.println("[ADD] Prato adicionado: " + p.getNome());
    }

    /**
     * Remove um prato do banco, da hash table e da lista visível.
     */
    public void removerPrato(Prato p) {
        pratoDAO.removerPrato(p.getId());
        hashTable.removerPrato(p.getNome());
        pratosObservable.remove(p);
        System.out.println("[REMOVE] Prato removido: " + p.getNome());
    }

    /**
     * Edita os dados de um prato existente.
     */
    public void editarPrato(Prato p) {
        pratoDAO.editarPrato(p);
        hashTable.atualizarPrato(p);
        carregarPratos(); // recarrega a lista e sincroniza tudo
        System.out.println("[EDIT] Prato editado: " + p.getNome());
    }

    /**
     * Busca um prato pelo ID.
     */
    public Prato buscarPorId(int id) {
        Prato p = pratoDAO.buscarPorId(id);
        System.out.println("[BUSCA POR ID] Resultado: " + (p != null ? p.getNome() : "Nenhum prato encontrado."));
        return p;
    }

    /** Busca um prato pelo nome usando a hash table. */
    public Prato buscarPorNome(String nome) {
        Prato p = hashTable.buscarPrato(nome);
        if (p != null) {
            System.out.println("[HASH BUSCA] Prato encontrado: " + p.getNome());
        } else {
            System.out.println("[HASH BUSCA] Nenhum prato com o nome '" + nome + "' foi encontrado.");
        }
        return p;
    }

    /**
     * Exemplo simples de comparação entre tempos de busca (HashTable x Banco de Dados).

    public void compararTempoDeBusca(String nome) {
        long inicioHash = System.nanoTime();
        Prato hashResult = hashTable.buscarPrato(nome);
        long fimHash = System.nanoTime();

        long inicioBanco = System.nanoTime();
        Prato dbResult = pratoDAO.buscarPorNome(nome); // se quiser implementar no DAO
        long fimBanco = System.nanoTime();

        System.out.println("\n--- Comparação de Tempo de Busca ---");
        System.out.println("HashTable: " + (fimHash - inicioHash) / 1_000_000.0 + " ms");
        System.out.println("Banco de Dados: " + (fimBanco - inicioBanco) / 1_000_000.0 + " ms");
        System.out.println("-----------------------------------\n");
     **/

}