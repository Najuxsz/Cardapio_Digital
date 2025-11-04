package com.cardapio_digital.model;

//atributos da classe prato, são os mesmos do banco de dados, iremos fazer o crud sobre eles
//CASO SEJA NECESSÁRIO FAZER A INSERÇÃO DE UM NOVO CAMPO, COMO CALORIAS, CATEGORIA...
//deve-se adicionar aqui como atributo, no construtor, nos getters/setters, no banco de dados e no método DAO
public class Prato {
    private int id;
    private String nome;
    private int preco;
    private int tempoPreparo;
    private String descricao;

    public Prato(int id, String nome, int preco, int tempoPreparo, String descricao) {

        if (preco < 0) throw new IllegalArgumentException("Preço não pode ser negativo");
        if (tempoPreparo < 0) throw new IllegalArgumentException("Tempo não pode ser negativo");

        this.id = id;
        this.nome = nome;
        this.preco = preco;
        this.tempoPreparo = tempoPreparo;
        this.descricao = descricao;
    }

    public Prato(String nome, int preco, int tempoPreparo, String descricao) {
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

    public void setPreco(int preco) {
        this.preco = preco;
    }

    public double getTempoPreparo() {
        return tempoPreparo;
    }

    public void setTempoPreparo(int tempoPreparo) {
        this.tempoPreparo = tempoPreparo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @Override
    //método toString() vem da classe Object, que é a classe base de todas as classes em Java.
    //define como o objeto será convertido para texto
    public String toString() {
        return String.format(
                "Prato: %d | %s | R$ %.2f | %s min | %s",
                id, nome, preco, tempoPreparo, descricao
        );
    }
}