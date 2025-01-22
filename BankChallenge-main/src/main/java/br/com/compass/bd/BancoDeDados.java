package br.com.compass.bd;

import br.com.compass.model.Conta;
import br.com.compass.model.Login;

import java.util.HashMap;
import java.util.Map;

public class BancoDeDados {


    private Map<String, Object> dados = new HashMap<String, Object>();

    public Map<String, Object> getDados() {
        return dados;
    }

    public void salvarDados(Conta conta) {
    dados.put("conta: " + conta.getCpf(), conta);
    dados.put("login: " + conta.getLogin().getCpf(), conta.getLogin());

    }

}
