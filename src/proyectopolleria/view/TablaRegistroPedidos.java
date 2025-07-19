package proyectopolleria.view;

import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import proyectopolleria.controller.ol.GestionDatosPedido;
import proyectopolleria.dao.Impl.ClienteDaoImpl;
import proyectopolleria.dao.Impl.PedidoDaoImpl;
import proyectopolleria.model.Pedido;
import proyectopolleria.service.Impl.ClienteServiceImpl;
import proyectopolleria.service.Impl.PedidoServiceImpl;
import proyectopolleria.service.interfaz.ClienteService;
import proyectopolleria.service.interfaz.PedidoService;
import proyectopolleria.service.interfaz.TrabajadorService;
import proyectopolleria.util.Conexion;
import java.sql.Connection;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JTable;
import proyectopolleria.dao.DaoException;
import proyectopolleria.dao.Impl.TrabajadorDaoImpl;
import proyectopolleria.model.Cliente;
import proyectopolleria.model.Trabajador;
import proyectopolleria.service.Impl.TrabajadorServiceImpl;
import proyectopolleria.util.ExportExcel;

public class TablaRegistroPedidos extends javax.swing.JFrame {

    private ClienteService clienteService;
    private TrabajadorService trabajadorService;
    private PedidoService pedidoService;
    private DefaultTableModel model;
    private Pedido pedido;

    public TablaRegistroPedidos() {
        initComponents();
        setLocationRelativeTo(null);

        Connection conn = Conexion.getInstancia().getConexion();
        clienteService = new ClienteServiceImpl(new ClienteDaoImpl(conn));
        trabajadorService = new TrabajadorServiceImpl(new TrabajadorDaoImpl(conn));
        pedidoService = new PedidoServiceImpl(new PedidoDaoImpl(conn));

        model = (DefaultTableModel) jTable1.getModel();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        cargarPedidosDesdeBD();
        jTable1.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent mouseEvent) {
                JTable table = (JTable) mouseEvent.getSource();
                Point point = mouseEvent.getPoint();
                int row = table.rowAtPoint(point);
                if (mouseEvent.getClickCount() == 1) {
                    llenarDatos();
                }
            }
        });

    }

    // Método para cargar los datos desde el archivo CSV
    private void cargarPedidosDesdeBD() {
        try {
            List<Pedido> pedidos = pedidoService.listarTodos();
            model.setRowCount(0); // Limpiar tabla

            for (Pedido p : pedidos) {
                Cliente cliente = clienteService.obtenerPorId(p.getIdCliente());
                if (cliente == null) {
                    continue;
                }

                String nombreCliente = cliente.getNombre();
                String dniCliente = cliente.getDni();
                String nombreTrabajador = "N/A";

                if (p.getTipo() == Pedido.tipoPedido.SALON && p.getIdMOZO() != null) {
                    Trabajador mozo = trabajadorService.obtenerPorId(p.getIdMOZO());
                    if (mozo != null) {
                        nombreTrabajador = mozo.getNombre();
                    }
                } else if (p.getTipo() == Pedido.tipoPedido.DELIVERY && p.getIdDELIVERY() != null) {
                    Trabajador delivery = trabajadorService.obtenerPorId(p.getIdDELIVERY());
                    if (delivery != null) {
                        nombreTrabajador = delivery.getNombre();
                    }
                }

                Object[] row = {
                    p.getId(),
                    nombreCliente,
                    p.getFechaHora(),
                    dniCliente,
                    nombreTrabajador,
                    p.getTipo().toString(),
                    "-", // Cantidad o descripción de productos: implementar luego
                    p.getTotal()
                };
                model.addRow(row);
            }
        } catch (DaoException e) {
            JOptionPane.showMessageDialog(this, "Error cargando pedidos: " + e.getMessage());
        }
    }

    private void llenarDatos() {
        Cliente cliente = new Cliente();
        cliente.setId(Integer.valueOf(jTable1.getValueAt(jTable1.getSelectedRow(), 0).toString()));
        cliente.setDni(jTable1.getValueAt(jTable1.getSelectedRow(), 1).toString());
        cliente.setNombre(jTable1.getValueAt(jTable1.getSelectedRow(), 2).toString());
        cliente.setTelefono(jTable1.getValueAt(jTable1.getSelectedRow(), 3).toString());
        cliente.setDireccion(jTable1.getValueAt(jTable1.getSelectedRow(), 4).toString());
        cliente.setReferencia(jTable1.getValueAt(jTable1.getSelectedRow(), 5).toString());
    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jTextField1 = new javax.swing.JTextField();
        jButton3 = new javax.swing.JToggleButton();
        jButton1 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setText("Registros de pedidos");

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "N° Pedido", "Cliente", "Fecha", "DNI", "Trabajador", "Tipo", "Cant", "Total"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.Double.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

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

        jButton3.setBackground(new java.awt.Color(108, 117, 125));
        jButton3.setForeground(new java.awt.Color(255, 255, 255));
        jButton3.setText("Buscar");
        jButton3.setBorderPainted(false);
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton1.setBackground(new java.awt.Color(237, 33, 58));
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Eliminar registro");
        jButton1.setBorderPainted(false);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel2.setText("Ingrese N° Pedido:");

        jButton2.setBackground(new java.awt.Color(238, 156, 32));
        jButton2.setForeground(new java.awt.Color(255, 255, 255));
        jButton2.setText("Generar Reporte");
        jButton2.setBorderPainted(false);
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 303, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton3)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 267, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(65, 65, 65)
                        .addComponent(jButton2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 780, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton3)
                    .addComponent(jButton1)
                    .addComponent(jButton2))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 358, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Pedidos", jPanel1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        ExportExcel.exportarJTableAExcel(jTable1);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        try {
            obtenerIdEliminar();
            pedidoService.eliminarPedido(pedido);
            model.setRowCount(0);
            cargarPedidosDesdeBD();
        } catch (DaoException ex) {
            Logger.getLogger(TablaCliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void obtenerIdEliminar(){
        pedido = new Pedido();
        pedido.setId(Integer.valueOf(jTable1.getValueAt(jTable1.getSelectedRow(), 0).toString()));
    }
    
    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        String textoBuscado = jTextField1.getText().trim();
        if (textoBuscado.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, ingresa un valor para buscar.", "Campo vacío", JOptionPane.WARNING_MESSAGE);
            return;
        }
        for (int i = 0; i < jTable1.getRowCount(); i++) {
            String valorEnTabla = jTable1.getValueAt(i, 0).toString();
            if (valorEnTabla.equalsIgnoreCase(textoBuscado)) {
                jTable1.setRowSelectionInterval(i, i);
                jTable1.scrollRectToVisible(jTable1.getCellRect(i, 0, true));
                return;
            }
        }
        JOptionPane.showMessageDialog(this, "No se encontraron coincidencias.", "Búsqueda", JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1ActionPerformed

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
            java.util.logging.Logger.getLogger(TablaRegistroPedidos.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TablaRegistroPedidos.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TablaRegistroPedidos.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TablaRegistroPedidos.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
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
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables
}
