package org.example.demo1.crudBiblioteca.Modelo;

import jakarta.persistence.*;

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
    public T update(T objeto){
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

    public boolean deletebyId(int id){
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

    public String buscarPorNombreContra(String nombre, String password){
        tx.begin();
        //Usuario usuario = em.find(Usuario.class, nombre);
        String sql = "SELECT * FROM Usuario WHERE nombre = ?";
        Query consulta = em.createNativeQuery(sql, Usuario.class);
        consulta.setParameter(1, nombre);

        Usuario usuario = (Usuario) consulta.getSingleResult();

        String mensaje = "";

        if(usuario == null){
             mensaje = "No se encontró el usuario";
        }else{
            if(password.equals(usuario.getPassword())){
                mensaje = "Usuario encontrado";
            }
        }
        tx.commit();
        return mensaje;
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
