package com.template;

import static com.template.util.DialogUtil.*;
import static com.template.util.FormUtil.*;

import com.template.model.dao.AlimentoDAO;
import com.template.model.dto.AlimentoDTO;
import com.template.service.AlimentoService;
import com.template.validator.AlimentoValidador;
import com.template.validator.IAlimentoValidador;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.util.List;

public class MainController {

    private final IAlimentoValidador validador;
    private final AlimentoService alimentoService;
    private ToggleGroup tgNatural;

    public MainController() {
        this.validador = new AlimentoValidador();
        this.alimentoService = new AlimentoService(new AlimentoDAO());
    }

    public MainController(IAlimentoValidador validador) {
        this.validador = validador;
        this.alimentoService = new AlimentoService(new AlimentoDAO());
    }

    public MainController(IAlimentoValidador validador, AlimentoService alimentoService) {
        this.validador = validador;
        this.alimentoService = alimentoService;
    }

    @FXML private TextField txtAlimento, txtCalorias;
    @FXML private RadioButton rbtnNaturalSim, rbtnNaturalNao;
    @FXML private Button btnSalvar, btnListar, btnAlterar, btnDeletar;
    @FXML private Label lblStatus, lblContador;

    @FXML private TableView<AlimentoDTO> tblTabelaNutricional;
    @FXML private TableColumn<AlimentoDTO, Integer> colId;
    @FXML private TableColumn<AlimentoDTO, String> colAlimento;
    @FXML private TableColumn<AlimentoDTO, Double> colCalorias;
    @FXML private TableColumn<AlimentoDTO, Boolean> colNatural;

    @FXML
    private void btnSalvarAction(ActionEvent event) {
        String[] campos = extrairCampos(txtAlimento, txtCalorias);

        if (!validador.validarCamposAlimento(campos[0], campos[1])) {
            return;
        }

        AlimentoDTO dto = extrairDtoDoFormulario(campos);
        alimentoService.salvarAlimento(dto, campos[1]);
        posAcaoSucesso("Alimento salvo com sucesso!");
    }

    @FXML
    private void btnAlterarAction(ActionEvent event) {
        AlimentoDTO selecionado = tblTabelaNutricional.getSelectionModel().getSelectedItem();

        if (selecionado == null) {
            mostrarAlerta("Aviso", "Ação Necessária",
                    "Por favor, selecione um alimento na tabela para poder alterar.", AlertType.WARNING);
            return;
        }

        String[] campos = extrairCampos(txtAlimento, txtCalorias);

        if (!validador.validarCamposAlimento(campos[0], campos[1])) {
            return;
        }

        AlimentoDTO dto = extrairDtoDoFormulario(campos);
        dto.setId(selecionado.getId());

        alimentoService.atualizarAlimento(dto, campos[1]);
        posAcaoSucesso("Alimento alterado com sucesso!");
    }

    @FXML
    private void btnDeletarAction(ActionEvent event) {
        AlimentoDTO selecionado = tblTabelaNutricional.getSelectionModel().getSelectedItem();

        if (selecionado == null) {
            mostrarAlerta("Aviso", "Ação Necessária",
                    "Por favor, selecione um alimento na tabela para deletar.", AlertType.WARNING);
            return;
        }

        alimentoService.deletarAlimento(selecionado.getId());
        posAcaoSucesso("Alimento excluído com sucesso!");
    }

    @FXML
    private void btnListarAction(ActionEvent event) {
        carregarAlimentos();
        mostrarAlerta("Tabela Atualizada", null, "Os dados da tabela foram atualizados.", AlertType.INFORMATION);
    }

    @FXML
    private void btnLimparAction(ActionEvent event) {
        limparFormulario();
        mostrarAlerta("Campos Limpos", null, "Os campos do formulário foram resetados.", AlertType.INFORMATION);
    }

    private void posAcaoSucesso(String mensagem) {
        carregarAlimentos();
        limparFormulario();
        mostrarAlerta("Sucesso", null, mensagem, AlertType.INFORMATION);
    }

    @FXML
    private void carregarAlimentos() {
        if (tblTabelaNutricional != null && alimentoService != null) {
            List<AlimentoDTO> lista = alimentoService.listarTudo();
            tblTabelaNutricional.setItems(FXCollections.observableArrayList(lista));

            if (lblContador != null) {
                lblContador.setText("Total de alimentos cadastrados: " + lista.size());
            }
            if (lblStatus != null) {
                lblStatus.setText("Dados recarregados.");
            }
        }
    }

    @FXML
    private void carregarCampos(MouseEvent event) {
        AlimentoDTO selecionado = tblTabelaNutricional.getSelectionModel().getSelectedItem();
        preencherFormulario(selecionado);
    }

    @FXML
    private void initialize() {
        tgNatural = new ToggleGroup();
        rbtnNaturalSim.setToggleGroup(tgNatural);
        rbtnNaturalNao.setToggleGroup(tgNatural);
        rbtnNaturalSim.setSelected(true);

        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colAlimento.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colCalorias.setCellValueFactory(new PropertyValueFactory<>("calorias"));
        colNatural.setCellValueFactory(new PropertyValueFactory<>("natural"));

        tblTabelaNutricional.getSelectionModel().selectedItemProperty().addListener((obs, oldV, selecionado) -> {
            boolean temSelecao = (selecionado != null);
            if (btnAlterar != null) btnAlterar.setDisable(!temSelecao);
            if (btnDeletar != null) btnDeletar.setDisable(!temSelecao);

            preencherFormulario(selecionado);
        });

        txtAlimento.setOnAction(e -> txtCalorias.requestFocus());
        txtCalorias.setOnAction(e -> btnSalvarAction(null));

        carregarAlimentos();
    }

    private AlimentoDTO extrairDtoDoFormulario(String[] campos) {
        AlimentoDTO dto = new AlimentoDTO();
        dto.setNome(campos[0]);
        dto.setCalorias(Double.parseDouble(campos[1].replace(",", ".")));
        dto.setNatural(rbtnNaturalSim.isSelected());
        return dto;
    }

    private void preencherFormulario(AlimentoDTO selecionado) {
        if (selecionado != null) {
            txtAlimento.setText(selecionado.getNome());
            txtCalorias.setText(String.valueOf(selecionado.getCalorias()));
            if (Boolean.TRUE.equals(selecionado.getNatural())) {
                rbtnNaturalSim.setSelected(true);
            } else {
                rbtnNaturalNao.setSelected(true);
            }
        }
    }

    private void limparFormulario() {
        limparCampos(tblTabelaNutricional, txtAlimento, txtCalorias);
        rbtnNaturalSim.setSelected(true);
        if (btnAlterar != null) btnAlterar.setDisable(true);
        if (btnDeletar != null) btnDeletar.setDisable(true);
    }
}