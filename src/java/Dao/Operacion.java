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

    public ArrayList<Aeropuerto> cargarOrigenes(Connection conex) throws SQLException {
        String sentencia = "SELECT * FROM aeropuerto ORDER BY CIUDAD";

        PreparedStatement prepStm = conex.prepareStatement(sentencia);
        ResultSet res = prepStm.executeQuery();

        ArrayList<Aeropuerto> aOrigenes = new ArrayList();
        while (res.next()) {
            Aeropuerto objAeropuerto = new Aeropuerto(res.getString("CIUDAD"), res.getString("PAIS"));
            objAeropuerto.setIata(res.getString("CODIGO_IATA"));
            aOrigenes.add(objAeropuerto);
        }
        return aOrigenes;
    }

    public ArrayList<Aeropuerto> cargarDestinos(Connection conex, String IATA_origen) throws SQLException {

        String sentencia = "SELECT * FROM aeropuerto WHERE CODIGO_IATA NOT LIKE '" + IATA_origen + "' ORDER BY CIUDAD";

        PreparedStatement prepStm = conex.prepareStatement(sentencia);
        ResultSet res = prepStm.executeQuery();

        ArrayList<Aeropuerto> aDestinos = new ArrayList();
        while (res.next()) {
            Aeropuerto objAeropuerto = new Aeropuerto(res.getString("CIUDAD"), res.getString("PAIS"));
            objAeropuerto.setIata(res.getString("CODIGO_IATA"));
            aDestinos.add(objAeropuerto);
        }
        return aDestinos;
    }

    public ArrayList<Vuelo> buscarVuelosIda(Connection conex, String origen, String destino, LocalDate fecha, int num_viajeros) throws SQLException {
        ArrayList<Vuelo> aVuelos = new ArrayList();
        String iataorigen = "";
        String iatadestino = "";
        int conexion = 0;
        String fechavuelo = fecha.toString();
        Aeropuerto a1 = new Aeropuerto();
        Aeropuerto a2 = new Aeropuerto();

        PreparedStatement sentenciaiataorigen = conex.prepareStatement("SELECT a.* FROM aeropuerto a WHERE a.CIUDAD LIKE ?");
        sentenciaiataorigen.setString(1, origen);

        ResultSet resultado1 = sentenciaiataorigen.executeQuery();

        while (resultado1.next()) {
            iataorigen = resultado1.getString("CODIGO_IATA");
            a1.setCiudad(resultado1.getString("CIUDAD"));
            a1.setPais(resultado1.getString("PAIS"));
            a1.setIata(iataorigen);
        }

        PreparedStatement sentenciaiatadestino = conex.prepareStatement("SELECT a.* FROM aeropuerto a WHERE a.CIUDAD LIKE ?");
        sentenciaiatadestino.setString(1, destino);

        ResultSet resultado2 = sentenciaiatadestino.executeQuery();

        while (resultado2.next()) {
            iatadestino = resultado2.getString("CODIGO_IATA");
            a2.setCiudad(resultado2.getString("CIUDAD"));
            a2.setPais(resultado2.getString("PAIS"));
            a2.setIata(iatadestino);
        }

        PreparedStatement sentenciaconexion = conex.prepareStatement("SELECT c.CODIGO_CONEXION FROM conexion c WHERE c.ORIGEN LIKE ? AND c.DESTINO LIKE ?");
        sentenciaconexion.setString(1, iataorigen);
        sentenciaconexion.setString(2, iatadestino);

        ResultSet resultado3 = sentenciaconexion.executeQuery();

        while (resultado3.next()) {
            conexion = resultado3.getInt("CODIGO_CONEXION");
        }

        PreparedStatement sentenciavuelos = conex.prepareStatement("SELECT v.*, ABS(DATEDIFF(v.FECHA, ?)) AS fecha FROM vuelo v WHERE v.CONEXION = ? AND v.ASIENTOS_LIBRES >= ? AND ABS(DATEDIFF(v.FECHA, ?)) <= 3 AND ABS(DATEDIFF(v.FECHA, ?)) >= -3 ORDER BY fecha");
        sentenciavuelos.setString(1, fechavuelo);
        sentenciavuelos.setInt(2, conexion);
        sentenciavuelos.setInt(3, num_viajeros);
        sentenciavuelos.setString(4, fechavuelo);
        sentenciavuelos.setString(5, fechavuelo);

        ResultSet resultado4 = sentenciavuelos.executeQuery();

        while (resultado4.next()) {
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
        String iataorigen = "";
        String iatadestino = "";
        int conexion = 0;
        String fechavuelo = fecha.toString();
        Aeropuerto a1 = new Aeropuerto();
        Aeropuerto a2 = new Aeropuerto();

        PreparedStatement sentenciaiataorigen = conex.prepareStatement("SELECT a.* FROM aeropuerto a WHERE a.CIUDAD LIKE ?");
        sentenciaiataorigen.setString(1, origen);

        ResultSet resultado1 = sentenciaiataorigen.executeQuery();

        while (resultado1.next()) {
            iataorigen = resultado1.getString("CODIGO_IATA");
            a1.setCiudad(resultado1.getString("CIUDAD"));
            a1.setPais(resultado1.getString("PAIS"));
            a1.setIata(iataorigen);
        }

        PreparedStatement sentenciaiatadestino = conex.prepareStatement("SELECT a.* FROM aeropuerto a WHERE a.CIUDAD LIKE ?");
        sentenciaiatadestino.setString(1, destino);

        ResultSet resultado2 = sentenciaiatadestino.executeQuery();

        while (resultado2.next()) {
            iatadestino = resultado2.getString("CODIGO_IATA");
            a2.setCiudad(resultado2.getString("CIUDAD"));
            a2.setPais(resultado2.getString("PAIS"));
            a2.setIata(iatadestino);
        }

        PreparedStatement sentenciaconexion = conex.prepareStatement("SELECT c.CODIGO_CONEXION FROM conexion c WHERE c.ORIGEN LIKE ? AND c.DESTINO LIKE ?");
        sentenciaconexion.setString(1, iataorigen);
        sentenciaconexion.setString(2, iatadestino);

        ResultSet resultado3 = sentenciaconexion.executeQuery();

        while (resultado3.next()) {
            conexion = resultado3.getInt("CODIGO_CONEXION");
        }

        PreparedStatement sentenciavuelos = conex.prepareStatement("SELECT v.*, ABS(DATEDIFF(v.FECHA, ?)) AS fecha FROM vuelo v WHERE v.CONEXION = ? AND v.ASIENTOS_LIBRES >= ? AND ABS(DATEDIFF(v.FECHA, ?)) <= 3 AND ABS(DATEDIFF(v.FECHA, ?)) >=-3 ORDER BY fecha");
        sentenciavuelos.setString(1, fechavuelo);
        sentenciavuelos.setInt(2, conexion);
        sentenciavuelos.setInt(3, num_viajeros);
        sentenciavuelos.setString(4, fechavuelo);
        sentenciavuelos.setString(5, fechavuelo);

        ResultSet resultado4 = sentenciavuelos.executeQuery();

        while (resultado4.next()) {
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

    public ArrayList<Servicio> sacarServicios(Connection conex) throws SQLException {
        ArrayList<Servicio> aServicios = new ArrayList();

        PreparedStatement sentenciaservicios = conex.prepareStatement("SELECT s.* FROM servicio s");

        ResultSet resultado = sentenciaservicios.executeQuery();

        while (resultado.next()) {
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

    public ArrayList<Boolean> sacarAsientosLibres(Connection conex, int cod_vuelo) throws SQLException {
        ArrayList<Boolean> aBoolean = new ArrayList<Boolean>(Arrays.asList(new Boolean[9]));
        Collections.fill(aBoolean, Boolean.TRUE);

        ArrayList<Integer> aNum = new ArrayList();

        PreparedStatement sentenciaasientos1 = conex.prepareStatement("SELECT ocupacion.ASIENTO FROM ocupacion INNER JOIN reserva ON ocupacion.RESERVA=reserva.COD_RESERVA WHERE reserva.COD_VUELO_IDA=? AND ocupacion.TIPO='IDA'");
        sentenciaasientos1.setInt(1, cod_vuelo);

        ResultSet resultado = sentenciaasientos1.executeQuery();

        while (resultado.next()) {
            aNum.add(resultado.getInt("ASIENTO"));
        }

        PreparedStatement sentenciaasientos2 = conex.prepareStatement("SELECT ocupacion.ASIENTO FROM ocupacion INNER JOIN reserva ON ocupacion.RESERVA=reserva.COD_RESERVA WHERE reserva.COD_VUELO_VUELTA=? AND ocupacion.TIPO='VUELTA'");
        sentenciaasientos2.setInt(1, cod_vuelo);

        ResultSet resultado2 = sentenciaasientos2.executeQuery();

        while (resultado2.next()) {
            aNum.add(resultado2.getInt("ASIENTO"));
        }

        for (int i = 0; i < aNum.size(); i++) {
            int pos = aNum.get(i);
            aBoolean.set(pos - 1, Boolean.FALSE);
        }

        return aBoolean;
    }

    public Pagador buscarPagador(Connection conex, String correo, String pass) throws SQLException {
        Pagador p1 = new Pagador();

        PreparedStatement sentenciapagador = conex.prepareStatement("SELECT pagador.* FROM pagador WHERE pagador.EMAIL LIKE ? AND pagador.PASS LIKE ?");
        sentenciapagador.setString(1, correo);
        sentenciapagador.setString(2, pass);

        ResultSet resultado = sentenciapagador.executeQuery();

        while (resultado.next()) {
            p1.setCodigo_pagador(resultado.getInt("CODIGO_PAGADOR"));
            p1.setNombre(resultado.getString("NOMBRE"));
            p1.setApellidos(resultado.getString("APELLIDOS"));
            p1.setEmail(resultado.getString("EMAIL"));
            p1.setNif(resultado.getString("NIF"));
            p1.setPass(resultado.getString("PASS"));
            LocalDate fecha = LocalDate.parse(resultado.getString("FECHA_NAC"));
            p1.setFecha_nac(fecha);
            p1.setPoblacion(resultado.getString("POBLACION"));
            p1.setDireccion(resultado.getString("DIRECCION"));
        }

        return p1;
    }

    public ArrayList<Tarjeta> buscarTarjetas(Connection conex, Pagador pagador) throws SQLException {
        ArrayList<Tarjeta> aTarjetas = new ArrayList();
        Tarjeta t1 = new Tarjeta();
        int cod_pagador = pagador.getCodigo_pagador();

        PreparedStatement sentenciatarjeta = conex.prepareStatement("SELECT tarjeta.* FROM tarjeta WHERE tarjeta.COD_PAGADOR=? ORDER BY tarjeta.NUM_USOS");
        sentenciatarjeta.setInt(1, cod_pagador);

        ResultSet resultado = sentenciatarjeta.executeQuery();

        while (resultado.next()) {
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

    public static String getCadenaAlfanumAleatoria(int longitud) {
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

    public ArrayList buscarPasajero(Connection conex, Pasajero pasajero) throws SQLException {
        ArrayList h = new ArrayList();
        Boolean x = false;
        int cod = -1;

        PreparedStatement sentencia = conex.prepareStatement("SELECT pasajero.* FROM pasajero WHERE pasajero.NIF LIKE ?");
        sentencia.setString(1, pasajero.getNif());

        ResultSet resultado = sentencia.executeQuery();

        while (resultado.next()) {
            x = true;
            cod = resultado.getInt("CODIGO_PASAJERO");
        }

        h.add(x);
        h.add(cod);

        return h;
    }

    public Boolean buscarPagador2(Connection conex, Pagador pagador) throws SQLException {
        Boolean h = false;

        PreparedStatement sentencia = conex.prepareStatement("SELECT pagador.* FROM pagador WHERE pagador.NIF LIKE ?");
        sentencia.setString(1, pagador.getNif());

        ResultSet resultado = sentencia.executeQuery();

        while (resultado.next()) {
            h = true;
        }

        return h;
    }

    public Boolean buscarTarjeta2(Connection conex, Tarjeta tarjeta) throws SQLException {
        Boolean h = false;

        PreparedStatement sentencia = conex.prepareStatement("SELECT tarjeta.* FROM tarjeta WHERE tarjeta.NUM_TARJETA LIKE ?");
        sentencia.setString(1, tarjeta.getNum_tarjeta());

        ResultSet resultado = sentencia.executeQuery();

        while (resultado.next()) {
            h = true;
        }

        return h;
    }

    public ArrayList buscarBebe(Connection conex, Bebe bebe) throws SQLException {
        ArrayList h = new ArrayList();
        Boolean x = false;
        int cod = -1;

        PreparedStatement sentencia = conex.prepareStatement("SELECT bebe.* FROM bebe WHERE bebe.NIF LIKE ?");
        sentencia.setString(1, bebe.getNif());

        ResultSet resultado = sentencia.executeQuery();

        while (resultado.next()) {
            x = true;
            cod = resultado.getInt("CODIGO_BEBE");
        }

        h.add(x);
        h.add(cod);

        return h;
    }

    public Boolean insertarReserva(Connection conex, Reserva reserva) throws SQLException {
        Boolean h = false;

        try {
            conex.setAutoCommit(false);

            //Pasajeros Adultos
            for (int i = 0; i < reserva.getaPasajerosAdultos().size(); i++) {
                ArrayList aX = buscarPasajero(conex, reserva.getaPasajerosAdultos().get(i));
                Boolean x = (Boolean) aX.get(0);
                PreparedStatement sentenciapasajero;
                if (x) {
                    reserva.getaPasajerosAdultos().get(i).setCodigo_pasajero((int) aX.get(1));
                    String fecha = reserva.getaPasajerosAdultos().get(i).getFecha_caducidad().toString();
                    sentenciapasajero = conex.prepareStatement("UPDATE pasajero SET pasajero.CADUCIDAD_NIF=? WHERE pasajero.NIF LIKE ?");
                    sentenciapasajero.setString(1, fecha);
                    sentenciapasajero.setString(2, reserva.getaPasajerosAdultos().get(i).getNif());
                } else {
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
                if (!x) {
                    ResultSet generatedIdpasajero = sentenciapasajero.getGeneratedKeys();
                    if (generatedIdpasajero.next()) {
                        reserva.getaPasajerosAdultos().get(i).setCodigo_pasajero(generatedIdpasajero.getInt(1));
                    }
                }
            }

            //Pasajeros Niños
            if (!reserva.getaPasajerosNinos().isEmpty()) {
                for (int i = 0; i < reserva.getaPasajerosNinos().size(); i++) {
                    ArrayList aX = buscarPasajero(conex, reserva.getaPasajerosNinos().get(i));
                    Boolean x = (Boolean) aX.get(0);
                    PreparedStatement sentenciapasajero;
                    if (x) {
                        reserva.getaPasajerosNinos().get(i).setCodigo_pasajero((int) aX.get(1));
                        String fecha = reserva.getaPasajerosNinos().get(i).getFecha_caducidad().toString();
                        sentenciapasajero = conex.prepareStatement("UPDATE pasajero SET pasajero.CADUCIDAD_NIF=? WHERE pasajero.NIF LIKE ?");
                        sentenciapasajero.setString(1, fecha);
                        sentenciapasajero.setString(2, reserva.getaPasajerosNinos().get(i).getNif());
                    } else {
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
                    if (!x) {
                        ResultSet generatedIdpasajeronino = sentenciapasajero.getGeneratedKeys();
                        if (generatedIdpasajeronino.next()) {
                            reserva.getaPasajerosNinos().get(i).setCodigo_pasajero(generatedIdpasajeronino.getInt(1));
                        }
                    }
                }
            }

            //Bebes
            if (!reserva.getaPasajerosBebes().isEmpty()) {
                for (int i = 0; i < reserva.getaPasajerosBebes().size(); i++) {
                    ArrayList aX = buscarBebe(conex, reserva.getaPasajerosBebes().get(i));
                    Boolean x = (Boolean) aX.get(0);
                    PreparedStatement sentenciapasajero;
                    if (x) {
                        reserva.getaPasajerosBebes().get(i).setCod_bebe((int) aX.get(1));
                        String nombre = reserva.getaPasajerosBebes().get(i).getNombre();
                        sentenciapasajero = conex.prepareStatement("UPDATE bebe SET bebe.NOMBRE=? WHERE bebe.NIF LIKE ?");
                        sentenciapasajero.setString(1, nombre);
                        sentenciapasajero.setString(2, reserva.getaPasajerosBebes().get(i).getNif());
                    } else {
                        sentenciapasajero = conex.prepareStatement("INSERT INTO bebe (NOMBRE, APELLIDOS, NIF, FECHA_NAC) VALUES(?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
                        sentenciapasajero.setString(1, reserva.getaPasajerosBebes().get(i).getNombre());
                        sentenciapasajero.setString(2, reserva.getaPasajerosBebes().get(i).getApellidos());
                        sentenciapasajero.setString(3, reserva.getaPasajerosBebes().get(i).getNif());
                        sentenciapasajero.setString(4, reserva.getaPasajerosBebes().get(i).getFecha_nac().toString());
                    }
                    sentenciapasajero.executeUpdate();
                    if (!x) {
                        ResultSet generatedIdpasajerobebe = sentenciapasajero.getGeneratedKeys();
                        if (generatedIdpasajerobebe.next()) {
                            reserva.getaPasajerosBebes().get(i).setCod_bebe(generatedIdpasajerobebe.getInt(1));
                        }
                    }
                }
            }

            //Pagador
            PreparedStatement sentenciapagador;
            Boolean y = buscarPagador2(conex, reserva.getPagador());
            if (y) {
                sentenciapagador = conex.prepareStatement("UPDATE pagador SET pagador.POBLACION=?, pagador.DIRECCION=? WHERE pagador.NIF LIKE ?");
                sentenciapagador.setString(1, reserva.getPagador().getPoblacion());
                sentenciapagador.setString(2, reserva.getPagador().getDireccion());
                sentenciapagador.setString(3, reserva.getPagador().getNif());
            } else {
                sentenciapagador = conex.prepareStatement("INSERT INTO pagador (NIF, EMAIL, PASS, NOMBRE, APELLIDOS, FECHA_NAC, POBLACION, DIRECCION) VALUES (?, ?, ?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
                sentenciapagador.setString(1, reserva.getPagador().getNif());
                sentenciapagador.setString(2, reserva.getPagador().getEmail());
                sentenciapagador.setString(3, reserva.getPagador().getPass());
                sentenciapagador.setString(4, reserva.getPagador().getNombre());
                sentenciapagador.setString(5, reserva.getPagador().getApellidos());
                sentenciapagador.setString(6, reserva.getPagador().getFecha_nac().toString());
                sentenciapagador.setString(7, reserva.getPagador().getPoblacion());
                sentenciapagador.setString(8, reserva.getPagador().getDireccion());
            }
            sentenciapagador.executeUpdate();
            if (!y) {
                ResultSet generatedIdpagador = sentenciapagador.getGeneratedKeys();
                if (generatedIdpagador.next()) {
                    reserva.getPagador().setCodigo_pagador(generatedIdpagador.getInt(1));
                }
            }

            //Tarjeta
            PreparedStatement sentenciatarjeta;
            Boolean z = buscarTarjeta2(conex, reserva.getTarjeta());
            if (z) {
                sentenciatarjeta = conex.prepareStatement("UPDATE tarjeta SET tarjeta.CADUCIDAD=?, tarjeta.NUM_USOS=tarjeta.NUM_USOS+1 WHERE tarjeta.NUM_TARJETA LIKE ?");
                sentenciatarjeta.setString(1, reserva.getTarjeta().getCaducidad().toString());
                sentenciatarjeta.setString(2, reserva.getTarjeta().getNum_tarjeta());
            } else {
                sentenciatarjeta = conex.prepareStatement("INSERT INTO tarjeta (NUM_TARJETA, COD_PAGADOR, CODSEGURIDAD, CADUCIDAD, PIN, NUM_USOS) VALUES(?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
                sentenciatarjeta.setString(1, reserva.getTarjeta().getNum_tarjeta());
                sentenciatarjeta.setInt(2, reserva.getPagador().getCodigo_pagador());
                sentenciatarjeta.setString(3, reserva.getTarjeta().getCod_seguridad());
                sentenciatarjeta.setString(4, reserva.getTarjeta().getCaducidad().toString());
                sentenciatarjeta.setInt(5, reserva.getTarjeta().getPin());
                sentenciatarjeta.setInt(6, reserva.getTarjeta().getNum_usos() + 1);
            }
            sentenciatarjeta.executeUpdate();
            if (!z) {
                ResultSet generatedIdtarjeta = sentenciatarjeta.getGeneratedKeys();
                if (generatedIdtarjeta.next()) {
                    reserva.getTarjeta().setCodigo_tarjeta(generatedIdtarjeta.getInt(1));
                }
            }

            //Reserva
            PreparedStatement sentenciareserva;
            if (reserva.getVuelo_vuelta() != null) {
                sentenciareserva = conex.prepareStatement("INSERT INTO reserva (COD_VUELO_IDA, COD_VUELO_VUELTA, COD_RESERVA, TARJETA, NUMERO_VIAJEROS, PRECIO_TOTAL, FACTURADA_VUELTA) VALUES(?, ?, ?, ?, ?, ?, 'N')");
                sentenciareserva.setInt(1, reserva.getVuelo_ida().getCodigo_vuelo());
                sentenciareserva.setInt(2, reserva.getVuelo_vuelta().getCodigo_vuelo());
                sentenciareserva.setString(3, reserva.getCod_reserva());
                sentenciareserva.setInt(4, reserva.getTarjeta().getCodigo_tarjeta());
                sentenciareserva.setInt(5, reserva.getNum_viajeros());
                sentenciareserva.setInt(6, reserva.getPrecio_total());
            } else {
                sentenciareserva = conex.prepareStatement("INSERT INTO reserva (COD_VUELO_IDA, COD_RESERVA, TARJETA, NUMERO_VIAJEROS, PRECIO_TOTAL) VALUES(?, ?, ?, ?, ?)");
                sentenciareserva.setInt(1, reserva.getVuelo_ida().getCodigo_vuelo());
                sentenciareserva.setString(2, reserva.getCod_reserva());
                sentenciareserva.setInt(3, reserva.getTarjeta().getCodigo_tarjeta());
                sentenciareserva.setInt(4, reserva.getNum_viajeros());
                sentenciareserva.setInt(5, reserva.getPrecio_total());
            }
            sentenciareserva.executeUpdate();

            //Actualizar plazas libres
            PreparedStatement sentenciaplazaslibres;
            sentenciaplazaslibres = conex.prepareStatement("UPDATE vuelo SET vuelo.ASIENTOS_LIBRES=vuelo.ASIENTOS_LIBRES-? WHERE vuelo.CODIGO_VUELO=?");
            sentenciaplazaslibres.setInt(1, reserva.getNum_viajeros());
            sentenciaplazaslibres.setInt(2, reserva.getVuelo_ida().getCodigo_vuelo());
            sentenciaplazaslibres.executeUpdate();

            //Tutor
            if (!reserva.getaPasajerosBebes().isEmpty()) {
                //Ida
                PreparedStatement sentenciatutor;
                for (int bb = 0; bb < reserva.getaPasajerosBebes().size(); bb++) {
                    String cod_reserva = reserva.getCod_reserva();
                    int cod_bebe = reserva.getaPasajerosBebes().get(bb).getCod_bebe();
                    int cod_tutor = reserva.getaPasajerosBebes().get(bb).getTutor_ida().getCodigo_pasajero();

                    sentenciatutor = conex.prepareStatement("INSERT INTO tutor (COD_PASAJERO, COD_BEBE, COD_RESERVA, TIPO) VALUES(?, ?, ?, 'IDA')");
                    sentenciatutor.setInt(1, cod_tutor);
                    sentenciatutor.setInt(2, cod_bebe);
                    sentenciatutor.setString(3, cod_reserva);

                    sentenciatutor.executeUpdate();
                }

                //Vuelta
                if (reserva.getVuelo_vuelta() != null) {
                    PreparedStatement sentenciatutorvuelta;
                    for (int bb = 0; bb < reserva.getaPasajerosBebes().size(); bb++) {
                        String cod_reserva = reserva.getCod_reserva();
                        int cod_bebe = reserva.getaPasajerosBebes().get(bb).getCod_bebe();
                        int cod_tutor = reserva.getaPasajerosBebes().get(bb).getTutor_vuelta().getCodigo_pasajero();

                        sentenciatutorvuelta = conex.prepareStatement("INSERT INTO tutor (COD_PASAJERO, COD_BEBE, COD_RESERVA, TIPO) VALUES(?, ?, ?, 'VUELTA')");
                        sentenciatutorvuelta.setInt(1, cod_tutor);
                        sentenciatutorvuelta.setInt(2, cod_bebe);
                        sentenciatutorvuelta.setString(3, cod_reserva);

                        sentenciatutorvuelta.executeUpdate();
                    }
                }
            }

            //Ocupacion y servicios Adultos
            for (int j = 0; j < reserva.getaPasajerosAdultos().size(); j++) {
                //OcupacionIda
                PreparedStatement sentenciaocuida;
                if (reserva.getaPasajerosAdultos().get(j).getAsiento_ida() == 0) {
                    sentenciaocuida = conex.prepareStatement("INSERT INTO ocupacion (RESERVA, TIPO, PASAJERO) VALUES(?, 'IDA', ?)", Statement.RETURN_GENERATED_KEYS);
                    sentenciaocuida.setString(1, reserva.getCod_reserva());
                    sentenciaocuida.setInt(2, reserva.getaPasajerosAdultos().get(j).getCodigo_pasajero());
                } else {
                    sentenciaocuida = conex.prepareStatement("INSERT INTO ocupacion (RESERVA, TIPO, PASAJERO, ASIENTO) VALUES(?, 'IDA', ?, ?)", Statement.RETURN_GENERATED_KEYS);
                    sentenciaocuida.setString(1, reserva.getCod_reserva());
                    sentenciaocuida.setInt(2, reserva.getaPasajerosAdultos().get(j).getCodigo_pasajero());
                    sentenciaocuida.setInt(3, reserva.getaPasajerosAdultos().get(j).getAsiento_ida());
                }

                sentenciaocuida.executeUpdate();
                int idOcuida = 0;
                ResultSet generatedIdocuida = sentenciaocuida.getGeneratedKeys();
                if (generatedIdocuida.next()) {
                    idOcuida = generatedIdocuida.getInt(1);
                }
                //Servicios Ocupacion Ida
                PreparedStatement sentenciaservida;
                if (!reserva.getaPasajerosAdultos().get(j).getaServiciosIda().isEmpty()) {
                    for (int o = 0; o < reserva.getaPasajerosAdultos().get(j).getaServiciosIda().size(); o++) {
                        sentenciaservida = conex.prepareStatement("INSERT INTO reserva_servicio (COD_OCUPACION, COD_SERVICIO) VALUES(?, ?)");
                        sentenciaservida.setInt(1, idOcuida);
                        sentenciaservida.setInt(2, reserva.getaPasajerosAdultos().get(j).getaServiciosIda().get(o).getCodigo_servicio());
                        sentenciaservida.executeUpdate();
                    }
                }
                //OcupacionVuelta
                PreparedStatement sentenciaocuvuelta;
                if (!reserva.getaPasajerosAdultos().get(j).getaServiciosVuelta().isEmpty()) {
                    if (reserva.getaPasajerosAdultos().get(j).getAsiento_vuelta() == 0) {
                        sentenciaocuvuelta = conex.prepareStatement("INSERT INTO ocupacion (RESERVA, TIPO, PASAJERO) VALUES(?, 'VUELTA', ?)", Statement.RETURN_GENERATED_KEYS);
                        sentenciaocuvuelta.setString(1, reserva.getCod_reserva());
                        sentenciaocuvuelta.setInt(2, reserva.getaPasajerosAdultos().get(j).getCodigo_pasajero());
                    } else {
                        sentenciaocuvuelta = conex.prepareStatement("INSERT INTO ocupacion (RESERVA, TIPO, PASAJERO, ASIENTO) VALUES(?, 'VUELTA', ?, ?)", Statement.RETURN_GENERATED_KEYS);
                        sentenciaocuvuelta.setString(1, reserva.getCod_reserva());
                        sentenciaocuvuelta.setInt(2, reserva.getaPasajerosAdultos().get(j).getCodigo_pasajero());
                        sentenciaocuvuelta.setInt(3, reserva.getaPasajerosAdultos().get(j).getAsiento_vuelta());
                    }

                    sentenciaocuvuelta.executeUpdate();
                    int idOcuvuelta = 0;
                    ResultSet generatedIdocuvuelta = sentenciaocuvuelta.getGeneratedKeys();
                    if (generatedIdocuvuelta.next()) {
                        idOcuvuelta = generatedIdocuvuelta.getInt(1);
                    }
                    //Servicios Ocupacion Vuelta
                    PreparedStatement sentenciaservvuelta;
                    if (!reserva.getaPasajerosAdultos().get(j).getaServiciosVuelta().isEmpty()) {
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
            if (!reserva.getaPasajerosNinos().isEmpty()) {
                for (int j = 0; j < reserva.getaPasajerosNinos().size(); j++) {
                    //OcupacionIda
                    PreparedStatement sentenciaocuida;
                    if (reserva.getaPasajerosNinos().get(j).getAsiento_ida() == 0) {
                        sentenciaocuida = conex.prepareStatement("INSERT INTO ocupacion (RESERVA, TIPO, PASAJERO) VALUES(?, 'IDA', ?)", Statement.RETURN_GENERATED_KEYS);
                        sentenciaocuida.setString(1, reserva.getCod_reserva());
                        sentenciaocuida.setInt(2, reserva.getaPasajerosNinos().get(j).getCodigo_pasajero());
                    } else {
                        sentenciaocuida = conex.prepareStatement("INSERT INTO ocupacion (RESERVA, TIPO, PASAJERO, ASIENTO) VALUES(?, 'IDA', ?, ?)", Statement.RETURN_GENERATED_KEYS);
                        sentenciaocuida.setString(1, reserva.getCod_reserva());
                        sentenciaocuida.setInt(2, reserva.getaPasajerosNinos().get(j).getCodigo_pasajero());
                        sentenciaocuida.setInt(3, reserva.getaPasajerosNinos().get(j).getAsiento_ida());
                    }

                    sentenciaocuida.executeUpdate();
                    int idOcuida = 0;
                    ResultSet generatedIdocuida = sentenciaocuida.getGeneratedKeys();
                    if (generatedIdocuida.next()) {
                        idOcuida = generatedIdocuida.getInt(1);
                    }
                    //Servicios Ocupacion Ida
                    PreparedStatement sentenciaservida;
                    if (!reserva.getaPasajerosNinos().get(j).getaServiciosIda().isEmpty()) {
                        for (int o = 0; o < reserva.getaPasajerosNinos().get(j).getaServiciosIda().size(); o++) {
                            sentenciaservida = conex.prepareStatement("INSERT INTO reserva_servicio (COD_OCUPACION, COD_SERVICIO) VALUES(?, ?)");
                            sentenciaservida.setInt(1, idOcuida);
                            sentenciaservida.setInt(2, reserva.getaPasajerosNinos().get(j).getaServiciosIda().get(o).getCodigo_servicio());
                            sentenciaservida.executeUpdate();
                        }
                    }
                    //OcupacionVuelta
                    PreparedStatement sentenciaocuvuelta;
                    if (!reserva.getaPasajerosNinos().get(j).getaServiciosVuelta().isEmpty()) {
                        if (reserva.getaPasajerosNinos().get(j).getAsiento_vuelta() == 0) {
                            sentenciaocuvuelta = conex.prepareStatement("INSERT INTO ocupacion (RESERVA, TIPO, PASAJERO) VALUES(?, 'VUELTA', ?)", Statement.RETURN_GENERATED_KEYS);
                            sentenciaocuvuelta.setString(1, reserva.getCod_reserva());
                            sentenciaocuvuelta.setInt(2, reserva.getaPasajerosNinos().get(j).getCodigo_pasajero());
                        } else {
                            sentenciaocuvuelta = conex.prepareStatement("INSERT INTO ocupacion (RESERVA, TIPO, PASAJERO, ASIENTO) VALUES(?, 'VUELTA', ?, ?)", Statement.RETURN_GENERATED_KEYS);
                            sentenciaocuvuelta.setString(1, reserva.getCod_reserva());
                            sentenciaocuvuelta.setInt(2, reserva.getaPasajerosNinos().get(j).getCodigo_pasajero());
                            sentenciaocuvuelta.setInt(3, reserva.getaPasajerosNinos().get(j).getAsiento_vuelta());
                        }

                        sentenciaocuvuelta.executeUpdate();
                        int idOcuvuelta = 0;
                        ResultSet generatedIdocuvuelta = sentenciaocuvuelta.getGeneratedKeys();
                        if (generatedIdocuvuelta.next()) {
                            idOcuvuelta = generatedIdocuvuelta.getInt(1);
                        }
                        //Servicios Ocupacion Vuelta
                        PreparedStatement sentenciaservvuelta;
                        if (!reserva.getaPasajerosNinos().get(j).getaServiciosVuelta().isEmpty()) {
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
        } catch (SQLException ex) {
            conex.rollback();
            h = false;
        }

        return h;
    }

    public Boolean buscarReservaFacturar(Connection conex, String correo, String pass) throws SQLException {
        Boolean h = false;

        PreparedStatement sentencia = conex.prepareStatement("SELECT reserva.`*` FROM reserva, pagador, tarjeta WHERE pagador.EMAIL LIKE ? AND tarjeta.COD_PAGADOR=pagador.CODIGO_PAGADOR AND tarjeta.CODIGO_TARJETA=reserva.TARJETA AND reserva.COD_RESERVA LIKE ?");
        sentencia.setString(1, correo);
        sentencia.setString(2, pass);

        ResultSet resultado = sentencia.executeQuery();

        while (resultado.next()) {
            h = true;
        }

        return h;
    }

    public Reserva montarReservaFacturar(Connection conex, String cod_reserva) throws SQLException {
        Reserva r1 = new Reserva();

        //Sacar datos para reserva.
        int cod_vuelo_ida = 0;
        int cod_vuelo_vuelta = 0;
        
        
        PreparedStatement sentenciareserva = conex.prepareStatement("SELECT * FROM reserva WHERE COD_RESERVA LIKE ?");
        sentenciareserva.setString(1, cod_reserva);

        ResultSet resultado1 = sentenciareserva.executeQuery();

        while (resultado1.next()) {
            cod_vuelo_ida = resultado1.getInt("COD_VUELO_IDA");
            cod_vuelo_vuelta = resultado1.getInt("COD_VUELO_VUELTA");
            r1.setCodigo_reserva(resultado1.getInt("CODIGO_RESERVA"));
            r1.setCod_reserva(resultado1.getString("COD_RESERVA"));
            r1.setFacturada_ida(resultado1.getString("FACTURADA_IDA"));
            r1.setFacturada_vuelta(resultado1.getString("FACTURADA_VUELTA"));
            r1.setFecha(resultado1.getTimestamp("FECHA").toLocalDateTime());
            r1.setNum_viajeros(resultado1.getInt("NUMERO_VIAJEROS"));
            r1.setPrecio_total(resultado1.getInt("PRECIO_TOTAL"));
        }
        
        //Vuelos
        Aeropuerto a1 = new Aeropuerto();
        Aeropuerto a2 = new Aeropuerto();
        int conexion_ida = 0;
        String aeropuerto_or_ida = "";
        String aeropuerto_de_ida = "";
        Vuelo vuelo_ida = new Vuelo();
        int conexion_vuelta = 0;
        String aeropuerto_or_vuelta = "";
        String aeropuerto_de_vuelta = "";
        Vuelo vuelo_vuelta = new Vuelo();
            //Vuelo Ida
            PreparedStatement sentenciavueloida = conex.prepareStatement("SELECT * FROM vuelo WHERE CODIGO_VUELO = ?");
            sentenciavueloida.setInt(1, cod_vuelo_ida);
            
            ResultSet resultado2 = sentenciavueloida.executeQuery();
            
            while(resultado1.next()) {
                vuelo_ida.setCodigo_vuelo(resultado2.getInt("CODIGO_VUELO"));
                vuelo_ida.setAsientos_libres(resultado2.getInt("ASIENTOS_LIBRES"));
                vuelo_ida.setConexion(resultado2.getInt("CONEXION"));
                vuelo_ida.setFecha(LocalDate.parse(resultado2.getString("FECHA")));
                vuelo_ida.setHora_llegada(LocalTime.parse(resultado2.getString("HORA_LLEGADA")));
                vuelo_ida.setHora_salida(LocalTime.parse(resultado2.getString("HORA_SALIDA")));
                vuelo_ida.setPrecio(resultado2.getInt("PRECIO"));
                vuelo_ida.setNum_vuelo(resultado2.getString("NUMERO"));
                conexion_ida = resultado2.getInt("CONEXION");
            }
                //Iatas conexion ida
                PreparedStatement sentenciaconexida = conex.prepareStatement("SELECT * FROM conexion WHERE CODIGO_CONEXION = ?");
                sentenciaconexida.setInt(1, conexion_ida);
                
                ResultSet resultado3 = sentenciaconexida.executeQuery();
                
                while(resultado3.next()){
                    aeropuerto_or_ida = resultado3.getString("ORIGEN");
                    aeropuerto_de_vuelta = resultado3.getString("DESTINO");
                }
                    //Aeropuertos
                    PreparedStatement sentenciaaerorida = conex.prepareStatement("SELECT * FROM aeropuerto WHERE CODIGO_IATA LIKE ?");
                    sentenciaaerorida.setString(1, aeropuerto_or_ida);
                
                    ResultSet resultado4 = sentenciaaerorida.executeQuery();
                
                    while(resultado4.next()){
                        a1.setCod_aer(resultado4.getInt("CODIGO_AEROPUERTO"));
                        a1.setIata(resultado4.getString("CODIGO_IATA"));
                        a1.setCiudad(resultado4.getString("CIUDAD"));
                        a1.setPais(resultado4.getString("PAIS"));
                    }
                    PreparedStatement sentenciaaerorvuelta = conex.prepareStatement("SELECT * FROM aeropuerto WHERE CODIGO_IATA LIKE ?");
                    sentenciaaerorvuelta.setString(1, aeropuerto_or_vuelta);
                
                    ResultSet resultado5 = sentenciaaerorvuelta.executeQuery();
                
                    while(resultado5.next()){
                        a2.setCod_aer(resultado5.getInt("CODIGO_AEROPUERTO"));
                        a2.setIata(resultado5.getString("CODIGO_IATA"));
                        a2.setCiudad(resultado5.getString("CIUDAD"));
                        a2.setPais(resultado5.getString("PAIS"));
                    }
            vuelo_ida.setOrigen(a1);
            vuelo_ida.setDestino(a2);
            r1.setVuelo_ida(vuelo_ida);
            //Vuelo vuelta
            if(cod_vuelo_vuelta != 0){
                PreparedStatement sentenciavuelovuelta = conex.prepareStatement("SELECT * FROM vuelo WHERE CODIGO_VUELO = ?");
                sentenciavuelovuelta.setInt(1, cod_vuelo_vuelta);

                ResultSet resultado6 = sentenciavuelovuelta.executeQuery();

                while(resultado6.next()) {
                    vuelo_vuelta.setCodigo_vuelo(resultado6.getInt("CODIGO_VUELO"));
                    vuelo_vuelta.setAsientos_libres(resultado6.getInt("ASIENTOS_LIBRES"));
                    vuelo_vuelta.setConexion(resultado6.getInt("CONEXION"));
                    vuelo_vuelta.setFecha(LocalDate.parse(resultado6.getString("FECHA")));
                    vuelo_vuelta.setHora_llegada(LocalTime.parse(resultado6.getString("HORA_LLEGADA")));
                    vuelo_vuelta.setHora_salida(LocalTime.parse(resultado6.getString("HORA_SALIDA")));
                    vuelo_vuelta.setPrecio(resultado6.getInt("PRECIO"));
                    vuelo_vuelta.setNum_vuelo(resultado6.getString("NUMERO"));
                }
            vuelo_vuelta.setOrigen(a2);
            vuelo_vuelta.setDestino(a1);
            r1.setVuelo_vuelta(vuelo_vuelta);
            }
        
        //Pasajeros Adultos
        ArrayList<Pasajero> aAdultos = new ArrayList();
        Pasajero p1 =new Pasajero();
        
        PreparedStatement sentenciapasajerosa = conex.prepareStatement("SELECT p.* FROM reserva r, ocupacion o, pasajero p WHERE o.RESERVA LIKE ? AND o.PASAJERO=p.CODIGO_PASAJERO AND p.TIPO LIKE 'adulto' GROUP BY p.CODIGO_PASAJERO");
        sentenciapasajerosa.setString(1, cod_reserva);
        
        ResultSet resultado7 = sentenciapasajerosa.executeQuery();
        
        while(resultado7.next()){
            p1.setApellidos(resultado7.getString("APELLIDOS"));
            p1.setCodigo_pasajero(resultado7.getInt("CODIGO_PASAJERO"));
            p1.setFecha_caducidad(LocalDate.parse(resultado7.getString("CADUCIDAD_NIF")));
            p1.setFecha_nac(LocalDate.parse(resultado7.getString("FECHA_NAC")));
            p1.setNif(resultado7.getString("NIF"));
            p1.setNombre(resultado7.getString("NOMBRE"));
            p1.setTipo(resultado7.getString("TIPO"));
            p1.setTratamiento(resultado7.getString("TRATAMIENTO"));
            aAdultos.add(p1);
        }
        
        //Pasajeros Niños
        ArrayList<Pasajero> aNinos = new ArrayList();
        
        PreparedStatement sentenciapasajerosn = conex.prepareStatement("SELECT p.* FROM reserva r, ocupacion o, pasajero p WHERE o.RESERVA LIKE ? AND o.PASAJERO=p.CODIGO_PASAJERO AND p.TIPO LIKE 'niño' GROUP BY p.CODIGO_PASAJERO");
        sentenciapasajerosn.setString(1, cod_reserva);
        
        ResultSet resultado8 = sentenciapasajerosn.executeQuery();
        
        while(resultado8.next()){
            p1.setApellidos(resultado8.getString("APELLIDOS"));
            p1.setCodigo_pasajero(resultado8.getInt("CODIGO_PASAJERO"));
            p1.setFecha_caducidad(LocalDate.parse(resultado8.getString("CADUCIDAD_NIF")));
            p1.setFecha_nac(LocalDate.parse(resultado8.getString("FECHA_NAC")));
            p1.setNif(resultado8.getString("NIF"));
            p1.setNombre(resultado8.getString("NOMBRE"));
            p1.setTipo(resultado8.getString("TIPO"));
            p1.setTratamiento(resultado8.getString("TRATAMIENTO"));
            aNinos.add(p1);
        }
        
        //Pasajeros Bebes
        ArrayList<Bebe> aBebes = new ArrayList();
        Bebe b1 = new Bebe();
        
        PreparedStatement sentenciapasajerosb = conex.prepareStatement("SELECT b.* FROM bebe b, tutor t WHERE b.CODIGO_BEBE=t.COD_BEBE AND t.COD_RESERVA LIKE ? GROUP BY b.CODIGO_BEBE");
        sentenciapasajerosb.setString(1, cod_reserva);
        
        ResultSet resultado9 = sentenciapasajerosb.executeQuery();
        
        while(resultado9.next()){
            b1.setApellidos(resultado9.getString("APELLIDOS"));
            b1.setCod_bebe(resultado9.getInt("CODIGO_BEBE"));
            b1.setFecha_nac(LocalDate.parse(resultado9.getString("FECHA_NAC")));
            b1.setNif(resultado9.getString("NIF"));
            b1.setNombre(resultado9.getString("NOMBRE"));
            aBebes.add(b1);
        }
        
        
        

        return r1;
    }
}
