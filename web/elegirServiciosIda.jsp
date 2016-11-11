<%-- 
    Document   : elegirServiciosIda
    Created on : 11-may-2016, 13:30:20
    Author     : Carlos
--%>

<%@page import="Clases.Pasajero"%>
<%@page import="Clases.Servicio"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>

<% ArrayList<Pasajero> aPasajerosAdultos = (ArrayList<Pasajero>) session.getAttribute("pasajerosadultos"); %>
<% ArrayList<Pasajero> aPasajerosNinos = (ArrayList<Pasajero>) session.getAttribute("pasajerosninos"); %>
<% ArrayList<Servicio> aServicios = (ArrayList<Servicio>) session.getAttribute("servicios"); %>

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
                <script type="text/javascript" src="js/javascript.js" charset="UTF-8"></script>
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
						<li><a href="">Mis Vuelos</a></li>
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
                                        <h2>Elije los servicios del viaje de Ida</h2>
                                    </header>
                                    <div id="contenido">
                                        <form action="controladorServiciosPasajerosIda" name="form4">
                                            <% if(aPasajerosAdultos !=null){%>
                                                <% for(int i=0; i<aPasajerosAdultos.size(); i++){%>
                                                    <div class="datos">
                                                        <h3>Servicios de <% out.print(aPasajerosAdultos.get(i).getNombre()+" "+aPasajerosAdultos.get(i).getApellidos()); %></h3>
                                                        <table class="serviciospasajeros">
                                                            <% for(int u=0; u<aServicios.size(); u++){%> 
                                                                <% if (aServicios.get(u).getNombre().equals("Bebe")){%>
                                                                    <tr>
                                                                        <td>
                                                                            <input onchange="datosBebe(this.id)" id="<% out.print(i); %>" type="checkbox" name="<% out.print("serv"+i); %>" value="<% out.print(u); %>"><span title="<% out.print(aServicios.get(u).getDescripcion()); %>"><% out.print(aServicios.get(u).getNombre()); %> (<% out.print(aServicios.get(u).getPrecio()); %>€).</span>
                                                                        </td>
                                                                    </tr>
                                                                <% } else{%>
                                                                    <tr>
                                                                        <td>
                                                                            <input id="<% out.print("serv"+i+u); %>" type="checkbox" name="<% out.print("serv"+i); %>" value="<% out.print(u); %>"><span title="<% out.print(aServicios.get(u).getDescripcion()); %>"><% out.print(aServicios.get(u).getNombre()); %> (<% out.print(aServicios.get(u).getPrecio()); %>€).</span>
                                                                        </td>
                                                                    </tr>
                                                                <% }%>
                                                            <% }%>
                                                                <tr class="trbebe" id="<% out.print("servt"+i); %>">
                                                                    <td>
                                                                        <table class="tablabebe">
                                                                            <tr>
                                                                                <td colspan="2" style="text-align: center;">
                                                                                    Datos bebe:
                                                                                </td>
                                                                            </tr>
                                                                            <tr>
                                                                                <td>
                                                                                    Nombre:
                                                                                </td>
                                                                                <td>
                                                                                    <input type="text" id="nombebe" name="<% out.print("nombebe"+i); %>">
                                                                                </td>
                                                                            </tr>
                                                                            <tr>
                                                                                <td>
                                                                                    Apellidos:
                                                                                </td>
                                                                                <td>
                                                                                    <input type="text" id="apebebe" name="<% out.print("apebebe"+i); %>">
                                                                                </td>
                                                                            </tr>
                                                                            <tr>
                                                                                <td>
                                                                                    Nif:
                                                                                </td>
                                                                                <td>
                                                                                    <input type="text" id="nifbebe" name="<% out.print("nifbebe"+i); %>">
                                                                                </td>
                                                                            </tr>
                                                                            <tr>
                                                                                <td>
                                                                                    Fecha Nacimiento:
                                                                                </td>
                                                                                <td>
                                                                                    <input type="text" class="datepickerbebe" name="<% out.print("fechabebe"+i); %>">
                                                                                </td>
                                                                            </tr>
                                                                        </table>
                                                                    </td>
                                                                </tr>
                                                        </table>
                                                        <% if(aPasajerosAdultos.size()>0){%>
                                                        <hr>
                                                        <% }%>
                                                    </div>
                                                <% }%>
                                            <% }%>
                                            <% if(aPasajerosNinos !=null){%>
                                                <% for(int i=0; i<aPasajerosNinos.size(); i++){%>
                                                    <div class="datos">
                                                        <h3>Servicios de <% out.print(aPasajerosNinos.get(i).getNombre()+" "+aPasajerosNinos.get(i).getApellidos()); %></h3>
                                                        <table class="serviciospasajeros">
                                                            <% for(int u=0; u<aServicios.size(); u++){%>
                                                                <% if (!aServicios.get(u).getNombre().equals("Bebe")){%>
                                                                    <tr>
                                                                        <td>
                                                                            <input type="checkbox" name="<% out.print("servn"+i); %>" value="<% out.print(u); %>"><span title="<% out.print(aServicios.get(u).getDescripcion()); %>"><% out.print(aServicios.get(u).getNombre()); %> (<% out.print(aServicios.get(u).getPrecio()); %>€).</span>
                                                                        </td>
                                                                    </tr>
                                                                <% }%>
                                                            <% }%>
                                                        </table>
                                                        <% if(aPasajerosNinos.size()>0){%>
                                                        <hr>
                                                        <% }%>
                                                    </div>
                                                <% }%>
                                            <% }%>
                                            <button type="submit" class="accion" name="serviciospasajeros" value="serviciospasajeros">
                                                Continuar
                                            </button>
                                        </form>
                                    </div>
				</section>
			</div>
		</div>
	<!-- /Main -->
        
	</body>
</html>