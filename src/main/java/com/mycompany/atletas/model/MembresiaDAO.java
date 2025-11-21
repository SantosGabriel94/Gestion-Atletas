/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.atletas.model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Admin
 */
public class MembresiaDAO {

    // Crear una membresía nueva para un atleta
    public int crearMembresia(int idAtleta, String fechaInicio, String estado) {
        String sql = """
            INSERT INTO membresias (id_atleta, fecha_inicio, fecha_fin, estado)
            VALUES (?, ?, ?, ?);
        """;

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, idAtleta);
            ps.setString(2, fechaInicio);
            ps.setString(3, ""); // sin fecha fin al inicio
            ps.setString(4, estado); // normalmente "Activa"

            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1);
            }

        } catch (SQLException e) {
            System.out.println("Error al crear membresía: " + e.getMessage());
        }
        return -1;
    }

    // Obtener todas las membresías de un atleta
    public List<Membresia> obtenerPorAtleta(int idAtleta) {
        List<Membresia> lista = new ArrayList<>();

        String sql = """
            SELECT id_membresia, id_atleta, fecha_inicio, fecha_fin, estado
            FROM membresias
            WHERE id_atleta = ?;
        """;

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idAtleta);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Membresia m = new Membresia(
                        rs.getInt("id_membresia"),
                        rs.getInt("id_atleta"),
                        rs.getString("fecha_inicio"),
                        rs.getString("fecha_fin"),
                        rs.getString("estado")
                );
                lista.add(m);
            }

        } catch (SQLException e) {
            System.out.println("Error al obtener membresías: " + e.getMessage());
        }

        return lista;
    }

    // Obtener la membresía activa (si existe)
    public Membresia obtenerMembresiaActiva(int idAtleta) {
        String sql = """
            SELECT id_membresia, id_atleta, fecha_inicio, fecha_fin, estado
            FROM membresias
            WHERE id_atleta = ? AND estado = 'Activa'
            LIMIT 1;
        """;

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idAtleta);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new Membresia(
                        rs.getInt("id_membresia"),
                        rs.getInt("id_atleta"),
                        rs.getString("fecha_inicio"),
                        rs.getString("fecha_fin"),
                        rs.getString("estado")
                );
            }

        } catch (SQLException e) {
            System.out.println("Error al obtener membresía activa: " + e.getMessage());
        }
        return null;
    }
}
