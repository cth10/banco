package br.com.compass.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Conta {

    private Integer id;
    private String nome;
    private LocalDate dataNascimento;
    private String cpf;
    private String telefone;
    private TipoConta tipoConta;


    private Login login;


    public Conta(String nome, String cpf, String dataNascimento, int tipoConta, Login login, String telefone) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        this.nome = nome;
        this.cpf = cpf;
        this.dataNascimento = LocalDate.parse(dataNascimento,dtf);
        this.login = login;
        this.telefone = telefone;
        this.tipoConta = TipoConta.getTipoConta(tipoConta);

    }

    public TipoConta getTipoConta() {
        return tipoConta;
    }

    public void setTipoConta(TipoConta tipoConta) {
        this.tipoConta = tipoConta;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public Login getLogin() {
        return login;
    }

    public void setLogin(Login login) {
        this.login = login;
    }
}
