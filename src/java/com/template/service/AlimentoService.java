package com.template.service;

import com.template.model.dto.AlimentoDTO;
import com.template.model.dao.IAlimentoDAO;
import com.template.validator.AlimentoValidador;

import java.util.List;

public class AlimentoService {

    private final IAlimentoDAO alimentoDAO;

    public AlimentoService(IAlimentoDAO alimentoDAO) {
        this.alimentoDAO = alimentoDAO;
    }

    public void salvarAlimento(AlimentoDTO dto, String caloriasTexto) {
        AlimentoValidador.validar(dto, caloriasTexto);
        alimentoDAO.salvar(dto);
    }

    public List<AlimentoDTO> listarTudo() {
        return alimentoDAO.buscarTodos();
    }

    public void deletarAlimento(int id) {
        alimentoDAO.deletar(id);
    }

    public void atualizarAlimento(AlimentoDTO dto, String caloriasTexto) {
        AlimentoValidador.validar(dto, caloriasTexto);
        alimentoDAO.atualizar(dto);
    }
}