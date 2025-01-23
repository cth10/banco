package br.com.compass;

import br.com.compass.controller.ContaController;
import br.com.compass.view.ContaView;

import java.util.Scanner;

import br.com.compass.view.LoginView;

public class App {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        mainMenu(scanner);

        scanner.close();
        System.out.println("Application closed");
    }

    public static void mainMenu(Scanner scanner) {
        boolean running = true;

        while (running) {
            System.out.println("========= Main Menu =========");
            System.out.println("|| 1. Login                ||");
            System.out.println("|| 2. Account Opening      ||");
            System.out.println("|| 0. Exit                 ||");
            System.out.println("=============================");
            System.out.print("Choose an option: ");

            int option = scanner.nextInt();

            switch (option) {
                case 1:
                    LoginView loginView = new LoginView();
                    if (loginView.realizarLogin(scanner)) {
                        bankMenu(scanner);
                    }
                    break;

                case 2:
                    ContaView contaView = new ContaView();
                    contaView.criarConta(scanner);
                    System.out.println("Account Opening.");
                    break;

                case 0:
                    running = false;
                    break;
                default:
                    System.out.println("Invalid option! Please try again.");
            }
        }
    }

    public static void bankMenu(Scanner scanner) {
        ContaController contaController = new ContaController();
        String cpfLogado = LoginView.getLastCpfLogado();
        boolean running = true;

        while (running) {
            System.out.println("========= Bank Menu =========");
            System.out.println("|| 1. Deposit              ||");
            System.out.println("|| 2. Withdraw             ||");
            System.out.println("|| 3. Check Balance        ||");
            System.out.println("|| 4. Transfer             ||");
            System.out.println("|| 5. Bank Statement       ||");
            System.out.println("|| 0. Exit                 ||");
            System.out.println("=============================");
            System.out.print("Choose an option: ");

            int option = scanner.nextInt();

            switch (option) {

                case 1:
                    System.out.print("Enter deposit amount: R$ ");
                    double depositAmount = scanner.nextDouble();
                    if (contaController.depositar(cpfLogado, depositAmount)) {
                        System.out.println("Deposit successful!");
                    } else {
                        System.out.println("Failed to complete deposit.");
                    }
                    break;

                case 2:
                    System.out.print("Enter withdrawal amount: R$ ");
                    double withdrawAmount = scanner.nextDouble();
                    if (contaController.sacar(cpfLogado, withdrawAmount)) {
                        System.out.println("Withdrawal successful!");
                    } else {
                        System.out.println("Failed to complete withdrawal.");
                    }
                    break;
                case 3:
                    Double saldo = contaController.consultarSaldo(cpfLogado);
                    if (saldo != null) {
                        System.out.printf("Current balance: R$ %.2f\n", saldo);
                    } else {
                        System.out.println("Unable to retrieve balance.");
                    }
                    break;
                case 4:
                    System.out.print("Enter destination CPF: ");
                    String cpfDestino = scanner.next();
                    System.out.print("Enter transfer amount: R$ ");
                    double transferAmount = scanner.nextDouble();

                    if (contaController.transferir(cpfLogado, cpfDestino, transferAmount)) {
                        System.out.println("Transfer successful!");
                    } else {
                        System.out.println("Failed to complete transfer.");
                    }
                    break;
                case 5:
                    contaController.exibirExtrato(cpfLogado);
                    break;
                case 0:
                    System.out.println("Exiting...");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid option! Please try again.");
            }
        }
    }

}
