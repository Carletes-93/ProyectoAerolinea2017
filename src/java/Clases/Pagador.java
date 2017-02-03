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
public class Pagador {
    private int codigo_pagador;
    private String nombre;
    private String apellidos;
    private String nif;
    private String email;
    private String pass;
    private LocalDate fecha_nac;
    private String poblacion;
    private String direccion;
    private String tipo;

    public Pagador() {
    }

    public Pagador(String nombre, String apellidos, String nif, LocalDate fecha_nac, String poblacion, String direccion) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.nif = nif;
        this.fecha_nac = fecha_nac;
        this.poblacion = poblacion;
        this.direccion = direccion;
    }

    public int getCodigo_pagador() {
        return codigo_pagador;
    }

    public void setCodigo_pagador(int codigo_pagador) {
        this.codigo_pagador = codigo_pagador;
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

    public String getPoblacion() {
        return poblacion;
    }

    public void setPoblacion(String poblacion) {
        this.poblacion = poblacion;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    @Override
    public String toString() {
        return "Pagador{" + "codigo_pagador=" + codigo_pagador + ", nombre=" + nombre + ", apellidos=" + apellidos + ", nif=" + nif + ", fecha_nac=" + fecha_nac + ", poblacion=" + poblacion + ", direccion=" + direccion + '}';
    }
}
