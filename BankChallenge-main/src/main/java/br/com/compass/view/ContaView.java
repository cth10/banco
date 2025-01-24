package br.com.compass.view;

import br.com.compass.controller.ContaService;
import br.com.compass.model.Conta;
import br.com.compass.model.Login;
import br.com.compass.utils.ValidationUtils;

import java.util.Scanner;

public class ContaView {
    public void criarConta(Scanner sc) {

        // ⚠️ Problema para nomes completos (com espaço)

        System.out.println("Digite o nome: ");
        String nome = sc.next();

        // Loop para validar o CPF

        String cpf;
        while (true) {
            System.out.println("Digite o CPF (apenas números): ");
            cpf = sc.next();


            if (ValidationUtils.validarCPF(cpf)) {
                cpf = ValidationUtils.formatarCPF(cpf);
                break;
            } else {
                System.out.println("CPF inválido! Por favor, digite um CPF válido.");
            }
        }

        // Solicita e lê a data de nascimento

        System.out.println("Digite a sua data de nascimento (dd/MM/yyyy): ");
        String dataNascimento = sc.next();


        // Loop para validar o telefone

        String telefone;
        while (true) {
            System.out.println("Digite seu telefone (apenas números): ");
            telefone = sc.next();

            // Valida o telefone

            if (ValidationUtils.validarTelefone(telefone)) {
                telefone = ValidationUtils.formatarTelefone(telefone);
                break;
            } else {
                System.out.println("Telefone inválido! Use o formato: DDD + número (Ex: 11999999999)");
            }
        }

        System.out.println("Tipo de conta, digite:");
        System.out.println("1 - Conta Corrente");
        System.out.println("2 - Conta Salário");
        System.out.println("3 - Conta Poupança");
        int tipoConta = sc.nextInt();

        // Senha

        String senha;
        while (true) {
            System.out.println("Digite uma senha (mínimo 8 caracteres): ");
            senha = sc.next();
            if (ValidationUtils.validarSenha(senha)) {
                break;
            } else {
                System.out.println("Senha inválida! A senha deve ter pelo menos 8 caracteres.");
            }
        }

        Login login = new Login(cpf, senha);
        Conta conta = new Conta(nome, cpf, dataNascimento, tipoConta, login, telefone);

        ContaService contaService = new ContaService();
        contaService.cadastrarConta(conta);
    }
}