/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.curlp.capapresentacion;


import com.curlp.capadatos.CDCargo;
import com.curlp.capalogica.CLCargo;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author nohel
 */
public class jFraCargo extends javax.swing.JFrame {

    /**
     * Creates new form jFraCargo
     */
    public jFraCargo() throws SQLException {
        initComponents();
        poblarTablaCargos();
        encontrarCorrelativo();
        this.jTFCargo.requestFocus();
        this.setLocationRelativeTo(null);
    }

    //Metodo para limpiar los datos de la tabla
    private void limpiarTabla() {
        DefaultTableModel dtm = (DefaultTableModel) this.jTblCargos.getModel();

        while (dtm.getRowCount() > 0) {
            dtm.removeRow(0);
        }
    }

    //Metodos para poblar los datos de la tabla
    private void poblarTablaCargos() throws SQLException {

        limpiarTabla();

        CDCargo cdc = new CDCargo();
        List<CLCargo> miLista = cdc.obtenerListaCargos();
        DefaultTableModel temp = (DefaultTableModel) this.jTblCargos.getModel();

        miLista.stream().map((CLCargo cl) -> {
            Object[] fila = new Object[2];
            fila[0] = cl.getIdCargo();
            fila[1] = cl.getCargo();
            return fila;

        }).forEachOrdered(temp::addRow);
    }

    //Metodo para encontrar el correlativo de id cargo.
    private void encontrarCorrelativo() throws SQLException {
        CDCargo cdc = new CDCargo();
        CLCargo cl = new CLCargo();

        cl.setIdCargo(cdc.autoIncrementarCargoID());
        this.jTFIdCargo.setText(String.valueOf(cl.getIdCargo()));
    }

    //Metodo para habilitar y dehabilitar botones
    private void habilitarBotones(boolean guardar, boolean editar, boolean eliminar,
            boolean limpiar) {
        this.jBtnGuardar.setEnabled(guardar);
        this.jBtnEditar.setEnabled(editar);
        this.jBtnEliminar.setEnabled(eliminar);
        this.jBtnLimpiar.setEnabled(limpiar);
    }

    //Metodo para limpiar las TextField
    private void limpiarTextField() {
        this.jTFIdCargo.setText("");
        this.jTFCargo.setText("");
        this.jTFCargo.requestFocus();

    }

    //Metodo para validar la TextField
    private boolean validarTextField() {
        boolean estado;

        estado = !this.jTFCargo.getText().equals("");
        return estado;
    }

    //Metodo de insertar un Cargo en la tabla
    private void insertarCargo() {
        if (!validarTextField()) {
            JOptionPane.showMessageDialog(null, "Tiene que ingresar el cargo", "Sistema Paquetería",
                    JOptionPane.INFORMATION_MESSAGE);
            this.jTFCargo.requestFocus();
        } else {
            try {
                CDCargo cdc = new CDCargo();
                CLCargo cl = new CLCargo();
                cl.setCargo(this.jTFCargo.getText().trim());
                cdc.insertarCargo(cl);

                JOptionPane.showMessageDialog(null, "Registro almacenado satisfactoriamente", "Sistema Paquetería",
                        JOptionPane.INFORMATION_MESSAGE);

            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Error al almacenar el registro" + ex);
            this.jTFCargo.requestFocus();
            }
        }
    }
    
    //Metodo para llamar el metodo de insertar cargo.
    private void guardar() throws SQLException {
        insertarCargo();
        poblarTablaCargos();
        habilitarBotones(true, false, false, true);
        limpiarTextField();
        encontrarCorrelativo();
    }

    //Metodo para actualizar un registro de la tabla Cargo
    private void actualizarCargo() {
        if (!validarTextField()) {
            JOptionPane.showMessageDialog(null, "Tiene que ingresar el cargo", "Sistema Paquetería",
                    JOptionPane.INFORMATION_MESSAGE);
            this.jTFCargo.requestFocus();
        } else {
            try {
                CDCargo cdc = new CDCargo();
                CLCargo cl = new CLCargo();
                cl.setIdCargo(Integer.parseInt(this.jTFIdCargo.getText().trim()));
                cl.setCargo(this.jTFCargo.getText().trim());
                cdc.actualizarCargo(cl);

                JOptionPane.showMessageDialog(null, "Registro actualizado satisfactoriamente", "Sistema Paquetería",
                        JOptionPane.INFORMATION_MESSAGE);

            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Error al actualizar el registro" + ex);
                this.jTFCargo.requestFocus();
            }
        }
    }

    //Metodo para tomar datos de la fila seleccionada y asi poder modificarlos
    private void filaSeleccionada() {
        if (this.jTblCargos.getSelectedRow() != -1) {
            this.jTFIdCargo.setText(String.valueOf(this.jTblCargos.getValueAt(this.jTblCargos.getSelectedRow(), 0)));
            this.jTFCargo.setText(String.valueOf(this.jTblCargos.getValueAt(this.jTblCargos.getSelectedRow(), 1)));
        }
    }

    //Metodo para llamar el metodo de actualizar registro de la tabla.
    private void editar() throws SQLException {
        actualizarCargo();
        poblarTablaCargos();
        habilitarBotones(true, false, false, true);
        limpiarTextField();
        encontrarCorrelativo();
    }

    //Metodo para eliminar.
    private void eliminarCargo() {
        try {
            CDCargo cdc = new CDCargo();
            CLCargo cl = new CLCargo();
            cl.setIdCargo(Integer.parseInt(this.jTFIdCargo.getText().trim()));
            cdc.eliminarCargo(cl);

            JOptionPane.showMessageDialog(null, "Registro eliminado satisfactoriamente", "Sistema Paquetería",
                    JOptionPane.INFORMATION_MESSAGE);

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al eliminar el registro" + ex);
            this.jTFCargo.requestFocus();
        }
    }
    //Metodo para invocar el metodo eliminar
    private void eliminar() throws SQLException {
        int resp = JOptionPane.showConfirmDialog(null, "¿Está seguro de que desea eliminar el registro?", "Sistema Paquetería",
                JOptionPane.YES_NO_OPTION);

        if (resp == JOptionPane.YES_OPTION) {
            try {
                eliminarCargo();
                poblarTablaCargos();
                habilitarBotones(true, false, false, true);
                limpiarTextField();
                encontrarCorrelativo();

            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Error" + ex);
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
        jTFIdCargo = new javax.swing.JTextField();
        jTFCargo = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTblCargos = new javax.swing.JTable();
        jBtnGuardar = new javax.swing.JButton();
        jBtnEditar = new javax.swing.JButton();
        jBtnEliminar = new javax.swing.JButton();
        jBtnLimpiar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(118, 213, 213));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 30)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("GESTIÓN DE CARGOS");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(207, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(199, 199, 199))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jLabel1)
                .addContainerGap(17, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 730, 70));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jPanel3.setBackground(new java.awt.Color(204, 232, 232));

        jPanel4.setBackground(new java.awt.Color(204, 232, 232));
        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED), "Cargo del Empleado", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", 1, 12), new java.awt.Color(153, 153, 153))); // NOI18N

        jLabel2.setForeground(new java.awt.Color(0, 0, 0));
        jLabel2.setText("Id Cargo");

        jTFIdCargo.setEditable(false);

        jLabel3.setForeground(new java.awt.Color(0, 0, 0));
        jLabel3.setText("Cargo");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTFCargo)
                    .addComponent(jTFIdCargo)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 132, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTFIdCargo, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jTFCargo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(41, Short.MAX_VALUE))
        );

        jTblCargos.setBackground(new java.awt.Color(255, 255, 255));
        jTblCargos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Id Cargo", "Cargo"
            }
        ));
        jTblCargos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTblCargosMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTblCargos);

        jBtnGuardar.setBackground(new java.awt.Color(118, 213, 213));
        jBtnGuardar.setText("Guardar");
        jBtnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnGuardarActionPerformed(evt);
            }
        });

        jBtnEditar.setBackground(new java.awt.Color(118, 213, 213));
        jBtnEditar.setText("Editar");
        jBtnEditar.setEnabled(false);
        jBtnEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnEditarActionPerformed(evt);
            }
        });

        jBtnEliminar.setBackground(new java.awt.Color(118, 213, 213));
        jBtnEliminar.setText("Eliminar");
        jBtnEliminar.setEnabled(false);
        jBtnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnEliminarActionPerformed(evt);
            }
        });

        jBtnLimpiar.setBackground(new java.awt.Color(118, 213, 213));
        jBtnLimpiar.setText("Limpiar");
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
                .addContainerGap()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addComponent(jBtnGuardar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jBtnEditar, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(36, 36, 36)
                        .addComponent(jBtnEliminar)
                        .addGap(36, 36, 36)
                        .addComponent(jBtnLimpiar))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 433, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(38, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jBtnEliminar)
                    .addComponent(jBtnEditar)
                    .addComponent(jBtnLimpiar)
                    .addComponent(jBtnGuardar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 235, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 20, Short.MAX_VALUE))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(18, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(19, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 70, 730, 340));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jBtnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnGuardarActionPerformed
        try {
            guardar();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al almacenar el registro" + ex);
        }
    }//GEN-LAST:event_jBtnGuardarActionPerformed

    private void jTblCargosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTblCargosMouseClicked
       filaSeleccionada();
        habilitarBotones(false, true, true, true);
    }//GEN-LAST:event_jTblCargosMouseClicked

    private void jBtnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnEditarActionPerformed
        try {
            editar();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al actualizar el registro" + ex);
        }
    }//GEN-LAST:event_jBtnEditarActionPerformed

    private void jBtnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnEliminarActionPerformed
        try {
            eliminar();
        } catch (SQLException ex) {
             JOptionPane.showMessageDialog(null, "Error al eliminar el registro" + ex);
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
            java.util.logging.Logger.getLogger(jFraCargo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(jFraCargo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(jFraCargo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(jFraCargo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new jFraCargo().setVisible(true);
                } catch (SQLException ex) {
                    Logger.getLogger(jFraCargo.class.getName()).log(Level.SEVERE, null, ex);
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
    private javax.swing.JTextField jTFCargo;
    private javax.swing.JTextField jTFIdCargo;
    private javax.swing.JTable jTblCargos;
    // End of variables declaration//GEN-END:variables
}
