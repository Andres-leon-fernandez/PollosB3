package proyectopolleria.controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GestionDatosUsuario {
    private static final String COMMA_DELIMITER = ",";
    private static final String NEW_LINE_SEPARATOR = "\n";

    // Método para guardar datos en un archivo CSV
    public static void guardarDatosCSV(String fileName, String[] headers, Object[][] data) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            // Escribir encabezados
            for (String header : headers) {
                writer.append(header).append(COMMA_DELIMITER);
            }
            writer.append(NEW_LINE_SEPARATOR);

            // Escribir datos
            for (Object[] row : data) {
                for (Object value : row) {
                    writer.append(value.toString()).append(COMMA_DELIMITER);
                }
                writer.append(NEW_LINE_SEPARATOR);
            }

            System.out.println("Los datos han sido guardados correctamente en el archivo CSV.");
        } catch (IOException e) {
            System.err.println("Error al escribir en el archivo CSV: " + e.getMessage());
        }
    }

    // Método para cargar datos desde un archivo CSV
    public static Object[][] cargarDatosDesdeCSV(String fileName, String[] headers) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            // Leer encabezados (primera línea)
            String line = reader.readLine();
            String[] headerValues = line.split(COMMA_DELIMITER);

            // Verificar si los encabezados coinciden
            if (!Arrays.equals(headerValues, headers)) {
                System.err.println("Los encabezados del archivo CSV no coinciden con los esperados.");
                return null;
            }

            // Leer datos
            List<Object[]> dataList = new ArrayList<>();
            while ((line = reader.readLine()) != null) {
                String[] values = line.split(COMMA_DELIMITER);
                dataList.add(values);
            }

            // Convertir la lista de datos a un array bidimensional
            Object[][] data = new Object[dataList.size()][headers.length];
            for (int i = 0; i < dataList.size(); i++) {
                data[i] = dataList.get(i);
            }

            return data;
        } catch (IOException e) {
            System.err.println("Error al leer el archivo CSV: " + e.getMessage());
            return null;
        }
    }
}
