package com.template;

import com.template.util.DialogUtil;
import com.template.util.FormUtil;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    // DIP: O Controller interage com a camada de Serviço
    private final AlimentoService alimentoService = new AlimentoService(new AlimentoDAO());

    @FXML private Button btnSalvar;
    @FXML private Button btnListar;
    @FXML private Button btnAlterar;
    @FXML private Button btnDeletar;

    @FXML private TextField txtAlimento;
    @FXML private TextField txtCalorias;
    @FXML private Label lblStatus;

    @FXML private RadioButton rbtnNaturalSim;
    @FXML private RadioButton rbtnNaturalNao;

    @FXML private TableView<AlimentoDTO> tblTabelaNutricional;
    @FXML private TableColumn<AlimentoDTO, Integer> colId;
    @FXML private TableColumn<AlimentoDTO, String> colAlimento;
    @FXML private TableColumn<AlimentoDTO, Double> colCalorias;
    @FXML private TableColumn<AlimentoDTO, Boolean> colNatural;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarColunasTabela();
        configurarListeners();
        configurarGruposRadioButton();
        btnListarAction();
    }

    private void configurarColunasTabela() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colAlimento.setCellValueFactory(new PropertyValueFactory<>("alimento"));
        colCalorias.setCellValueFactory(new PropertyValueFactory<>("calorias"));
        colNatural.setCellValueFactory(new PropertyValueFactory<>("natural"));
    }

    private void configurarListeners() {
        tblTabelaNutricional.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> {
            if (newSel != null) {
                popularCampos(newSel);
            }
        });
    }

    private void configurarGruposRadioButton() {
        ToggleGroup grupoNatural = new ToggleGroup();
        rbtnNaturalSim.setToggleGroup(grupoNatural);
        rbtnNaturalNao.setToggleGroup(grupoNatural);
    }

    // ==========================================
    // MÉTODOS DE AÇÃO DO FXML (Nomes Exatos)
    // ==========================================

    @FXML
    private void btnSalvarAction(ActionEvent event) {
        try {
            AlimentoDTO dto = FormUtil.extrairDados(txtAlimento, txtCalorias, rbtnNaturalSim);
            alimentoService.salvarAlimento(dto, txtCalorias.getText());

            DialogUtil.showInfo("Alimento cadastrado com sucesso!");
            finalizarAcao();
        } catch (Exception e) {
            DialogUtil.showError(e.getMessage());
        }
    }

    @FXML
    private void btnAlterarAction(ActionEvent event) {
        AlimentoDTO selecionado = tblTabelaNutricional.getSelectionModel().getSelectedItem();
        if (selecionado == null) {
            DialogUtil.showError("Selecione um item na tabela para alterar!");
            return;
        }

        try {
            AlimentoDTO dto = FormUtil.extrairDados(txtAlimento, txtCalorias, rbtnNaturalSim);
            dto.setId(selecionado.getId());

            alimentoService.atualizarAlimento(dto, txtCalorias.getText());

            DialogUtil.showInfo("Alimento atualizado com sucesso!");
            finalizarAcao();
        } catch (Exception e) {
            DialogUtil.showError(e.getMessage());
        }
    }

    @FXML
    private void btnDeletarAction(ActionEvent event) {
        AlimentoDTO selecionado = tblTabelaNutricional.getSelectionModel().getSelectedItem();
        if (selecionado == null) {
            DialogUtil.showError("Selecione um item na tabela para excluir!");
            return;
        }

        boolean confirmou = DialogUtil.confirm("Deseja realmente excluir o alimento '" + selecionado.getAlimento() + "'?");
        if (confirmou) {
            try {
                alimentoService.deletarAlimento(selecionado.getId());
                DialogUtil.showInfo("Alimento excluído com sucesso!");
                finalizarAcao();
            } catch (Exception e) {
                DialogUtil.showError(e.getMessage());
            }
        }
    }

    @FXML
    private void btnListarAction() {
        try {
            ArrayList<AlimentoDTO> lista = alimentoService.listarTudo();
            tblTabelaNutricional.setItems(FXCollections.observableArrayList(lista));
        } catch (Exception e) {
            DialogUtil.showError("Erro ao carregar lista: " + e.getMessage());
        }
    }

    // ==========================================
    // MÉTODOS AUXILIARES
    // ==========================================

    private void popularCampos(AlimentoDTO dto) {
        txtAlimento.setText(dto.getAlimento());
        txtCalorias.setText(String.valueOf(dto.getCalorias()));
        if (dto.isNatural()) {
            rbtnNaturalSim.setSelected(true);
        } else {
            rbtnNaturalNao.setSelected(true);
        }
        btnAlterar.setDisable(false);
        btnDeletar.setDisable(false);
    }

    private void finalizarAcao() {
        btnListarAction();
        FormUtil.limpar(txtAlimento, txtCalorias, rbtnNaturalSim, rbtnNaturalNao);
        btnAlterar.setDisable(true);
        btnDeletar.setDisable(true);
    }
}