package br.com.compass.view;

import br.com.compass.controller.LoginService;
import br.com.compass.model.Login;

import java.util.Scanner;

public class LoginView {

    public boolean realizarLogin(Scanner sc) {
        System.out.println("Digite seu CPF: ");
        String cpf = sc.next();

        System.out.println("Digite sua senha: ");
        String senha = sc.next();

        Login login = new Login(cpf, senha);
        LoginService loginService = new LoginService();

        boolean loginSucesso = loginService.validarLogin(login);

        if (loginSucesso) {
            System.out.println("Login realizado com sucesso!");
            return true;
        } else {
            System.out.println("CPF ou senha inválidos!");
            return false;
        }
    }
}