package br.com.compass.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Conta {

    private Integer id;
    private String nome;
    private LocalDate dataNascimento;
    private String cpf;
    private String telefone;
    private TipoConta tipoConta;
    private Login login;
    private Double saldo;
    private List<Transacao> transacoes;

    public Conta(String nome, String cpf, String dataNascimento, int tipoConta, Login login, String telefone) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        this.nome = nome;
        this.cpf = cpf;
        this.dataNascimento = LocalDate.parse(dataNascimento,dtf);
        this.login = login;
        this.telefone = telefone;
        this.tipoConta = TipoConta.getTipoConta(tipoConta);
        this.saldo = 0.0;
        this.transacoes = new ArrayList<>();
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

    public TipoConta getTipoConta() {
        return tipoConta;
    }

    public void setTipoConta(TipoConta tipoConta) {
        this.tipoConta = tipoConta;
    }

    public Login getLogin() {
        return login;
    }

    public void setLogin(Login login) {
        this.login = login;
    }

    public Double getSaldo() {
        return saldo;
    }

    public void setSaldo(Double saldo) {
        this.saldo = saldo;
    }

    public List<Transacao> getTransacoes() {
        return transacoes;
    }

    public void addTransacao(Transacao transacao) {
        this.transacoes.add(transacao);
    }
}