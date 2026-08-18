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

    // DIP: Dependência da abstração (Service)
    private final AlimentoService alimentoService = new AlimentoService(new AlimentoDAO());

    @FXML private Button btnSalvar, btnListar, btnAlterar, btnDeletar;
    @FXML private TextField txtAlimento, txtCalorias;
    @FXML private Label lblStatus;
    @FXML private RadioButton rbtnNaturalSim, rbtnNaturalNao;

    @FXML private TableView<AlimentoDTO> tblTabelaNutricional;
    @FXML private TableColumn<AlimentoDTO, Integer> colId;
    @FXML private TableColumn<AlimentoDTO, String> colAlimento;
    @FXML private TableColumn<AlimentoDTO, Double> colCalorias;
    @FXML private TableColumn<AlimentoDTO, Boolean> colNatural;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarColunasTabela();
        configurarListeners(); // <--- Onde o Regex é ativado
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
        // Listener para seleção da tabela
        tblTabelaNutricional.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> {
            if (newSel != null) popularCampos(newSel);
        });

        // ============================================================
        // AQUI ESTÁ O REGEX (MÁSCARA DE ENTRADA)
        // ============================================================
        txtCalorias.textProperty().addListener((obs, oldValue, newValue) -> {
            if (!newValue.matches("\\d*([\\.,]\\d*)?")) {
                txtCalorias.setText(oldValue);
            }
        });
    }

    private void configurarGruposRadioButton() {
        ToggleGroup grupoNatural = new ToggleGroup();
        rbtnNaturalSim.setToggleGroup(grupoNatural);
        rbtnNaturalNao.setToggleGroup(grupoNatural);
    }

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
        if (selecionado == null) return;

        try {
            AlimentoDTO dto = FormUtil.extrairDados(txtAlimento, txtCalorias, rbtnNaturalSim);
            dto.setId(selecionado.getId());
            alimentoService.atualizarAlimento(dto, txtCalorias.getText());
            DialogUtil.showInfo("Alimento atualizado!");
            finalizarAcao();
        } catch (Exception e) {
            DialogUtil.showError(e.getMessage());
        }
    }

    @FXML
    private void btnDeletarAction(ActionEvent event) {
        AlimentoDTO selecionado = tblTabelaNutricional.getSelectionModel().getSelectedItem();
        if (selecionado != null && DialogUtil.confirm("Excluir " + selecionado.getAlimento() + "?")) {
            try {
                alimentoService.deletarAlimento(selecionado.getId());
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
            DialogUtil.showError("Erro ao listar: " + e.getMessage());
        }
    }

    private void popularCampos(AlimentoDTO dto) {
        txtAlimento.setText(dto.getAlimento());
        txtCalorias.setText(String.valueOf(dto.getCalorias()));
        if (dto.isNatural()) rbtnNaturalSim.setSelected(true); else rbtnNaturalNao.setSelected(true);
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