package com.cardapio_digital.model;

public class Prato {
    private int id;
    private String nome;
    private double preco;
    private double tempoPreparo;
    private String descricao;

    public Prato(int id, String nome, double preco, double tempoPreparo, String descricao) {
        this.id = id;
        this.nome = nome;
        this.preco = preco;
        this.tempoPreparo = tempoPreparo;
        this.descricao = descricao;
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
        this.nome = nome;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public double getTempoPreparo() {
        return tempoPreparo;
    }

    public void setTempoPreparo(double tempoPreparo) {
        this.tempoPreparo = tempoPreparo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public String toString() {
        return "Prato{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", preco=" + preco +
                ", tempoPreparo=" + tempoPreparo +
                ", descricao='" + descricao + '\'' +
                '}';
    }
}