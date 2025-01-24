package br.com.compass.view;

import br.com.compass.controller.LoginService;
import br.com.compass.model.Login;
import br.com.compass.utils.ValidationUtils;

import java.util.Scanner;

public class LoginView {
    private static String lastCpfLogado;

    public static String getLastCpfLogado() {
        return lastCpfLogado;
    }

    public boolean realizarLogin(Scanner sc) {
        System.out.println("Enter your CPF: ");
        String cpf = sc.next();

        // Formata o CPF antes de fazer a validação
        cpf = ValidationUtils.formatarCPF(cpf);
        System.out.println("CPF formatted for login: " + cpf);

        System.out.println("Enter your password: ");
        String senha = sc.next();

        Login login = new Login(cpf, senha);
        LoginService loginService = new LoginService();

        boolean loginSucesso = loginService.validarLogin(login);

        if (loginSucesso) {
            lastCpfLogado = cpf;
            System.out.println("Login successful!");
            return true;
        } else {
            System.out.println("Invalid CPF or password!");
            return false;
        }
    }
}