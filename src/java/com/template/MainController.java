package com.template;

import com.template.util.DialogUtil;
import com.template.util.FormUtil;
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
    private FormUtil formUtil; // Gerenciador do formulário integrado

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

    // ==========================================
    // MÉTODOS DE AÇÃO (FLUXO PRINCIPAL)
    // ==========================================

    @FXML
    private void btnSalvarAction(ActionEvent event) {
        if (!validarFormulario()) return;

        AlimentoDTO dto = formUtil.extrairDadosDaTela();
        alimentoDAO.cadastrarAlimento(dto);

        finalizarAcao("Alimento cadastrado com sucesso!", false);
    }

    @FXML
    private void btnAlterarAction(ActionEvent event) {
        AlimentoDTO selecionado = tblTabelaNutricional.getSelectionModel().getSelectedItem();
        if (selecionado == null) return;

        if (!validarFormulario()) return;

        AlimentoDTO dto = formUtil.extrairDadosDaTela();
        dto.setId(selecionado.getId());

        alimentoDAO.alterarAlimento(dto);

        finalizarAcao("Alimento atualizado com sucesso!", false);
    }

    @FXML
    private void btnDeletarAction(ActionEvent event) {
        AlimentoDTO selecionado = tblTabelaNutricional.getSelectionModel().getSelectedItem();
        if (selecionado == null) return;

        boolean confirmou = DialogUtil.showConfirmation(
                "Deseja realmente excluir o alimento '" + selecionado.getAlimento() + "'?"
        );

        if (confirmou) {
            alimentoDAO.excluirAlimento(selecionado.getId());
            finalizarAcao("Alimento excluído com sucesso!", false);
        }
    }

    @FXML
    private void btnListarAction() {
        ArrayList<AlimentoDTO> lista = alimentoDAO.listaAlimentos();
        tblTabelaNutricional.setItems(FXCollections.observableArrayList(lista));
        txtTitulo.setText("CRUD TABELA NUTRICIONAL (" + lista.size() + " itens)");
    }

    // ==========================================
    // MÉTODOS COMPLEMENTARES
    // ==========================================

    private boolean validarFormulario() {
        if (!AlimentoValidador.validarCampos(txtAlimento.getText(), txtCalorias.getText())) {
            formUtil.notificarUsuario("Por favor, preencha todos os campos com valores válidos!", true);
            return false;
        }
        return true;
    }

    private void finalizarAcao(String mensagem, boolean ehErro) {
        btnListarAction();
        formUtil.limparCampos();
        formUtil.notificarUsuario(mensagem, ehErro);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Inicializa o utilitário do formulário passando os componentes injetados
        this.formUtil = new FormUtil(txtAlimento, txtCalorias, rbtnNaturalSim,
                rbtnNaturalNao, btnAlterar, btnDeletar, lblStatus);

        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colAlimento.setCellValueFactory(new PropertyValueFactory<>("alimento"));
        colCalorias.setCellValueFactory(new PropertyValueFactory<>("calorias"));
        colNatural.setCellValueFactory(new PropertyValueFactory<>("natural"));

        btnAlterar.setDisable(true);
        btnDeletar.setDisable(true);
        if (lblStatus != null) lblStatus.setText("");

        // Vincula a seleção da tabela ao preenchimento automático via formUtil
        tblTabelaNutricional.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> {
            if (newSel != null) {
                formUtil.popularCampos(newSel);
            }
        });

        // Restringe a entrada de caracteres não numéricos na calorias
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