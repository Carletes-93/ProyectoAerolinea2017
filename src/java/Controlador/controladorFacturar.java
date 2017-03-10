/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Clases.Conexion;
import Clases.Reserva;
import Dao.Operacion;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
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
public class controladorFacturar extends HttpServlet {

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
        Reserva r1fact = (Reserva) session.getAttribute("reserva_a_facturar");

        Operacion objop = new Operacion();
        Boolean h;
        ArrayList<Boolean> aB = new ArrayList();
        ArrayList aAsi = new ArrayList();
        int cod = r1fact.getVuelo_ida().getCodigo_vuelo();

        try {
            aB = objop.sacarAsientosLibres(Conex, cod);
            for (int u = 0; u < aB.size(); u++) {
                if (aB.get(u)) {
                    aAsi.add(u + 1);
                }
            }
        } catch (SQLException ex) {

        }

        if (request.getParameter("facturar").equals("facturarida")) {
            try {
                int cont = 0;
                for (int i = 0; i < r1fact.getaPasajerosAdultos().size(); i++) {
                    if (r1fact.getaPasajerosAdultos().get(i).getAsiento_ida() == 0) {
                        r1fact.getaPasajerosAdultos().get(i).setAsiento_ida((int) aAsi.get(cont));
                        objop.asignarAsientoFacturacionIda(Conex, r1fact.getaPasajerosAdultos().get(i), r1fact.getCod_reserva());
                        cont++;
                    }
                }
                for (int j = 0; j < r1fact.getaPasajerosNinos().size(); j++) {
                    if (r1fact.getaPasajerosNinos().get(j).getAsiento_ida() == 0) {
                        r1fact.getaPasajerosNinos().get(j).setAsiento_ida((int) aAsi.get(cont));
                        objop.asignarAsientoFacturacionIda(Conex, r1fact.getaPasajerosNinos().get(j), r1fact.getCod_reserva());
                        cont++;
                    }
                }

                h = objop.facturarIda(Conex, r1fact);
            } catch (SQLException ex) {
                h = false;
            }

            if (h) {
                r1fact.setFacturada_ida("S");
                session.setAttribute("reserva_a_facturar", r1fact);
                response.sendRedirect("facturarReserva.jsp");
            } else {
                session.setAttribute("error", 13);
                response.sendRedirect("errorSQL.jsp");
            }
        }

        if (request.getParameter("facturar").equals("facturarvuelta")) {
            try {
                int cont = 0;
                for (int i = 0; i < r1fact.getaPasajerosAdultos().size(); i++) {
                    if (r1fact.getaPasajerosAdultos().get(i).getAsiento_vuelta() == 0) {
                        r1fact.getaPasajerosAdultos().get(i).setAsiento_vuelta((int) aAsi.get(cont));
                        objop.asignarAsientoFacturacionVuelta(Conex, r1fact.getaPasajerosAdultos().get(i), r1fact.getCod_reserva());
                        cont++;
                    }
                }
                for (int j = 0; j < r1fact.getaPasajerosNinos().size(); j++) {
                    if (r1fact.getaPasajerosNinos().get(j).getAsiento_vuelta() == 0) {
                        r1fact.getaPasajerosNinos().get(j).setAsiento_vuelta((int) aAsi.get(cont));
                        objop.asignarAsientoFacturacionVuelta(Conex, r1fact.getaPasajerosNinos().get(j), r1fact.getCod_reserva());
                        cont++;
                    }
                }

                h = objop.facturarVuelta(Conex, r1fact);
            } catch (SQLException ex) {
                h = false;
            }

            if (h) {
                r1fact.setFacturada_vuelta("S");
                session.setAttribute("reserva_a_facturar", r1fact);
                response.sendRedirect("facturarReserva.jsp");
            } else {
                session.setAttribute("error", 14);
                response.sendRedirect("errorSQL.jsp");
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
