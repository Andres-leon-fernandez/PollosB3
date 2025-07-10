package proyectopolleria.controller.ol;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

// Atributo que almacena la ruta del archivo CSV
public class GestionDatosPedido {
    private String filePath;
    
    // Constructor que recibe la ruta del archivo CSV
    public GestionDatosPedido(String filePath) {
        this.filePath = filePath;
    }
    
    // Método para guardar datos en el archivo CSV
    public void guardarDatos(List<String[]> data) {
        try (Writer writer = new FileWriter(filePath, true)) {
            for (String[] rowData : data) {
                StringBuilder line = new StringBuilder();
                for (int i = 0; i < rowData.length; i++) {
                    line.append(rowData[i]);
                    if (i < rowData.length - 1) {
                        line.append(",");
                    }
                }
                line.append("\n");
                writer.write(line.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al guardar datos en el archivo CSV.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    // Método para cargar datos desde el archivo CSV
    public List<String[]> cargarDatos() {
        List<String[]> datos = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] row = line.split(",");
                datos.add(row);
            }
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al cargar datos desde el archivo CSV.", "Error", JOptionPane.ERROR_MESSAGE);
        }

        return datos;
    }
}
