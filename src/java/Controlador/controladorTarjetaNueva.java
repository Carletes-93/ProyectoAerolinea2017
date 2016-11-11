/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Clases.Conexion;
import Clases.Pagador;
import Clases.Tarjeta;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Carlos
 */
public class controladorTarjetaNueva extends HttpServlet {

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
        
        ServletContext servletcont = getServletContext();
        RequestDispatcher requestdisp;
        
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        
        Tarjeta t1 = new Tarjeta();
        
        HttpSession session=request.getSession(true);
        Pagador p1 = (Pagador) session.getAttribute("pagador");
        
        t1.setNum_tarjeta(request.getParameter("num"));
        LocalDate caducidad = LocalDate.parse(request.getParameter("cad"), formato);
        t1.setCaducidad(caducidad);
        t1.setPin(Integer.parseInt(request.getParameter("pin")));
        t1.setCod_seguridad(request.getParameter("seg"));
        t1.setPagador(p1);
        t1.setTipo("nueva");
        
        session.setAttribute("tarjeta elegida", t1);
        
        requestdisp = servletcont.getRequestDispatcher("/controladorMontarReserva");
        requestdisp.forward(request, response);
        
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
