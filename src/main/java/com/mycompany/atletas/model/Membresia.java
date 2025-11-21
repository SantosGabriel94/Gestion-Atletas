/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.atletas.model;

/**
 *
 * @author Admin
 */
public class Membresia {

    private int idMembresia;
    private int idAtleta;
    private String fechaInicio; // dd/MM/aaaa o yyyy-MM-dd, como decidas
    private String fechaFin;    // puede ser "" o null si sigue activa
    private String estado;      // Activa / Inactiva

    public Membresia(int idMembresia,
                     int idAtleta,
                     String fechaInicio,
                     String fechaFin,
                     String estado) {
        this.idMembresia = idMembresia;
        this.idAtleta = idAtleta;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.estado = estado;
    }

    public int getIdMembresia() {
        return idMembresia;
    }

    public int getIdAtleta() {
        return idAtleta;
    }

    public String getFechaInicio() {
        return fechaInicio;
    }

    public String getFechaFin() {
        return fechaFin;
    }

    public String getEstado() {
        return estado;
    }

    public void setFechaFin(String fechaFin) {
        this.fechaFin = fechaFin;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "Membresia{" +
                "idMembresia=" + idMembresia +
                ", idAtleta=" + idAtleta +
                ", fechaInicio='" + fechaInicio + '\'' +
                ", fechaFin='" + fechaFin + '\'' +
                ", estado='" + estado + '\'' +
                '}';
    }
}
