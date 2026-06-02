package com.template;

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

    @FXML private Button btnSalvar;
    @FXML private Button btnListar;
    @FXML private Button btnAlterar;
    @FXML private Button btnDeletar;

    @FXML private TextField txtAlimento;
    @FXML private TextField txtCalorias;

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
        // Validação visual de campos vazios
        if (txtAlimento.getText().trim().isEmpty() || txtCalorias.getText().trim().isEmpty()) {
            mostrarAlerta("Campos Vazios", "Por favor, preencha todos os campos antes de salvar.", Alert.AlertType.WARNING);
            return;
        }

        AlimentoDTO dto = new AlimentoDTO();
        dto.setAlimento(txtAlimento.getText());

        // Tratamento visual para evitar crash por letras nas calorias
        try {
            dto.setCalorias(Double.parseDouble(txtCalorias.getText()));
        } catch (NumberFormatException e) {
            mostrarAlerta("Erro de Formato", "O campo 'Calorias' deve conter apenas números válidos.", Alert.AlertType.ERROR);
            return;
        }

        dto.setNatural(rbtnNaturalSim.isSelected());

        new AlimentoDAO().cadastrarAlimento(dto);

        btnListarAction();
        limparCampos();
    }

    // =========================
    // LISTAR
    // =========================
    @FXML
    private void btnListarAction() {
        ArrayList<AlimentoDTO> lista = new AlimentoDAO().listaAlimentos();
        tblTabelaNutricional.setItems(FXCollections.observableArrayList(lista));
    }

    // =========================
    // ALTERAR (SEM txtId)
    // =========================
    @FXML
    private void btnAlterarAction(ActionEvent event) {
        AlimentoDTO selecionado = tblTabelaNutricional.getSelectionModel().getSelectedItem();

        if (selecionado == null) {
            mostrarAlerta("Nenhum Item Selecionado", "Selecione um alimento na tabela para poder alterá-lo.", Alert.AlertType.WARNING);
            return;
        }

        if (txtAlimento.getText().trim().isEmpty() || txtCalorias.getText().trim().isEmpty()) {
            mostrarAlerta("Campos Vazios", "Os campos de edição não podem ficar em branco.", Alert.AlertType.WARNING);
            return;
        }

        AlimentoDTO dto = new AlimentoDTO();
        dto.setId(selecionado.getId());
        dto.setAlimento(txtAlimento.getText());

        try {
            dto.setCalorias(Double.parseDouble(txtCalorias.getText()));
        } catch (NumberFormatException e) {
            mostrarAlerta("Erro de Formato", "O campo 'Calorias' deve conter apenas números válidos.", Alert.AlertType.ERROR);
            return;
        }

        dto.setNatural(rbtnNaturalSim.isSelected());

        new AlimentoDAO().alterarAlimento(dto);

        btnListarAction();
        limparCampos();
    }

    // =========================
    // DELETAR (SEM txtId)
    // =========================
    @FXML
    private void btnDeletarAction(ActionEvent event) {
        AlimentoDTO selecionado = tblTabelaNutricional.getSelectionModel().getSelectedItem();

        if (selecionado == null) {
            mostrarAlerta("Nenhum Item Selecionado", "Selecione o alimento na tabela que você deseja excluir.", Alert.AlertType.WARNING);
            return;
        }

        new AlimentoDAO().excluirAlimento(selecionado.getId());

        btnListarAction();
        limparCampos();
    }

    // =========================
    // LIMPAR CAMPOS
    // =========================
    private void limparCampos() {
        txtAlimento.clear();
        txtCalorias.clear();
        rbtnNaturalSim.setSelected(false);
        rbtnNaturalNao.setSelected(false);
    }

    // =========================
    // SELECIONAR LINHA (UX MELHOR)
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
        }
    }

    // =========================
    // MÉTODO AUXILIAR PARA ALERTAS VISUAIS
    // =========================
    private void mostrarAlerta(String titulo, String mensagem, Alert.AlertType tipo) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensagem);
        alerta.showAndWait();
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

        // Vincula a seleção por clique e por setas do teclado de forma segura
        tblTabelaNutricional.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                selecionarItem();
            }
        });

        // Cria o grupo e une os botões para restrição exclusiva sim/não
        ToggleGroup grupoNatural = new ToggleGroup();
        rbtnNaturalSim.setToggleGroup(grupoNatural);
        rbtnNaturalNao.setToggleGroup(grupoNatural);

        btnListarAction();
    }
}