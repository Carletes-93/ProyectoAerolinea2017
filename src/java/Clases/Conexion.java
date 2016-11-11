/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Carlos
 */
public class Conexion {

    private static Conexion UnicaConexion = null;
    private Connection Conex;

    private Conexion() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");
        String connectionUrl = "jdbc:mysql://localhost:3306/bd_proyecto_aviones15";
        Conex = DriverManager.getConnection(connectionUrl, "root", "root");
    }

    public synchronized static Conexion GetConexion() throws ClassNotFoundException, SQLException {
        if (UnicaConexion == null) {
            UnicaConexion = new Conexion();
        }
        return UnicaConexion;
    }

    public Connection GetCon() {
        return Conex;
    }

    public void Destroy() throws SQLException {
        Conex.close();
    }
}
