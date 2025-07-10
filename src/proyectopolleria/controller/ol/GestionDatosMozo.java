package proyectopolleria.controller.ol;

import proyectopolleria.model.previos.Mozo;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import proyectopolleria.model.previos.Mozo;

public class GestionDatosMozo {
    private static final String CSV_FILE = "mozos.csv";

    public static void guardarMozos(List<Mozo> mozos) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(CSV_FILE))) {
            for (Mozo mozo : mozos) {
                writer.write(String.format("%s,%s,%s\n", mozo.getApellidos(), mozo.getNombres(), mozo.getDni()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<Mozo> cargarMozos() {
        List<Mozo> mozos = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(CSV_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length == 3) {
                String apellidos = data[0];
                String nombres = data[1];
                String dni = data[2];
                Mozo mozo = new Mozo(apellidos, nombres, dni);
                mozos.add(mozo);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return mozos;
    }   
}
