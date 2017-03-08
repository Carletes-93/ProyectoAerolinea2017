<%-- 
    Document   : tarjetaNueva
    Created on : 01-jun-2016, 19:35:50
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
                                        <h2>Rellena los datos de la tarjeta</h2>
                                    </header>
                                    <div id="contenido">
                                        <form action="controladorTarjetaNueva" name="form13">
                                            <table class="tarjetanueva">
                                                <tr>
                                                    <th>
                                                        Número de Tarjeta
                                                    </th>
                                                    <td>
                                                        <input type="text" name="num" required>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <th>
                                                        Caducidad
                                                    </th>
                                                    <td>
                                                        <input type="text" class="tarjetadatepicker" name="cad" required>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <th>
                                                        PIN
                                                    </th>
                                                    <td>
                                                        <input type="password" name="pin" required>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <th>
                                                        Codigo de seguridad
                                                    </th>
                                                    <td>
                                                        <input type="password" name="seg" required>
                                                    </td>
                                                </tr>
                                            </table>
                                            <button type="submit" class="accion" name="tarjetanueva" value="tarjetanueva">
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

