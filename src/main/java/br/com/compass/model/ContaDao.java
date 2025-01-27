package br.com.compass.model;

import br.com.compass.bd.Conexao;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

public class ContaDao {

    // Metodo para salvar uma nova conta no banco de dados
    public void save(Conta conta) {
        EntityManager em = Conexao.getConnection();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(conta);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            e.printStackTrace();
            throw e;
        }
    }

    // Metodo para buscar conta por CPF
    public Conta findByCpf(String cpf) {
        EntityManager em = Conexao.getConnection();
        try {
            return em.createQuery("SELECT c FROM Conta c WHERE c.cpf = :cpf", Conta.class)
                    .setParameter("cpf", cpf)
                    .getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    // Metodo para atualizar conta
    public void update(Conta conta) {
        EntityManager em = Conexao.getConnection();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.merge(conta);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            e.printStackTrace();
            throw e;
        }
    }
}