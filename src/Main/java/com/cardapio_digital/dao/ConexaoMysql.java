package com.cardapio_digital.dao;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoMysql {

    private static ConexaoMysql con;
    private Connection connection;

    private ConexaoMysql(){
        String usuario = "root";
        String senha = "Ana123";
        String strCon = "jdbc:mysql://127.0.0.1:3306/cardapio_digital?useSSL=false";
        try {
            this.connection = DriverManager.getConnection(strCon, usuario, senha);
        }
        catch (SQLException ex){
            ex.printStackTrace();
        }
    }

    public static ConexaoMysql getConexaoMySQL(){
        if(con == null){
            con = new ConexaoMysql();
        }
        return con;
    }

    public Connection getConnection(){
        return this.connection;
    }

}
