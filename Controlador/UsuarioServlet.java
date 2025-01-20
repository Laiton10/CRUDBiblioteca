package org.example.demo1.crudBiblioteca.Controlador;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
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

@WebServlet("/usuarioServlet")
public class UsuarioServlet extends HttpServlet {

    DAOGenerico<Usuario> daoUsuario;

    public void init(){
        daoUsuario = new DAOGenerico<>(Usuario.class);
    }

    public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {
        res.setContentType("application/json");

        PrintWriter impresora = res.getWriter();
        ObjectMapper conversorJson = new ObjectMapper();
        conversorJson.registerModule(new JavaTimeModule());

        Integer id = Integer.parseInt(req.getParameter("id"));
        String nombre = req.getParameter("nombre");
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String tipo = req.getParameter("tipo");
        String penalizacionParam = req.getParameter("penalizacion");
        LocalDate penalizacionHasta = null;
        if (penalizacionParam != null && !penalizacionParam.isEmpty()) {
            penalizacionHasta = LocalDate.parse(penalizacionParam);
        }
        String operacion = req.getParameter("operacion");

        if ("update".equals(operacion)) {
            Usuario usuario = daoUsuario.getbyId(id);
            usuario.setNombre(nombre);
            usuario.setEmail(email);
            usuario.setPassword(password);
            usuario.setTipo(tipo);
            usuario.setPenalizacionHasta(penalizacionHasta);
            daoUsuario.update(usuario);
            String jsonResponse = conversorJson.writeValueAsString(usuario);
            impresora.println(jsonResponse);

        } else if ("deleteBy".equals(operacion)) {
            daoUsuario.deletebyId(id);
            String jsonResponse = conversorJson.writeValueAsString("ID: " + id);
            impresora.println(jsonResponse);

        } else if ("selectBy".equals(operacion)) {
            Usuario usuario = daoUsuario.getbyId(id);
            String jsonResponse = conversorJson.writeValueAsString("Usuario: " + usuario);
            impresora.println(jsonResponse);
        }
    }
}
