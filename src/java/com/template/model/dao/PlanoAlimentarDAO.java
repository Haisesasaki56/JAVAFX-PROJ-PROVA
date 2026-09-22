package com.template.model.dao;

import com.template.model.ConexaoBD;
import com.template.model.dto.PlanoAlimentarDTO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PlanoAlimentarDAO implements IPlanoAlimentarDAO {

    @Override
    public void salvar(PlanoAlimentarDTO dto) {
        String sql = "INSERT INTO plano_alimentar (paciente, meta_calorias) VALUES (?, ?)";
        try (Connection conn = ConexaoBD.conectarBD();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, dto.getPaciente());
            stmt.setDouble(2, dto.getMetaCalorias());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar plano alimentar: " + e.getMessage(), e);
        }
    }

    @Override
    public void atualizar(PlanoAlimentarDTO dto) {
        String sql = "UPDATE plano_alimentar SET paciente = ?, meta_calorias = ? WHERE id = ?";
        try (Connection conn = ConexaoBD.conectarBD();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, dto.getPaciente());
            stmt.setDouble(2, dto.getMetaCalorias());
            stmt.setInt(3, dto.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar plano alimentar: " + e.getMessage(), e);
        }
    }

    @Override
    public void deletar(int id) {
        String sql = "DELETE FROM plano_alimentar WHERE id = ?";
        try (Connection conn = ConexaoBD.conectarBD();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar plano alimentar: " + e.getMessage(), e);
        }
    }

    @Override
    public List<PlanoAlimentarDTO> buscarTodos() {
        List<PlanoAlimentarDTO> lista = new ArrayList<>();
        String sql = "SELECT * FROM plano_alimentar ORDER BY id";
        try (Connection conn = ConexaoBD.conectarBD();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                PlanoAlimentarDTO dto = new PlanoAlimentarDTO();
                dto.setId(rs.getInt("id"));
                dto.setPaciente(rs.getString("paciente"));
                dto.setMetaCalorias(rs.getDouble("meta_calorias"));
                lista.add(dto);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar planos alimentares: " + e.getMessage(), e);
        }
        return lista;
    }
}