/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.curlp.capadatos;

import com.curlp.capalogica.CLCiudad;
import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author Jaime Luna
 */
public class CDCiudad {
    
    private final Connection cn;
    PreparedStatement ps;
    Statement st;
    ResultSet rs;

    public CDCiudad() throws SQLException {
        this.cn = Conexion.conectar();
        
    }
    
    // Método para insertar una ciudad en tabla
    public void insertarCiudad(CLCiudad cl) throws SQLException {
        
        String sql = "{CALL insertarCiudad(?)}";
        
        try {
            ps = cn.prepareCall((sql));
            ps.setString(1, cl.getNombreCiudad());
            ps.execute();
            
        }catch (SQLException e){
            JOptionPane.showMessageDialog(null, "Error: " +  e.getMessage());
        }
    }
    
    // Método para actualizar una ciudad
    public void actualizarCiudad(CLCiudad cl) throws SQLException {
        
        String sql = "{CALL actualizarCiudad(?,?)}";
        
        try {
            ps = cn.prepareCall((sql));
            ps.setInt(1, cl.getIdCiudad());
            ps.setString(2, cl.getNombreCiudad());
            ps.execute();
            
        }catch (SQLException e){
            JOptionPane.showMessageDialog(null, "Error: " +  e.getMessage());
        }
    }
    
    // Método para eliminar Ciudad
    public void eliminarCiudad(CLCiudad cl) throws SQLException {
        
        String sql = "{CALL eliminarCiudad(?)}";
        
        try {
            ps = cn.prepareCall((sql));
            ps.setInt(1, cl.getIdCiudad());
            ps.execute();
            
        }catch (SQLException e){
            JOptionPane.showMessageDialog(null, "Error: " +  e.getMessage());
        }
    }
    
    // Método paara obtener el autoincrementado de la ciudad
    public int autoIncrementarCiudadId() throws SQLException {
        
        int idCiudad = 0;
        
        String sql = "{CALL autoIncrementarCiudadId()}";
        try {
            st = cn.createStatement();
            rs = st.executeQuery(sql);
            rs.next();
            
            idCiudad = rs.getInt("idCiudad");
            
            if (idCiudad == 0) {
                idCiudad = 1;
            }
            
        }catch (SQLException e) {
          JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());  
        }
        
        return idCiudad;
    }
    
    // Método para poblar de datos la tabla
    public List<CLCiudad> obtenerListaCiudades() throws SQLException {
        
        String sql = "{CALL mostrarCiudades()}";
            
        List<CLCiudad> miLista = null;
            
        try{
            st = cn.createStatement();
            rs = st.executeQuery(sql);
            
            miLista = new ArrayList<>();
            
            while (rs.next()) {
                CLCiudad cl = new CLCiudad();
                
                cl.setIdCiudad(rs.getInt("idCiudad"));
                cl.setNombreCiudad(rs.getString("nombreCiudad"));
                miLista.add(cl);
            }
        }catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        }
        return miLista;
    } 
    
    // Método que nos va permitir llenar el combo de ciudad
    public List<String> cargarComboCiudades() throws SQLException {
        
        String sql = "{CALL mostrarCiudades()}";
            
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