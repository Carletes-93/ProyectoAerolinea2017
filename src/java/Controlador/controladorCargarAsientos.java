/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Clases.Conexion;
import Clases.Pasajero;
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
public class controladorCargarAsientos extends HttpServlet {

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
        Boolean aradulto = false;
        Boolean arnino = false;

        for (int i = 0; i < aPasajerosAdultos.size(); i++) {
            for (int u = 0; u < aPasajerosAdultos.get(i).getaServiciosIda().size(); u++) {
                if (aPasajerosAdultos.get(i).getaServiciosIda().get(u).getNombre().equals("Asiento reservado")) {
                    aradulto = true;
                }
            }
        }
        for (int j = 0; j < aPasajerosNinos.size(); j++) {
            for (int h = 0; h < aPasajerosNinos.get(j).getaServiciosIda().size(); h++) {
                if (aPasajerosNinos.get(j).getaServiciosIda().get(h).getNombre().equals("Asiento reservado")) {
                    arnino = true;
                }
            }
        }

        if (!aradulto && !arnino) {
            response.sendRedirect("procesoPagar.jsp");
        } else {
            Operacion objop = new Operacion();

            Vuelo v_ida = (Vuelo) session.getAttribute("Vuelo_ida_elegido");
            Vuelo v_vuelta = (Vuelo) session.getAttribute("Vuelo_vuelta_elegido");

            String h;

            ArrayList<Boolean> aBooleanIda = new ArrayList();
            ArrayList<Boolean> aBooleanVuelta = new ArrayList();

            try {
                int cod_vuelo_ida = v_ida.getCodigo_vuelo();
                aBooleanIda = objop.sacarAsientosLibres(Conex, cod_vuelo_ida);

                if (v_vuelta != null) {
                    int cod_vuelo_vuelta = v_vuelta.getCodigo_vuelo();

                    aBooleanVuelta = objop.sacarAsientosLibres(Conex, cod_vuelo_vuelta);
                }

                h = "Hay asientos";
            } catch (SQLException errsql) {
                h = "Error SQL";
            }

            if (h.equals("Hay asientos")) {
                session.setAttribute("asientos ida", aBooleanIda);
                session.setAttribute("asientos vuelta", aBooleanVuelta);

                response.sendRedirect("elegirAsientos.jsp");
            }
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
