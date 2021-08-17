/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.curlp.capadatos;

import com.curlp.capalogica.CLCargo;
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
 * @author nohel
 */
public class CDCargo {
   
    // Declarar las variables de conexion y consulta.
    private final Connection cn;
    PreparedStatement ps;
    ResultSet rs;
    Statement st;

    public CDCargo() throws SQLException {
        this.cn = Conexion.conectar();
        
    }
    
    // Método para insertar un cargo en la tabla.
    public void insertarCargo(CLCargo cl)throws SQLException{
        
        String sql = "{CALL insertarCargo(?)}";
        
        try{
            ps = cn.prepareCall(sql);
            ps.setString(1, cl.getCargo());
            ps.execute();
            
        }catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        }
    }
    
    // Método para actualizar el cargo en la tabla.
    
    public void actualizarCargo(CLCargo cl)throws SQLException{   
        String sql = "{CALL actualizarCargo(?,?)}";
        
        try{
            ps = cn.prepareCall(sql);
            ps.setInt(1, cl.getIdCargo());
            ps.setString(2, cl.getCargo());
            ps.execute();
            
        }catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        }
    }
    
    // Método para eliminar el cargo en la tabla.
    public void eliminarCargo(CLCargo cl) throws SQLException {
        String sql = "{CALL eliminarCargo(?)}";

        try {
            ps = cn.prepareCall(sql);
            ps.setInt(1, cl.getIdCargo());
            ps.execute();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        }

    }
    
    // Método para obtener el id autoincrementado del cargo.
    public int autoIncrementarCargoID() throws SQLException {
        
        int idCargo = 0;
        
        String sql = "{CALL autoIncrementarCargoID()}";
        
        try{
            st = cn.createStatement();
            rs = st.executeQuery(sql);
            rs.next();
            
            idCargo = rs.getInt("idCargo");
            
            if(idCargo == 0) {
                idCargo = 1;
            }
            
        } catch (SQLException e){
             JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        }
        
        return idCargo;
    }
    
    // Método para poblar de datos la tabla.
    public List<CLCargo> obtenerListaCargos() throws SQLException {
        
        String sql = "{CALL mostrarCargos()}";
        
        List<CLCargo> miLista = null;
        
        try{
            st = cn.createStatement();
            rs = st.executeQuery(sql);
            
            miLista = new ArrayList<>();
            
            while(rs.next()) {
                CLCargo cl = new CLCargo();
                
                cl.setIdCargo(rs.getInt("idCargo"));
                cl.setCargo(rs.getString("cargo"));
                miLista.add(cl);
            }
        } catch (SQLException e){
             JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        }
        return miLista;
    }
    
    // Método que nos va a permitir llenar el combo de cargo.
    public List<String> cargarComboCargos() throws SQLException {

        String sql = "{CALL mostrarCargos()}";

        List<String> miLista = null;

        try {
            st = cn.createStatement();
            rs = st.executeQuery(sql);

            miLista = new ArrayList<>();
            miLista.add("--Seleccione--");

            while (rs.next()) {
                miLista.add(rs.getString("cargo"));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        }
        return miLista;
    }

}
