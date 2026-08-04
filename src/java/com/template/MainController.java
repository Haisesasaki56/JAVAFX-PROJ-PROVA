package com.template;

import com.template.util.DialogUtil;
import com.template.validator.AlimentoValidador;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    private final AlimentoDAO alimentoDAO = new AlimentoDAO();

    @FXML private Button btnSalvar;
    @FXML private Button btnListar;
    @FXML private Button btnAlterar;
    @FXML private Button btnDeletar;

    @FXML private TextField txtAlimento;
    @FXML private TextField txtCalorias;
    @FXML private Text txtTitulo;

    @FXML private Label lblStatus;

    @FXML private RadioButton rbtnNaturalSim;
    @FXML private RadioButton rbtnNaturalNao;

    @FXML private TableView<AlimentoDTO> tblTabelaNutricional;

    @FXML private TableColumn<AlimentoDTO, Integer> colId;
    @FXML private TableColumn<AlimentoDTO, String> colAlimento;
    @FXML private TableColumn<AlimentoDTO, Double> colCalorias;
    @FXML private TableColumn<AlimentoDTO, Boolean> colNatural;

    // =========================
    // SALVAR
    // =========================
    @FXML
    private void btnSalvarAction(ActionEvent event) {
        if (!AlimentoValidador.validarCampos(txtAlimento.getText(), txtCalorias.getText())) {
            notificarUsuario("Por favor, preencha todos os campos com valores válidos!", true);
            return;
        }

        AlimentoDTO dto = new AlimentoDTO();
        dto.setAlimento(txtAlimento.getText().trim());
        dto.setCalorias(Double.parseDouble(txtCalorias.getText()));
        dto.setNatural(rbtnNaturalSim.isSelected());

        alimentoDAO.cadastrarAlimento(dto);

        btnListarAction();
        limparCampos();
        notificarUsuario("Alimento cadastrado com sucesso!", false);
    }

    // =========================
    // LISTAR & CONTADOR
    // =========================
    @FXML
    private void btnListarAction() {
        ArrayList<AlimentoDTO> lista = alimentoDAO.listaAlimentos();

        tblTabelaNutricional.setItems(FXCollections.observableArrayList(lista));
        txtTitulo.setText("CRUD TABELA NUTRICIONAL (" + lista.size() + " itens)");
    }

    // =========================
    // ALTERAR
    // =========================
    @FXML
    private void btnAlterarAction(ActionEvent event) {
        AlimentoDTO selecionado = tblTabelaNutricional.getSelectionModel().getSelectedItem();
        if (selecionado == null) return;

        if (!AlimentoValidador.validarCampos(txtAlimento.getText(), txtCalorias.getText())) {
            notificarUsuario("Os campos não podem ficar vazios ou inválidos na edição.", true);
            return;
        }

        AlimentoDTO dto = new AlimentoDTO();
        dto.setId(selecionado.getId());
        dto.setAlimento(txtAlimento.getText().trim());
        dto.setCalorias(Double.parseDouble(txtCalorias.getText()));
        dto.setNatural(rbtnNaturalSim.isSelected());

        alimentoDAO.alterarAlimento(dto);

        btnListarAction();
        limparCampos();
        notificarUsuario("Alimento atualizado com sucesso!", false);
    }

    // =========================
    // DELETAR
    // =========================
    @FXML
    private void btnDeletarAction(ActionEvent event) {
        AlimentoDTO selecionado = tblTabelaNutricional.getSelectionModel().getSelectedItem();
        if (selecionado == null) return;

        boolean confirmou = DialogUtil.showConfirmation("Deseja realmente excluir o alimento '" + selecionado.getAlimento() + "'?");

        if (confirmou) {
            alimentoDAO.excluirAlimento(selecionado.getId());
            btnListarAction();
            limparCampos();
            notificarUsuario("Alimento excluído com sucesso!", false);
        }
    }

    // =========================
    // AUXILIARES
    // =========================
    private void limparCampos() {
        txtAlimento.clear();
        txtCalorias.clear();
        rbtnNaturalSim.setSelected(false);
        rbtnNaturalNao.setSelected(false);

        btnAlterar.setDisable(true);
        btnDeletar.setDisable(true);
        txtAlimento.requestFocus();
    }

    @FXML
    private void selecionarItem() {
        AlimentoDTO dto = tblTabelaNutricional.getSelectionModel().getSelectedItem();

        if (dto != null) {
            txtAlimento.setText(dto.getAlimento());
            txtCalorias.setText(String.valueOf(dto.getCalorias()));

            if (dto.isNatural()) {
                rbtnNaturalSim.setSelected(true);
            } else {
                rbtnNaturalNao.setSelected(true);
            }

            btnAlterar.setDisable(false);
            btnDeletar.setDisable(false);
            lblStatus.setText("");
        }
    }

    private void notificarUsuario(String mensagem, boolean ehErro) {
        lblStatus.setText(mensagem);
        if (ehErro) {
            lblStatus.setStyle("-fx-text-fill: #FF3333; -fx-font-weight: bold;");
        } else {
            lblStatus.setStyle("-fx-text-fill: #FFFF00; -fx-font-weight: bold;");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colAlimento.setCellValueFactory(new PropertyValueFactory<>("alimento"));
        colCalorias.setCellValueFactory(new PropertyValueFactory<>("calorias"));
        colNatural.setCellValueFactory(new PropertyValueFactory<>("natural"));

        btnAlterar.setDisable(true);
        btnDeletar.setDisable(true);
        if (lblStatus != null) lblStatus.setText("");

        tblTabelaNutricional.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                selecionarItem();
            }
        });

        txtCalorias.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*([\\.]\\d*)?")) {
                txtCalorias.setText(oldValue);
            }
        });

        ToggleGroup grupoNatural = new ToggleGroup();
        rbtnNaturalSim.setToggleGroup(grupoNatural);
        rbtnNaturalNao.setToggleGroup(grupoNatural);

        btnListarAction();
    }
}