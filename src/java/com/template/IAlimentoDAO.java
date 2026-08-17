package com.template;
import java.util.ArrayList;

public interface IAlimentoDAO {
    void cadastrarAlimento(AlimentoDTO obj);
    ArrayList<AlimentoDTO> listarAlimentos();
    void alterarAlimento(AlimentoDTO obj);
    void excluirAlimento(int id);
}