package com.template.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class ConexaoBD {
    private static final String URL = "jdbc:postgresql://localhost:5432/db_crud";
    private static final String USUARIO = "postgres";
    private static final String SENHA = "postgres";

    public Connection conectarBD() throws SQLException {
        try {
            return DriverManager.getConnection(URL, USUARIO, SENHA);
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao conectar ao PostgreSQL: " + e.getMessage(), e);
        }
    }
}