package org.example.demo1.crudBiblioteca.Controlador;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.demo1.crudBiblioteca.Modelo.DAOGenerico;
import org.example.demo1.crudBiblioteca.Modelo.Ejemplar;
import org.example.demo1.crudBiblioteca.Modelo.Prestamo;
import org.example.demo1.crudBiblioteca.Modelo.Usuario;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.List;

@WebServlet("/prestamoServlet")
public class PrestamoServlet extends HttpServlet {
    DAOGenerico<Prestamo> daoPrestamo;
    DAOGenerico<Usuario> daoUsuario;
    DAOGenerico<Ejemplar> daoEjemplar;
    private ControlPrestamo controlPrestamo;
    private ControlEjemplar controlEjemplar;

    public void init() {
        daoPrestamo = new DAOGenerico<>(Prestamo.class);
        daoUsuario = new DAOGenerico<>(Usuario.class);
        daoEjemplar = new DAOGenerico<>(Ejemplar.class);
        controlPrestamo = new ControlPrestamo();
        controlEjemplar = new ControlEjemplar();
    }

    public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {
        res.setContentType("application/json");

        PrintWriter impresora = res.getWriter();
        ObjectMapper conversorJson = new ObjectMapper();
        conversorJson.registerModule(new JavaTimeModule());

        String idParam = req.getParameter("id");
        Integer id = null;
        if (idParam != null && !idParam.isEmpty()) {
            id = Integer.parseInt(idParam);
        }
        String idEjemplarParam = req.getParameter("idEjemplar");
        Integer idEjemplar = null;
        if (idEjemplarParam != null && !idEjemplarParam.isEmpty()) {
            idEjemplar = Integer.parseInt(idEjemplarParam);
        }

        String idUsuarioParam = req.getParameter("idUsuario");
        Integer idUsuario = null;
        if (idUsuarioParam != null && !idUsuarioParam.isEmpty()) {
            idUsuario = Integer.parseInt(idUsuarioParam);
        }
        String fechaInicioParam = req.getParameter("fechaInicio");
        LocalDate fechaInicio = null;
        if (fechaInicioParam != null && !fechaInicioParam.isEmpty()) {
            fechaInicio = LocalDate.parse(fechaInicioParam);
        }
        String fechaDevolucionParam = req.getParameter("fechaDevolucion");
        LocalDate fechaDevolucion = null;
        if (fechaDevolucionParam != null && !fechaDevolucionParam.isEmpty()) {
            fechaDevolucion = LocalDate.parse(fechaDevolucionParam);
        }

        String operacion = req.getParameter("operacion");

        if ("insert".equals(operacion)) {
            Usuario usuario = daoUsuario.getbyId(idUsuario);
            Ejemplar ejemplar = daoEjemplar.getbyId(idEjemplar);
            Prestamo nuevoPrestamo = controlPrestamo.realizarPrestamo(usuario, ejemplar, LocalDate.now());
            daoPrestamo.add(nuevoPrestamo);
            controlEjemplar.actualizarEjemplar(ejemplar);
            daoEjemplar.update(ejemplar);

            String jsonResponse = conversorJson.writeValueAsString(nuevoPrestamo);
            impresora.println(jsonResponse);
        }else if ("update".equals(operacion)) {
            Prestamo prestamo = daoPrestamo.getbyId(id);
            prestamo.setFechaInicio(fechaInicio);
            //prestamo.setFechaDevolucion(fechaDevolucion);
            daoPrestamo.update(prestamo);
            String jsonResponse = conversorJson.writeValueAsString(prestamo);
            impresora.println(jsonResponse);
        }else if ("delete".equals(operacion)) {
            daoPrestamo.deletebyId(id);
            System.out.println("id: " + id);
            System.out.println("operacion: " + operacion);
            String jsonResponse = conversorJson.writeValueAsString("Usuario borrado: " + id);
            impresora.println(jsonResponse);
        }else if("selectBy".equals(operacion)){
            Prestamo prestamo = daoPrestamo.getbyId(id);
            String jsonResponse = conversorJson.writeValueAsString("Prestamo: " + prestamo);
            impresora.println(jsonResponse);
        } else if("devolver".equals(operacion)) {
            Usuario usuario = daoUsuario.getbyId(idUsuario);
            String mensaje = controlPrestamo.establecerPenalizacion(usuario);
            daoUsuario.update(usuario);

            String jsonResponse = conversorJson.writeValueAsString("Mensaje: " + mensaje);
            impresora.println(jsonResponse);

        }
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");

        PrintWriter impresora = response.getWriter();
        ObjectMapper conversorJson = new ObjectMapper();
        conversorJson.registerModule(new JavaTimeModule());

        List<Prestamo> listaPrestamos  = daoPrestamo.getAll();
        System.out.println("En java" + listaPrestamos);

        String json_response = conversorJson.writeValueAsString(listaPrestamos);
        System.out.println("En java json" + json_response);
        impresora.println(json_response);
    }

}
