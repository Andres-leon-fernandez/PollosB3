package proyectopolleria.view;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import proyectopolleria.controller.ol.GestionDatosPedido;

public class TablaRegistroPedidos extends javax.swing.JFrame {
    
    private GestionDatosPedido gestionDatosPedido;
    private String[][] datos;

    public TablaRegistroPedidos() {
        initComponents();
        setLocationRelativeTo(null);
        // Establece la ruta para guardar los datos o cargar los datos
        gestionDatosPedido = new GestionDatosPedido("ruta_del_archivo.csv");

        // Cargar datos desde el archivo CSV
        if (cargarDatosDesdeArchivo()) {
            // Ordenar los datos por el número de pedido usando Quick Sort
            quickSort(datos, 0, datos.length - 1);
            // Mostrar los datos en la tabla
            mostrarDatosEnTabla();
        } else {
            JOptionPane.showMessageDialog(this, "No se pudieron cargar los datos desde el archivo CSV.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    // Método para cargar los datos desde el archivo CSV
    private boolean cargarDatosDesdeArchivo() {
        List<String[]> data = gestionDatosPedido.cargarDatos();// Cargar datos desde el archivo CSV
        if (data != null && !data.isEmpty()) {
            datos = new String[data.size()][];// Inicia la matriz de datos con el tamaño requerido
            datos = data.toArray(datos);// Convertir la lista de datos en una matriz
            return true;   
        }
        return false;
    }
    // Método para mostrar los datos en la tabla
    private void mostrarDatosEnTabla() {
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel(); //Obtener el modelo de la tabla
        model.setRowCount(0); // Limpiar la tabla antes de mostrar nuevos datos
        for (String[] row : datos) {
            model.addRow(row);// Agrega cada fila de datos al modelo de la tabla
        }
    }
    // Método para ordenar los datos usando Quick Sort
    private void quickSort(String[][] array, int low, int high) {
        if (low < high) {
            int pi = partition(array, low, high);// Obtener el índice del pivote
            quickSort(array, low, pi - 1);// Ordenar recursivamente los elementos antes del pivote
            quickSort(array, pi + 1, high);// Ordenar recursivamente los elementos después del pivote
        }
    }
    // Método auxiliar para particionar la Matriz en Quick Sort
    private int partition(String[][] array, int low, int high) {
        String[] pivot = array[high];// Seleccionar el último elemento como pivote
        int i = (low - 1);// Índice del elemento más pequeño
        for (int j = low; j < high; j++) {
            if (array[j][2].compareTo(pivot[2]) < 0) { // Si el elemento actual es menor o igual al pivote
                i++;// Incrementar el índice del elemento más pequeño
                String[] temp = array[i];  // Intercambiar array[i] y array[j]
                array[i] = array[j];
                array[j] = temp;
            }
        }
        // Intercambiar array[i + 1] y array[high]
        String[] temp = array[i + 1];
        array[i + 1] = array[high];
        array[high] = temp;
        // Retornar el índice del pivote después del particionamiento
        return i + 1;
    }
    // Método para buscar un registro por el número de pedido usando búsqueda binaria
    private int buscarPorNumeroPedido(String[][] array, String numeroPedido) {
    int left = 0;// Índice del extremo izquierdo
    int right = array.length - 1;// Índice del extremo derecho
    while (left <= right) {
        int mid = left + (right - left) / 2;// Calcular el índice del elemento medio
        int cmp = array[mid][2].compareTo(numeroPedido);// Comparar el número de pedido con el elemento medio
        if (cmp == 0) {
            return mid;// Retornar el índice del elemento si se encuentra el número de pedido
        }
        if (cmp < 0) {// Si el número de pedido es mayor, buscar en la mitad derecha
            left = mid + 1;
        } else {// Si el número de pedido es menor, buscar en la mitad izquierda
            right = mid - 1;
        }
    }
    return -1;// Retornar -1 si el número de pedido no se encuentra en la matriz
    }
    // Método para eliminar el registro del archivo CSV
    private void eliminarRegistroCSV(int rowIndex) {
    try {
        // Cargar todos los datos desde el archivo CSV
        List<String[]> data = gestionDatosPedido.cargarDatos();
        
        // Eliminar el registro correspondiente al índice rowIndex
        data.remove(rowIndex);
        
        // Sobrescribir el archivo CSV con los datos actualizados
        guardarDatosEnArchivo(data);
    } catch (Exception e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "Error al eliminar el registro del archivo CSV.", "Error", JOptionPane.ERROR_MESSAGE);
    }
    }
    // Método para guardar los datos en el archivo CSV
    private void guardarDatosEnArchivo(List<String[]> data) {
    try (Writer writer = new FileWriter("ruta_del_archivo.csv")) {
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
        JOptionPane.showMessageDialog(this, "Error al guardar datos en el archivo CSV.", "Error", JOptionPane.ERROR_MESSAGE);
    }
    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jTextField1 = new javax.swing.JTextField();
        jButton3 = new javax.swing.JToggleButton();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setText("Registros de pedidos");

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Usuario", "Fecha del pedido", "N° Pedido", "Mesa", "Cliente", "DNI", "Mozo", "Cod. Producto", "Descripcion", "Cant", "Total"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(jTable1);

        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });

        jButton3.setText("Buscar");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton1.setText("Eliminar registro");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Salir");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel2.setText("Buscar por numero de pedido");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(44, 44, 44)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jButton2)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 753, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 241, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(jButton3)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(56, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(325, 325, 325))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addGap(13, 13, 13)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton3)
                    .addComponent(jButton1))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 325, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 24, Short.MAX_VALUE)
                .addComponent(jButton2)
                .addGap(33, 33, 33))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
    // Botón de buscar
    String numeroPedido = jTextField1.getText().trim();
    if (!numeroPedido.isEmpty()) {
        int index = buscarPorNumeroPedido(datos, numeroPedido);
        if (index != -1) {
            // Limpiar la tabla antes de mostrar los resultados de la búsqueda
            DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
            model.setRowCount(0);
            
            // Mostrar solo la fila correspondiente al resultado de la búsqueda
            model.addRow(datos[index]);
            
            // Seleccionar la fila correspondiente en la tabla
            jTable1.setRowSelectionInterval(0, 0);
            jTable1.scrollRectToVisible(jTable1.getCellRect(0, 0, true));
        } else {
            JOptionPane.showMessageDialog(this, "Número de pedido no encontrado.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    } else {
        JOptionPane.showMessageDialog(this, "Por favor, ingrese un número de pedido.", "Error", JOptionPane.ERROR_MESSAGE);
    }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
    // Botón de eliminar registro
    int selectedRow = jTable1.getSelectedRow();
    if (selectedRow != -1) {
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        // Iterar sobre cada columna de la fila y establecerla como una cadena vacía
        for (int i = 0; i < model.getColumnCount(); i++) {
            model.setValueAt("", selectedRow, i);
        }
        
        // También eliminar el registro del archivo CSV
        eliminarRegistroCSV(selectedRow);
    } else {
        JOptionPane.showMessageDialog(this, "Por favor, seleccione un registro para eliminar.", "Error", JOptionPane.ERROR_MESSAGE);
    }
    }//GEN-LAST:event_jButton1ActionPerformed
    
    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        this.dispose();
    }//GEN-LAST:event_jButton2ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(TablaRegistroPedidos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TablaRegistroPedidos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TablaRegistroPedidos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TablaRegistroPedidos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TablaRegistroPedidos().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JToggleButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables
}
