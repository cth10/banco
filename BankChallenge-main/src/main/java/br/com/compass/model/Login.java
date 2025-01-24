package br.com.compass.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity

public class Login {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer loginId;
    private String cpf;
    private String senha;

    public Login() {

    }

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
