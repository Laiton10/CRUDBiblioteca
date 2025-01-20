package org.example.demo1.crudBiblioteca.Controlador;

import org.example.demo1.crudBiblioteca.Modelo.DAOGenerico;
import org.example.demo1.crudBiblioteca.Modelo.Ejemplar;
import org.example.demo1.crudBiblioteca.Modelo.Prestamo;
import org.example.demo1.crudBiblioteca.Modelo.Usuario;

import java.time.LocalDate;

public class ControlPrestamo {
    DAOGenerico<Ejemplar> daoEjemplar;

    public ControlPrestamo() {
        super();
        daoEjemplar = new DAOGenerico<>(Ejemplar.class);
    }

    public LocalDate registrarDevolucion(Prestamo prestamo) {
        Ejemplar ejemplar = prestamo.getEjemplar();
        ejemplar.setEstado("Disponible");
        daoEjemplar.update(ejemplar);
        LocalDate fechaHaSidoDevuelto = LocalDate.now();
        return fechaHaSidoDevuelto;
    }

    public Prestamo realizarPrestamo(Usuario usu, Ejemplar eje, LocalDate fechaInicio) {
        boolean validar = true;
        String mensaje = null;
        if(eje.getEstado().equals("No disponible")) {
            mensaje = "No se puede hacer el préstamo, ejemplar no disponible";
            validar = false;
        }

        if(usu.getPrestamos().size() > 3){
            mensaje = "No se puede hacer el préstamo, ya que el usuario ya tiene 3 préstamos activos";
            validar = false;
        }

        if(usu.getPenalizacionHasta() != null){
            mensaje = "No se puede hacer el préstamo, ya que el usuario tiene una penalización";
            validar = false;
        }

        if(!validar){
            System.out.println(mensaje);
        }else{
            eje.setEstado("Prestado");
            Prestamo prestamo = new Prestamo(usu, eje, fechaInicio);
            return prestamo;
        }
        return null;
    }

    public String establecerPenalizacion(Usuario usu) { // si no se pasa la fecha, no es penalizado y se actualiza el estado del ejemplar a disponible
        int dias = 0;
        String mensaje = null;
        for(Prestamo p : usu.getPrestamos()){
            LocalDate fechaDevuelto = registrarDevolucion(p);
            if(fechaDevuelto.isAfter(p.getFechaDevolucion())){
                dias+= 15;
            }
        }

        if(dias > 0) {
            usu.setPenalizacionHasta(LocalDate.now().plusDays(dias));
            mensaje = "Se ha establecido una penalización y cambiado el estado del ejemplar a disponible";
            return mensaje;
        }
        mensaje = "No se ha establecido una penalización y se ha cambiado el estado del ejemplar a disponible ";
        return mensaje;
    }
}
