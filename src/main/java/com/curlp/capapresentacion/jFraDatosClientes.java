/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.curlp.capapresentacion;

import com.curlp.capadatos.CDCiudad;
import com.curlp.capadatos.CDCliente;
import com.curlp.capadatos.CDSexo;
import com.curlp.capalogica.CLCliente;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Oniel_Espinal
 */
public class jFraDatosClientes extends javax.swing.JFrame {

    /**
     * Creates new form jFraDatosClientes
     */
    public jFraDatosClientes() throws SQLException {
        initComponents();
        poblarComboCiudad();
        polarComboSexo();
        poblarTablaClientes();
        habilitarBotones(true, false, true);
        this.jTFFDniCliente.requestFocus();
        setLocationRelativeTo(null);
    }
    
    private void limpiarTabla() {
        DefaultTableModel dtm = (DefaultTableModel) this.jTblCliente.getModel();

        while (dtm.getRowCount() > 0) {
            dtm.removeRow(0);
        }
    }

    private void limpiarTextField() {
        this.jTFFDniCliente.setValue(null);
        this.jTFNombreCliente.setText("");
        this.jTFApellidosCliente.setText("");
        this.jTADireccion.setText("");
        this.jTADireccion.setText("");
        this.jTFFCelular.setText(null);
        this.jCboSexo.setSelectedIndex(0);
        this.jCboCiudad.setSelectedIndex(0);
        this.jTFFDniCliente.requestFocus();

    }
    private void polarComboSexo() throws SQLException {
        CDSexo cds = new CDSexo();

        String[] sexo = new String[cds.cargarSexos().size()];
        sexo = cds.cargarSexos().toArray(sexo);

        DefaultComboBoxModel modeloSexo = new DefaultComboBoxModel(sexo);
        this.jCboSexo.setModel(modeloSexo);
    }
    // Método para poblar el combobox Ciudad.
    private void poblarComboCiudad() throws SQLException {
        CDCiudad cdc = new CDCiudad();

        String[] ciudad = new String[cdc.cargarComboCiudades().size()];
        ciudad = cdc.cargarComboCiudades().toArray(ciudad);

        DefaultComboBoxModel modeloCiudad = new DefaultComboBoxModel(ciudad);
        this.jCboCiudad.setModel(modeloCiudad);
    }
    
    // Método para poblar datos en la tabla.
    private void poblarTablaClientes() throws SQLException {
        limpiarTabla();

        CDCliente cdc = new CDCliente();
        List<CLCliente> miLista = cdc.obtenerListaCliente();
        DefaultTableModel dtm = (DefaultTableModel) this.jTblCliente.getModel();
        
        miLista.stream().map(clc -> {
            Object[] fila = new Object[7];
            fila[0] = clc.getDniCliente();
            fila[1] = clc.getNombre();
            fila[2] = clc.getApellidos();
            fila[3] = clc.getDireccion();
            fila[4] = clc.getCelular();
            fila[5] = clc.getSexo();
            fila[6] = clc.getCiudad();
            return fila;
        }).forEachOrdered(dtm::addRow);
  
        
    }
    // Método para poblar datos en la tabla por DNI.
    private void poblarTablaClientesPorDNI(String dniCliente) throws SQLException {
        limpiarTabla();

        CDCliente cdc = new CDCliente();
        List<CLCliente> miLista = cdc.obtenerListaClientePorDNI(dniCliente);
        DefaultTableModel dtm = (DefaultTableModel) this.jTblCliente.getModel();

        miLista.stream().map(clc -> {
            Object[] fila = new Object[7];
            fila[0] = clc.getDniCliente();
            fila[1] = clc.getNombre();
            fila[2] = clc.getApellidos();
            fila[3] = clc.getDireccion();
            fila[4] = clc.getCelular();
            fila[5] = clc.getSexo();
            fila[6] = clc.getCiudad();
            return fila;
        }).forEachOrdered(dtm::addRow);

        
    }
    // Método para seleccionar la fila en la jTable.
    private void filaSeleccionada() throws ParseException {

        if (this.jTblCliente.getSelectedRow() != -1) {
            this.jTFFDniCliente.setText(String.valueOf(this.jTblCliente.getValueAt(this.jTblCliente.getSelectedRow(), 0)));
            this.jTFNombreCliente.setText(String.valueOf(this.jTblCliente.getValueAt(this.jTblCliente.getSelectedRow(), 1)));
            this.jTFApellidosCliente.setText(String.valueOf(this.jTblCliente.getValueAt(this.jTblCliente.getSelectedRow(), 2)));
            this.jTADireccion.setText(String.valueOf(this.jTblCliente.getValueAt(this.jTblCliente.getSelectedRow(), 3)));
            this.jTFFCelular.setText(String.valueOf(this.jTblCliente.getValueAt(this.jTblCliente.getSelectedRow(), 4)));
            this.jCboSexo.setSelectedItem(String.valueOf(this.jTblCliente.getValueAt(this.jTblCliente.getSelectedRow(), 5)));
            this.jCboCiudad.setSelectedItem(String.valueOf(this.jTblCliente.getValueAt(this.jTblCliente.getSelectedRow(), 6)));
        }
    }
    // Método para habilitar y deshabilitar botones.
    private void habilitarBotones(boolean salvar, boolean editar, boolean limpiar) {
        this.jBtnGuardar.setEnabled(salvar);
        this.jBtnEditar.setEnabled(editar);
        this.jBtnCancelar.setEnabled(limpiar);
    }

    // Método para validar textfield.
    private boolean validarTextField() {
        boolean estado;

        estado = !(this.jTFFDniCliente.getText().isEmpty() || this.jTFNombreCliente.getText().isEmpty()
                || this.jTFApellidosCliente.getText().isEmpty() || this.jTADireccion.getText().isEmpty()
                || this.jTFFCelular.getText().isEmpty() || this.jCboCiudad.getSelectedIndex() == -1
                || this.jCboCiudad.getSelectedIndex() == -1);
        return estado;
    }
    // Método para insertar un cliente en la base de datos.
    private void insertarCliente() {
        if (validarTextField()) {
            try {
                CDCliente cdc = new CDCliente();
                CLCliente clc = new CLCliente();

                clc.setDniCliente(this.jTFFDniCliente.getText().trim());
                clc.setNombre(this.jTFNombreCliente.getText().trim());
                clc.setApellidos(this.jTFApellidosCliente.getText().trim());
                clc.setDireccion(this.jTADireccion.getText().trim());
                clc.setCelular(this.jTFFCelular.getText().trim());
                clc.setSexo(this.jCboSexo.getSelectedItem().toString());
                clc.setCiudad(this.jCboCiudad.getSelectedItem().toString());
                cdc.insertarCliente(clc);
                JOptionPane.showMessageDialog(null, "Registro almacenado satisfactoriamente...", "control paqueteria",
                        JOptionPane.INFORMATION_MESSAGE);
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Error al guardar el cliente: " + ex);

            }
        } else {
            JOptionPane.showMessageDialog(null, "Verifique que todos los datos requeridos tengan valor", "control paquetería",
                    JOptionPane.INFORMATION_MESSAGE);
            this.jTFFDniCliente.requestFocus();
        }
    }
     // Método para llamar el metodo de insertar cliente
    private void guardar() throws SQLException {
        insertarCliente();
        poblarTablaClientes();
        habilitarBotones(true, false, true);
        limpiarTextField();
    }
    // Método para actualizar un cliente en la base de datos.
    private void actualizarCliente() {
        if (validarTextField()) {
            try {
                CDCliente cdc = new CDCliente();
                CLCliente clc = new CLCliente();

                clc.setDniCliente(this.jTFFDniCliente.getText().trim());
                clc.setNombre(this.jTFNombreCliente.getText().trim());
                clc.setApellidos(this.jTFApellidosCliente.getText().trim());
                clc.setDireccion(this.jTADireccion.getText().trim());
                clc.setCelular(this.jTFFCelular.getText().trim());
                clc.setSexo(this.jCboSexo.getSelectedItem().toString());
                clc.setCiudad(this.jCboCiudad.getSelectedItem().toString());
                cdc.actualizarCliente(clc);
                JOptionPane.showMessageDialog(null, "Registro actualizado satisfactoriamente...", "control paqueteria",
                        JOptionPane.INFORMATION_MESSAGE);
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Error al actualizar el cliente: " + ex);

            }
        } else {
            JOptionPane.showMessageDialog(null, "Verifique que todos los datos requeridos tengan valor", "control paquetería",
                    JOptionPane.INFORMATION_MESSAGE);
            this.jTFFDniCliente.requestFocus();
        }
    }
    
    // Método para llamar al método de actualizarCliente.
    private void actualizar() throws SQLException {
        actualizarCliente();
        poblarTablaClientes();
        habilitarBotones(true, false, true);
        limpiarTextField();
    }
    
    // Metodo para eliminacion de un cliente
    private void eliminarCliente(){
        try {
            CDCliente cdc = new CDCliente();
            CLCliente clc = new CLCliente();

            String DNI = null;
            if (this.jTblCliente.getSelectedRow() != -1) {
                DNI = String.valueOf(this.jTblCliente.getValueAt(this.jTblCliente.getSelectedRow(), 0));
            }

            clc.setDniCliente(DNI);

            cdc.eliminarCliente(clc);

            JOptionPane.showMessageDialog(null, "Registro eliminado con éxito", "Control de paquetería",
                    JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al eliminar cliente" + ex);
        }
    }
    
    private void eliminar() throws SQLException{
        int resp = JOptionPane.showConfirmDialog(null, "¿Estás seguro que deseas eliminar el registro?", "Control paquetería",
                JOptionPane.YES_NO_OPTION);
        if (resp == JOptionPane.YES_OPTION) {

            String DNI;
            try {
                eliminarCliente();
                poblarTablaClientes();
                limpiarTextField();
                habilitarBotones(true, false, true);
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Error: " + ex);
            }
        } else {
            limpiarTextField();
            this.jTFFDniCliente.requestFocus();
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

        jBGSexo = new javax.swing.ButtonGroup();
        jPMOpcion = new javax.swing.JPopupMenu();
        jMISeleccionar = new javax.swing.JMenuItem();
        jMIEliminar = new javax.swing.JMenuItem();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jTFNombreCliente = new javax.swing.JTextField();
        jTFApellidosCliente = new javax.swing.JTextField();
        jCboCiudad = new javax.swing.JComboBox<>();
        jCboSexo = new javax.swing.JComboBox<>();
        jTFFDniCliente = new javax.swing.JFormattedTextField();
        jTFFCelular = new javax.swing.JFormattedTextField();
        jPanel5 = new javax.swing.JPanel();
        jBtnCancelar = new javax.swing.JButton();
        jBtnGuardar = new javax.swing.JButton();
        jBtnEditar = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTADireccion = new javax.swing.JTextArea();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTblCliente = new javax.swing.JTable();
        jBtnMostraTodosClientes = new javax.swing.JButton();
        jTFBuscarCliente = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();

        jMISeleccionar.setText("Seleccionar");
        jMISeleccionar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMISeleccionarActionPerformed(evt);
            }
        });
        jPMOpcion.add(jMISeleccionar);

        jMIEliminar.setText("Eliminar");
        jMIEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMIEliminarActionPerformed(evt);
            }
        });
        jPMOpcion.add(jMIEliminar);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(118, 213, 213));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 30)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("GESTIÓN DE CLIENTE");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(278, 278, 278)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 457, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(285, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 70, Short.MAX_VALUE)
        );

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1020, 70));

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setForeground(new java.awt.Color(255, 255, 255));

        jPanel2.setBackground(new java.awt.Color(204, 232, 232));
        jPanel2.setMinimumSize(new java.awt.Dimension(660, 334));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel2.setText("Nombres del Cliente");
        jPanel2.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 70, 154, 27));

        jLabel3.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel3.setText("Apellidos del Cliente");
        jPanel2.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 120, 150, 28));

        jLabel4.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel4.setText("DNI Cliente");
        jLabel4.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jPanel2.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 30, 150, 25));

        jLabel5.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel5.setText("Dirección");
        jPanel2.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 170, 154, 28));

        jLabel6.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel6.setText("Celular");
        jPanel2.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 260, 154, 29));

        jLabel7.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel7.setText("Sexo");
        jPanel2.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 310, 154, 27));

        jLabel8.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel8.setText("Ciudad");
        jPanel2.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 370, 154, 28));
        jPanel2.add(jTFNombreCliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 70, 190, -1));
        jPanel2.add(jTFApellidosCliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 120, 190, -1));

        jPanel2.add(jCboCiudad, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 370, 190, -1));

        jPanel2.add(jCboSexo, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 310, 190, -1));

        try {
            jTFFDniCliente.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("####-####-#####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        jPanel2.add(jTFFDniCliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 30, 190, -1));

        try {
            jTFFCelular.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("####-####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        jPanel2.add(jTFFCelular, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 260, 190, -1));

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));

        jBtnCancelar.setBackground(new java.awt.Color(118, 213, 213));
        jBtnCancelar.setText("Cancelar");
        jBtnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnCancelarActionPerformed(evt);
            }
        });

        jBtnGuardar.setBackground(new java.awt.Color(118, 213, 213));
        jBtnGuardar.setText("Guardar");
        jBtnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnGuardarActionPerformed(evt);
            }
        });

        jBtnEditar.setBackground(new java.awt.Color(118, 213, 213));
        jBtnEditar.setText("Editar");
        jBtnEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnEditarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap(39, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jBtnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jBtnGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jBtnEditar, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(31, 31, 31))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addComponent(jBtnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(72, 72, 72)
                .addComponent(jBtnGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 86, Short.MAX_VALUE)
                .addComponent(jBtnEditar, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30))
        );

        jPanel2.add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 20, 190, 350));

        jTADireccion.setColumns(20);
        jTADireccion.setRows(5);
        jScrollPane2.setViewportView(jTADireccion);

        jPanel2.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 160, 260, 80));

        jTabbedPane1.addTab("Registro de Clientes", jPanel2);

        jPanel3.setBackground(new java.awt.Color(204, 232, 232));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jTblCliente.setFont(new java.awt.Font("Lucida Console", 0, 11)); // NOI18N
        jTblCliente.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "DNI", "Nombre", "Apellidos", "Dirreccion", "Celular", "Sexo", "Ciudad"
            }
        ));
        jTblCliente.setComponentPopupMenu(jPMOpcion);
        jScrollPane1.setViewportView(jTblCliente);

        jPanel3.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 70, 910, 330));

        jBtnMostraTodosClientes.setBackground(new java.awt.Color(118, 213, 213));
        jBtnMostraTodosClientes.setText("Mostrar Todos");
        jBtnMostraTodosClientes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnMostraTodosClientesActionPerformed(evt);
            }
        });
        jPanel3.add(jBtnMostraTodosClientes, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 20, 120, 40));

        jTFBuscarCliente.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTFBuscarClienteKeyReleased(evt);
            }
        });
        jPanel3.add(jTFBuscarCliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 20, 280, 30));

        jLabel9.setText("Buscar");
        jPanel3.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(860, 30, 50, -1));

        jTabbedPane1.addTab("Listar Clientes", jPanel3);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 950, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(39, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 449, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 70, 1020, 470));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jBtnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnGuardarActionPerformed
        // TODO add your handling code here:
        try {
            guardar();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error" +ex);
        }
    }//GEN-LAST:event_jBtnGuardarActionPerformed

    private void jBtnMostraTodosClientesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnMostraTodosClientesActionPerformed
        // TODO add your handling code here:
        try {
            poblarTablaClientes();
            this.jTFBuscarCliente.setText("");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error: " + ex);
        }
    }//GEN-LAST:event_jBtnMostraTodosClientesActionPerformed

    private void jTFBuscarClienteKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTFBuscarClienteKeyReleased
        // TODO add your handling code here:
        String busqueda;
        busqueda = this.jTFBuscarCliente.getText();
        try {
            poblarTablaClientesPorDNI(busqueda);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error" + ex);
        }
    }//GEN-LAST:event_jTFBuscarClienteKeyReleased

    private void jBtnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnEditarActionPerformed
        // TODO add your handling code here:
        try {
            actualizar();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error" + ex);
        }
    }//GEN-LAST:event_jBtnEditarActionPerformed

    private void jBtnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnCancelarActionPerformed
        // TODO add your handling code here:
        limpiarTextField();
        habilitarBotones(true, false, true);
    }//GEN-LAST:event_jBtnCancelarActionPerformed

    private void jMIEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMIEliminarActionPerformed
        try {
            eliminar();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error" + ex);

        }
    }//GEN-LAST:event_jMIEliminarActionPerformed

    private void jMISeleccionarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMISeleccionarActionPerformed
        try {
            filaSeleccionada();
            habilitarBotones(false, true, true);
        } catch (ParseException ex) {
            JOptionPane.showMessageDialog(null, "Error" + ex);
        }
    }//GEN-LAST:event_jMISeleccionarActionPerformed
   
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
            java.util.logging.Logger.getLogger(jFraDatosClientes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(jFraDatosClientes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(jFraDatosClientes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(jFraDatosClientes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try{
                new jFraDatosClientes().setVisible(true);
            }catch(SQLException ex){
                Logger.getLogger(jFraDatosClientes.class.getName()).log(Level.SEVERE,null,ex);
            }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup jBGSexo;
    private javax.swing.JButton jBtnCancelar;
    private javax.swing.JButton jBtnEditar;
    private javax.swing.JButton jBtnGuardar;
    private javax.swing.JButton jBtnMostraTodosClientes;
    private javax.swing.JComboBox<String> jCboCiudad;
    private javax.swing.JComboBox<String> jCboSexo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenuItem jMIEliminar;
    private javax.swing.JMenuItem jMISeleccionar;
    private javax.swing.JPopupMenu jPMOpcion;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextArea jTADireccion;
    private javax.swing.JTextField jTFApellidosCliente;
    private javax.swing.JTextField jTFBuscarCliente;
    private javax.swing.JFormattedTextField jTFFCelular;
    private javax.swing.JFormattedTextField jTFFDniCliente;
    private javax.swing.JTextField jTFNombreCliente;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTblCliente;
    // End of variables declaration//GEN-END:variables
}
