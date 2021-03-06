/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Clases.Conexion;
import Clases.Pasajero;
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
public class controladorAsientosPasajeros extends HttpServlet {

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

        HttpSession session = request.getSession(true);
        ArrayList<Pasajero> aPasajerosAdultos = (ArrayList<Pasajero>) session.getAttribute("pasajerosadultos");
        ArrayList<Pasajero> aPasajerosNinos = (ArrayList<Pasajero>) session.getAttribute("pasajerosninos");
        ArrayList<Boolean> aAsientosVuelta = (ArrayList<Boolean>) session.getAttribute("asientos vuelta");

        String adultoida = "asientoidaadult";
        String adultovuelta = "asientovueltaadult";
        String ninoida = "asientoidanino";
        String ninovuelta = "asientovueltanino";

        for (int i = 0; i < aPasajerosAdultos.size(); i++) {
            for (int s = 0; s < aPasajerosAdultos.get(i).getaServiciosIda().size(); s++) {
                if (aPasajerosAdultos.get(i).getaServiciosIda().get(s).getNombre().equals("Asiento reservado")) {
                    aPasajerosAdultos.get(i).setAsiento_ida(Integer.parseInt(request.getParameter(adultoida + String.valueOf(i))));
                }
            }
            if (!aAsientosVuelta.isEmpty()) {
                aPasajerosAdultos.get(i).setAsiento_vuelta(Integer.parseInt(request.getParameter(adultovuelta + String.valueOf(i))));
            }
        }

        if (!aPasajerosNinos.isEmpty()) {
            for (int i = 0; i < aPasajerosNinos.size(); i++) {
                for (int s = 0; s < aPasajerosNinos.get(i).getaServiciosIda().size(); s++) {
                    if (aPasajerosNinos.get(i).getaServiciosIda().get(s).getNombre().equals("Asiento reservado")) {
                        aPasajerosNinos.get(i).setAsiento_ida(Integer.parseInt(request.getParameter(ninoida + String.valueOf(i))));
                    }
                }
                if (!aAsientosVuelta.isEmpty()) {
                    aPasajerosNinos.get(i).setAsiento_vuelta(Integer.parseInt(request.getParameter(ninovuelta + String.valueOf(i))));
                }
            }
        }

        response.sendRedirect("procesoPagar.jsp");
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
