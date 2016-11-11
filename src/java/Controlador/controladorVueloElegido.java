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
public class controladorVueloElegido extends HttpServlet {

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
        HttpSession session=request.getSession(true);
        ArrayList<Vuelo> aVuelosIda = (ArrayList<Vuelo>) session.getAttribute("vuelosida");
        ArrayList<Vuelo> aVuelosVuelta = (ArrayList<Vuelo>) session.getAttribute("vuelosvuelta");
        
        int pos_ida = Integer.parseInt(request.getParameter("vueloida"));
        Vuelo vueloida = new Vuelo();
        vueloida = aVuelosIda.get(pos_ida);
        
        Vuelo vuelovuelta = new Vuelo();
        int pos_vuelta = -1;
        
        if(aVuelosVuelta == null || aVuelosVuelta.isEmpty()){
            
        } else{
            pos_vuelta = Integer.parseInt(request.getParameter("vuelovuelta"));
            vuelovuelta = aVuelosVuelta.get(pos_ida);
        }
        
        switch (request.getParameter("vueloelegido")) {
            case "vueloelegido":
                session.setAttribute("Vuelo_ida_elegido", vueloida);
                if(pos_vuelta != -1){
                    session.setAttribute("Vuelo_vuelta_elegido", vuelovuelta);
                }
                response.sendRedirect("ingresarDatosPasajeros.jsp");
                break;
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
