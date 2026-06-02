package com.template;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoBD {
    private static final String URL = "jdbc:postgresql://localhost:5432/db_crud";
    private static final String USUARIO = "postgres";
    private static final String SENHA = "postgres";

    public Connection conectarBD() {
        try {
            // Importante: retorna a conexão configurada usando as constantes
            return DriverManager.getConnection(URL, USUARIO, SENHA);
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao conectar: " + e.getMessage(), e);
        }
    }
}