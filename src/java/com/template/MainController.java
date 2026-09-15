package com.template;

import com.template.model.dto.AlimentoDTO;
import com.template.model.dao.AlimentoDAO;
import com.template.service.AlimentoService;
import com.template.util.DialogUtil;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

public class MainController {

    @FXML private TextField txtAlimento;
    @FXML private TextField txtCalorias;
    @FXML private RadioButton rbtnNaturalSim;
    @FXML private RadioButton rbtnNaturalNao;

    @FXML private Button btnSalvar;
    @FXML private Button btnListar;
    @FXML private Button btnAlterar;
    @FXML private Button btnDeletar;

    @FXML private Label lblStatus;

    @FXML private TableView<AlimentoDTO> tblTabelaNutricional;
    @FXML private TableColumn<AlimentoDTO, Integer> colId;
    @FXML private TableColumn<AlimentoDTO, String> colAlimento;
    @FXML private TableColumn<AlimentoDTO, Double> colCalorias;
    @FXML private TableColumn<AlimentoDTO, Boolean> colNatural;

    private AlimentoService alimentoService;
    private ToggleGroup tgNatural;
    private final ObservableList<AlimentoDTO> listaAlimentos = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        this.alimentoService = new AlimentoService(new AlimentoDAO());

        // Agrupa os RadioButtons
        tgNatural = new ToggleGroup();
        rbtnNaturalSim.setToggleGroup(tgNatural);
        rbtnNaturalNao.setToggleGroup(tgNatural);
        rbtnNaturalSim.setSelected(true);

        // Mapeamento das colunas com as propriedades do DTO
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colAlimento.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colCalorias.setCellValueFactory(new PropertyValueFactory<>("calorias"));
        colNatural.setCellValueFactory(new PropertyValueFactory<>("natural"));

        // Habilita edição/exclusão ao clicar em uma linha da tabela
        tblTabelaNutricional.getSelectionModel().selectedItemProperty().addListener((obs, antigo, selecionado) -> {
            boolean temSelecao = (selecionado != null);
            btnAlterar.setDisable(!temSelecao);
            btnDeletar.setDisable(!temSelecao);

            if (temSelecao) {
                txtAlimento.setText(selecionado.getNome());
                txtCalorias.setText(String.valueOf(selecionado.getCalorias()));
                if (Boolean.TRUE.equals(selecionado.getNatural())) {
                    rbtnNaturalSim.setSelected(true);
                } else {
                    rbtnNaturalNao.setSelected(true);
                }
            }
        });

        atualizarTabela();
    }

    @FXML
    public void btnSalvarAction() {
        try {
            AlimentoDTO dto = extrairCampos();
            alimentoService.salvarAlimento(dto, txtCalorias.getText());
            lblStatus.setText("Alimento salvo com sucesso!");
            DialogUtil.mostrarSucesso("Alimento salvo com sucesso!");

            limparCampos();
            atualizarTabela();
        } catch (NumberFormatException e) {
            DialogUtil.mostrarErro("Erro de Validação", "Insira um número válido para as calorias.");
        } catch (Exception e) {
            DialogUtil.mostrarErro("Erro Interno", e.getMessage());
        }
    }

    @FXML
    public void btnListarAction() {
        atualizarTabela();
        lblStatus.setText("Tabela atualizada.");
    }

    @FXML
    public void btnAlterarAction() {
        AlimentoDTO selecionado = tblTabelaNutricional.getSelectionModel().getSelectedItem();
        if (selecionado != null) {
            try {
                AlimentoDTO dto = extrairCampos();
                dto.setId(selecionado.getId());

                AlimentoDAO dao = new AlimentoDAO();
                dao.atualizar(dto);

                lblStatus.setText("Alimento alterado com sucesso!");
                limparCampos();
                atualizarTabela();
            } catch (Exception e) {
                DialogUtil.mostrarErro("Erro ao Alterar", e.getMessage());
            }
        }
    }

    @FXML
    public void btnDeletarAction() {
        AlimentoDTO selecionado = tblTabelaNutricional.getSelectionModel().getSelectedItem();
        if (selecionado != null) {
            try {
                AlimentoDAO dao = new AlimentoDAO();
                dao.deletar(selecionado.getId());

                lblStatus.setText("Alimento excluído com sucesso!");
                limparCampos();
                atualizarTabela();
            } catch (Exception e) {
                DialogUtil.mostrarErro("Erro ao Deletar", e.getMessage());
            }
        }
    }

    private AlimentoDTO extrairCampos() {
        AlimentoDTO dto = new AlimentoDTO();
        dto.setNome(txtAlimento.getText());
        dto.setCalorias(Double.parseDouble(txtCalorias.getText()));
        dto.setNatural(rbtnNaturalSim.isSelected());
        return dto;
    }

    private void limparCampos() {
        txtAlimento.clear();
        txtCalorias.clear();
        rbtnNaturalSim.setSelected(true);
        tblTabelaNutricional.getSelectionModel().clearSelection();
        btnAlterar.setDisable(true);
        btnDeletar.setDisable(true);
    }

    private void atualizarTabela() {
        if (tblTabelaNutricional != null && alimentoService != null) {
            listaAlimentos.clear();
            listaAlimentos.addAll(alimentoService.listarTudo());
            tblTabelaNutricional.setItems(listaAlimentos);
        }
    }
}