package br.com.compass.controller;

import br.com.compass.model.Conta;
import br.com.compass.model.ContaDao;
import br.com.compass.model.Transacao;
import jakarta.persistence.EntityManager;
import br.com.compass.bd.Conexao;

public class ContaController {
    private ContaDao contaDao;

    public ContaController() {
        this.contaDao = new ContaDao();
    }

    public boolean depositar(String cpf, Double valor) {
        if (valor <= 0) {
            System.out.println("Valor inválido para depósito!");
            return false;
        }

        EntityManager em = Conexao.getConnection();


        // Ciclo de vida da transação -> DEPÓSITO <-:
        // 1. begin() - Inicia a transação
        // 2. commit() - Confirma se tudo deu certo
        // 3. rollback() - Desfaz se deu erro


        try {
            em.getTransaction().begin();

            Conta conta = contaDao.findByCpf(cpf);
            if (conta != null) {
                conta.setSaldo(conta.getSaldo() + valor);

                Transacao transacao = new Transacao(
                        "DEPOSITO",
                        valor,
                        "Depósito em conta",
                        cpf,
                        cpf
                );

                // Persistir a transação antes de adicionar à conta
                em.persist(transacao);
                conta.addTransacao(transacao);

                em.merge(conta);
                em.getTransaction().commit(); // Confirma todas as operações

                return true;
            }
            return false;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback(); // Desfaz tudo se deu erro
            }
            e.printStackTrace();
            return false;
        }
    }

    public boolean sacar(String cpf, Double valor) {
        if (valor <= 0) {
            System.out.println("Valor inválido para saque!");
            return false;
        }

        EntityManager em = Conexao.getConnection();


        // Ciclo de vida da transação -> SAQUE <-:
        // 1. begin() - Inicia a transação
        // 2. commit() - Confirma se tudo deu certo
        // 3. rollback() - Desfaz se deu erro

        try {
            em.getTransaction().begin();

            Conta conta = contaDao.findByCpf(cpf);
            if (conta != null && conta.getSaldo() >= valor) {
                conta.setSaldo(conta.getSaldo() - valor);

                Transacao transacao = new Transacao(
                        "SAQUE",
                        valor,
                        "Saque em conta",
                        cpf,
                        cpf
                );

                em.persist(transacao);
                conta.addTransacao(transacao);

                em.merge(conta);
                em.getTransaction().commit(); // Confirma todas as operações
                return true;
            } else {
                System.out.println("Saldo insuficiente!");
                return false;
            }
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback(); // Desfaz tudo se deu erro
            }
            e.printStackTrace();
            return false;
        }
    }

    public Double consultarSaldo(String cpf) {
        Conta conta = contaDao.findByCpf(cpf);
        if (conta != null) {
            return conta.getSaldo();
        }
        return null;
    }

    public boolean transferir(String cpfOrigem, String cpfDestino, Double valor) {

        // ⚠️ Problema: Falta validação se a contaDestino existe antes de iniciar a transação

        if (valor <= 0) {
            System.out.println("Valor inválido para transferência!");
            return false;
        }

        EntityManager em = Conexao.getConnection();



        // Ciclo de vida da transação -> TRANSFERENCIA <-:
        // 1. begin() - Inicia a transação
        // 2. commit() - Confirma todas as operações se der certo
        // 3. rollback() - Desfaz todas as operações se der erro
        //
        // Importante na transferência:
        // - Se der erro em qualquer parte, nada é salvo
        // - Ou transfere td o valor ou não transfere nada
        // - Garante que não vai tirar de uma conta sem colocar na outra



        try {
            em.getTransaction().begin();

            Conta contaOrigem = contaDao.findByCpf(cpfOrigem);
            Conta contaDestino = contaDao.findByCpf(cpfDestino);

            if (contaOrigem != null && contaDestino != null && contaOrigem.getSaldo() >= valor) {
                contaOrigem.setSaldo(contaOrigem.getSaldo() - valor);
                contaDestino.setSaldo(contaDestino.getSaldo() + valor);

                Transacao transacaoOrigem = new Transacao(
                        "TRANSFERENCIA_ENVIADA",
                        valor,
                        "Transferência enviada para: " + contaDestino.getNome(),
                        cpfOrigem,
                        cpfDestino
                );

                Transacao transacaoDestino = new Transacao(
                        "TRANSFERENCIA_RECEBIDA",
                        valor,
                        "Transferência recebida de: " + contaOrigem.getNome(),
                        cpfOrigem,
                        cpfDestino
                );

                em.persist(transacaoOrigem);
                em.persist(transacaoDestino);

                contaOrigem.addTransacao(transacaoOrigem);
                contaDestino.addTransacao(transacaoDestino);

                em.merge(contaOrigem);
                em.merge(contaDestino);
                em.getTransaction().commit(); // Confirma todas as operações
                return true;
            } else {
                System.out.println("Saldo insuficiente!");
                return false;
            }
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback(); // Desfaz todas as operações
            }
            e.printStackTrace();
            return false;
        }
    }

    public void exibirExtrato(String cpf) {
        Conta conta = contaDao.findByCpf(cpf);
        if (conta != null) {
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