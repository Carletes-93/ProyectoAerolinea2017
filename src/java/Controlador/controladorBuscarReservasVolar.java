/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Clases.Conexion;
import Clases.Reserva;
import Clases.Vuelo;
import Dao.Operacion;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Carlos
 */
public class controladorBuscarReservasVolar extends HttpServlet {

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

        String num_vuelo = request.getParameter("num");
        String fechas = request.getParameter("fecha");

        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate fecha = LocalDate.parse(fechas, formato);

        Operacion objop = new Operacion();
        Vuelo vuelo = new Vuelo();
        ArrayList<Reserva> aReservasSoloIda = new ArrayList();
        ArrayList<Reserva> aReservasIda = new ArrayList();
        ArrayList<Reserva> aReservasVuelta = new ArrayList();

        try {
            vuelo = objop.sacarVueloVolar(Conex, num_vuelo, fecha);
        } catch (SQLException ex) {
            vuelo = null;
            HttpSession session=request.getSession(true);
            session.setAttribute("error", 6);
            response.sendRedirect("errorSQL.jsp");
        }

        HttpSession session = request.getSession(true);
        session.setAttribute("vuelo_volar", vuelo);

        try {
            aReservasSoloIda = objop.sacarReservasSoloIdaVolar(Conex, vuelo);
            aReservasIda = objop.sacarReservasIdaVolar(Conex, vuelo);
            aReservasVuelta = objop.sacarReservasVueltaVolar(Conex, vuelo);
        } catch (SQLException ex) {
            session.setAttribute("error", 4);
            response.sendRedirect("errorSQL.jsp");
        }

        Boolean soloida = false;
        Boolean ida = false;
        Boolean vuelta = false;
        Boolean volar = false;

        if (!aReservasSoloIda.isEmpty()) {
            for (Reserva r1 : aReservasSoloIda) {
                try {
                    soloida = objop.reservaBackupSoloIda(Conex, r1);
                } catch (SQLException ex) {
                    soloida = false;
                }
            }
        } else {
            soloida = true;
        }
        if (!aReservasIda.isEmpty()) {
            for (Reserva r1 : aReservasIda) {
                try {
                    ida = objop.reservaBackupIda(Conex, r1);
                } catch (SQLException ex) {
                    ida = false;
                }
            }
        } else {
            ida = true;
        }
        if (!aReservasVuelta.isEmpty()) {
            for (Reserva r1 : aReservasVuelta) {
                try {
                    vuelta = objop.reservaBackupVuelta(Conex, r1);
                } catch (SQLException ex) {
                    vuelta = false;
                }
            }
        } else {
            vuelta = true;
        }
        
        if (!aReservasSoloIda.isEmpty()) {
            for (Reserva r1 : aReservasSoloIda) {
                try {
                    objop.borrarReserva(Conex, r1);
                } catch (SQLException ex) {
                }
            }
        }
        
        if (!aReservasIda.isEmpty()) {
            for (Reserva r1 : aReservasIda) {
                if(r1.getVuelo_ida()!=null){
                    
                } else {
                    try {
                        objop.borrarReserva(Conex, r1);
                    } catch (SQLException ex) {
                    }
                }
            }
        }
        
        if (!aReservasVuelta.isEmpty()) {
            for (Reserva r1 : aReservasVuelta) {
                try {
                    objop.borrarReserva(Conex, r1);
                } catch (SQLException ex) {
                }
            }
        }

        if (soloida & ida & vuelta) {
            try {
                volar = objop.borrarVuelo(Conex, vuelo);
            } catch (SQLException ex) {
                volar = false;
            }
        } else {
            volar = false;
        }

        if (volar) {
            response.sendRedirect("exitoVolar.jsp");
        } else {
            session.setAttribute("error", 5);
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
        protected void doGet
        (HttpServletRequest request, HttpServletResponse response)
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
        protected void doPost
        (HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
            processRequest(request, response);
        }

        /**
         * Returns a short description of the servlet.
         *
         * @return a String containing servlet description
         */
        @Override
        public String getServletInfo
        
            () {
        return "Short description";
        }// </editor-fold>

    }
