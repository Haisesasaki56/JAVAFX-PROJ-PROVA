package com.template.model.dao;

import com.template.model.dto.AlimentoDTO;
import java.util.List;

public interface IAlimentoDAO {
    void salvar(AlimentoDTO alimento);
    void atualizar(AlimentoDTO alimento);
    void deletar(Integer id);
    List<AlimentoDTO> buscarTodos();
    AlimentoDTO buscarPorId(Integer id);
}