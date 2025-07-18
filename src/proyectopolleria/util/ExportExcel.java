package proyectopolleria.util;

import javax.swing.*;
import javax.swing.table.TableModel;
import java.io.*;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author Sebastian Onofre
 */
public class ExportExcel {
    /**
     * Exporta los datos de un JTable a un archivo Excel (.xlsx)
     * Abre un diálogo para que el usuario seleccione dónde guardar el archivo.
     *
     * Ejemplo de uso:
     * ExportExcel.exportarJTableAExcel(miJTable);
     *
     * @param table El JTable que contiene los datos a exportar
     */

    public static void exportarJTableAExcel(JTable table) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Guardar archivo Excel");
        fileChooser.setSelectedFile(new File("datos.xlsx"));

        int userSelection = fileChooser.showSaveDialog(null);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File archivo = fileChooser.getSelectedFile();

            try (Workbook workbook = new XSSFWorkbook()) {
                Sheet sheet = workbook.createSheet("Datos");

                TableModel model = table.getModel();

                // Escribir encabezados
                Row headerRow = sheet.createRow(0);
                for (int col = 0; col < model.getColumnCount(); col++) {
                    Cell cell = headerRow.createCell(col);
                    cell.setCellValue(model.getColumnName(col));
                }

                // Escribir datos
                for (int row = 0; row < model.getRowCount(); row++) {
                    Row excelRow = sheet.createRow(row + 1);
                    for (int col = 0; col < model.getColumnCount(); col++) {
                        Cell cell = excelRow.createCell(col);
                        Object value = model.getValueAt(row, col);
                        cell.setCellValue(value != null ? value.toString() : "");
                    }
                }

                // Guardar archivo
                try (FileOutputStream out = new FileOutputStream(archivo)) {
                    workbook.write(out);
                    JOptionPane.showMessageDialog(null, "Archivo guardado exitosamente.");
                }

            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Error al guardar el archivo: " + e.getMessage());
            }
        }
    }

}
