package br.com.compass.bd;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class  Conexao {

   // Metodo que retorna uma conexão com o banco de dados

   public static EntityManager getConnection() {

      EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("BankChallengePU");

      // Cria e retorna um novo EntityManager para interagir com o banco

      EntityManager entityManager = entityManagerFactory.createEntityManager();
      return entityManager;
   }

}

// SELECT *FROM conta;

// DELETE FROM conta_transacao WHERE Conta_id = ;

// DELETE FROM conta WHERE id = ;