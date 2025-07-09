package proyectopolleria.util;

import java.sql.Connection;
import java.util.Scanner;

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
            Conexion conexion = Conexion.getInstancia();
            Connection con = conexion.getConexion();

            if (con != null && !con.isClosed()) {
                System.out.println("? Conexión a la base de datos establecida correctamente.");
                String a = "jose";
                Scanner sc = new Scanner(System.in);
                Sha256 conversor = new Sha256();
                String int1 = conversor.sha256(a);
                System.out.println(conversor.sha256(a));
                System.out.println("coloca la clave");
                String cl = sc.nextLine();
                String int2 = conversor.sha256(cl);
                if (int1.equals(int2)) {
                    System.out.println("son iguales");
                } else {
                    System.out.println("son diferentes");
                }
            } else {
                System.out.println("? La conexión está cerrada o es nula.");
            }

        } catch (Exception e) {
            System.out.println("? Error al probar la conexión: " + e.getMessage());
        }
    }

}
