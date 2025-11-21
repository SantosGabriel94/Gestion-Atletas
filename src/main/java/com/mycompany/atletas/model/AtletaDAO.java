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
public class AtletaDAO {

    public AtletaDAO() {
        // La BD se inicializa desde el main, no desde aquí.
    }

    // ============================================
    // INSERTAR NUEVO ATLETA
    // ============================================
    public boolean insertar(Atleta atleta) {

        String sql = """
            INSERT INTO atletas (
                nombre,
                cedula,
                direccion,
                telefono,
                correo,
                fecha_nacimiento,
                id_entrenador,
                id_categoria,
                sexo,
                lateralidad,
                fecha_ingreso_asociacion,
                estado,
                es_federado
            ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);
        """;

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, atleta.getNombre());
            ps.setString(2, atleta.getCedula());
            ps.setString(3, atleta.getDireccion());
            ps.setString(4, atleta.getTelefono());
            ps.setString(5, atleta.getCorreo());
            ps.setString(6, atleta.getFechaNacimiento());
            ps.setInt(7, atleta.getIdEntrenador());
            ps.setInt(8, atleta.getIdCategoria());
            ps.setString(9, atleta.getSexo());
            ps.setString(10, atleta.getLateralidad());
            ps.setString(11, atleta.getFechaIngresoAsociacion());
            ps.setString(12, atleta.getEstado());
            ps.setString(13, atleta.getEsFederado());

            ps.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.out.println("Error al insertar atleta: " + e.getMessage());
            return false;
        }
    }

    // ============================================
    // LISTAR TODOS LOS ATLETAS
    // ============================================
    public List<Atleta> listarTodos() {

        List<Atleta> lista = new ArrayList<>();

        String sql = """
            SELECT
                id_atleta,
                nombre,
                cedula,
                direccion,
                telefono,
                correo,
                fecha_nacimiento,
                id_entrenador,
                id_categoria,
                sexo,
                lateralidad,
                fecha_ingreso_asociacion,
                estado,
                es_federado
            FROM atletas;
        """;

        try (Connection conn = ConexionBD.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {

                Atleta a = new Atleta(
                        rs.getInt("id_atleta"),
                        rs.getString("nombre"),
                        rs.getString("cedula"),
                        rs.getString("direccion"),
                        rs.getString("telefono"),
                        rs.getString("correo"),
                        rs.getString("fecha_nacimiento"),
                        rs.getInt("id_entrenador"),
                        rs.getInt("id_categoria"),
                        rs.getString("sexo"),
                        rs.getString("lateralidad"),
                        rs.getString("fecha_ingreso_asociacion"),
                        rs.getString("estado"),
                        rs.getString("es_federado")
                );

                lista.add(a);
            }

        } catch (SQLException e) {
            System.out.println("Error al listar atletas: " + e.getMessage());
        }

        return lista;
    }

    // ============================================
    // BUSCAR POR NOMBRE O CÉDULA (FILTRO)
    // ============================================
    public List<Atleta> buscarPorNombreOCedula(String patron) {
        List<Atleta> lista = new ArrayList<>();

        String sql = """
            SELECT
                id_atleta,
                nombre,
                cedula,
                direccion,
                telefono,
                correo,
                fecha_nacimiento,
                id_entrenador,
                id_categoria,
                sexo,
                lateralidad,
                fecha_ingreso_asociacion,
                estado,
                es_federado
            FROM atletas
            WHERE nombre LIKE ? OR cedula LIKE ?;
        """;

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            String like = "%" + patron + "%";
            ps.setString(1, like);
            ps.setString(2, like);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Atleta a = new Atleta(
                        rs.getInt("id_atleta"),
                        rs.getString("nombre"),
                        rs.getString("cedula"),
                        rs.getString("direccion"),
                        rs.getString("telefono"),
                        rs.getString("correo"),
                        rs.getString("fecha_nacimiento"),
                        rs.getInt("id_entrenador"),
                        rs.getInt("id_categoria"),
                        rs.getString("sexo"),
                        rs.getString("lateralidad"),
                        rs.getString("fecha_ingreso_asociacion"),
                        rs.getString("estado"),
                        rs.getString("es_federado")
                );
                lista.add(a);
            }

        } catch (SQLException e) {
            System.out.println("Error al buscar atletas: " + e.getMessage());
        }

        return lista;
    }

    // ============================================
    // BUSCAR POR ID
    // ============================================
    public Atleta buscarPorId(int idAtleta) {

        String sql = """
            SELECT
                id_atleta,
                nombre,
                cedula,
                direccion,
                telefono,
                correo,
                fecha_nacimiento,
                id_entrenador,
                id_categoria,
                sexo,
                lateralidad,
                fecha_ingreso_asociacion,
                estado,
                es_federado
            FROM atletas
            WHERE id_atleta = ?;
        """;

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idAtleta);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new Atleta(
                        rs.getInt("id_atleta"),
                        rs.getString("nombre"),
                        rs.getString("cedula"),
                        rs.getString("direccion"),
                        rs.getString("telefono"),
                        rs.getString("correo"),
                        rs.getString("fecha_nacimiento"),
                        rs.getInt("id_entrenador"),
                        rs.getInt("id_categoria"),
                        rs.getString("sexo"),
                        rs.getString("lateralidad"),
                        rs.getString("fecha_ingreso_asociacion"),
                        rs.getString("estado"),
                        rs.getString("es_federado")
                );
            }

        } catch (SQLException e) {
            System.out.println("Error al buscar atleta: " + e.getMessage());
        }

        return null;
    }

    // ============================================
    // ACTUALIZAR ATLETA
    // ============================================
    public boolean actualizar(Atleta atleta) {

        String sql = """
            UPDATE atletas
            SET
                nombre = ?,
                cedula = ?,
                direccion = ?,
                telefono = ?,
                correo = ?,
                fecha_nacimiento = ?,
                id_entrenador = ?,
                id_categoria = ?,
                sexo = ?,
                lateralidad = ?,
                fecha_ingreso_asociacion = ?,
                estado = ?,
                es_federado = ?
            WHERE id_atleta = ?;
        """;

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, atleta.getNombre());
            ps.setString(2, atleta.getCedula());
            ps.setString(3, atleta.getDireccion());
            ps.setString(4, atleta.getTelefono());
            ps.setString(5, atleta.getCorreo());
            ps.setString(6, atleta.getFechaNacimiento());
            ps.setInt(7, atleta.getIdEntrenador());
            ps.setInt(8, atleta.getIdCategoria());
            ps.setString(9, atleta.getSexo());
            ps.setString(10, atleta.getLateralidad());
            ps.setString(11, atleta.getFechaIngresoAsociacion());
            ps.setString(12, atleta.getEstado());
            ps.setString(13, atleta.getEsFederado());
            ps.setInt(14, atleta.getId());

            int filas = ps.executeUpdate();
            return filas > 0;

        } catch (SQLException e) {
            System.out.println("Error al actualizar atleta: " + e.getMessage());
            return false;
        }
    }

    // ============================================
    // ELIMINAR ATLETA
    // ============================================
    public boolean eliminar(int idAtleta) {

        String sql = "DELETE FROM atletas WHERE id_atleta = ?;";

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idAtleta);
            int filas = ps.executeUpdate();
            return filas > 0;

        } catch (SQLException e) {
            System.out.println("Error al eliminar atleta: " + e.getMessage());
            return false;
        }
    }
}