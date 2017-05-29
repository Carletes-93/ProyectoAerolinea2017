/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Clases.Conexion;
import Clases.Pasajero;
import Clases.Reserva;
import Clases.Servicio;
import Clases.Vuelo;
import Dao.Operacion;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author carlos
 */
public class controladorEstadisticas extends HttpServlet {

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

        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        LocalDate fechaIn = LocalDate.parse(request.getParameter("fechai"), formato);
        LocalDate fechaFin = LocalDate.parse(request.getParameter("fechaf"), formato);

        String aeroOr = request.getParameter("aeroOr");
        String aeroDes = request.getParameter("aeroDes");

        int conexion = 0;

        try {
            conexion = objop.sacarConexion(Conex, aeroOr, aeroDes);
        } catch (SQLException ex) {

        }

        ArrayList<Vuelo> vuelos = new ArrayList();

        try {
            vuelos = objop.sacarVuelosEst(Conex, conexion, fechaIn, fechaFin);
        } catch (SQLException ex) {

        }

        ArrayList<ArrayList<Reserva>> reservas = new ArrayList();

        if (!vuelos.isEmpty()) {
            for (Vuelo v1 : vuelos) {
                ArrayList<Reserva> r1 = new ArrayList();
                try {
                    r1 = objop.sacarReservasEst(Conex, v1);
                    reservas.add(r1);
                } catch (SQLException ex) {

                }
            }
            ArrayList<Servicio> servicios = new ArrayList();

            try {
                servicios = objop.sacarServicios(Conex);
            } catch (SQLException ex) {
            }

            ArrayList<Reserva> reservas_est = new ArrayList();

            for (ArrayList<Reserva> r1 : reservas) {
                for (Reserva res : r1) {
                    if (reservas_est.isEmpty()) {
                        reservas_est.add(res);
                    } else {
                        for (Reserva rest : reservas_est) {
                            if (rest.getCod_reserva().equals(res.getCod_reserva())) {
                                reservas_est.remove(rest);
                            }
                        }
                        reservas_est.add(res);
                    }
                }
            }
            
            for (Reserva res : reservas_est) {
                for (Pasajero p1 : res.getaPasajerosAdultos()) {
                    for (Servicio ser : p1.getaServiciosIda()) {
                        for (Servicio s1 : servicios) {
                            if (s1.getNombre().equals(ser.getNombre())) {
                                s1.setSuma(s1.getSuma() + 1);
                            }
                        }
                    }
                    for (Servicio ser : p1.getaServiciosVuelta()) {
                        for (Servicio s1 : servicios) {
                            if (s1.getNombre().equals(ser.getNombre())) {
                                s1.setSuma(s1.getSuma() + 1);
                            }
                        }
                    }
                }
                for (Pasajero p1 : res.getaPasajerosNinos()) {
                    for (Servicio ser : p1.getaServiciosIda()) {
                        for (Servicio s1 : servicios) {
                            if (s1.getNombre().equals(ser.getNombre())) {
                                s1.setSuma(s1.getSuma() + 1);
                            }
                        }
                    }
                    for (Servicio ser : p1.getaServiciosVuelta()) {
                        for (Servicio s1 : servicios) {
                            if (s1.getNombre().equals(ser.getNombre())) {
                                s1.setSuma(s1.getSuma() + 1);
                            }
                        }
                    }
                }
            }

            Collections.sort(servicios, new Comparator<Servicio>() {
                @Override
                public int compare(Servicio s1, Servicio s2) {
                    return new Integer(s2.getPrecio()*s2.getSuma()).compareTo(new Integer(s1.getPrecio()*s1.getSuma()));
                }
            });

            HttpSession session = request.getSession(true);
            session.setAttribute("servicios_est", servicios);
            session.setAttribute("fechaI", fechaIn);
            session.setAttribute("fechaF", fechaFin);
            session.setAttribute("or", aeroOr);
            session.setAttribute("des", aeroDes);
            response.sendRedirect("vistaEstadisticas.jsp");
        } else {
            HttpSession session = request.getSession(true);
            session.setAttribute("error", 100);
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
