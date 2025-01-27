package br.com.compass.controller;

import br.com.compass.model.Conta;
import br.com.compass.model.ContaDao;
import br.com.compass.model.Transacao;
import br.com.compass.utils.ValidationUtils;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import br.com.compass.bd.Conexao;

public class ContaController {
    private ContaDao contaDao;

    public ContaController() {
        this.contaDao = new ContaDao();
    }

    public boolean depositar(String cpf, Double valor) {
        // Validação 1: Verifica se o valor do depósito é positivo
        // Se tentar depositar valor negativo ou zero, retorna false e exibe mensagem
        if (valor <= 0) {
            System.out.println("Invalid deposit amount!");
            return false;
        }

        EntityManager em = Conexao.getConnection();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();

            // Validação 2: Verifica se a conta existe
            // Se o CPF não existir no banco, retorna false
            Conta conta = contaDao.findByCpf(cpf);
            if (conta != null) {
                conta.setSaldo(conta.getSaldo() + valor);

                // Cria registro da transação para o histórico
                Transacao transacao = new Transacao(
                        "DEPOSITO",
                        valor,
                        "Depósito em conta",
                        cpf,
                        cpf
                );

                em.persist(transacao);
                conta.addTransacao(transacao);

                em.merge(conta);
                tx.commit();
                return true;
            }
            return false;
        } catch (Exception e) {
            // Em caso de erro na transação, faz rollback para garantir consistência
            if (tx.isActive()) {
                tx.rollback();
            }
            e.printStackTrace();
            return false;
        }
    }

    public boolean sacar(String cpf, Double valor) {
        // Validação 1: Verifica se o valor do saque é positivo
        // Se tentar sacar valor negativo ou zero, retorna false e exibe mensagem
        if (valor <= 0) {
            System.out.println("Invalid amount for withdrawal!");
            return false;
        }

        EntityManager em = Conexao.getConnection();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();

            // Validação 2: Verifica se a conta existe e tem saldo suficiente
            // Se o CPF não existir ou saldo for insuficiente, retorna false
            Conta conta = contaDao.findByCpf(cpf);
            if (conta != null && conta.getSaldo() >= valor) {
                conta.setSaldo(conta.getSaldo() - valor);

                // Cria registro da transação para o histórico
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
                tx.commit();
                return true;
            } else {
                System.out.println("Insufficient balance!");
                return false;
            }
        } catch (Exception e) {
            // Em caso de erro na transação, faz rollback para garantir consistência
            if (tx.isActive()) {
                tx.rollback();
            }
            e.printStackTrace();
            return false;
        }
    }

    public Double consultarSaldo(String cpf) {
        // Validação: Verifica se a conta existe
        // Se o CPF não existir, retorna null
        Conta conta = contaDao.findByCpf(cpf);
        if (conta != null) {
            return conta.getSaldo();
        }
        return null;
    }

    public boolean transferir(String cpfOrigem, String cpfDestino, Double valor) {
        // Validação 1: Verifica se o valor da transferência é positivo
        // Se tentar transferir valor negativo ou zero, retorna false e exibe mensagem
        if (valor <= 0) {
            System.out.println("Invalid amount for transfer!");
            return false;
        }

        // Validação 2: Formata os CPFs antes da busca para garantir padrão
        String cpfOrigemFormatado = ValidationUtils.formatarCPF(cpfOrigem);
        String cpfDestinoFormatado = ValidationUtils.formatarCPF(cpfDestino);

        // Validação 3: Verifica se ambas as contas existem
        Conta contaOrigem = contaDao.findByCpf(cpfOrigemFormatado);
        Conta contaDestino = contaDao.findByCpf(cpfDestinoFormatado);

        if (contaOrigem == null || contaDestino == null) {
            System.out.println("One or both accounts not found!");
            System.out.println("Origin CPF (formatted): " + cpfOrigemFormatado);
            System.out.println("Destination CPF (formatted): " + cpfDestinoFormatado);
            return false;
        }

        // Validação 4: Verifica se há saldo suficiente na conta de origem
        if (contaOrigem.getSaldo() < valor) {
            System.out.println("Insufficient balance!");
            return false;
        }

        EntityManager em = Conexao.getConnection();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();

            // Atualiza os saldos das contas
            contaOrigem.setSaldo(contaOrigem.getSaldo() - valor);
            contaDestino.setSaldo(contaDestino.getSaldo() + valor);

            // Cria registros das transações para ambas as contas
            Transacao transacaoOrigem = new Transacao(
                    "TRANSFERENCIA_ENVIADA",
                    valor,
                    "Transfer sent to: " + contaDestino.getNome(),
                    cpfOrigemFormatado,
                    cpfDestinoFormatado
            );

            Transacao transacaoDestino = new Transacao(
                    "TRANSFERENCIA_RECEBIDA",
                    valor,
                    "Transfer received from: " + contaOrigem.getNome(),
                    cpfOrigemFormatado,
                    cpfDestinoFormatado
            );

            // Persiste as transações no banco
            em.persist(transacaoOrigem);
            em.persist(transacaoDestino);

            // Adiciona as transações ao histórico das contas
            contaOrigem.addTransacao(transacaoOrigem);
            contaDestino.addTransacao(transacaoDestino);

            // Atualiza as contas no banco
            em.merge(contaOrigem);
            em.merge(contaDestino);

            tx.commit();
            return true;
        } catch (Exception e) {
            // Em caso de erro na transação, faz rollback para garantir consistência
            // Isso garante que ou a transferência ocorre por completo ou nada é alterado
            if (tx.isActive()) {
                tx.rollback();
            }
            e.printStackTrace();
            return false;
        }
    }

    public void exibirExtrato(String cpf) {
        // Validação: Verifica se a conta existe
        // Se o CPF não existir, nada é exibido
        Conta conta = contaDao.findByCpf(cpf);
        if (conta != null) {
            System.out.println("\n====== BANK STATEMENT ======");
            System.out.println("Customer: " + conta.getNome());
            System.out.println("CPF: " + conta.getCpf());
            System.out.println("Account Type: " + conta.getTipoConta().getDescricao());
            System.out.println("\nTransactions:");

            // Exibe todas as transações da conta
            for (Transacao t : conta.getTransacoes()) {
                System.out.printf("%s - %s - R$ %.2f - %s\n",
                        t.getData().toString(),
                        t.getTipo(),
                        t.getValor(),
                        t.getDescricao()
                );
            }

            System.out.println("\nCurrent balance: R$ " + String.format("%.2f", conta.getSaldo()));
            System.out.println("=============================\n");
        }
    }
}