<%-- 
    Document   : vistaEstadisticas
    Created on : 10-mar-2017, 11:38:04
    Author     : carlos
--%>

<%@page import="Clases.Servicio"%>
<%@page import="java.util.ArrayList"%>
<% ArrayList<Servicio> servicio = (ArrayList<Servicio>) session.getAttribute("servicios_est"); %>
<% String fechaIn = session.getAttribute("fechaI").toString(); %>
<% String fechaFin = session.getAttribute("fechaF").toString(); %>
<% String origen = session.getAttribute("or").toString(); %>
<% String destino = session.getAttribute("des").toString(); %>

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
                        <li><a href="estadisticas.jsp">Estadisticas</a></li>
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
                        <h2>Ingresos de los Servicios</h2>
                    </header>
                </section>
                <div id="contenido">
                    <p>Entre <% out.print(fechaIn); %> y <% out.print(fechaFin); %></p>
                    <p>Entre <% out.print(origen); %> y <% out.print(destino); %></p>
                    <table>
                        <tr>
                            <th>
                                Servicio
                            </th>
                            <th>
                                Importe (€)
                            </th>
                        </tr>
                        <% for(Servicio s1 : servicio){%>
                        <tr>
                            <td>
                                <% out.print(s1.getNombre()); %>
                            </td>
                            <td>
                                <% out.print(s1.getSuma()*s1.getPrecio()); %>
                            </td>
                        </tr>
                        <% } %>
                    </table>
                </div>
            </div>
        </div>
        <!-- /Main -->

    </body>
</html>
