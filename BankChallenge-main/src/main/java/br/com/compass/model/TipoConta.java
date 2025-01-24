package br.com.compass.model;

public enum TipoConta {


    CONTA_CORRENTE("Conta Corrente (Checking Account)"),
    CONTA_SALARIO("Conta Salário (Salary Account)"),
    CONTA_POUPANCA("Conta Poupanca (Savings Account)"),;

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
