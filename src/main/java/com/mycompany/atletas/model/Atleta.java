/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.atletas.model;

/**
 *
 * @author Admin
 */

public class Atleta {

    private int id;
    private String nombre;
    private String cedula;
    private String direccion;
    private String telefono;
    private String correo;
    private String fechaNacimiento;
    private int idEntrenador;
    private int idCategoria;
    private String sexo;
    private String lateralidad;
    private String fechaIngresoAsociacion;
    private String estado;
    private String esFederado;

    public Atleta(int id, String nombre, String cedula, String direccion,
                  String telefono, String correo, String fechaNacimiento,
                  int idEntrenador, int idCategoria, String sexo,
                  String lateralidad, String fechaIngresoAsociacion,
                  String estado, String esFederado) {

        this.id = id;
        this.nombre = nombre;
        this.cedula = cedula;
        this.direccion = direccion;
        this.telefono = telefono;
        this.correo = correo;
        this.fechaNacimiento = fechaNacimiento;
        this.idEntrenador = idEntrenador;
        this.idCategoria = idCategoria;
        this.sexo = sexo;
        this.lateralidad = lateralidad;
        this.fechaIngresoAsociacion = fechaIngresoAsociacion;
        this.estado = estado;
        this.esFederado = esFederado;
    }

    // Getters (por ahora solo los básicos; luego podemos generar todos)
    public int getId() { return id; }
    public String getNombre() { return nombre; }
    public String getCedula() { return cedula; }
    public String getDireccion() { return direccion; }
    public String getTelefono() { return telefono; }
    public String getCorreo() { return correo; }
    public String getFechaNacimiento() { return fechaNacimiento; }
    public int getIdEntrenador() { return idEntrenador; }
    public int getIdCategoria() { return idCategoria; }
    public String getSexo() { return sexo; }
    public String getLateralidad() { return lateralidad; }
    public String getFechaIngresoAsociacion() { return fechaIngresoAsociacion; }
    public String getEstado() { return estado; }
    public String getEsFederado() { return esFederado; }

    @Override
    public String toString() {
        return "Atleta{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", cedula='" + cedula + '\'' +
                ", direccion='" + direccion + '\'' +
                ", telefono='" + telefono + '\'' +
                ", correo='" + correo + '\'' +
                ", fechaNacimiento='" + fechaNacimiento + '\'' +
                ", idEntrenador=" + idEntrenador +
                ", idCategoria=" + idCategoria +
                ", sexo='" + sexo + '\'' +
                ", lateralidad='" + lateralidad + '\'' +
                ", fechaIngresoAsociacion='" + fechaIngresoAsociacion + '\'' +
                ", estado='" + estado + '\'' +
                ", esFederado='" + esFederado + '\'' +
                '}';
    }
}