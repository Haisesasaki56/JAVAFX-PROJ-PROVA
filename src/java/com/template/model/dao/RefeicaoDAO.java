package com.template.model.dao;

import com.template.model.ConexaoBD;
import com.template.model.dto.RefeicaoDTO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RefeicaoDAO implements IRefeicaoDAO {

    @Override
    public void salvar(RefeicaoDTO dto) {
        String sql = "INSERT INTO refeicao (nome, horario) VALUES (?, ?)";
        try (Connection conn = ConexaoBD.conectarBD();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, dto.getNome());
            stmt.setString(2, dto.getHorario());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar refeição: " + e.getMessage(), e);
        }
    }

    @Override
    public void atualizar(RefeicaoDTO dto) {
        String sql = "UPDATE refeicao SET nome = ?, horario = ? WHERE id = ?";
        try (Connection conn = ConexaoBD.conectarBD();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, dto.getNome());
            stmt.setString(2, dto.getHorario());
            stmt.setInt(3, dto.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar refeição: " + e.getMessage(), e);
        }
    }

    @Override
    public void deletar(int id) {
        String sql = "DELETE FROM refeicao WHERE id = ?";
        try (Connection conn = ConexaoBD.conectarBD();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar refeição: " + e.getMessage(), e);
        }
    }

    @Override
    public List<RefeicaoDTO> buscarTodos() {
        List<RefeicaoDTO> lista = new ArrayList<>();
        String sql = "SELECT * FROM refeicao ORDER BY id";
        try (Connection conn = ConexaoBD.conectarBD();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                RefeicaoDTO dto = new RefeicaoDTO();
                dto.setId(rs.getInt("id"));
                dto.setNome(rs.getString("nome"));
                dto.setHorario(rs.getString("horario"));
                lista.add(dto);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar refeições: " + e.getMessage(), e);
        }
        return lista;
    }
}