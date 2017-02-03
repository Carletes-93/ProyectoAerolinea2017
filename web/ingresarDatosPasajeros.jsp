<%-- 
    Document   : ingresarDatosPasajeros
    Created on : 10-may-2016, 9:51:06
    Author     : Carlos
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>

<% int numadultos =(Integer) session.getAttribute("numadultos"); %>
<% int numninos =(Integer) session.getAttribute("numninos"); %>
<% int numbebes =(Integer) session.getAttribute("numbebes"); %>

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
                                        <h2>Rellena los datos</h2>
                                    </header>
                                    <div id="contenido">
                                        <form action="controladorDatosPasajeros" name="form3">
                                            <% if(numadultos!=0){%>
                                                <% for(int i=1; i<=numadultos;i++){%>
                                                    <div class="datos">
                                                        <h3>Datos pasajero Adulto <% out.print(i); %></h3>
                                                        <table class="datospasajeros">
                                                            <tr>
                                                                <th>
                                                                    Tratamiento
                                                                </th>
                                                                <td>
                                                                    <select  name="<% out.print("adulto"+i); %>">
                                                                        <option value="Mr">Mr.</option>
                                                                        <option value="Ms">Ms.</option>
                                                                    </select>
                                                                </td>
                                                            </tr>
                                                            <tr>
                                                                <th>
                                                                    Nombre
                                                                </th>
                                                                <td>
                                                                    <input type="text" name="<% out.print("nomadul"+i); %>" required>
                                                                </td>
                                                            </tr>
                                                            <tr>
                                                                <th>
                                                                    Apellidos
                                                                </th>
                                                                <td>
                                                                    <input type="text" name="<% out.print("apeladul"+i); %>" required>
                                                                </td>
                                                            </tr>
                                                            <tr>
                                                                <th>
                                                                    NIF
                                                                </th>
                                                                <td>
                                                                    <input type="text" name="<% out.print("nifadul"+i); %>" required>
                                                                </td>
                                                            </tr>
                                                            <tr>
                                                                <th>
                                                                    Caducidad DNI
                                                                </th>
                                                                <td>
                                                                    <input type="text" class="caducidad" name="<% out.print("caducadul"+i); %>" required>
                                                                </td>
                                                            </tr>
                                                            <tr>
                                                                <th>
                                                                    Fecha de nacimiento
                                                                </th>
                                                                <td>
                                                                    <input type="text" name="<% out.print("nacadul"+i); %>" class="datepickernac" required>
                                                                </td>
                                                            </tr>
                                                        </table>
                                                        <% if(numadultos>0){%>
                                                        <hr>
                                                        <% }%>
                                                    </div>
                                                <% }%>
                                            <% }%>
                                            <% if(numninos!=0){%>
                                                <% for(int i=1; i<=numninos;i++){%>
                                                    <div class="datos">
                                                        <h3>Datos pasajero Niño <% out.print(i); %></h3>
                                                        <table class="datospasajeros">
                                                            <tr>
                                                                <th>
                                                                    Tratamiento
                                                                </th>
                                                                <td>
                                                                    <select  name="<% out.print("nino"+i); %>">
                                                                        <option value="Mr">Mr.</option>
                                                                        <option value="Ms">Ms.</option>
                                                                    </select>
                                                                </td>
                                                            </tr>
                                                            <tr>
                                                                <th>
                                                                    Nombre
                                                                </th>
                                                                <td>
                                                                    <input type="text" name="<% out.print("nomnino"+i); %>" required>
                                                                </td>
                                                            </tr>
                                                            <tr>
                                                                <th>
                                                                    Apellidos
                                                                </th>
                                                                <td>
                                                                    <input type="text" name="<% out.print("apelnino"+i); %>" required>
                                                                </td>
                                                            </tr>
                                                            <tr>
                                                                <th>
                                                                    NIF
                                                                </th>
                                                                <td>
                                                                    <input type="text" name="<% out.print("nifnino"+i); %>" required>
                                                                </td>
                                                            </tr>
                                                            <tr>
                                                                <th>
                                                                    Caducidad DNI
                                                                </th>
                                                                <td>
                                                                    <input type="text" class="caducidad" name="<% out.print("caducnino"+i); %>" required>
                                                                </td>
                                                            </tr>
                                                            <tr>
                                                                <th>
                                                                    Fecha de nacimiento
                                                                </th>
                                                                <td>
                                                                    <input type="text" name="<% out.print("nacnino"+i); %>" class="datepickernac" required>
                                                                </td>
                                                            </tr>
                                                        </table>
                                                        <% if(numninos>0){%>
                                                        <hr>
                                                        <% }%>
                                                    </div>
                                                <% }%>
                                            <% }%>
                                            <% if(numbebes!=0){%>
                                                <% for(int i=1; i<=numbebes;i++){%>
                                                    <div class="datos">
                                                        <h3>Datos pasajero Bebe <% out.print(i); %></h3>
                                                        <table class="datospasajeros">
                                                            <tr>
                                                                <th>
                                                                    Tratamiento
                                                                </th>
                                                                <td>
                                                                    <select  name="<% out.print("bebe"+i); %>">
                                                                        <option value="Mr">Mr.</option>
                                                                        <option value="Ms">Ms.</option>
                                                                    </select>
                                                                </td>
                                                            </tr>
                                                            <tr>
                                                                <th>
                                                                    Nombre
                                                                </th>
                                                                <td>
                                                                    <input type="text" name="<% out.print("nombebe"+i); %>" required>
                                                                </td>
                                                            </tr>
                                                            <tr>
                                                                <th>
                                                                    Apellidos
                                                                </th>
                                                                <td>
                                                                    <input type="text" name="<% out.print("apelbebe"+i); %>" required>
                                                                </td>
                                                            </tr>
                                                            <tr>
                                                                <th>
                                                                    NIF
                                                                </th>
                                                                <td>
                                                                    <input type="text" name="<% out.print("nifbebe"+i); %>" required>
                                                                </td>
                                                            </tr>
                                                            <tr>
                                                                <th>
                                                                    Fecha de nacimiento
                                                                </th>
                                                                <td>
                                                                    <input type="text" name="<% out.print("nacbebe"+i); %>" class="datepickernac" required>
                                                                </td>
                                                            </tr>
                                                        </table>
                                                        <% if(numbebes>0){%>
                                                        <hr>
                                                        <% }%>
                                                    </div>
                                                <% }%>
                                            <% }%>
                                            <button type="submit" class="accion" name="datospasajeros" value="datospasajeros">
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