<%-- 
    Document   : pagadorNuevo
    Created on : 01-jun-2016, 12:38:58
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
                                        <h2>Rellena los datos</h2>
                                    </header>
                                    <div id="contenido">
                                        <form action="controladorPagadorNuevo" id="form" name="form9" onsubmit="validarPass()">
                                            <table class="pagadornuevo">
                                                <tr>
                                                    <th>
                                                        Nombre
                                                    </th>
                                                    <td>
                                                        <input type="text" name="nom" required>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <th>
                                                        Apellidos
                                                    </th>
                                                    <td>
                                                        <input type="text" name="ape" required>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <th>
                                                        Fecha de nacimiento
                                                    </th>
                                                    <td>
                                                        <input type="text" class="pagadordatepicker" name="nac" required>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <th>
                                                        Población
                                                    </th>
                                                    <td>
                                                        <input type="text" name="pob" required>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <th>
                                                        Dirección
                                                    </th>
                                                    <td>
                                                        <input type="text" name="dir" required>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <th>
                                                        NIF
                                                    </th>
                                                    <td>
                                                        <input type="text" name="nif" required>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <th>
                                                        Correo electronico
                                                    </th>
                                                    <td>
                                                        <input type="email" name="email" required>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <th>
                                                        Contraseña
                                                    </th>
                                                    <td>
                                                        <input type="password" name="pass" required>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <th>
                                                        Repetir Contraseña
                                                    </th>
                                                    <td>
                                                        <input type="password" name="passr" required>
                                                    </td>
                                                </tr>
                                            </table>
                                            <button type="submit" class="accion" name="pagadornuevo" value="pagadornuevo">
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
