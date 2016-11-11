/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Clases.Conexion;
import Clases.*;
import Dao.Operacion;
import static Dao.Operacion.getCadenaAlfanumAleatoria;
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
public class controladorMontarReserva extends HttpServlet {

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
        Vuelo vuelo_ida = (Vuelo) session.getAttribute("Vuelo_ida_elegido");
        Vuelo vuelo_vuelta = (Vuelo) session.getAttribute("Vuelo_vuelta_elegido");
        ArrayList<Pasajero> aAdultos = (ArrayList<Pasajero>) session.getAttribute("pasajerosadultos");
        ArrayList<Pasajero> aNinos = (ArrayList<Pasajero>) session.getAttribute("pasajerosninos");
        Pagador pagador = (Pagador) session.getAttribute("pagador");
        Tarjeta tarjeta = (Tarjeta) session.getAttribute("tarjeta elegida");
        int numero_viajeros = aAdultos.size() + aNinos.size();
        int precio_total = 0;
        
        Reserva reserva = new Reserva();
        reserva.setVuelo_ida(vuelo_ida);
        reserva.setVuelo_vuelta(vuelo_vuelta);
        reserva.setaPasajerosAdultos(aAdultos);
        reserva.setaPasajerosNinos(aNinos);
        reserva.setPagador(pagador);
        reserva.setTarjeta(tarjeta);
        reserva.setNum_viajeros(numero_viajeros);
        reserva.setCod_reserva(getCadenaAlfanumAleatoria(10));
        
        precio_total=precio_total + (reserva.getVuelo_ida().getPrecio()*reserva.getaPasajerosAdultos().size()) + (reserva.getVuelo_ida().getPrecio()*reserva.getaPasajerosNinos().size());
        if(reserva.getVuelo_vuelta()!=null){
            precio_total=precio_total + (reserva.getVuelo_vuelta().getPrecio()*reserva.getaPasajerosAdultos().size()) + (reserva.getVuelo_vuelta().getPrecio()*reserva.getaPasajerosNinos().size());
        }
        for (int i = 0; i < reserva.getaPasajerosAdultos().size(); i++) {
            for (int j = 0; j < reserva.getaPasajerosAdultos().get(i).getaServiciosIda().size(); j++) {
                precio_total=precio_total + reserva.getaPasajerosAdultos().get(i).getaServiciosIda().get(j).getPrecio();
            }
            if(!reserva.getaPasajerosAdultos().get(i).getaServiciosVuelta().isEmpty()){
                for (int u = 0; u < reserva.getaPasajerosAdultos().get(i).getaServiciosIda().size(); u++) {
                    precio_total=precio_total + reserva.getaPasajerosAdultos().get(i).getaServiciosVuelta().get(u).getPrecio();
                }
            }
        }
        if(reserva.getaPasajerosNinos()!=null){
            for (int i = 0; i < reserva.getaPasajerosNinos().size(); i++) {
                for (int j = 0; j < reserva.getaPasajerosNinos().get(i).getaServiciosIda().size(); j++) {
                    precio_total=precio_total + reserva.getaPasajerosNinos().get(i).getaServiciosIda().get(j).getPrecio();
                }
                if(!reserva.getaPasajerosNinos().get(i).getaServiciosVuelta().isEmpty()){
                    for (int u = 0; u < reserva.getaPasajerosNinos().get(i).getaServiciosIda().size(); u++) {
                        precio_total=precio_total + reserva.getaPasajerosNinos().get(i).getaServiciosVuelta().get(u).getPrecio();
                    }
                }
            }
        }
        
        reserva.setPrecio_total(precio_total);
        
        session.setAttribute("reserva", reserva);
        
        response.sendRedirect("confirmarPagoReserva.jsp");
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
