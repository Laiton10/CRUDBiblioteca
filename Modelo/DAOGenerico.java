package org.example.demo1.crudBiblioteca.Modelo;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

import java.util.List;

public class DAOGenerico<T> {
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("unidad-biblioteca");
    EntityManager em = emf.createEntityManager();
    EntityTransaction tx = em.getTransaction();
    Class<T> clase;

    public DAOGenerico(Class<T> clase){
        this.clase=clase;
    }

    //INSERT
    public T add(T objeto){
        tx.begin();
        em.persist(objeto);
        tx.commit();
        return objeto;
    }

    //SELECT WHERE ID
    public T getbyId(int id){
        return em.find(clase, id);
    }

    //SELECT ALL
    public List<T> getAll(){
        return em.createQuery("SELECT c from "+ clase.getSimpleName()+" c").getResultList();
    }

    //UPDATE
    public T update( T objeto){
        tx.begin();
        em.merge(objeto);
        tx.commit();
        return objeto;
    }

    //DELETE BY ID
    public boolean deletebyId(String id){
        tx.begin();
        em.remove(em.find(clase, id));
        tx.commit();
        return false;
    }

    //DELETE
    public boolean delete(T objeto){
        tx.begin();
        em.remove(objeto);
        tx.commit();
        return false;
    }

    @Override
    public String toString() {
        return "DAOGenerico{" +
                "clase simple name=" + clase.getSimpleName() + "\n"+
                "clase canonical name=" + clase.getCanonicalName() + "\n"+
                "clase name=" + clase.getName() + "\n"+
                '}';
    }

}
