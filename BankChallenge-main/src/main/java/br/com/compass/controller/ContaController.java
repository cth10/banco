package br.com.compass.controller;

import br.com.compass.bd.BancoDeDados;
import br.com.compass.model.Conta;
import br.com.compass.model.Transacao;

public class ContaController {
    private BancoDeDados bancoDeDados;

    public ContaController() {
        this.bancoDeDados = new BancoDeDados();
    }

    public boolean depositar(String cpf, Double valor) {
        if (valor <= 0) {
            System.out.println("Valor inválido para depósito!");
            return false;
        }

        Object contaObj = bancoDeDados.getDados().get("conta: " + cpf);
        if (contaObj != null && contaObj instanceof Conta) {
            Conta conta = (Conta) contaObj;
            conta.setSaldo(conta.getSaldo() + valor);

            Transacao transacao = new Transacao(
                    "DEPOSITO",
                    valor,
                    "Depósito em conta",
                    cpf,
                    cpf
            );
            conta.addTransacao(transacao);

            return true;
        }
        return false;
    }

    public boolean sacar(String cpf, Double valor) {
        if (valor <= 0) {
            System.out.println("Valor inválido para saque!");
            return false;
        }

        Object contaObj = bancoDeDados.getDados().get("conta: " + cpf);
        if (contaObj != null && contaObj instanceof Conta) {
            Conta conta = (Conta) contaObj;
            if (conta.getSaldo() >= valor) {
                conta.setSaldo(conta.getSaldo() - valor);

                Transacao transacao = new Transacao(
                        "SAQUE",
                        valor,
                        "Saque em conta",
                        cpf,
                        cpf
                );
                conta.addTransacao(transacao);

                return true;
            } else {
                System.out.println("Saldo insuficiente!");
                return false;
            }
        }
        return false;
    }

    public Double consultarSaldo(String cpf) {
        Object contaObj = bancoDeDados.getDados().get("conta: " + cpf);
        if (contaObj != null && contaObj instanceof Conta) {
            Conta conta = (Conta) contaObj;
            return conta.getSaldo();
        }
        return null;
    }

    public boolean transferir(String cpfOrigem, String cpfDestino, Double valor) {
        if (valor <= 0) {
            System.out.println("Valor inválido para transferência!");
            return false;
        }

        Object contaOrigemObj = bancoDeDados.getDados().get("conta: " + cpfOrigem);
        Object contaDestinoObj = bancoDeDados.getDados().get("conta: " + cpfDestino);

        if (contaOrigemObj != null && contaOrigemObj instanceof Conta &&
                contaDestinoObj != null && contaDestinoObj instanceof Conta) {

            Conta contaOrigem = (Conta) contaOrigemObj;
            Conta contaDestino = (Conta) contaDestinoObj;

            if (contaOrigem.getSaldo() >= valor) {
                contaOrigem.setSaldo(contaOrigem.getSaldo() - valor);
                contaDestino.setSaldo(contaDestino.getSaldo() + valor);

                Transacao transacaoOrigem = new Transacao(
                        "TRANSFERENCIA_ENVIADA",
                        valor,
                        "Transferência enviada para: " + contaDestino.getNome(),
                        cpfOrigem,
                        cpfDestino
                );
                contaOrigem.addTransacao(transacaoOrigem);

                Transacao transacaoDestino = new Transacao(
                        "TRANSFERENCIA_RECEBIDA",
                        valor,
                        "Transferência recebida de: " + contaOrigem.getNome(),
                        cpfOrigem,
                        cpfDestino
                );
                contaDestino.addTransacao(transacaoDestino);

                return true;
            } else {
                System.out.println("Saldo insuficiente!");
                return false;
            }
        }
        return false;
    }

    public void exibirExtrato(String cpf) {
        Object contaObj = bancoDeDados.getDados().get("conta: " + cpf);
        if (contaObj != null && contaObj instanceof Conta) {
            Conta conta = (Conta) contaObj;
            System.out.println("\n====== EXTRATO BANCÁRIO ======");
            System.out.println("Cliente: " + conta.getNome());
            System.out.println("CPF: " + conta.getCpf());
            System.out.println("Tipo de Conta: " + conta.getTipoConta().getDescricao());
            System.out.println("\nTransações:");

            for (Transacao t : conta.getTransacoes()) {
                System.out.printf("%s - %s - R$ %.2f - %s\n",
                        t.getData().toString(),
                        t.getTipo(),
                        t.getValor(),
                        t.getDescricao()
                );
            }

            System.out.println("\nSaldo atual: R$ " + String.format("%.2f", conta.getSaldo()));
            System.out.println("=============================\n");
        }
    }
}