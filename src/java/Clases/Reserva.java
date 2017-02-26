/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;

import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 *
 * @author Carlos
 */
public class Reserva {
    private int codigo_reserva;
    private String cod_reserva;
    private Vuelo vuelo_ida;
    private Vuelo vuelo_vuelta;
    private Pagador pagador;
    private Tarjeta tarjeta;
    private int num_viajeros;
    private LocalDateTime fecha;
    private ArrayList<Pasajero> aPasajerosAdultos = new ArrayList();
    private ArrayList<Pasajero> aPasajerosNinos = new ArrayList();
    private ArrayList<Bebe> aPasajerosBebes = new ArrayList();
    private int precio_total;
    private String facturada_ida;
    private String facturada_vuelta;

    public Reserva() {
    }

    public Reserva(String cod_reserva, Vuelo vuelo_ida, Vuelo vuelo_vuelta, Pagador pagador, Tarjeta tarjeta, int num_viajeros) {
        this.cod_reserva = cod_reserva;
        this.vuelo_ida = vuelo_ida;
        this.vuelo_vuelta = vuelo_vuelta;
        this.pagador = pagador;
        this.tarjeta = tarjeta;
        this.num_viajeros = num_viajeros;
    }

    public int getCodigo_reserva() {
        return codigo_reserva;
    }

    public void setCodigo_reserva(int codigo_reserva) {
        this.codigo_reserva = codigo_reserva;
    }

    public String getCod_reserva() {
        return cod_reserva;
    }

    public void setCod_reserva(String cod_reserva) {
        this.cod_reserva = cod_reserva;
    }

    public Vuelo getVuelo_ida() {
        return vuelo_ida;
    }

    public void setVuelo_ida(Vuelo vuelo_ida) {
        this.vuelo_ida = vuelo_ida;
    }

    public Vuelo getVuelo_vuelta() {
        return vuelo_vuelta;
    }

    public void setVuelo_vuelta(Vuelo vuelo_vuelta) {
        this.vuelo_vuelta = vuelo_vuelta;
    }

    public Pagador getPagador() {
        return pagador;
    }

    public void setPagador(Pagador pagador) {
        this.pagador = pagador;
    }

    public Tarjeta getTarjeta() {
        return tarjeta;
    }

    public void setTarjeta(Tarjeta tarjeta) {
        this.tarjeta = tarjeta;
    }

    public int getNum_viajeros() {
        return num_viajeros;
    }

    public void setNum_viajeros(int num_viajeros) {
        this.num_viajeros = num_viajeros;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public ArrayList<Pasajero> getaPasajerosAdultos() {
        return aPasajerosAdultos;
    }

    public void setaPasajerosAdultos(ArrayList<Pasajero> aPasajerosAdultos) {
        this.aPasajerosAdultos = aPasajerosAdultos;
    }

    public ArrayList<Pasajero> getaPasajerosNinos() {
        return aPasajerosNinos;
    }

    public void setaPasajerosNinos(ArrayList<Pasajero> aPasajerosNinos) {
        this.aPasajerosNinos = aPasajerosNinos;
    }

    public int getPrecio_total() {
        return precio_total;
    }

    public void setPrecio_total(int precio_total) {
        this.precio_total = precio_total;
    }

    public ArrayList<Bebe> getaPasajerosBebes() {
        return aPasajerosBebes;
    }

    public void setaPasajerosBebes(ArrayList<Bebe> aPasajerosBebes) {
        this.aPasajerosBebes = aPasajerosBebes;
    }

    public String getFacturada_ida() {
        return facturada_ida;
    }

    public void setFacturada_ida(String facturada_ida) {
        this.facturada_ida = facturada_ida;
    }

    public String getFacturada_vuelta() {
        return facturada_vuelta;
    }

    public void setFacturada_vuelta(String facturada_vuelta) {
        this.facturada_vuelta = facturada_vuelta;
    }
    
    @Override
    public String toString() {
        return "Reserva{" + "codigo_reserva=" + codigo_reserva + ", cod_reserva=" + cod_reserva + ", vuelo_ida=" + vuelo_ida + ", vuelo_vuelta=" + vuelo_vuelta + ", pagador=" + pagador + ", tarjeta=" + tarjeta + ", num_viajeros=" + num_viajeros + ", fecha=" + fecha + '}';
    }
}
