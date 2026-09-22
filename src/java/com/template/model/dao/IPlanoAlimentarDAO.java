package com.template.model.dao;

import com.template.model.dto.PlanoAlimentarDTO;
import java.util.List;

public interface IPlanoAlimentarDAO {
    void salvar(PlanoAlimentarDTO dto);
    void atualizar(PlanoAlimentarDTO dto);
    void deletar(int id);
    List<PlanoAlimentarDTO> buscarTodos();
}