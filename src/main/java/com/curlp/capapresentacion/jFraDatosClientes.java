/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.curlp.capapresentacion;

/**
 *
 * @author Oniel_Espinal
 */
public class jFraDatosClientes extends javax.swing.JFrame {

    /**
     * Creates new form jFraDatosClientes
     */
    public jFraDatosClientes() {
        initComponents();
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
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jTFBuscarCliente = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jBtnBuscar = new javax.swing.JButton();
        jBtnEliminar = new javax.swing.JButton();
        jBtnEditar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTblCliente = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jTFDni = new javax.swing.JTextField();
        jTFNombreCliente = new javax.swing.JTextField();
        jTFApellidosCliente = new javax.swing.JTextField();
        jTFDirreccion = new javax.swing.JTextField();
        jTFCelular = new javax.swing.JTextField();
        jCboCiudad = new javax.swing.JComboBox<>();
        jBtnGuardar = new javax.swing.JButton();
        jBtnNuevoCliente = new javax.swing.JButton();
        jRBFemenino = new javax.swing.JRadioButton();
        jRBMasculino = new javax.swing.JRadioButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(118, 213, 213));

        jLabel1.setFont(new java.awt.Font("Lucida Console", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("REGISTRO DE CLIENTES");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(49, 49, 49)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 366, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(595, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 64, Short.MAX_VALUE)
        );

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1010, -1));

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setForeground(new java.awt.Color(255, 255, 255));

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Listado de Clientes", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Lucida Console", 0, 11))); // NOI18N
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jPanel3.add(jTFBuscarCliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(95, 25, 154, -1));

        jLabel9.setFont(new java.awt.Font("Lucida Console", 1, 11)); // NOI18N
        jLabel9.setText("Buscar");
        jPanel3.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(16, 30, 61, -1));

        jBtnBuscar.setBackground(new java.awt.Color(255, 255, 255));
        jBtnBuscar.setFont(new java.awt.Font("Lucida Console", 1, 11)); // NOI18N
        jBtnBuscar.setText("Buscar");
        jPanel3.add(jBtnBuscar, new org.netbeans.lib.awtextra.AbsoluteConstraints(255, 26, -1, -1));

        jBtnEliminar.setBackground(new java.awt.Color(255, 255, 255));
        jBtnEliminar.setFont(new java.awt.Font("Lucida Console", 1, 11)); // NOI18N
        jBtnEliminar.setText("Eliminar");
        jPanel3.add(jBtnEliminar, new org.netbeans.lib.awtextra.AbsoluteConstraints(342, 26, -1, -1));

        jBtnEditar.setBackground(new java.awt.Color(255, 255, 255));
        jBtnEditar.setFont(new java.awt.Font("Lucida Console", 1, 11)); // NOI18N
        jBtnEditar.setText("Editar");
        jPanel3.add(jBtnEditar, new org.netbeans.lib.awtextra.AbsoluteConstraints(439, 26, -1, -1));

        jTblCliente.setFont(new java.awt.Font("Lucida Console", 0, 11)); // NOI18N
        jTblCliente.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "DNI", "Nombre", "Apellidos", "Dirreccion", "Sexo", "Ciudad"
            }
        ));
        jScrollPane1.setViewportView(jTblCliente);

        jPanel3.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 60, 500, 330));

        jPanel2.setBackground(new java.awt.Color(204, 232, 232));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Registro de Clientes"));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setFont(new java.awt.Font("Lucida Console", 1, 12)); // NOI18N
        jLabel2.setText("Nombre del Cliente");
        jPanel2.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(16, 65, 154, 27));

        jLabel3.setFont(new java.awt.Font("Lucida Console", 1, 12)); // NOI18N
        jLabel3.setText("Apellidos del Cliente");
        jPanel2.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(16, 110, -1, 28));

        jLabel4.setFont(new java.awt.Font("Lucida Console", 1, 12)); // NOI18N
        jLabel4.setText("DNI Cliente");
        jPanel2.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(16, 27, 154, 25));

        jLabel5.setFont(new java.awt.Font("Lucida Console", 1, 12)); // NOI18N
        jLabel5.setText("Dirreccion");
        jPanel2.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(16, 149, 154, 28));

        jLabel6.setFont(new java.awt.Font("Lucida Console", 1, 12)); // NOI18N
        jLabel6.setText("Celular");
        jPanel2.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(16, 195, 154, 29));

        jLabel7.setFont(new java.awt.Font("Lucida Console", 1, 12)); // NOI18N
        jLabel7.setText("Sexo");
        jPanel2.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(16, 251, 154, 27));

        jLabel8.setFont(new java.awt.Font("Lucida Console", 1, 12)); // NOI18N
        jLabel8.setText("Ciudad");
        jPanel2.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(16, 306, 154, 28));
        jPanel2.add(jTFDni, new org.netbeans.lib.awtextra.AbsoluteConstraints(253, 32, 150, -1));
        jPanel2.add(jTFNombreCliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(253, 68, 150, -1));
        jPanel2.add(jTFApellidosCliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(253, 114, 150, -1));
        jPanel2.add(jTFDirreccion, new org.netbeans.lib.awtextra.AbsoluteConstraints(253, 153, 150, -1));

        jTFCelular.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTFCelularActionPerformed(evt);
            }
        });
        jPanel2.add(jTFCelular, new org.netbeans.lib.awtextra.AbsoluteConstraints(253, 199, 150, -1));

        jPanel2.add(jCboCiudad, new org.netbeans.lib.awtextra.AbsoluteConstraints(273, 309, 130, -1));

        jBtnGuardar.setFont(new java.awt.Font("Lucida Console", 1, 12)); // NOI18N
        jBtnGuardar.setText("Guardar");
        jPanel2.add(jBtnGuardar, new org.netbeans.lib.awtextra.AbsoluteConstraints(232, 371, 120, -1));

        jBtnNuevoCliente.setFont(new java.awt.Font("Lucida Console", 1, 12)); // NOI18N
        jBtnNuevoCliente.setText("Nuevo");
        jPanel2.add(jBtnNuevoCliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(26, 371, 120, -1));

        jRBFemenino.setBackground(new java.awt.Color(204, 232, 232));
        jBGSexo.add(jRBFemenino);
        jRBFemenino.setText("Femenino");
        jPanel2.add(jRBFemenino, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 253, 91, -1));

        jRBMasculino.setBackground(new java.awt.Color(204, 232, 232));
        jBGSexo.add(jRBMasculino);
        jRBMasculino.setText("Masculino");
        jPanel2.add(jRBMasculino, new org.netbeans.lib.awtextra.AbsoluteConstraints(313, 253, -1, -1));

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addGap(4, 4, 4)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 454, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 526, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 407, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 407, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 60, 1000, 420));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTFCelularActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTFCelularActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTFCelularActionPerformed

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
                new jFraDatosClientes().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup jBGSexo;
    private javax.swing.JButton jBtnBuscar;
    private javax.swing.JButton jBtnEditar;
    private javax.swing.JButton jBtnEliminar;
    private javax.swing.JButton jBtnGuardar;
    private javax.swing.JButton jBtnNuevoCliente;
    private javax.swing.JComboBox<String> jCboCiudad;
    private javax.swing.JLabel jLabel1;
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
    private javax.swing.JRadioButton jRBFemenino;
    private javax.swing.JRadioButton jRBMasculino;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTFApellidosCliente;
    private javax.swing.JTextField jTFBuscarCliente;
    private javax.swing.JTextField jTFCelular;
    private javax.swing.JTextField jTFDirreccion;
    private javax.swing.JTextField jTFDni;
    private javax.swing.JTextField jTFNombreCliente;
    private javax.swing.JTable jTblCliente;
    // End of variables declaration//GEN-END:variables
}
