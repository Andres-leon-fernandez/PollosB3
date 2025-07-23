package proyectopolleria.view;

import java.awt.Point;
import java.awt.event.ItemEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import java.io.FileOutputStream;
import java.time.format.DateTimeFormatter;
import java.awt.Desktop;
import java.io.File;
import java.util.Objects;

import proyectopolleria.dao.Impl.ClienteDaoImpl;
import proyectopolleria.dao.Impl.PedidoDaoImpl;
import proyectopolleria.model.Pedido;
import proyectopolleria.model.Producto;
import proyectopolleria.service.Impl.ClienteServiceImpl;
import proyectopolleria.service.Impl.PedidoServiceImpl;
import proyectopolleria.service.interfaz.ClienteService;
import proyectopolleria.service.interfaz.PedidoService;
import proyectopolleria.service.interfaz.ProductoService;
import proyectopolleria.service.interfaz.TrabajadorService;
import proyectopolleria.util.Conexion;
import java.sql.Connection;
import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;

import proyectopolleria.dao.DaoException;
import proyectopolleria.dao.Impl.OrdenDaoImpl;
import proyectopolleria.dao.Impl.ProductoDaoImpl;
import proyectopolleria.dao.Impl.TrabajadorDaoImpl;
import proyectopolleria.model.Cliente;
import proyectopolleria.model.Orden;
import proyectopolleria.model.Trabajador;
import proyectopolleria.service.Impl.OrdenServiceImpl;
import proyectopolleria.service.Impl.ProductoServiceImpl;
import proyectopolleria.service.Impl.TrabajadorServiceImpl;
import proyectopolleria.service.interfaz.OrdenService;
import proyectopolleria.util.ExportExcel;

public class TablaRegistroPedidos extends javax.swing.JFrame {

    private ClienteService clienteService;
    private TrabajadorService trabajadorService;
    private ProductoService productoService;
    private PedidoService pedidoService;
    private OrdenService ordenService;
    private DefaultTableModel model;
    private DefaultTableModel modelDetalle;
    private Pedido pedido;
    private Cliente cliente;
    private Orden orden;

    public TablaRegistroPedidos() {
        initComponents();
        setLocationRelativeTo(null);

        Connection conn = Conexion.getInstancia().getConexion();
        clienteService = new ClienteServiceImpl(new ClienteDaoImpl(conn));
        trabajadorService = new TrabajadorServiceImpl(new TrabajadorDaoImpl(conn));
        pedidoService = new PedidoServiceImpl(new PedidoDaoImpl(conn));
        productoService = new ProductoServiceImpl(new ProductoDaoImpl(conn));
        ordenService = new OrdenServiceImpl(new OrdenDaoImpl(conn));

        model = (DefaultTableModel) jTable1.getModel();
        modelDetalle = (DefaultTableModel) jTable2.getModel();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        cargarPedidosDesdeBD();
        llenarComboProductos();
        llenarComboMozo();
        llenarComboDelivery();
        habilitarCamposTab2(false);
        jTable1.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent mouseEvent) {
                JTable table = (JTable) mouseEvent.getSource();
                Point point = mouseEvent.getPoint();
                int row = table.rowAtPoint(point);
                if (mouseEvent.getClickCount() == 2) {
                    try {
                        llenarDatos();
                        jTabbedPane1.setSelectedIndex(1);
                        habilitarCamposTab2(false);
                    } catch (DaoException ex) {
                        System.getLogger(TablaRegistroPedidos.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
                    }
                }
            }
        });
        jTable2.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent mouseEvent) {
                JTable table = (JTable) mouseEvent.getSource();
                Point point = mouseEvent.getPoint();
                int row = table.rowAtPoint(point);
                if (mouseEvent.getClickCount() == 1) {
                    llenarDatosOrden();
                }
            }
        });
        jcbTipoPedido.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                actualizarCamposTipoPedido();
            }
        });
        jTabbedPane1.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                int selectedIndex = jTabbedPane1.getSelectedIndex();
                if (selectedIndex == 0) {
                    habilitarCamposTab2(false);
                } else {
                    cargarPedidosDesdeBD();
                }
            }
        });

    }

    // Metodo para cargar los datos desde el archivo CSV
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
                    "-", // Cantidad o descripcion de productos: implementar luego
                    p.getTotal()
                };
                model.addRow(row);
            }
        } catch (DaoException e) {
            JOptionPane.showMessageDialog(this, "Error cargando pedidos: " + e.getMessage());
        }
    }

    private void cargarOrdenDesdeBD(int id) {
        try {
            List<Orden> listado = ordenService.listarPorPedido(id);
            modelDetalle.setRowCount(0); // Limpiar tabla

            for (Orden orden : listado) {
                Object[] row = {
                    orden.getId(),
                    productoService.nombreProductoById(orden.getIdProducto()),
                    orden.getCantidad(),
                    orden.getSubtotal()
                };
                modelDetalle.addRow(row);
            }
        } catch (DaoException e) {
            JOptionPane.showMessageDialog(this, "Error cargando las ordenes del pedido Nro " + id + ": " + e.getMessage());
        }
    }

    private void llenarDatos() throws DaoException {
        pedido = new Pedido();
        pedido = pedidoService.obtenerPorId(Integer.parseInt(jTable1.getValueAt(jTable1.getSelectedRow(), 0).toString()));
        // Cliente
        if (pedido.getIdCliente() != null) {
            cliente = clienteService.obtenerPorId(pedido.getIdCliente());
            if (cliente != null) {
                txtDocumento.setText(cliente.getDni());
                txtxCliente.setText(cliente.getNombre());
                txtTelefono.setText(cliente.getTelefono());
                txtDireccion.setText(cliente.getDireccion());
            }
        }

        // Trabajador (Mozo o Delivery segun tipo de pedido)
        if (pedido.getTipo() == Pedido.tipoPedido.SALON) {
            // Si es pedido local, buscar mozo
            if (pedido.getIdMOZO() != null) {
                Trabajador mozo = trabajadorService.obtenerPorId(pedido.getIdMOZO());
                if (mozo != null) {
                    jComboBox1.setSelectedItem(mozo); // Seleccionar mozo en el combo
                }
            }
            jcbTipoPedido.setSelectedItem("Local");

        } else if (pedido.getTipo() == Pedido.tipoPedido.DELIVERY) {
            // Si es delivery, buscar repartidor
            if (pedido.getIdDELIVERY() != null) {
                Trabajador delivery = trabajadorService.obtenerPorId(pedido.getIdDELIVERY());
                if (delivery != null) {
                    jComboBox2.setSelectedItem(delivery); // Seleccionar delivery en el combo
                }
            }
            jcbTipoPedido.setSelectedItem("Delivery");
        }
        txtTotal.setText(String.format("%.2f", pedido.getTotal()));
        actualizarCamposTipoPedido();
        //Orden
        cargarOrdenDesdeBD(pedido.getId());
    }

    private void llenarDatosOrden() {
        orden = new Orden();
        orden.setId(Integer.valueOf(jTable2.getValueAt(jTable2.getSelectedRow(), 0).toString()));
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
        jButton9 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        txtDocumento = new javax.swing.JTextField();
        txtxCliente = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtDireccion = new javax.swing.JTextArea();
        jLabel7 = new javax.swing.JLabel();
        txtTelefono = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jcbTipoPedido = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jComboBox2 = new javax.swing.JComboBox<>();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jPanel5 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jComboBox3 = new javax.swing.JComboBox<>();
        jSpinner1 = new javax.swing.JSpinner();
        jLabel10 = new javax.swing.JLabel();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jLabel13 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        txtTotal = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setText("Listado de pedidos");

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
        jButton1.setText("Eliminar pedido");
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

        jButton9.setBackground(new java.awt.Color(32, 134, 238));
        jButton9.setForeground(new java.awt.Color(255, 255, 255));
        jButton9.setText("Nuevo");
        jButton9.setBorderPainted(false);
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
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
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 780, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 2, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 303, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton3))
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 267, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton2, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jButton9)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jButton2))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton3)
                    .addComponent(jButton1)
                    .addComponent(jButton9))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 386, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Listado", jPanel1);

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Datos del Cliente"));

        jLabel3.setText("N° Documento:");

        txtDocumento.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtDocumentoKeyPressed(evt);
            }
        });

        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel4.setText("Cliente:");

        txtDireccion.setColumns(20);
        txtDireccion.setRows(5);
        jScrollPane2.setViewportView(txtDireccion);

        jLabel7.setText("Dirección:");

        jLabel12.setText("Teléfono:");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(txtDocumento, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel12)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 317, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtxCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 317, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtDocumento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtxCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(7, Short.MAX_VALUE))
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("Datos del Pedido"));

        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel6.setText("Encargado:");

        jcbTipoPedido.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Local", "Delivery" }));
        jcbTipoPedido.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcbTipoPedidoActionPerformed(evt);
            }
        });

        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel5.setText("Tipo Pedido:");

        jLabel8.setText("Repartidor:");

        jButton4.setText("Ver Pedido");

        jButton5.setBackground(new java.awt.Color(40, 120, 220));
        jButton5.setForeground(new java.awt.Color(255, 255, 255));
        jButton5.setText("Imprimir Boleta");
        jButton5.setBorderPainted(false);
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel8)
                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jcbTipoPedido, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(jComboBox1, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jComboBox2, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addComponent(jButton5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 127, Short.MAX_VALUE)
                        .addComponent(jButton4)))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jcbTipoPedido, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 11, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton4)
                    .addComponent(jButton5))
                .addContainerGap())
        );

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Secuencia", "Producto", "Cantidad", "Sub Total"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, true, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane3.setViewportView(jTable2);
        if (jTable2.getColumnModel().getColumnCount() > 0) {
            jTable2.getColumnModel().getColumn(0).setMaxWidth(80);
            jTable2.getColumnModel().getColumn(2).setMaxWidth(60);
            jTable2.getColumnModel().getColumn(3).setMinWidth(90);
            jTable2.getColumnModel().getColumn(3).setMaxWidth(110);
        }

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder("Registrar Orden"));

        jLabel9.setText("Producto:");

        jComboBox3.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBox3ItemStateChanged(evt);
            }
        });
        jComboBox3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox3ActionPerformed(evt);
            }
        });

        jSpinner1.setValue(1);

        jLabel10.setText("Cantidad:");

        jButton6.setBackground(new java.awt.Color(237, 33, 58));
        jButton6.setForeground(new java.awt.Color(255, 255, 255));
        jButton6.setText("Eliminar");
        jButton6.setBorderPainted(false);
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jButton7.setBackground(new java.awt.Color(32, 134, 238));
        jButton7.setForeground(new java.awt.Color(255, 255, 255));
        jButton7.setText("Registrar");
        jButton7.setBorderPainted(false);
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        jLabel13.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(204, 0, 51));
        jLabel13.setText("Producto Agotado");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSpinner1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton7)
                .addGap(18, 18, 18)
                .addComponent(jButton6)
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jSpinner1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10)
                    .addComponent(jButton6)
                    .addComponent(jButton7)
                    .addComponent(jLabel13))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel11.setBackground(new java.awt.Color(255, 51, 102));
        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel11.setText("Total:");

        txtTotal.setEditable(false);
        txtTotal.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtTotal.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane3)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(txtTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(11, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Pedido", jPanel2);

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
            ordenService.eliminarOrdenesPorPedido(pedido.getId());
            pedidoService.eliminarPedido(pedido);
            model.setRowCount(0);
            cargarPedidosDesdeBD();
        } catch (DaoException ex) {
            Logger.getLogger(TablaCliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void obtenerIdEliminar() {
        pedido = new Pedido();
        pedido.setId(Integer.valueOf(jTable1.getValueAt(jTable1.getSelectedRow(), 0).toString()));
    }

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        String textoBuscado = jTextField1.getText().trim();
        if (textoBuscado.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, ingresa un valor para buscar.", "Campo vacio", JOptionPane.WARNING_MESSAGE);
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
        JOptionPane.showMessageDialog(this, "No se encontraron coincidencias.", "Busqueda", JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        try {
            if (orden != null || orden.getId() != null) {
                ordenService.eliminarOrden(orden);
                model.setRowCount(0);
                cargarOrdenDesdeBD(orden.getId());
            } else {
                JOptionPane.showMessageDialog(this, "No se ha seleccionado una orden a eliminar.", "Orden no seleccionada", JOptionPane.WARNING_MESSAGE);
            }

        } catch (DaoException ex) {
            Logger.getLogger(TablaCliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        try {
            Producto productoSeleccionado = (Producto) jComboBox3.getSelectedItem();
            int cantidad = (Integer) jSpinner1.getValue();
            if (productoSeleccionado == null) {
                JOptionPane.showMessageDialog(this,
                        "Por favor seleccione un producto",
                        "Error",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (!validarProducto()) {
                return;
            }
            orden = new Orden();
            orden.setIdProducto(productoSeleccionado.getId());
            orden.setCantidad(cantidad);
            orden.setSubtotal(productoSeleccionado.getPrecio() * cantidad);

            if (pedido == null || pedido.getId() == null) {
                pedido = new Pedido();
                obtenerCliente();
                if (cliente.getId() == null) {
                    if (Pedido.tipoPedido.DELIVERY == pedido.getTipo()) {
                        clienteService.registrarClienteDelivery(cliente);
                    } else {
                        clienteService.registrarClienteLocal(cliente);
                    }
                }
                pedido.setIdCliente(cliente.getId());
                pedido.setFechaHora(LocalDateTime.now());
                pedido.setTotal(0.0);
                pedido.setTipo(jcbTipoPedido.getSelectedItem().toString().equals("Local")
                        ? Pedido.tipoPedido.SALON : Pedido.tipoPedido.DELIVERY);
                Trabajador trabajadorSeleccionado = (Trabajador) jComboBox1.getSelectedItem();
                Trabajador deliverySeleccionado = (Trabajador) jComboBox2.getSelectedItem();
                if (Pedido.tipoPedido.DELIVERY == pedido.getTipo()) {
                    pedido.setIdDELIVERY(deliverySeleccionado.getId());
                } else {
                    pedido.setIdMOZO(trabajadorSeleccionado.getId());
                }
                pedidoService.registrarPedido(pedido);
            }
            orden.setIdPedido(pedido.getId());
            ordenService.registrarOrden(orden);
            cargarOrdenDesdeBD(pedido.getId());
            jSpinner1.setValue(1);
            jComboBox3.setSelectedIndex(0);
            obtenerMontoTotal();
        } catch (DaoException ex) {
            JOptionPane.showMessageDialog(this,
                    "Error al registrar la orden: " + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            Logger.getLogger(TablaRegistroPedidos.class.getName()).log(Level.SEVERE, null, ex);
        }

    }//GEN-LAST:event_jButton7ActionPerformed

    private void obtenerMontoTotal() {
        double total = 0.0;
        for (int i = 0; i < jTable2.getRowCount(); i++) {
            Object valor = jTable2.getValueAt(i, 3);
            if (valor != null && valor instanceof Number) {
                total += ((Number) valor).doubleValue();
            } else {
                try {
                    total += Double.parseDouble(valor.toString());
                } catch (Exception e) {
                    
                }
            }
        }
        txtTotal.setText(String.format("%.2f", total));

    }

    private void obtenerCliente() {
        cliente.setDni(txtDocumento.getText());
        cliente.setNombre(txtxCliente.getText());
        cliente.setTelefono(txtTelefono.getText());
        cliente.setDireccion(txtDireccion.getText());
    }

    private void jcbTipoPedidoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcbTipoPedidoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jcbTipoPedidoActionPerformed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        jTabbedPane1.setSelectedIndex(1);
        habilitarCamposTab2(true);
        limpiarData();
    }//GEN-LAST:event_jButton9ActionPerformed

    private void jComboBox3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox3ActionPerformed

    private void jComboBox3ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox3ItemStateChanged
        if (evt.getStateChange() == ItemEvent.SELECTED) {
            validarProducto();
        }
    }//GEN-LAST:event_jComboBox3ItemStateChanged

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        try {
            // Asumiendo que tienes el pedido actual y el cliente en variables
            Pedido pedidoActual = pedido;
            //List<Orden> ordenes = /* obtener Ordenes del pedido */ ;

            // Crear el documento
            String rutaPDF = "boleta_" + pedidoActual.getId() + ".pdf";
            Rectangle pageSize = new Rectangle(300, 842);
            Document documento = new Document(pageSize);
            documento.setMargins(20, 20, 20, 20);
            PdfWriter.getInstance(documento, new FileOutputStream(rutaPDF));
            documento.open();

            // Fuentes
            Font fuenteTitulo = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD);
            Font fuenteSubtitulo = new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD);
            Font fuenteNormal = new Font(Font.FontFamily.HELVETICA, 12, Font.NORMAL);
            Font fuenteTotal = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);

            // Encabezado
            Paragraph titulo = new Paragraph("POLLOS B3", fuenteTitulo);
            titulo.setAlignment(Element.ALIGN_CENTER);
            documento.add(titulo);

            Paragraph subtitulo = new Paragraph("BOLETA ELECTRONICA", fuenteSubtitulo);
            subtitulo.setAlignment(Element.ALIGN_CENTER);
            subtitulo.setSpacingBefore(10);
            documento.add(subtitulo);

            // Informacion de la empresa
            documento.add(new Paragraph("RUC: 20XXXXXXXXX", fuenteNormal));
            documento.add(new Paragraph("Direccion: Av. Example 123", fuenteNormal));
            documento.add(new Paragraph("Telefono: (01) 555-5555", fuenteNormal));

            // Informacion del cliente
            documento.add(new Paragraph("-----------------------------------------------------------------", fuenteNormal));
            documento.add(new Paragraph("DATOS DEL CLIENTE:", fuenteSubtitulo));
            documento.add(new Paragraph("Cliente: " + Objects.toString(cliente.getNombre(), ""), fuenteNormal));
            documento.add(new Paragraph("DNI: " + Objects.toString(cliente.getDni(), ""), fuenteNormal));
            if (pedidoActual.getTipo() == Pedido.tipoPedido.DELIVERY) {
                documento.add(new Paragraph("Direccion: " + Objects.toString(cliente.getDireccion(), ""), fuenteNormal));
                documento.add(new Paragraph("Referencia: " + Objects.toString(cliente.getReferencia(), ""), fuenteNormal));
                documento.add(new Paragraph("Telefono: " + Objects.toString(cliente.getTelefono(), ""), fuenteNormal));
            }

            // Informacion de la boleta
            documento.add(new Paragraph("-----------------------------------------------------------------", fuenteNormal));
            documento.add(new Paragraph("Boleta N°: B001-" + String.format("%06d", pedidoActual.getId()), fuenteNormal));
            documento.add(new Paragraph("Fecha: " + pedidoActual.getFechaHora().format(
                    DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")), fuenteNormal));
            documento.add(new Paragraph("Tipo: " + pedidoActual.getTipo(), fuenteNormal));
            /*if (pedidoActual.getIdMOZO() != null) {
                documento.add(new Paragraph("Mozo: " + pedidoActual.getIdMOZO(), fuenteNormal));
            }
            if (pedidoActual.getIdDELIVERY() != null) {
                documento.add(new Paragraph("Delivery: " + pedidoActual.getIdDELIVERY(), fuenteNormal));
            }*/
            documento.add(new Paragraph("-----------------------------------------------------------------", fuenteNormal));

            // Crear tabla de productos
            PdfPTable tabla = new PdfPTable(3);
            tabla.setWidthPercentage(100);
            tabla.setSpacingBefore(20);
            tabla.setSpacingAfter(20);

            // Estilo para los encabezados
            PdfPCell headerCell = new PdfPCell();
            headerCell.setBackgroundColor(new BaseColor(240, 240, 240));
            headerCell.setPadding(5);

            // Encabezados de la tabla
            headerCell.setPhrase(new Phrase("Producto", fuenteSubtitulo));
            tabla.addCell(headerCell);
            headerCell.setPhrase(new Phrase("Cantidad", fuenteSubtitulo));
            tabla.addCell(headerCell);
            headerCell.setPhrase(new Phrase("Subtotal", fuenteSubtitulo));
            tabla.addCell(headerCell);

            // Estilo para las celdas de datos
            PdfPCell dataCell = new PdfPCell();
            dataCell.setPadding(5);
            dataCell.setBorderWidthTop(1);
            dataCell.setBorderWidthBottom(1);

            // Agregar datos de la tabla
            double totalGeneral = 0.0;
            for (int i = 0; i < jTable2.getRowCount(); i++) {
                // Producto (columna 1)
                dataCell.setPhrase(new Phrase(Objects.toString(jTable2.getValueAt(i, 1), ""), fuenteNormal));
                dataCell.setHorizontalAlignment(Element.ALIGN_LEFT);
                tabla.addCell(dataCell);

                // Cantidad (columna 2)
                dataCell.setPhrase(new Phrase(Objects.toString(jTable2.getValueAt(i, 2), "0"), fuenteNormal));
                dataCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                tabla.addCell(dataCell);

                // Subtotal (columna 3)
                String subtotal = Objects.toString(jTable2.getValueAt(i, 3), "0.0");
                dataCell.setPhrase(new Phrase("S/ " + subtotal, fuenteNormal));
                dataCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                tabla.addCell(dataCell);

                // Sumar al total
                try {
                    totalGeneral += Double.parseDouble(subtotal);
                } catch (NumberFormatException e) {
                    // Manejar error de conversión si es necesario
                }
            }
            documento.add(tabla);

            // Totales
            double subtotalMonto = totalGeneral / 1.18; // Asumiendo IGV de 18%
            double igvMonto = totalGeneral - subtotalMonto;

            Paragraph subtotalP = new Paragraph("Subtotal: S/ " + String.format("%.2f", subtotalMonto), fuenteTotal);
            subtotalP.setAlignment(Element.ALIGN_RIGHT);
            documento.add(subtotalP);

            Paragraph igv = new Paragraph("IGV (18%): S/ " + String.format("%.2f", igvMonto), fuenteTotal);
            igv.setAlignment(Element.ALIGN_RIGHT);
            documento.add(igv);

            Paragraph total = new Paragraph("Total: S/ " + String.format("%.2f", totalGeneral), fuenteTotal);
            total.setAlignment(Element.ALIGN_RIGHT);
            documento.add(total);

            // Pie de pagina
            Paragraph footer = new Paragraph("¡Gracias por su preferencia!", fuenteNormal);
            footer.setAlignment(Element.ALIGN_CENTER);
            footer.setSpacingBefore(30);
            documento.add(footer);

            documento.close();

            // Mostrar mensaje de exito
            JOptionPane.showMessageDialog(null,
                    "Boleta generada exitosamente en: " + rutaPDF,
                    "Exito",
                    JOptionPane.INFORMATION_MESSAGE);

            // Abrir el archivo PDF
            Desktop.getDesktop().open(new File(rutaPDF));

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,
                    "Error al generar la boleta: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }

    }//GEN-LAST:event_jButton5ActionPerformed

    private boolean validarProducto() {
        boolean hayStock = false;
        try {
            Producto selectedItem = (Producto) jComboBox3.getSelectedItem();
            if (selectedItem != null) {
                hayStock = ordenService.validarInsumosDisponiblesPorOrden(selectedItem.getId(), (Integer) jSpinner1.getValue());

                jButton6.setEnabled(hayStock);
                jButton7.setEnabled(hayStock);
                jLabel13.setVisible(!hayStock);  // Mostrar label si NO hay stock

                if (!hayStock) {
                    JOptionPane.showMessageDialog(this,
                            "El producto seleccionado esta agotado",
                            "Producto Agotado",
                            JOptionPane.WARNING_MESSAGE);
                }
            }
        } catch (DaoException ex) {
            JOptionPane.showMessageDialog(this,
                    "Error al validar el stock: " + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }

        return hayStock;
    }

    private void limpiarData() {
        cliente = new Cliente();
        orden = new Orden();
        pedido = new Pedido();
        txtDocumento.setText("");
        txtxCliente.setText("");
        txtTelefono.setText("");
        txtDireccion.setText("");
        txtTotal.setText("");

        // Limpiar ComboBoxes - Establecer seleccion por defecto
        jComboBox1.setSelectedIndex(0); // ComboBox Mozo
        jComboBox2.setSelectedIndex(0); // ComboBox Delivery
        jcbTipoPedido.setSelectedIndex(0);
        jComboBox3.setSelectedIndex(0);

        // Restablecer el JSpinner a su valor inicial
        jSpinner1.setValue(jSpinner1.getModel().getPreviousValue());
        modelDetalle.setRowCount(0);
    }

    private void actualizarCamposTipoPedido() {
        boolean esLocal = jcbTipoPedido.getSelectedItem().toString().equals("Local");

        jComboBox1.setEnabled(esLocal);
        jComboBox2.setEnabled(!esLocal);
        if (esLocal) {
            jComboBox2.setSelectedIndex(-1);
        } else {
            jComboBox1.setSelectedIndex(-1);
        }
    }

    private void txtDocumentoKeyPressed(java.awt.event.KeyEvent evt) {
        if (evt.getKeyCode() == KeyEvent.VK_ENTER || evt.getKeyCode() == KeyEvent.VK_TAB) {
            String documento = txtDocumento.getText().trim();
            if (documento.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Por favor, ingrese un numero de documento",
                        "Campo vacio",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }
            try {
                cliente = clienteService.buscarClientePorDocumento(documento);
                if (cliente != null && cliente.getId() != null) {
                    txtDocumento.setText(cliente.getDni());
                    txtTelefono.setText(cliente.getTelefono());
                    txtxCliente.setText(cliente.getNombre());
                    txtDireccion.setText(cliente.getDireccion());
                }
            } catch (DaoException ex) {
                System.getLogger(TablaRegistroPedidos.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
            }
        }
    }

    private void llenarComboProductos() {
        try {
            List<Producto> productos = productoService.listarTodos();
            DefaultComboBoxModel<Producto> model = new DefaultComboBoxModel<>();

            for (Producto producto : productos) {
                model.addElement(producto);
            }

            jComboBox3.setModel(model);

        } catch (Exception ex) {
            Logger.getLogger(TablaPedidos.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(this,
                    "Error al cargar productos: " + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void llenarComboDelivery() {
        try {
            List<Trabajador> lista = trabajadorService.listarDeliveryDisponible();
            DefaultComboBoxModel<Trabajador> model = new DefaultComboBoxModel<>();

            for (Trabajador trabajador : lista) {
                model.addElement(trabajador);
            }

            jComboBox2.setModel(model);

        } catch (Exception ex) {
            Logger.getLogger(TablaPedidos.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(this,
                    "Error al cargar los trabajadores para delivery: " + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void llenarComboMozo() {
        try {
            List<Trabajador> lista = trabajadorService.listarMozoDisponible();
            DefaultComboBoxModel<Trabajador> model = new DefaultComboBoxModel<>();

            for (Trabajador trabajador : lista) {
                model.addElement(trabajador);
            }

            jComboBox1.setModel(model);

        } catch (Exception ex) {
            Logger.getLogger(TablaPedidos.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(this,
                    "Error al cargar los trabajadores para delivery: " + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void habilitarCamposTab2(boolean estado) {
        // Campos de texto
        txtDocumento.setEnabled(estado);
        txtxCliente.setEnabled(estado);
        txtTelefono.setEnabled(estado);
        txtDireccion.setEnabled(estado);
        jButton6.setEnabled(estado);
        jButton7.setEnabled(estado);
        // ComboBoxes
        jComboBox1.setEnabled(estado); // ComboBox para Mozo
        jComboBox2.setEnabled(estado); // ComboBox para Delivery
        jcbTipoPedido.setEnabled(estado);
        jComboBox3.setEnabled(estado);
        jSpinner1.setEnabled(estado);
        jLabel13.setVisible(false);
        // Si hay otros campos en el segundo tab, agregarlos aqui
        // Por ejemplo:
        // txtOtrosCampos.setEnabled(estado);
        // btnGuardar.setEnabled(estado);
        // etc...
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
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton9;
    private javax.swing.JComboBox<Trabajador> jComboBox1;
    private javax.swing.JComboBox<Trabajador> jComboBox2;
    private javax.swing.JComboBox<Producto> jComboBox3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JSpinner jSpinner1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JComboBox<String> jcbTipoPedido;
    private javax.swing.JTextArea txtDireccion;
    private javax.swing.JTextField txtDocumento;
    private javax.swing.JTextField txtTelefono;
    private javax.swing.JTextField txtTotal;
    private javax.swing.JTextField txtxCliente;
    // End of variables declaration//GEN-END:variables

}
