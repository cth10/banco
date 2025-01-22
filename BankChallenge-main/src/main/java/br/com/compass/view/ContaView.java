package br.com.compass.view;

import br.com.compass.controller.ContaService;
import br.com.compass.model.Conta;
import br.com.compass.model.Login;

import java.util.Scanner;

public class ContaView {
   public void criarConta (Scanner sc ) {

       System.out.println("Digite o nome: ");
       String nome = sc.next();

       System.out.println("Digite o CPF: ");
       String cpf = sc.next();

       System.out.println("Digite a sua data de nascimento: ");
       String dataNascimento = sc.next();

       System.out.println("Digite seu telefone: ");
       String telefone = sc.next();

       System.out.println("Tipo de conta, digite 1 para conta corrente, 2 para salário, 3 para conta poupança");
       int tipoConta = sc.nextInt();


       System.out.println("Digite uma senha: ");
       String senha = sc.next();


       Login login = new Login(cpf, senha);
       Conta conta = new Conta(nome, cpf, dataNascimento, tipoConta, login, telefone);

       ContaService contaService = new ContaService();
       contaService.cadastrarConta(conta);



   }
}
