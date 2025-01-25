package br.com.compass.model;

import br.com.compass.bd.Conexao;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;

public class LoginDao {
    public void save(Login login) {
        EntityManager em = Conexao.getConnection();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(login);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            e.printStackTrace();
            throw e;
        }
    }

    // Metodo para buscar login por CPF
    public Login findByCpf(String cpf) {
        EntityManager em = Conexao.getConnection();
        try {
            return em.createQuery("SELECT l FROM Login l WHERE l.cpf = :cpf", Login.class)
                    .setParameter("cpf", cpf)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}