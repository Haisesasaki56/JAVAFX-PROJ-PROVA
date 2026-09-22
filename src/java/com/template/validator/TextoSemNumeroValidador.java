package com.template.validator;

public class TextoSemNumeroValidador implements Validador<String> {

    private final String nomeCampo;
    private final String valor;

    public TextoSemNumeroValidador(String nomeCampo, String valor) {
        this.nomeCampo = nomeCampo;
        this.valor = valor;
    }

    @Override
    public boolean validar(String valor) {
        if (this.valor == null || this.valor.trim().isEmpty()) {
            return false;
        }
        return !this.valor.matches(".*\\d.*");
    }

    @Override
    public String getMensagemErro() {
        return "O campo " + nomeCampo + " não pode conter números.";
    }

    @Override
    public String getValor() {
        return valor;
    }
}