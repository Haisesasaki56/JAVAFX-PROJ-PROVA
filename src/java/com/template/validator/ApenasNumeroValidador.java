package com.template.validator;

public class ApenasNumeroValidador implements Validador<String> {

    private final String nomeCampo;
    private final String valor;

    public ApenasNumeroValidador(String nomeCampo, String valor) {
        this.nomeCampo = nomeCampo;
        this.valor = valor;
    }

    @Override
    public boolean validar(String valor) {
        if (this.valor == null || this.valor.trim().isEmpty()) {
            return false;
        }
        try {
            double num = Double.parseDouble(this.valor.replace(",", "."));
            return num > 0;
        } catch (NumberFormatException e) {
            return false; // Captura o erro nativo e retorna false para ativar a mensagem customizada
        }
    }

    @Override
    public String getMensagemErro() {
        return "O campo " + nomeCampo + " deve conter apenas números válidos e maiores que zero (não aceita letras).";
    }

    @Override
    public String getValor() {
        return valor;
    }
}