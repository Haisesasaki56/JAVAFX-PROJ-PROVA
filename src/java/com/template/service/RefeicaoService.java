package com.template.service;

import com.template.model.dto.RefeicaoDTO;
import com.template.model.dao.IRefeicaoDAO;
import com.template.validator.RefeicaoValidador;
import java.util.List;

public class RefeicaoService {
    private final IRefeicaoDAO refeicaoDAO;

    public RefeicaoService(IRefeicaoDAO refeicaoDAO) {
        this.refeicaoDAO = refeicaoDAO;
    }

    public void cadastrarRefeicao(String nome, String horario) {
        RefeicaoValidador.validar(nome, horario);
        RefeicaoDTO dto = new RefeicaoDTO(0, nome, horario);
        refeicaoDAO.salvar(dto);
    }

    public List<RefeicaoDTO> obterTodasRefeicoes() {
        return refeicaoDAO.listar();
    }
}