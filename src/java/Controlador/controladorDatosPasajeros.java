/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Clases.Bebe;
import Clases.Conexion;
import Clases.Pasajero;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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
public class controladorDatosPasajeros extends HttpServlet {

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
        HttpSession session=request.getSession(true);
        int numadultos = (Integer) session.getAttribute("numadultos");
        int numninos = (Integer) session.getAttribute("numninos");
        int numbebes = (Integer) session.getAttribute("numbebes");
        String adult = "adulto";
        String nino = "niño";
        
        String ta = "adulto";
        String na = "nomadul";
        String aa = "apeladul";
        String da = "nifadul";
        String ca = "caducadul";
        String fa = "nacadul";
        String tn = "nino";
        String nn = "nomnino";
        String an = "apelnino";
        String dn = "nifnino";
        String cn = "caducnino";
        String fn = "nacnino";
        String nb = "nombebe";
        String ab = "apelbebe";
        String db = "nifbebe";
        String fb = "nacbebe";
        
        ArrayList<Pasajero> aPasajerosAdultos = new ArrayList();
        ArrayList<Pasajero> aPasajerosNinos = new ArrayList();
        ArrayList<Bebe> aPasajerosBebes = new ArrayList();
        
        switch(request.getParameter("datospasajeros")){
            case "datospasajeros":
                
                for (int i = 1; i <= numadultos; i++) {
                    Pasajero p1 = new Pasajero();
                    p1.setTratamiento(request.getParameter(ta+String.valueOf(i)));
                    p1.setNombre(request.getParameter(na+String.valueOf(i)));
                    p1.setApellidos(request.getParameter(aa+String.valueOf(i)));
                    p1.setNif(request.getParameter(da+String.valueOf(i)));
                    LocalDate fecha_caducidad = LocalDate.parse(request.getParameter(ca+String.valueOf(i)), formato);
                    p1.setFecha_caducidad(fecha_caducidad);
                    LocalDate fecha_nac = LocalDate.parse(request.getParameter(fa+String.valueOf(i)), formato);
                    p1.setFecha_nac(fecha_nac);
                    p1.setTipo(adult);
                    aPasajerosAdultos.add(p1);
                }
                
                if(numninos>0){
                    for (int i = 1; i <= numninos; i++) {
                        Pasajero p1 = new Pasajero();
                        p1.setTratamiento(request.getParameter(tn+String.valueOf(i)));
                        p1.setNombre(request.getParameter(nn+String.valueOf(i)));
                        p1.setApellidos(request.getParameter(an+String.valueOf(i)));
                        p1.setNif(request.getParameter(dn+String.valueOf(i)));
                        LocalDate fecha_caducidad = LocalDate.parse(request.getParameter(cn+String.valueOf(i)), formato);
                        p1.setFecha_caducidad(fecha_caducidad);
                        LocalDate fecha_nac = LocalDate.parse(request.getParameter(fn+String.valueOf(i)), formato);
                        p1.setFecha_nac(fecha_nac);
                        p1.setTipo(nino);
                        aPasajerosNinos.add(p1);
                    }
                }
                
                if(numbebes>0){
                    for (int i = 1; i <= numbebes; i++) {
                        Bebe b1 = new Bebe();
                        b1.setNombre(request.getParameter(nb+String.valueOf(i)));
                        b1.setApellidos(request.getParameter(ab+String.valueOf(i)));
                        b1.setNif(request.getParameter(db+String.valueOf(i)));
                        LocalDate fecha_nac = LocalDate.parse(request.getParameter(fb+String.valueOf(i)), formato);
                        b1.setFecha_nac(fecha_nac);
                        aPasajerosBebes.add(b1);
                    }
                }
                
                session.setAttribute("pasajerosadultos", aPasajerosAdultos);
                session.setAttribute("pasajerosninos", aPasajerosNinos);
                session.setAttribute("pasajerosbebes", aPasajerosBebes);
                
                requestdisp = servletcont.getRequestDispatcher("/controladorCargarServicios");
                requestdisp.forward(request, response);
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
