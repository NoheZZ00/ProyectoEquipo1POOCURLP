
package com.curlp.capadatos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {
    //Declaracion de variables para la url el usuario y la clave de la base de datos
    private static String url = "jdbc:mysql://localhost:3306/sistemapaqueteria?zeroDateTimeBehavior=CONVERT_TO_NULL";
    private static String user = "root";
    private static String clave = "Agaperojo.2";
    
    //Método para conectar a la base de datos
    public static Connection conectar() throws SQLException {
        try {
          Class.forName("com.mysql.cj.jdbc.Driver");
          
          return DriverManager.getConnection(url,user,clave);
          
        } catch(ClassNotFoundException e) {
            throw new SQLException(e.getMessage());
        }
    }
}
