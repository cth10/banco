package br.com.compass.controller;

import br.com.compass.bd.BancoDeDados;
import br.com.compass.model.Login;

public class LoginService {

    public boolean validarLogin(Login login) {
        BancoDeDados bancoDeDados = new BancoDeDados();

        Object loginSalvo = bancoDeDados.getDados().get("login: " + login.getCpf());

        if (loginSalvo != null && loginSalvo instanceof Login) {
            Login loginBD = (Login) loginSalvo;
            return loginBD.getSenha().equals(login.getSenha());
        }

        return false;
    }
}