/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package proyectopolleria.view;

import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import proyectopolleria.service.interfaz.ProductoService;
import proyectopolleria.service.interfaz.ProveedorService;
import java.sql.Connection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import proyectopolleria.dao.DaoException;
import proyectopolleria.dao.Impl.InsumoDaoImpl;
import proyectopolleria.dao.Impl.ProductoDaoImpl;
import proyectopolleria.dao.Impl.ProveedorDaoImpl;
import proyectopolleria.model.Insumo;
import proyectopolleria.model.Producto;
import proyectopolleria.model.Proveedor;
import proyectopolleria.service.Impl.InsumoServiceImpl;
import proyectopolleria.service.Impl.ProductoServiceImpl;
import proyectopolleria.service.Impl.ProveedorServiceImp;
import proyectopolleria.service.interfaz.InsumoService;
import proyectopolleria.util.Conexion;

public class tb_gestion_de_insumos extends javax.swing.JFrame {

    private ProveedorService proveedorservice;
    private InsumoService insumoService;
    DefaultTableModel model;
    private Connection conn;
    private int rowselec;

    public tb_gestion_de_insumos() {
        initComponents();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        conn = Conexion.getInstancia().getConexion();
        model = (DefaultTableModel) productoTabla.getModel();
        insumoService = new InsumoServiceImpl(new InsumoDaoImpl(conn));
        proveedorservice = new ProveedorServiceImp(new ProveedorDaoImpl(conn));

        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

        cargarTabla();
        mostrarTipoCombo();
        mostrarProveedorCb();

        productoTabla.addMouseListener(new MouseAdapter() {
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

    private void llenarDatos() {
        Insumo insInter = new Insumo();
        Proveedor provinter = new Proveedor();
        insInter.setId(Integer.valueOf(productoTabla.getValueAt(productoTabla.getSelectedRow(), 0).toString()));
        insInter.setNombre(String.valueOf(productoTabla.getValueAt(productoTabla.getSelectedRow(), 1).toString()));
        insInter.setStock(Double.parseDouble(productoTabla.getValueAt(productoTabla.getSelectedRow(), 2).toString()));
        insInter.setStockMin(Double.parseDouble(productoTabla.getValueAt(productoTabla.getSelectedRow(), 3).toString()));
        insInter.setUnidad(Insumo.Unidad.valueOf(productoTabla.getValueAt(productoTabla.getSelectedRow(), 4).toString()));
        insInter.setPrecioUnitario(Double.parseDouble(productoTabla.getValueAt(productoTabla.getSelectedRow(), 5).toString()));

        provinter.setNombre(String.valueOf(productoTabla.getValueAt(productoTabla.getSelectedRow(), 6).toString()));
        provinter.setRuc(String.valueOf(productoTabla.getValueAt(productoTabla.getSelectedRow(), 7).toString()));
        provinter.setCorreo(String.valueOf(productoTabla.getValueAt(productoTabla.getSelectedRow(), 8).toString()));
        provinter.setDireccion(String.valueOf(productoTabla.getValueAt(productoTabla.getSelectedRow(), 9).toString()));

        txtNombre.setText(insInter.getNombre());
        txtStockActual.setText(String.valueOf(insInter.getStock()));
        txtStockMin.setText(String.valueOf(insInter.getStockMin()));
        txtPrecioU.setText(String.valueOf(insInter.getPrecioUnitario()));

        CbUnidad.setSelectedItem(insInter.getUnidad());
        cbProveedor.setSelectedItem(provinter.getNombre());

    }

    private Proveedor extraerDatosProveedor() throws DaoException {
        List<Proveedor> Proveedores = proveedorservice.listarTodos();
        Proveedor p = new Proveedor();
        String seleccionado = cbProveedor.getSelectedItem().toString();

        for (Proveedor pro : Proveedores) {
            if (pro.getNombre().trim().toLowerCase().equals(seleccionado.trim().toLowerCase())) {
                p = pro;
                return p;
            }
            System.out.println(p.getNombre());
        }
        System.out.println(p.getNombre());
        return null;
    }

    private void guardarInsumo() throws DaoException {
        Insumo ins = new Insumo();
        ins.setNombre(txtNombre.getText());
        ins.setStock(Double.parseDouble(txtStockActual.getText()));
        ins.setStockMin(Double.parseDouble(txtStockMin.getText()));
        ins.setPrecioUnitario(Double.parseDouble(txtPrecioU.getText()));
        ins.setUnidad((Insumo.Unidad) CbUnidad.getSelectedItem());

        Proveedor p = extraerDatosProveedor();
        if (p == null) {
            JOptionPane.showMessageDialog(null, "Debe seleccionar un proveedor v�lido.", "Error", JOptionPane.ERROR_MESSAGE);
            return; // Evita continuar si no hay proveedor
        }

        ins.setIdProveedor(p.getId());
        insumoService.registrarInsumo(ins);
        model.setRowCount(0);
        cargarTabla();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTree1 = new javax.swing.JTree();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        productoTabla = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        CbUnidad = new javax.swing.JComboBox<>();
        cbProveedor = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
        txtStockActual = new javax.swing.JTextField();
        txtStockMin = new javax.swing.JTextField();
        txtPrecioU = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        BtGuardar = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();

        jScrollPane1.setViewportView(jTree1);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        productoTabla.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "id", "nombre", "stock Actual", "stock minimo", "unidad", "precio unitario", "Nombre Proveedor", "Ruc", "Correo", "Numero"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.String.class, java.lang.Double.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        productoTabla.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                productoTablaMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(productoTabla);

        jLabel1.setText("Nombre:");

        jLabel2.setText("Stock Actual:");

        jLabel3.setText("Stock Min:");

        jLabel4.setText("Precio Unitario");

        CbUnidad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CbUnidadActionPerformed(evt);
            }
        });

        jLabel5.setText("Unidad:");

        jLabel6.setText("Proveedor:");

        BtGuardar.setText("Agregar");
        BtGuardar.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                BtGuardarFocusGained(evt);
            }
        });
        BtGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtGuardarActionPerformed(evt);
            }
        });

        jButton2.setText("Eliminar");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText("Actualizar");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap(71, Short.MAX_VALUE)
                .addComponent(BtGuardar)
                .addGap(29, 29, 29)
                .addComponent(jButton2)
                .addGap(26, 26, 26)
                .addComponent(jButton3)
                .addGap(48, 48, 48))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap(10, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton3)
                    .addComponent(jButton2)
                    .addComponent(BtGuardar))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                            .addComponent(jLabel3)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(txtStockMin, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addGap(75, 75, 75)
                            .addComponent(jLabel1)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGap(42, 42, 42)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtStockActual, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 55, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addComponent(jLabel6)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(cbProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(CbUnidad, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtPrecioU, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(52, 52, 52))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(109, 109, 109)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel4)
                    .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtPrecioU, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(CbUnidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(txtStockActual, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(cbProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)
                    .addComponent(txtStockMin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        CbUnidad.getAccessibleContext().setAccessibleName("");

        jLabel7.setBackground(new java.awt.Color(0, 0, 0));
        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel7.setText("Gestion de Insumos");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 691, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(45, 45, 45)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(15, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel7)
                .addGap(243, 243, 243))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(12, Short.MAX_VALUE)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 6, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BtGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtGuardarActionPerformed
        try {
            guardarInsumo();
            cargarTabla();
            limpiar();
        } catch (DaoException ex) {
            Logger.getLogger(tb_gestion_de_insumos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_BtGuardarActionPerformed

    private void BtGuardarFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_BtGuardarFocusGained

    }//GEN-LAST:event_BtGuardarFocusGained

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        try {
            eliminarPorFila();
            limpiar();

            cargarTabla();
        } catch (DaoException ex) {
            Logger.getLogger(tb_gestion_de_insumos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void productoTablaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_productoTablaMouseClicked
        rowselec = productoTabla.rowAtPoint(evt.getPoint());
    }//GEN-LAST:event_productoTablaMouseClicked

    private void CbUnidadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CbUnidadActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_CbUnidadActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        try {
            actualiazarPorFila();
            limpiar();
            cargarTabla();
        } catch (DaoException ex) {
            Logger.getLogger(tb_gestion_de_insumos.class.getName()).log(Level.SEVERE, null, ex);
        }        // TODO add your handling code here:        // TODO add your handling code here:
    }//GEN-LAST:event_jButton3ActionPerformed

    private void limpiar() {
        txtNombre.setText("");
        txtStockActual.setText("");
        txtStockMin.setText("");
        txtPrecioU.setText("");
        CbUnidad.setSelectedIndex(0);
        cbProveedor.setSelectedIndex(0);
    }

    private void cargarTabla() {
        model.setRowCount(0);
        try {
            List<Insumo> Productos = insumoService.listarTodos();
            Proveedor pro = null;
            for (Insumo in : Productos) {
                pro = proveedorservice.obtenerPorId(in.getIdProveedor());
                Object[] row = {
                    in.getId(),
                    in.getNombre(),
                    in.getStock(),
                    in.getStockMin(),
                    in.getUnidad().name(),
                    in.getPrecioUnitario(),
                    pro.getNombre(),
                    pro.getRuc(),
                    pro.getCorreo(),
                    pro.getTelefono()
                };
                model.addRow(row);
            }
        } catch (Exception ex) {
            System.out.println("error xd" + ex);
        }
    }

    private void mostrarTipoCombo() {
        CbUnidad.setModel(new DefaultComboBoxModel<>(Insumo.Unidad.values()));

    }

    private void mostrarProveedorCb() {

        try {
            List<Proveedor> Proveedores = proveedorservice.listarTodos();

            cbProveedor.removeAllItems();

            cbProveedor.addItem("Seleccione un Proveedor");

            for (Proveedor pro : Proveedores) {
                cbProveedor.addItem(pro.getNombre());
            }

        } catch (DaoException e) {
            JOptionPane.showMessageDialog(this, "Error al cargar mozos: " + e.getMessage(), "Error de BD", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error inesperado: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void actualiazarPorFila() throws DaoException {
        int locacion = rowselec;
        if (locacion < 0 || locacion >= productoTabla.getRowCount()) {
            JOptionPane.showMessageDialog(this, "Selecciona una fila v�lida para actualizar.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int id = (int) productoTabla.getValueAt(locacion, 0);

        // Obtener insumo actualizado
        Insumo insumoActualizado = new Insumo();
        insumoActualizado.setId(id);
        insumoActualizado.setNombre(txtNombre.getText());
        insumoActualizado.setStock(Double.parseDouble(txtStockActual.getText()));
        insumoActualizado.setStockMin(Double.parseDouble(txtStockMin.getText()));
        insumoActualizado.setPrecioUnitario(Double.parseDouble(txtPrecioU.getText()));
        insumoActualizado.setUnidad((Insumo.Unidad) CbUnidad.getSelectedItem());

        // Obtener proveedor
        Proveedor proveedor = extraerDatosProveedor();
        if (proveedor == null) {
            JOptionPane.showMessageDialog(this, "Debe seleccionar un proveedor v�lido.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        insumoActualizado.setIdProveedor(proveedor.getId());

        // Actualizar insumo en la base de datos
        insumoService.actualizarInsumo(insumoActualizado);
        JOptionPane.showMessageDialog(this, "�Insumo actualizado con �xito!");
    }

    private void eliminarPorFila() throws DaoException {
        int locacion = rowselec;
        if (locacion < 0 || locacion >= productoTabla.getRowCount()) {
            throw new DaoException("Fila seleccionada inv�lida.");
        }
        int id_local = (int) productoTabla.getValueAt(locacion, 0);
        Insumo insumoAEliminar = insumoService.obtenerPorId(id_local);
        if (insumoAEliminar != null) {
            insumoService.eliminarInsumo(insumoAEliminar);
        } else {
            throw new DaoException("No se encontr� el insumo con ID: " + id_local);
        }
    }

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
            java.util.logging.Logger.getLogger(tb_gestion_de_insumos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(tb_gestion_de_insumos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(tb_gestion_de_insumos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(tb_gestion_de_insumos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new tb_gestion_de_insumos().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BtGuardar;
    private javax.swing.JComboBox<Insumo.Unidad> CbUnidad;
    private javax.swing.JComboBox<String> cbProveedor;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTree jTree1;
    private javax.swing.JTable productoTabla;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtPrecioU;
    private javax.swing.JTextField txtStockActual;
    private javax.swing.JTextField txtStockMin;
    // End of variables declaration//GEN-END:variables
}
