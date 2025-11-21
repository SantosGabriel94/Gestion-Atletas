/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.atletas.controller;
import com.mycompany.atletas.model.Atleta;
import com.mycompany.atletas.model.AtletaDAO;
import com.mycompany.View.AtletaView;
import com.mycompany.atletas.model.ConexionBD;
import com.mycompany.atletas.util.Validador;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Scanner;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
/**
 *
 * @author Admin
 */
public class AtletaController {

    private final AtletaDAO dao;

    public AtletaController() {
        this.dao = new AtletaDAO();
    }

    // =====================================================
    // CREATE
    // =====================================================

    /**
     * Inserta un nuevo atleta en la base de datos.
     * Los datos YA DEBEN VENIR VALIDADOS.
     */
    public boolean crearAtleta(Atleta atleta) {
        return dao.insertar(atleta);
    }

    // =====================================================
    // READ (Listar todos)
    // =====================================================

    /**
     * Devuelve todos los atletas.
     */
    public List<Atleta> listarAtletas() {
        return dao.listarTodos();
    }

    // =====================================================
    // READ filtrado (nombre/cédula)
    // =====================================================

    /**
     * Busca atletas cuyo nombre o cédula coincidan parcialmente.
     */
    public List<Atleta> buscarAtleta(String patron) {
        return dao.buscarPorNombreOCedula(patron);
    }

    // =====================================================
    // READ por ID
    // =====================================================

    /**
     * Devuelve un atleta por su ID o null si no existe.
     */
    public Atleta buscarPorId(int id) {
        return dao.buscarPorId(id);
    }

    // =====================================================
    // UPDATE
    // =====================================================

    /**
     * Actualiza un atleta existente.
     */
    public boolean actualizarAtleta(Atleta atleta) {
        return dao.actualizar(atleta);
    }

    // =====================================================
    // DELETE
    // =====================================================

    /**
     * Elimina un atleta por su ID.
     */
    public boolean eliminarAtleta(int id) {
        return dao.eliminar(id);
    }
}

