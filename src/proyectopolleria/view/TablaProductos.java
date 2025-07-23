/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package proyectopolleria.view;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import proyectopolleria.dao.Impl.InsumoDaoImpl;
import proyectopolleria.dao.interfaces.InsumoDao;
import proyectopolleria.service.Impl.InsumoServiceImpl;
import proyectopolleria.service.interfaz.InsumoService;

import java.sql.Connection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import proyectopolleria.dao.DaoException;
import proyectopolleria.dao.Impl.ProductoDaoImpl;
import proyectopolleria.dao.Impl.ProveedorDaoImpl;
import proyectopolleria.dao.Impl.RecetaDaoImpl;
import proyectopolleria.dao.Impl.RecetaInsumoDaoImpl;
import proyectopolleria.model.Insumo;
import proyectopolleria.model.Producto;
import proyectopolleria.model.Proveedor;
import proyectopolleria.model.Receta;
import proyectopolleria.model.RecetaInsumo;
import proyectopolleria.service.Impl.ProductoServiceImpl;
import proyectopolleria.service.Impl.ProveedorServiceImp;
import proyectopolleria.service.Impl.RecetaInsumoServiceImpl;
import proyectopolleria.service.Impl.RecetaServiceImpl;
import proyectopolleria.service.interfaz.ProductoService;
import proyectopolleria.service.interfaz.ProveedorService;
import proyectopolleria.service.interfaz.RecetaInsumoService;
import proyectopolleria.service.interfaz.RecetaService;
import proyectopolleria.util.Conexion;

/**
 *
 * @author andres
 */
public class TablaProductos extends javax.swing.JFrame {

    private InsumoService insumoService;
    private Connection conn;
    private ProveedorService proveedorService;
    private RecetaInsumoService recetaInsumoService;
    private RecetaService recetaService;
    private DefaultTableModel modelo;
    private ProductoService productoService;
    private Producto pzero;

    /**
     * Creates new form TablaProductos
     */
    public TablaProductos() {
        initComponents();
        conn = Conexion.getInstancia().getConexion();
        insumoService = new InsumoServiceImpl(new InsumoDaoImpl(conn));
        proveedorService = new ProveedorServiceImp(new ProveedorDaoImpl(conn));
        recetaInsumoService = new RecetaInsumoServiceImpl(new RecetaInsumoDaoImpl(conn));
        productoService = new ProductoServiceImpl(new ProductoDaoImpl(conn));
        recetaService = new RecetaServiceImpl(new RecetaDaoImpl(conn));

        modelo = (DefaultTableModel) tbMostrar.getModel();
        CArgarInsumosAcombo();
        cargarcbTipoProducto();

        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

        CbInsumos.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    actualizarInfoInsumo();
                }
            }
        });
    }

    private void CArgarInsumosAcombo() {
        try {
            List<Insumo> insumos = insumoService.listarTodos();
            CbInsumos.removeAllItems();
            CbInsumos.addItem("Elije un insumo...");
            for (Insumo ins : insumos) {
                CbInsumos.addItem(ins.getNombre());
            }
        } catch (DaoException ex) {
            Logger.getLogger(TablaProductos.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void cargarcbTipoProducto() {
        cbTipoProducto.addItem("Elije un tipo de producto...");
        cbTipoProducto.addItem("Comida");
        cbTipoProducto.addItem("Bebida");
        cbTipoProducto.addItem("Acompañamiento");
        cbTipoProducto.addItem("Postre");
        cbTipoProducto.addItem("Salsa");
        cbTipoProducto.addItem("Adicional");
        cbTipoProducto.addItem("Combo");
    }

    private void CargarDatosDeInsumoPorCombo() {
        try {

        } catch (Exception ex) {
            Logger.getLogger(TablaProductos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private Insumo buscarCoincidenciaDeInsumo() {
        try {
            List<Insumo> insumos;
            insumos = insumoService.listarTodos();

            for (Insumo ins : insumos) {
                if (ins.getNombre().equalsIgnoreCase(String.valueOf(CbInsumos.getSelectedItem()))) {
                    return ins;
                }
            }
        } catch (DaoException ex) {
            Logger.getLogger(TablaProductos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    private void actualizarInfoInsumo() {
        try {
            Insumo insumoSeleccionado = buscarCoincidenciaDeInsumo();
            Proveedor proseleccion = proveedorService.obtenerPorId(insumoSeleccionado.getIdProveedor());
            if (insumoSeleccionado != null) {
                txtProveedor.setText(proseleccion.getNombre());
                txtRucproveedor.setText(proseleccion.getRuc());
                txtStockActual.setText(String.valueOf(insumoSeleccionado.getStock()));
                txtTelefonoProveedor.setText(proseleccion.getTelefono());
            } else {
                JOptionPane.showMessageDialog(null, "no se permite eso xd");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "error xd: " + ex);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        cbcategoria = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        cbDisponibilidad = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        txtNombreProductos = new javax.swing.JTextField();
        TxtPrecioProducto = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        BtnAgregarInsumos = new javax.swing.JToggleButton();
        CbInsumos = new javax.swing.JComboBox<>();
        BtnInsumosMostar = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        txtProveedor = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtRucproveedor = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtStockActual = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        txtTelefonoProveedor = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        cbTipoProducto = new javax.swing.JComboBox<>();
        CantidadAUSar = new javax.swing.JSpinner();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbMostrar = new javax.swing.JTable();
        jLabel9 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("Nombre:");

        jLabel2.setText("Precio:");

        jLabel3.setText("Tipo:");

        cbDisponibilidad.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Disponible", "No Disponible" }));

        jLabel4.setText("Disponible:");

        jButton1.setText("Eliminar");

        jButton3.setText("Modificar");

        jButton2.setText("Guardar");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(45, 45, 45)
                .addComponent(jButton2)
                .addGap(66, 66, 66)
                .addComponent(jButton3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 74, Short.MAX_VALUE)
                .addComponent(jButton1)
                .addGap(56, 56, 56))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(17, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton3)
                    .addComponent(jButton2))
                .addGap(14, 14, 14))
        );

        BtnAgregarInsumos.setText("Agregar Insumo");
        BtnAgregarInsumos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnAgregarInsumosActionPerformed(evt);
            }
        });

        CbInsumos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CbInsumosActionPerformed(evt);
            }
        });

        BtnInsumosMostar.setText("Ver Lista de Insumos");
        BtnInsumosMostar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnInsumosMostarActionPerformed(evt);
            }
        });

        jLabel5.setText("Proveedor:");

        txtProveedor.setEditable(false);

        jLabel6.setText("Ruc Proveedor:");

        txtRucproveedor.setEditable(false);

        jLabel7.setText("Stcok Actual:");

        txtStockActual.setEditable(false);

        jLabel8.setText("Telefono:");

        txtTelefonoProveedor.setEditable(false);

        jLabel10.setText("Cantidad a Usar:");

        javax.swing.GroupLayout cbcategoriaLayout = new javax.swing.GroupLayout(cbcategoria);
        cbcategoria.setLayout(cbcategoriaLayout);
        cbcategoriaLayout.setHorizontalGroup(
            cbcategoriaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(cbcategoriaLayout.createSequentialGroup()
                .addGroup(cbcategoriaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(cbcategoriaLayout.createSequentialGroup()
                        .addGap(33, 33, 33)
                        .addGroup(cbcategoriaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(cbcategoriaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(TxtPrecioProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtNombreProductos, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 44, Short.MAX_VALUE)
                        .addGroup(cbcategoriaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING)))
                    .addGroup(cbcategoriaLayout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(BtnInsumosMostar)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(cbcategoriaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(cbDisponibilidad, 0, 150, Short.MAX_VALUE)
                    .addComponent(cbTipoProducto, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(43, 43, 43))
            .addGroup(cbcategoriaLayout.createSequentialGroup()
                .addGap(65, 65, 65)
                .addComponent(CbInsumos, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(BtnAgregarInsumos)
                .addGap(59, 59, 59))
            .addGroup(cbcategoriaLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(cbcategoriaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(cbcategoriaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(cbcategoriaLayout.createSequentialGroup()
                            .addGroup(cbcategoriaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(cbcategoriaLayout.createSequentialGroup()
                                    .addGap(23, 23, 23)
                                    .addComponent(jLabel5))
                                .addComponent(jLabel6))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addGroup(cbcategoriaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addGroup(cbcategoriaLayout.createSequentialGroup()
                                    .addComponent(txtProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(18, 18, 18)
                                    .addComponent(jLabel7)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(txtStockActual, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(cbcategoriaLayout.createSequentialGroup()
                                    .addComponent(txtRucproveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel8)
                                    .addGap(18, 18, 18)
                                    .addComponent(txtTelefonoProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGroup(cbcategoriaLayout.createSequentialGroup()
                            .addComponent(jLabel10)
                            .addGap(6, 6, 6)
                            .addComponent(CantidadAUSar, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        cbcategoriaLayout.setVerticalGroup(
            cbcategoriaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(cbcategoriaLayout.createSequentialGroup()
                .addGroup(cbcategoriaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(cbcategoriaLayout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addGroup(cbcategoriaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(txtNombreProductos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(23, 23, 23))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, cbcategoriaLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(cbcategoriaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(cbTipoProducto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)))
                .addGroup(cbcategoriaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(cbDisponibilidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(TxtPrecioProducto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(BtnInsumosMostar)
                .addGap(17, 17, 17)
                .addGroup(cbcategoriaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(CbInsumos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(BtnAgregarInsumos))
                .addGap(18, 18, 18)
                .addGroup(cbcategoriaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7)
                    .addComponent(txtStockActual, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(cbcategoriaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtRucproveedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8)
                    .addComponent(txtTelefonoProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(cbcategoriaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(CantidadAUSar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(20, Short.MAX_VALUE))
        );

        tbMostrar.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Id", "Nombre", "Cantidad"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tbMostrar);

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel9.setText("Productos");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(232, 232, 232)
                        .addComponent(jLabel9)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(cbcategoria, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(51, 51, 51))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(cbcategoria, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 215, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(49, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BtnInsumosMostarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnInsumosMostarActionPerformed
        TablaInsumos t1 = new TablaInsumos();
        t1.setVisible(true);
    }//GEN-LAST:event_BtnInsumosMostarActionPerformed

    private void CbInsumosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CbInsumosActionPerformed
        actualizarInfoInsumo();
    }//GEN-LAST:event_CbInsumosActionPerformed

    private void BtnAgregarInsumosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAgregarInsumosActionPerformed
        Insumo ins = buscarCoincidenciaDeInsumo();

        if (ins == null) {
            JOptionPane.showMessageDialog(this, "Seleccione un insumo válido.");
            return;
        }

        double cantidad = Double.parseDouble(CantidadAUSar.getValue().toString());

        // Agregar fila a la tabla
        if (cantidad <= 0) {
            JOptionPane.showMessageDialog(this, "La cantidad debe ser mayor a cero.");
            return;
        }

        // Agregar a tabla
        DefaultTableModel modelo = (DefaultTableModel) tbMostrar.getModel();
        modelo.addRow(new Object[]{
            ins.getId(),
            ins.getNombre(),
            cantidad
        });
    }//GEN-LAST:event_BtnAgregarInsumosActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // 1. Crear el producto (tú puedes llenar los datos como quieras)
        try {
            Producto producto = new Producto();
            producto.setDescripcion(txtNombreProductos.getText());
            producto.setPrecio(Double.parseDouble(TxtPrecioProducto.getText()));
            producto.setCategoria(cbcategoria.getName());
            producto.setActivo(cbDisponibilidad.getSelectedItem().equals("Disponible"));

            // 2. Registrar el producto en la base de datos
            productoService.registrarProducto(producto);

            producto = buscarPorDescripcion(producto.getDescripcion());

            // 3. Crear la receta asociada
            Receta receta = new Receta(null, producto.getId());
            recetaService.registrarReceta(receta); // Esto asigna un ID

            // 4. Recorrer la tabla tbMostrar y agregar insumos a la receta
            DefaultTableModel modelo = (DefaultTableModel) tbMostrar.getModel();
            for (int i = 0; i < modelo.getRowCount(); i++) {
                int idInsumo = (int) modelo.getValueAt(i, 0);
                double cantidad = Double.parseDouble(modelo.getValueAt(i, 2).toString());

                RecetaInsumo ri = new RecetaInsumo(receta.getId(), idInsumo, cantidad);
                recetaInsumoService.agregarInsumoAReceta(ri);
            }
            JOptionPane.showMessageDialog(this, "Producto y receta registrados correctamente.");
            limpiarFormulario();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al registrar el producto y la receta: " + ex.getMessage());
            ex.printStackTrace();
            limpiarFormulario();
        }

    }//GEN-LAST:event_jButton2ActionPerformed

    private Producto buscarPorDescripcion(String des) {
        try {
            List<Producto> productos = productoService.listarTodos();
            for (Producto p : productos) {
                if (p.getDescripcion().equalsIgnoreCase(des)) {
                    return p;
                }
            }
        } catch (Exception e) {
            e.printStackTrace(); // para ayudarte a depurar si falla algo
        }
        return null; // Si no encuentra ningún producto
    }

    private void agregaraTabla() {
        Insumo ins = buscarCoincidenciaDeInsumo();
        Object[] row = {
            ins.getId(),
            ins.getNombre(),
            CantidadAUSar.getValue()
        };
    }

    private void limpiarFormulario() {
        txtNombreProductos.setText("");
        TxtPrecioProducto.setText("");
        cbTipoProducto.setSelectedIndex(0);
        CantidadAUSar.setValue(0);

        DefaultTableModel modelo = (DefaultTableModel) tbMostrar.getModel();
        modelo.setRowCount(0); // Limpia la tabla de insumos
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
            java.util.logging.Logger.getLogger(TablaProductos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TablaProductos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TablaProductos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TablaProductos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TablaProductos().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JToggleButton BtnAgregarInsumos;
    private javax.swing.JButton BtnInsumosMostar;
    private javax.swing.JSpinner CantidadAUSar;
    private javax.swing.JComboBox<String> CbInsumos;
    private javax.swing.JTextField TxtPrecioProducto;
    private javax.swing.JComboBox<String> cbDisponibilidad;
    private javax.swing.JComboBox<String> cbTipoProducto;
    private javax.swing.JPanel cbcategoria;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tbMostrar;
    private javax.swing.JTextField txtNombreProductos;
    private javax.swing.JTextField txtProveedor;
    private javax.swing.JTextField txtRucproveedor;
    private javax.swing.JTextField txtStockActual;
    private javax.swing.JTextField txtTelefonoProveedor;
    // End of variables declaration//GEN-END:variables
}
