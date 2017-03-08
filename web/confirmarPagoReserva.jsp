<%-- 
    Document   : confirmarPagoReserva
    Created on : 02-jun-2016, 18:21:56
    Author     : Carlos
--%>

<%@page import= "Clases.*" %>
<%@page import= "java.util.ArrayList" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.util.Date;" %>
<%@ page import="java.text.SimpleDateFormat"%>
  
<%
   Date dNow = new Date();
   SimpleDateFormat ft = 
   new SimpleDateFormat ("dd/MM/yyyy");
   String currentDate = ft.format(dNow);
%>

<% Reserva reserva = (Reserva) session.getAttribute("reserva");%>

<!DOCTYPE HTML>
<html>
	<head>
		<title>AB Airline</title>
		<meta http-equiv="content-type" content="text/html; charset=utf-8" />
		<meta name="description" content="" />
		<meta name="keywords" content="" />
		<link href='http://fonts.googleapis.com/css?family=Roboto:400,100,300,700,500,900' rel='stylesheet' type='text/css'>
		<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>
		<script src="js/skel.min.js"></script>
		<script src="js/skel-panels.min.js"></script>
		<script src="js/init.js"></script>
                <script type="text/javascript" src="js/javascript.js?1234" charset="UTF-8"></script>
		<noscript>
			<link rel="stylesheet" href="css/skel-noscript.css" />
			<link rel="stylesheet" href="css/style.css" />
			<link rel="stylesheet" href="css/style-desktop.css" />
		</noscript>
                <link rel="stylesheet" href="jquery-ui-1.11.4.custom/jquery-ui.css">
                <script src="jquery-ui-1.11.4.custom/jquery-ui.js"></script>
                <script src="jquery-ui-1.11.4.custom/datepicker-es.js"></script>
	</head>
        <body>

	<!-- Header -->
		<div id="header">
			<div id="nav-wrapper"> 
				<!-- Nav -->
				<nav id="nav">
					<ul>
						<li><a href="index.jsp">Inicio</a></li>
						<li><a href="finalizarVuelo.jsp">Mis Vuelos</a></li>
						<li><a href="facturacion.jsp">Facturacion</a></li>
					</ul>
				</nav>
			</div>
			<div class="container"> 
				
				<!-- Logo -->
				<div id="logo">
					<h1><a href="#">AB Airline</a></h1>
				</div>
			</div>
		</div>
	<!-- Header --> 

	<!-- Main -->
		<div id="main">
			<div id="content" class="container">
				<section>
                                    <header>
                                        <h2>Datos de la Reserva</h2>
                                    </header>
                                    <div id="contenidoresumenv">
                                        <form action="controladorConfirmarPago" name="form14">
                                            <span><b>Codigo Reserva: </b><% out.print(reserva.getCod_reserva()); %>&nbsp;&nbsp;</span>
                                            <span><b>Fecha: </b><% out.print(currentDate); %>&nbsp;&nbsp;</span>
                                            <span><b>Numero de viajeros: </b><% out.print(reserva.getNum_viajeros()); %>&nbsp;&nbsp;</span>
                                            <span><b>Precio Total: </b><% out.print(reserva.getPrecio_total()); %></span>
                                            <div class="acordeon">
                                                <h3>Vuelos</h3>
                                                <div class="acordeon">
                                                    <h3>Vuelo ida</h3>
                                                    <div>
                                                        <table class="resumen">
                                                            <tr>
                                                                <th>
                                                                    Origen
                                                                </th>
                                                                <th>
                                                                    Destino
                                                                </th>
                                                                <th>
                                                                    Fecha
                                                                </th>
                                                                <th>
                                                                    Hora Salida
                                                                </th>
                                                                <th>
                                                                    Hora Llegada
                                                                </th>
                                                                <th>
                                                                    Precio
                                                                </th>
                                                            </tr>
                                                            <tr>
                                                                <td>
                                                                    <% out.print(reserva.getVuelo_ida().getOrigen().getCiudad()); %>
                                                                </td>
                                                                <td>
                                                                    <% out.print(reserva.getVuelo_ida().getDestino().getCiudad()); %>
                                                                </td>
                                                                <td>
                                                                    <% out.print(reserva.getVuelo_ida().getFecha()); %>
                                                                </td>
                                                                <td>
                                                                    <% out.print(reserva.getVuelo_ida().getHora_salida()); %>
                                                                </td>
                                                                <td>
                                                                    <% out.print(reserva.getVuelo_ida().getHora_llegada()); %>
                                                                </td>
                                                                <td>
                                                                    <% out.print(reserva.getVuelo_ida().getPrecio()); %>
                                                                </td>
                                                            </tr>
                                                        </table>
                                                    </div>
                                                    <% if(reserva.getVuelo_vuelta()!=null){%>
                                                        <h3>Vuelo vuelta</h3>
                                                        <div>
                                                            <table class="resumen">
                                                                <tr>
                                                                    <th>
                                                                        Origen
                                                                    </th>
                                                                    <th>
                                                                        Destino
                                                                    </th>
                                                                    <th>
                                                                        Fecha
                                                                    </th>
                                                                    <th>
                                                                        Hora Salida
                                                                    </th>
                                                                    <th>
                                                                        Hora Llegada
                                                                    </th>
                                                                    <th>
                                                                        Precio
                                                                    </th>
                                                                </tr>
                                                                <tr>
                                                                    <td>
                                                                        <% out.print(reserva.getVuelo_vuelta().getOrigen().getCiudad()); %>
                                                                    </td>
                                                                    <td>
                                                                        <% out.print(reserva.getVuelo_vuelta().getDestino().getCiudad()); %>
                                                                    </td>
                                                                    <td>
                                                                        <% out.print(reserva.getVuelo_vuelta().getFecha()); %>
                                                                    </td>
                                                                    <td>
                                                                        <% out.print(reserva.getVuelo_vuelta().getHora_salida()); %>
                                                                    </td>
                                                                    <td>
                                                                        <% out.print(reserva.getVuelo_vuelta().getHora_llegada()); %>
                                                                    </td>
                                                                    <td>
                                                                        <% out.print(reserva.getVuelo_vuelta().getPrecio()); %>
                                                                    </td>
                                                                </tr>
                                                            </table>
                                                        </div>
                                                    <% }%>
                                                </div>
                                                <h3>Pasajeros</h3>
                                                <div class="acordeon">
                                                    <% for(int i=0; i< reserva.getaPasajerosAdultos().size(); i++){%>
                                                        <h3><% out.print(reserva.getaPasajerosAdultos().get(i).getNombre()); %> <% out.print(reserva.getaPasajerosAdultos().get(i).getApellidos()); %></h3>
                                                        <div>
                                                            <table class="resumen">
                                                                <tr>
                                                                    <th>
                                                                        DNI
                                                                    </th>
                                                                    <th>
                                                                        Fecha Nacimiento
                                                                    </th>
                                                                    <th>
                                                                        Caducidad DNI
                                                                    </th>
                                                                    <th>
                                                                        Tipo
                                                                    </th>
                                                                </tr>
                                                                <tr>
                                                                    <td>
                                                                        <% out.print(reserva.getaPasajerosAdultos().get(i).getNif()); %>
                                                                    </td>
                                                                    <td>
                                                                        <% out.print(reserva.getaPasajerosAdultos().get(i).getFecha_nac()); %>
                                                                    </td>
                                                                    <td>
                                                                        <% out.print(reserva.getaPasajerosAdultos().get(i).getFecha_caducidad()); %>
                                                                    </td>
                                                                    <td>
                                                                        <% out.print(reserva.getaPasajerosAdultos().get(i).getTipo()); %>
                                                                    </td>
                                                                </tr>
                                                            </table>
                                                            <% if(reserva.getaPasajerosAdultos().get(i).getBebe()!=null){%>
                                                                <h3>Datos Bebe</h3>
                                                                <div>
                                                                    <table class="resumen">
                                                                        <tr>
                                                                            <th>
                                                                                Nombre
                                                                            </th>
                                                                            <th>
                                                                                Apellidos
                                                                            </th>
                                                                            <th>
                                                                                DNI
                                                                            </th>
                                                                            <th>
                                                                                Fecha Nacimiento
                                                                            </th>
                                                                        </tr>
                                                                        <tr>
                                                                            <td>
                                                                                <% out.print(reserva.getaPasajerosAdultos().get(i).getBebe().getNombre()); %>
                                                                            </td>
                                                                            <td>
                                                                                <% out.print(reserva.getaPasajerosAdultos().get(i).getBebe().getApellidos()); %>
                                                                            </td>
                                                                            <td>
                                                                                <% out.print(reserva.getaPasajerosAdultos().get(i).getBebe().getNif()); %>
                                                                            </td>
                                                                            <td>
                                                                                <% out.print(reserva.getaPasajerosAdultos().get(i).getBebe().getFecha_nac()); %>
                                                                            </td>
                                                                        </tr>
                                                                    </table>
                                                                </div>
                                                            <% }%>
                                                        </div>
                                                    <% }%>
                                                    <% for(int i=0; i< reserva.getaPasajerosNinos().size(); i++){%>
                                                        <h3><% out.print(reserva.getaPasajerosNinos().get(i).getNombre()); %> <% out.print(reserva.getaPasajerosNinos().get(i).getApellidos()); %></h3>
                                                        <div>
                                                            <table class="resumen">
                                                                <tr>
                                                                    <th>
                                                                        DNI
                                                                    </th>
                                                                    <th>
                                                                        Fecha Nacimiento
                                                                    </th>
                                                                    <th>
                                                                        Caducidad DNI
                                                                    </th>
                                                                    <th>
                                                                        Tipo
                                                                    </th>
                                                                </tr>
                                                                <tr>
                                                                    <td>
                                                                        <% out.print(reserva.getaPasajerosNinos().get(i).getNif()); %>
                                                                    </td>
                                                                    <td>
                                                                        <% out.print(reserva.getaPasajerosNinos().get(i).getFecha_nac()); %>
                                                                    </td>
                                                                    <td>
                                                                        <% out.print(reserva.getaPasajerosNinos().get(i).getFecha_caducidad()); %>
                                                                    </td>
                                                                    <td>
                                                                        <% out.print(reserva.getaPasajerosNinos().get(i).getTipo()); %>
                                                                    </td>
                                                                </tr>
                                                            </table>
                                                        </div>
                                                    <% }%>
                                                </div>
                                                <h3>Servicios</h3>
                                                <div>
                                                    <% for(int j=0; j< reserva.getaPasajerosAdultos().size(); j++){%>
                                                        <h3>Servicios de <% out.print(reserva.getaPasajerosAdultos().get(j).getNombre()); %> <% out.print(reserva.getaPasajerosAdultos().get(j).getApellidos()); %></h3>
                                                        <div class="acordeon">
                                                            <h3>Servicios de ida</h3>
                                                            <div>
                                                                <table class="resumen">
                                                                    <tr>
                                                                        <th>
                                                                            Nombre
                                                                        </th>
                                                                        <th>
                                                                            Precio
                                                                        </th>
                                                                    </tr>
                                                                    <% for(int u=0; u<reserva.getaPasajerosAdultos().get(j).getaServiciosIda().size(); u++){%>
                                                                    <tr>
                                                                        <td>
                                                                            <% out.print(reserva.getaPasajerosAdultos().get(j).getaServiciosIda().get(u).getNombre()); %>
                                                                        </td>
                                                                        <td>
                                                                            <% out.print(reserva.getaPasajerosAdultos().get(j).getaServiciosIda().get(u).getPrecio()); %>
                                                                        </td>
                                                                    </tr>
                                                                    <% }%>
                                                                </table>
                                                            </div>
                                                            <% if(!reserva.getaPasajerosAdultos().get(j).getaServiciosVuelta().isEmpty()){%>
                                                                <h3>Servicios de vuelta</h3>
                                                                <div>
                                                                    <table class="resumen">
                                                                        <tr>
                                                                            <th>
                                                                                Nombre
                                                                            </th>
                                                                            <th>
                                                                                Precio
                                                                            </th>
                                                                        </tr>
                                                                        <% for(int x=0; x<reserva.getaPasajerosAdultos().get(j).getaServiciosVuelta().size(); x++){%>
                                                                        <tr>
                                                                            <td>
                                                                                <% out.print(reserva.getaPasajerosAdultos().get(j).getaServiciosVuelta().get(x).getNombre()); %>
                                                                            </td>
                                                                            <td>
                                                                                <% out.print(reserva.getaPasajerosAdultos().get(j).getaServiciosVuelta().get(x).getPrecio()); %>
                                                                            </td>
                                                                        </tr>
                                                                        <% }%>
                                                                    </table>
                                                                </div>
                                                            <% }%>
                                                        </div>
                                                    <% }%>
                                                    <% if(reserva.getaPasajerosNinos()!=null){%>
                                                        <% for(int f=0; f< reserva.getaPasajerosNinos().size(); f++){%>
                                                            <h3>Servicios de <% out.print(reserva.getaPasajerosNinos().get(f).getNombre()); %> <% out.print(reserva.getaPasajerosNinos().get(f).getApellidos()); %></h3>
                                                            <div class="acordeon">
                                                                <h3>Servicios de ida</h3>
                                                                <div>
                                                                    <table class="resumen">
                                                                        <tr>
                                                                            <th>
                                                                                Nombre
                                                                            </th>
                                                                            <th>
                                                                                Precio
                                                                            </th>
                                                                        </tr>
                                                                        <% for(int s=0; s<reserva.getaPasajerosNinos().get(f).getaServiciosIda().size(); s++){%>
                                                                        <tr>
                                                                            <td>
                                                                                <% out.print(reserva.getaPasajerosNinos().get(f).getaServiciosIda().get(s).getNombre()); %>
                                                                            </td>
                                                                            <td>
                                                                                <% out.print(reserva.getaPasajerosNinos().get(f).getaServiciosIda().get(s).getPrecio()); %>
                                                                            </td>
                                                                        </tr>
                                                                        <% }%>
                                                                    </table>
                                                                </div>
                                                                <% if(!reserva.getaPasajerosNinos().get(f).getaServiciosVuelta().isEmpty()){%>
                                                                    <h3>Servicios de vuelta</h3>
                                                                    <div>
                                                                        <table class="resumen">
                                                                            <tr>
                                                                                <th>
                                                                                    Nombre
                                                                                </th>
                                                                                <th>
                                                                                    Precio
                                                                                </th>
                                                                            </tr>
                                                                            <% for(int z=0; z<reserva.getaPasajerosNinos().get(f).getaServiciosVuelta().size(); z++){%>
                                                                            <tr>
                                                                                <td>
                                                                                    <% out.print(reserva.getaPasajerosNinos().get(f).getaServiciosVuelta().get(z).getNombre()); %>
                                                                                </td>
                                                                                <td>
                                                                                    <% out.print(reserva.getaPasajerosNinos().get(f).getaServiciosVuelta().get(z).getPrecio()); %>
                                                                                </td>
                                                                            </tr>
                                                                            <% }%>
                                                                        </table>
                                                                    </div>
                                                                <% }%>
                                                            </div>
                                                        <% }%>
                                                    <% }%>
                                                </div>
                                                <h3>Pagador y Tarjeta</h3>
                                                <div class="acordeon">
                                                    <h3>Pagador</h3>
                                                    <div>
                                                        <table class="resumen">
                                                            <tr>
                                                                <th>
                                                                    Nombre
                                                                </th>
                                                                <th>
                                                                    Apellidos
                                                                </th>
                                                                <th>
                                                                    DNI
                                                                </th>
                                                                <th>
                                                                    Fecha Nacimiento
                                                                </th>
                                                                <th>
                                                                    Población
                                                                </th>
                                                                <th>
                                                                    Dirección
                                                                </th>
                                                            </tr>
                                                            <tr>
                                                                <td>
                                                                    <% out.print(reserva.getPagador().getNombre()); %>
                                                                </td>
                                                                <td>
                                                                    <% out.print(reserva.getPagador().getApellidos()); %>
                                                                </td>
                                                                <td>
                                                                    <% out.print(reserva.getPagador().getNif()); %>
                                                                </td>
                                                                <td>
                                                                    <% out.print(reserva.getPagador().getFecha_nac()); %>
                                                                </td>
                                                                <td>
                                                                    <% out.print(reserva.getPagador().getPoblacion()); %>
                                                                </td>
                                                                <td>
                                                                    <% out.print(reserva.getPagador().getDireccion()); %>
                                                                </td>
                                                            </tr>
                                                        </table>
                                                    </div>
                                                    <h3>Tarjeta</h3>
                                                    <div>
                                                        <table class="resumen">
                                                            <tr>
                                                                <th>
                                                                    Número Tarjeta
                                                                </th>
                                                                <th>
                                                                    Caducidad
                                                                </th>
                                                                <th>
                                                                    Numero Usos
                                                                </th>
                                                            </tr>
                                                            <tr>
                                                                <td>
                                                                    <% out.print(reserva.getTarjeta().getNum_tarjeta()); %>
                                                                </td>
                                                                <td>
                                                                    <% out.print(reserva.getTarjeta().getCaducidad()); %>
                                                                </td>
                                                                <td>
                                                                    <% out.print(reserva.getTarjeta().getNum_usos()); %>
                                                                </td>
                                                            </tr>
                                                        </table>
                                                    </div>
                                                </div>
                                            </div>
                                            <button type="submit" class="accion" name="confirmar" value="confirmar">Confirmar pago</button> 
                                        </form>
                                    </div>
                                    <!-- DIV para imprimir el resumen -->
                                    <div id="contenidoresumen" style="visibility: hidden; display: none">
                                        <form action="controladorConfirmarPago" name="form14">
                                            <span><b>Codigo Reserva: </b><% out.print(reserva.getCod_reserva()); %>&nbsp;&nbsp;</span>
                                            <span><b>Fecha: </b><% out.print(currentDate); %>&nbsp;&nbsp;</span>
                                            <span><b>Numero de viajeros: </b><% out.print(reserva.getNum_viajeros()); %>&nbsp;&nbsp;</span>
                                            <span><b>Precio Total: </b><% out.print(reserva.getPrecio_total()); %></span>
                                            <div>
                                                <h3>Vuelos</h3>
                                                <div>
                                                    <h3>Vuelo ida</h3>
                                                    <div>
                                                        <table class="resumen">
                                                            <tr>
                                                                <th>
                                                                    Origen
                                                                </th>
                                                                <th>
                                                                    Destino
                                                                </th>
                                                                <th>
                                                                    Fecha
                                                                </th>
                                                                <th>
                                                                    Hora Salida
                                                                </th>
                                                                <th>
                                                                    Hora Llegada
                                                                </th>
                                                                <th>
                                                                    Precio
                                                                </th>
                                                            </tr>
                                                            <tr>
                                                                <td>
                                                                    <% out.print(reserva.getVuelo_ida().getOrigen().getCiudad()); %>
                                                                </td>
                                                                <td>
                                                                    <% out.print(reserva.getVuelo_ida().getDestino().getCiudad()); %>
                                                                </td>
                                                                <td>
                                                                    <% out.print(reserva.getVuelo_ida().getFecha()); %>
                                                                </td>
                                                                <td>
                                                                    <% out.print(reserva.getVuelo_ida().getHora_salida()); %>
                                                                </td>
                                                                <td>
                                                                    <% out.print(reserva.getVuelo_ida().getHora_llegada()); %>
                                                                </td>
                                                                <td>
                                                                    <% out.print(reserva.getVuelo_ida().getPrecio()); %>
                                                                </td>
                                                            </tr>
                                                        </table>
                                                    </div>
                                                    <% if(reserva.getVuelo_vuelta()!=null){%>
                                                        <h3>Vuelo vuelta</h3>
                                                        <div>
                                                            <table class="resumen">
                                                                <tr>
                                                                    <th>
                                                                        Origen
                                                                    </th>
                                                                    <th>
                                                                        Destino
                                                                    </th>
                                                                    <th>
                                                                        Fecha
                                                                    </th>
                                                                    <th>
                                                                        Hora Salida
                                                                    </th>
                                                                    <th>
                                                                        Hora Llegada
                                                                    </th>
                                                                    <th>
                                                                        Precio
                                                                    </th>
                                                                </tr>
                                                                <tr>
                                                                    <td>
                                                                        <% out.print(reserva.getVuelo_vuelta().getOrigen().getCiudad()); %>
                                                                    </td>
                                                                    <td>
                                                                        <% out.print(reserva.getVuelo_vuelta().getDestino().getCiudad()); %>
                                                                    </td>
                                                                    <td>
                                                                        <% out.print(reserva.getVuelo_vuelta().getFecha()); %>
                                                                    </td>
                                                                    <td>
                                                                        <% out.print(reserva.getVuelo_vuelta().getHora_salida()); %>
                                                                    </td>
                                                                    <td>
                                                                        <% out.print(reserva.getVuelo_vuelta().getHora_llegada()); %>
                                                                    </td>
                                                                    <td>
                                                                        <% out.print(reserva.getVuelo_vuelta().getPrecio()); %>
                                                                    </td>
                                                                </tr>
                                                            </table>
                                                        </div>
                                                    <% }%>
                                                </div>
                                                <h3>Pasajeros</h3>
                                                <div>
                                                    <% for(int i=0; i< reserva.getaPasajerosAdultos().size(); i++){%>
                                                        <h3><% out.print(reserva.getaPasajerosAdultos().get(i).getNombre()); %> <% out.print(reserva.getaPasajerosAdultos().get(i).getApellidos()); %></h3>
                                                        <div>
                                                            <table class="resumen">
                                                                <tr>
                                                                    <th>
                                                                        DNI
                                                                    </th>
                                                                    <th>
                                                                        Fecha Nacimiento
                                                                    </th>
                                                                    <th>
                                                                        Caducidad DNI
                                                                    </th>
                                                                    <th>
                                                                        Tipo
                                                                    </th>
                                                                </tr>
                                                                <tr>
                                                                    <td>
                                                                        <% out.print(reserva.getaPasajerosAdultos().get(i).getNif()); %>
                                                                    </td>
                                                                    <td>
                                                                        <% out.print(reserva.getaPasajerosAdultos().get(i).getFecha_nac()); %>
                                                                    </td>
                                                                    <td>
                                                                        <% out.print(reserva.getaPasajerosAdultos().get(i).getFecha_caducidad()); %>
                                                                    </td>
                                                                    <td>
                                                                        <% out.print(reserva.getaPasajerosAdultos().get(i).getTipo()); %>
                                                                    </td>
                                                                </tr>
                                                            </table>
                                                            <% if(reserva.getaPasajerosAdultos().get(i).getBebe()!=null){%>
                                                                <h3>Datos Bebe</h3>
                                                                <div>
                                                                    <table class="resumen">
                                                                        <tr>
                                                                            <th>
                                                                                Nombre
                                                                            </th>
                                                                            <th>
                                                                                Apellidos
                                                                            </th>
                                                                            <th>
                                                                                DNI
                                                                            </th>
                                                                            <th>
                                                                                Fecha Nacimiento
                                                                            </th>
                                                                        </tr>
                                                                        <tr>
                                                                            <td>
                                                                                <% out.print(reserva.getaPasajerosAdultos().get(i).getBebe().getNombre()); %>
                                                                            </td>
                                                                            <td>
                                                                                <% out.print(reserva.getaPasajerosAdultos().get(i).getBebe().getApellidos()); %>
                                                                            </td>
                                                                            <td>
                                                                                <% out.print(reserva.getaPasajerosAdultos().get(i).getBebe().getNif()); %>
                                                                            </td>
                                                                            <td>
                                                                                <% out.print(reserva.getaPasajerosAdultos().get(i).getBebe().getFecha_nac()); %>
                                                                            </td>
                                                                        </tr>
                                                                    </table>
                                                                </div>
                                                            <% }%>
                                                        </div>
                                                    <% }%>
                                                    <% for(int i=0; i< reserva.getaPasajerosNinos().size(); i++){%>
                                                        <h3><% out.print(reserva.getaPasajerosNinos().get(i).getNombre()); %> <% out.print(reserva.getaPasajerosNinos().get(i).getApellidos()); %></h3>
                                                        <div>
                                                            <table class="resumen">
                                                                <tr>
                                                                    <th>
                                                                        DNI
                                                                    </th>
                                                                    <th>
                                                                        Fecha Nacimiento
                                                                    </th>
                                                                    <th>
                                                                        Caducidad DNI
                                                                    </th>
                                                                    <th>
                                                                        Tipo
                                                                    </th>
                                                                </tr>
                                                                <tr>
                                                                    <td>
                                                                        <% out.print(reserva.getaPasajerosNinos().get(i).getNif()); %>
                                                                    </td>
                                                                    <td>
                                                                        <% out.print(reserva.getaPasajerosNinos().get(i).getFecha_nac()); %>
                                                                    </td>
                                                                    <td>
                                                                        <% out.print(reserva.getaPasajerosNinos().get(i).getFecha_caducidad()); %>
                                                                    </td>
                                                                    <td>
                                                                        <% out.print(reserva.getaPasajerosNinos().get(i).getTipo()); %>
                                                                    </td>
                                                                </tr>
                                                            </table>
                                                        </div>
                                                    <% }%>
                                                </div>
                                                <h3>Servicios</h3>
                                                <div>
                                                    <% for(int j=0; j< reserva.getaPasajerosAdultos().size(); j++){%>
                                                        <h3>Servicios de <% out.print(reserva.getaPasajerosAdultos().get(j).getNombre()); %> <% out.print(reserva.getaPasajerosAdultos().get(j).getApellidos()); %></h3>
                                                        <div class="acordeon">
                                                            <h3>Servicios de ida</h3>
                                                            <div>
                                                                <table class="resumen">
                                                                    <tr>
                                                                        <th>
                                                                            Nombre
                                                                        </th>
                                                                        <th>
                                                                            Precio
                                                                        </th>
                                                                    </tr>
                                                                    <% for(int u=0; u<reserva.getaPasajerosAdultos().get(j).getaServiciosIda().size(); u++){%>
                                                                    <tr>
                                                                        <td>
                                                                            <% out.print(reserva.getaPasajerosAdultos().get(j).getaServiciosIda().get(u).getNombre()); %>
                                                                        </td>
                                                                        <td>
                                                                            <% out.print(reserva.getaPasajerosAdultos().get(j).getaServiciosIda().get(u).getPrecio()); %>
                                                                        </td>
                                                                    </tr>
                                                                    <% }%>
                                                                </table>
                                                            </div>
                                                            <% if(!reserva.getaPasajerosAdultos().get(j).getaServiciosVuelta().isEmpty()){%>
                                                                <h3>Servicios de vuelta</h3>
                                                                <div>
                                                                    <table class="resumen">
                                                                        <tr>
                                                                            <th>
                                                                                Nombre
                                                                            </th>
                                                                            <th>
                                                                                Precio
                                                                            </th>
                                                                        </tr>
                                                                        <% for(int x=0; x<reserva.getaPasajerosAdultos().get(j).getaServiciosVuelta().size(); x++){%>
                                                                        <tr>
                                                                            <td>
                                                                                <% out.print(reserva.getaPasajerosAdultos().get(j).getaServiciosVuelta().get(x).getNombre()); %>
                                                                            </td>
                                                                            <td>
                                                                                <% out.print(reserva.getaPasajerosAdultos().get(j).getaServiciosVuelta().get(x).getPrecio()); %>
                                                                            </td>
                                                                        </tr>
                                                                        <% }%>
                                                                    </table>
                                                                </div>
                                                            <% }%>
                                                        </div>
                                                    <% }%>
                                                    <% if(reserva.getaPasajerosNinos()!=null){%>
                                                        <% for(int f=0; f< reserva.getaPasajerosNinos().size(); f++){%>
                                                            <h3>Servicios de <% out.print(reserva.getaPasajerosNinos().get(f).getNombre()); %> <% out.print(reserva.getaPasajerosNinos().get(f).getApellidos()); %></h3>
                                                            <div class="acordeon">
                                                                <h3>Servicios de ida</h3>
                                                                <div>
                                                                    <table class="resumen">
                                                                        <tr>
                                                                            <th>
                                                                                Nombre
                                                                            </th>
                                                                            <th>
                                                                                Precio
                                                                            </th>
                                                                        </tr>
                                                                        <% for(int s=0; s<reserva.getaPasajerosNinos().get(f).getaServiciosIda().size(); s++){%>
                                                                        <tr>
                                                                            <td>
                                                                                <% out.print(reserva.getaPasajerosNinos().get(f).getaServiciosIda().get(s).getNombre()); %>
                                                                            </td>
                                                                            <td>
                                                                                <% out.print(reserva.getaPasajerosNinos().get(f).getaServiciosIda().get(s).getPrecio()); %>
                                                                            </td>
                                                                        </tr>
                                                                        <% }%>
                                                                    </table>
                                                                </div>
                                                                <% if(!reserva.getaPasajerosNinos().get(f).getaServiciosVuelta().isEmpty()){%>
                                                                    <h3>Servicios de vuelta</h3>
                                                                    <div>
                                                                        <table class="resumen">
                                                                            <tr>
                                                                                <th>
                                                                                    Nombre
                                                                                </th>
                                                                                <th>
                                                                                    Precio
                                                                                </th>
                                                                            </tr>
                                                                            <% for(int z=0; z<reserva.getaPasajerosNinos().get(f).getaServiciosVuelta().size(); z++){%>
                                                                            <tr>
                                                                                <td>
                                                                                    <% out.print(reserva.getaPasajerosNinos().get(f).getaServiciosVuelta().get(z).getNombre()); %>
                                                                                </td>
                                                                                <td>
                                                                                    <% out.print(reserva.getaPasajerosNinos().get(f).getaServiciosVuelta().get(z).getPrecio()); %>
                                                                                </td>
                                                                            </tr>
                                                                            <% }%>
                                                                        </table>
                                                                    </div>
                                                                <% }%>
                                                            </div>
                                                        <% }%>
                                                    <% }%>
                                                </div>
                                                <h3>Pagador y Tarjeta</h3>
                                                <div>
                                                    <h3>Pagador</h3>
                                                    <div>
                                                        <table class="resumen">
                                                            <tr>
                                                                <th>
                                                                    Nombre
                                                                </th>
                                                                <th>
                                                                    Apellidos
                                                                </th>
                                                                <th>
                                                                    DNI
                                                                </th>
                                                                <th>
                                                                    Fecha Nacimiento
                                                                </th>
                                                                <th>
                                                                    Población
                                                                </th>
                                                                <th>
                                                                    Dirección
                                                                </th>
                                                            </tr>
                                                            <tr>
                                                                <td>
                                                                    <% out.print(reserva.getPagador().getNombre()); %>
                                                                </td>
                                                                <td>
                                                                    <% out.print(reserva.getPagador().getApellidos()); %>
                                                                </td>
                                                                <td>
                                                                    <% out.print(reserva.getPagador().getNif()); %>
                                                                </td>
                                                                <td>
                                                                    <% out.print(reserva.getPagador().getFecha_nac()); %>
                                                                </td>
                                                                <td>
                                                                    <% out.print(reserva.getPagador().getPoblacion()); %>
                                                                </td>
                                                                <td>
                                                                    <% out.print(reserva.getPagador().getDireccion()); %>
                                                                </td>
                                                            </tr>
                                                        </table>
                                                    </div>
                                                    <h3>Tarjeta</h3>
                                                    <div>
                                                        <table class="resumen">
                                                            <tr>
                                                                <th>
                                                                    Número Tarjeta
                                                                </th>
                                                                <th>
                                                                    Caducidad
                                                                </th>
                                                                <th>
                                                                    Numero Usos
                                                                </th>
                                                            </tr>
                                                            <tr>
                                                                <td>
                                                                    <% out.print(reserva.getTarjeta().getNum_tarjeta()); %>
                                                                </td>
                                                                <td>
                                                                    <% out.print(reserva.getTarjeta().getCaducidad()); %>
                                                                </td>
                                                                <td>
                                                                    <% out.print(reserva.getTarjeta().getNum_usos()); %>
                                                                </td>
                                                            </tr>
                                                        </table>
                                                    </div>
                                                </div>
                                            </div>
                                        </form>
                                    </div>
                                    <button onclick="imprimirResumen()">Imprimir Resumen</button>                            
				</section>
			</div>
		</div>
	<!-- /Main -->
        
	</body>
</html>
