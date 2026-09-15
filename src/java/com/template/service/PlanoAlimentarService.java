package com.template.service;

import com.template.model.dto.PlanoAlimentarDTO;
import com.template.model.dao.IPlanoAlimentarDAO;
import com.template.validator.PlanoAlimentarValidador;

import java.util.List;

public class PlanoAlimentarService {
    private final IPlanoAlimentarDAO planoDAO;

    public PlanoAlimentarService(IPlanoAlimentarDAO planoDAO) {
        this.planoDAO = planoDAO;
    }

    public void cadastrarPlano(String paciente, String metaCalorias) {
        PlanoAlimentarValidador.validar(paciente, metaCalorias);
        double meta = Double.parseDouble(metaCalorias);
        PlanoAlimentarDTO dto = new PlanoAlimentarDTO(0, paciente, meta);
        planoDAO.cadastrarPlano(dto);
    }

    public List<PlanoAlimentarDTO> obterTodosPlanos() {
        return planoDAO.listarPlanos();
    }
}