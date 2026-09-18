package com.template.model.dao;

import com.template.model.dto.PlanoAlimentarDTO;
import java.util.ArrayList;
import java.util.List;

public class PlanoAlimentarDAO implements IPlanoAlimentarDAO {

    @Override
    public void cadastrarPlano(PlanoAlimentarDTO obj) {
    }

    @Override
    public List<PlanoAlimentarDTO> listarPlanos() {
        return new ArrayList<>();
    }

    @Override
    public void alterarPlano(PlanoAlimentarDTO obj) {
    }

    @Override
    public void excluirPlano(int id) {
    }
}