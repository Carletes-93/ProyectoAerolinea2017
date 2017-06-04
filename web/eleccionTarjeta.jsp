<%-- 
    Document   : eleccionTarjeta
    Created on : 01-jun-2016, 18:51:31
    Author     : Carlos
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>

<%@page import= "Clases.*" %>
<%@page import= "java.util.ArrayList" %>

<% ArrayList<Tarjeta> aTarjetas = (ArrayList<Tarjeta>) session.getAttribute("TarjetasPagador"); %>

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
                                        <h2>Elige una tarjeta para pagar</h2>
                                    </header>
                                    <div id="contenido">
                                        <form action="controladorTarjetaElegida" name="form12">
                                            <div class="datos">
                                                <h3>Tarjetas</h3>
                                                <table class="tablatarjetasantiguas">
                                                    <thead>
                                                        <tr>
                                                            <td>

                                                            </td>
                                                            <td>
                                                                Numero de Tarjeta
                                                            </td>
                                                            <td>
                                                                Fecha de Caducidad
                                                            </td>
                                                            <td>
                                                                Numero de usos
                                                            </td>
                                                        </tr>
                                                    </thead>
                                                    <% for(int i=0; i<aTarjetas.size(); i++){%>
                                                        <tr>
                                                            <td>
                                                                <input type="radio" name="tarjeta" value="<% out.print(i);%>" required>
                                                            </td>
                                                            <td>
                                                                <% 
                                                                    String numencriptado = aTarjetas.get(i).getNum_tarjeta();
                                                                    String ultimosdigitos = numencriptado.substring(24);
                                                                    String numeromostrar = "************" + ultimosdigitos;
                                                                    
                                                                    out.print(numeromostrar);
                                                                %>
                                                            </td>
                                                            <td>
                                                                <% out.print(aTarjetas.get(i).getCaducidad());%>
                                                            </td>
                                                            <td>
                                                                <% out.print(aTarjetas.get(i).getNum_usos());%>
                                                            </td>
                                                        </tr>
                                                    <% } %>
                                                </table>
                                                <button type="submit" class="accion" name="tarjetaelegida" value="tarjetaelegida">
                                                    Siguiente
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