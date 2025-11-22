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

public class CategoriaDAO {

    // Listar todas las categorias
    public List<Categoria> listarTodas() {
        List<Categoria> lista = new ArrayList<>();

        String sql = """
            SELECT id_categoria, nombre_categoria, edad_min, edad_max
            FROM categorias_edad
            ORDER BY id_categoria;
        """;

        try (Connection conn = ConexionBD.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                Categoria c = new Categoria(
                        rs.getInt("id_categoria"),
                        rs.getString("nombre_categoria"),
                        rs.getInt("edad_min"),
                        rs.getInt("edad_max")
                );
                lista.add(c);
            }

        } catch (SQLException e) {
            System.out.println("Error al listar categorias: " + e.getMessage());
        }

        return lista;
    }

    // Buscar categoria por ID
    public Categoria buscarPorId(int idCategoria) {
        String sql = """
            SELECT id_categoria, nombre_categoria, edad_min, edad_max
            FROM categorias_edad
            WHERE id_categoria = ?;
        """;

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idCategoria);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new Categoria(
                        rs.getInt("id_categoria"),
                        rs.getString("nombre_categoria"),
                        rs.getInt("edad_min"),
                        rs.getInt("edad_max")
                );
            }

        } catch (SQLException e) {
            System.out.println("Error al buscar categoria: " + e.getMessage());
        }

        return null;
    }
}