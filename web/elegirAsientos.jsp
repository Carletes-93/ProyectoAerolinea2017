<%-- 
    Document   : elegirAsientos
    Created on : 30-may-2016, 9:50:49
    Author     : Carlos
--%>

<%@page import="Clases.Pasajero"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>

<% ArrayList<Pasajero> aPasajerosAdultos = (ArrayList<Pasajero>) session.getAttribute("pasajerosadultos"); %>
<% ArrayList<Pasajero> aPasajerosNinos = (ArrayList<Pasajero>) session.getAttribute("pasajerosninos"); %>
<% ArrayList<Boolean> aAsientosIda = (ArrayList<Boolean>) session.getAttribute("asientos ida"); %>
<% ArrayList<Boolean> aAsientosVuelta = (ArrayList<Boolean>) session.getAttribute("asientos vuelta"); %>

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
						<li><a href="finalizarVuelo.jsp">Volar</a></li>
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
                                        <h2>Elije los asientos</h2>
                                    </header>
                                    <div id="contenido">
                                        <form action="controladorAsientosPasajeros" name="form6">
                                            <% if(aPasajerosAdultos !=null){%>
                                                <% for(int i=0; i<aPasajerosAdultos.size(); i++){%>
                                                    <% for(int s = 0; s < aPasajerosAdultos.get(i).getaServiciosIda().size(); s++){ %>
                                                        <% if(aPasajerosAdultos.get(i).getaServiciosIda().get(s).getNombre().equals("Asiento reservado")){ %>    
                                                            <div class="datos">
                                                                <h3>Asiento de <% out.print(aPasajerosAdultos.get(i).getNombre()+" "+aPasajerosAdultos.get(i).getApellidos()); %></h3>
                                                                <table class="asientospasajeros">
                                                                    <tr>
                                                                        <td>
                                                                            Asiento de ida: &nbsp;
                                                                        </td>
                                                                        <td>
                                                                            <select name="<% out.print("asientoidaadult"+i); %>">
                                                                                <% for(int j=0; j<aAsientosIda.size(); j++){ %>
                                                                                    <% if(aAsientosIda.get(j)==true){%>
                                                                                    <option value="<% out.print(j+1); %>"><% out.print(j+1); %></option>
                                                                                    <% }%>
                                                                                <% }%>
                                                                            </select>
                                                                        </td>
                                                                    </tr>
                                                                    <% if(!aAsientosVuelta.isEmpty()){%>
                                                                        <tr>
                                                                            <td>
                                                                                Asiento de vuelta: &nbsp;
                                                                            </td>
                                                                            <td>
                                                                                <select name="<% out.print("asientovueltaadult"+i); %>">
                                                                                    <% for(int u=0; u<aAsientosVuelta.size(); u++){ %>
                                                                                        <% if(aAsientosVuelta.get(u)==true){%>
                                                                                        <option value="<% out.print(u+1); %>"><% out.print(u+1); %></option>
                                                                                        <% }%>
                                                                                    <% }%>
                                                                                </select>
                                                                            </td>
                                                                        </tr>
                                                                    <% }%>
                                                                </table>
                                                                <% if(aPasajerosAdultos.size()>0){%>
                                                                <hr>
                                                                <% }%>
                                                            </div>
                                                        <% } %>
                                                    <% } %>
                                                <% }%>
                                            <% }%>
                                            <% if(aPasajerosNinos !=null){%>
                                                <% for(int x=0; x<aPasajerosNinos.size(); x++){%>
                                                    <% for(int sn = 0; sn < aPasajerosNinos.get(x).getaServiciosIda().size(); sn++){ %>
                                                        <% if(aPasajerosNinos.get(x).getaServiciosIda().get(sn).getNombre().equals("Asiento reservado")){ %>
                                                            <div class="datos">
                                                                <h3>Asiento de <% out.print(aPasajerosNinos.get(x).getNombre()+" "+aPasajerosNinos.get(x).getApellidos()); %></h3>
                                                                <table class="asientospasajeros">
                                                                    <tr>
                                                                        <td>
                                                                            Asiento de ida: &nbsp;
                                                                        </td>
                                                                        <td>
                                                                            <select name="<% out.print("asientoidanino"+x); %>">
                                                                                <% for(int n=0; n<aAsientosIda.size(); n++){ %>
                                                                                    <% if(aAsientosIda.get(n)==true){%>
                                                                                    <option value="<% out.print(n+1); %>"><% out.print(n+1); %></option>
                                                                                    <% }%>
                                                                                <% }%>
                                                                            </select>
                                                                        </td>
                                                                    </tr>
                                                                    <% if(!aAsientosVuelta.isEmpty()){%>
                                                                        <tr>
                                                                            <td>
                                                                                Asiento de vuelta: &nbsp;
                                                                            </td>
                                                                            <td>
                                                                                <select name="<% out.print("asientovueltanino"+x); %>">
                                                                                    <% for(int u=0; u<aAsientosVuelta.size(); u++){ %>
                                                                                        <% if(aAsientosVuelta.get(u)==true){%>
                                                                                        <option value="<% out.print(u+1); %>"><% out.print(u+1); %></option>
                                                                                        <% }%>
                                                                                    <% }%>
                                                                                </select>
                                                                            </td>
                                                                        </tr>
                                                                    <% }%>
                                                                </table>
                                                                <% if(aPasajerosNinos.size()>0){%>
                                                                <hr>
                                                                <% }%>
                                                            </div>
                                                        <% } %>
                                                    <% } %>        
                                                <% }%>
                                            <% }%>
                                            <div id="mapaasientos">
                                                <img src="images/asientosmapa.jpg">
                                            </div>
                                            <button type="submit" class="accion" name="asientospasajeros" value="asientospasajeros">
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