package com.template;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    @FXML private Button btnSalvar;
    @FXML private Button btnListar;
    @FXML private Button btnAlterar;
    @FXML private Button btnDeletar;

    @FXML private TextField txtAlimento;
    @FXML private TextField txtCalorias;
    @FXML private Text txtTitulo;

    // UI/UX: Label de feedback exigido pelo critério de avaliação
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
        if (txtAlimento.getText().trim().isEmpty() || txtCalorias.getText().trim().isEmpty()) {
            notificarUsuario("Por favor, preencha todos os campos!", true);
            return;
        }

        AlimentoDTO dto = new AlimentoDTO();
        dto.setAlimento(txtAlimento.getText().trim());
        dto.setCalorias(Double.parseDouble(txtCalorias.getText()));
        dto.setNatural(rbtnNaturalSim.isSelected());

        new AlimentoDAO().cadastrarAlimento(dto);

        btnListarAction();
        limparCampos();
        notificarUsuario("Alimento cadastrado com sucesso!", false);
    }

    // =========================
    // LISTAR & CONTADOR
    // =========================
    @FXML
    private void btnListarAction() {
        ArrayList<AlimentoDTO> lista = new AlimentoDAO().listaAlimentos();
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

        if (txtAlimento.getText().trim().isEmpty() || txtCalorias.getText().trim().isEmpty()) {
            notificarUsuario("Os campos não podem ficar vazios na edição.", true);
            return;
        }

        AlimentoDTO dto = new AlimentoDTO();
        dto.setId(selecionado.getId());
        dto.setAlimento(txtAlimento.getText().trim());
        dto.setCalorias(Double.parseDouble(txtCalorias.getText()));
        dto.setNatural(rbtnNaturalSim.isSelected());

        new AlimentoDAO().alterarAlimento(dto);

        btnListarAction();
        limparCampos();
        notificarUsuario("Alimento atualizado com sucesso!", false);
    }

    // =========================
    // DELETAR (COM CONFIRMAÇÃO)
    // =========================
    @FXML
    private void btnDeletarAction(ActionEvent event) {
        AlimentoDTO selecionado = tblTabelaNutricional.getSelectionModel().getSelectedItem();

        if (selecionado == null) return;

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmar Exclusão");
        alert.setHeaderText(null);
        alert.setContentText("Deseja realmente excluir o alimento '" + selecionado.getAlimento() + "'?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            new AlimentoDAO().excluirAlimento(selecionado.getId());
            btnListarAction();
            limparCampos();
            notificarUsuario("Alimento excluído com sucesso!", false);
        }
    }

    // =========================
    // LIMPAR CAMPOS & FOCO
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

    // =========================
    // SELECIONAR LINHA
    // =========================
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
            lblStatus.setText(""); // Limpa avisos anteriores ao selecionar novo item
        }
    }

    // =========================
    // GERENCIADOR DE MENSAGENS (LABEL)
    // =========================
    private void notificarUsuario(String mensagem, boolean ehErro) {
        lblStatus.setText(mensagem);
        if (ehErro) {
            // Texto em vermelho vivo para destacar erros sobre o fundo verde
            lblStatus.setStyle("-fx-text-fill: #FF3333; -fx-font-weight: bold;");
        } else {
            // Texto em amarelo ou branco para indicar sucesso de forma legível no verde
            lblStatus.setStyle("-fx-text-fill: #FFFF00; -fx-font-weight: bold;");
        }
    }

    // =========================
    // INIT
    // =========================
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