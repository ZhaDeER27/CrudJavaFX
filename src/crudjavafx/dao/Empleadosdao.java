/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crudjavafx.dao;

import crudjavafx.conexion.ConexionMySQL;
import crudjavafx.modelo.empleado;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *
 * @author Usuario
 */
public class Empleadosdao {
    
    private ConexionMySQL fabricaConexion;
    
    
    public Empleadosdao(){
        this.fabricaConexion = new ConexionMySQL();
    }
    
    public boolean registrar(empleado empleado){
        
        try {
            
            String SQL = "Insert into db_empleado(nombre,apellido,puesto,fecha_contratacion,correo,"
                    + "portal_web,fisico,estado )"
                    + "values(?,?,?,?,?,?,?,?)";
                    
            Connection connection = this.fabricaConexion.getConnection();
            
            PreparedStatement sentencia = connection.prepareStatement(SQL);
            
            sentencia.setString(1, empleado.getNombre());
            sentencia.setString(2, empleado.getApellido());
            sentencia.setString(3, empleado.getPuesto());
            sentencia.setString(4, empleado.getFecha_contratacion());
            sentencia.setString(5, empleado.getCorreo());
            sentencia.setString(6, empleado.getPortal_web());
            sentencia.setString(7, empleado.getFisico());
            sentencia.setString(8, empleado.getEstado());
            
            sentencia.executeUpdate();
            
            sentencia.close();
            
            return true;
            
        } catch (Exception e) {
            
            System.err.println("Hubo un error al registrar el empleado");
            System.err.println("Mensaje del error: " +e.getMessage());
            System.err.println("Detalle del erro: ");
            
            e.printStackTrace();
            
            return false;
        }     
    }
    
    public  List<empleado> listar(){
        
            List<empleado> listaEmpleados = new ArrayList<>();
            
        try {
            
            
            
            String SQL = "SELECT * FROM db_crud_java_fx.db_empleado;";
            
            Connection connection = this.fabricaConexion.getConnection();
            
            PreparedStatement sentencia = connection.prepareStatement(SQL);
            
            ResultSet data = sentencia.executeQuery();
            
            while(data.next() == true){
                
                empleado empleado = new empleado();
                
                empleado.setId(data.getInt(1));
                empleado.setNombre(data.getString(2));
                empleado.setApellido(data.getString(3));
                empleado.setPuesto(data.getString(4));
                empleado.setFecha_contratacion(data.getString(5));
                empleado.setCorreo(data.getString(6));
                empleado.setPortal_web(data.getString(7));
                empleado.setFisico(data.getString(8));
                empleado.setEstado(data.getString(9));
                
                
                listaEmpleados.add(empleado);
            }
            
            data.close();
            sentencia.close();
            
        } catch (Exception e) {
            
            System.err.println("Hubo un error al mostrar los empleados");
            System.err.println("Mensaje del error: " +e.getMessage());
            System.err.println("Detalle del erro: ");
            
            e.printStackTrace();
            
        }
        
        
        return listaEmpleados;
    }
    
    public boolean editar(empleado empleado){
        
        try {
            
            String SQL = "update db_empleado set nombre=?, apellido=?, puesto=?,"
                    + "fecha_contratacion=?,correo=?,portal_web=?,fisico=?,"
                    + "estado=? WHERE id=?";
            
            Connection connection = this.fabricaConexion.getConnection();
            
            PreparedStatement sentencia = connection.prepareStatement(SQL);
            
            sentencia.setString(1, empleado.getNombre());
            sentencia.setString(2, empleado.getApellido());
            sentencia.setString(3, empleado.getPuesto());
            sentencia.setString(4, empleado.getFecha_contratacion());
            sentencia.setString(5, empleado.getCorreo());
            sentencia.setString(6, empleado.getPortal_web());
            sentencia.setString(7, empleado.getFisico());
            sentencia.setString(8, empleado.getEstado());
            
            sentencia.setInt(9, empleado.getId());
            
            sentencia.executeUpdate();
            
            sentencia.close();
            
            return true;
            
            
        } catch (Exception e) {
             System.err.println("Hubo un error al editar los datos del empleado");
            System.err.println("Mensaje del error: " +e.getMessage());
            System.err.println("Detalle del erro: ");
            
            e.printStackTrace();
            
            return false;
        }
    }
   
    public boolean eliminar(int id){
        
        try {
            
            String SQL = "delete from db_empleado where id=?";
            
            Connection connection = this.fabricaConexion.getConnection();
           PreparedStatement sentencia = connection.prepareStatement(SQL);
           
           sentencia.setInt(1, id);
           
           sentencia.executeUpdate();
           
           sentencia.close();
           
           return true;
            
        } catch (Exception e) {
            
             System.err.println("Hubo un error al editar los datos del empleado");
            System.err.println("Mensaje del error: " +e.getMessage());
            System.err.println("Detalle del erro: ");
            
            e.printStackTrace();
            
            return false; 
        }
        
    }
    
}
