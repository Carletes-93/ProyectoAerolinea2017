<%-- 
    Document   : estadisticas
    Created on : 10-mar-2017, 10:03:52
    Author     : carlos
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
        <script src="qrcode/jquery-qrcode-0.14.0.js"></script>
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
                        <h2>Consultar estadisticas</h2>
                    </header>
                    <div id="contenido">
                        <form action="controladorEstadisticas" name="form25">
                            <table class="tablafinvuelo">
                                <tr>
                                    <th>
                                        Fecha Inicio 
                                    </th>
                                    <td>
                                        <input type="text" name="fechai" class="datepicker" size="20" required>
                                    </td>
                                </tr>
                                <tr>
                                    <th>
                                        Fecha Fin 
                                    </th>
                                    <td>
                                        <input type="text" name="fechaf" class="datepicker" size="20" required>
                                    </td>
                                </tr><tr>
                                    <th>
                                        Aeropuerto Origen
                                    </th>
                                    <td>
                                        <select id="origen" name="aeroOr" onchange="cargarDestinos(this.value)">
                                            <option value="Origen">Origen</option>
                                        </select>
                                    </td>
                                </tr>
                                <tr>
                                    <th>
                                        Aeropuerto Destino 
                                    </th>
                                    <td>
                                        <select id="destino" name="aeroDes" disabled="disabled">
                                            <option value="Destino">Destino</option>
                                        </select>
                                    </td>
                                </tr>
                            </table>
                            <button type="submit" class="accion" name="estadisticas" value="estadisticas">
                                Consultar
                            </button>
                        </form>
                    </div>
                </section>
            </div>
        </div>
        <!-- /Main -->

    </body>
</html>