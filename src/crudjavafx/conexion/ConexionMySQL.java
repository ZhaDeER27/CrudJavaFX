/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crudjavafx.conexion;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author Usuario
 */
public class ConexionMySQL {
    
    private Connection connection;
    private String usuario = "root";
    private String password = "";
    private String servidor = "localhost";
    private String puerto = "3306";
    private String nombreDB = "db_crud_java_fx";
    
    private String url = "jdbc:mysql://"+servidor+":"+puerto+"/"+nombreDB+"?serverTimezone=UTC";  
    
    private String driver = "com.mysql.cj.jdbc.Driver";
    public ConexionMySQL() {
        
        try {
                
            Class.forName(driver);
            
            connection = DriverManager.getConnection(url, usuario, password);
            
            if( connection != null){
                
                System.out.println("Conexion realizada con exito!");
            }
            
        } catch (Exception e) {
            
            System.err.println("Hubo un error en la conexion");
            System.err.println("Mensaje del error: " +e.getMessage());
            System.err.println("Detalle del erro: ");
            
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        return connection;
    }
    
    
    
}
