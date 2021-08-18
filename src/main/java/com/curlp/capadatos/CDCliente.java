/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.curlp.capadatos;

import com.curlp.capalogica.CLCliente;
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
 * @author Oniel_Espinal
 */
public class CDCliente {
    private final Connection cn;
    PreparedStatement ps;
    ResultSet rs;
    Statement st;
    
    public CDCliente() throws SQLException{
        this.cn=Conexion.conectar();
    }
    public void insertarCliente(CLCliente clc) throws SQLException{
        String sql="{CALL insertarCliente(?,?,?,?,?,?,?)}";
        try{
            ps = cn.prepareCall(sql);
            ps.setString(1, clc.getDniCliente());
            ps.setString(2, clc.getNombre());
            ps.setString(3, clc.getApellidos());
            ps.setString(4, clc.getDireccion());
            ps.setString(5, clc.getCelular());
            ps.setString(6, clc.getSexo());
            ps.setString(7, clc.getCiudad());
            
            ps.execute();
        }catch (SQLException e){
            JOptionPane.showMessageDialog(null,"Eror: "+ e.getMessage());
        }
    }
    
    public void actualizarCliente(CLCliente clc) throws SQLException{
        String sql="{CALL actualizarCliente(?,?,?,?,?,?,?)}";
        try{
            ps = cn.prepareCall(sql);
            ps.setString(1, clc.getDniCliente());
            ps.setString(2, clc.getNombre());
            ps.setString(3, clc.getApellidos());
            ps.setString(4, clc.getDireccion());
            ps.setString(5, clc.getCelular());
            ps.setString(6, clc.getSexo());
            ps.setString(7, clc.getCiudad());
            
            ps.execute();
        }catch (SQLException e){
            JOptionPane.showMessageDialog(null,"Eror: "+ e.getMessage());
        }
    }
    public void eliminarCliente(CLCliente clc) throws SQLException{
        String sql="{CALL eliminarCliente(?)}";
        
        try{
            ps=cn.prepareStatement(sql);
            ps.setString(1, clc.getDniCliente());
            
            ps.execute();            
        } catch(SQLException e){
            JOptionPane.showMessageDialog(null,"Error: "+e.getMessage());
        }
    }
    
    public List<CLCliente> obtenerListaCliente() throws SQLException{
        String sql;
        
        sql="{CALL mostrarClientes()}";
        List<CLCliente> miLista=null;
        
        try{
            ps = cn.prepareStatement(sql);
            rs = ps.executeQuery();
            
            miLista = new ArrayList<>();
            
            while (rs.next()){
                CLCliente clc = new CLCliente();
                
                clc.setDniCliente(rs.getString("DNICliente"));
                clc.setNombre(rs.getString("nombreCliente"));
                clc.setApellidos(rs.getString("apellidosCliente"));
                clc.setDireccion(rs.getString("direccion"));
                clc.setCelular(rs.getString("celular"));
                clc.setSexo(rs.getString("sexo"));
                clc.setCiudad(rs.getString("nombreCiudad"));      
                miLista.add(clc);
            }
        }catch (SQLException e){
                JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        }
        return miLista;
        }
    
    public List<CLCliente> obtenerListaClientePorDNI(String dniCliente) throws SQLException{
        String sql;
        
        sql="{CALL mostrarClienteX(?)}";
        List<CLCliente> miLista=null;
        
        try{
            ps = cn.prepareStatement(sql);
            ps.setString(1, dniCliente);
            rs = ps.executeQuery();
            
            miLista = new ArrayList<>();
            
            while (rs.next()){
                CLCliente clc = new CLCliente();
                
                clc.setDniCliente(rs.getString("DNICliente"));
                clc.setNombre(rs.getString("nombreCliente"));
                clc.setApellidos(rs.getString("apellidosCliente"));
                clc.setDireccion(rs.getString("direccion"));
                clc.setCelular(rs.getString("celular"));
                clc.setSexo(rs.getString("sexo"));
                clc.setCiudad(rs.getString("nombreCiudad"));      
                miLista.add(clc);
                
            }
        }catch (SQLException e){
                JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        }
        return miLista;
        }
    
}
      

