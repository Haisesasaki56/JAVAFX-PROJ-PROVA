package com.template;

import java.sql.*;
import java.util.ArrayList;

public class AlimentoDAO implements IAlimentoDAO {

    @Override
    public void cadastrarAlimento(AlimentoDTO obj) {
        // Uso de aspas em "natural" para evitar conflito com palavra reservada do Postgres
        String sql = "INSERT INTO alimento (alimento, calorias, \"natural\") VALUES (?, ?, ?)";
        try (Connection conn = new ConexaoBD().conectarBD();
             PreparedStatement pstm = conn.prepareStatement(sql)) {
            pstm.setString(1, obj.getAlimento());
            pstm.setDouble(2, obj.getCalorias());
            pstm.setBoolean(3, obj.isNatural());
            pstm.execute();
        } catch (SQLException e) {
            throw new RuntimeException("Erro DAO Cadastrar: " + e.getMessage());
        }
    }

    @Override
    public ArrayList<AlimentoDTO> listarAlimentos() {
        String sql = "SELECT * FROM alimento ORDER BY id";
        ArrayList<AlimentoDTO> lista = new ArrayList<>();
        try (Connection conn = new ConexaoBD().conectarBD();
             PreparedStatement pstm = conn.prepareStatement(sql);
             ResultSet rs = pstm.executeQuery()) {
            while (rs.next()) {
                AlimentoDTO dto = new AlimentoDTO();
                dto.setId(rs.getInt("id"));
                dto.setAlimento(rs.getString("alimento"));
                dto.setCalorias(rs.getDouble("calorias"));
                dto.setNatural(rs.getBoolean("natural"));
                lista.add(dto);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro DAO Listar: " + e.getMessage());
        }
        return lista;
    }

    @Override
    public void alterarAlimento(AlimentoDTO obj) {
        String sql = "UPDATE alimento SET alimento = ?, calorias = ?, \"natural\" = ? WHERE id = ?";
        try (Connection conn = new ConexaoBD().conectarBD();
             PreparedStatement pstm = conn.prepareStatement(sql)) {
            pstm.setString(1, obj.getAlimento());
            pstm.setDouble(2, obj.getCalorias());
            pstm.setBoolean(3, obj.isNatural());
            pstm.setInt(4, obj.getId());
            pstm.execute();
        } catch (SQLException e) {
            throw new RuntimeException("Erro DAO Alterar: " + e.getMessage());
        }
    }

    @Override
    public void excluirAlimento(int id) {
        String sql = "DELETE FROM alimento WHERE id = ?";
        try (Connection conn = new ConexaoBD().conectarBD();
             PreparedStatement pstm = conn.prepareStatement(sql)) {
            pstm.setInt(1, id);
            pstm.execute();
        } catch (SQLException e) {
            throw new RuntimeException("Erro DAO Excluir: " + e.getMessage());
        }
    }
}