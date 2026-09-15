package com.template.model.dao;

import com.template.model.dto.RefeicaoDTO;

import java.util.List;

public interface IRefeicaoDAO {
    void salvar(RefeicaoDTO refeicao);
    List<RefeicaoDTO> listar();
    void alterar(RefeicaoDTO refeicao);
    void deletar(int id);
}