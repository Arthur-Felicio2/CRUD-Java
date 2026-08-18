package com.template.validator;

public class ValidatorCampNecessary implements Validator<String> {

    private final String nomeCampo;
    private final String valor;

    public ValidatorCampNecessary(String nomeCampo, String valor) {
        this.nomeCampo = nomeCampo;
        this.valor = valor;
    }

    @Override
    public boolean validar(String valorAtual) {
        return valorAtual != null && !valorAtual.trim().isEmpty();
    }

    @Override
    public String getMensagemErro() {
        return "O campo '" + nomeCampo + "' é obrigatório e precisa ser preenchido.";
    }

    @Override
    public String getValor() {
        return valor;
    }
}