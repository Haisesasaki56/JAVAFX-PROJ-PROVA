package com.template;

import com.template.util.DialogUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AlimentoDAO {

    private static final Logger logger = Logger.getLogger(AlimentoDAO.class.getName());

    // CADASTRAR ALIMENTO
    public void cadastrarAlimento(AlimentoDTO objAlimentoDTO) {
        // Envolvendo "natural" com aspas para evitar erro de palavra reservada
        String sql = "INSERT INTO alimento (alimento, calorias, \"natural\") VALUES (?, ?, ?)";

        try (Connection conn = new ConexaoBD().conectarBD();
             PreparedStatement pstm = conn.prepareStatement(sql)) {

            pstm.setString(1, objAlimentoDTO.getAlimento());
            pstm.setDouble(2, objAlimentoDTO.getCalorias());
            pstm.setBoolean(3, objAlimentoDTO.isNatural());

            pstm.execute();

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Erro ao cadastrar alimento", e);
            DialogUtil.showError("Erro ao cadastrar o alimento no banco de dados.");
        }
    }

    // LISTAR ALIMENTOS
    public ArrayList<AlimentoDTO> listaAlimentos() {
        String sql = "SELECT * FROM alimento";
        ArrayList<AlimentoDTO> lista = new ArrayList<>();

        try (Connection conn = new ConexaoBD().conectarBD();
             PreparedStatement pstm = conn.prepareStatement(sql);
             ResultSet rs = pstm.executeQuery()) {

            while (rs.next()) {
                AlimentoDTO objAlimentoDTO = new AlimentoDTO();
                objAlimentoDTO.setId(rs.getInt("id"));
                objAlimentoDTO.setAlimento(rs.getString("alimento"));
                objAlimentoDTO.setCalorias(rs.getDouble("calorias"));
                objAlimentoDTO.setNatural(rs.getBoolean("natural"));

                lista.add(objAlimentoDTO);
            }

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Erro ao pesquisar alimentos", e);
            DialogUtil.showError("Erro ao carregar a lista de alimentos do banco de dados.");
        }
        return lista;
    }

    // ALTERAR ALIMENTO
    public void alterarAlimento(AlimentoDTO objAlimentoDTO) {
        // Envolvendo "natural" com aspas para evitar erro de palavra reservada
        String sql = "UPDATE alimento SET alimento = ?, calorias = ?, \"natural\" = ? WHERE id = ?";

        try (Connection conn = new ConexaoBD().conectarBD();
             PreparedStatement pstm = conn.prepareStatement(sql)) {

            pstm.setString(1, objAlimentoDTO.getAlimento());
            pstm.setDouble(2, objAlimentoDTO.getCalorias());
            pstm.setBoolean(3, objAlimentoDTO.isNatural());
            pstm.setInt(4, objAlimentoDTO.getId());

            pstm.execute();

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Erro ao alterar alimento", e);
            DialogUtil.showError("Erro ao atualizar as informações do alimento.");
        }
    }

    // EXCLUIR ALIMENTO
    public void excluirAlimento(int idAlimento) {
        String sql = "DELETE FROM alimento WHERE id = ?";

        try (Connection conn = new ConexaoBD().conectarBD();
             PreparedStatement pstm = conn.prepareStatement(sql)) {

            pstm.setInt(1, idAlimento);
            pstm.execute();

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Erro ao excluir alimento", e);
            DialogUtil.showError("Erro ao remover o alimento do banco de dados.");
        }
    }
}