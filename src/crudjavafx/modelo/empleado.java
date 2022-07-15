/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crudjavafx.modelo;

/**
 *
 * @author Usuario
 */
public class empleado {
    
    private int id;
    private String nombre;
    private String apellido;
    private String puesto;
    private String fecha_contratacion;
    private String correo;
    private String portal_web;
    private String fisico;
    private String estado;

    public empleado() {
    }
    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getPuesto() {
        return puesto;
    }

    public void setPuesto(String puesto) {
        this.puesto = puesto;
    }

    public String getFecha_contratacion() {
        return fecha_contratacion;
    }

    public void setFecha_contratacion(String fecha_contratacion) {
        this.fecha_contratacion = fecha_contratacion;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getPortal_web() {
        return portal_web;
    }

    public void setPortal_web(String portal_web) {
        this.portal_web = portal_web;
    }

    public String getFisico() {
        return fisico;
    }

    public void setFisico(String fisico) {
        this.fisico = fisico;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "empleado{" + "id=" + id + ", nombre=" + nombre + ", apellido=" + apellido + ", puesto=" + puesto + ", fecha_contratacion=" + fecha_contratacion + ", correo=" + correo + ", portal_web=" + portal_web + ", fisico=" + fisico + ", estado=" + estado + '}';
    }
    
    
    
}
