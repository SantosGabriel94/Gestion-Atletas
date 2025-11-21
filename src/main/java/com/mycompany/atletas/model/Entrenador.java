/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.atletas.model;

/**
 *
 * @author Admin
 */
public class Entrenador {

    private int idEntrenador;
    private String nombre;
    private String telefono;
    private String correo;
    private String licenciaFederacion;
    private String categoriaLicencia;

    public Entrenador(int idEntrenador,
                      String nombre,
                      String telefono,
                      String correo,
                      String licenciaFederacion,
                      String categoriaLicencia) {
        this.idEntrenador = idEntrenador;
        this.nombre = nombre;
        this.telefono = telefono;
        this.correo = correo;
        this.licenciaFederacion = licenciaFederacion;
        this.categoriaLicencia = categoriaLicencia;
    }

    // GETTERS
    public int getIdEntrenador() {
        return idEntrenador;
    }

    public String getNombre() {
        return nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public String getLicenciaFederacion() {
        return licenciaFederacion;
    }

    public String getCategoriaLicencia() {
        return categoriaLicencia;
    }

    // SETTERS
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public void setLicenciaFederacion(String licenciaFederacion) {
        this.licenciaFederacion = licenciaFederacion;
    }

    public void setCategoriaLicencia(String categoriaLicencia) {
        this.categoriaLicencia = categoriaLicencia;
    }

    @Override
    public String toString() {
        return "Entrenador{" +
                "idEntrenador=" + idEntrenador +
                ", nombre='" + nombre + '\'' +
                ", telefono='" + telefono + '\'' +
                ", correo='" + correo + '\'' +
                ", licenciaFederacion='" + licenciaFederacion + '\'' +
                ", categoriaLicencia='" + categoriaLicencia + '\'' +
                '}';
    }
}
