package com.template.model.dao;

import com.template.model.dto.RefeicaoDTO;

import java.util.ArrayList;
import java.util.List;

public class RefeicaoDAO implements IRefeicaoDAO {
    private static final List<RefeicaoDTO> bancoMock = new ArrayList<>();
    private static int contadorId = 1;

    @Override
    public void salvar(RefeicaoDTO refeicao) {
        refeicao.setId(contadorId++);
        bancoMock.add(refeicao);
    }

    @Override
    public List<RefeicaoDTO> listar() {
        return new ArrayList<>(bancoMock);
    }

    @Override
    public void alterar(RefeicaoDTO refeicao) {
        for (int i = 0; i < bancoMock.size(); i++) {
            if (bancoMock.get(i).getId() == refeicao.getId()) {
                bancoMock.set(i, refeicao);
                break;
            }
        }
    }

    @Override
    public void deletar(int id) {
        bancoMock.removeIf(r -> r.getId() == id);
    }
}