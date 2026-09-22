package com.template.model.dao;

import com.template.model.dto.RefeicaoDTO;
import java.util.List;

public interface IRefeicaoDAO {
    void salvar(RefeicaoDTO dto);
    void atualizar(RefeicaoDTO dto);
    void deletar(int id);
    List<RefeicaoDTO> buscarTodos();
}