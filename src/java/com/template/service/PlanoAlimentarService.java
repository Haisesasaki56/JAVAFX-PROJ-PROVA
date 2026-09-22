package com.template.service;

import com.template.model.dao.IPlanoAlimentarDAO;
import com.template.model.dto.PlanoAlimentarDTO;
import com.template.validator.IPlanoAlimentarValidador;
import com.template.validator.PlanoAlimentarValidador;

import java.util.List;

public class PlanoAlimentarService {

    private final IPlanoAlimentarDAO planoDAO;
    private final IPlanoAlimentarValidador validador;

    public PlanoAlimentarService(IPlanoAlimentarDAO planoDAO) {
        this.planoDAO = planoDAO;
        this.validador = new PlanoAlimentarValidador();
    }

    public PlanoAlimentarService(IPlanoAlimentarDAO planoDAO, IPlanoAlimentarValidador validador) {
        this.planoDAO = planoDAO;
        this.validador = validador;
    }

    public void salvar(String paciente, String metaCalorias) {
        validador.validarCamposPlanoAlimentar(paciente, metaCalorias);

        PlanoAlimentarDTO dto = new PlanoAlimentarDTO();
        dto.setPaciente(paciente);
        dto.setMetaCalorias(Double.parseDouble(metaCalorias.replace(",", ".")));

        planoDAO.salvar(dto);
    }

    public void atualizar(int id, String paciente, String metaCalorias) {
        validador.validarCamposPlanoAlimentar(paciente, metaCalorias);

        PlanoAlimentarDTO dto = new PlanoAlimentarDTO();
        dto.setId(id);
        dto.setPaciente(paciente);
        dto.setMetaCalorias(Double.parseDouble(metaCalorias.replace(",", ".")));

        planoDAO.atualizar(dto);
    }

    public void deletar(PlanoAlimentarDTO dto) {
        if (dto != null) {
            planoDAO.deletar(dto.getId());
        }
    }

    public List<PlanoAlimentarDTO> listar() {
        return planoDAO.buscarTodos();
    }
}