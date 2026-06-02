package com.template;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AlimentoDAO {
    private static final Logger logger = Logger.getLogger(AlimentoDAO.class.getName());
    private ArrayList<AlimentoDTO> listaAlimento = new ArrayList<>();

    public void cadastrarAlimento(AlimentoDTO dto) {
        String sql = "INSERT INTO alimento (alimento, calorias, \"natural\") VALUES (?, ?, ?)";
        // Ajustado para usar a classe ConexaoBD e o método conectarBD()
        try (Connection conn = new ConexaoBD().conectarBD();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, dto.getAlimento());
            ps.setDouble(2, dto.getCalorias());
            ps.setBoolean(3, dto.isNatural());
            ps.execute();

            logger.info("Alimento cadastrado com sucesso: " + dto.getAlimento());
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Erro ao cadastrar alimento", e);
        }
    }

    public ArrayList<AlimentoDTO> listaAlimentos() {
        String sql = "SELECT * FROM alimento";
        listaAlimento.clear(); // Evita duplicar itens na interface do JavaFX ao atualizar

        // Ajustado para usar a classe ConexaoBD e o método conectarBD()
        try (Connection c = new ConexaoBD().conectarBD();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                AlimentoDTO alimento = new AlimentoDTO();
                alimento.setId(rs.getInt("id"));
                alimento.setAlimento(rs.getString("alimento"));
                alimento.setCalorias(rs.getDouble("calorias"));
                alimento.setNatural(rs.getBoolean("natural"));

                listaAlimento.add(alimento);
            }

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Erro ao listar alimentos", e);
        }

        return listaAlimento;
    }

    public void alterarAlimento(AlimentoDTO dto) {
        String sql = "UPDATE alimento SET alimento=?, calorias=?, \"natural\"=? WHERE id=?";
        // Ajustado para usar a classe ConexaoBD e o método conectarBD()
        try (Connection conn = new ConexaoBD().conectarBD();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, dto.getAlimento());
            ps.setDouble(2, dto.getCalorias());
            ps.setBoolean(3, dto.isNatural());
            ps.setInt(4, dto.getId());
            ps.executeUpdate();

            logger.info("Alimento alterado com sucesso! ID: " + dto.getId());
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Erro ao alterar alimento", e);
        }
    }

    public void excluirAlimento(int id) {
        String sql = "DELETE FROM alimento WHERE id = ?";
        // Ajustado para usar a classe ConexaoBD e o método conectarBD()
        try (Connection conn = new ConexaoBD().conectarBD();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.execute();

            logger.info("Alimento excluído com sucesso! ID: " + id);
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Erro ao excluir alimento", e);
        }
    }
}