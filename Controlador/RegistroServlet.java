package org.example.demo1.crudBiblioteca.Controlador;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.demo1.crudBiblioteca.Modelo.DAOGenerico;
import org.example.demo1.crudBiblioteca.Modelo.Libro;
import org.example.demo1.crudBiblioteca.Modelo.Usuario;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;

@WebServlet("/registroServlet")
public class RegistroServlet extends HttpServlet {

    DAOGenerico<Usuario> daoUsuario;

    public void init(){
        daoUsuario = new DAOGenerico<>(Usuario.class);
    }

    public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        res.setContentType("application/json");

        PrintWriter impresora = res.getWriter();
        ObjectMapper conversorJson = new ObjectMapper();
        conversorJson.registerModule(new JavaTimeModule());

        String dni = req.getParameter("dni");
        String nombre = req.getParameter("nombre");
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String tipo = req.getParameter("tipo");
        LocalDate penalizacionHasta = null;

        Usuario usuario = new Usuario(dni, nombre, email, password, tipo, penalizacionHasta);
        daoUsuario.add(usuario);
        String json = conversorJson.writeValueAsString(usuario);
        impresora.println(json);
    }
}
