/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Clases.Conexion;
import Clases.Pagador;
import Dao.Operacion;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Carlos
 */
public class controladorBuscarPagador extends HttpServlet {

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
        
        Operacion objop = new Operacion();
        
        String nif = request.getParameter("nif");
        String pass = request.getParameter("pass");
        
        Pagador p1 = new Pagador();
        String h;
        
        try{
            p1 = objop.buscarPagador(Conex, nif, pass);
            
            h="Existe";
            
            if(p1==null){
                h="No existe";
            }
            if(p1!=null){
                p1.setTipo("existente");
            }
            
        }catch(SQLException errsql){
            h="Error SQL";
        }
        
        if(h.equals("Existe")){
            
            HttpSession session=request.getSession(true);
            session.setAttribute("pagador", p1);
            response.sendRedirect("procesoTarjeta.jsp");
        }
        if(h.equals("No existe")){
            response.sendRedirect("errorPagador.jsp");
        }
        if(h.equals("Error SQL")){
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
