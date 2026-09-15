package com.template.model.dao;

import com.template.model.dto.PlanoAlimentarDTO;
import java.util.ArrayList;
import java.util.List;

public class PlanoAlimentarDAO implements IPlanoAlimentarDAO {

    @Override
    public void cadastrarPlano(PlanoAlimentarDTO obj) {
        // Lógica JDBC de INSERT no banco
    }

    @Override
    public List<PlanoAlimentarDTO> listarPlanos() {
        // Lógica JDBC de SELECT no banco
        return new ArrayList<>();
    }

    @Override
    public void alterarPlano(PlanoAlimentarDTO obj) {
        // Lógica JDBC de UPDATE no banco
    }

    @Override
    public void excluirPlano(int id) {
        // Lógica JDBC de DELETE no banco
    }
}