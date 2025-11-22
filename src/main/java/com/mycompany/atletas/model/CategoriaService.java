/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.atletas.model;

import java.util.List;

/**
 *
 * @author Admin
 */
public class CategoriaService {

    private final CategoriaDAO dao;

    public CategoriaService() {
        this.dao = new CategoriaDAO();
    }

    // listar todas las categorias (para combos, tablas, etc.)
    public List<Categoria> listarTodas() {
        return dao.listarTodas();
    }

    // buscar una categoria por id (para validar que exista)
    public Categoria buscarPorId(int idCategoria) {
        if (idCategoria <= 0) {
            return null;
        }
        return dao.buscarPorId(idCategoria);
    }

    // helper opcional: obtener la categoria adecuada segun una edad
    public Categoria buscarPorEdad(int edad) {
        if (edad <= 0) {
            return null;
        }

        List<Categoria> categorias = dao.listarTodas();
        for (Categoria c : categorias) {
            if (edad >= c.getEdadMin() && edad <= c.getEdadMax()) {
                return c;
            }
        }
        return null; // sin categoria que calce
    }
}
