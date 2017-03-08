<%-- 
    Document   : facturacion
    Created on : 06-jun-2016, 17:30:34
    Author     : Carlos
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
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
                                        <h2>Facturar Reserva</h2>
                                    </header>
                                    <div id="contenido">
                                        <form action="controladorBuscarReservaFacturar" name="form16">
                                            <table class="facturarInicio">
                                                <tr>
                                                    <th>
                                                        Correo electronico 
                                                    </th>
                                                    <td>
                                                        <input type="email" name="email" size="40" required>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <th>
                                                        Numero de Reserva 
                                                    </th>
                                                    <td>
                                                        <input type="text" name="pass" size="40" required>
                                                    </td>
                                                </tr>
                                            </table>
                                            <button type="submit" class="accion" name="pagadorfacturar" value="pagadorfacturar">
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
