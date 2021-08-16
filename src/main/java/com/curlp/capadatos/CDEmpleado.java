
package com.curlp.capadatos;

import com.curlp.capalogica.CLEmpleado;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class CDEmpleado {
    private final Connection cn;
    PreparedStatement ps;
    Statement st;
    ResultSet rs;

    public CDEmpleado() throws SQLException {
        cn = Conexion.conectar();
    }
    
    //Método para agregar un empleado a la tabla empleado
    public void insertarEmpleado(CLEmpleado cl) throws SQLException {
        
        String sql = "{CALL insertarEmpleado(?,?,?,?,?,?,?)}";
        
        try {
            ps = cn.prepareCall(sql);
            
            ps.setString(1, cl.getDNIEmp());
            ps.setString(2, cl.getNombreEmp());
            ps.setString(3, cl.getApellidosEmp());
            ps.setString(4, cl.getDireccion());
            ps.setString(5, cl.getCelular());
            ps.setString(6, cl.getCargo());
            ps.setString(7,cl.getCiudad());
            
            ps.execute();
            
        } catch(SQLException e) {
            JOptionPane.showMessageDialog(null,"Error en funcion CDEMPLEADO" + e.getMessage());
        }
    }
    
    //Método para actualizar un empleado en la tabla empleado
    public void actualizarEmpleado(CLEmpleado cl) throws SQLException {
        
        String sql = "{CALL actualizarEmpleado(?,?,?,?,?,?,?)}";
        
        try {
            ps = cn.prepareCall(sql);
           
            ps.setString(1, cl.getDNIEmp());
            ps.setString(2, cl.getNombreEmp());
            ps.setString(3, cl.getApellidosEmp());
            ps.setString(4, cl.getDireccion());
            ps.setString(5, cl.getCelular());
            ps.setString(6, cl.getCargo());
            ps.setString(7, cl.getCiudad());
            
            ps.execute();
            
        } catch(SQLException e) {
            JOptionPane.showMessageDialog(null,"Error " + e.getMessage());
        }
        
    }
    
    //Método para eliminar un empleado en la tabla empleado
    public void eliminarEmpleado(CLEmpleado cl) throws SQLException {
        
        String sql = "{CALL eliminarEmpleado(?)}";
        
        try {
            ps = cn.prepareCall(sql);
           
            ps.setString(1, cl.getDNIEmp());
            
            ps.execute();
            
        } catch(SQLException e) {
            JOptionPane.showMessageDialog(null,"Error " + e.getMessage());
        }
    }
    
    //Método para OBTENER la lista de empleados 
    public List<CLEmpleado> obtenerListaEmpleados() throws SQLException {
        
        String sql = "{CALL mostrarEmpleados()}";
        
        List<CLEmpleado> miLista = null;
        
        try {
            st = cn.createStatement();
            rs = st.executeQuery(sql);
            
            miLista = new ArrayList<>();
            
            while(rs.next()) {
                CLEmpleado cl = new CLEmpleado();
                
                cl.setDNIEmp(rs.getString("DNIEmp"));
                cl.setNombreEmp(rs.getString("nombreEmp"));
                cl.setApellidosEmp(rs.getString("apellidosEmp"));
                cl.setDireccion(rs.getString("direccion"));
                cl.setCelular(rs.getString("celular"));
                cl.setCargo(rs.getString("cargo"));
                cl.setCiudad(rs.getString("nombreCiudad"));
                
                miLista.add(cl);
            }
            
        } catch(SQLException e) {
            JOptionPane.showMessageDialog(null,"Error " + e.getMessage());
        }
        
        return miLista;
    }
    
    
    public List<CLEmpleado> obtenerListaEmpleadoPorNombre(String nombreEmpleado) throws SQLException {
        String sql = "{CALL mostrarEmpleadoPorNombre(?)}";
        
        List<CLEmpleado> miLista = null;
        miLista = new ArrayList<>();
        
        try {
            ps = cn.prepareStatement(sql);
            ps.setString(1,nombreEmpleado);
            ps.executeQuery();
            
            while(rs.next()) {
                CLEmpleado cl = new CLEmpleado();
                
                cl.setDNIEmp(rs.getString("DNIEmp"));
                cl.setNombreEmp(rs.getString("nombreEmp"));
                cl.setApellidosEmp(rs.getString("apellidosEmp"));
                cl.setDireccion(rs.getString("direccion"));
                cl.setCelular(rs.getString("celular"));
                cl.setCargo(rs.getString("cargo"));
                cl.setCiudad(rs.getString("ciudad"));
                
                miLista.add(cl);
            }
            
        } catch(SQLException e) {
            JOptionPane.showMessageDialog(null,"Error " + e.getMessage());

        }
        return miLista;
    }
}