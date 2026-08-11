package com.template.util;

import com.template.AlimentoDTO;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;

public class FormUtil {

    private final TextField txtAlimento;
    private final TextField txtCalorias;
    private final RadioButton rbtnNaturalSim;
    private final RadioButton rbtnNaturalNao;
    private final Button btnAlterar;
    private final Button btnDeletar;
    private final Label lblStatus;

    public FormUtil(TextField txtAlimento, TextField txtCalorias,
                    RadioButton rbtnNaturalSim, RadioButton rbtnNaturalNao,
                    Button btnAlterar, Button btnDeletar, Label lblStatus) {
        this.txtAlimento = txtAlimento;
        this.txtCalorias = txtCalorias;
        this.rbtnNaturalSim = rbtnNaturalSim;
        this.rbtnNaturalNao = rbtnNaturalNao;
        this.btnAlterar = btnAlterar;
        this.btnDeletar = btnDeletar;
        this.lblStatus = lblStatus;
    }

    /**
     * Extrai e monta o DTO a partir dos componentes da tela.
     */
    public AlimentoDTO extrairDadosDaTela() {
        AlimentoDTO dto = new AlimentoDTO();
        dto.setAlimento(txtAlimento.getText().trim());
        dto.setCalorias(Double.parseDouble(txtCalorias.getText().trim()));
        dto.setNatural(rbtnNaturalSim.isSelected());
        return dto;
    }

    /**
     * Preenche os campos do formulário com os dados do DTO selecionado.
     */
    public void popularCampos(AlimentoDTO dto) {
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
            if (lblStatus != null) lblStatus.setText("");
        }
    }

    /**
     * Limpa as entradas de texto e redefine o estado inicial dos botões.
     */
    public void limparCampos() {
        txtAlimento.clear();
        txtCalorias.clear();
        rbtnNaturalSim.setSelected(false);
        rbtnNaturalNao.setSelected(false);

        btnAlterar.setDisable(true);
        btnDeletar.setDisable(true);
        txtAlimento.requestFocus();
    }

    /**
     * Atualiza o texto e a cor da Label de status inferior.
     */
    public void notificarUsuario(String mensagem, boolean ehErro) {
        if (lblStatus == null) return;

        lblStatus.setText(mensagem);
        if (ehErro) {
            lblStatus.setStyle("-fx-text-fill: #FF3333; -fx-font-weight: bold;");
        } else {
            lblStatus.setStyle("-fx-text-fill: #FFFF00; -fx-font-weight: bold;");
        }
    }
}