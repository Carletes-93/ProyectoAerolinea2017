/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Clases.Conexion;
import Clases.Vuelo;
import Dao.Operacion;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Carlos
 */
public class controladorBuscarVuelosIdaVuelta extends HttpServlet {

    Connection Conex;
    @Override
    public void init() throws ServletException {
        try {
            Conexion ConexBD = Conexion.GetConexion();

            Conex = ConexBD.GetCon();
        } catch (ClassNotFoundException cnfe) {
        } catch (SQLException sqle) {
        }
    }
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        String origen=request.getParameter("ORIGEN");
        String destino=request.getParameter("DESTINO");
        String fechaida=request.getParameter("FECHA_IDA");
        String fechavuelta=request.getParameter("FECHA_VUELTA");
        int adultos = Integer.parseInt(request.getParameter("ADULTOS"));
        int ninos = Integer.parseInt(request.getParameter("NINOS"));
        int bebes = Integer.parseInt(request.getParameter("BEBES"));
        int num_viajeros=adultos+ninos;
        
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate fechaIda = LocalDate.parse(fechaida, formato);
        LocalDate fechaVuelta = LocalDate.parse(fechavuelta, formato);
        
        Operacion objop = new Operacion();
        ArrayList<Vuelo> vuelosida = new ArrayList();
        ArrayList<Vuelo> vuelosvuelta = new ArrayList();
        
        String h;
        String j;
        
        try {
            vuelosida=objop.buscarVuelosIda(Conex, origen, destino, fechaIda, num_viajeros);
            vuelosvuelta=objop.buscarVuelosVuelta(Conex, destino, origen, fechaVuelta, num_viajeros);
            if(vuelosida.isEmpty()){
                h="No hay vuelos de ida";
            }
            else{
                h="Hay vuelos de ida";
            }
            if(vuelosvuelta.isEmpty()){
                j="No hay vuelos de vuelta";
            }
            else{
                j="Hay vuelos de vuelta";
            }
        } catch (SQLException errsql) {
            h="Error SQL";
            j="Error SQL";
        }
        
        if(h.equals("Hay vuelos de ida") || j.equals("Hay vuelos de vuelta")){
            HttpSession session=request.getSession(true);
            session.setAttribute("vuelosida", vuelosida);
            session.setAttribute("vuelosvuelta", vuelosvuelta);
            session.setAttribute("numadultos", adultos);
            session.setAttribute("numninos", ninos);
            session.setAttribute("numbebes", bebes);
            
            response.sendRedirect("eleccionVuelo.jsp");
        }
        if(h.equals("No hay vuelos de ida") && j.equals("No hay vuelos de vuelta")){
            response.sendRedirect("errorNoVuelos.jsp");
        }
        if(h.equals("Error SQL") || j.equals("Error SQL")){
            response.sendRedirect("errorSQL.jsp");
        }
        
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
