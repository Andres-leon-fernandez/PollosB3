
import proyectopolleria.util.Sha256;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
/**
 *
 * @author andres
 */
public class hashxd {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        String password = "root";

        for (int i = 1; i <= 10; i++) {
            password = Sha256.sha256(password);
            System.out.println("Hash " + i + ": " + password);
        }
    }

}
