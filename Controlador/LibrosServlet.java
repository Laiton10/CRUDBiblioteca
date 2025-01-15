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
        String titulo = request.getParameter("titulo");
        String autor = request.getParameter("autor");
        String isbn = request.getParameter("isbn");
        String operacion = request.getParameter("operacion");

        response.setContentType("application/html");
        PrintWriter out = response.getWriter();



        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<body>");
        System.out.println(operacion);


        if(operacion.equals("insert")){
            Libro libro = new Libro(isbn, titulo, autor);
            daolibro.add(libro);
            out.println("El libro creado es: " + libro.toString());

        }else if(operacion.equals("update")){
            Libro libro = new Libro(isbn, titulo, autor);
            daolibro.update(libro);
            out.println("El libro actualizado es: " + libro.toString());

        }else if(operacion.equals("delete")){
            Libro libro = new Libro(isbn, titulo, autor);
            daolibro.delete(libro);
            out.println("El libro borrado es: " + libro.toString());

        }else if(operacion.equals("deleteBy")){
            Libro libro = new Libro(isbn, titulo, autor);
            daolibro.deletebyId(isbn);
            out.println("El libro borrado por isbn es: " + libro.toString());
        }
        out.println("</body>");
        out.println("</html>");
    }

    //SELECTALL
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

