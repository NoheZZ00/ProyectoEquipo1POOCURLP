/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.curlp.capapresentacion;

import com.curlp.capadatos.CDFormaPago;
import com.curlp.capalogica.CLFormaPago;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author nohel
 */
public class jFraFormaPago extends javax.swing.JFrame {

    /**
     * Creates new form jFraFormaPago
     */
    public jFraFormaPago() throws SQLException {
        initComponents();
        poblarTablaFormaPago();
        encontrarCorrelativo();
        this.jTFFormaPago.requestFocus();
        this.setLocationRelativeTo(null);
    }
    
    //Método para limpiar los datos de la tabla.
    private void limpiarTabla() {
        DefaultTableModel dtm = (DefaultTableModel) this.jTblFormaPago.getModel();
        
        while (dtm.getRowCount() > 0) {
            dtm.removeRow(0);
        }
    }
    
    //Metodo para poblar las tablas
    private void poblarTablaFormaPago() throws SQLException {
        
        limpiarTabla();
        
        CDFormaPago cdc = new CDFormaPago();
        List<CLFormaPago> miLista = cdc.obtenerListaFormaPago();
        DefaultTableModel temp = (DefaultTableModel) this.jTblFormaPago.getModel();
        
        miLista.stream().map((CLFormaPago cl) -> {
            Object[] fila = new Object[2];
            fila[0] = cl.getIdFormaPago();
            fila[1] = cl.getFormaPago();
            return fila;
        }).forEachOrdered(temp::addRow);
        
    }
    
    //Metodo para encontrar correlativo del id forma pago
    private void encontrarCorrelativo() throws SQLException {
        CDFormaPago cdc = new CDFormaPago();
        CLFormaPago cl = new CLFormaPago();
        
        cl.setIdFormaPago(cdc.autoIncrementarFormaPagoId());
        this.jTFId.setText(String.valueOf(cl.getIdFormaPago()));
    }
    
    //Metodo para habilitar y desabilitar botones
    private void habilitarBotones(boolean guardar, boolean editar, boolean eliminar, boolean limpiar) {
        
        this.jBtnGuardar.setEnabled(guardar);
        this.jBtnEditar.setEnabled(editar);
        this.jBtnEliminar.setEnabled(eliminar);
        this.jBtnLimpiar.setEnabled(limpiar);
    }
    
    //Metodo para limpiar textfield
    private void limpiarTextField() {
        this.jTFId.setText("");
        this.jTFFormaPago.setText("");
        this.jTFFormaPago.requestFocus();
    }
    
    //metodo para validar la textfield
    private boolean validarTextField() {
        boolean estado;
        estado = !this.jTFFormaPago.getText().equals("");
        return estado;
    }
    
    //Metodo para insertar
    private void insertarFormaPago() {
        if (!validarTextField()) {
            JOptionPane.showMessageDialog(null, "Tiene que ingresar la forma de pago", "Sistema Paquetería",
                                          JOptionPane.INFORMATION_MESSAGE);
            this.jTFFormaPago.requestFocus();
        }else {
            try{
                CDFormaPago cdc = new CDFormaPago();
                CLFormaPago cl = new CLFormaPago();
                cl.setFormaPago(this.jTFFormaPago.getText().trim());
                cdc.insertarFormaPago(cl);
                
                JOptionPane.showMessageDialog(null, "Forma de Pago almacenada correctamente", "Sistema Paquetería",
                                          JOptionPane.INFORMATION_MESSAGE);
                
            }catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Error al guardar la forma de pago:" + ex);
                this.jTFFormaPago.requestFocus();
            }
        }
    }
    
    //Metodo para llamar el metodo insertar
    private void guardar() throws SQLException {
        insertarFormaPago();
        poblarTablaFormaPago();
        habilitarBotones(true, false, false, true);
        limpiarTextField();
        encontrarCorrelativo();
    }
    
    //Metodo para actualizar un registro de la tabla forma pago.
    private void actualizarFormaPago() {
        if (!validarTextField()) {
            JOptionPane.showMessageDialog(null, "Tiene que ingresar la forma de pago", "Sistema Paquetería",
                                          JOptionPane.INFORMATION_MESSAGE);
            this.jTFFormaPago.requestFocus();
        }else {
            try{
                CDFormaPago cdc = new CDFormaPago();
                CLFormaPago cl = new CLFormaPago();
                cl.setIdFormaPago(Integer.parseInt(this.jTFId.getText().trim()));
                cl.setFormaPago(this.jTFFormaPago.getText().trim());
                cdc.actualizarformaPago(cl);
                
                JOptionPane.showMessageDialog(null, "Registro actualizado correctamente", "Sistema Paquetería",
                                          JOptionPane.INFORMATION_MESSAGE);
                
            }catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Error al actualizar el registro:" + ex);
                this.jTFFormaPago.requestFocus();
            }
        }
    }
    
    //Metodo para selecionar los datos de la fila y asi poder modificarlos
    private void filaSeleccionada() {
        if (this.jTblFormaPago.getSelectedRow() != -1) {
            this.jTFId.setText(String.valueOf(this.jTblFormaPago.getValueAt(this.jTblFormaPago.getSelectedRow(), 0)));
            this.jTFFormaPago.setText(String.valueOf(this.jTblFormaPago.getValueAt(this.jTblFormaPago.getSelectedRow(), 1)));     
        }
    }
    
    //Metodo para llamar el metodo actualizar registro de la tabla
    private void editar() throws SQLException {
        actualizarFormaPago();
        poblarTablaFormaPago();
        habilitarBotones(true, false, false, true);
        limpiarTextField();
    }
    
    //Metodo para eliminar
    private void eliminarFormaPago() {
        try {
            CDFormaPago cdc = new CDFormaPago();
            CLFormaPago cl = new CLFormaPago();
            cl.setIdFormaPago(Integer.parseInt(this.jTFId.getText().trim()));
            cdc.eliminarFormaPago(cl);

            JOptionPane.showMessageDialog(null, "Registro eliminado correctamente", "Sistema Paquetería",
                    JOptionPane.INFORMATION_MESSAGE);

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al eliminar el registro:" + ex);
            this.jTFFormaPago.requestFocus();
        }
    }
    private void eliminar() throws SQLException {
        int resp = JOptionPane.showConfirmDialog(null, "Esta seguro de que desea eliminar?", "Sistema Paquetería",
                                                 JOptionPane.YES_NO_OPTION);
        if (resp == JOptionPane.YES_OPTION) {
            try {
                eliminarFormaPago();
                poblarTablaFormaPago();
                habilitarBotones(true, false, false, true);
                limpiarTextField();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Error al eliminar el registro;" + ex);
            }
        }else {
            limpiarTextField();
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
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jTFId = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jTFFormaPago = new javax.swing.JTextField();
        jBtnLimpiar = new javax.swing.JButton();
        jBtnGuardar = new javax.swing.JButton();
        jBtnEditar = new javax.swing.JButton();
        jBtnEliminar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTblFormaPago = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(118, 213, 213));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 30)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("GESTIÓN DE FORMA DE PAGO");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(149, 149, 149)
                .addComponent(jLabel1)
                .addContainerGap(149, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jLabel1)
                .addContainerGap(25, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 750, 80));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jPanel3.setBackground(new java.awt.Color(204, 232, 232));

        jPanel4.setBackground(new java.awt.Color(204, 232, 232));
        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Forma de Pago", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", 1, 14), new java.awt.Color(51, 51, 51))); // NOI18N
        jPanel4.setForeground(new java.awt.Color(255, 255, 255));

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 0, 0));
        jLabel2.setText("Id Forma de Pago");

        jTFId.setEditable(false);

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 0, 0));
        jLabel3.setText("Forma de Pago");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3)
                    .addComponent(jTFId, javax.swing.GroupLayout.DEFAULT_SIZE, 159, Short.MAX_VALUE)
                    .addComponent(jTFFormaPago))
                .addContainerGap(37, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTFId, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addComponent(jLabel3)
                .addGap(18, 18, 18)
                .addComponent(jTFFormaPago, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(49, Short.MAX_VALUE))
        );

        jBtnLimpiar.setBackground(new java.awt.Color(118, 213, 213));
        jBtnLimpiar.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        jBtnLimpiar.setText("Limpiar");
        jBtnLimpiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnLimpiarActionPerformed(evt);
            }
        });

        jBtnGuardar.setBackground(new java.awt.Color(118, 213, 213));
        jBtnGuardar.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        jBtnGuardar.setText("Guardar");
        jBtnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnGuardarActionPerformed(evt);
            }
        });

        jBtnEditar.setBackground(new java.awt.Color(118, 213, 213));
        jBtnEditar.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        jBtnEditar.setText("Editar");
        jBtnEditar.setEnabled(false);
        jBtnEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnEditarActionPerformed(evt);
            }
        });

        jBtnEliminar.setBackground(new java.awt.Color(118, 213, 213));
        jBtnEliminar.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        jBtnEliminar.setText("Eliminar");
        jBtnEliminar.setEnabled(false);
        jBtnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnEliminarActionPerformed(evt);
            }
        });

        jTblFormaPago.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Id Forma Pgo", "Forma de Pago"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTblFormaPago.setGridColor(new java.awt.Color(255, 255, 255));
        jTblFormaPago.setShowHorizontalLines(false);
        jTblFormaPago.setShowVerticalLines(false);
        jTblFormaPago.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTblFormaPagoMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTblFormaPago);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addComponent(jBtnGuardar)
                        .addGap(42, 42, 42)
                        .addComponent(jBtnEditar, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(45, 45, 45)
                        .addComponent(jBtnEliminar)
                        .addGap(26, 26, 26)
                        .addComponent(jBtnLimpiar, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 455, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(20, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(20, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jBtnEditar)
                    .addComponent(jBtnEliminar)
                    .addComponent(jBtnGuardar)
                    .addComponent(jBtnLimpiar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 217, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(17, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(13, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 78, 750, 330));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jBtnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnGuardarActionPerformed
        try {
            guardar();
        } catch (SQLException ex) {
            Logger.getLogger(jFraFormaPago.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jBtnGuardarActionPerformed

    private void jTblFormaPagoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTblFormaPagoMouseClicked
        filaSeleccionada();
        habilitarBotones(false, true, true, true);
        
    }//GEN-LAST:event_jTblFormaPagoMouseClicked

    private void jBtnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnEditarActionPerformed
        try {
            editar();
        } catch (SQLException ex) {
            Logger.getLogger(jFraFormaPago.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jBtnEditarActionPerformed

    private void jBtnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnEliminarActionPerformed
        try {
            eliminar();
        } catch (SQLException ex) {
            Logger.getLogger(jFraFormaPago.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jBtnEliminarActionPerformed

    private void jBtnLimpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnLimpiarActionPerformed
        limpiarTextField();
    }//GEN-LAST:event_jBtnLimpiarActionPerformed

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
            java.util.logging.Logger.getLogger(jFraFormaPago.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(jFraFormaPago.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(jFraFormaPago.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(jFraFormaPago.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new jFraFormaPago().setVisible(true);
                } catch (SQLException ex) {
                    Logger.getLogger(jFraFormaPago.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBtnEditar;
    private javax.swing.JButton jBtnEliminar;
    private javax.swing.JButton jBtnGuardar;
    private javax.swing.JButton jBtnLimpiar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTFFormaPago;
    private javax.swing.JTextField jTFId;
    private javax.swing.JTable jTblFormaPago;
    // End of variables declaration//GEN-END:variables
}
