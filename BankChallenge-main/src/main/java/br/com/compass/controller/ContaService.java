package br.com.compass.controller;

import br.com.compass.bd.BancoDeDados;
import br.com.compass.model.Conta;
import br.com.compass.model.Login;

public class ContaService {

    public void cadastrarConta(Conta conta) {

        BancoDeDados bancoDeDados = new BancoDeDados();
        bancoDeDados.salvarDados(conta);
        System.out.println("Conta cadastrada com sucesso!");

    }

}
