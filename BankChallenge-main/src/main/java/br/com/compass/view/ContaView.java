package br.com.compass.view;

import br.com.compass.controller.ContaService;
import br.com.compass.model.Conta;
import br.com.compass.model.Login;
import br.com.compass.utils.ValidationUtils;

import java.util.Scanner;

public class ContaView {
    public void criarConta(Scanner sc) {

        System.out.println("Enter your name: ");
        sc.nextLine();
        String nome = sc.nextLine();

        // Loop para validar o CPF

        String cpf;
        while (true) {
            System.out.println("Enter your CPF (numbers only): ");
            cpf = sc.next();


            if (ValidationUtils.validarCPF(cpf)) {
                cpf = ValidationUtils.formatarCPF(cpf);
                break;
            } else {
                System.out.println("Invalid CPF! Please enter a valid CPF.");
            }
        }

        // Solicita e lê a data de nascimento

        System.out.println("Enter your date of birth (dd/MM/yyyy): ");
        String dataNascimento = sc.next();


        // Loop para validar o telefone

        String telefone;
        while (true) {
            System.out.println("Enter your phone number (numbers only): ");
            telefone = sc.next();

            // Valida o telefone

            if (ValidationUtils.validarTelefone(telefone)) {
                telefone = ValidationUtils.formatarTelefone(telefone);
                break;
            } else {
                System.out.println("Invalid phone number! Use the format: Area code + number (Ex: 11999999999)" );
            }
        }

        System.out.println("Account type, enter:");
        System.out.println("1 - Conta Corrente (Checking Account)");
        System.out.println("2 - Conta Salário (Salary Account)");
        System.out.println("3 - Conta Poupança (Savings Account)");
        int tipoConta = sc.nextInt();

        // Senha

        String senha;
        while (true) {
            System.out.println("Enter a password (minimum 8 characters): ");
            senha = sc.next();
            if (ValidationUtils.validarSenha(senha)) {
                break;
            } else {
                System.out.println("Invalid password! Password must be at least 8 characters. ");
            }
        }

        Login login = new Login(cpf, senha);
        Conta conta = new Conta(nome, cpf, dataNascimento, tipoConta, login, telefone);

        ContaService contaService = new ContaService();
        contaService.cadastrarConta(conta);
    }
}