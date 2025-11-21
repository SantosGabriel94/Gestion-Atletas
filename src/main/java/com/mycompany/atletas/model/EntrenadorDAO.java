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
public class EntrenadorDAO {

    // INSERTAR
    public boolean insertar(Entrenador e) {
        String sql = """
            INSERT INTO entrenadores (
                nombre,
                telefono,
                correo,
                licencia_federacion,
                categoria_licencia
            ) VALUES (?, ?, ?, ?, ?);
        """;

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, e.getNombre());
            ps.setString(2, e.getTelefono());
            ps.setString(3, e.getCorreo());
            ps.setString(4, e.getLicenciaFederacion());
            ps.setString(5, e.getCategoriaLicencia());

            ps.executeUpdate();
            return true;

        } catch (SQLException ex) {
            System.out.println("Error al insertar entrenador: " + ex.getMessage());
            return false;
        }
    }

    // LISTAR TODOS
    public List<Entrenador> listarTodos() {
        List<Entrenador> lista = new ArrayList<>();

        String sql = """
            SELECT
                id_entrenador,
                nombre,
                telefono,
                correo,
                licencia_federacion,
                categoria_licencia
            FROM entrenadores;
        """;

        try (Connection conn = ConexionBD.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                Entrenador e = new Entrenador(
                        rs.getInt("id_entrenador"),
                        rs.getString("nombre"),
                        rs.getString("telefono"),
                        rs.getString("correo"),
                        rs.getString("licencia_federacion"),
                        rs.getString("categoria_licencia")
                );
                lista.add(e);
            }

        } catch (SQLException ex) {
            System.out.println("Error al listar entrenadores: " + ex.getMessage());
        }

        return lista;
    }

    // BUSCAR POR ID
    public Entrenador buscarPorId(int id) {
        String sql = """
            SELECT
                id_entrenador,
                nombre,
                telefono,
                correo,
                licencia_federacion,
                categoria_licencia
            FROM entrenadores
            WHERE id_entrenador = ?;
        """;

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new Entrenador(
                        rs.getInt("id_entrenador"),
                        rs.getString("nombre"),
                        rs.getString("telefono"),
                        rs.getString("correo"),
                        rs.getString("licencia_federacion"),
                        rs.getString("categoria_licencia")
                );
            }

        } catch (SQLException ex) {
            System.out.println("Error al buscar entrenador: " + ex.getMessage());
        }

        return null;
    }

    // ACTUALIZAR
    public boolean actualizar(Entrenador e) {
        String sql = """
            UPDATE entrenadores
            SET
                nombre = ?,
                telefono = ?,
                correo = ?,
                licencia_federacion = ?,
                categoria_licencia = ?
            WHERE id_entrenador = ?;
        """;

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, e.getNombre());
            ps.setString(2, e.getTelefono());
            ps.setString(3, e.getCorreo());
            ps.setString(4, e.getLicenciaFederacion());
            ps.setString(5, e.getCategoriaLicencia());
            ps.setInt(6, e.getIdEntrenador());

            int filas = ps.executeUpdate();
            return filas > 0;

        } catch (SQLException ex) {
            System.out.println("Error al actualizar entrenador: " + ex.getMessage());
            return false;
        }
    }

    // ELIMINAR
    public boolean eliminar(int id) {
        String sql = "DELETE FROM entrenadores WHERE id_entrenador = ?;";

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            int filas = ps.executeUpdate();
            return filas > 0;

        } catch (SQLException ex) {
            System.out.println("Error al eliminar entrenador: " + ex.getMessage());
            return false;
        }
    }
}