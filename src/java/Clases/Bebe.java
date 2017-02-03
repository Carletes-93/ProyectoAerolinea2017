/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;

import java.time.LocalDate;

/**
 *
 * @author Carlos
 */
public class Bebe {
    private int cod_bebe;
    private String nombre;
    private String apellidos;
    private String nif;
    private LocalDate fecha_nac;
    private Pasajero tutor_ida;
    private Pasajero tutor_vuelta;

    public Bebe() {
    }

    public Bebe(String nombre, String apellidos, String nif, LocalDate fecha_nac) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.nif = nif;
        this.fecha_nac = fecha_nac;
    }

    public int getCod_bebe() {
        return cod_bebe;
    }

    public void setCod_bebe(int cod_bebe) {
        this.cod_bebe = cod_bebe;
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

    public LocalDate getFecha_nac() {
        return fecha_nac;
    }

    public void setFecha_nac(LocalDate fecha_nac) {
        this.fecha_nac = fecha_nac;
    }

    public Pasajero getTutor_ida() {
        return tutor_ida;
    }

    public void setTutor_ida(Pasajero tutor_ida) {
        this.tutor_ida = tutor_ida;
    }

    public Pasajero getTutor_vuelta() {
        return tutor_vuelta;
    }

    public void setTutor_vuelta(Pasajero tutor_vuelta) {
        this.tutor_vuelta = tutor_vuelta;
    }

    @Override
    public String toString() {
        return "Bebe{" + "cod_bebe=" + cod_bebe + ", nombre=" + nombre + ", apellidos=" + apellidos + ", nif=" + nif + ", fecha_nac=" + fecha_nac + '}';
    }
    
}
