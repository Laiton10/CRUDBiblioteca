package org.example.demo1.crudBiblioteca.Controlador;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.demo1.crudBiblioteca.Modelo.DAOGenerico;
import org.example.demo1.crudBiblioteca.Modelo.Libro;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/libroServlet")
public class LibrosServlet extends HttpServlet {

    DAOGenerico<Libro> daolibro;

    public void init(){
        daolibro = new DAOGenerico<>(Libro.class);
    }
    //INSERT, select by isbn, delete, actualizar
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");

        PrintWriter impresora = response.getWriter();
        ObjectMapper conversorJson = new ObjectMapper();
        conversorJson.registerModule(new JavaTimeModule());

        String titulo = request.getParameter("titulo");
        String autor = request.getParameter("autor");
        String isbn = request.getParameter("isbn");
        String operacion = request.getParameter("operacion");

        if ("insert".equals(operacion)) {
                Libro libro = new Libro(isbn, titulo, autor);
                daolibro.add(libro);
                String jsonResponse = conversorJson.writeValueAsString(libro);
                impresora.println(jsonResponse);

        } else if ("update".equals(operacion)) {
                Libro libro = new Libro(isbn, titulo, autor);
                daolibro.update(libro);
                String jsonResponse = conversorJson.writeValueAsString(libro);
                impresora.println(jsonResponse);

        } else if ("deleteBy".equals(operacion)) {
                daolibro.deletebyId(isbn);
                String jsonResponse = conversorJson.writeValueAsString("Libro borrado por ISBN: " + isbn);
                impresora.println(jsonResponse);

        }
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");

        PrintWriter impresora = response.getWriter();
        ObjectMapper conversorJson = new ObjectMapper();
        conversorJson.registerModule(new JavaTimeModule());

        List<Libro> listaLibros  = daolibro.getAll();
        System.out.println("En java" + listaLibros);

        String json_response = conversorJson.writeValueAsString(listaLibros);
        System.out.println("En java json" + json_response);
        impresora.println(json_response);
    }



    public void destroy(){

    }
}

