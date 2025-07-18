/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyectopolleria.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JOptionPane;
import javax.swing.JTable;

/**
 *
 * @author Administrador
 */
public class ExportExcel {
    private String getDesktopFolderPath() {
        String os = System.getProperty("os.name").toLowerCase();
        String homePath = System.getProperty("user.home");

        if (os.contains("win")) {
            return "C:\\Users\\LAB-USR-ATE\\Desktop";
        } 
        return homePath;
    }

    /**
     * Exporta los datos de un JTable a un archivo CSV en la carpeta de Descargas.
     * @param table El JTable a exportar.
     * @param fileName El nombre del archivo CSV a generar (ej. "datos.csv").
     */
    public String exportJTableToCsv(JTable table, String fileName) {
        String downloadsPath = getDesktopFolderPath();
        File outputFile = new File(downloadsPath, fileName);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {
            // Escribir los encabezados de las columnas
            for (int i = 0; i < table.getColumnCount(); i++) {
                writer.write(escapeCsv(table.getColumnName(i)));
                if (i < table.getColumnCount() - 1) {
                    writer.write(","); // Delimitador de columnas
                }
            }
            writer.newLine(); // Nueva línea después de los encabezados

            // Escribir los datos de las filas
            for (int rows = 0; rows < table.getRowCount(); rows++) {
                for (int cols = 0; cols < table.getColumnCount(); cols++) {
                    Object value = table.getValueAt(rows, cols);
                    String cellValue = (value != null) ? value.toString() : "";
                    writer.write(escapeCsv(cellValue)); // Escapar el valor para CSV
                    if (cols < table.getColumnCount() - 1) {
                        writer.write(","); // Delimitador de columnas
                    }
                }
                writer.newLine(); // Nueva línea después de cada fila
            }

            return "Reporte CSV generado exitosamente en: " + outputFile.getAbsolutePath();

        } catch (IOException ex) {
            ex.printStackTrace();
            return "Error al generar el archivo CSV: " + ex.getMessage();
        }
    }

    /**
     * Escapa un valor de cadena para ser utilizado en un archivo CSV.
     * Esto maneja comas, comillas dobles y saltos de línea dentro de un valor.
     * @param value La cadena a escapar.
     * @return La cadena escapada.
     */
    private String escapeCsv(String value) {
        if (value == null) {
            return "";
        }
        String escapedValue = value.replace("\"", "\"\""); // Escapar comillas dobles
        if (escapedValue.contains(",") || escapedValue.contains("\"") || escapedValue.contains("\n") || escapedValue.contains("\r")) {
            return "\"" + escapedValue + "\""; // Entrecomillar si contiene delimitadores o comillas
        }
        return escapedValue;
    }
}
