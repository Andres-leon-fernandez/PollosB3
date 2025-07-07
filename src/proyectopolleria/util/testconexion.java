
package proyectopolleria.util;

import java.sql.Connection;

/**
 *
 * @author Andres
 */
public class testconexion {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            Conexion conexion=Conexion.getInstancia();
            Connection con = conexion.getConexion();
            
            if(con!=null && !con.isClosed()){
                System.out.println("? Conexi�n a la base de datos establecida correctamente.");
            } else {
                System.out.println("? La conexi�n est� cerrada o es nula.");
            }
            
            
        } catch (Exception e) {
            System.out.println("? Error al probar la conexi�n: " + e.getMessage());
        }
    }
    
}
