<%-- 
    Document   : facturarReserva
    Created on : 28-feb-2017, 12:19:39
    Author     : Carlos
--%>

<%@page import="java.time.ZoneId"%>
<%@page import="java.util.Date"%>
<%@page import="java.time.LocalDate"%>
<%@page import="Clases.Reserva"%>
<%
    Reserva reserva = (Reserva) session.getAttribute("reserva_a_facturar");
    Boolean ida;
    String vuelta;
    LocalDate fecha_ida = reserva.getVuelo_ida().getFecha();
    LocalDate fecha_vuelta = reserva.getVuelo_vuelta().getFecha();
    LocalDate fecha_ahora = LocalDate.now();
    Date d_ida = Date.from(fecha_ida.atStartOfDay(ZoneId.systemDefault()).toInstant());
    Date d_vuelta = Date.from(fecha_vuelta.atStartOfDay(ZoneId.systemDefault()).toInstant());
    Date d_ahora = Date.from(fecha_ahora.atStartOfDay(ZoneId.systemDefault()).toInstant());
    
    final long MILLSECS_PER_DAY = 24 * 60 * 60 * 1000;
    
    long dif_ida = (d_ida.getTime() - d_ahora.getTime()) / MILLSECS_PER_DAY;
    
    if(dif_ida <= 5){
        ida = true;
    } else {
        ida = false;
    }
    
    if(reserva.getVuelo_vuelta() != null){
        long dif_vuelta = (d_vuelta.getTime() - d_ahora.getTime()) / MILLSECS_PER_DAY;
        if(dif_vuelta <= 5){
            vuelta = "Si";
        } else {
            vuelta = "No";
        }    
    } else {
        vuelta = "No tiene";
    }
    
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
                        <h2>Facturar Reserva</h2>
                    </header>
                    <div id="contenido">
                        <form action="controladorFacturar" name="form16">
                            <h2>Facturacion de la reserva: </h2><h1><b><% out.print(reserva.getCod_reserva()); %></b></h1>
                            <table class="tablafacturar">
                                <tr>
                                    <th>
                                        Facturar Ida
                                    </th>
                                    <td>
                                        <% if(reserva.getFacturada_ida().equals("N")) { %>
                                            <% if(ida) { %>
                                                <button type="submit" class="accion" name="facturar" value="facturarida">Facturar</button>
                                            <% } else { %>
                                                Todavía no se puede facturar (La facturación se permite cuando faltan 5 días para volar).
                                            <% } %>
                                        <% } else { %>
                                            Ida Facturada.
                                        <% } %>
                                    </td>
                                </tr>
                                <tr>
                                    <th>
                                        Facturar Vuelta
                                    </th>
                                    <td>
                                        <% if(vuelta.equals("Si")) { %>
                                            <% if(reserva.getFacturada_vuelta().equals("N")) { %>
                                                <button type="submit" class="accion" name="facturar" value="facturarvuelta">Facturar</button>
                                            <% }  %>
                                        <% }  %>
                                        <% if(vuelta.equals("No")) { %>
                                            <% if(reserva.getFacturada_vuelta().equals("N")) { %>
                                                Todavía no se puede facturar (La facturación se permite cuando faltan 5 días para volar).
                                            <% } %>
                                        <% } %>
                                        <% if(vuelta.equals("No tiene")) { %>
                                            
                                        <% } %>
                                    </td>
                                </tr>
                            </table>
                        </form>
                        <br>
                        <% if(reserva.getFacturada_ida().equals("S")) { %>
                                <a href="qrIda.jsp"><button>Billetes Ida</button></a>
                        <% } else { %>
                                
                        <% } %>
                        <% if(reserva.getFacturada_vuelta().equals("S")) { %>
                                <a href="qrVuelta.jsp"><button>Billetes Vuelta</button></a>
                        <% }  %>
                        <% if(reserva.getFacturada_vuelta().equals("N")) { %>
                                
                        <% } %>
                        <% if(vuelta.equals("No tiene")) { %>
                                
                        <% } %>
                        <br>
                        <a href="facturacion.jsp"><button>Volver</button></a>
                    </div>
                </section>
            </div>
        </div>
        <!-- /Main -->

    </body>
</html>