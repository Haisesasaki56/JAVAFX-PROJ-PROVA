package com.template.model.dao;

import com.template.model.dto.PlanoAlimentarDTO;
import java.util.List;

public interface IPlanoAlimentarDAO {
    void cadastrarPlano(PlanoAlimentarDTO plano);
    List<PlanoAlimentarDTO> listarPlanos();
    void alterarPlano(PlanoAlimentarDTO plano);
    void excluirPlano(int id);
}