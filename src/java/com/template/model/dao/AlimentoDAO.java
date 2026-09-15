package com.template.model.dao;

import com.template.model.dto.AlimentoDTO;
import com.template.model.ConexaoBD;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AlimentoDAO implements IAlimentoDAO {

    private final ConexaoBD conexaoBD = new ConexaoBD();

    @Override
    public void salvar(AlimentoDTO alimento) {
        String sql = "INSERT INTO alimento (nome, calorias, \"natural\") VALUES (?, ?, ?)";
        try (Connection conn = conexaoBD.conectarBD();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, alimento.getNome());
            stmt.setDouble(2, alimento.getCalorias());
            stmt.setBoolean(3, alimento.getNatural() != null && alimento.getNatural());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar alimento no banco: " + e.getMessage(), e);
        }
    }

    @Override
    public void atualizar(AlimentoDTO alimento) {
        String sql = "UPDATE alimento SET nome=?, calorias=?, \"natural\"=? WHERE id=?";
        try (Connection conn = conexaoBD.conectarBD();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, alimento.getNome());
            stmt.setDouble(2, alimento.getCalorias());
            stmt.setBoolean(3, alimento.getNatural() != null && alimento.getNatural());
            stmt.setInt(4, alimento.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar alimento: " + e.getMessage(), e);
        }
    }

    @Override
    public void deletar(Integer id) {
        String sql = "DELETE FROM alimento WHERE id=?";
        try (Connection conn = conexaoBD.conectarBD();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar alimento: " + e.getMessage(), e);
        }
    }

    @Override
    public List<AlimentoDTO> buscarTodos() {
        List<AlimentoDTO> alimentos = new ArrayList<>();
        String sql = "SELECT id, nome, calorias, \"natural\" FROM alimento ORDER BY id";
        try (Connection conn = conexaoBD.conectarBD();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                alimentos.add(extrairDoResultSet(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar alimentos: " + e.getMessage(), e);
        }
        return alimentos;
    }

    @Override
    public AlimentoDTO buscarPorId(Integer id) {
        String sql = "SELECT id, nome, calorias, \"natural\" FROM alimento WHERE id=?";
        try (Connection conn = conexaoBD.conectarBD();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return extrairDoResultSet(rs);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar por ID: " + e.getMessage(), e);
        }
        return null;
    }

    private AlimentoDTO extrairDoResultSet(ResultSet rs) throws SQLException {
        AlimentoDTO dto = new AlimentoDTO();
        dto.setId(rs.getInt("id"));
        dto.setNome(rs.getString("nome"));
        dto.setCalorias(rs.getDouble("calorias"));
        dto.setNatural(rs.getBoolean("natural"));
        return dto;
    }
}