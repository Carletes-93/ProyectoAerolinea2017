<!DOCTYPE HTML>

<%@page import= "Clases.*" %>

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
        <body onload="cargarPagina()">

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
                                        <h2>Reserva tu vuelo</h2>
                                    </header>
                                    <div id="contenido">
                                        <div class="btn_iyv">
                                            <input class="desactivado" type="button" id="ida" name="IDA" value="Solo ida" onclick="ida()" disabled="disabled">
                                            <input class="activado" type="button" id="vuelta" name="VUELTA" value="Vuelta" onclick="vuelta()">
                                            <br>
                                            <p id="texto">Solo ida</p>
                                        </div>
                                        <form action="dispatcher" name="form">
                                            <div class="datos">
                                                <table id="tablavuelo">
                                                    <tr>
                                                        <td>
                                                            <select id="origen" name="ORIGEN" onchange="cargarDestinos(this.value)">
                                                                <option value="Origen">Origen</option>
                                                            </select>
                                                        </td>
                                                        <td>
                                                            <select id="destino" name="DESTINO" disabled="disabled">
                                                                <option value="Destino">Destino</option>
                                                            </select>
                                                        </td>
                                                        <td>

                                                        </td>
                                                        <td>
                                                            <select id="adultos" name="ADULTOS" onchange="cambiarninosbebes()">
                                                                <option value="1">1 Adultos (+18)</option>
                                                                <option value="2">2 Adultos (+18)</option>
                                                                <option value="3">3 Adultos (+18)</option>
                                                                <option value="4">4 Adultos (+18)</option>
                                                                <option value="5">5 Adultos (+18)</option>
                                                                <option value="6">6 Adultos (+18)</option>
                                                                <option value="7">7 Adultos (+18)</option>
                                                            </select>
                                                        </td>
                                                    </tr>
                                                    <tr>
                                                        <td>
                                                            <input type="text" name="FECHA_IDA" class="datepickerida" id="fechaida" placeholder="Fecha Ida" required="required">
                                                        </td>
                                                        <td>
                                                            <input type="text" name="FECHA_VUELTA" class="datepickervuelta" id="fechavuelta" placeholder="Fecha Vuelta" disabled="disabled">
                                                        </td>
                                                        <td>

                                                        </td>
                                                        <td>
                                                            <select id="ninos" name="NINOS">
                                                                <option value="0" selected="selected">0 Niños (2-17)</option>
                                                                <option value="1">1 Niño (2-17)</option>
                                                                <option value="2">2 Niños (2-17)</option>
                                                                <option value="3">3 Niños (2-17)</option>
                                                                <option value="4">4 Niños (2-17)</option>
                                                                <option value="5">5 Niños (2-17)</option>
                                                                <option value="6">6 Niños (2-17)</option>
                                                            </select>
                                                        </td>
                                                    </tr>
                                                    <tr>
                                                        <td>
                                                            
                                                        </td>
                                                        <td>
                                                            
                                                        </td>
                                                        <td>

                                                        </td>
                                                        <td>
                                                            <select id="bebes" name="BEBES">
                                                                <option value="0" selected="selected">0 Bebes (0-2)</option>
                                                                <option value="1">1 Bebe (0-2)</option>
                                                            </select>
                                                        </td>
                                                    </tr>
                                                </table>
                                                <button type="submit" id="accion" name="accion" value="IDA">
                                                    Buscar
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