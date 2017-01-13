package Dao;

import Clases.*;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;
/**
 *
 * @author Carlos
 */
public class Operacion {

    public Operacion() {
    }
    
    public ArrayList<Vuelo> buscarVuelosIda(Connection conex, String origen, String destino, LocalDate fecha, int num_viajeros) throws SQLException {
        ArrayList<Vuelo> aVuelos = new ArrayList();
        String iataorigen="";
        String iatadestino="";
        int conexion=0;
        String fechavuelo = fecha.toString();
        Aeropuerto a1 = new Aeropuerto();
        Aeropuerto a2 = new Aeropuerto();
        
        PreparedStatement sentenciaiataorigen = conex.prepareStatement("SELECT a.* FROM aeropuerto a WHERE a.CIUDAD LIKE ?");
        sentenciaiataorigen.setString(1, origen);
        
        ResultSet resultado1 = sentenciaiataorigen.executeQuery();
        
        while(resultado1.next()){
            iataorigen = resultado1.getString("CODIGO_IATA");
            a1.setCiudad(resultado1.getString("CIUDAD"));
            a1.setPais(resultado1.getString("PAIS"));
            a1.setIata(iataorigen);
        }
        
        
        PreparedStatement sentenciaiatadestino = conex.prepareStatement("SELECT a.* FROM aeropuerto a WHERE a.CIUDAD LIKE ?");
        sentenciaiatadestino.setString(1, destino);
        
        ResultSet resultado2 = sentenciaiatadestino.executeQuery();
        
        while(resultado2.next()){
            iatadestino = resultado2.getString("CODIGO_IATA");
            a2.setCiudad(resultado2.getString("CIUDAD"));
            a2.setPais(resultado2.getString("PAIS"));
            a2.setIata(iatadestino);
        }
        
        
        PreparedStatement sentenciaconexion = conex.prepareStatement("SELECT c.CODIGO_CONEXION FROM conexion c WHERE c.ORIGEN LIKE ? AND c.DESTINO LIKE ?");
        sentenciaconexion.setString(1, iataorigen);
        sentenciaconexion.setString(2, iatadestino);
        
        ResultSet resultado3 = sentenciaconexion.executeQuery();
        
        while(resultado3.next()){
            conexion = resultado3.getInt("CODIGO_CONEXION");
        }
        
        
        PreparedStatement sentenciavuelos = conex.prepareStatement("SELECT v.*, ABS(DATEDIFF(v.FECHA, ?)) AS fecha FROM vuelo v WHERE v.CONEXION = ? AND v.ASIENTOS_LIBRES >= ? AND ABS(DATEDIFF(v.FECHA, ?)) <= 3 AND ABS(DATEDIFF(v.FECHA, ?)) >= -3 ORDER BY fecha");
        sentenciavuelos.setString(1, fechavuelo);
        sentenciavuelos.setInt(2, conexion);
        sentenciavuelos.setInt(3, num_viajeros);
        sentenciavuelos.setString(4, fechavuelo);
        sentenciavuelos.setString(5, fechavuelo);
        
        ResultSet resultado4 = sentenciavuelos.executeQuery();
        
        while(resultado4.next()){
            LocalDate fecha1 = LocalDate.parse(resultado4.getString("FECHA"));
            LocalTime horasalida = LocalTime.parse(resultado4.getString("HORA_SALIDA"));
            LocalTime horallegada = LocalTime.parse(resultado4.getString("HORA_LLEGADA"));
            int precio = resultado4.getInt("PRECIO");
            int codigo_vuelo = resultado4.getInt("CODIGO_VUELO");
            Vuelo v1 = new Vuelo(a1, a2, fecha1, horasalida, horallegada, precio);
            v1.setConexion(conexion);
            v1.setCodigo_vuelo(codigo_vuelo);
            v1.setAsientos_libres(resultado4.getInt("ASIENTOS_LIBRES"));
            aVuelos.add(v1);
        }
        
        return aVuelos;
    }
    
    public ArrayList<Vuelo> buscarVuelosVuelta(Connection conex, String origen, String destino, LocalDate fecha, int num_viajeros) throws SQLException {
        ArrayList<Vuelo> aVuelos = new ArrayList();
        String iataorigen="";
        String iatadestino="";
        int conexion=0;
        String fechavuelo = fecha.toString();
        Aeropuerto a1 = new Aeropuerto();
        Aeropuerto a2 = new Aeropuerto();
        
        PreparedStatement sentenciaiataorigen = conex.prepareStatement("SELECT a.* FROM aeropuerto a WHERE a.CIUDAD LIKE ?");
        sentenciaiataorigen.setString(1, origen);
        
        ResultSet resultado1 = sentenciaiataorigen.executeQuery();
        
        while(resultado1.next()){
            iataorigen = resultado1.getString("CODIGO_IATA");
            a1.setCiudad(resultado1.getString("CIUDAD"));
            a1.setPais(resultado1.getString("PAIS"));
            a1.setIata(iataorigen);
        }
        
        
        PreparedStatement sentenciaiatadestino = conex.prepareStatement("SELECT a.* FROM aeropuerto a WHERE a.CIUDAD LIKE ?");
        sentenciaiatadestino.setString(1, destino);
        
        ResultSet resultado2 = sentenciaiatadestino.executeQuery();
        
        while(resultado2.next()){
            iatadestino = resultado2.getString("CODIGO_IATA");
            a2.setCiudad(resultado2.getString("CIUDAD"));
            a2.setPais(resultado2.getString("PAIS"));
            a2.setIata(iatadestino);
        }
        
        
        PreparedStatement sentenciaconexion = conex.prepareStatement("SELECT c.CODIGO_CONEXION FROM conexion c WHERE c.ORIGEN LIKE ? AND c.DESTINO LIKE ?");
        sentenciaconexion.setString(1, iataorigen);
        sentenciaconexion.setString(2, iatadestino);
        
        ResultSet resultado3 = sentenciaconexion.executeQuery();
        
        while(resultado3.next()){
            conexion = resultado3.getInt("CODIGO_CONEXION");
        }
        
        
        PreparedStatement sentenciavuelos = conex.prepareStatement("SELECT v.*, ABS(DATEDIFF(v.FECHA, ?)) AS fecha FROM vuelo v WHERE v.CONEXION = ? AND v.ASIENTOS_LIBRES >= ? AND ABS(DATEDIFF(v.FECHA, ?)) <= 3 AND ABS(DATEDIFF(v.FECHA, ?)) >=-3 ORDER BY fecha");
        sentenciavuelos.setString(1, fechavuelo);
        sentenciavuelos.setInt(2, conexion);
        sentenciavuelos.setInt(3, num_viajeros);
        sentenciavuelos.setString(4, fechavuelo);
        sentenciavuelos.setString(5, fechavuelo);
        
        ResultSet resultado4 = sentenciavuelos.executeQuery();
        
        while(resultado4.next()){
            LocalDate fecha1 = LocalDate.parse(resultado4.getString("FECHA"));
            LocalTime horasalida = LocalTime.parse(resultado4.getString("HORA_SALIDA"));
            LocalTime horallegada = LocalTime.parse(resultado4.getString("HORA_LLEGADA"));
            int precio = resultado4.getInt("PRECIO");
            int codigo_vuelo = resultado4.getInt("CODIGO_VUELO");
            Vuelo v1 = new Vuelo(a1, a2, fecha1, horasalida, horallegada, precio);
            v1.setConexion(conexion);
            v1.setCodigo_vuelo(codigo_vuelo);
            v1.setAsientos_libres(resultado4.getInt("ASIENTOS_LIBRES"));
            aVuelos.add(v1);
        }
        
        return aVuelos;
    }
    
    public ArrayList<Servicio> sacarServicios(Connection conex) throws SQLException{
        ArrayList<Servicio> aServicios = new ArrayList();
        
        PreparedStatement sentenciaservicios = conex.prepareStatement("SELECT s.* FROM servicio s");
        
        ResultSet resultado = sentenciaservicios.executeQuery();
        
        while(resultado.next()){
            int cod_servicio = resultado.getInt("CODIGO_SERVICIO");
            String nombre = resultado.getString("NOMBRE");
            String descripcion = resultado.getString("DESCRIPCION");
            int precio = resultado.getInt("PRECIO");
            Servicio s1 = new Servicio(nombre, descripcion, precio);
            s1.setCodigo_servicio(cod_servicio);
            aServicios.add(s1);
        }
        
        return aServicios;
    }
    
    public ArrayList<Boolean> sacarAsientosLibres(Connection conex, int cod_vuelo)throws SQLException{
        ArrayList<Boolean> aBoolean = new ArrayList<Boolean>(Arrays.asList(new Boolean[9]));
        Collections.fill(aBoolean, Boolean.TRUE);
        
        ArrayList<Integer> aNum = new ArrayList();
        
        PreparedStatement sentenciaasientos1 = conex.prepareStatement("SELECT ocupacion.ASIENTO FROM ocupacion INNER JOIN reserva ON ocupacion.RESERVA=reserva.COD_RESERVA WHERE reserva.COD_VUELO_IDA=? AND ocupacion.TIPO='IDA'");
        sentenciaasientos1.setInt(1, cod_vuelo);
        
        ResultSet resultado = sentenciaasientos1.executeQuery();
        
        while(resultado.next()){
            aNum.add(resultado.getInt("ASIENTO"));
        }
        
        PreparedStatement sentenciaasientos2 = conex.prepareStatement("SELECT ocupacion.ASIENTO FROM ocupacion INNER JOIN reserva ON ocupacion.RESERVA=reserva.COD_RESERVA WHERE reserva.COD_VUELO_VUELTA=? AND ocupacion.TIPO='VUELTA'");
        sentenciaasientos2.setInt(1, cod_vuelo);
        
        ResultSet resultado2 = sentenciaasientos2.executeQuery();
        
        while(resultado2.next()){
            aNum.add(resultado2.getInt("ASIENTO"));
        }
        
        for (int i = 0; i < aNum.size(); i++) {
            int pos = aNum.get(i);
            aBoolean.set(pos-1, Boolean.FALSE);
        }
        
        return aBoolean;
    }
    
    public Pagador buscarPagador(Connection conex, String nif, String pass) throws SQLException{
        Pagador p1 = new Pagador();
        
        PreparedStatement sentenciapagador = conex.prepareStatement("SELECT pagador.* FROM pagador WHERE pagador.NIF LIKE ? AND pagador.PASS LIKE ?");
        sentenciapagador.setString(1, nif);
        sentenciapagador.setString(2, pass);
        
        ResultSet resultado = sentenciapagador.executeQuery();
        
        while(resultado.next()){
            p1.setCodigo_pagador(resultado.getInt("CODIGO_PAGADOR"));
            p1.setNombre(resultado.getString("NOMBRE"));
            p1.setApellidos(resultado.getString("APELLIDOS"));
            p1.setNif(resultado.getString("NIF"));
            p1.setPass(resultado.getString("PASS"));
            LocalDate fecha = LocalDate.parse(resultado.getString("FECHA_NAC"));
            p1.setFecha_nac(fecha);
            p1.setPoblacion(resultado.getString("POBLACION"));
            p1.setDireccion(resultado.getString("DIRECCION"));
        }
        
        return p1;
    }
    
    public ArrayList<Tarjeta> buscarTarjetas(Connection conex, Pagador pagador) throws SQLException{
        ArrayList<Tarjeta> aTarjetas = new ArrayList();
        Tarjeta t1 = new Tarjeta();
        int cod_pagador = pagador.getCodigo_pagador();
        
        PreparedStatement sentenciatarjeta = conex.prepareStatement("SELECT tarjeta.* FROM tarjeta WHERE tarjeta.COD_PAGADOR=? ORDER BY tarjeta.NUM_USOS");
        sentenciatarjeta.setInt(1, cod_pagador);
        
        ResultSet resultado = sentenciatarjeta.executeQuery();
        
        while(resultado.next()){
            t1.setCodigo_tarjeta(resultado.getInt("CODIGO_TARJETA"));
            t1.setNum_tarjeta(resultado.getString("NUM_TARJETA"));
            t1.setPagador(pagador);
            t1.setCod_seguridad(resultado.getString("CODSEGURIDAD"));
            t1.setPin(resultado.getInt("PIN"));
            t1.setNum_usos(resultado.getInt("NUM_USOS"));
            LocalDate fecha_cad = LocalDate.parse(resultado.getString("CADUCIDAD"));
            t1.setCaducidad(fecha_cad);
            aTarjetas.add(t1);
        }
        
        return aTarjetas;
    }
    
    public static String getCadenaAlfanumAleatoria (int longitud){
        String cadenaAleatoria = "";
        long milis = new java.util.GregorianCalendar().getTimeInMillis();
        Random r = new Random(milis);
        int i = 0;
        while (i < longitud) {
            char c = (char) r.nextInt(255);
            if ((c >= '0' && c <= '9') || (c >= 'A' && c <= 'Z')) {
                cadenaAleatoria += c;
                i++;
            }
        }
        return cadenaAleatoria;
    }
    
    public ArrayList buscarPasajero(Connection conex, Pasajero pasajero) throws SQLException{
        ArrayList h = new ArrayList();
        Boolean x = false;
        int cod = -1;
        
        PreparedStatement sentencia = conex.prepareStatement("SELECT pasajero.* FROM pasajero WHERE pasajero.NIF LIKE ?");
        sentencia.setString(1, pasajero.getNif());
        
        ResultSet resultado = sentencia.executeQuery();
        
        while(resultado.next()){
            x=true;
            cod = resultado.getInt("CODIGO_PASAJERO");
        }
        
        h.add(x);
        h.add(cod);
        
        return h;
    }
    
    public Boolean buscarPagador2(Connection conex, Pagador pagador) throws SQLException{
        Boolean h = false;
        
        PreparedStatement sentencia = conex.prepareStatement("SELECT pagador.* FROM pagador WHERE pagador.NIF LIKE ?");
        sentencia.setString(1, pagador.getNif());
        
        ResultSet resultado = sentencia.executeQuery();
        
        while(resultado.next()){
            h=true;
        }
        
        return h;
    }
    
    public Boolean buscarTarjeta2(Connection conex, Tarjeta tarjeta) throws SQLException{
        Boolean h = false;
        
        PreparedStatement sentencia = conex.prepareStatement("SELECT tarjeta.* FROM tarjeta WHERE tarjeta.NUM_TARJETA LIKE ?");
        sentencia.setString(1, tarjeta.getNum_tarjeta());
        
        ResultSet resultado = sentencia.executeQuery();
        
        while(resultado.next()){
            h=true;
        }
        
        return h;
    }
    
    public ArrayList buscarBebe(Connection conex, Bebe bebe) throws SQLException{
        ArrayList h = new ArrayList();
        Boolean x = false;
        int cod = -1;
        
        PreparedStatement sentencia = conex.prepareStatement("SELECT bebe.* FROM bebe WHERE bebe.NIF LIKE ?");
        sentencia.setString(1, bebe.getNif());
        
        ResultSet resultado = sentencia.executeQuery();
        
        while(resultado.next()){
            x=true;
            cod = resultado.getInt("CODIGO_BEBE");
        }
        
        h.add(x);
        h.add(cod);
        
        return h;
    }
    
    public Boolean insertarReserva(Connection conex, Reserva reserva) throws SQLException{
        Boolean h = false;
        
        try{
            conex.setAutoCommit(false);
            
            //Pasajeros Adultos
            for (int i = 0; i < reserva.getaPasajerosAdultos().size(); i++) {
                ArrayList aX = buscarPasajero(conex, reserva.getaPasajerosAdultos().get(i));
                Boolean x = (Boolean)aX.get(0);
                PreparedStatement sentenciapasajero;
                if(x){
                    reserva.getaPasajerosAdultos().get(i).setCodigo_pasajero((int)aX.get(1));
                    String fecha = reserva.getaPasajerosAdultos().get(i).getFecha_caducidad().toString();
                    sentenciapasajero = conex.prepareStatement("UPDATE pasajero SET pasajero.CADUCIDAD_NIF=? WHERE pasajero.NIF LIKE ?");
                    sentenciapasajero.setString(1, fecha);
                    sentenciapasajero.setString(2, reserva.getaPasajerosAdultos().get(i).getNif());
                }else{
                    sentenciapasajero = conex.prepareStatement("INSERT INTO pasajero (TRATAMIENTO, NOMBRE, APELLIDOS, NIF, CADUCIDAD_NIF, FECHA_NAC, TIPO) VALUES (?, ?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
                    sentenciapasajero.setString(1, reserva.getaPasajerosAdultos().get(i).getTratamiento());
                    sentenciapasajero.setString(2, reserva.getaPasajerosAdultos().get(i).getNombre());
                    sentenciapasajero.setString(3, reserva.getaPasajerosAdultos().get(i).getApellidos());
                    sentenciapasajero.setString(4, reserva.getaPasajerosAdultos().get(i).getNif());
                    sentenciapasajero.setString(5, reserva.getaPasajerosAdultos().get(i).getFecha_caducidad().toString());
                    sentenciapasajero.setString(6, reserva.getaPasajerosAdultos().get(i).getFecha_nac().toString());
                    sentenciapasajero.setString(7, reserva.getaPasajerosAdultos().get(i).getTipo());
                }
                sentenciapasajero.executeUpdate();
                if(!x){
                    ResultSet generatedIdpasajero = sentenciapasajero.getGeneratedKeys();
                    if(generatedIdpasajero.next()){
                        reserva.getaPasajerosAdultos().get(i).setCodigo_pasajero(generatedIdpasajero.getInt(1));
                    }
                }
            }
            //Pasajeros Niños
            if(!reserva.getaPasajerosNinos().isEmpty()){
                for (int i = 0; i < reserva.getaPasajerosNinos().size(); i++) {
                    ArrayList aX = buscarPasajero(conex, reserva.getaPasajerosNinos().get(i));
                    Boolean x = (Boolean)aX.get(0);
                    PreparedStatement sentenciapasajero;
                    if(x){
                        reserva.getaPasajerosNinos().get(i).setCodigo_pasajero((int)aX.get(1));
                        String fecha = reserva.getaPasajerosNinos().get(i).getFecha_caducidad().toString();
                        sentenciapasajero = conex.prepareStatement("UPDATE pasajero SET pasajero.CADUCIDAD_NIF=? WHERE pasajero.NIF LIKE ?");
                        sentenciapasajero.setString(1, fecha);
                        sentenciapasajero.setString(2, reserva.getaPasajerosNinos().get(i).getNif());
                    }else{
                        sentenciapasajero = conex.prepareStatement("INSERT INTO pasajero (TRATAMIENTO, NOMBRE, APELLIDOS, NIF, CADUCIDAD_NIF, FECHA_NAC, TIPO) VALUES (?, ?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
                        sentenciapasajero.setString(1, reserva.getaPasajerosNinos().get(i).getTratamiento());
                        sentenciapasajero.setString(2, reserva.getaPasajerosNinos().get(i).getNombre());
                        sentenciapasajero.setString(3, reserva.getaPasajerosNinos().get(i).getApellidos());
                        sentenciapasajero.setString(4, reserva.getaPasajerosNinos().get(i).getNif());
                        sentenciapasajero.setString(5, reserva.getaPasajerosNinos().get(i).getFecha_caducidad().toString());
                        sentenciapasajero.setString(6, reserva.getaPasajerosNinos().get(i).getFecha_nac().toString());
                        sentenciapasajero.setString(7, reserva.getaPasajerosNinos().get(i).getTipo());
                    }
                    sentenciapasajero.executeUpdate();
                    if(!x){
                        ResultSet generatedIdpasajeronino = sentenciapasajero.getGeneratedKeys();
                        if(generatedIdpasajeronino.next()){
                            reserva.getaPasajerosNinos().get(i).setCodigo_pasajero(generatedIdpasajeronino.getInt(1));
                        }
                    }
                }
            }
            
            //Pagador
            PreparedStatement sentenciapagador;
            Boolean y = buscarPagador2(conex, reserva.getPagador());
            if(y){
                sentenciapagador = conex.prepareStatement("UPDATE pagador SET pagador.POBLACION=?, pagador.DIRECCION=? WHERE pagador.NIF LIKE ?");
                sentenciapagador.setString(1, reserva.getPagador().getPoblacion());
                sentenciapagador.setString(2, reserva.getPagador().getDireccion());
                sentenciapagador.setString(3, reserva.getPagador().getNif());
            }else{
                sentenciapagador = conex.prepareStatement("INSERT INTO pagador (NIF, PASS, NOMBRE, APELLIDOS, FECHA_NAC, POBLACION, DIRECCION) VALUES (?, ?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
                sentenciapagador.setString(1, reserva.getPagador().getNif());
                sentenciapagador.setString(2, reserva.getPagador().getPass());
                sentenciapagador.setString(3, reserva.getPagador().getNombre());
                sentenciapagador.setString(4, reserva.getPagador().getApellidos());
                sentenciapagador.setString(5, reserva.getPagador().getFecha_nac().toString());
                sentenciapagador.setString(6, reserva.getPagador().getPoblacion());
                sentenciapagador.setString(7, reserva.getPagador().getDireccion());
            }
            sentenciapagador.executeUpdate();
            if(!y){
                ResultSet generatedIdpagador = sentenciapagador.getGeneratedKeys();
                if(generatedIdpagador.next()){
                    reserva.getPagador().setCodigo_pagador(generatedIdpagador.getInt(1));
                }
            }
                
            //Tarjeta
            PreparedStatement sentenciatarjeta;
            Boolean z = buscarTarjeta2(conex, reserva.getTarjeta());
            if(z){
                sentenciatarjeta = conex.prepareStatement("UPDATE tarjeta SET tarjeta.CADUCIDAD=?, tarjeta.NUM_USOS=tarjeta.NUM_USOS+1 WHERE tarjeta.NUM_TARJETA LIKE ?");
                sentenciatarjeta.setString(1, reserva.getTarjeta().getCaducidad().toString());
                sentenciatarjeta.setString(2, reserva.getTarjeta().getNum_tarjeta());
            }else{
                sentenciatarjeta = conex.prepareStatement("INSERT INTO tarjeta (NUM_TARJETA, COD_PAGADOR, CODSEGURIDAD, CADUCIDAD, PIN, NUM_USOS) VALUES(?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
                sentenciatarjeta.setString(1, reserva.getTarjeta().getNum_tarjeta());
                sentenciatarjeta.setInt(2, reserva.getPagador().getCodigo_pagador());
                sentenciatarjeta.setString(3, reserva.getTarjeta().getCod_seguridad());
                sentenciatarjeta.setString(4, reserva.getTarjeta().getCaducidad().toString());
                sentenciatarjeta.setInt(5, reserva.getTarjeta().getPin());
                sentenciatarjeta.setInt(6, reserva.getTarjeta().getNum_usos()+1);
            }
            sentenciatarjeta.executeUpdate();
            if(!z){
                ResultSet generatedIdtarjeta = sentenciatarjeta.getGeneratedKeys();
                if(generatedIdtarjeta.next()){
                    reserva.getTarjeta().setCodigo_tarjeta(generatedIdtarjeta.getInt(1));
                }
            }
            
            //Reserva
            PreparedStatement sentenciareserva;
            if(reserva.getVuelo_vuelta()!=null){
                sentenciareserva = conex.prepareStatement("INSERT INTO reserva (COD_VUELO_IDA, COD_VUELO_VUELTA, COD_RESERVA, TARJETA, NUMERO_VIAJEROS, PRECIO_TOTAL) VALUES(?, ?, ?, ?, ?, ?)");
                sentenciareserva.setInt(1, reserva.getVuelo_ida().getCodigo_vuelo());
                sentenciareserva.setInt(2, reserva.getVuelo_vuelta().getCodigo_vuelo());
                sentenciareserva.setString(3, reserva.getCod_reserva());
                sentenciareserva.setInt(4, reserva.getTarjeta().getCodigo_tarjeta());
                sentenciareserva.setInt(5, reserva.getNum_viajeros());
                sentenciareserva.setInt(6, reserva.getPrecio_total());
            }else{
                sentenciareserva = conex.prepareStatement("INSERT INTO reserva (COD_VUELO_IDA, COD_RESERVA, TARJETA, NUMERO_VIAJEROS, PRECIO_TOTAL) VALUES(?, ?, ?, ?, ?)");
                sentenciareserva.setInt(1, reserva.getVuelo_ida().getCodigo_vuelo());
                sentenciareserva.setString(2, reserva.getCod_reserva());
                sentenciareserva.setInt(3, reserva.getTarjeta().getCodigo_tarjeta());
                sentenciareserva.setInt(4, reserva.getNum_viajeros());
                sentenciareserva.setInt(5, reserva.getPrecio_total());
            }
            sentenciareserva.executeUpdate();
            
            //Ocupacion y servicios Adultos
            for (int j = 0; j < reserva.getaPasajerosAdultos().size(); j++) {
                //Bebe
                if(reserva.getaPasajerosAdultos().get(j).getBebe()!=null){
                    PreparedStatement sentenciabebe;
                    ArrayList aX = buscarBebe(conex, reserva.getaPasajerosAdultos().get(j).getBebe());
                    Boolean x = (Boolean)aX.get(0);
                    if(x){
                        reserva.getaPasajerosAdultos().get(j).getBebe().setCod_bebe((int)aX.get(1));
                        sentenciabebe = conex.prepareStatement("UPDATE bebe SET bebe.NIF=? WHERE bebe.NIF LIKE ?");
                        sentenciabebe.setString(1, reserva.getaPasajerosAdultos().get(j).getBebe().getNif());
                        sentenciabebe.setString(2, reserva.getaPasajerosAdultos().get(j).getBebe().getNif());
                    }else{
                        sentenciabebe = conex.prepareStatement("INSERT INTO bebe (NOMBRE, APELLIDOS, NIF, FECHA_NAC) VALUES(?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
                        sentenciabebe.setString(1, reserva.getaPasajerosAdultos().get(j).getBebe().getNombre());
                        sentenciabebe.setString(2, reserva.getaPasajerosAdultos().get(j).getBebe().getApellidos());
                        sentenciabebe.setString(3, reserva.getaPasajerosAdultos().get(j).getBebe().getNif());
                        sentenciabebe.setString(4, reserva.getaPasajerosAdultos().get(j).getBebe().getFecha_nac().toString());
                    }
                    sentenciabebe.executeUpdate();
                    if(!x){
                        ResultSet generatedIdbebe = sentenciabebe.getGeneratedKeys();
                        if(generatedIdbebe.next()){
                            reserva.getaPasajerosAdultos().get(j).getBebe().setCod_bebe(generatedIdbebe.getInt(1));
                        }
                    }
                }
                //OcupacionIda
                PreparedStatement sentenciaocuida;
                if(reserva.getaPasajerosAdultos().get(j).getBebe()!=null){
                    sentenciaocuida = conex.prepareStatement("INSERT INTO ocupacion (RESERVA, TIPO, PASAJERO, ASIENTO, BEBE) VALUES(?, 'IDA', ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
                    sentenciaocuida.setString(1, reserva.getCod_reserva());
                    sentenciaocuida.setInt(2, reserva.getaPasajerosAdultos().get(j).getCodigo_pasajero());
                    sentenciaocuida.setInt(3, reserva.getaPasajerosAdultos().get(j).getAsiento_ida());
                    sentenciaocuida.setInt(4, reserva.getaPasajerosAdultos().get(j).getBebe().getCod_bebe());
                }else{
                    sentenciaocuida = conex.prepareStatement("INSERT INTO ocupacion (RESERVA, TIPO, PASAJERO, ASIENTO) VALUES(?, 'IDA', ?, ?)", Statement.RETURN_GENERATED_KEYS);
                    sentenciaocuida.setString(1, reserva.getCod_reserva());
                    sentenciaocuida.setInt(2, reserva.getaPasajerosAdultos().get(j).getCodigo_pasajero());
                    sentenciaocuida.setInt(3, reserva.getaPasajerosAdultos().get(j).getAsiento_ida());
                }
                sentenciaocuida.executeUpdate();
                int idOcuida = 0;
                ResultSet generatedIdocuida = sentenciaocuida.getGeneratedKeys();
                if(generatedIdocuida.next()){
                    idOcuida = generatedIdocuida.getInt(1);
                }
                //Servicios Ocupacion Ida
                PreparedStatement sentenciaservida;
                if(!reserva.getaPasajerosAdultos().get(j).getaServiciosIda().isEmpty()){
                    for (int o = 0; o < reserva.getaPasajerosAdultos().get(j).getaServiciosIda().size(); o++) {
                        sentenciaservida = conex.prepareStatement("INSERT INTO reserva_servicio (COD_OCUPACION, COD_SERVICIO) VALUES(?, ?)");
                        sentenciaservida.setInt(1, idOcuida);
                        sentenciaservida.setInt(2, reserva.getaPasajerosAdultos().get(j).getaServiciosIda().get(o).getCodigo_servicio());
                        sentenciaservida.executeUpdate();
                    }
                }
                //OcupacionVuelta
                PreparedStatement sentenciaocuvuelta;
                if(!reserva.getaPasajerosAdultos().get(j).getaServiciosVuelta().isEmpty()){
                    if(reserva.getaPasajerosAdultos().get(j).getBebe()!=null){
                        sentenciaocuvuelta = conex.prepareStatement("INSERT INTO ocupacion (RESERVA, TIPO, PASAJERO, ASIENTO, BEBE) VALUES(?, 'VUELTA', ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
                        sentenciaocuvuelta.setString(1, reserva.getCod_reserva());
                        sentenciaocuvuelta.setInt(2, reserva.getaPasajerosAdultos().get(j).getCodigo_pasajero());
                        sentenciaocuvuelta.setInt(3, reserva.getaPasajerosAdultos().get(j).getAsiento_vuelta());
                        sentenciaocuvuelta.setInt(4, reserva.getaPasajerosAdultos().get(j).getBebe().getCod_bebe());
                    }else{
                        sentenciaocuvuelta = conex.prepareStatement("INSERT INTO ocupacion (RESERVA, TIPO, PASAJERO, ASIENTO) VALUES(?, 'VUELTA', ?, ?)", Statement.RETURN_GENERATED_KEYS);
                        sentenciaocuvuelta.setString(1, reserva.getCod_reserva());
                        sentenciaocuvuelta.setInt(2, reserva.getaPasajerosAdultos().get(j).getCodigo_pasajero());
                        sentenciaocuvuelta.setInt(3, reserva.getaPasajerosAdultos().get(j).getAsiento_vuelta());
                    }
                    sentenciaocuvuelta.executeUpdate();
                    int idOcuvuelta = 0;
                    ResultSet generatedIdocuvuelta = sentenciaocuvuelta.getGeneratedKeys();
                    if(generatedIdocuvuelta.next()){
                        idOcuvuelta = generatedIdocuvuelta.getInt(1);
                    }
                    //Servicios Ocupacion Vuelta
                    PreparedStatement sentenciaservvuelta;
                    if(!reserva.getaPasajerosAdultos().get(j).getaServiciosVuelta().isEmpty()){
                        for (int o = 0; o < reserva.getaPasajerosAdultos().get(j).getaServiciosVuelta().size(); o++) {
                            sentenciaservvuelta = conex.prepareStatement("INSERT INTO reserva_servicio (COD_OCUPACION, COD_SERVICIO) VALUES(?, ?)");
                            sentenciaservvuelta.setInt(1, idOcuvuelta);
                            sentenciaservvuelta.setInt(2, reserva.getaPasajerosAdultos().get(j).getaServiciosVuelta().get(o).getCodigo_servicio());
                            sentenciaservvuelta.executeUpdate();
                        }
                    }
                }
            }
            //Ocupacion y servicios Niños
            if(!reserva.getaPasajerosNinos().isEmpty()){
                for (int j = 0; j < reserva.getaPasajerosNinos().size(); j++) {
                    //OcupacionIda
                    PreparedStatement sentenciaocuida;
                        sentenciaocuida = conex.prepareStatement("INSERT INTO ocupacion (RESERVA, TIPO, PASAJERO, ASIENTO) VALUES(?, 'IDA', ?, ?)", Statement.RETURN_GENERATED_KEYS);
                        sentenciaocuida.setString(1, reserva.getCod_reserva());
                        sentenciaocuida.setInt(2, reserva.getaPasajerosNinos().get(j).getCodigo_pasajero());
                        sentenciaocuida.setInt(3, reserva.getaPasajerosNinos().get(j).getAsiento_ida());
                    sentenciaocuida.executeUpdate();
                    int idOcuida = 0;
                    ResultSet generatedIdocuida = sentenciaocuida.getGeneratedKeys();
                    if(generatedIdocuida.next()){
                        idOcuida = generatedIdocuida.getInt(1);
                    }
                    //Servicios Ocupacion Ida
                    PreparedStatement sentenciaservida;
                    if(!reserva.getaPasajerosNinos().get(j).getaServiciosIda().isEmpty()){
                        for (int o = 0; o < reserva.getaPasajerosNinos().get(j).getaServiciosIda().size(); o++) {
                            sentenciaservida = conex.prepareStatement("INSERT INTO reserva_servicio (COD_OCUPACION, COD_SERVICIO) VALUES(?, ?)");
                            sentenciaservida.setInt(1, idOcuida);
                            sentenciaservida.setInt(2, reserva.getaPasajerosNinos().get(j).getaServiciosIda().get(o).getCodigo_servicio());
                            sentenciaservida.executeUpdate();
                        }
                    }
                    //OcupacionVuelta
                    PreparedStatement sentenciaocuvuelta;
                    if(!reserva.getaPasajerosNinos().get(j).getaServiciosVuelta().isEmpty()){
                            sentenciaocuvuelta = conex.prepareStatement("INSERT INTO ocupacion (RESERVA, TIPO, PASAJERO, ASIENTO) VALUES(?, 'VUELTA', ?, ?)", Statement.RETURN_GENERATED_KEYS);
                            sentenciaocuvuelta.setString(1, reserva.getCod_reserva());
                            sentenciaocuvuelta.setInt(2, reserva.getaPasajerosNinos().get(j).getCodigo_pasajero());
                            sentenciaocuvuelta.setInt(3, reserva.getaPasajerosNinos().get(j).getAsiento_vuelta());
                        sentenciaocuvuelta.executeUpdate();
                        int idOcuvuelta = 0;
                        ResultSet generatedIdocuvuelta = sentenciaocuvuelta.getGeneratedKeys();
                        if(generatedIdocuvuelta.next()){
                            idOcuvuelta = generatedIdocuvuelta.getInt(1);
                        }
                        //Servicios Ocupacion Vuelta
                        PreparedStatement sentenciaservvuelta;
                        if(!reserva.getaPasajerosNinos().get(j).getaServiciosVuelta().isEmpty()){
                            for (int o = 0; o < reserva.getaPasajerosNinos().get(j).getaServiciosVuelta().size(); o++) {
                                sentenciaservvuelta = conex.prepareStatement("INSERT INTO reserva_servicio (COD_OCUPACION, COD_SERVICIO) VALUES(?, ?)");
                                sentenciaservvuelta.setInt(1, idOcuvuelta);
                                sentenciaservvuelta.setInt(2, reserva.getaPasajerosNinos().get(j).getaServiciosVuelta().get(o).getCodigo_servicio());
                                sentenciaservvuelta.executeUpdate();
                            }
                        }
                    }
                }
            }
            
            conex.commit();
            h = true;
        }catch (SQLException ex){
            conex.rollback();
            h = false;
        }
        
        return h;
    }
}