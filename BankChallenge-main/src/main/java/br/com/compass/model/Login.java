package br.com.compass.model;

public class Login {

    private Integer loginId;
    private String cpf;
    private String senha;

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Login(String cpf, String senha) {
        this.cpf = cpf;
        this.senha = senha;
    }


}
