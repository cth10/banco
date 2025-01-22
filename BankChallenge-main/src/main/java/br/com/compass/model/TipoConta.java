package br.com.compass.model;

public enum TipoConta {


    CONTA_CORRENTE("Conta Corrente"),
    CONTA_SALARIO("Conta Salario"),
    CONTA_POUPANCA("Conta Poupanca"),;

    private String descricao;

    TipoConta(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

    public static TipoConta getTipoConta(int codigo) {
        switch (codigo) {
            case 1: return CONTA_CORRENTE;
            case 2: return CONTA_SALARIO;
            case 3: return CONTA_POUPANCA;

            default: return null;

        }
    }

}
