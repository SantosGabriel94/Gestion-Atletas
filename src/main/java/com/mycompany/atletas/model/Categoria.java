/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.atletas.model;

/**
 *
 * @author Admin
 */
public class Categoria {

    private int idCategoria;
    private String nombreCategoria;
    private int edadMin;
    private int edadMax;

    public Categoria(int idCategoria, String nombreCategoria, int edadMin, int edadMax) {
        this.idCategoria = idCategoria;
        this.nombreCategoria = nombreCategoria;
        this.edadMin = edadMin;
        this.edadMax = edadMax;
    }

    public int getIdCategoria() {
        return idCategoria;
    }

    public String getNombreCategoria() {
        return nombreCategoria;
    }

    public int getEdadMin() {
        return edadMin;
    }

    public int getEdadMax() {
        return edadMax;
    }

    @Override
    public String toString() {
        return "Categoria{" +
                "idCategoria=" + idCategoria +
                ", nombreCategoria='" + nombreCategoria + '\'' +
                ", edadMin=" + edadMin +
                ", edadMax=" + edadMax +
                '}';
    }
}
