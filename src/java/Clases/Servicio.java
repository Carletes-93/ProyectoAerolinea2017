/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;

/**
 *
 * @author Carlos
 */
public class Servicio {
    private int codigo_servicio;
    private String nombre;
    private String descripcion;
    private int precio;

    public Servicio() {
    }

    public Servicio(String nombre, String descripcion, int precio) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
    }

    public int getCodigo_servicio() {
        return codigo_servicio;
    }

    public void setCodigo_servicio(int codigo_servicio) {
        this.codigo_servicio = codigo_servicio;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getPrecio() {
        return precio;
    }

    public void setPrecio(int precio) {
        this.precio = precio;
    }

    @Override
    public String toString() {
        return "Servicio{" + "codigo_servicio=" + codigo_servicio + ", nombre=" + nombre + ", descripcion=" + descripcion + ", precio=" + precio + '}';
    }
    
}
