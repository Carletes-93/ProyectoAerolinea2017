/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Clases.Bebe;
import Clases.Conexion;
import Clases.Pasajero;
import Clases.Servicio;
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
public class controladorServiciosPasajerosIda extends HttpServlet {

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
        HttpSession session = request.getSession(true);
        ArrayList<Pasajero> aPasajerosAdultos = (ArrayList<Pasajero>) session.getAttribute("pasajerosadultos");
        ArrayList<Pasajero> aPasajerosNinos = (ArrayList<Pasajero>) session.getAttribute("pasajerosninos");
        ArrayList<Bebe> aPasajerosBebes = (ArrayList<Bebe>) session.getAttribute("pasajerosbebes");
        ArrayList<Servicio> aServicios = (ArrayList<Servicio>) session.getAttribute("servicios");

        String name = "serv";
        String namen = "servn";

        for (int i = 0; i < aPasajerosAdultos.size(); i++) {
            ArrayList<Servicio> aServiciosIda = new ArrayList();
            String[] serv = request.getParameterValues(name + i);
            if(serv != null){
                int[] servicios = new int[serv.length];
                for (int j = 0; j < servicios.length; j++) {
                    servicios[j] = Integer.parseInt(serv[j]);
                }
                for (int u = 0; u < servicios.length; u++) {
                    aServiciosIda.add(aServicios.get(servicios[u]));
                }

                if (!aPasajerosBebes.isEmpty()) {
                    for(int b = 0; b < aPasajerosBebes.size(); b++){
                        if(request.getParameter("bebe"+b).equals(aPasajerosAdultos.get(i).getNif())){
                            aPasajerosBebes.get(b).setTutor_ida(aPasajerosAdultos.get(i));
                            for(int s = 0; s < aServicios.size(); s++){
                                if(aServicios.get(s).getNombre().equals("Bebe")){
                                    aServiciosIda.add(aServicios.get(s));
                                }
                            }
                        }
                    }
                }
            }
            
            aPasajerosAdultos.get(i).setaServiciosIda(aServiciosIda);
        }

        if (!aPasajerosNinos.isEmpty()) {
            for (int i = 0; i < aPasajerosNinos.size(); i++) {
                ArrayList<Servicio> aServiciosIda = new ArrayList();
                String[] serv = request.getParameterValues(namen + i);
                if(serv != null){
                    int[] servicios = new int[serv.length];
                    for (int j = 0; j < servicios.length; j++) {
                        servicios[j] = Integer.parseInt(serv[j]);
                    }
                    for (int u = 0; u < servicios.length; u++) {
                        aServiciosIda.add(aServicios.get(servicios[u]));
                    }
                }
                aPasajerosNinos.get(i).setaServiciosIda(aServiciosIda);
            }
        }

        if (session.getAttribute("Vuelo_vuelta_elegido") == null) {
            requestdisp = servletcont.getRequestDispatcher("/controladorCargarAsientos");
            requestdisp.forward(request, response);
        }
        if (session.getAttribute("Vuelo_vuelta_elegido") != null) {
            response.sendRedirect("elegirServiciosVuelta.jsp");
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
