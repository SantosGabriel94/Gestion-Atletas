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
public class PagoMensualDAO {

    // Registrar o actualizar un pago de un mes concreto
    public boolean registrarPago(int idMembresia,
                                 int mes,
                                 int anio,
                                 double monto,
                                 String fechaPago,
                                 String estadoPago) {

        String sql = """
            INSERT INTO pagos_mensuales (id_membresia, mes, anio, monto, fecha_pago, estado_pago)
            VALUES (?, ?, ?, ?, ?, ?)
            ON CONFLICT(id_membresia, anio, mes)
            DO UPDATE SET
                monto = excluded.monto,
                fecha_pago = excluded.fecha_pago,
                estado_pago = excluded.estado_pago;
        """;

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idMembresia);
            ps.setInt(2, mes);
            ps.setInt(3, anio);
            ps.setDouble(4, monto);
            ps.setString(5, fechaPago);
            ps.setString(6, estadoPago);

            int filas = ps.executeUpdate();
            return filas > 0;

        } catch (SQLException e) {
            System.out.println("Error al registrar pago mensual: " + e.getMessage());
            return false;
        }
    }

    // Obtener todos los pagos de un año para una membresía
    public List<PagoMensual> obtenerPagosPorMembresiaYAnio(int idMembresia, int anio) {
        List<PagoMensual> lista = new ArrayList<>();

        String sql = """
            SELECT id_pago, id_membresia, mes, anio, monto, fecha_pago, estado_pago
            FROM pagos_mensuales
            WHERE id_membresia = ? AND anio = ?
            ORDER BY mes;
        """;

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idMembresia);
            ps.setInt(2, anio);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                PagoMensual p = new PagoMensual(
                        rs.getInt("id_pago"),
                        rs.getInt("id_membresia"),
                        rs.getInt("mes"),
                        rs.getInt("anio"),
                        rs.getDouble("monto"),
                        rs.getString("fecha_pago"),
                        rs.getString("estado_pago")
                );
                lista.add(p);
            }

        } catch (SQLException e) {
            System.out.println("Error al obtener pagos mensuales: " + e.getMessage());
        }

        return lista;
    }
}