package proyectopolleria.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {

    private static Conexion instancia;
    private Connection con;

    private final String url = "jdbc:mysql://localhost:3306/PollosB3";
    private final String user = "root";
    private final String paswword = "root";

    private Conexion() {
        try {
            con = DriverManager.getConnection(url, user, paswword);
            System.out.println("conexion exitosa");
        } catch (SQLException e) {
            throw new RuntimeException("Error de conexion en bd uwu " + e.getMessage());
        }
    }

    public static Conexion getInstancia() {
        if (instancia == null) {
            instancia = new Conexion();
        }
        return instancia;
    }
    
    public Connection getConexion(){
    return con;
    };
}
