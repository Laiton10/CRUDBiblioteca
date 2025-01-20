package org.example.demo1.crudBiblioteca.Controlador;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.demo1.crudBiblioteca.Modelo.DAOGenerico;
import org.example.demo1.crudBiblioteca.Modelo.Usuario;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;

@WebServlet("/loginServlet")
public class LoginServlet extends HttpServlet {

    DAOGenerico<Usuario> daoUsuario;

    public void init(){
        daoUsuario = new DAOGenerico<>(Usuario.class);
    }

    public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        res.setContentType("application/html");

        PrintWriter impresora = res.getWriter();

        String nombre = req.getParameter("nombre");
        String password = req.getParameter("password");

        impresora.println(daoUsuario.buscarPorNombreContra(nombre, password));


    }
}
