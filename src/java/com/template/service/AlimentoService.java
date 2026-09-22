package com.template.service;

import com.template.model.dao.IAlimentoDAO;
import com.template.model.dto.AlimentoDTO;
import com.template.validator.IAlimentoValidador;

import java.util.List;

public class AlimentoService {

    private final IAlimentoDAO alimentoDAO;
    private final IAlimentoValidador validador;

    public AlimentoService(IAlimentoDAO alimentoDAO, IAlimentoValidador validador) {
        this.alimentoDAO = alimentoDAO;
        this.validador = validador;
    }

    public void salvarAlimento(AlimentoDTO dto, String caloriasTexto) {
        // 1. Valida primeiro os campos em texto. Se houver letras, o validador lança IllegalArgumentException aqui!
        validador.validarCamposAlimento(dto.getNome(), caloriasTexto);

        // 2. Só executa a conversão se a validação acima passar sem erros
        dto.setCalorias(Double.parseDouble(caloriasTexto.replace(",", ".")));

        // 3. Persiste no banco de dados
        alimentoDAO.salvar(dto);
    }

    public void atualizarAlimento(AlimentoDTO dto, String caloriasTexto) {
        // 1. Valida primeiro os campos
        validador.validarCamposAlimento(dto.getNome(), caloriasTexto);

        // 2. Converte
        dto.setCalorias(Double.parseDouble(caloriasTexto.replace(",", ".")));

        // 3. Atualiza no banco
        alimentoDAO.atualizar(dto);
    }

    public void deletarAlimento(int id) {
        alimentoDAO.deletar(id);
    }

    public List<AlimentoDTO> listarTudo() {
        return alimentoDAO.buscarTodos();
    }
}