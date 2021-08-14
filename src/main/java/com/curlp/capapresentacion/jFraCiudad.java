/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.curlp.capapresentacion;

import com.curlp.capadatos.CDCiudad;
import com.curlp.capalogica.CLCiudad;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Jaime Luna
 */
public class jFraCiudad extends javax.swing.JFrame {

    /**
     * Creates new form jFFCiudad
     * @throws java.sql.SQLException
     */
    public jFraCiudad() throws SQLException {
        initComponents();
        poblarTablaCiudad();
        encontrarCorrelativo();
        this.jTFNombreCiudad.requestFocus();
        this.setLocationRelativeTo(null);
    }

    //Metodo para Limpiar tablas 
    private void limpiarTabla() {
        DefaultTableModel dtm = (DefaultTableModel) this.jTblCiudades.getModel();
        
        while (dtm.getRowCount() > 0) {
            dtm.removeRow(0);
        }
    }
    
    //Metodo para poblar las tablas
    private void poblarTablaCiudad() throws SQLException {
        
        limpiarTabla();
        
        CDCiudad cdc = new CDCiudad();
        List<CLCiudad> miLista = cdc.obtenerListaCiudades();
        DefaultTableModel temp = (DefaultTableModel) this.jTblCiudades.getModel();
        
        miLista.stream().map((CLCiudad cl) -> {
            Object[] fila = new Object[2];
            fila[0] = cl.getIdCiudad();
            fila[1] = cl.getNombreCiudad();
            return fila;
        }).forEachOrdered(temp::addRow);
        
    }
    
    //Metodo para encontrar correlativo del id ciudad
    private void encontrarCorrelativo() throws SQLException {
        CDCiudad cdc = new CDCiudad();
        CLCiudad cl = new CLCiudad();
        
        cl.setIdCiudad(cdc.autoIncrementarCiudadId());
        this.jTFIdCiudad.setText(String.valueOf(cl.getIdCiudad()));
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
        this.jTFIdCiudad.setText("");
        this.jTFNombreCiudad.setText("");
        this.jTFNombreCiudad.requestFocus();
    }
    
    //metodo para validar la textfield
    private boolean validarTextField() {
        boolean estado;
        estado = !this.jTFNombreCiudad.getText().equals("");
        return estado;
    }
    
    //Metodo para insertar
    private void insertarCiudad() {
        if (!validarTextField()) {
            JOptionPane.showMessageDialog(null, "Tiene que ingresar el nombre de la ciudad", "Sistema Paquetería",
                                          JOptionPane.INFORMATION_MESSAGE);
            this.jTFNombreCiudad.requestFocus();
        }else {
            try{
                CDCiudad cdc = new CDCiudad();
                CLCiudad cl = new CLCiudad();
                cl.setNombreCiudad(this.jTFNombreCiudad.getText().trim());
                cdc.insertarCiudad(cl);
                
                JOptionPane.showMessageDialog(null, "Registro almacenado correctamente", "Sistema Paquetería",
                                          JOptionPane.INFORMATION_MESSAGE);
                
            }catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Error al almacenar el registro:" + ex);
                this.jTFNombreCiudad.requestFocus();
            }
        }
    }
    
    //Metodo para llamar el metodo insertar
    private void guardar() throws SQLException {
        insertarCiudad();
        poblarTablaCiudad();
        habilitarBotones(true, false, false, true);
        limpiarTextField();
        encontrarCorrelativo();
    }
    
    //Metodo para actualizar un registro de la tabla ciudad
    private void actualizarCiudad() {
        if (!validarTextField()) {
            JOptionPane.showMessageDialog(null, "Tiene que ingresar el nombre de la ciudad", "Sistema Paquetería",
                                          JOptionPane.INFORMATION_MESSAGE);
            this.jTFNombreCiudad.requestFocus();
        }else {
            try{
                CDCiudad cdc = new CDCiudad();
                CLCiudad cl = new CLCiudad();
                cl.setIdCiudad(Integer.parseInt(this.jTFIdCiudad.getText().trim()));
                cl.setNombreCiudad(this.jTFNombreCiudad.getText().trim());
                cdc.actualizarCiudad(cl);
                
                JOptionPane.showMessageDialog(null, "Registro actualizado correctamente", "Sistema Paquetería",
                                          JOptionPane.INFORMATION_MESSAGE);
                
            }catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Error al actualizar el registro:" + ex);
                this.jTFNombreCiudad.requestFocus();
            }
        }
    }
    
    //Metodo para selecionar los datos de la fila y asi poder modificarlos
    private void filaSeleccionada() {
        if (this.jTblCiudades.getSelectedRow() != -1) {
            this.jTFIdCiudad.setText(String.valueOf(this.jTblCiudades.getValueAt(this.jTblCiudades.getSelectedRow(), 0)));
            this.jTFNombreCiudad.setText(String.valueOf(this.jTblCiudades.getValueAt(this.jTblCiudades.getSelectedRow(), 1)));     
        }
    }
    
    //Metodo para llamar el metodo actualizar registro de la tabla
    private void editar() throws SQLException {
        actualizarCiudad();
        poblarTablaCiudad();
        habilitarBotones(true, false, false, true);
        limpiarTextField();
    }
    
    //Metodo para eliminar
    private void eliminarCiudad() {
        try {
            CDCiudad cdc = new CDCiudad();
            CLCiudad cl = new CLCiudad();
            cl.setIdCiudad(Integer.parseInt(this.jTFIdCiudad.getText().trim()));
            cdc.eliminarCiudad(cl);

            JOptionPane.showMessageDialog(null, "Registro eliminado correctamente", "Sistema Paquetería",
                    JOptionPane.INFORMATION_MESSAGE);

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al eliminar el registro:" + ex);
            this.jTFNombreCiudad.requestFocus();
        }
    }
    private void eliminar() throws SQLException {
        int resp = JOptionPane.showConfirmDialog(null, "Esta seguro de que desea eliminar?", "Sistema Paquetería",
                                                 JOptionPane.YES_NO_OPTION);
        if (resp == JOptionPane.YES_OPTION) {
            try {
                eliminarCiudad();
                poblarTablaCiudad();
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
        jLabel3 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jTFIdCiudad = new javax.swing.JTextField();
        jTFNombreCiudad = new javax.swing.JTextField();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTblCiudades = new javax.swing.JTable();
        jBtnGuardar = new javax.swing.JButton();
        jBtnEditar = new javax.swing.JButton();
        jBtnEliminar = new javax.swing.JButton();
        jBtnLimpiar = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(118, 213, 213));

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 30)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("GESTIÓN DE CIUDADES");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(177, Short.MAX_VALUE)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 446, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(97, 97, 97))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(24, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 720, 70));

        jPanel3.setBackground(new java.awt.Color(204, 232, 232));

        jPanel4.setBackground(new java.awt.Color(204, 232, 232));
        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 0, 0));
        jLabel1.setText("Id Ciudad");

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 0, 0));
        jLabel2.setText("Nombre Ciudad");

        jTFIdCiudad.setEditable(false);
        jTFIdCiudad.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jTFNombreCiudad.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jTFIdCiudad, javax.swing.GroupLayout.DEFAULT_SIZE, 182, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(jTFNombreCiudad))
                .addContainerGap(15, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTFIdCiudad, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jTFNombreCiudad, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(58, Short.MAX_VALUE))
        );

        jPanel5.setBackground(new java.awt.Color(204, 232, 232));
        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        jTblCiudades.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jTblCiudades.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Id Ciudad", "Nombre Ciudad"
            }
        ));
        jTblCiudades.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTblCiudadesMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTblCiudades);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 206, Short.MAX_VALUE)
                .addContainerGap())
        );

        jBtnGuardar.setBackground(new java.awt.Color(118, 213, 213));
        jBtnGuardar.setText("Guardar");
        jBtnGuardar.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jBtnGuardar.setBorderPainted(false);
        jBtnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnGuardarActionPerformed(evt);
            }
        });

        jBtnEditar.setBackground(new java.awt.Color(118, 213, 213));
        jBtnEditar.setText("Editar");
        jBtnEditar.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jBtnEditar.setBorderPainted(false);
        jBtnEditar.setEnabled(false);
        jBtnEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnEditarActionPerformed(evt);
            }
        });

        jBtnEliminar.setBackground(new java.awt.Color(118, 213, 213));
        jBtnEliminar.setText("Eliminar");
        jBtnEliminar.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jBtnEliminar.setBorderPainted(false);
        jBtnEliminar.setEnabled(false);
        jBtnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnEliminarActionPerformed(evt);
            }
        });

        jBtnLimpiar.setBackground(new java.awt.Color(118, 213, 213));
        jBtnLimpiar.setText("Limpiar");
        jBtnLimpiar.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jBtnLimpiar.setBorderPainted(false);
        jBtnLimpiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnLimpiarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jBtnGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(40, 40, 40)
                        .addComponent(jBtnEditar, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 44, Short.MAX_VALUE)
                        .addComponent(jBtnEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(27, 27, 27)
                        .addComponent(jBtnLimpiar, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18))))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(47, Short.MAX_VALUE))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jBtnEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jBtnEditar, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jBtnGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jBtnLimpiar, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(16, 80, 690, 290));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 720, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 330, Short.MAX_VALUE)
        );

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 70, 720, 330));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jBtnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnGuardarActionPerformed
        try {
            guardar();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al almacenar el registro:" + ex);
        }
    }//GEN-LAST:event_jBtnGuardarActionPerformed

    private void jTblCiudadesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTblCiudadesMouseClicked
        filaSeleccionada();
        habilitarBotones(false, true, true, true);
    }//GEN-LAST:event_jTblCiudadesMouseClicked

    private void jBtnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnEditarActionPerformed
        try {
            editar();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al actualizar el registro:" + ex);
        }
    }//GEN-LAST:event_jBtnEditarActionPerformed

    private void jBtnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnEliminarActionPerformed
        try {
            eliminar();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al eliminar el registro:" + ex);
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
            java.util.logging.Logger.getLogger(jFraCiudad.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(jFraCiudad.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(jFraCiudad.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(jFraCiudad.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new jFraCiudad().setVisible(true);
                } catch (SQLException ex) {
                    Logger.getLogger(jFraCiudad.class.getName()).log(Level.SEVERE, null, ex);
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
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTFIdCiudad;
    private javax.swing.JTextField jTFNombreCiudad;
    private javax.swing.JTable jTblCiudades;
    // End of variables declaration//GEN-END:variables
}
