package proyectopolleria.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

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
    
    public static void desconectar(){
    if(instancia!=null && instancia.con!=null){
        try {
            instancia.con.close();
            instancia.con=null;
            instancia=null;
            JOptionPane.showMessageDialog(null, "Conexion Cerrada");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,"Error al cerrar la conexión: "+e);
        }
    }
    }
}
