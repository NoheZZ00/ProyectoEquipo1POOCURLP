/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.curlp.capadatos;

import com.curlp.capalogica.CLFormaPago;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author Owner User
 */
public class CDFormaPago {
    
    // Declarar las variables de conexión y de consulta.
    private final Connection cn;
    PreparedStatement ps;
    ResultSet rs;
    Statement st;

    public CDFormaPago() throws SQLException {
        this.cn = Conexion.conectar();
    }
    
    //Método para insertar forma pago
    public void insertarFormaPago(CLFormaPago cl) throws SQLException {
        
        String sql = "{CALL insertarFormaPago(?)}";
        
        try {
            ps = cn.prepareCall(sql);
            ps.setString(1, cl.getFormaPago());
            ps.execute();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error" + e.getMessage());
        }
    }
    
    //Método para actualizar la tabla forma pago.
    public void actualizarformaPago(CLFormaPago cl) throws SQLException {
        
        String sql = "{CALL actualizarformaPago(?,?)}";
        
        try {
            ps = cn.prepareCall(sql); 
            ps.setInt(1, cl.getIdFormaPago());
            ps.setString(2, cl.getFormaPago());
            ps.execute();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error" + e.getMessage());
        }
    }
    
    //Método para eliminar una forma de pago.
    public void eliminarFormaPago(CLFormaPago cl) throws SQLException {
        
        String sql = "{CALL eliminarFormaPago(?)}";
        
        try {
            ps = cn.prepareCall(sql); 
            ps.setInt(1, cl.getIdFormaPago());
            ps.execute();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error" + e.getMessage());
        }
    }
    
    //Metodo paara obtener el autoincrementado de la ciudad
    public int autoIncrementarFormaPagoId() throws SQLException {
        
        int idFormaPago = 0;
        
        String sql = "{CALL autoIncrementarFormaPagoId()}";
        try {
            st = cn.createStatement();
            rs = st.executeQuery(sql);
            rs.next();
            
            idFormaPago = rs.getInt("idFormaPago");
            
            if (idFormaPago == 0) {
                idFormaPago = 1;
            }
            
        }catch (SQLException e) {
          JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());  
        }
        
        return idFormaPago;
    }
    
    //Metodo para poblar de datos la tabla
    public List<CLFormaPago> obtenerListaFormaPago() throws SQLException {
        
        String sql = "{CALL mostrarFormasPago()}";
            
        List<CLFormaPago> miLista = null;
            
        try{
            st = cn.createStatement();
            rs = st.executeQuery(sql);
            
            miLista = new ArrayList<>();
            
            while (rs.next()) {
                CLFormaPago cl = new CLFormaPago();
                
                cl.setIdFormaPago(rs.getInt("idFormaPago"));
                cl.setFormaPago(rs.getString("formaPago"));
                miLista.add(cl);
            }
        }catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        }
        return miLista;
    } 
    
    //Metodo que nos va permitir llenar el combo de forma de pago
    public List<String> cargarComboFormaPago() throws SQLException {
        
        String sql = "{CALL mostrarFormasPago()}";
            
        List<String> miLista = null;
            
        try {
            st = cn.createStatement();
            rs = st.executeQuery(sql);
            
            miLista = new ArrayList<>();
            miLista.add("---Seleccione---");
            
            while (rs.next()) {
                miLista.add(rs.getString("nombreCiudad"));
            }
        }catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        }
        return miLista;
}
}
