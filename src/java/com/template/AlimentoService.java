package com.template;

import com.template.validator.AlimentoValidador;
import java.util.ArrayList;

public class AlimentoService {
    private final IAlimentoDAO dao;

    // DIP: O serviço depende da Interface, não da classe concreta
    public AlimentoService(IAlimentoDAO dao) {
        this.dao = dao;
    }

    public void salvarAlimento(AlimentoDTO dto, String caloriasTexto) {
        AlimentoValidador.validar(dto.getAlimento(), caloriasTexto);
        dao.cadastrarAlimento(dto);
    }

    public ArrayList<AlimentoDTO> listarTudo() {
        return dao.listarAlimentos();
    }

    public void deletarAlimento(int id) {
        dao.excluirAlimento(id);
    }

    public void atualizarAlimento(AlimentoDTO dto, String caloriasTexto) {
        AlimentoValidador.validar(dto.getAlimento(), caloriasTexto);
        dao.alterarAlimento(dto);
    }
}