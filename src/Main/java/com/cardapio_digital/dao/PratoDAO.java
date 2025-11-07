package com.cardapio_digital.dao;

import com.cardapio_digital.model.Prato;
import com.cardapio_digital.model.HashTablePratos;
import java.sql.*;
import java.util.ArrayList;

public class PratoDAO {

    private Connection con;
    private PreparedStatement ps;
    private HashTablePratos tabelaHash = new HashTablePratos();

    public PratoDAO() {
        this.con = ConexaoMysql.getConexaoMySQL().getConnection();
    }

    public void cadastrarPrato(Prato p) {
        String query = "INSERT INTO pratos (nome, descricao, preco) VALUES (?, ?, ?)";
        //logica
    }

    public ArrayList<Prato> listarPrato() {
        ArrayList<Prato> listaPrato = new ArrayList<>();
        String query = "SELECT * FROM pratos";
        //logica

        return listaPrato;
    }

    public void editarPrato(Prato p) {
        String query = "UPDATE pratos SET nome = ?, descricao = ?, preco = ? WHERE id = ?";
        //logica
    }

    public void removerPrato(int idPrato) {
        Prato prato = buscarPorId(idPrato);
        //logica
    }

    public Prato buscarPorId(int idPrato) {
        String query = "SELECT * FROM pratos WHERE id = ?";
        try {
            ps = this.con.prepareStatement(query);
            ps.setInt(1, idPrato);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Prato p = new Prato();
                p.setId(rs.getInt("id"));
                p.setNome(rs.getString("nome"));
                p.setPreco(rs.getInt("preco"));
                p.setTempoPreparo(rs.getInt("tempo_preparo"));
                p.setDescricao(rs.getString("descricao"));

                return p;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
