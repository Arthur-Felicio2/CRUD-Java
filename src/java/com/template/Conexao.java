package com.template;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Gerencia a conexão com o banco de dados PostgreSQL.
 */
public class Conexao {

    private static final String URL = "jdbc:postgresql://localhost:5432/RPG";
    private static final String USUARIO = "postgres";
    private static final String SENHA = "postgres";

    public Connection conectar() {
        try {
            return DriverManager.getConnection(URL, USUARIO, SENHA);
        } catch (SQLException e) {
            System.err.println("Erro de conexao: " + e.getMessage());
            return null;
        }
    }
}