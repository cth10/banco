package br.com.compass.controller;

import br.com.compass.model.Login;
import br.com.compass.model.LoginDao;

public class LoginService {

    // Metodo para validar as credenciais de login do usuário
    public boolean validarLogin(Login login) {
        LoginDao loginDao = new LoginDao();

        // Adiciona log para verificar o CPF que está sendo buscado
        System.out.println("Tentando login com CPF: " + login.getCpf());

        Login loginSalvo = loginDao.findByCpf(login.getCpf());

        if (loginSalvo != null) {
            System.out.println("Login encontrado no banco!");
            System.out.println("Senha fornecida: " + login.getSenha());
            System.out.println("Senha no banco: " + loginSalvo.getSenha());

            // Compara a senha fornecida com a senha armazenada
            return loginSalvo.getSenha().equals(login.getSenha());
        } else {
            System.out.println("Nenhum login encontrado para o CPF: " + login.getCpf());
        }
        // Retorna false se o CPF não for encontrado no banco
        return false;
    }
}