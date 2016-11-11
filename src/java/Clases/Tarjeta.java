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
public class Tarjeta {
    private int codigo_tarjeta;
    private String num_tarjeta;
    private Pagador pagador;
    private String cod_seguridad;
    private LocalDate caducidad;
    private int pin;
    private int num_usos;
    private String tipo;

    public Tarjeta() {
    }

    public Tarjeta(String num_tarjeta, Pagador pagador, String cod_seguridad, LocalDate caducidad, int pin) {
        this.num_tarjeta = num_tarjeta;
        this.pagador = pagador;
        this.cod_seguridad = cod_seguridad;
        this.caducidad = caducidad;
        this.pin = pin;
    }

    public int getCodigo_tarjeta() {
        return codigo_tarjeta;
    }

    public void setCodigo_tarjeta(int codigo_tarjeta) {
        this.codigo_tarjeta = codigo_tarjeta;
    }

    public String getNum_tarjeta() {
        return num_tarjeta;
    }

    public void setNum_tarjeta(String num_tarjeta) {
        this.num_tarjeta = num_tarjeta;
    }

    public Pagador getPagador() {
        return pagador;
    }

    public void setPagador(Pagador pagador) {
        this.pagador = pagador;
    }

    public String getCod_seguridad() {
        return cod_seguridad;
    }

    public void setCod_seguridad(String cod_seguridad) {
        this.cod_seguridad = cod_seguridad;
    }

    public LocalDate getCaducidad() {
        return caducidad;
    }

    public void setCaducidad(LocalDate caducidad) {
        this.caducidad = caducidad;
    }

    public int getPin() {
        return pin;
    }

    public void setPin(int pin) {
        this.pin = pin;
    }

    public int getNum_usos() {
        return num_usos;
    }

    public void setNum_usos(int num_usos) {
        this.num_usos = num_usos;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    
    @Override
    public String toString() {
        return "Tarjeta{" + "codigo_tarjeta=" + codigo_tarjeta + ", num_tarjeta=" + num_tarjeta + ", pagador=" + pagador + ", cod_seguridad=" + cod_seguridad + ", caducidad=" + caducidad + ", pin=" + pin + ", num_usos=" + num_usos + '}';
    }
}
