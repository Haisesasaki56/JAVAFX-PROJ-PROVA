package com.template.service;

import com.template.model.dao.IRefeicaoDAO;
import com.template.model.dto.RefeicaoDTO;
import com.template.validator.IRefeicaoValidador;
import com.template.validator.RefeicaoValidador;

import java.util.List;

public class RefeicaoService {

    private final IRefeicaoDAO refeicaoDAO;
    private final IRefeicaoValidador validador;

    public RefeicaoService(IRefeicaoDAO refeicaoDAO) {
        this.refeicaoDAO = refeicaoDAO;
        this.validador = new RefeicaoValidador();
    }

    public RefeicaoService(IRefeicaoDAO refeicaoDAO, IRefeicaoValidador validador) {
        this.refeicaoDAO = refeicaoDAO;
        this.validador = validador;
    }

    public void salvar(String nome, String horario) {
        validador.validarCamposRefeicao(nome, horario);

        RefeicaoDTO dto = new RefeicaoDTO();
        dto.setNome(nome);
        dto.setHorario(horario);

        refeicaoDAO.salvar(dto);
    }

    public void atualizar(int id, String nome, String horario) {
        validador.validarCamposRefeicao(nome, horario);

        RefeicaoDTO dto = new RefeicaoDTO();
        dto.setId(id);
        dto.setNome(nome);
        dto.setHorario(horario);

        refeicaoDAO.atualizar(dto);
    }

    public void deletar(RefeicaoDTO dto) {
        if (dto != null) {
            refeicaoDAO.deletar(dto.getId());
        }
    }

    public List<RefeicaoDTO> listar() {
        return refeicaoDAO.buscarTodos();
    }
}