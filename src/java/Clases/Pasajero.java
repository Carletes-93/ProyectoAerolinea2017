/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;

import java.time.LocalDate;
import java.util.ArrayList;
import Clases.Servicio;

/**
 *
 * @author Carlos
 */
public class Pasajero {
    private int codigo_pasajero;
    private String tratamiento;
    private String nombre;
    private String apellidos;
    private String nif;
    private LocalDate fecha_caducidad;
    private LocalDate fecha_nac;
    private String tipo;
    private ArrayList<Servicio> aServiciosIda = new ArrayList();
    private ArrayList<Servicio> aServiciosVuelta = new ArrayList();
    private Bebe bebe;
    private int asiento_ida;
    private int asiento_vuelta;

    public Pasajero() {
    }

    public Pasajero(String tratamiento, String nombre, String apellidos, String nif, LocalDate fecha_caducidad, LocalDate fecha_nac, String tipo) {
        this.tratamiento = tratamiento;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.nif = nif;
        this.fecha_caducidad = fecha_caducidad;
        this.fecha_nac = fecha_nac;
        this.tipo = tipo;
    }

    public int getCodigo_pasajero() {
        return codigo_pasajero;
    }

    public void setCodigo_pasajero(int codigo_pasajero) {
        this.codigo_pasajero = codigo_pasajero;
    }

    public String getTratamiento() {
        return tratamiento;
    }

    public void setTratamiento(String tratamiento) {
        this.tratamiento = tratamiento;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getNif() {
        return nif;
    }

    public void setNif(String nif) {
        this.nif = nif;
    }

    public LocalDate getFecha_caducidad() {
        return fecha_caducidad;
    }

    public void setFecha_caducidad(LocalDate fecha_caducidad) {
        this.fecha_caducidad = fecha_caducidad;
    }

    public LocalDate getFecha_nac() {
        return fecha_nac;
    }

    public void setFecha_nac(LocalDate fecha_nac) {
        this.fecha_nac = fecha_nac;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public ArrayList<Servicio> getaServiciosIda() {
        return aServiciosIda;
    }

    public void setaServiciosIda(ArrayList<Servicio> aServiciosIda) {
        this.aServiciosIda = aServiciosIda;
    }

    public ArrayList<Servicio> getaServiciosVuelta() {
        return aServiciosVuelta;
    }

    public void setaServiciosVuelta(ArrayList<Servicio> aServiciosVuelta) {
        this.aServiciosVuelta = aServiciosVuelta;
    }

    public Bebe getBebe() {
        return bebe;
    }

    public void setBebe(Bebe bebe) {
        this.bebe = bebe;
    }

    public int getAsiento_ida() {
        return asiento_ida;
    }

    public void setAsiento_ida(int asiento_ida) {
        this.asiento_ida = asiento_ida;
    }

    public int getAsiento_vuelta() {
        return asiento_vuelta;
    }

    public void setAsiento_vuelta(int asiento_vuelta) {
        this.asiento_vuelta = asiento_vuelta;
    }

    @Override
    public String toString() {
        return "Pasajero{" + "codigo_pasajero=" + codigo_pasajero + ", tratamiento=" + tratamiento + ", nombre=" + nombre + ", apellidos=" + apellidos + ", nif=" + nif + ", fecha_caducidad=" + fecha_caducidad + ", fecha_nac=" + fecha_nac + ", tipo=" + tipo + ", aServiciosIda=" + aServiciosIda + ", aServiciosVuelta=" + aServiciosVuelta + ", bebe=" + bebe + ", asiento_ida=" + asiento_ida + ", asiento_vuelta=" + asiento_vuelta + '}';
    }
}
