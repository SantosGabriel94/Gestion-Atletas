/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.atletas.model;

/**
 *
 * @author Admin
 */
public class PagoMensual {

    private int idPago;
    private int idMembresia;
    private int mes;        // 1-12
    private int anio;       // 4 dígitos
    private double monto;
    private String fechaPago;   // puede ser "" si está pendiente/atrasado
    private String estadoPago;  // Pagado / Pendiente / Atrasado

    public PagoMensual(int idPago,
                       int idMembresia,
                       int mes,
                       int anio,
                       double monto,
                       String fechaPago,
                       String estadoPago) {
        this.idPago = idPago;
        this.idMembresia = idMembresia;
        this.mes = mes;
        this.anio = anio;
        this.monto = monto;
        this.fechaPago = fechaPago;
        this.estadoPago = estadoPago;
    }

    public int getIdPago() {
        return idPago;
    }

    public int getIdMembresia() {
        return idMembresia;
    }

    public int getMes() {
        return mes;
    }

    public int getAnio() {
        return anio;
    }

    public double getMonto() {
        return monto;
    }

    public String getFechaPago() {
        return fechaPago;
    }

    public String getEstadoPago() {
        return estadoPago;
    }

    public void setFechaPago(String fechaPago) {
        this.fechaPago = fechaPago;
    }

    public void setEstadoPago(String estadoPago) {
        this.estadoPago = estadoPago;
    }

    @Override
    public String toString() {
        return "PagoMensual{" +
                "idPago=" + idPago +
                ", idMembresia=" + idMembresia +
                ", mes=" + mes +
                ", anio=" + anio +
                ", monto=" + monto +
                ", fechaPago='" + fechaPago + '\'' +
                ", estadoPago='" + estadoPago + '\'' +
                '}';
    }
}