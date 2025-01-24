package br.com.compass.model;

import br.com.compass.bd.Conexao;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;

public class LoginDao {
    public void save(Login login) {
        EntityManager em = Conexao.getConnection();
        em.getTransaction().begin();
        em.persist(login);
        em.getTransaction().commit();
    }

    // Novo metodo para buscar login por CPF
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