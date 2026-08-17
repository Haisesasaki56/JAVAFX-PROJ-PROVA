package com.template.util;

import com.template.AlimentoDTO;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;

public class FormUtil {
    public static AlimentoDTO extrairDados(TextField txtAlimento, TextField txtCalorias, RadioButton rbtnSim) {
        AlimentoDTO dto = new AlimentoDTO();
        dto.setAlimento(txtAlimento.getText());
        try {
            dto.setCalorias(Double.parseDouble(txtCalorias.getText()));
        } catch (Exception e) { dto.setCalorias(0.0); }
        dto.setNatural(rbtnSim.isSelected());
        return dto;
    }

    public static void limpar(TextField t1, TextField t2, RadioButton r1, RadioButton r2) {
        t1.clear(); t2.clear();
        r1.setSelected(false); r2.setSelected(false);
        t1.requestFocus();
    }
}