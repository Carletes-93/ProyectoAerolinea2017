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

        String sentencia = "SELECT * FROM aeropuerto WHERE CIUDAD NOT LIKE '" + IATA_origen + "' ORDER BY CIUDAD";

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

    public static ArrayList<Servicio> sacarServicios(Connection conex) throws SQLException {
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
        ArrayList<Boolean> aBoolean = new ArrayList<Boolean>(Arrays.asList(new Boolean[12]));
        Collections.fill(aBoolean, Boolean.TRUE);

        ArrayList<Integer> aNum = new ArrayList();

        PreparedStatement sentenciaasientos1 = conex.prepareStatement("SELECT ocupacion.ASIENTO FROM ocupacion INNER JOIN reserva ON ocupacion.RESERVA=reserva.COD_RESERVA WHERE reserva.COD_VUELO_IDA=? AND ocupacion.TIPO='IDA'");
        sentenciaasientos1.setInt(1, cod_vuelo);

        ResultSet resultado = sentenciaasientos1.executeQuery();

        while (resultado.next()) {
            if (resultado.getInt("ASIENTO") != 0) {
                aNum.add(resultado.getInt("ASIENTO"));
            }

        }

        PreparedStatement sentenciaasientos2 = conex.prepareStatement("SELECT ocupacion.ASIENTO FROM ocupacion INNER JOIN reserva ON ocupacion.RESERVA=reserva.COD_RESERVA WHERE reserva.COD_VUELO_VUELTA=? AND ocupacion.TIPO='VUELTA'");
        sentenciaasientos2.setInt(1, cod_vuelo);

        ResultSet resultado2 = sentenciaasientos2.executeQuery();

        while (resultado2.next()) {
            if (resultado2.getInt("ASIENTO") != 0) {
                aNum.add(resultado2.getInt("ASIENTO"));
            }
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

    public static Boolean buscarServicioBackup(Connection conex, Servicio servicio) throws SQLException {
        Boolean h = false;

        PreparedStatement sentencia = conex.prepareStatement("SELECT servicio_backup.* FROM servicio_backup WHERE servicio_backup.NOMBRE LIKE ? AND servicio_backup.PRECIO = ?");
        sentencia.setString(1, servicio.getNombre());
        sentencia.setInt(2, servicio.getPrecio());

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
                PreparedStatement sentenciapasajero;

                sentenciapasajero = conex.prepareStatement("INSERT INTO pasajero (TRATAMIENTO, NOMBRE, APELLIDOS, NIF, CADUCIDAD_NIF, FECHA_NAC, TIPO) VALUES (?, ?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
                sentenciapasajero.setString(1, reserva.getaPasajerosAdultos().get(i).getTratamiento());
                sentenciapasajero.setString(2, reserva.getaPasajerosAdultos().get(i).getNombre());
                sentenciapasajero.setString(3, reserva.getaPasajerosAdultos().get(i).getApellidos());
                sentenciapasajero.setString(4, reserva.getaPasajerosAdultos().get(i).getNif());
                sentenciapasajero.setString(5, reserva.getaPasajerosAdultos().get(i).getFecha_caducidad().toString());
                sentenciapasajero.setString(6, reserva.getaPasajerosAdultos().get(i).getFecha_nac().toString());
                sentenciapasajero.setString(7, reserva.getaPasajerosAdultos().get(i).getTipo());

                sentenciapasajero.executeUpdate();

                ResultSet generatedIdpasajero = sentenciapasajero.getGeneratedKeys();
                if (generatedIdpasajero.next()) {
                    reserva.getaPasajerosAdultos().get(i).setCodigo_pasajero(generatedIdpasajero.getInt(1));
                }
            }

            //Pasajeros Niños
            if (!reserva.getaPasajerosNinos().isEmpty()) {
                for (int i = 0; i < reserva.getaPasajerosNinos().size(); i++) {
                    PreparedStatement sentenciapasajero;

                    sentenciapasajero = conex.prepareStatement("INSERT INTO pasajero (TRATAMIENTO, NOMBRE, APELLIDOS, NIF, CADUCIDAD_NIF, FECHA_NAC, TIPO) VALUES (?, ?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
                    sentenciapasajero.setString(1, reserva.getaPasajerosNinos().get(i).getTratamiento());
                    sentenciapasajero.setString(2, reserva.getaPasajerosNinos().get(i).getNombre());
                    sentenciapasajero.setString(3, reserva.getaPasajerosNinos().get(i).getApellidos());
                    sentenciapasajero.setString(4, reserva.getaPasajerosNinos().get(i).getNif());
                    sentenciapasajero.setString(5, reserva.getaPasajerosNinos().get(i).getFecha_caducidad().toString());
                    sentenciapasajero.setString(6, reserva.getaPasajerosNinos().get(i).getFecha_nac().toString());
                    sentenciapasajero.setString(7, reserva.getaPasajerosNinos().get(i).getTipo());

                    sentenciapasajero.executeUpdate();

                    ResultSet generatedIdpasajeronino = sentenciapasajero.getGeneratedKeys();
                    if (generatedIdpasajeronino.next()) {
                        reserva.getaPasajerosNinos().get(i).setCodigo_pasajero(generatedIdpasajeronino.getInt(1));
                    }
                }
            }

            //Bebes
            if (!reserva.getaPasajerosBebes().isEmpty()) {
                for (int i = 0; i < reserva.getaPasajerosBebes().size(); i++) {
                    PreparedStatement sentenciapasajero;

                    sentenciapasajero = conex.prepareStatement("INSERT INTO bebe (NOMBRE, APELLIDOS, NIF, FECHA_NAC) VALUES(?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
                    sentenciapasajero.setString(1, reserva.getaPasajerosBebes().get(i).getNombre());
                    sentenciapasajero.setString(2, reserva.getaPasajerosBebes().get(i).getApellidos());
                    sentenciapasajero.setString(3, reserva.getaPasajerosBebes().get(i).getNif());
                    sentenciapasajero.setString(4, reserva.getaPasajerosBebes().get(i).getFecha_nac().toString());

                    sentenciapasajero.executeUpdate();

                    ResultSet generatedIdpasajerobebe = sentenciapasajero.getGeneratedKeys();
                    if (generatedIdpasajerobebe.next()) {
                        reserva.getaPasajerosBebes().get(i).setCod_bebe(generatedIdpasajerobebe.getInt(1));
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
                sentenciatarjeta = conex.prepareStatement("INSERT INTO tarjeta (NUM_TARJETA, COD_PAGADOR, CODSEGURIDAD, CADUCIDAD, NUM_USOS) VALUES(?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
                sentenciatarjeta.setString(1, reserva.getTarjeta().getNum_tarjeta());
                sentenciatarjeta.setInt(2, reserva.getPagador().getCodigo_pagador());
                sentenciatarjeta.setString(3, reserva.getTarjeta().getCod_seguridad());
                sentenciatarjeta.setString(4, reserva.getTarjeta().getCaducidad().toString());
                sentenciatarjeta.setInt(5, reserva.getTarjeta().getNum_usos() + 1);
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
            if (reserva.getVuelo_vuelta() != null) {
                PreparedStatement sentenciaplazaslibresv;
                sentenciaplazaslibresv = conex.prepareStatement("UPDATE vuelo SET vuelo.ASIENTOS_LIBRES=vuelo.ASIENTOS_LIBRES-? WHERE vuelo.CODIGO_VUELO=?");
                sentenciaplazaslibresv.setInt(1, reserva.getNum_viajeros());
                sentenciaplazaslibresv.setInt(2, reserva.getVuelo_vuelta().getCodigo_vuelo());
                sentenciaplazaslibresv.executeUpdate();
            }

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

        while (resultado2.next()) {
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

        while (resultado3.next()) {
            aeropuerto_or_ida = resultado3.getString("ORIGEN");
            aeropuerto_or_vuelta = resultado3.getString("DESTINO");
        }
        //Aeropuertos
        PreparedStatement sentenciaaerorida = conex.prepareStatement("SELECT * FROM aeropuerto WHERE CODIGO_IATA LIKE ?");
        sentenciaaerorida.setString(1, aeropuerto_or_ida);

        ResultSet resultado4 = sentenciaaerorida.executeQuery();

        while (resultado4.next()) {
            a1.setCod_aer(resultado4.getInt("CODIGO_AEROPUERTO"));
            a1.setIata(resultado4.getString("CODIGO_IATA"));
            a1.setCiudad(resultado4.getString("CIUDAD"));
            a1.setPais(resultado4.getString("PAIS"));
        }
        PreparedStatement sentenciaaerorvuelta = conex.prepareStatement("SELECT * FROM aeropuerto WHERE CODIGO_IATA LIKE ?");
        sentenciaaerorvuelta.setString(1, aeropuerto_or_vuelta);

        ResultSet resultado5 = sentenciaaerorvuelta.executeQuery();

        while (resultado5.next()) {
            a2.setCod_aer(resultado5.getInt("CODIGO_AEROPUERTO"));
            a2.setIata(resultado5.getString("CODIGO_IATA"));
            a2.setCiudad(resultado5.getString("CIUDAD"));
            a2.setPais(resultado5.getString("PAIS"));
        }
        vuelo_ida.setOrigen(a1);
        vuelo_ida.setDestino(a2);
        r1.setVuelo_ida(vuelo_ida);
        //Vuelo vuelta
        if (cod_vuelo_vuelta != 0) {
            PreparedStatement sentenciavuelovuelta = conex.prepareStatement("SELECT * FROM vuelo WHERE CODIGO_VUELO = ?");
            sentenciavuelovuelta.setInt(1, cod_vuelo_vuelta);

            ResultSet resultado6 = sentenciavuelovuelta.executeQuery();

            while (resultado6.next()) {
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

        PreparedStatement sentenciapasajerosa = conex.prepareStatement("SELECT DISTINCT p.* FROM reserva r, ocupacion o, pasajero p WHERE o.RESERVA LIKE ? AND o.PASAJERO=p.CODIGO_PASAJERO AND p.TIPO LIKE 'adulto'");
        sentenciapasajerosa.setString(1, cod_reserva);

        ResultSet resultado7 = sentenciapasajerosa.executeQuery();

        while (resultado7.next()) {
            Pasajero p1 = new Pasajero();
            p1.setApellidos(resultado7.getString("APELLIDOS"));
            p1.setCodigo_pasajero(resultado7.getInt("CODIGO_PASAJERO"));
            p1.setFecha_caducidad(LocalDate.parse(resultado7.getString("CADUCIDAD_NIF")));
            p1.setFecha_nac(LocalDate.parse(resultado7.getString("FECHA_NAC")));
            p1.setNif(resultado7.getString("NIF"));
            p1.setNombre(resultado7.getString("NOMBRE"));
            p1.setTipo(resultado7.getString("TIPO"));
            p1.setTratamiento(resultado7.getString("TRATAMIENTO"));

            PreparedStatement sentenciacodocu = conex.prepareStatement("SELECT o.* FROM reserva r, ocupacion o, pasajero p WHERE o.RESERVA LIKE ? AND o.PASAJERO=? AND o.TIPO LIKE 'IDA' GROUP BY o.CODIGO_OCUPACION");
            sentenciacodocu.setString(1, cod_reserva);
            sentenciacodocu.setInt(2, p1.getCodigo_pasajero());
            ResultSet res = sentenciacodocu.executeQuery();
            while (res.next()) {
                p1.setCodocuida(res.getInt("CODIGO_OCUPACION"));
            }

            PreparedStatement sentenciacodocu2 = conex.prepareStatement("SELECT o.* FROM reserva r, ocupacion o, pasajero p WHERE o.RESERVA LIKE ? AND o.PASAJERO=? AND o.TIPO LIKE 'VUELTA' GROUP BY o.CODIGO_OCUPACION");
            sentenciacodocu2.setString(1, cod_reserva);
            sentenciacodocu2.setInt(2, p1.getCodigo_pasajero());
            ResultSet res2 = sentenciacodocu2.executeQuery();
            while (res2.next()) {
                p1.setCodocuvuelta(res2.getInt("CODIGO_OCUPACION"));
            }

            aAdultos.add(p1);
        }

        //Pasajeros Niños
        ArrayList<Pasajero> aNinos = new ArrayList();

        PreparedStatement sentenciapasajerosn = conex.prepareStatement("SELECT DISTINCT p.* FROM reserva r, ocupacion o, pasajero p WHERE o.RESERVA LIKE ? AND o.PASAJERO=p.CODIGO_PASAJERO AND p.TIPO LIKE 'niño'");
        sentenciapasajerosn.setString(1, cod_reserva);

        ResultSet resultado8 = sentenciapasajerosn.executeQuery();

        while (resultado8.next()) {
            Pasajero p1 = new Pasajero();
            p1.setApellidos(resultado8.getString("APELLIDOS"));
            p1.setCodigo_pasajero(resultado8.getInt("CODIGO_PASAJERO"));
            p1.setFecha_caducidad(LocalDate.parse(resultado8.getString("CADUCIDAD_NIF")));
            p1.setFecha_nac(LocalDate.parse(resultado8.getString("FECHA_NAC")));
            p1.setNif(resultado8.getString("NIF"));
            p1.setNombre(resultado8.getString("NOMBRE"));
            p1.setTipo(resultado8.getString("TIPO"));
            p1.setTratamiento(resultado8.getString("TRATAMIENTO"));

            PreparedStatement sentenciacodocu = conex.prepareStatement("SELECT o.* FROM reserva r, ocupacion o, pasajero p WHERE o.RESERVA LIKE ? AND o.PASAJERO=? AND o.TIPO LIKE 'IDA' GROUP BY o.CODIGO_OCUPACION");
            sentenciacodocu.setString(1, cod_reserva);
            sentenciacodocu.setInt(2, p1.getCodigo_pasajero());
            ResultSet res = sentenciacodocu.executeQuery();
            while (res.next()) {
                p1.setCodocuida(res.getInt("CODIGO_OCUPACION"));
            }

            PreparedStatement sentenciacodocu2 = conex.prepareStatement("SELECT o.* FROM reserva r, ocupacion o, pasajero p WHERE o.RESERVA LIKE ? AND o.PASAJERO=? AND o.TIPO LIKE 'VUELTA' GROUP BY o.CODIGO_OCUPACION");
            sentenciacodocu2.setString(1, cod_reserva);
            sentenciacodocu2.setInt(2, p1.getCodigo_pasajero());
            ResultSet res2 = sentenciacodocu2.executeQuery();
            while (res2.next()) {
                p1.setCodocuvuelta(res2.getInt("CODIGO_OCUPACION"));
            }

            aNinos.add(p1);
        }

        //Pasajeros Bebes
        ArrayList<Bebe> aBebes = new ArrayList();
        Bebe b1 = new Bebe();

        PreparedStatement sentenciapasajerosb = conex.prepareStatement("SELECT b.* FROM bebe b, tutor t WHERE b.CODIGO_BEBE=t.COD_BEBE AND t.COD_RESERVA LIKE ? GROUP BY b.CODIGO_BEBE");
        sentenciapasajerosb.setString(1, cod_reserva);

        ResultSet resultado9 = sentenciapasajerosb.executeQuery();

        while (resultado9.next()) {
            b1.setApellidos(resultado9.getString("APELLIDOS"));
            b1.setCod_bebe(resultado9.getInt("CODIGO_BEBE"));
            b1.setFecha_nac(LocalDate.parse(resultado9.getString("FECHA_NAC")));
            b1.setNif(resultado9.getString("NIF"));
            b1.setNombre(resultado9.getString("NOMBRE"));
            aBebes.add(b1);
        }

        //Asientos Pasajeros Adultos
        if (!aAdultos.isEmpty()) {
            for (int i = 0; i < aAdultos.size(); i++) {
                int cod_pasajero = aAdultos.get(i).getCodigo_pasajero();

                PreparedStatement sentenciaasientoida = conex.prepareStatement("SELECT * FROM ocupacion WHERE PASAJERO = ? AND RESERVA LIKE ? AND TIPO LIKE 'IDA'");
                sentenciaasientoida.setInt(1, cod_pasajero);
                sentenciaasientoida.setString(2, cod_reserva);

                ResultSet resultado10 = sentenciaasientoida.executeQuery();

                while (resultado10.next()) {
                    aAdultos.get(i).setAsiento_ida(resultado10.getInt("ASIENTO"));
                    aAdultos.get(i).setCodocuida(resultado10.getInt("CODIGO_OCUPACION"));
                }

                if (cod_vuelo_vuelta != 0) {
                    PreparedStatement sentenciaasientovuelta = conex.prepareStatement("SELECT * FROM ocupacion WHERE PASAJERO = ? AND RESERVA LIKE ? AND TIPO LIKE 'VUELTA'");
                    sentenciaasientovuelta.setInt(1, cod_pasajero);
                    sentenciaasientovuelta.setString(2, cod_reserva);

                    ResultSet resultado11 = sentenciaasientovuelta.executeQuery();

                    while (resultado11.next()) {
                        aAdultos.get(i).setAsiento_vuelta(resultado11.getInt("ASIENTO"));
                        aAdultos.get(i).setCodocuvuelta(resultado11.getInt("CODIGO_OCUPACION"));
                    }
                }
            }
        }

        //Asientos Pasajeros Niños
        if (!aNinos.isEmpty()) {
            for (int u = 0; u < aNinos.size(); u++) {
                int cod_pasajero = aNinos.get(u).getCodigo_pasajero();

                PreparedStatement sentenciaasientonida = conex.prepareStatement("SELECT * FROM ocupacion WHERE PASAJERO = ? AND RESERVA LIKE ? AND TIPO LIKE 'IDA'");
                sentenciaasientonida.setInt(1, cod_pasajero);
                sentenciaasientonida.setString(2, cod_reserva);

                ResultSet resultado12 = sentenciaasientonida.executeQuery();

                while (resultado12.next()) {
                    aNinos.get(u).setAsiento_ida(resultado12.getInt("ASIENTO"));
                    aNinos.get(u).setCodocuida(resultado12.getInt("CODIGO_OCUPACION"));
                }

                if (cod_vuelo_vuelta != 0) {
                    PreparedStatement sentenciaasientonvuelta = conex.prepareStatement("SELECT * FROM ocupacion WHERE PASAJERO = ? AND RESERVA LIKE ? AND TIPO LIKE 'VUELTA'");
                    sentenciaasientonvuelta.setInt(1, cod_pasajero);
                    sentenciaasientonvuelta.setString(2, cod_reserva);

                    ResultSet resultado13 = sentenciaasientonvuelta.executeQuery();

                    while (resultado13.next()) {
                        aNinos.get(u).setAsiento_vuelta(resultado13.getInt("ASIENTO"));
                        aNinos.get(u).setCodocuvuelta(resultado13.getInt("CODIGO_OCUPACION"));
                    }
                }
            }
        }

        //Tutores
        if (!aBebes.isEmpty()) {
            for (int j = 0; j < aBebes.size(); j++) {
                PreparedStatement sentenciatutorida = conex.prepareStatement("SELECT p.* FROM bebe b, pasajero p, tutor t WHERE t.COD_BEBE = ? AND t.COD_PASAJERO=p.CODIGO_PASAJERO AND t.COD_RESERVA LIKE ? AND t.TIPO LIKE 'IDA'");
                sentenciatutorida.setInt(1, aBebes.get(j).getCod_bebe());
                sentenciatutorida.setString(2, cod_reserva);

                ResultSet resultado14 = sentenciatutorida.executeQuery();

                while (resultado14.next()) {
                    int codpasaj = resultado14.getInt("CODIGO_PASAJERO");
                    for (int k = 0; k < aAdultos.size(); k++) {
                        if (aAdultos.get(k).getCodigo_pasajero() == codpasaj) {
                            aBebes.get(j).setTutor_ida(aAdultos.get(k));
                        }
                    }
                }

                if (cod_vuelo_vuelta != 0) {
                    PreparedStatement sentenciatutorvuelta = conex.prepareStatement("SELECT p.* FROM bebe b, pasajero p, tutor t WHERE t.COD_BEBE = ? AND t.COD_PASAJERO=p.CODIGO_PASAJERO AND t.COD_RESERVA LIKE ? AND t.TIPO LIKE 'VUELTA'");
                    sentenciatutorvuelta.setInt(1, aBebes.get(j).getCod_bebe());
                    sentenciatutorvuelta.setString(2, cod_reserva);

                    ResultSet resultado15 = sentenciatutorvuelta.executeQuery();

                    while (resultado15.next()) {
                        int codpasaj = resultado15.getInt("CODIGO_PASAJERO");
                        for (int f = 0; f < aAdultos.size(); f++) {
                            if (aAdultos.get(f).getCodigo_pasajero() == codpasaj) {
                                aBebes.get(j).setTutor_vuelta(aAdultos.get(f));
                            }
                        }
                    }
                }
            }
        }

        //Servicios Pasajeros Adultos
        if (!aAdultos.isEmpty()) {
            for (int i2 = 0; i2 < aAdultos.size(); i2++) {
                ArrayList<Servicio> aServiciosIda = new ArrayList();
                PreparedStatement sentenciaservida = conex.prepareStatement("SELECT s.* FROM servicio s, reserva_servicio rs WHERE rs.COD_OCUPACION=(SELECT o.CODIGO_OCUPACION FROM ocupacion o WHERE o.PASAJERO=? AND o.RESERVA LIKE ? AND o.TIPO LIKE 'IDA') AND s.CODIGO_SERVICIO=rs.COD_SERVICIO");
                sentenciaservida.setInt(1, aAdultos.get(i2).getCodigo_pasajero());
                sentenciaservida.setString(2, cod_reserva);

                ResultSet resultado16 = sentenciaservida.executeQuery();

                while (resultado16.next()) {
                    Servicio s1 = new Servicio();
                    s1.setCodigo_servicio(resultado16.getInt("CODIGO_SERVICIO"));
                    s1.setDescripcion(resultado16.getString("DESCRIPCION"));
                    s1.setNombre(resultado16.getString("NOMBRE"));
                    s1.setPrecio(resultado16.getInt("PRECIO"));
                    aServiciosIda.add(s1);
                }

                aAdultos.get(i2).setaServiciosIda(aServiciosIda);

                if (cod_vuelo_vuelta != 0) {
                    ArrayList<Servicio> aServiciosVuelta = new ArrayList();
                    PreparedStatement sentenciaservvuelta = conex.prepareStatement("SELECT s.* FROM servicio s, reserva_servicio rs WHERE rs.COD_OCUPACION=(SELECT o.CODIGO_OCUPACION FROM ocupacion o WHERE o.PASAJERO=? AND o.RESERVA LIKE ? AND o.TIPO LIKE 'VUELTA') AND s.CODIGO_SERVICIO=rs.COD_SERVICIO");
                    sentenciaservvuelta.setInt(1, aAdultos.get(i2).getCodigo_pasajero());
                    sentenciaservvuelta.setString(2, cod_reserva);

                    ResultSet resultado17 = sentenciaservvuelta.executeQuery();

                    while (resultado17.next()) {
                        Servicio s1 = new Servicio();
                        s1.setCodigo_servicio(resultado17.getInt("CODIGO_SERVICIO"));
                        s1.setDescripcion(resultado17.getString("DESCRIPCION"));
                        s1.setNombre(resultado17.getString("NOMBRE"));
                        s1.setPrecio(resultado17.getInt("PRECIO"));
                        aServiciosVuelta.add(s1);
                    }

                    aAdultos.get(i2).setaServiciosVuelta(aServiciosVuelta);
                }
            }
        }

        //Servicios Pasajeros Niños
        if (!aNinos.isEmpty()) {
            for (int i2 = 0; i2 < aNinos.size(); i2++) {
                ArrayList<Servicio> aServiciosIda = new ArrayList();
                PreparedStatement sentenciaservida = conex.prepareStatement("SELECT s.* FROM servicio s, reserva_servicio rs WHERE rs.COD_OCUPACION=(SELECT o.CODIGO_OCUPACION FROM ocupacion o WHERE o.PASAJERO=? AND o.RESERVA LIKE ? AND o.TIPO LIKE 'IDA') AND s.CODIGO_SERVICIO=rs.COD_SERVICIO");
                sentenciaservida.setInt(1, aNinos.get(i2).getCodigo_pasajero());
                sentenciaservida.setString(2, cod_reserva);

                ResultSet resultado18 = sentenciaservida.executeQuery();

                while (resultado18.next()) {
                    Servicio s1 = new Servicio();
                    s1.setCodigo_servicio(resultado18.getInt("CODIGO_SERVICIO"));
                    s1.setDescripcion(resultado18.getString("DESCRIPCION"));
                    s1.setNombre(resultado18.getString("NOMBRE"));
                    s1.setPrecio(resultado18.getInt("PRECIO"));
                    aServiciosIda.add(s1);
                }

                aNinos.get(i2).setaServiciosIda(aServiciosIda);

                if (cod_vuelo_vuelta != 0) {
                    ArrayList<Servicio> aServiciosVuelta = new ArrayList();
                    PreparedStatement sentenciaservvuelta = conex.prepareStatement("SELECT s.* FROM servicio s, reserva_servicio rs WHERE rs.COD_OCUPACION=(SELECT o.CODIGO_OCUPACION FROM ocupacion o WHERE o.PASAJERO=? AND o.RESERVA LIKE ? AND o.TIPO LIKE 'VUELTA') AND s.CODIGO_SERVICIO=rs.COD_SERVICIO");
                    sentenciaservvuelta.setInt(1, aNinos.get(i2).getCodigo_pasajero());
                    sentenciaservvuelta.setString(2, cod_reserva);

                    ResultSet resultado19 = sentenciaservvuelta.executeQuery();

                    while (resultado19.next()) {
                        Servicio s1 = new Servicio();
                        s1.setCodigo_servicio(resultado19.getInt("CODIGO_SERVICIO"));
                        s1.setDescripcion(resultado19.getString("DESCRIPCION"));
                        s1.setNombre(resultado19.getString("NOMBRE"));
                        s1.setPrecio(resultado19.getInt("PRECIO"));
                        aServiciosVuelta.add(s1);
                    }

                    aNinos.get(i2).setaServiciosVuelta(aServiciosVuelta);
                }
            }
        }

        //Tarjeta
        PreparedStatement sentenciatar = conex.prepareStatement("SELECT t.* FROM tarjeta t, reserva r WHERE t.CODIGO_TARJETA=r.TARJETA AND r.COD_RESERVA LIKE ?");
        sentenciatar.setString(1, cod_reserva);
        ResultSet res = sentenciatar.executeQuery();
        while (res.next()) {
            Tarjeta t1 = new Tarjeta();
            t1.setCaducidad(LocalDate.parse(res.getString("CADUCIDAD")));
            t1.setCod_seguridad(res.getString("CODSEGURIDAD"));
            t1.setCodigo_tarjeta(res.getInt("CODIGO_TARJETA"));
            t1.setNum_usos(res.getInt("NUM_USOS"));
            r1.setTarjeta(t1);
        }

        r1.setaPasajerosAdultos(aAdultos);
        r1.setaPasajerosNinos(aNinos);
        r1.setaPasajerosBebes(aBebes);

        return r1;
    }

    public Boolean facturarIda(Connection conex, Reserva r1) throws SQLException {
        String cod_reserva = r1.getCod_reserva();
        Boolean h;

        try {
            conex.setAutoCommit(false);

            PreparedStatement sentenciafactida = conex.prepareStatement("UPDATE reserva SET FACTURADA_IDA = 'S' WHERE COD_RESERVA LIKE ?");
            sentenciafactida.setString(1, cod_reserva);

            int rows = -1;

            rows = sentenciafactida.executeUpdate();

            if (rows == 1) {
                h = true;
            } else {
                h = false;
            }

            conex.commit();
        } catch (SQLException ex) {
            conex.rollback();
            h = false;
        }

        return h;
    }

    public Boolean facturarVuelta(Connection conex, Reserva r1) throws SQLException {
        String cod_reserva = r1.getCod_reserva();
        Boolean h;

        try {
            conex.setAutoCommit(false);

            PreparedStatement sentenciafactvuelta = conex.prepareStatement("UPDATE reserva SET FACTURADA_VUELTA = 'S' WHERE COD_RESERVA LIKE ?");
            sentenciafactvuelta.setString(1, cod_reserva);

            int rows = -1;

            rows = sentenciafactvuelta.executeUpdate();

            if (rows == 1) {
                h = true;
            } else {
                h = false;
            }

            conex.commit();
        } catch (SQLException ex) {
            conex.rollback();
            h = false;
        }

        return h;
    }

    public Boolean asignarAsientoFacturacionIda(Connection conex, Pasajero p1, String cod_reserva) throws SQLException {
        int cod_pasajero = p1.getCodigo_pasajero();
        int asiento = p1.getAsiento_ida();

        PreparedStatement sentenciaasida = conex.prepareStatement("UPDATE ocupacion SET ocupacion.ASIENTO = ? WHERE ocupacion.PASAJERO = ? AND ocupacion.RESERVA LIKE ? AND TIPO LIKE 'IDA'");
        sentenciaasida.setInt(1, asiento);
        sentenciaasida.setInt(2, cod_pasajero);
        sentenciaasida.setString(3, cod_reserva);

        int resultado = sentenciaasida.executeUpdate();

        if (resultado != 0) {
            return true;
        } else {
            return false;
        }
    }

    public Boolean asignarAsientoFacturacionVuelta(Connection conex, Pasajero p1, String cod_reserva) throws SQLException {
        int cod_pasajero = p1.getCodigo_pasajero();
        int asiento = p1.getAsiento_vuelta();

        PreparedStatement sentenciaasvuelta = conex.prepareStatement("UPDATE ocupacion SET ocupacion.ASIENTO = ? WHERE ocupacion.PASAJERO = ? AND ocupacion.RESERVA LIKE ? AND TIPO LIKE 'VUELTA'");
        sentenciaasvuelta.setInt(1, asiento);
        sentenciaasvuelta.setInt(2, cod_pasajero);
        sentenciaasvuelta.setString(3, cod_reserva);

        int resultado = sentenciaasvuelta.executeUpdate();

        if (resultado != 0) {
            return true;
        } else {
            return false;
        }
    }

    public Vuelo sacarVueloVolar(Connection conex, String numero, LocalDate fecha) throws SQLException {
        Vuelo v1 = new Vuelo();
        String fechavuelo = fecha.toString();

        PreparedStatement sentencia = conex.prepareStatement("SELECT * FROM vuelo WHERE NUMERO LIKE ? AND FECHA LIKE ?");
        sentencia.setString(1, numero);
        sentencia.setString(2, fechavuelo);

        ResultSet res = sentencia.executeQuery();

        while (res.next()) {
            v1.setCodigo_vuelo(res.getInt("CODIGO_VUELO"));
            v1.setAsientos_libres(res.getInt("ASIENTOS_LIBRES"));
            v1.setConexion(res.getInt("CONEXION"));
            v1.setFecha(LocalDate.parse(res.getString("FECHA")));
            v1.setHora_llegada(LocalTime.parse(res.getString("HORA_LLEGADA")));
            v1.setHora_salida(LocalTime.parse(res.getString("HORA_SALIDA")));
            v1.setDuracion(LocalTime.parse(res.getString("DURACION")));
            v1.setNum_vuelo(res.getString("NUMERO"));
            v1.setPrecio(res.getInt("PRECIO"));
            v1.setAvion(res.getString("AVION"));
        }

        return v1;
    }

    public ArrayList<Reserva> sacarReservasSoloIdaVolar(Connection conex, Vuelo vuelo) throws SQLException {
        ArrayList<Reserva> aReservas = new ArrayList();

        PreparedStatement sentencia = conex.prepareStatement("SELECT * FROM reserva WHERE COD_VUELO_IDA=? AND COD_VUELO_VUELTA IS NULL");
        sentencia.setInt(1, vuelo.getCodigo_vuelo());

        ResultSet res = sentencia.executeQuery();

        while (res.next()) {
            aReservas.add(montarReservaFacturar(conex, res.getString("COD_RESERVA")));
        }

        return aReservas;
    }

    public ArrayList<Reserva> sacarReservasIdaVolar(Connection conex, Vuelo vuelo) throws SQLException {
        ArrayList<Reserva> aReservas = new ArrayList();

        PreparedStatement sentencia = conex.prepareStatement("SELECT * FROM reserva WHERE COD_VUELO_IDA=? AND COD_VUELO_VUELTA IS NOT NULL");
        sentencia.setInt(1, vuelo.getCodigo_vuelo());

        ResultSet res = sentencia.executeQuery();

        while (res.next()) {
            aReservas.add(montarReservaFacturar(conex, res.getString("COD_RESERVA")));
        }

        return aReservas;
    }

    public ArrayList<Reserva> sacarReservasVueltaVolar(Connection conex, Vuelo vuelo) throws SQLException {
        ArrayList<Reserva> aReservas = new ArrayList();

        PreparedStatement sentencia = conex.prepareStatement("SELECT * FROM reserva WHERE COD_VUELO_VUELTA=?");
        sentencia.setInt(1, vuelo.getCodigo_vuelo());

        ResultSet res = sentencia.executeQuery();

        while (res.next()) {
            aReservas.add(montarReservaFacturar(conex, res.getString("COD_RESERVA")));
        }

        return aReservas;
    }

    public Boolean reservaBackupSoloIda(Connection conex, Reserva reserva) throws SQLException {
        try {
            conex.setAutoCommit(false);
            //Pasajeros Adultos
            for (int i = 0; i < reserva.getaPasajerosAdultos().size(); i++) {
                PreparedStatement sentenciapasajero;

                sentenciapasajero = conex.prepareStatement("INSERT INTO pasajero_backup (TRATAMIENTO, NOMBRE, APELLIDOS, NIF, CADUCIDAD_NIF, FECHA_NAC, TIPO) VALUES (?, ?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
                sentenciapasajero.setString(1, reserva.getaPasajerosAdultos().get(i).getTratamiento());
                sentenciapasajero.setString(2, reserva.getaPasajerosAdultos().get(i).getNombre());
                sentenciapasajero.setString(3, reserva.getaPasajerosAdultos().get(i).getApellidos());
                sentenciapasajero.setString(4, reserva.getaPasajerosAdultos().get(i).getNif());
                sentenciapasajero.setString(5, reserva.getaPasajerosAdultos().get(i).getFecha_caducidad().toString());
                sentenciapasajero.setString(6, reserva.getaPasajerosAdultos().get(i).getFecha_nac().toString());
                sentenciapasajero.setString(7, reserva.getaPasajerosAdultos().get(i).getTipo());

                sentenciapasajero.executeUpdate();

                ResultSet generatedIdpasajero = sentenciapasajero.getGeneratedKeys();
                if (generatedIdpasajero.next()) {
                    reserva.getaPasajerosAdultos().get(i).setCodigo_pasajero(generatedIdpasajero.getInt(1));
                }
            }

            for (int i = 0; i < reserva.getaPasajerosAdultos().size(); i++) {
                PreparedStatement sentenciapasajero;

                sentenciapasajero = conex.prepareStatement("DELETE FROM pasajero WHERE CODIGO_PASAJERO = ?");
                sentenciapasajero.setInt(1, reserva.getaPasajerosAdultos().get(i).getCodigo_pasajero());

                sentenciapasajero.executeUpdate();
            }

            //Pasajeros Niños
            if (!reserva.getaPasajerosNinos().isEmpty()) {
                for (int i = 0; i < reserva.getaPasajerosNinos().size(); i++) {
                    PreparedStatement sentenciapasajero;

                    sentenciapasajero = conex.prepareStatement("INSERT INTO pasajero_backup (TRATAMIENTO, NOMBRE, APELLIDOS, NIF, CADUCIDAD_NIF, FECHA_NAC, TIPO) VALUES (?, ?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
                    sentenciapasajero.setString(1, reserva.getaPasajerosNinos().get(i).getTratamiento());
                    sentenciapasajero.setString(2, reserva.getaPasajerosNinos().get(i).getNombre());
                    sentenciapasajero.setString(3, reserva.getaPasajerosNinos().get(i).getApellidos());
                    sentenciapasajero.setString(4, reserva.getaPasajerosNinos().get(i).getNif());
                    sentenciapasajero.setString(5, reserva.getaPasajerosNinos().get(i).getFecha_caducidad().toString());
                    sentenciapasajero.setString(6, reserva.getaPasajerosNinos().get(i).getFecha_nac().toString());
                    sentenciapasajero.setString(7, reserva.getaPasajerosNinos().get(i).getTipo());

                    sentenciapasajero.executeUpdate();

                    ResultSet generatedIdpasajeronino = sentenciapasajero.getGeneratedKeys();
                    if (generatedIdpasajeronino.next()) {
                        reserva.getaPasajerosNinos().get(i).setCodigo_pasajero(generatedIdpasajeronino.getInt(1));
                    }
                }

                for (int i = 0; i < reserva.getaPasajerosNinos().size(); i++) {
                    PreparedStatement sentenciapasajero;

                    sentenciapasajero = conex.prepareStatement("DELETE FROM pasajero WHERE CODIGO_PASAJERO = ?");
                    sentenciapasajero.setInt(1, reserva.getaPasajerosNinos().get(i).getCodigo_pasajero());

                    sentenciapasajero.executeUpdate();
                }

            }

            //Bebes
            if (!reserva.getaPasajerosBebes().isEmpty()) {
                for (int i = 0; i < reserva.getaPasajerosBebes().size(); i++) {
                    PreparedStatement sentenciapasajero;

                    sentenciapasajero = conex.prepareStatement("INSERT INTO bebe_backup (NOMBRE, APELLIDOS, NIF, FECHA_NAC) VALUES(?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
                    sentenciapasajero.setString(1, reserva.getaPasajerosBebes().get(i).getNombre());
                    sentenciapasajero.setString(2, reserva.getaPasajerosBebes().get(i).getApellidos());
                    sentenciapasajero.setString(3, reserva.getaPasajerosBebes().get(i).getNif());
                    sentenciapasajero.setString(4, reserva.getaPasajerosBebes().get(i).getFecha_nac().toString());

                    sentenciapasajero.executeUpdate();

                    ResultSet generatedIdpasajerobebe = sentenciapasajero.getGeneratedKeys();
                    if (generatedIdpasajerobebe.next()) {
                        reserva.getaPasajerosBebes().get(i).setCod_bebe(generatedIdpasajerobebe.getInt(1));
                    }
                }
                for (int i = 0; i < reserva.getaPasajerosBebes().size(); i++) {
                    PreparedStatement sentenciapasajero;

                    sentenciapasajero = conex.prepareStatement("DELETE FROM bebe WHERE CODIGO_BEBE = ?");
                    sentenciapasajero.setInt(1, reserva.getaPasajerosBebes().get(i).getCod_bebe());

                    sentenciapasajero.executeUpdate();
                }

            }

            //Reserva
            PreparedStatement sentenciareserva;
            sentenciareserva = conex.prepareStatement("INSERT INTO reserva_backup (CODIGO_RESERVA, COD_VUELO_IDA, COD_VUELO_VUELTA, COD_RESERVA, TARJETA, NUMERO_VIAJEROS, PRECIO_TOTAL, FECHA) VALUES(?, ?, NULL, ?, ?, ?, ?, ?)");
            sentenciareserva.setInt(1, reserva.getCodigo_reserva());
            sentenciareserva.setInt(2, reserva.getVuelo_ida().getCodigo_vuelo());
            sentenciareserva.setString(3, reserva.getCod_reserva());
            sentenciareserva.setInt(4, reserva.getTarjeta().getCodigo_tarjeta());
            sentenciareserva.setInt(5, reserva.getNum_viajeros());
            sentenciareserva.setInt(6, reserva.getPrecio_total());
            sentenciareserva.setString(7, reserva.getFecha().toString());
            sentenciareserva.executeUpdate();

            //Tutor
            if (!reserva.getaPasajerosBebes().isEmpty()) {
                //Ida
                PreparedStatement sentenciatutor;
                for (int bb = 0; bb < reserva.getaPasajerosBebes().size(); bb++) {
                    String cod_reserva = reserva.getCod_reserva();
                    int cod_bebe = reserva.getaPasajerosBebes().get(bb).getCod_bebe();
                    int cod_tutor = reserva.getaPasajerosBebes().get(bb).getTutor_ida().getCodigo_pasajero();

                    sentenciatutor = conex.prepareStatement("INSERT INTO tutor_backup (COD_PASAJERO, COD_BEBE, COD_RESERVA, TIPO) VALUES(?, ?, ?, 'IDA')");
                    sentenciatutor.setInt(1, cod_tutor);
                    sentenciatutor.setInt(2, cod_bebe);
                    sentenciatutor.setString(3, cod_reserva);
                    sentenciatutor.executeUpdate();

                    PreparedStatement borrartutor;
                    borrartutor = conex.prepareStatement("DELETE FROM tutor WHERE COD_BEBE=? AND COD_RESERVA LIKE ? AND TIPO LIKE 'IDA'");
                    borrartutor.setInt(1, reserva.getaPasajerosBebes().get(bb).getCod_bebe());
                    borrartutor.setString(2, reserva.getCod_reserva());
                    borrartutor.executeUpdate();
                }
            }

            //Servicios a BackUp
            PreparedStatement backupservicio;
            ArrayList<Servicio> aServicios = sacarServicios(conex);
            for (int se = 0; se < aServicios.size(); se++) {
                Boolean ser1 = buscarServicioBackup(conex, aServicios.get(se));
                if (!ser1) {
                    backupservicio = conex.prepareStatement("INSERT INTO servicio_backup (NOMBRE, DESCRIPCION, PRECIO) VALUES(?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
                    backupservicio.setString(1, aServicios.get(se).getNombre());
                    backupservicio.setString(2, aServicios.get(se).getDescripcion());
                    backupservicio.setInt(3, aServicios.get(se).getPrecio());
                    backupservicio.executeUpdate();

                    ResultSet generatedIdservicio = backupservicio.getGeneratedKeys();
                    if (generatedIdservicio.next()) {
                        aServicios.get(se).setCodigo_servicio(generatedIdservicio.getInt(1));
                    }
                }

            }

            //Ocupacion y servicios Adultos
            for (int j = 0; j < reserva.getaPasajerosAdultos().size(); j++) {
                //OcupacionIda
                PreparedStatement sentenciaocuida;
                sentenciaocuida = conex.prepareStatement("INSERT INTO ocupacion_backup (RESERVA, TIPO, PASAJERO, ASIENTO) VALUES(?, 'IDA', ?, ?)", Statement.RETURN_GENERATED_KEYS);
                sentenciaocuida.setString(1, reserva.getCod_reserva());
                sentenciaocuida.setInt(2, reserva.getaPasajerosAdultos().get(j).getCodigo_pasajero());
                sentenciaocuida.setInt(3, reserva.getaPasajerosAdultos().get(j).getAsiento_ida());
                sentenciaocuida.executeUpdate();
                int idOcuida = 0;
                ResultSet generatedIdocuida = sentenciaocuida.getGeneratedKeys();
                if (generatedIdocuida.next()) {
                    idOcuida = generatedIdocuida.getInt(1);
                }
                //Servicios Ocupacion Ida
                PreparedStatement sentenciaservida;
                int codServBackupA = 0;
                if (!reserva.getaPasajerosAdultos().get(j).getaServiciosIda().isEmpty()) {
                    for (int o = 0; o < reserva.getaPasajerosAdultos().get(j).getaServiciosIda().size(); o++) {
                        for (int zx = 0; zx < aServicios.size(); zx++) {
                            if (reserva.getaPasajerosAdultos().get(j).getaServiciosIda().get(o).getNombre().equals(aServicios.get(zx).getNombre())) {
                                codServBackupA = aServicios.get(zx).getCodigo_servicio();
                            }
                        }
                        sentenciaservida = conex.prepareStatement("INSERT INTO reserva_servicio_backup (COD_OCUPACION, COD_SERVICIO) VALUES(?, ?)");
                        sentenciaservida.setInt(1, idOcuida);
                        sentenciaservida.setInt(2, codServBackupA);
                        sentenciaservida.executeUpdate();
                    }
                }
                PreparedStatement borraro;
                borraro = conex.prepareStatement("DELETE FROM ocupacion WHERE RESERVA LIKE ? AND PASAJERO=? AND TIPO LIKE 'IDA'");
                borraro.setString(1, reserva.getCod_reserva());
                borraro.setInt(2, reserva.getaPasajerosAdultos().get(j).getCodigo_pasajero());
                borraro.executeUpdate();
            }
            //Ocupacion y servicios Niños
            if (!reserva.getaPasajerosNinos().isEmpty()) {
                for (int j = 0; j < reserva.getaPasajerosNinos().size(); j++) {
                    //OcupacionIda
                    PreparedStatement sentenciaocuida;
                    sentenciaocuida = conex.prepareStatement("INSERT INTO ocupacion_backup (RESERVA, TIPO, PASAJERO, ASIENTO) VALUES(?, 'IDA', ?, ?)", Statement.RETURN_GENERATED_KEYS);
                    sentenciaocuida.setString(1, reserva.getCod_reserva());
                    sentenciaocuida.setInt(2, reserva.getaPasajerosNinos().get(j).getCodigo_pasajero());
                    sentenciaocuida.setInt(3, reserva.getaPasajerosNinos().get(j).getAsiento_ida());
                    sentenciaocuida.executeUpdate();
                    int idOcuida = 0;
                    ResultSet generatedIdocuida = sentenciaocuida.getGeneratedKeys();
                    if (generatedIdocuida.next()) {
                        idOcuida = generatedIdocuida.getInt(1);
                    }
                    //Servicios Ocupacion Ida
                    int codServBackupN = 0;
                    PreparedStatement sentenciaservida;
                    if (!reserva.getaPasajerosNinos().get(j).getaServiciosIda().isEmpty()) {
                        for (int o = 0; o < reserva.getaPasajerosNinos().get(j).getaServiciosIda().size(); o++) {
                            for (int zx = 0; zx < aServicios.size(); zx++) {
                                if (reserva.getaPasajerosNinos().get(j).getaServiciosIda().get(o).getNombre().equals(aServicios.get(zx).getNombre())) {
                                    codServBackupN = aServicios.get(zx).getCodigo_servicio();
                                }
                            }
                            sentenciaservida = conex.prepareStatement("INSERT INTO reserva_servicio_backup (COD_OCUPACION, COD_SERVICIO) VALUES(?, ?)");
                            sentenciaservida.setInt(1, idOcuida);
                            sentenciaservida.setInt(2, codServBackupN);
                            sentenciaservida.executeUpdate();
                        }
                    }
                    PreparedStatement borraro;
                    borraro = conex.prepareStatement("DELETE FROM ocupacion WHERE RESERVA LIKE ? AND PASAJERO=? AND TIPO LIKE 'IDA'");
                    borraro.setString(1, reserva.getCod_reserva());
                    borraro.setInt(2, reserva.getaPasajerosNinos().get(j).getCodigo_pasajero());
                    borraro.executeUpdate();
                }
            }

            //Borrando
            PreparedStatement borrarreserva;
            borrarreserva = conex.prepareStatement("DELETE FROM reserva WHERE COD_RESERVA LIKE ?");
            borrarreserva.setString(1, reserva.getCod_reserva());
            borrarreserva.executeUpdate();

            conex.commit();
            return true;
        } catch (SQLException ex) {
            conex.rollback();
            return false;
        }
    }

    public Boolean reservaBackupIda(Connection conex, Reserva reserva) throws SQLException {
        try {
            conex.setAutoCommit(false);

            //Reserva
            PreparedStatement sentenciareserva;
            sentenciareserva = conex.prepareStatement("INSERT INTO reserva_backup (CODIGO_RESERVA, COD_VUELO_IDA, COD_VUELO_VUELTA, COD_RESERVA, TARJETA, NUMERO_VIAJEROS, PRECIO_TOTAL, FECHA) VALUES(?, ?, NULL, ?, ?, ?, ?, ?)");
            sentenciareserva.setInt(1, reserva.getCodigo_reserva());
            sentenciareserva.setInt(2, reserva.getVuelo_ida().getCodigo_vuelo());
            sentenciareserva.setString(3, reserva.getCod_reserva());
            sentenciareserva.setInt(4, reserva.getTarjeta().getCodigo_tarjeta());
            sentenciareserva.setInt(5, reserva.getNum_viajeros());
            sentenciareserva.setInt(6, reserva.getPrecio_total());
            sentenciareserva.setString(7, reserva.getFecha().toString());
            sentenciareserva.executeUpdate();

            //Tutor
            if (!reserva.getaPasajerosBebes().isEmpty()) {
                //Ida
                PreparedStatement sentenciatutor;
                for (int bb = 0; bb < reserva.getaPasajerosBebes().size(); bb++) {
                    String cod_reserva = reserva.getCod_reserva();
                    int cod_bebe = reserva.getaPasajerosBebes().get(bb).getCod_bebe();
                    int cod_tutor = reserva.getaPasajerosBebes().get(bb).getTutor_ida().getCodigo_pasajero();

                    sentenciatutor = conex.prepareStatement("INSERT INTO tutor_backup (COD_PASAJERO, COD_BEBE, COD_RESERVA, TIPO) VALUES(?, ?, ?, 'IDA')");
                    sentenciatutor.setInt(1, cod_tutor);
                    sentenciatutor.setInt(2, cod_bebe);
                    sentenciatutor.setString(3, cod_reserva);
                    sentenciatutor.executeUpdate();

                    PreparedStatement borrartutor;
                    borrartutor = conex.prepareStatement("DELETE FROM tutor WHERE COD_BEBE=? AND COD_RESERVA LIKE ? AND TIPO LIKE 'IDA'");
                    borrartutor.setInt(1, reserva.getaPasajerosBebes().get(bb).getCod_bebe());
                    borrartutor.setString(2, reserva.getCod_reserva());
                    borrartutor.executeUpdate();
                }
            }

            //Servicios a BackUp
            PreparedStatement backupservicio;
            ArrayList<Servicio> aServicios = sacarServicios(conex);
            for (int se = 0; se < aServicios.size(); se++) {
                Boolean ser1 = buscarServicioBackup(conex, aServicios.get(se));
                if (!ser1) {
                    backupservicio = conex.prepareStatement("INSERT INTO servicio_backup (NOMBRE, DESCRIPCION, PRECIO) VALUES(?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
                    backupservicio.setString(1, aServicios.get(se).getNombre());
                    backupservicio.setString(2, aServicios.get(se).getDescripcion());
                    backupservicio.setInt(3, aServicios.get(se).getPrecio());
                    backupservicio.executeUpdate();

                    ResultSet generatedIdservicio = backupservicio.getGeneratedKeys();
                    if (generatedIdservicio.next()) {
                        aServicios.get(se).setCodigo_servicio(generatedIdservicio.getInt(1));
                    }
                }

            }

            //Ocupacion y servicios Adultos
            for (int j = 0; j < reserva.getaPasajerosAdultos().size(); j++) {
                //OcupacionIda
                PreparedStatement sentenciaocuida;
                sentenciaocuida = conex.prepareStatement("INSERT INTO ocupacion_backup (RESERVA, TIPO, PASAJERO, ASIENTO) VALUES(?, 'IDA', ?, ?)", Statement.RETURN_GENERATED_KEYS);
                sentenciaocuida.setString(1, reserva.getCod_reserva());
                sentenciaocuida.setInt(2, reserva.getaPasajerosAdultos().get(j).getCodigo_pasajero());
                sentenciaocuida.setInt(3, reserva.getaPasajerosAdultos().get(j).getAsiento_ida());
                sentenciaocuida.executeUpdate();
                int idOcuida = 0;
                ResultSet generatedIdocuida = sentenciaocuida.getGeneratedKeys();
                if (generatedIdocuida.next()) {
                    idOcuida = generatedIdocuida.getInt(1);
                }
                //Servicios Ocupacion Ida
                int codServBackupA = 0;
                PreparedStatement sentenciaservida;
                if (!reserva.getaPasajerosAdultos().get(j).getaServiciosIda().isEmpty()) {
                    for (int o = 0; o < reserva.getaPasajerosAdultos().get(j).getaServiciosIda().size(); o++) {
                        for (int zx = 0; zx < aServicios.size(); zx++) {
                            if (reserva.getaPasajerosAdultos().get(j).getaServiciosIda().get(o).getNombre().equals(aServicios.get(zx).getNombre())) {
                                codServBackupA = aServicios.get(zx).getCodigo_servicio();
                            }
                        }
                        sentenciaservida = conex.prepareStatement("INSERT INTO reserva_servicio_backup (COD_OCUPACION, COD_SERVICIO) VALUES(?, ?)");
                        sentenciaservida.setInt(1, idOcuida);
                        sentenciaservida.setInt(2, codServBackupA);
                        sentenciaservida.executeUpdate();
                    }
                }
                PreparedStatement borraro;
                borraro = conex.prepareStatement("DELETE FROM ocupacion WHERE CODIGO_OCUPACION=?");
                borraro.setInt(1, reserva.getaPasajerosAdultos().get(j).getCodocuida());
                borraro.executeUpdate();
            }
            //Ocupacion y servicios Niños
            if (!reserva.getaPasajerosNinos().isEmpty()) {
                for (int j = 0; j < reserva.getaPasajerosNinos().size(); j++) {
                    //OcupacionIda
                    PreparedStatement sentenciaocuida;
                    sentenciaocuida = conex.prepareStatement("INSERT INTO ocupacion_backup (RESERVA, TIPO, PASAJERO, ASIENTO) VALUES(?, 'IDA', ?, ?)", Statement.RETURN_GENERATED_KEYS);
                    sentenciaocuida.setString(1, reserva.getCod_reserva());
                    sentenciaocuida.setInt(2, reserva.getaPasajerosNinos().get(j).getCodigo_pasajero());
                    sentenciaocuida.setInt(3, reserva.getaPasajerosNinos().get(j).getAsiento_ida());
                    sentenciaocuida.executeUpdate();
                    int idOcuida = 0;
                    ResultSet generatedIdocuida = sentenciaocuida.getGeneratedKeys();
                    if (generatedIdocuida.next()) {
                        idOcuida = generatedIdocuida.getInt(1);
                    }
                    //Servicios Ocupacion Ida
                    int codServBackupN = 0;
                    PreparedStatement sentenciaservida;
                    if (!reserva.getaPasajerosNinos().get(j).getaServiciosIda().isEmpty()) {
                        for (int o = 0; o < reserva.getaPasajerosNinos().get(j).getaServiciosIda().size(); o++) {
                            for (int zx = 0; zx < aServicios.size(); zx++) {
                                if (reserva.getaPasajerosNinos().get(j).getaServiciosIda().get(o).getNombre().equals(aServicios.get(zx).getNombre())) {
                                    codServBackupN = aServicios.get(zx).getCodigo_servicio();
                                }
                            }
                            sentenciaservida = conex.prepareStatement("INSERT INTO reserva_servicio_backup (COD_OCUPACION, COD_SERVICIO) VALUES(?, ?)");
                            sentenciaservida.setInt(1, idOcuida);
                            sentenciaservida.setInt(2, codServBackupN);
                            sentenciaservida.executeUpdate();
                        }
                    }
                    PreparedStatement borraro;
                    borraro = conex.prepareStatement("DELETE FROM ocupacion WHERE RESERVA LIKE ? AND PASAJERO=? AND TIPO LIKE 'IDA'");
                    borraro.setString(1, reserva.getCod_reserva());
                    borraro.setInt(2, reserva.getaPasajerosNinos().get(j).getCodigo_pasajero());
                    borraro.executeUpdate();
                }
            }

            //Borrando
            PreparedStatement update;
            update = conex.prepareStatement("UPDATE reserva SET COD_VUELO_IDA = NULL WHERE COD_RESERVA LIKE ?");
            update.setString(1, reserva.getCod_reserva());
            update.executeUpdate();

            conex.commit();
            return true;
        } catch (SQLException ex) {
            conex.rollback();
            return false;
        }
    }

    public Boolean reservaBackupVuelta(Connection conex, Reserva reserva) throws SQLException {
        try {
            conex.setAutoCommit(false);
            //Pasajeros Adultos
            for (int i = 0; i < reserva.getaPasajerosAdultos().size(); i++) {
                PreparedStatement sentenciapasajero;

                sentenciapasajero = conex.prepareStatement("INSERT INTO pasajero_backup (TRATAMIENTO, NOMBRE, APELLIDOS, NIF, CADUCIDAD_NIF, FECHA_NAC, TIPO) VALUES (?, ?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
                sentenciapasajero.setString(1, reserva.getaPasajerosAdultos().get(i).getTratamiento());
                sentenciapasajero.setString(2, reserva.getaPasajerosAdultos().get(i).getNombre());
                sentenciapasajero.setString(3, reserva.getaPasajerosAdultos().get(i).getApellidos());
                sentenciapasajero.setString(4, reserva.getaPasajerosAdultos().get(i).getNif());
                sentenciapasajero.setString(5, reserva.getaPasajerosAdultos().get(i).getFecha_caducidad().toString());
                sentenciapasajero.setString(6, reserva.getaPasajerosAdultos().get(i).getFecha_nac().toString());
                sentenciapasajero.setString(7, reserva.getaPasajerosAdultos().get(i).getTipo());

                sentenciapasajero.executeUpdate();

                ResultSet generatedIdpasajero = sentenciapasajero.getGeneratedKeys();
                if (generatedIdpasajero.next()) {
                    reserva.getaPasajerosAdultos().get(i).setCodigo_pasajero(generatedIdpasajero.getInt(1));
                }
            }
            
            for (int i = 0; i < reserva.getaPasajerosAdultos().size(); i++) {
                PreparedStatement sentenciapasajero;

                sentenciapasajero = conex.prepareStatement("DELETE FROM pasajero WHERE CODIGO_PASAJERO = ?");
                sentenciapasajero.setInt(1, reserva.getaPasajerosAdultos().get(i).getCodigo_pasajero());

                sentenciapasajero.executeUpdate();
            }

            //Pasajeros Niños
            if (!reserva.getaPasajerosNinos().isEmpty()) {
                for (int i = 0; i < reserva.getaPasajerosNinos().size(); i++) {
                    PreparedStatement sentenciapasajero;

                    sentenciapasajero = conex.prepareStatement("INSERT INTO pasajero_backup (TRATAMIENTO, NOMBRE, APELLIDOS, NIF, CADUCIDAD_NIF, FECHA_NAC, TIPO) VALUES (?, ?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
                    sentenciapasajero.setString(1, reserva.getaPasajerosNinos().get(i).getTratamiento());
                    sentenciapasajero.setString(2, reserva.getaPasajerosNinos().get(i).getNombre());
                    sentenciapasajero.setString(3, reserva.getaPasajerosNinos().get(i).getApellidos());
                    sentenciapasajero.setString(4, reserva.getaPasajerosNinos().get(i).getNif());
                    sentenciapasajero.setString(5, reserva.getaPasajerosNinos().get(i).getFecha_caducidad().toString());
                    sentenciapasajero.setString(6, reserva.getaPasajerosNinos().get(i).getFecha_nac().toString());
                    sentenciapasajero.setString(7, reserva.getaPasajerosNinos().get(i).getTipo());

                    sentenciapasajero.executeUpdate();

                    ResultSet generatedIdpasajeronino = sentenciapasajero.getGeneratedKeys();
                    if (generatedIdpasajeronino.next()) {
                        reserva.getaPasajerosNinos().get(i).setCodigo_pasajero(generatedIdpasajeronino.getInt(1));
                    }
                }
                
                for (int i = 0; i < reserva.getaPasajerosNinos().size(); i++) {
                    PreparedStatement sentenciapasajero;

                    sentenciapasajero = conex.prepareStatement("DELETE FROM pasajero WHERE CODIGO_PASAJERO = ?");
                    sentenciapasajero.setInt(1, reserva.getaPasajerosNinos().get(i).getCodigo_pasajero());

                    sentenciapasajero.executeUpdate();
                }
            }

            //Bebes
            if (!reserva.getaPasajerosBebes().isEmpty()) {
                for (int i = 0; i < reserva.getaPasajerosBebes().size(); i++) {
                    PreparedStatement sentenciapasajero;

                    sentenciapasajero = conex.prepareStatement("INSERT INTO bebe_backup (NOMBRE, APELLIDOS, NIF, FECHA_NAC) VALUES(?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
                    sentenciapasajero.setString(1, reserva.getaPasajerosBebes().get(i).getNombre());
                    sentenciapasajero.setString(2, reserva.getaPasajerosBebes().get(i).getApellidos());
                    sentenciapasajero.setString(3, reserva.getaPasajerosBebes().get(i).getNif());
                    sentenciapasajero.setString(4, reserva.getaPasajerosBebes().get(i).getFecha_nac().toString());

                    sentenciapasajero.executeUpdate();

                    ResultSet generatedIdpasajerobebe = sentenciapasajero.getGeneratedKeys();
                    if (generatedIdpasajerobebe.next()) {
                        reserva.getaPasajerosBebes().get(i).setCod_bebe(generatedIdpasajerobebe.getInt(1));
                    }
                }
                
                for (int i = 0; i < reserva.getaPasajerosBebes().size(); i++) {
                    PreparedStatement sentenciapasajero;

                    sentenciapasajero = conex.prepareStatement("DELETE FROM bebe WHERE CODIGO_BEBE = ?");
                    sentenciapasajero.setInt(1, reserva.getaPasajerosBebes().get(i).getCod_bebe());

                    sentenciapasajero.executeUpdate();
                }
            }

            //Reserva
            PreparedStatement update;
            update = conex.prepareStatement("UPDATE reserva_backup SET COD_VUELO_VUELTA = ? WHERE COD_RESERVA LIKE ?");
            update.setInt(1, reserva.getVuelo_vuelta().getCodigo_vuelo());
            update.setString(2, reserva.getCod_reserva());
            update.executeUpdate();

            //Tutor
            if (!reserva.getaPasajerosBebes().isEmpty()) {
                //Vuelta
                PreparedStatement sentenciatutor;
                for (int bb = 0; bb < reserva.getaPasajerosBebes().size(); bb++) {
                    String cod_reserva = reserva.getCod_reserva();
                    int cod_bebe = reserva.getaPasajerosBebes().get(bb).getCod_bebe();
                    int cod_tutor = reserva.getaPasajerosBebes().get(bb).getTutor_vuelta().getCodigo_pasajero();

                    sentenciatutor = conex.prepareStatement("INSERT INTO tutor_backup (COD_PASAJERO, COD_BEBE, COD_RESERVA, TIPO) VALUES(?, ?, ?, 'VUELTA')");
                    sentenciatutor.setInt(1, cod_tutor);
                    sentenciatutor.setInt(2, cod_bebe);
                    sentenciatutor.setString(3, cod_reserva);
                    sentenciatutor.executeUpdate();

                    PreparedStatement borrartutor;
                    borrartutor = conex.prepareStatement("DELETE FROM tutor WHERE COD_BEBE=? AND COD_RESERVA LIKE ? AND TIPO LIKE 'VUELTA'");
                    borrartutor.setInt(1, reserva.getaPasajerosBebes().get(bb).getCod_bebe());
                    borrartutor.setString(2, reserva.getCod_reserva());
                    borrartutor.executeUpdate();
                }
            }

            //Servicios a BackUp
            PreparedStatement backupservicio;
            ArrayList<Servicio> aServicios = sacarServicios(conex);
            for (int se = 0; se < aServicios.size(); se++) {
                Boolean ser1 = buscarServicioBackup(conex, aServicios.get(se));
                if (!ser1) {
                    backupservicio = conex.prepareStatement("INSERT INTO servicio_backup (NOMBRE, DESCRIPCION, PRECIO) VALUES(?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
                    backupservicio.setString(1, aServicios.get(se).getNombre());
                    backupservicio.setString(2, aServicios.get(se).getDescripcion());
                    backupservicio.setInt(3, aServicios.get(se).getPrecio());
                    backupservicio.executeUpdate();

                    ResultSet generatedIdservicio = backupservicio.getGeneratedKeys();
                    if (generatedIdservicio.next()) {
                        aServicios.get(se).setCodigo_servicio(generatedIdservicio.getInt(1));
                    }
                }

            }

            //Ocupacion y servicios Adultos
            for (int j = 0; j < reserva.getaPasajerosAdultos().size(); j++) {
                //OcupacionVuelta
                PreparedStatement sentenciaocuida;
                sentenciaocuida = conex.prepareStatement("INSERT INTO ocupacion_backup (RESERVA, TIPO, PASAJERO, ASIENTO) VALUES(?, 'VUELTA', ?, ?)", Statement.RETURN_GENERATED_KEYS);
                sentenciaocuida.setString(1, reserva.getCod_reserva());
                sentenciaocuida.setInt(2, reserva.getaPasajerosAdultos().get(j).getCodigo_pasajero());
                sentenciaocuida.setInt(3, reserva.getaPasajerosAdultos().get(j).getAsiento_vuelta());
                sentenciaocuida.executeUpdate();
                int idOcuida = 0;
                ResultSet generatedIdocuida = sentenciaocuida.getGeneratedKeys();
                if (generatedIdocuida.next()) {
                    idOcuida = generatedIdocuida.getInt(1);
                }
                //Servicios Ocupacion Vuelta
                int codServBackupA = 0;
                PreparedStatement sentenciaservida;
                if (!reserva.getaPasajerosAdultos().get(j).getaServiciosVuelta().isEmpty()) {
                    for (int o = 0; o < reserva.getaPasajerosAdultos().get(j).getaServiciosVuelta().size(); o++) {
                        for (int zx = 0; zx < aServicios.size(); zx++) {
                            if (reserva.getaPasajerosAdultos().get(j).getaServiciosVuelta().get(o).getNombre().equals(aServicios.get(zx).getNombre())) {
                                codServBackupA = aServicios.get(zx).getCodigo_servicio();
                            }
                        }
                        sentenciaservida = conex.prepareStatement("INSERT INTO reserva_servicio_backup (COD_OCUPACION, COD_SERVICIO) VALUES(?, ?)");
                        sentenciaservida.setInt(1, idOcuida);
                        sentenciaservida.setInt(2, codServBackupA);
                        sentenciaservida.executeUpdate();
                    }
                }
                PreparedStatement borraro;
                borraro = conex.prepareStatement("DELETE FROM ocupacion WHERE RESERVA LIKE ? AND PASAJERO=? AND TIPO LIKE 'VUELTA'");
                borraro.setString(1, reserva.getCod_reserva());
                borraro.setInt(2, reserva.getaPasajerosAdultos().get(j).getCodigo_pasajero());
                borraro.executeUpdate();
            }
            //Ocupacion y servicios Niños
            if (!reserva.getaPasajerosNinos().isEmpty()) {
                for (int j = 0; j < reserva.getaPasajerosNinos().size(); j++) {
                    //OcupacionVuelta
                    PreparedStatement sentenciaocuida;
                    sentenciaocuida = conex.prepareStatement("INSERT INTO ocupacion_backup (RESERVA, TIPO, PASAJERO, ASIENTO) VALUES(?, 'VUELTA', ?, ?)", Statement.RETURN_GENERATED_KEYS);
                    sentenciaocuida.setString(1, reserva.getCod_reserva());
                    sentenciaocuida.setInt(2, reserva.getaPasajerosNinos().get(j).getCodigo_pasajero());
                    sentenciaocuida.setInt(3, reserva.getaPasajerosNinos().get(j).getAsiento_vuelta());
                    sentenciaocuida.executeUpdate();
                    int idOcuida = 0;
                    ResultSet generatedIdocuida = sentenciaocuida.getGeneratedKeys();
                    if (generatedIdocuida.next()) {
                        idOcuida = generatedIdocuida.getInt(1);
                    }
                    //Servicios Ocupacion Vuelta
                    int codServBackupN = 0;
                    PreparedStatement sentenciaservida;
                    if (!reserva.getaPasajerosNinos().get(j).getaServiciosVuelta().isEmpty()) {
                        for (int o = 0; o < reserva.getaPasajerosNinos().get(j).getaServiciosVuelta().size(); o++) {
                            for (int zx = 0; zx < aServicios.size(); zx++) {
                                if (reserva.getaPasajerosNinos().get(j).getaServiciosVuelta().get(o).getNombre().equals(aServicios.get(zx).getNombre())) {
                                    codServBackupN = aServicios.get(zx).getCodigo_servicio();
                                }
                            }
                            sentenciaservida = conex.prepareStatement("INSERT INTO reserva_servicio_backup (COD_OCUPACION, COD_SERVICIO) VALUES(?, ?)");
                            sentenciaservida.setInt(1, idOcuida);
                            sentenciaservida.setInt(2, codServBackupN);
                            sentenciaservida.executeUpdate();
                        }
                    }
                    PreparedStatement borraro;
                    borraro = conex.prepareStatement("DELETE FROM ocupacion WHERE RESERVA LIKE ? AND PASAJERO=? AND TIPO LIKE 'VUELTA'");
                    borraro.setString(1, reserva.getCod_reserva());
                    borraro.setInt(2, reserva.getaPasajerosNinos().get(j).getCodigo_pasajero());
                    borraro.executeUpdate();
                }
            }

            //Borrando
            PreparedStatement borrarreserva;
            borrarreserva = conex.prepareStatement("DELETE FROM reserva WHERE COD_RESERVA LIKE ?");
            borrarreserva.setString(1, reserva.getCod_reserva());
            borrarreserva.executeUpdate();

            conex.commit();
            return true;
        } catch (SQLException ex) {
            conex.rollback();
            return false;
        }
    }

    public Boolean borrarVuelo(Connection conex, Vuelo vuelo) throws SQLException {
        try {
            conex.setAutoCommit(false);

            PreparedStatement vueloback = conex.prepareStatement("INSERT INTO vuelo_backup (CODIGO_VUELO, CONEXION, FECHA, HORA_SALIDA, HORA_LLEGADA, DURACION, ASIENTOS_LIBRES, NUMERO, AVION, PRECIO) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            vueloback.setInt(1, vuelo.getCodigo_vuelo());
            vueloback.setInt(2, vuelo.getConexion());
            vueloback.setString(3, vuelo.getFecha().toString());
            vueloback.setString(4, vuelo.getHora_salida().toString());
            vueloback.setString(5, vuelo.getHora_llegada().toString());
            vueloback.setString(6, vuelo.getDuracion().toString());
            vueloback.setInt(7, vuelo.getAsientos_libres());
            vueloback.setString(8, vuelo.getNum_vuelo());
            vueloback.setString(9, vuelo.getAvion());
            vueloback.setInt(10, vuelo.getPrecio());
            vueloback.executeUpdate();

            PreparedStatement borrarvuelo = conex.prepareStatement("DELETE FROM vuelo WHERE CODIGO_VUELO=?");
            borrarvuelo.setInt(1, vuelo.getCodigo_vuelo());
            borrarvuelo.executeUpdate();

            conex.commit();
            return true;
        } catch (SQLException ex) {
            conex.rollback();
            return false;
        }
    }

    public int sacarConexion(Connection conex, String or, String des) throws SQLException {
        int conexion = 0;
        String iataOr = "";
        String iataDes = "";

        PreparedStatement sent1 = conex.prepareStatement("SELECT aeropuerto.CODIGO_IATA FROM aeropuerto WHERE aeropuerto.CIUDAD = ?");
        sent1.setString(1, or);
        ResultSet res1 = sent1.executeQuery();
        while (res1.next()) {
            iataOr = res1.getString("CODIGO_IATA");
        }

        PreparedStatement sent2 = conex.prepareStatement("SELECT aeropuerto.CODIGO_IATA FROM aeropuerto WHERE aeropuerto.CIUDAD = ?");
        sent2.setString(1, des);
        ResultSet res2 = sent2.executeQuery();
        while (res2.next()) {
            iataDes = res2.getString("CODIGO_IATA");
        }

        PreparedStatement sent3 = conex.prepareStatement("SELECT conexion.CODIGO_CONEXION FROM conexion WHERE conexion.ORIGEN LIKE ? AND conexion.DESTINO LIKE ?");
        sent3.setString(1, iataOr);
        sent3.setString(2, iataDes);
        ResultSet res3 = sent3.executeQuery();
        while (res3.next()) {
            conexion = res3.getInt("CODIGO_CONEXION");
        }

        return conexion;
    }

    public ArrayList<Vuelo> sacarVuelosEst(Connection conex, int conexion, LocalDate fechaI, LocalDate fechaF) throws SQLException {
        ArrayList<Vuelo> vuelos = new ArrayList();

        PreparedStatement sentencia = conex.prepareStatement("SELECT vuelo_backup.* FROM vuelo_backup WHERE vuelo_backup.FECHA BETWEEN ? AND ? AND vuelo_backup.CONEXION = ?");
        sentencia.setString(1, fechaI.toString());
        sentencia.setString(2, fechaF.toString());
        sentencia.setInt(3, conexion);

        ResultSet res = sentencia.executeQuery();

        while (res.next()) {
            Vuelo v1 = new Vuelo();
            v1.setCodigo_vuelo(res.getInt("CODIGO_VUELO"));
            v1.setAsientos_libres(res.getInt("ASIENTOS_LIBRES"));
            v1.setConexion(res.getInt("CONEXION"));
            v1.setFecha(LocalDate.parse(res.getString("FECHA")));
            v1.setHora_llegada(LocalTime.parse(res.getString("HORA_LLEGADA")));
            v1.setHora_salida(LocalTime.parse(res.getString("HORA_SALIDA")));
            v1.setDuracion(LocalTime.parse(res.getString("DURACION")));
            v1.setNum_vuelo(res.getString("NUMERO"));
            v1.setPrecio(res.getInt("PRECIO"));
            v1.setAvion(res.getString("AVION"));
            vuelos.add(v1);
        }

        return vuelos;
    }

    public Reserva montarReservaEst(Connection conex, String cod_reserva) throws SQLException {
        Reserva r1 = new Reserva();

        //Sacar datos para reserva.
        int cod_vuelo_ida = 0;
        int cod_vuelo_vuelta = 0;

        PreparedStatement sentenciareserva = conex.prepareStatement("SELECT * FROM reserva_backup WHERE COD_RESERVA LIKE ?");
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

        //Pasajeros Adultos
        ArrayList<Pasajero> aAdultos = new ArrayList();

        PreparedStatement sentenciapasajerosa = conex.prepareStatement("SELECT DISTINCT p.* FROM reserva_backup r, ocupacion_backup o, pasajero_backup p WHERE o.RESERVA LIKE ? AND o.PASAJERO=p.CODIGO_PASAJERO AND p.TIPO LIKE 'adulto'");
        sentenciapasajerosa.setString(1, cod_reserva);

        ResultSet resultado7 = sentenciapasajerosa.executeQuery();

        while (resultado7.next()) {
            Pasajero p1 = new Pasajero();
            p1.setApellidos(resultado7.getString("APELLIDOS"));
            p1.setCodigo_pasajero(resultado7.getInt("CODIGO_PASAJERO"));
            p1.setFecha_caducidad(LocalDate.parse(resultado7.getString("CADUCIDAD_NIF")));
            p1.setFecha_nac(LocalDate.parse(resultado7.getString("FECHA_NAC")));
            p1.setNif(resultado7.getString("NIF"));
            p1.setNombre(resultado7.getString("NOMBRE"));
            p1.setTipo(resultado7.getString("TIPO"));
            p1.setTratamiento(resultado7.getString("TRATAMIENTO"));

            PreparedStatement sentenciacodocu = conex.prepareStatement("SELECT o.* FROM reserva_backup r, ocupacion_backup o, pasajero_backup p WHERE o.RESERVA LIKE ? AND o.PASAJERO=? AND o.TIPO LIKE 'IDA' GROUP BY o.CODIGO_OCUPACION");
            sentenciacodocu.setString(1, cod_reserva);
            sentenciacodocu.setInt(2, p1.getCodigo_pasajero());
            ResultSet res = sentenciacodocu.executeQuery();
            while (res.next()) {
                p1.setCodocuida(res.getInt("CODIGO_OCUPACION"));
            }

            PreparedStatement sentenciacodocu2 = conex.prepareStatement("SELECT o.* FROM reserva_backup r, ocupacion_backup o, pasajero_backup p WHERE o.RESERVA LIKE ? AND o.PASAJERO=? AND o.TIPO LIKE 'VUELTA' GROUP BY o.CODIGO_OCUPACION");
            sentenciacodocu2.setString(1, cod_reserva);
            sentenciacodocu2.setInt(2, p1.getCodigo_pasajero());
            ResultSet res2 = sentenciacodocu2.executeQuery();
            while (res2.next()) {
                p1.setCodocuvuelta(res2.getInt("CODIGO_OCUPACION"));
            }

            aAdultos.add(p1);
        }

        //Pasajeros Niños
        ArrayList<Pasajero> aNinos = new ArrayList();

        PreparedStatement sentenciapasajerosn = conex.prepareStatement("SELECT DISTINCT p.* FROM reserva_backup r, ocupacion_backup o, pasajero_backup p WHERE o.RESERVA LIKE ? AND o.PASAJERO=p.CODIGO_PASAJERO AND p.TIPO LIKE 'niño'");
        sentenciapasajerosn.setString(1, cod_reserva);

        ResultSet resultado8 = sentenciapasajerosn.executeQuery();

        while (resultado8.next()) {
            Pasajero p1 = new Pasajero();
            p1.setApellidos(resultado8.getString("APELLIDOS"));
            p1.setCodigo_pasajero(resultado8.getInt("CODIGO_PASAJERO"));
            p1.setFecha_caducidad(LocalDate.parse(resultado8.getString("CADUCIDAD_NIF")));
            p1.setFecha_nac(LocalDate.parse(resultado8.getString("FECHA_NAC")));
            p1.setNif(resultado8.getString("NIF"));
            p1.setNombre(resultado8.getString("NOMBRE"));
            p1.setTipo(resultado8.getString("TIPO"));
            p1.setTratamiento(resultado8.getString("TRATAMIENTO"));

            PreparedStatement sentenciacodocu = conex.prepareStatement("SELECT o.* FROM reserva_backup r, ocupacion_backup o, pasajero_backup p WHERE o.RESERVA LIKE ? AND o.PASAJERO=? AND o.TIPO LIKE 'IDA' GROUP BY o.CODIGO_OCUPACION");
            sentenciacodocu.setString(1, cod_reserva);
            sentenciacodocu.setInt(2, p1.getCodigo_pasajero());
            ResultSet res = sentenciacodocu.executeQuery();
            while (res.next()) {
                p1.setCodocuida(res.getInt("CODIGO_OCUPACION"));
            }

            PreparedStatement sentenciacodocu2 = conex.prepareStatement("SELECT o.* FROM reserva_backup r, ocupacion_backup o, pasajero_backup p WHERE o.RESERVA LIKE ? AND o.PASAJERO=? AND o.TIPO LIKE 'VUELTA' GROUP BY o.CODIGO_OCUPACION");
            sentenciacodocu2.setString(1, cod_reserva);
            sentenciacodocu2.setInt(2, p1.getCodigo_pasajero());
            ResultSet res2 = sentenciacodocu2.executeQuery();
            while (res2.next()) {
                p1.setCodocuvuelta(res2.getInt("CODIGO_OCUPACION"));
            }

            aNinos.add(p1);
        }

        //Pasajeros Bebes
        ArrayList<Bebe> aBebes = new ArrayList();
        Bebe b1 = new Bebe();

        PreparedStatement sentenciapasajerosb = conex.prepareStatement("SELECT b.* FROM bebe_backup b, tutor_backup t WHERE b.CODIGO_BEBE=t.COD_BEBE AND t.COD_RESERVA LIKE ? GROUP BY b.CODIGO_BEBE");
        sentenciapasajerosb.setString(1, cod_reserva);

        ResultSet resultado9 = sentenciapasajerosb.executeQuery();

        while (resultado9.next()) {
            b1.setApellidos(resultado9.getString("APELLIDOS"));
            b1.setCod_bebe(resultado9.getInt("CODIGO_BEBE"));
            b1.setFecha_nac(LocalDate.parse(resultado9.getString("FECHA_NAC")));
            b1.setNif(resultado9.getString("NIF"));
            b1.setNombre(resultado9.getString("NOMBRE"));
            aBebes.add(b1);
        }

        //Asientos Pasajeros Adultos
        if (!aAdultos.isEmpty()) {
            for (int i = 0; i < aAdultos.size(); i++) {
                int cod_pasajero = aAdultos.get(i).getCodigo_pasajero();

                PreparedStatement sentenciaasientoida = conex.prepareStatement("SELECT * FROM ocupacion_backup WHERE PASAJERO = ? AND RESERVA LIKE ? AND TIPO LIKE 'IDA'");
                sentenciaasientoida.setInt(1, cod_pasajero);
                sentenciaasientoida.setString(2, cod_reserva);

                ResultSet resultado10 = sentenciaasientoida.executeQuery();

                while (resultado10.next()) {
                    aAdultos.get(i).setAsiento_ida(resultado10.getInt("ASIENTO"));
                    aAdultos.get(i).setCodocuida(resultado10.getInt("CODIGO_OCUPACION"));
                }

                if (cod_vuelo_vuelta != 0) {
                    PreparedStatement sentenciaasientovuelta = conex.prepareStatement("SELECT * FROM ocupacion_backup WHERE PASAJERO = ? AND RESERVA LIKE ? AND TIPO LIKE 'VUELTA'");
                    sentenciaasientovuelta.setInt(1, cod_pasajero);
                    sentenciaasientovuelta.setString(2, cod_reserva);

                    ResultSet resultado11 = sentenciaasientovuelta.executeQuery();

                    while (resultado11.next()) {
                        aAdultos.get(i).setAsiento_vuelta(resultado11.getInt("ASIENTO"));
                        aAdultos.get(i).setCodocuvuelta(resultado11.getInt("CODIGO_OCUPACION"));
                    }
                }
            }
        }

        //Asientos Pasajeros Niños
        if (!aNinos.isEmpty()) {
            for (int u = 0; u < aNinos.size(); u++) {
                int cod_pasajero = aNinos.get(u).getCodigo_pasajero();

                PreparedStatement sentenciaasientonida = conex.prepareStatement("SELECT * FROM ocupacion_backup WHERE PASAJERO = ? AND RESERVA LIKE ? AND TIPO LIKE 'IDA'");
                sentenciaasientonida.setInt(1, cod_pasajero);
                sentenciaasientonida.setString(2, cod_reserva);

                ResultSet resultado12 = sentenciaasientonida.executeQuery();

                while (resultado12.next()) {
                    aNinos.get(u).setAsiento_ida(resultado12.getInt("ASIENTO"));
                    aNinos.get(u).setCodocuida(resultado12.getInt("CODIGO_OCUPACION"));
                }

                if (cod_vuelo_vuelta != 0) {
                    PreparedStatement sentenciaasientonvuelta = conex.prepareStatement("SELECT * FROM ocupacion_backup WHERE PASAJERO = ? AND RESERVA LIKE ? AND TIPO LIKE 'VUELTA'");
                    sentenciaasientonvuelta.setInt(1, cod_pasajero);
                    sentenciaasientonvuelta.setString(2, cod_reserva);

                    ResultSet resultado13 = sentenciaasientonvuelta.executeQuery();

                    while (resultado13.next()) {
                        aNinos.get(u).setAsiento_vuelta(resultado13.getInt("ASIENTO"));
                        aNinos.get(u).setCodocuvuelta(resultado13.getInt("CODIGO_OCUPACION"));
                    }
                }
            }
        }

        //Tutores
        if (!aBebes.isEmpty()) {
            for (int j = 0; j < aBebes.size(); j++) {
                PreparedStatement sentenciatutorida = conex.prepareStatement("SELECT DISTINCT p.* FROM bebe_backup b, pasajero_backup p, tutor_backup t WHERE t.COD_BEBE = ? AND t.COD_PASAJERO=p.CODIGO_PASAJERO AND t.COD_RESERVA LIKE ? AND t.TIPO LIKE 'IDA'");
                sentenciatutorida.setInt(1, aBebes.get(j).getCod_bebe());
                sentenciatutorida.setString(2, cod_reserva);

                ResultSet resultado14 = sentenciatutorida.executeQuery();

                while (resultado14.next()) {
                    int codpasaj = resultado14.getInt("CODIGO_PASAJERO");
                    for (int k = 0; k < aAdultos.size(); k++) {
                        if (aAdultos.get(k).getCodigo_pasajero() == codpasaj) {
                            aBebes.get(j).setTutor_ida(aAdultos.get(k));
                        }
                    }
                }

                if (cod_vuelo_vuelta != 0) {
                    PreparedStatement sentenciatutorvuelta = conex.prepareStatement("SELECT DISTINCT p.* FROM bebe_backup b, pasajero_backup p, tutor_backup t WHERE t.COD_BEBE = ? AND t.COD_PASAJERO=p.CODIGO_PASAJERO AND t.COD_RESERVA LIKE ? AND t.TIPO LIKE 'VUELTA'");
                    sentenciatutorvuelta.setInt(1, aBebes.get(j).getCod_bebe());
                    sentenciatutorvuelta.setString(2, cod_reserva);

                    ResultSet resultado15 = sentenciatutorvuelta.executeQuery();

                    while (resultado15.next()) {
                        int codpasaj = resultado15.getInt("CODIGO_PASAJERO");
                        for (int f = 0; f < aAdultos.size(); f++) {
                            if (aAdultos.get(f).getCodigo_pasajero() == codpasaj) {
                                aBebes.get(j).setTutor_vuelta(aAdultos.get(f));
                            }
                        }
                    }
                }
            }
        }

        //Servicios Pasajeros Adultos
        if (!aAdultos.isEmpty()) {
            for (int i2 = 0; i2 < aAdultos.size(); i2++) {
                ArrayList<Servicio> aServiciosIda = new ArrayList();
                PreparedStatement sentenciaservida = conex.prepareStatement("SELECT s.* FROM servicio_backup s, reserva_servicio_backup rs WHERE rs.COD_OCUPACION=(SELECT o.CODIGO_OCUPACION FROM ocupacion_backup o WHERE o.PASAJERO=? AND o.RESERVA LIKE ? AND o.TIPO LIKE 'IDA') AND s.CODIGO_SERVICIO=rs.COD_SERVICIO");
                sentenciaservida.setInt(1, aAdultos.get(i2).getCodigo_pasajero());
                sentenciaservida.setString(2, cod_reserva);

                ResultSet resultado16 = sentenciaservida.executeQuery();

                while (resultado16.next()) {
                    Servicio s1 = new Servicio();
                    s1.setCodigo_servicio(resultado16.getInt("CODIGO_SERVICIO"));
                    s1.setDescripcion(resultado16.getString("DESCRIPCION"));
                    s1.setNombre(resultado16.getString("NOMBRE"));
                    s1.setPrecio(resultado16.getInt("PRECIO"));
                    aServiciosIda.add(s1);
                }

                aAdultos.get(i2).setaServiciosIda(aServiciosIda);

                if (cod_vuelo_vuelta != 0) {
                    ArrayList<Servicio> aServiciosVuelta = new ArrayList();
                    PreparedStatement sentenciaservvuelta = conex.prepareStatement("SELECT s.* FROM servicio_backup s, reserva_servicio_backup rs WHERE rs.COD_OCUPACION=(SELECT o.CODIGO_OCUPACION FROM ocupacion_backup o WHERE o.PASAJERO=? AND o.RESERVA LIKE ? AND o.TIPO LIKE 'VUELTA') AND s.CODIGO_SERVICIO=rs.COD_SERVICIO");
                    sentenciaservvuelta.setInt(1, aAdultos.get(i2).getCodigo_pasajero());
                    sentenciaservvuelta.setString(2, cod_reserva);

                    ResultSet resultado17 = sentenciaservvuelta.executeQuery();

                    while (resultado17.next()) {
                        Servicio s1 = new Servicio();
                        s1.setCodigo_servicio(resultado17.getInt("CODIGO_SERVICIO"));
                        s1.setDescripcion(resultado17.getString("DESCRIPCION"));
                        s1.setNombre(resultado17.getString("NOMBRE"));
                        s1.setPrecio(resultado17.getInt("PRECIO"));
                        aServiciosVuelta.add(s1);
                    }

                    aAdultos.get(i2).setaServiciosVuelta(aServiciosVuelta);
                }
            }
        }

        //Servicios Pasajeros Niños
        if (!aNinos.isEmpty()) {
            for (int i2 = 0; i2 < aNinos.size(); i2++) {
                ArrayList<Servicio> aServiciosIda = new ArrayList();
                PreparedStatement sentenciaservida = conex.prepareStatement("SELECT s.* FROM servicio_backup s, reserva_servicio_backup rs WHERE rs.COD_OCUPACION=(SELECT o.CODIGO_OCUPACION FROM ocupacion_backup o WHERE o.PASAJERO=? AND o.RESERVA LIKE ? AND o.TIPO LIKE 'IDA') AND s.CODIGO_SERVICIO=rs.COD_SERVICIO");
                sentenciaservida.setInt(1, aNinos.get(i2).getCodigo_pasajero());
                sentenciaservida.setString(2, cod_reserva);

                ResultSet resultado18 = sentenciaservida.executeQuery();

                while (resultado18.next()) {
                    Servicio s1 = new Servicio();
                    s1.setCodigo_servicio(resultado18.getInt("CODIGO_SERVICIO"));
                    s1.setDescripcion(resultado18.getString("DESCRIPCION"));
                    s1.setNombre(resultado18.getString("NOMBRE"));
                    s1.setPrecio(resultado18.getInt("PRECIO"));
                    aServiciosIda.add(s1);
                }

                aNinos.get(i2).setaServiciosIda(aServiciosIda);

                if (cod_vuelo_vuelta != 0) {
                    ArrayList<Servicio> aServiciosVuelta = new ArrayList();
                    PreparedStatement sentenciaservvuelta = conex.prepareStatement("SELECT s.* FROM servicio_backup s, reserva_servicio_backup rs WHERE rs.COD_OCUPACION=(SELECT o.CODIGO_OCUPACION FROM ocupacion_backup o WHERE o.PASAJERO=? AND o.RESERVA LIKE ? AND o.TIPO LIKE 'VUELTA') AND s.CODIGO_SERVICIO=rs.COD_SERVICIO");
                    sentenciaservvuelta.setInt(1, aNinos.get(i2).getCodigo_pasajero());
                    sentenciaservvuelta.setString(2, cod_reserva);

                    ResultSet resultado19 = sentenciaservvuelta.executeQuery();

                    while (resultado19.next()) {
                        Servicio s1 = new Servicio();
                        s1.setCodigo_servicio(resultado19.getInt("CODIGO_SERVICIO"));
                        s1.setDescripcion(resultado19.getString("DESCRIPCION"));
                        s1.setNombre(resultado19.getString("NOMBRE"));
                        s1.setPrecio(resultado19.getInt("PRECIO"));
                        aServiciosVuelta.add(s1);
                    }

                    aNinos.get(i2).setaServiciosVuelta(aServiciosVuelta);
                }
            }
        }

        r1.setaPasajerosAdultos(aAdultos);
        r1.setaPasajerosNinos(aNinos);
        r1.setaPasajerosBebes(aBebes);

        return r1;
    }

    public ArrayList<Reserva> sacarReservasEst(Connection conex, Vuelo vuelo) throws SQLException {
        ArrayList<Reserva> aReservas = new ArrayList();

        PreparedStatement sentencia = conex.prepareStatement("SELECT * FROM reserva_backup WHERE COD_VUELO_IDA=? OR COD_VUELO_VUELTA=?");
        sentencia.setInt(1, vuelo.getCodigo_vuelo());
        sentencia.setInt(2, vuelo.getCodigo_vuelo());

        ResultSet res = sentencia.executeQuery();

        while (res.next()) {
            aReservas.add(montarReservaEst(conex, res.getString("COD_RESERVA")));
        }

        return aReservas;
    }
}
