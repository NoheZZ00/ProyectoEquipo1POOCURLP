
package com.curlp.capapresentacion;

import com.curlp.capadatos.CDCargo;
import com.curlp.capadatos.CDCiudad;
import com.curlp.capadatos.CDEmpleado;
import com.curlp.capalogica.CLEmpleado;
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
 * @author Walter Maradiaga
 */
public class jFraEmpleado extends javax.swing.JFrame {

    public jFraEmpleado() throws SQLException {
        initComponents();
        //poblarComboCargos();
        //poblarComboCiudades();
        poblarTablaEmpleados();
        this.jTFDNIEmp.requestFocus();
        this.setLocationRelativeTo(null);
    }
    
    //Método para limpiar la tabla
    private void limpiarTabla() {
        DefaultTableModel dtm = (DefaultTableModel) this.jTblListaEmpleados.getModel();
        
        while(dtm.getRowCount() > 0) {
            dtm.removeRow(0);
        }
    }
    
    //Método para limpiar las texfield
    private void limpiarControlesRegistro() {
        this.jTFDNIEmp.setText("");
        this.jTFNombreEmp.setText("");
        this.jTFApellidosEmp.setText("");
        this.jTFDireccion.setText("");
        this.jTFCelular.setText("");
        this.jCBCargo.setSelectedIndex(0);
        this.jCBCiudad.setSelectedIndex(0);
        this.jTFDNIEmp.requestFocus();
    }
    
    //Método para poblar de datos el combo cargos
    private void poblarComboCargos() throws SQLException {
        CDCargo cdc = new CDCargo();
        
        String[] cargos = new String[cdc.cargarComboCargos().size()];
        
        cargos = cdc.cargarComboCargos().toArray(cargos);
        
        DefaultComboBoxModel modeloCargos = new DefaultComboBoxModel(cargos);
        this.jCBCargo.setModel(modeloCargos);   
    }
    
    //Método para poblar de datos el combo ciudades
    private void poblarComboCiudades() throws SQLException {
        CDCiudad cdc = new CDCiudad();
        
        String[] ciudades = new String[cdc.cargarComboCiudades().size()];
        
        ciudades = cdc.cargarComboCiudades().toArray(ciudades);
        
        DefaultComboBoxModel modeloCiudades = new DefaultComboBoxModel(ciudades);
        this.jCBCiudad.setModel(modeloCiudades);   
    }
    
    //Método para poblar de datos la tabla empleados
    private void poblarTablaEmpleados() throws SQLException {
        limpiarTabla();
        
        CDEmpleado cde = new CDEmpleado();
        
        List<CLEmpleado> miLista = cde.obtenerListaEmpleados();
        
        DefaultTableModel dtm = (DefaultTableModel) this.jTblListaEmpleados.getModel();
        
        miLista.stream().map((cle) -> {
            Object[] fila = new Object[7];
            fila[0] = cle.getDNIEmp();
            fila[1] = cle.getNombreEmp();
            fila[2] = cle.getApellidosEmp();
            fila[3] = cle.getDireccion();
            fila[4] = cle.getCelular();
            fila[5] = cle.getCargo();
            fila[6] = cle.getCiudad();
                    
            return fila;
        }).forEachOrdered(dtm::addRow);
    }
    
    //Método para poblar la tabla empleados por nombre
    private void poblarTablaEmpleadosPorNombre(String nombreEmpleado) throws SQLException {
        limpiarTabla();
        
        CDEmpleado cde = new CDEmpleado();
        
        List<CLEmpleado> miLista = cde.obtenerListaEmpleadoPorNombre(nombreEmpleado);
        
        DefaultTableModel dtm = (DefaultTableModel) this.jTblListaEmpleados.getModel();
        
        miLista.stream().map((cle) -> {
            Object[] fila = new Object[7];
            fila[0] = cle.getDNIEmp();
            fila[1] = cle.getNombreEmp();
            fila[2] = cle.getApellidosEmp();
            fila[3] = cle.getDireccion();
            fila[4] = cle.getCelular();
            fila[5] = cle.getCargo();
            fila[6] = cle.getCiudad();
                    
            return fila;
            
        }).forEachOrdered(dtm::addRow);
    }
    
    //Método para seleccionar una fila  de la tabla y pasarla a los textfield
    private void filaSeleccionada() throws ParseException {
        if(this.jTblListaEmpleados.getSelectedRow() != -1) {
            this.jTFDNIEmp.setText(String.valueOf(this.jTblListaEmpleados.getValueAt(this.jTblListaEmpleados.getSelectedRow(),0)));
            this.jTFNombreEmp.setText(String.valueOf(this.jTblListaEmpleados.getValueAt(this.jTblListaEmpleados.getSelectedRow(),1)));
            this.jTFApellidosEmp.setText(String.valueOf(this.jTblListaEmpleados.getValueAt(this.jTblListaEmpleados.getSelectedRow(),2)));
            this.jTFDireccion.setText(String.valueOf(this.jTblListaEmpleados.getValueAt(this.jTblListaEmpleados.getSelectedRow(),3)));
            this.jTFCelular.setText(String.valueOf(this.jTblListaEmpleados.getValueAt(this.jTblListaEmpleados.getSelectedRow(),4)));
            this.jCBCargo.setSelectedItem(String.valueOf(this.jTblListaEmpleados.getValueAt(this.jTblListaEmpleados.getSelectedRow(),5)));
            this.jCBCiudad.setSelectedItem(String.valueOf(this.jTblListaEmpleados.getValueAt(this.jTblListaEmpleados.getSelectedRow(),6)));
        }
    }
    
    //Método para habilitar y deshabilitar botones
    private void habilitarBotones(boolean guardar, boolean editar, boolean eliminar, boolean limpiar) {
        this.jBtnGuardar.setEnabled(guardar);
        this.jBtnEditar.setEnabled(editar);
        this.jBtnEliminar.setEnabled(eliminar);
        this.jBtnCancelar.setEnabled(limpiar);
    }
    
    //Método para validar textField
    private boolean validarTextField() {
        boolean estado;
        
        estado = !(this.jTFDNIEmp.getText().isEmpty() || this.jTFNombreEmp.getText().isEmpty()
                || this.jTFApellidosEmp.getText().isEmpty() || this.jTFDireccion.getText().isEmpty()
                || this.jTFCelular.getText().isEmpty() || this.jCBCargo.getSelectedIndex() == -1
                || this.jCBCiudad.getSelectedIndex() == -1);
        
        return estado;
    }
    
    //Método para insertar un empleado
    private void insertarEmpleado() throws SQLException {
        if(validarTextField()) {
            try {
                CDEmpleado cde = new CDEmpleado();

                CLEmpleado cle = new CLEmpleado();
            
                cle.setDNIEmp(this.jTFDNIEmp.getText().trim());
                cle.setNombreEmp(this.jTFNombreEmp.getText().trim());
                cle.setApellidosEmp(this.jTFApellidosEmp.getText().trim());
                cle.setDireccion(this.jTFDireccion.getText().trim());
                cle.setCelular(this.jTFCelular.getText().trim());
                cle.setCargo(this.jCBCargo.getSelectedItem().toString());
                cle.setCiudad(this.jCBCiudad.getSelectedItem().toString());

                cde.insertarEmpleado(cle);

                JOptionPane.showMessageDialog(null, "Registro almacenado satisfactoriamente...", "Control empleados",
                    JOptionPane.INFORMATION_MESSAGE);
            
            }catch(SQLException ex) {
                JOptionPane.showMessageDialog(null,"Error al almacenar el empleado" + ex);
            }
            
        } else {
            JOptionPane.showMessageDialog(null, "Verifica que todos los datos requeridos tengan algun valor", "Control empleados",
                    JOptionPane.INFORMATION_MESSAGE);
            this.jTFDNIEmp.requestFocus();
        } 
    }
    
    //Método para que ejecute el llamado a la fuhncion insertar empleado
    private void guardar() throws SQLException {
        limpiarTabla();
        insertarEmpleado();
        poblarTablaEmpleados();
        habilitarBotones(true,false,false,true);
        limpiarControlesRegistro();
    }
    
    //Método para actualiar el registro de un empleado
    private void actualizarEmpleado() throws SQLException {
        if(validarTextField()) {
            try {
                CDEmpleado cde = new CDEmpleado();
                CLEmpleado cle = new CLEmpleado();

                cle.setDNIEmp(this.jTFDNIEmp.getText().trim());
                cle.setNombreEmp(this.jTFNombreEmp.getText().trim());
                cle.setApellidosEmp(this.jTFApellidosEmp.getText().trim());
                cle.setDireccion(this.jTFDireccion.getText().trim());
                cle.setCelular(this.jTFCelular.getText().trim());
                cle.setCargo(this.jCBCargo.getSelectedItem().toString());
                cle.setCiudad(this.jCBCiudad.getSelectedItem().toString());

                cde.actualizarEmpleado(cle);
                
                JOptionPane.showMessageDialog(null, "Registro actualizado satisfactoriamente...", "Control empleados",
                    JOptionPane.INFORMATION_MESSAGE);
            
            } catch(SQLException ex) {
                JOptionPane.showMessageDialog(null,"Error al actualizar empleado " + ex.getMessage());
            }
        } else {
            JOptionPane.showMessageDialog(null, "Verifica que todos los datos requeridos tengan algun valor", "Control empleados",
                    JOptionPane.INFORMATION_MESSAGE);
            this.jTFDNIEmp.requestFocus();
        }  
    }
    
    //Método para llamar al metodo de actualizar empleado
    private void actualizar() throws SQLException {
        actualizarEmpleado();
        poblarTablaEmpleados();
        habilitarBotones(true,false,false,true);
        limpiarControlesRegistro();
    }
    
    //Método para eliminar un empleado
    private void eliminarEmpleado() {
        
        try {
            CDEmpleado cde = new CDEmpleado();
            CLEmpleado cle = new CLEmpleado();
            
            cle.setDNIEmp(this.jTFDNIEmp.getText().trim());
            
            cde.eliminarEmpleado(cle);
            
            JOptionPane.showMessageDialog(null, "Registro eliminado satisfactoriamente...", "Control empleados",
                    JOptionPane.INFORMATION_MESSAGE);
            
        } catch(SQLException ex) {
                JOptionPane.showMessageDialog(null,"Error al eliminar empleado" + ex.getMessage());
        }
    }
    
    //Método para invocar al metodo de eliminar empleado
    private void eliminar() throws SQLException {
        int resp = JOptionPane.showConfirmDialog(null, "Registro eliminado satisfactoriamente...", "Control empleados",
                    JOptionPane.YES_NO_OPTION);
        
        if(resp == JOptionPane.YES_OPTION) {
            try {
                eliminarEmpleado();
                //limpiarTabla();
                poblarTablaEmpleados();
                limpiarControlesRegistro();
                habilitarBotones(true,false,false,true);
                
            } catch(SQLException ex) {
                JOptionPane.showMessageDialog(null,"Error al eliminar empleado" + ex.getMessage());
            }
        }else {
            limpiarControlesRegistro();
            this.jTFDNIEmp.requestFocus();
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

        jProgressBar1 = new javax.swing.JProgressBar();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jTFDNIEmp = new javax.swing.JTextField();
        jTFNombreEmp = new javax.swing.JTextField();
        jTFApellidosEmp = new javax.swing.JTextField();
        jTFDireccion = new javax.swing.JTextField();
        jTFCelular = new javax.swing.JTextField();
        jCBCargo = new javax.swing.JComboBox<>();
        jCBCiudad = new javax.swing.JComboBox<>();
        jBtnGuardar = new javax.swing.JButton();
        jBtnCancelar = new javax.swing.JButton();
        jBtnEditar = new javax.swing.JButton();
        jBtnEliminar = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jTFBusqueda = new javax.swing.JTextField();
        jBtnMostrarTodos = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTblListaEmpleados = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(118, 213, 213));
        jPanel1.setBorder(new javax.swing.border.MatteBorder(null));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("GESTIÓN DE EMPLEADOS");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(213, 213, 213)
                .addComponent(jLabel1)
                .addContainerGap(288, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addComponent(jLabel1)
                .addContainerGap(34, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 810, 100));

        jTabbedPane1.setBackground(new java.awt.Color(204, 255, 255));

        jPanel2.setBackground(new java.awt.Color(204, 204, 204));
        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel2.setText("Número De Identidad: ");

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel3.setText("Nombres: ");

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel4.setText("Apellidos:");

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel5.setText("Dirección:");

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel6.setText("Celular");

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel7.setText("Cargo");

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel8.setText("Ciudad");

        jBtnGuardar.setIcon(new javax.swing.ImageIcon("C:\\Users\\Walter Maradiaga\\Documents\\NetBeansProjects\\ProyectoEquipo1POOCURLP\\src\\main\\java\\com\\curlp\\imagenes\\Guardar.png")); // NOI18N
        jBtnGuardar.setText("Guardar");
        jBtnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnGuardarActionPerformed(evt);
            }
        });

        jBtnCancelar.setIcon(new javax.swing.ImageIcon("C:\\Users\\Walter Maradiaga\\Documents\\NetBeansProjects\\ProyectoEquipo1POOCURLP\\src\\main\\java\\com\\curlp\\imagenes\\Cancelar.png")); // NOI18N
        jBtnCancelar.setText("Cancelar");
        jBtnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnCancelarActionPerformed(evt);
            }
        });

        jBtnEditar.setIcon(new javax.swing.ImageIcon("C:\\Users\\Walter Maradiaga\\Documents\\NetBeansProjects\\ProyectoEquipo1POOCURLP\\src\\main\\java\\com\\curlp\\imagenes\\Editar.png")); // NOI18N
        jBtnEditar.setText("Editar");
        jBtnEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnEditarActionPerformed(evt);
            }
        });

        jBtnEliminar.setIcon(new javax.swing.ImageIcon("C:\\Users\\Walter Maradiaga\\Documents\\NetBeansProjects\\ProyectoEquipo1POOCURLP\\src\\main\\java\\com\\curlp\\imagenes\\Eliminar.png")); // NOI18N
        jBtnEliminar.setText("Eliminar");
        jBtnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnEliminarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel8)
                            .addComponent(jLabel7)
                            .addComponent(jLabel6)
                            .addComponent(jLabel5)
                            .addComponent(jLabel4)
                            .addComponent(jLabel3)
                            .addComponent(jLabel2))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jTFDNIEmp, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTFDireccion, javax.swing.GroupLayout.DEFAULT_SIZE, 446, Short.MAX_VALUE)
                            .addComponent(jTFCelular, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jCBCargo, 0, 292, Short.MAX_VALUE)
                            .addComponent(jCBCiudad, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jTFNombreEmp)
                            .addComponent(jTFApellidosEmp))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jBtnGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(61, 61, 61)
                        .addComponent(jBtnEditar, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 66, Short.MAX_VALUE)
                        .addComponent(jBtnEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(48, 48, 48)
                        .addComponent(jBtnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(141, 141, 141))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(41, 41, 41)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(jLabel2)
                                            .addComponent(jTFDNIEmp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(18, 18, 18)
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(jLabel3)
                                            .addComponent(jTFNombreEmp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(18, 18, 18)
                                        .addComponent(jLabel4))
                                    .addComponent(jTFApellidosEmp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addComponent(jLabel5))
                            .addComponent(jTFDireccion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(jTFCelular, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(jLabel7))
                    .addComponent(jCBCargo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(jCBCiudad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 24, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jBtnGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jBtnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jBtnEditar, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jBtnEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(29, 29, 29))
        );

        jTabbedPane1.addTab("DATOS DEL EMPLEADO", jPanel2);

        jPanel3.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel9.setText("Buscar por nombres o apellidos:");

        jBtnMostrarTodos.setText("Mostrar todos");
        jBtnMostrarTodos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnMostrarTodosActionPerformed(evt);
            }
        });

        jTblListaEmpleados.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Número de identidad", "Nombres", "Apellidos", "Dirección", "Celular", "Cargo", "Ciudad"
            }
        ));
        jTblListaEmpleados.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTblListaEmpleadosMouseClicked(evt);
            }
        });
        jTblListaEmpleados.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTblListaEmpleadosKeyReleased(evt);
            }
        });
        jScrollPane1.setViewportView(jTblListaEmpleados);
        if (jTblListaEmpleados.getColumnModel().getColumnCount() > 0) {
            jTblListaEmpleados.getColumnModel().getColumn(0).setPreferredWidth(90);
        }

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel9)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jTFBusqueda, javax.swing.GroupLayout.PREFERRED_SIZE, 433, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(37, 37, 37)
                                .addComponent(jBtnMostrarTodos)))
                        .addGap(0, 210, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTFBusqueda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jBtnMostrarTodos))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 298, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("LISTA DE EMPLEADOS", jPanel3);

        getContentPane().add(jTabbedPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 100, 810, 420));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jBtnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnGuardarActionPerformed
        try {
            guardar();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,"Error: " + ex.getMessage());        }
    }//GEN-LAST:event_jBtnGuardarActionPerformed

    private void jTblListaEmpleadosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTblListaEmpleadosMouseClicked
        try {
            filaSeleccionada();
            habilitarBotones(false,true,true,true);
        } catch (ParseException ex) {
            JOptionPane.showMessageDialog(null,"Error: " + ex.getMessage());
        }
    }//GEN-LAST:event_jTblListaEmpleadosMouseClicked

    private void jBtnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnEditarActionPerformed
        try {
            actualizar();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,"Error: " + ex.getMessage());
        }
    }//GEN-LAST:event_jBtnEditarActionPerformed

    private void jBtnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnEliminarActionPerformed
        try {
            eliminar();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,"Error: " + ex.getMessage());
        }
    }//GEN-LAST:event_jBtnEliminarActionPerformed

    private void jBtnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnCancelarActionPerformed
        limpiarControlesRegistro();
    }//GEN-LAST:event_jBtnCancelarActionPerformed

    private void jTblListaEmpleadosKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTblListaEmpleadosKeyReleased
        String busqueda;
        
        busqueda = this.jTFBusqueda.getText();
        
        try {
            
            poblarTablaEmpleadosPorNombre(busqueda);
            
        } catch(SQLException ex) {
            JOptionPane.showMessageDialog(null,"Error: " + ex.getMessage());
        }
    }//GEN-LAST:event_jTblListaEmpleadosKeyReleased

    private void jBtnMostrarTodosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnMostrarTodosActionPerformed
        try {
            poblarTablaEmpleados();
            this.jTFBusqueda.setText(" ");
        } catch(SQLException ex) {
            JOptionPane.showMessageDialog(null,"Error: " + ex.getMessage());
        }
    }//GEN-LAST:event_jBtnMostrarTodosActionPerformed

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
            java.util.logging.Logger.getLogger(jFraEmpleado.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(jFraEmpleado.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(jFraEmpleado.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(jFraEmpleado.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new jFraEmpleado().setVisible(true);
                } catch (SQLException ex) {
                    Logger.getLogger(jFraEmpleado.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBtnCancelar;
    private javax.swing.JButton jBtnEditar;
    private javax.swing.JButton jBtnEliminar;
    private javax.swing.JButton jBtnGuardar;
    private javax.swing.JButton jBtnMostrarTodos;
    private javax.swing.JComboBox<String> jCBCargo;
    private javax.swing.JComboBox<String> jCBCiudad;
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
    private javax.swing.JProgressBar jProgressBar1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTFApellidosEmp;
    private javax.swing.JTextField jTFBusqueda;
    private javax.swing.JTextField jTFCelular;
    private javax.swing.JTextField jTFDNIEmp;
    private javax.swing.JTextField jTFDireccion;
    private javax.swing.JTextField jTFNombreEmp;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTblListaEmpleados;
    // End of variables declaration//GEN-END:variables
}
