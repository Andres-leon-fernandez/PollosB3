package proyectopolleria.view;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.List;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import proyectopolleria.controller.ol.GestionDatosPedido;
import proyectopolleria.util.MenuPolleria;


public class TablaPedidos extends javax.swing.JFrame {
    
    private MenuPolleria menu;
    private static int contadorPedidos = 0;
    private String usuario;
    private String fechaHora;
    private GestionDatosPedido gestionDatos;
    
    public TablaPedidos(String usuario, String fechaHora) {
        initComponents();
        jPanel1.setOpaque(false);
        setLocationRelativeTo(null);
        contadorPedidos = NumeroPedidoManager.cargarNumeroPedido();
        // Crea una instancia de la clase MenuPolleria
        menu = new MenuPolleria();
        
        //Asigna valores para fecha y hora
        this.usuario = usuario;
        this.fechaHora = fechaHora;
        jTextField1.setText(this.usuario);
        jTextField2.setText(this.fechaHora);
        
        for (MenuPolleria.Producto producto : menu.getAllItems()) {
            jComboBox3.addItem(producto.getNombre());
        }  
        jComboBox3.addActionListener((java.awt.event.ActionEvent evt) -> {
            jComboBox3ActionPerformed(evt);
        });    
        
        jTextField3.setText(String.valueOf(contadorPedidos));
        
        gestionDatos = new GestionDatosPedido("ruta_del_archivo.csv");
        
        List<String> nombresMozos = cargarNombresMozos("mozos.csv");
        for (String nombre : nombresMozos) {
        jComboBox2.addItem(nombre);
        }
    }
    public static List<String> cargarNombresMozos(String csvFile) {
    List<String> nombresMozos = new ArrayList<>();
    try (BufferedReader reader = new BufferedReader(new FileReader(csvFile))) {
        String line;
        while ((line = reader.readLine()) != null) {
            String[] data = line.split(",");
            if (data.length == 3) {
                String apellidos = data[0];
                String nombres = data[1];
                String dni = data[2];
                // Agregar solo el nombre a la lista
                nombresMozos.add(nombres);
            }
        }
    } catch (IOException e) {
        e.printStackTrace();
    }
    return nombresMozos;
}   
    public class NumeroPedidoManager {
    private static final String NUMERO_PEDIDO_FILE = "numero_pedido.txt";

     public static int cargarNumeroPedido() {
        try (BufferedReader reader = new BufferedReader(new FileReader(NUMERO_PEDIDO_FILE))) {
            String numeroPedidoStr = reader.readLine();
            return Integer.parseInt(numeroPedidoStr);
        } catch (IOException | NumberFormatException e) {
            // En caso de error o si el archivo no existe, se devuelve 0
            return 0;
        }
    }

    public static void guardarNumeroPedido(int numeroPedido) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(NUMERO_PEDIDO_FILE))) {
            writer.write(String.valueOf(numeroPedido));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
    //Metodo para guardar los datos del pedido
    private void grabarDatos() {
        // Obtener datos de los componentes
        String idusuario = jTextField1.getText();
        String fecha = jTextField2.getText();
        String npedido = jTextField3.getText();
        String mesa = (String) jComboBox1.getSelectedItem();
        String cliente = jTextField4.getText();
        String dni = jTextField5.getText();
        String mozo = (String) jComboBox2.getSelectedItem();
        String codproducto = jTextField6.getText();
        String descripcion = (String) jComboBox3.getSelectedItem();
        String cantidad = jTextField7.getText();
        String total = jTextField9.getText();

        // Validar datos
        if (cliente.trim().isEmpty() || !dni.matches("\\d{8}") || descripcion.equals("Elija un producto")) {
        JOptionPane.showMessageDialog(this, "Ingrese correctamente los datos del cliente y seleccione un producto.", "No registrado", JOptionPane.ERROR_MESSAGE);
        return;
    }

    // Crear un arreglo con los datos a guardar en el archivo CSV
    String[] rowData = {idusuario, fecha, npedido, mesa, cliente, dni, mozo, codproducto, descripcion, cantidad, total};

    // Guardar los datos en el archivo CSV
    List<String[]> data = new ArrayList<>();
    data.add(rowData);
    gestionDatos.guardarDatos(data);

    // Incrementar el contador de pedidos
    contadorPedidos++;
    
    // Actualizar el número de pedido en la interfaz gráfica
    jTextField3.setText(String.valueOf(contadorPedidos));

    JOptionPane.showMessageDialog(this, "Pedido registrado exitosamente.", "Registrado", JOptionPane.INFORMATION_MESSAGE);
}
    
    public String getUsuario() {
        return usuario;
    }
        
    //Calcula la suma se los valores
    private void updateTotal() {
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        double total = 0;
        for (int i = 0; i < model.getRowCount(); i++) {
        total += Double.parseDouble(model.getValueAt(i, 3).toString()); 
    }
        
    //Muestra la suma de los importes que estan agregados en la tabla
    jTextField9.setText(String.format("%.2f", total));
}

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();
        jComboBox1 = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jTextField4 = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jTextField5 = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jComboBox2 = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jTextField2 = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        jButton3 = new javax.swing.JButton();
        jLabel12 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jTextField6 = new javax.swing.JTextField();
        jComboBox3 = new javax.swing.JComboBox<>();
        jTextField7 = new javax.swing.JTextField();
        jTextField8 = new javax.swing.JTextField();
        panel1 = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel13 = new javax.swing.JLabel();
        jTextField9 = new javax.swing.JTextField();
        jButton2 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jLabel14 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setText("Fecha y hora");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 30, -1, -1));

        jLabel3.setText("Pedido N°0001 -");
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(22, 82, -1, -1));

        jTextField3.setEditable(false);
        jTextField3.setBackground(new java.awt.Color(204, 204, 204));
        getContentPane().add(jTextField3, new org.netbeans.lib.awtextra.AbsoluteConstraints(114, 79, 90, -1));

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Ninguno", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10" }));
        jComboBox1.setName(""); // NOI18N
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });
        getContentPane().add(jComboBox1, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 141, 154, -1));

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel4.setText("Informacion de venta");
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 20, 266, 74));

        jLabel5.setText("N° Mesa:");
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(104, 144, -1, -1));

        jLabel6.setText("Cliente:");
        getContentPane().add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(104, 184, 48, -1));

        jTextField4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField4ActionPerformed(evt);
            }
        });
        getContentPane().add(jTextField4, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 181, 213, -1));

        jLabel7.setText("DNI:");
        getContentPane().add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(423, 184, -1, -1));
        getContentPane().add(jTextField5, new org.netbeans.lib.awtextra.AbsoluteConstraints(464, 181, 117, -1));

        jLabel8.setText("Mozo(a):");
        getContentPane().add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(104, 224, -1, -1));

        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Ninguno" }));
        jComboBox2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox2ActionPerformed(evt);
            }
        });
        getContentPane().add(jComboBox2, new org.netbeans.lib.awtextra.AbsoluteConstraints(169, 221, 214, -1));

        jLabel1.setText("Usuario");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(22, 19, -1, -1));

        jTextField1.setEditable(false);
        jTextField1.setBackground(new java.awt.Color(204, 204, 204));
        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });
        getContentPane().add(jTextField1, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 16, 82, -1));

        jTextField2.setEditable(false);
        jTextField2.setBackground(new java.awt.Color(204, 204, 204));
        jTextField2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField2ActionPerformed(evt);
            }
        });
        getContentPane().add(jTextField2, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 20, 140, -1));

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jButton3.setText("Atras");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(642, 660, 90, 66));

        jLabel12.setText("Importe");
        jPanel1.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 400, -1, -1));

        jLabel11.setText("Cantidad");
        jPanel1.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 360, -1, -1));

        jLabel10.setText("Descripción");
        jPanel1.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 320, -1, -1));

        jLabel9.setText("Codigo");
        jPanel1.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 280, -1, -1));

        jTextField6.setEditable(false);
        jTextField6.setBackground(new java.awt.Color(204, 204, 204));
        jTextField6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField6ActionPerformed(evt);
            }
        });
        jPanel1.add(jTextField6, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 280, 90, -1));

        jComboBox3.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Elija un producto" }));
        jComboBox3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox3ActionPerformed(evt);
            }
        });
        jPanel1.add(jComboBox3, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 320, 190, -1));
        jPanel1.add(jTextField7, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 360, 90, -1));

        jTextField8.setEditable(false);
        jTextField8.setBackground(new java.awt.Color(204, 204, 204));
        jTextField8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField8ActionPerformed(evt);
            }
        });
        jPanel1.add(jTextField8, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 400, 90, -1));

        panel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Informacion del producto"));
        panel1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                panel1ActionPerformed(evt);
            }
        });
        jPanel1.add(panel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 260, 520, 190));

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "N° Pedido", "Descripción", "Cantidad", "Precio"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 470, 700, 120));

        jLabel13.setText("Total:");
        jPanel1.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 610, -1, -1));

        jTextField9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField9ActionPerformed(evt);
            }
        });
        jPanel1.add(jTextField9, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 610, 130, -1));

        jButton2.setText("Borrar item");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 660, -1, 66));

        jButton1.setText("Registrar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 660, -1, 66));

        jButton4.setText("Agregar");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton4, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 410, -1, -1));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 830, 760));

        jLabel14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/proyectopolleria/resourse/img/imagen.png"))); // NOI18N
        jLabel14.setText("jLabel14");
        getContentPane().add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 860, 780));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTextField4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField4ActionPerformed

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox1ActionPerformed

    private void panel1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_panel1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_panel1ActionPerformed

    private void jTextField8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField8ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField8ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
    DefaultTableModel model = (DefaultTableModel) jTable1.getModel(); // Obtener referencia al modelo de la tabla
    int selectedRow = jTable1.getSelectedRow();
    if (selectedRow != -1) {
        model.removeRow(selectedRow);
        //Llama al metodo para calcular la suma
        updateTotal();
    }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        this.dispose();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jTextField9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField9ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField9ActionPerformed

    private void jComboBox3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox3ActionPerformed
    
        String nombreSeleccionado = (String) jComboBox3.getSelectedItem();
        for (MenuPolleria.Producto producto : menu.getAllItems()) {
            if (producto.getNombre().equals(nombreSeleccionado)) {
                jTextField6.setText(producto.getCodigo());
                jTextField8.setText(String.valueOf(producto.getPrecio()));
                break;
            }
        }
                      
    }//GEN-LAST:event_jComboBox3ActionPerformed

    private void jTextField6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField6ActionPerformed

    }//GEN-LAST:event_jTextField6ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
    // Obtener datos del formulario
    String pedidoNumber = jTextField3.getText();
    String descripcion = (String) jComboBox3.getSelectedItem();
    String cantidadStr = jTextField7.getText();
    String importeStr = jTextField8.getText();
    String clienteNombre = jTextField4.getText();
    String dni = jTextField5.getText();

    // Validar si la cantidad es numérica y positiva
    try {
        int cantidad = Integer.parseInt(cantidadStr);
        if (cantidad <= 0) {
            JOptionPane.showMessageDialog(this, "La cantidad debe ser un número entero positivo.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Validar que el cliente tenga un nombre
        if (clienteNombre.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese el nombre del cliente.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        //verifica que el dni tenga 8 digitos
        if (!dni.matches("\\d{8}")) {
            JOptionPane.showMessageDialog(this, "El DNI debe tener exactamente 8 dígitos numéricos.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Calcular el precio multiplicando la cantidad por el importe
        double importe = Double.parseDouble(importeStr);
        double precio = cantidad * importe;

        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();

        // Limpiar filas vacías en la tabla
        for (int i = model.getRowCount() - 1; i >= 0; i--) {
            if (model.getValueAt(i, 0) == null || model.getValueAt(i, 0).toString().isEmpty()) {
                model.removeRow(i);
            }
        }

        // Agregar nueva fila con los datos calculados
        model.addRow(new Object[]{pedidoNumber, descripcion, cantidadStr, String.format("%.2f", precio)});
        
        //Llama a la funcion que calcula la suma total
        updateTotal();
    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(this, "La cantidad debe ser un número entero válido.", "Error", JOptionPane.ERROR_MESSAGE);
    }
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jTextField2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField2ActionPerformed

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        grabarDatos();
        NumeroPedidoManager.guardarNumeroPedido(contadorPedidos);
        contadorPedidos++;
        // Limpiar la tabla
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        model.setRowCount(0);
        jComboBox1.setSelectedIndex(0);
        jComboBox2.setSelectedIndex(0);
        jComboBox3.setSelectedIndex(0);
        jTextField4.setText("");
        jTextField5.setText("");
        jTextField6.setText("");
        jTextField7.setText("");
        jTextField7.setText("");
        jTextField8.setText("");
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jComboBox2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox2ActionPerformed
        String nombreMozoSeleccionado = (String) jComboBox2.getSelectedItem();
        JOptionPane.showMessageDialog(this, "Mozo seleccionado: " + nombreMozoSeleccionado, "Mozo seleccionado", JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_jComboBox2ActionPerformed

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
            java.util.logging.Logger.getLogger(TablaPedidos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TablaPedidos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TablaPedidos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TablaPedidos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TablaPedidos("Prueba", "Prueba").setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JComboBox<String> jComboBox3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextField jTextField6;
    private javax.swing.JTextField jTextField7;
    private javax.swing.JTextField jTextField8;
    private javax.swing.JTextField jTextField9;
    private javax.swing.JTextField panel1;
    // End of variables declaration//GEN-END:variables
}
