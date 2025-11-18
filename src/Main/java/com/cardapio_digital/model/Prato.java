package com.cardapio_digital.model;
import java.util.Objects;

public class Prato {
    private int id;
    private String nome;
    private int preco;
    private int tempoPreparo;
    private String descricao;

    public Prato(int id, String nome, int preco, int tempoPreparo, String descricao) {
        validarParametros(nome, preco, tempoPreparo);
        this.id = id;
        this.nome = nome;
        this.preco = preco;
        this.tempoPreparo = tempoPreparo;
        this.descricao = descricao != null ? descricao : "";
    }
    public Prato(String nome, int preco, int tempoPreparo, String descricao) {
        this(0, nome, preco, tempoPreparo, descricao);
    }
    public Prato() {
        this.nome = "";
        this.descricao = "";
    }

    private void validarParametros(String nome, int preco, int tempoPreparo) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome não pode ser vazio");
        }
        if (preco < 0) {
            throw new IllegalArgumentException("Preço não pode ser negativo");
        }
        if (tempoPreparo < 0) {
            throw new IllegalArgumentException("Tempo não pode ser negativo");
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        if (nome != null && !nome.trim().isEmpty()) {
            this.nome = nome;
        }
    }

    public int getPreco() {
        return preco;
    }

    public void setPreco(int preco) {
        if (preco >= 0) {
            this.preco = preco;
        }
    }

    public int getTempoPreparo() {
        return tempoPreparo;
    }

    public void setTempoPreparo(int tempoPreparo) {
        if (tempoPreparo >= 0) {
            this.tempoPreparo = tempoPreparo;
        }
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao != null ? descricao : "";
    }

    @Override
    //define como o objeto é mostrado quando impresso no console
    public String toString() {
        return String.format("Prato: %d | %s | R$ %d | %d min | %s",
                id, nome, preco, tempoPreparo, descricao);
    }

    @Override
    //define quando dois pratos são considerados iguais
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Prato)) return false;
        Prato p = (Prato) o;
        return nome.equalsIgnoreCase(p.nome);
    }

    @Override
    //gera um código numérico único baseado no nome do prato, usado pela tabela hash.
    public int hashCode() {
        return Objects.hash(nome.toLowerCase());
    }
}