package org.example.demo1.crudBiblioteca.Controlador;

import org.example.demo1.crudBiblioteca.Modelo.Ejemplar;

import java.util.List;

public class ControlEjemplar {

    public ControlEjemplar() {
        super();
    }

    public int calcularStock(List<Ejemplar> stock){
        int contador = 0;
        for(Ejemplar e : stock){
            if(e.getEstado().equals("Disponible")){
                contador++;
            }
        }
        return contador;
    }

    public void actualizarEjemplar(Ejemplar ejemplar){
        ejemplar.setEstado("Prestado");
    }
}
