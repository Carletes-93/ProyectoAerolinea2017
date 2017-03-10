<%-- 
    Document   : errorSQL
    Created on : 07-may-2016, 16:38:10
    Author     : Carlos
--%>

<% int codigo = Integer.parseInt(session.getAttribute("error").toString()); %>

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
                        <h2>Se ha producido un error</h2>
                    </header>
                </section>
                <div id="contenido">
                    <% if (codigo == 1) {%>
                    <p>El pagador no esta dado de alta.</p>
                    <h5>Codigo de error: <% out.print(codigo); %></h5>
                    <% }%>
                    <% if (codigo == 2) {%>
                    <p>Error recuperar el pagador de la BB.DD.</p>
                    <h5>Codigo de error: <% out.print(codigo); %></h5>
                    <% }%>
                    <% if (codigo == 3) {%>
                    <p>Error al buscar la reserva a facturar en la BB.DD.</p>
                    <h5>Codigo de error: <% out.print(codigo); %></h5>
                    <% }%>
                    <% if (codigo == 4) {%>
                    <p>Error al rescatar las reservas implicadas en el borrado del vuelo.</p>
                    <h5>Codigo de error: <% out.print(codigo); %></h5>
                    <% }%>
                    <% if (codigo == 5) {%>
                    <p>Error al borrar el vuelo y las reservas implicadas de la BB.DD.</p>
                    <h5>Codigo de error: <% out.print(codigo); %></h5>
                    <% }%>
                    <% if (codigo == 6) {%>
                    <p>Error al sacar el vuelo de la BB.DD.</p>
                    <h5>Codigo de error: <% out.print(codigo); %></h5>
                    <% }%>
                    <% if (codigo == 7) {%>
                    <p>Error al buscar vuelos.</p>
                    <h5>Codigo de error: <% out.print(codigo); %></h5>
                    <% }%>
                    <% if (codigo == 8) {%>
                    <p>No hay vuelos cercanos a la fecha buscada.</p>
                    <h5>Codigo de error: <% out.print(codigo); %></h5>
                    <% }%>
                    <% if (codigo == 9) {%>
                    <p>Error al sacar los asientos libres de la BB.DD.</p>
                    <h5>Codigo de error: <% out.print(codigo); %></h5>
                    <% }%>
                    <% if (codigo == 10) {%>
                    <p>No se encontraron servicios en la BB.DD.</p>
                    <h5>Codigo de error: <% out.print(codigo); %></h5>
                    <% }%>
                    <% if (codigo == 11) {%>
                    <p>Error al sacar los servicios de la BB.DD.</p>
                    <h5>Codigo de error: <% out.print(codigo); %></h5>
                    <% }%>
                    <% if (codigo == 12) {%>
                    <p>Error al insertar la reserva en la BB.DD.</p>
                    <h5>Codigo de error: <% out.print(codigo); %></h5>
                    <% }%>
                    <% if (codigo == 13) {%>
                    <p>Error al facturar la reserva de ida.</p>
                    <h5>Codigo de error: <% out.print(codigo); %></h5>
                    <% }%>
                    <% if (codigo == 14) {%>
                    <p>Error al facturar la reserva de vuelta.</p>
                    <h5>Codigo de error: <% out.print(codigo); %></h5>
                    <% }%>
                    <% if (codigo == 15) {%>
                    <p>Error al sacar los datos de la reserva a facturar de la BB.DD..</p>
                    <h5>Codigo de error: <% out.print(codigo); %></h5>
                    <% }%>
                    <% if (codigo == 16) {%>
                    <p>El pagador no tiene tarjetas antiguas guardadas.</p>
                    <h5>Codigo de error: <% out.print(codigo); %></h5>
                    <% }%>
                    <% if (codigo == 17) {%>
                    <p>Error al sacar las tarjetas antiguas del pagador.</p>
                    <h5>Codigo de error: <% out.print(codigo); %></h5>
                    <% }%>
                </div>
            </div>
        </div>
        <!-- /Main -->

    </body>
</html>