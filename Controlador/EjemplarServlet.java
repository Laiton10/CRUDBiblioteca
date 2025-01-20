package org.example.demo1.crudBiblioteca.Controlador;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.demo1.crudBiblioteca.Modelo.DAOGenerico;
import org.example.demo1.crudBiblioteca.Modelo.Ejemplar;
import org.example.demo1.crudBiblioteca.Modelo.Libro;
import org.example.demo1.crudBiblioteca.Modelo.Usuario;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/ejemplarServlet")
public class EjemplarServlet extends HttpServlet {
    DAOGenerico<Ejemplar> daoEjemplar;
    private ControlEjemplar controlEjemplar;

    public void init(){
        daoEjemplar = new DAOGenerico<>(Ejemplar.class);
        controlEjemplar = new ControlEjemplar();
    }

    public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {
        res.setContentType("application/json");

        PrintWriter impresora = res.getWriter();
        ObjectMapper conversorJson = new ObjectMapper();
        conversorJson.registerModule(new JavaTimeModule());

        Integer id = Integer.parseInt(req.getParameter("id"));
        String isbn = req.getParameter("isbn");
        String estado = req.getParameter("estado");
        String operacion = req.getParameter("operacion");

        if ("insert".equals(operacion)) {
            Ejemplar ej = new Ejemplar(isbn, estado);
            daoEjemplar.add(ej);
            String jsonResponse = conversorJson.writeValueAsString(ej);
            impresora.println(jsonResponse);

        }else if ("update".equals(operacion)) {
            Ejemplar ejemplar = daoEjemplar.getbyId(id);
            controlEjemplar.actualizarEjemplar(ejemplar);
            daoEjemplar.update(ejemplar);
            String jsonResponse = conversorJson.writeValueAsString(ejemplar);
            impresora.println(jsonResponse);

        } else if ("delete".equals(operacion)) {
            Ejemplar ejemplar = daoEjemplar.getbyId(id);
            daoEjemplar.delete(ejemplar);
            String jsonResponse = conversorJson.writeValueAsString(ejemplar);
            impresora.println(jsonResponse);

        }
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");

        PrintWriter impresora = response.getWriter();
        ObjectMapper conversorJson = new ObjectMapper();
        conversorJson.registerModule(new JavaTimeModule());

        List<Ejemplar> listaEjemplar  = daoEjemplar.getAll();
        System.out.println("En java" + listaEjemplar);

        String json_response = conversorJson.writeValueAsString(listaEjemplar);
        System.out.println("En java json" + json_response);
        impresora.println(json_response);
    }

}

