package org.example.demo1.crudBiblioteca.Modelo;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

public class DAOUsuario extends DAOGenerico<Usuario> {
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("unidad-biblioteca");
    EntityManager em = emf.createEntityManager();
    EntityTransaction tx = em.getTransaction();

    public DAOUsuario() {
        super(Usuario.class);
    }

}
