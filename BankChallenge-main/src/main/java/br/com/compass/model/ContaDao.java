package br.com.compass.model;

import br.com.compass.bd.Conexao;
import jakarta.persistence.EntityManager;

public class ContaDao {

    // Metodo para salvar uma nova conta no banco de dados

    public void save(Conta conta) {
        EntityManager em = Conexao.getConnection();
        em.getTransaction().begin();
        em.persist(conta);
        em.getTransaction().commit();
    }

    // Novo metodo para buscar conta por CPF
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
        em.getTransaction().begin();
        em.merge(conta);
        em.getTransaction().commit();
    }
}

