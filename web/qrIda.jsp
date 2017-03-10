<%-- 
    Document   : qrIda
    Created on : 01-mar-2017, 17:32:17
    Author     : Carlos
--%>

<%@page import="Clases.Reserva"%>
<%
    Reserva reserva = (Reserva) session.getAttribute("reserva_a_facturar");
%>

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
        <script src="qrcode/jquery-qrcode-0.14.0.js"></script>
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
                        <h2>Billetes</h2>
                    </header>
                    <div id="contenido">
                        <h2>Billetes de la reserva: </h2><h1><b><% out.print(reserva.getCod_reserva()); %></b></h1>
                        <% for(int i = 0; i < reserva.getaPasajerosAdultos().size(); i++) { %>
                        <div id="pdf<% out.print(i); %>"> 
                            <table class="tablaBilletes">
                                <tr>
                                    <th>
                                        <b><% out.print(reserva.getaPasajerosAdultos().get(i).getNombre()); %> <% out.print(reserva.getaPasajerosAdultos().get(i).getApellidos()); %></b>
                                    </th>
                                    <td>
                                        Nº Vuelo: <b><% out.print(reserva.getVuelo_ida().getNum_vuelo()); %></b>
                                    </td>
                                    <td>
                                        <b><% out.print(reserva.getVuelo_ida().getOrigen().getIata()); %></b> ----> <b><% out.print(reserva.getVuelo_ida().getDestino().getIata()); %></b>
                                    </td>
                                </tr>
                                <tr>
                                    <td>
                                        Fecha: <b><% out.print(reserva.getVuelo_ida().getFecha().toString()); %></b>
                                    </td>
                                    <td>
                                        Hora Salida: <b><% out.print(reserva.getVuelo_ida().getHora_salida().toString()); %></b>
                                    </td>
                                    <td>
                                        Hora Llegada: <b><% out.print(reserva.getVuelo_ida().getHora_llegada().toString()); %> (Aprox.)</b>
                                    </td>
                                </tr>
                                <tr>
                                    <td>
                                        Pasajero: <b>Adulto</b>
                                    </td>
                                    <td>
                                        Asiento: <b><% out.print(reserva.getaPasajerosAdultos().get(i).getAsiento_ida()); %></b>
                                    </td>
                                    <td>
                                        <% for(int b = 0; b < reserva.getaPasajerosAdultos().get(i).getaServiciosIda().size(); b++){ %>
                                        <%      if(reserva.getaPasajerosAdultos().get(i).getaServiciosIda().get(b).getNombre().equals("Bebe")){ %>
                                                    Bebe: <b>Si</b>
                                        <%      } %>
                                        <% } %>
                                    </td>
                                </tr>
                                <tr>
                                    <td colspan="3" id="qr<% out.print(i); %>">
                                        <img id="qr<% out.print(i); %>">
                                            <script>
                                                $('#qr<% out.print(i); %>').qrcode({
                                                    size: 150,
                                                    text: "Nombre: <% out.print(reserva.getaPasajerosAdultos().get(i).getNombre()); %> <% out.print(reserva.getaPasajerosAdultos().get(i).getApellidos()); %>\nNIF: <% out.print(reserva.getaPasajerosAdultos().get(i).getNif()); %>\nNºVuelo: <% out.print(reserva.getVuelo_ida().getNum_vuelo()); %>\nFecha: <% out.print(reserva.getVuelo_ida().getFecha().toString()); %>\nHora Salida: <% out.print(reserva.getVuelo_ida().getHora_salida()); %>\nAsiento: <% out.print(reserva.getaPasajerosAdultos().get(i).getAsiento_ida()); %>", 
                                                    mode: 0,
                                                    ecLevel: 'H',
                                                    render: 'image',
                                                    quiet: 1,
                                                    minVersion: 10
                                                });
                                            </script>
                                    </td>
                                </tr>
                            </table>
                        </div>
                        <button onclick="imprimir<% out.print(i); %>()">Descargar Billete</button>
                        <hr>
                        <% } %>
                        <% for(int i = 0; i < reserva.getaPasajerosNinos().size(); i++) { %>
                        <div id="pdf<% out.print(i); %>n"> 
                            <table class="tablaBilletes">
                                <tr>
                                    <th>
                                        <b><% out.print(reserva.getaPasajerosNinos().get(i).getNombre()); %> <% out.print(reserva.getaPasajerosNinos().get(i).getApellidos()); %></b>
                                    </th>
                                    <td>
                                        Nº Vuelo: <b><% out.print(reserva.getVuelo_ida().getNum_vuelo()); %></b>
                                    </td>
                                    <td>
                                        <b><% out.print(reserva.getVuelo_ida().getOrigen().getIata()); %></b> ----> <b><% out.print(reserva.getVuelo_ida().getDestino().getIata()); %></b>
                                    </td>
                                </tr>
                                <tr>
                                    <td>
                                        Fecha: <b><% out.print(reserva.getVuelo_ida().getFecha().toString()); %></b>
                                    </td>
                                    <td>
                                        Hora Salida: <b><% out.print(reserva.getVuelo_ida().getHora_salida().toString()); %></b>
                                    </td>
                                    <td>
                                        Hora Llegada: <b><% out.print(reserva.getVuelo_ida().getHora_llegada().toString()); %> (Aprox.)</b>
                                    </td>
                                </tr>
                                <tr>
                                    <td>
                                        Pasajero: <b>Niño</b>
                                    </td>
                                    <td>
                                        Asiento: <b><% out.print(reserva.getaPasajerosNinos().get(i).getAsiento_ida()); %></b>
                                    </td>
                                    <td>
                                        
                                    </td>
                                </tr>
                                <tr>
                                    <td colspan="3" id="qr<% out.print(i); %>n">
                                        <img id="qr<% out.print(i); %>n">
                                            <script>
                                                $('#qr<% out.print(i); %>n').qrcode({
                                                    size: 150,
                                                    text: "Nombre: <% out.print(reserva.getaPasajerosNinos().get(i).getNombre()); %> <% out.print(reserva.getaPasajerosNinos().get(i).getApellidos()); %>\nNIF: <% out.print(reserva.getaPasajerosNinos().get(i).getNif()); %>\nNºVuelo: <% out.print(reserva.getVuelo_ida().getNum_vuelo()); %>\nFecha: <% out.print(reserva.getVuelo_ida().getFecha().toString()); %>\nHora Salida: <% out.print(reserva.getVuelo_ida().getHora_salida()); %>\nAsiento: <% out.print(reserva.getaPasajerosNinos().get(i).getAsiento_ida()); %>", 
                                                    mode: 0,
                                                    ecLevel: 'H',
                                                    render: 'image',
                                                    quiet: 1,
                                                    minVersion: 10
                                                });
                                            </script>
                                    </td>
                                </tr>
                            </table>
                        </div>
                        <button onclick="imprimir<% out.print(i); %>n()">Descargar Billete</button>
                        <hr>
                        <% } %>
                        <a href="facturarReserva.jsp"><button>Volver</button></a>
                    </div>
                </section>
            </div>
        </div>
        <!-- /Main -->

    </body>
</html>