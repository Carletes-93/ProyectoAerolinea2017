<%-- 
    Document   : eleccionVuelo
    Created on : 07-may-2016, 13:09:15
    Author     : Carlos
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>

<%@page import= "Clases.*" %>
<%@page import= "java.util.ArrayList" %>

<% ArrayList<Vuelo> aVuelosIda = (ArrayList<Vuelo>) session.getAttribute("vuelosida"); %>
<% ArrayList<Vuelo> aVuelosVuelta = (ArrayList<Vuelo>) session.getAttribute("vuelosvuelta"); %>

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
                                        <% if(aVuelosVuelta == null || aVuelosVuelta.isEmpty()){ %>
                                        <h2>Selecciona tu Vuelo</h2>
                                        <% } else{ %>
                                        <h2>Selecciona tus Vuelos</h2>
                                        <% } %>
                                    </header>
                                    <div id="contenido">
                                        <form action="controladorVueloElegido" name="form2">
                                            <div class="datos">
                                                <h3>Vuelos Ida</h3>
                                                <table class="tablavueloIda">
                                                    <thead>
                                                        <tr>
                                                            <td>

                                                            </td>
                                                            <td>
                                                                Fecha
                                                            </td>
                                                            <td>
                                                                Origen
                                                            </td>
                                                            <td>
                                                                Destino
                                                            </td>
                                                            <td>
                                                                Hora de salida
                                                            </td>
                                                            <td>
                                                                Hora de llegada
                                                            </td>
                                                            <td>
                                                                Precio
                                                            </td>
                                                        </tr>
                                                    </thead>
                                                    <% for(int i=0; i<aVuelosIda.size(); i++){%>
                                                        <tr>
                                                            <td>
                                                                <input type="radio" name="vueloida" value="<% out.print(i);%>" required>
                                                            </td>
                                                            <td>
                                                                <% out.print(aVuelosIda.get(i).getFecha().toString());%>
                                                            </td>
                                                            <td>
                                                                <% out.print(aVuelosIda.get(i).getOrigen().getCiudad());%>
                                                            </td>
                                                            <td>
                                                                <% out.print(aVuelosIda.get(i).getDestino().getCiudad());%>
                                                            </td>
                                                            <td>
                                                                <% out.print(aVuelosIda.get(i).getHora_salida().toString());%>
                                                            </td>
                                                            <td>
                                                                <% out.print(aVuelosIda.get(i).getHora_llegada().toString());%>
                                                            </td>
                                                            <td>
                                                                <% out.print(aVuelosIda.get(i).getPrecio());%>
                                                            </td>
                                                        </tr>
                                                    <% } %>
                                                </table>
                                                <hr>
                                                <% if(aVuelosVuelta!=null){
                                                    if(!aVuelosVuelta.isEmpty()){ %>
                                                <h3>Vuelos Vuelta</h3>
                                                <table class="tablavueloVuelta">
                                                    <thead>
                                                        <tr>
                                                            <td>

                                                            </td>
                                                            <td>
                                                                Fecha
                                                            </td>
                                                            <td>
                                                                Origen
                                                            </td>
                                                            <td>
                                                                Destino
                                                            </td>
                                                            <td>
                                                                Hora de salida
                                                            </td>
                                                            <td>
                                                                Hora de llegada
                                                            </td>
                                                            <td>
                                                                Precio
                                                            </td>
                                                        </tr>
                                                    </thead>
                                                    <% for(int i=0; i<aVuelosVuelta.size(); i++){%>
                                                        <tr>
                                                            <td>
                                                                <input type="radio" name="vuelovuelta" value="<% out.print(i);%>" required>
                                                            </td>
                                                            <td>
                                                                <% out.print(aVuelosVuelta.get(i).getFecha().toString());%>
                                                            </td>
                                                            <td>
                                                                <% out.print(aVuelosVuelta.get(i).getOrigen().getCiudad());%>
                                                            </td>
                                                            <td>
                                                                <% out.print(aVuelosVuelta.get(i).getDestino().getCiudad());%>
                                                            </td>
                                                            <td>
                                                                <% out.print(aVuelosVuelta.get(i).getHora_salida().toString());%>
                                                            </td>
                                                            <td>
                                                                <% out.print(aVuelosVuelta.get(i).getHora_llegada().toString());%>
                                                            </td>
                                                            <td>
                                                                <% out.print(aVuelosVuelta.get(i).getPrecio());%>
                                                            </td>
                                                        </tr>
                                                    <% } %>
                                                </table>
                                                <%      }
                                                    }%>
                                                <button type="submit" class="accion" name="vueloelegido" value="vueloelegido">
                                                    Continuar
                                                </button>
                                            </div>
                                        </form>
                                    </div>
				</section>
			</div>
		</div>
	<!-- /Main -->
        
	</body>
</html>