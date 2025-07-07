package proyectopolleria.view;

import java.text.DecimalFormat;
import java.util.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.JOptionPane;
import proyectopolleria.controller.GestionDatosPedido;

public class TablaOrdenes extends javax.swing.JFrame {
    
    DefaultTableModel miModelo;
    String [] Cabecera={"Usuario","Fecha","Cod.Orden","Mesa","Mozo","Dni","Cliente","Cod.Prod","Descripcion","Cantidad"};
    
    public class Nodo {
      String Codigo;
      String orden;
      String cantidad;
      String mesa;
      String dni;
      String cliente;
      String moso;
      String direccion;
      float importe;
      Nodo sig;

        public Nodo(String Codigo, String orden, String cantidad,String mesa,String dni,String cliente,String moso,String direccion,float importe) {
            this.Codigo = Codigo;
            this.orden = orden;
            this.cantidad = cantidad;
            this.mesa = mesa;
            this.dni = dni;
            this.cliente = cliente;
            this.moso = moso;
            this.direccion = direccion;
            this.importe = importe;
            sig=null;
        }
    }
   
    public Nodo frente,fincola;
    public Nodo pFound;
    int num=0,tam;
    
    public TablaOrdenes() {
        initComponents();
        setLocationRelativeTo(null);
        
        miModelo = new DefaultTableModel(null, Cabecera); // Inicializa el modelo de tabla
        
        // Obtén datos desde el archivo CSV utilizando GestionDatosPedido
        GestionDatosPedido gestionDatos = new GestionDatosPedido("ruta_del_archivo.csv");
        List<String[]> datos = gestionDatos.cargarDatos();
        
        // Cargar datos en el modelo de tabla
        for (String[] fila : datos) {
            miModelo.addRow(fila);
        }
        
        // Asignar el modelo a la tabla
        TBREGISTROS.setModel(miModelo);
        // Mostrar la cantidad de filas en TXTTAM
        TXTTAM.setText(String.valueOf(miModelo.getRowCount()));
    
        fincola=null;
        tam=0;
    }
    
    private void mensaje (String data) {
       StringTokenizer st = new StringTokenizer(data ,",");
       
       String c = st.nextToken();
       String o = st.nextToken();
       String ca = st.nextToken();
       String me = st.nextToken();
       String dn = st.nextToken();
       String cli = st.nextToken();
       String mo = st.nextToken();
       String im= st.nextToken();
       String datos = "Descripcion del dato Atendido : \n"+
               "codigo          :"+c+"\n"+
               "Orden  : "+o+"\n"+
               "Cantidad       :"+ca+"\n"+
               "Mesa           :"+me+"\n"+
               "Dni            :"+dn+"\n"+
               "Cliente        :"+cli+"\n"+
               "Moso             :"+mo+"\n"+
               "Importe        :"+im+"\n";
       
       JOptionPane.showMessageDialog(this,datos);
    }
    
    private void encolar (String cod, String or ,String can,String me,String dn, String cli,String mo,String di, float im){
        Nodo nuevo = new Nodo (cod,or,can,me,dn,cli,mo,di,im);
        if(frente == null)
            frente = nuevo;
        else
            fincola.sig=nuevo;
        fincola=nuevo;
        fincola.sig=null;
    }
     private String frente() {
        String eliminado="";
        Nodo aux=frente;
        
        String c =aux.Codigo;
        String o =aux.orden;
        String ca = aux.cantidad;
        String me = aux.mesa;
        String dn = aux.dni;
        String cli = aux.cliente;
        String mo = aux.moso;
        String di = aux.direccion;
        float su = aux.importe;
        eliminado = c+","+o+","+ca+","+me+","+dn+","+cli+","+di+","+mo+","+String.valueOf(su);
        
        frente = frente.sig;
        return eliminado;
    }
     private Nodo buscar(Nodo tope,String cod){                                             
        Nodo pos = frente;
        
        while (pos!= null && !cod.equalsIgnoreCase(pos.Codigo))
            pos=pos.sig;
        
        return pos;
    }  
     
    void Verdatos (){                                             
       String cod,des,can,me,dn,cli,mo,di,im;
       Nodo aux = frente;
       vaciar_tabla();
       num=0;
       while (aux!=null){                                             
             cod = aux.Codigo;
             des = aux.orden;
             can = aux.cantidad;
             me = aux.mesa;
             dn = aux.dni;
             cli = aux.cliente;
             mo = aux.moso;
             di = aux.direccion;
             DecimalFormat df2 = new DecimalFormat("####.00");
             im = df2.format(aux.importe);
             num++;
             Object[] fila = {num,cod,des,can,me,dn,cli,mo,di,im};
             miModelo.addRow(fila);
             aux = aux.sig;
            } 
       TXTTAM.setText(String.valueOf(num));
    } 
    void vaciar_tabla(){
        int filas = TBREGISTROS.getRowCount();
        for(int i=0;i<filas;i++)
            miModelo.removeRow(0);
    }
     void limpiarEntradas(){
        CBMESA.setSelectedIndex(0);
        TXTCLIENTE.setText("");
        TXTDNI.setText("");
        CBMOZO.setSelectedIndex(0);
        TXTCODIGO.setText("");
        CBORDEN.setSelectedIndex(0);
        TXTCANTIDAD.setText("");
        TXTIMPORTE.setText("");
        txtdireccion.setText("");
        TXTCLIENTE.requestFocus();
    }
     void Deshabilitar(){
        BTNATENDIDO.setEnabled(false);
        BTNGUARDAR.setEnabled(true);
        CBMESA.setEnabled(false);
        CBMOZO.setEnabled(false);
        txtdireccion.setEnabled(false);
    }
     void Habilitar(){
       BTNATENDIDO.setEnabled(true);
       BTNGUARDAR.setEnabled(false);;
    }
     void habilitarde (){
         txtdireccion.setEnabled(true);
         CBMOZO.setEnabled(false);
         CBMESA.setEnabled(false);
     }
     void habilitarme (){
         txtdireccion.setEnabled(false);
         CBMESA.setEnabled(true);
         CBMOZO.setEnabled(true);
     }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton2 = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        BTNGUARDAR = new javax.swing.JButton();
        BTNATENDIDO = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        CBORDEN = new javax.swing.JComboBox<>();
        jLabel7 = new javax.swing.JLabel();
        TXTCLIENTE = new javax.swing.JTextField();
        TXTDNI = new javax.swing.JTextField();
        TXTCANTIDAD = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        TXTIMPORTE = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        CODIGO = new javax.swing.JLabel();
        TXTCODIGO = new javax.swing.JTextField();
        BTNACTUALIZAR = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        TBREGISTROS = new javax.swing.JTable();
        TXTTAM = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        BTNCONSULTAR = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        txtdireccion = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        CBMESA = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        CBMOZO = new javax.swing.JComboBox<>();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();

        jButton2.setText("jButton2");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Segoe UI Black", 0, 36)); // NOI18N
        jLabel8.setText("ORDENES");

        BTNGUARDAR.setText("GUARDAR");
        BTNGUARDAR.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BTNGUARDARActionPerformed(evt);
            }
        });

        BTNATENDIDO.setText("ATENDIDO");
        BTNATENDIDO.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BTNATENDIDOActionPerformed(evt);
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel3.setText("ORDEN:");

        jLabel4.setText("IMPORTE:");

        CBORDEN.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { ".", "COMBO 1", "COMBO 2", "COMBO 3", "COMBO DUO", "COMBO FAMILIAR", " " }));

        jLabel7.setText("CANTIDAD:");
        jLabel7.setToolTipText("");

        jLabel1.setText("CLIENTE:");

        jLabel2.setText("DNI:");

        CODIGO.setText("CODIGO:");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(28, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3)
                    .addComponent(jLabel7)
                    .addComponent(jLabel4)
                    .addComponent(CODIGO))
                .addGap(35, 35, 35)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(TXTCLIENTE)
                    .addComponent(TXTDNI)
                    .addComponent(TXTCANTIDAD)
                    .addComponent(TXTIMPORTE)
                    .addComponent(CBORDEN, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(TXTCODIGO))
                .addGap(19, 19, 19))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(CODIGO)
                    .addComponent(TXTCODIGO, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(TXTCLIENTE, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(14, 14, 14)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(TXTDNI, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(CBORDEN, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(TXTCANTIDAD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(TXTIMPORTE, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(55, Short.MAX_VALUE))
        );

        BTNACTUALIZAR.setText("ACTUALIZAR");
        BTNACTUALIZAR.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BTNACTUALIZARActionPerformed(evt);
            }
        });

        TBREGISTROS.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(TBREGISTROS);

        TXTTAM.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TXTTAMActionPerformed(evt);
            }
        });

        jLabel9.setText("N° Ordenes");

        BTNCONSULTAR.setText("CONSULTAR");
        BTNCONSULTAR.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BTNCONSULTARActionPerformed(evt);
            }
        });

        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel10.setText("DIRECCION");

        jButton1.setText("DELIVERY");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtdireccion, javax.swing.GroupLayout.PREFERRED_SIZE, 274, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 26, Short.MAX_VALUE))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 26, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(txtdireccion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(22, 22, 22))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel5.setText("MESA");

        CBMESA.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { ".", "MEZA 1", "MEZA 2", "MEZA 3", "MEZA 4", "MEZA 5", " " }));

        jLabel6.setText("MOZO");

        CBMOZO.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { ".", "ALBERTO", "KIARA", "KARINA ", "CARLOS", "VICTOR" }));

        jButton3.setText("MEZA");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(15, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6))
                .addGap(35, 35, 35)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton3)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(CBMESA, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(CBMOZO, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(14, 14, 14))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jButton3)
                .addGap(26, 26, 26)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(CBMESA, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 39, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(CBMOZO, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(31, 31, 31))
        );

        jButton4.setText("Salir");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(577, 577, 577)
                                .addComponent(jButton4))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(294, 294, 294)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(BTNATENDIDO, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(BTNGUARDAR, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(BTNACTUALIZAR)
                                    .addComponent(BTNCONSULTAR))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jScrollPane1)
                                .addGroup(layout.createSequentialGroup()
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                            .addComponent(jLabel9)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                            .addComponent(TXTTAM, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGap(153, 153, 153))
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGap(18, 18, 18)))
                                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(274, 274, 274)
                        .addComponent(jLabel8)))
                .addContainerGap(30, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(25, 25, 25)
                                .addComponent(BTNGUARDAR)
                                .addGap(18, 18, 18)
                                .addComponent(BTNCONSULTAR)
                                .addGap(18, 18, 18)
                                .addComponent(BTNACTUALIZAR)
                                .addGap(18, 18, 18)
                                .addComponent(BTNATENDIDO))
                            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(31, 31, 31)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(TXTTAM, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 226, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton4)
                .addGap(28, 28, 28))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BTNGUARDARActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BTNGUARDARActionPerformed
        String cod=TXTCODIGO.getText();
        String des=CBORDEN.getSelectedItem().toString();
        String me= CBMESA.getSelectedItem().toString();
        String can= TXTCANTIDAD.getText().toUpperCase();
        String im= TXTIMPORTE.getText();
        String dn = TXTDNI.getText().toUpperCase();
        String cli = TXTCLIENTE.getText().toUpperCase();
        String mo = CBMOZO.getSelectedItem().toString();
        String di = txtdireccion.getText().toUpperCase();
        
        encolar(cod,des,can,me,dn,cli,mo,di,Float.parseFloat(im));
        tam=tam+1;
        limpiarEntradas();
        Verdatos();
    }//GEN-LAST:event_BTNGUARDARActionPerformed

    private void BTNATENDIDOActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BTNATENDIDOActionPerformed
       if(frente==null){
            JOptionPane.showMessageDialog(this, "La cola esta vacia");
            TXTCODIGO.requestFocus();
        }else {
            String dato= frente();
            mensaje(dato);
            Verdatos();
            limpiarEntradas();
            if(frente==null){
                JOptionPane.showMessageDialog(this, "La lista esta vacia");
                Deshabilitar();
            }
        }
    }//GEN-LAST:event_BTNATENDIDOActionPerformed

    private void BTNCONSULTARActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BTNCONSULTARActionPerformed
        String cod =  TXTCODIGO.getText();
        if(cod.equalsIgnoreCase("")){
            JOptionPane.showMessageDialog(this, "Ingrese un Codigo por favor");
        }else{
            pFound = buscar(fincola,cod);
                if(pFound!=null){
                    TXTCLIENTE.setText(pFound.cliente);
                    TXTDNI.setText(pFound.dni);
                    if(pFound.orden.equalsIgnoreCase("COMBO 1")){
                        CBORDEN.setSelectedIndex(2);
                    }else if(pFound.orden.equalsIgnoreCase("COMBO 2")){
                        CBORDEN.setSelectedIndex(3);
                    }else if(pFound.orden.equalsIgnoreCase("COMBO 3")){
                        CBORDEN.setSelectedIndex(4);
                    }else if (pFound.orden.equalsIgnoreCase("COMBO DUO")){
                        CBORDEN.setSelectedIndex(5);
                    }else if(pFound.orden.equalsIgnoreCase("COMBO FAMILIAR")){
                        CBORDEN.setSelectedIndex(6);
                    };
                    TXTCANTIDAD.setText(pFound.cantidad);
                    TXTIMPORTE.setText(String.valueOf(pFound.importe));
                    if(pFound.mesa.equalsIgnoreCase("MESA 1")){
                        CBMESA.setSelectedIndex(2);
                    }else if(pFound.mesa.equalsIgnoreCase("MESA 2")){
                        CBMESA.setSelectedIndex(3);
                    }else if(pFound.mesa.equalsIgnoreCase("MESA 3")){
                        CBMESA.setSelectedIndex(4);
                    }else if(pFound.mesa.equalsIgnoreCase("MESA 4")){
                        CBMESA.setSelectedIndex(5);
                    }else if(pFound.mesa.equalsIgnoreCase("MESA 5")){
                        CBMESA.setSelectedIndex(6);
                    };
                    if(pFound.moso.equalsIgnoreCase("ALBERTO")){
                        CBMESA.setSelectedIndex(2);
                    }else if(pFound.moso.equalsIgnoreCase("KIARA")){
                        CBMESA.setSelectedIndex(3);
                    }else if(pFound.moso.equalsIgnoreCase("KARINA")){
                        CBMESA.setSelectedIndex(4);
                    }else if(pFound.moso.equalsIgnoreCase("CARLOS")){
                        CBMESA.setSelectedIndex(5);
                    }else if(pFound.moso.equalsIgnoreCase("VICTOR")){
                        CBMESA.setSelectedIndex(6);
                    };
                }
        }
    }//GEN-LAST:event_BTNCONSULTARActionPerformed

    private void BTNACTUALIZARActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BTNACTUALIZARActionPerformed
       pFound.Codigo = TXTCODIGO.getText();
       pFound.cliente = TXTCLIENTE.getText().toUpperCase();
       pFound.dni = TXTDNI.getText().toUpperCase();
       pFound.orden = CBORDEN.getSelectedItem().toString();
       pFound.cantidad = TXTCANTIDAD.getText().toUpperCase();
       pFound.importe = Float.parseFloat(TXTIMPORTE.getText());
       pFound.mesa = CBMESA.getSelectedItem().toString();
       pFound.moso = CBMOZO.getSelectedItem().toString();
       
       limpiarEntradas();
       Deshabilitar();
       Verdatos();
    }//GEN-LAST:event_BTNACTUALIZARActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
       
    }//GEN-LAST:event_formWindowOpened

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
       habilitarde();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void TXTTAMActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TXTTAMActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TXTTAMActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
       habilitarme();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        this.dispose();
    }//GEN-LAST:event_jButton4ActionPerformed

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
            java.util.logging.Logger.getLogger(TablaOrdenes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TablaOrdenes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TablaOrdenes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TablaOrdenes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TablaOrdenes().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BTNACTUALIZAR;
    private javax.swing.JButton BTNATENDIDO;
    private javax.swing.JButton BTNCONSULTAR;
    private javax.swing.JButton BTNGUARDAR;
    private javax.swing.JComboBox<String> CBMESA;
    private javax.swing.JComboBox<String> CBMOZO;
    private javax.swing.JComboBox<String> CBORDEN;
    private javax.swing.JLabel CODIGO;
    private javax.swing.JTable TBREGISTROS;
    private javax.swing.JTextField TXTCANTIDAD;
    private javax.swing.JTextField TXTCLIENTE;
    private javax.swing.JTextField TXTCODIGO;
    private javax.swing.JTextField TXTDNI;
    private javax.swing.JTextField TXTIMPORTE;
    private javax.swing.JTextField TXTTAM;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
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
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField txtdireccion;
    // End of variables declaration//GEN-END:variables
}
