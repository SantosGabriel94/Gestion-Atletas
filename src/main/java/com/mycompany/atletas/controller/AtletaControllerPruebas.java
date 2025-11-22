/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.atletas.controller;

import com.mycompany.atletas.model.Atleta;
import com.mycompany.atletas.model.AtletaDAO;
import com.mycompany.atletas.model.ConexionBD;
import com.mycompany.atletas.model.Entrenador;
import com.mycompany.atletas.model.EntrenadorDAO;
import com.mycompany.atletas.model.Membresia;
import com.mycompany.atletas.model.MembresiaDAO;
import com.mycompany.atletas.model.PagoMensual;
import com.mycompany.atletas.model.PagoMensualDAO;

import java.util.List;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;


/**
 *
 * @author Admin
 */
public class AtletaControllerPruebas {

    public static void main(String[] args) {

        // 1) INICIALIZAR BASE DE DATOS Y DATOS DE EJEMPLO
        // En modo real, solo se usa inicializarBaseDatos una vez
        ConexionBD.inicializarBaseDatos();
        ConexionBD.insertarDatosEjemplo();

        AtletaDAO atletaDAO = new AtletaDAO();
        EntrenadorDAO entrenadorDAO = new EntrenadorDAO();
        MembresiaDAO membDAO = new MembresiaDAO();
        PagoMensualDAO pagoDAO = new PagoMensualDAO();

        System.out.println();
        System.out.println("========== PRUEBA GENERAL DEL SISTEMA ==========");
        System.out.println();

        probarEntrenadores(entrenadorDAO);
        probarCategoriasEdad();
        probarListadoAtletas(atletaDAO);
        probarPosicionesYAtletaPosicion();
        probarMedidasAtletas();
        probarTorneosYParticipacion();

        int anioPrueba = 2024;
        probarMembresiasYPagosPorAtleta(membDAO, pagoDAO, 1, anioPrueba);
        probarMembresiasYPagosPorAtleta(membDAO, pagoDAO, 2, anioPrueba);

        registrarYMostrarNuevoPago(membDAO, pagoDAO, 1, anioPrueba, 4);

        System.out.println();
        System.out.println("========== FIN DE PRUEBAS ==========");
        System.out.println();
    }

    // ----------------------------------------------------
    // PRUEBA 1: ENTRENADORES
    // ----------------------------------------------------
    private static void probarEntrenadores(EntrenadorDAO dao) {
        System.out.println("---- PRUEBA 1: ENTRENADORES REGISTRADOS ----");

        List<Entrenador> lista = dao.listarTodos();
        if (lista.isEmpty()) {
            System.out.println("No hay entrenadores registrados.");
            System.out.println();
            return;
        }

        for (Entrenador e : lista) {
            System.out.println("- " + e);
        }
        System.out.println();
    }

    // ----------------------------------------------------
    // PRUEBA 2: CATEGORIAS_EDAD (consulta directa)
    // ----------------------------------------------------
    private static void probarCategoriasEdad() {
        System.out.println("---- PRUEBA 2: CATEGORIAS DE EDAD ----");

        String sql = """
            SELECT id_categoria, nombre_categoria, edad_min, edad_max
            FROM categorias_edad
            ORDER BY id_categoria;
        """;

        try (Connection conn = ConexionBD.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                int id = rs.getInt("id_categoria");
                String nombre = rs.getString("nombre_categoria");
                int min = rs.getInt("edad_min");
                int max = rs.getInt("edad_max");
                System.out.printf("ID=%d | %s (%d-%d anios)%n", id, nombre, min, max);
            }

        } catch (SQLException e) {
            System.out.println("Error al listar categorias: " + e.getMessage());
        }
        System.out.println();
    }

    // ----------------------------------------------------
    // PRUEBA 3: ATLETAS
    // ----------------------------------------------------
    private static void probarListadoAtletas(AtletaDAO atletaDAO) {
        System.out.println("---- PRUEBA 3: ATLETAS REGISTRADOS ----");

        List<Atleta> atletas = atletaDAO.listarTodos();
        if (atletas.isEmpty()) {
            System.out.println("No hay atletas registrados.");
        } else {
            for (Atleta a : atletas) {
                System.out.println("- " + a);
            }
        }
        System.out.println();
    }

    // ----------------------------------------------------
    // PRUEBA 4: POSICIONES Y ATLETA_POSICION
    // ----------------------------------------------------
    private static void probarPosicionesYAtletaPosicion() {
        System.out.println("---- PRUEBA 4A: POSICIONES DISPONIBLES ----");

        String sqlPos = """
            SELECT id_posicion, nombre_posicion
            FROM posiciones
            ORDER BY id_posicion;
        """;

        try (Connection conn = ConexionBD.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sqlPos)) {

            while (rs.next()) {
                int id = rs.getInt("id_posicion");
                String nombre = rs.getString("nombre_posicion");
                System.out.printf("ID=%d | %s%n", id, nombre);
            }

        } catch (SQLException e) {
            System.out.println("Error al listar posiciones: " + e.getMessage());
        }

        System.out.println();
        System.out.println("---- PRUEBA 4B: POSICIONES POR ATLETA ----");

        String sqlAtletaPos = """
            SELECT a.id_atleta, a.nombre, p.nombre_posicion
            FROM atleta_posicion ap
            JOIN atletas a   ON ap.id_atleta = a.id_atleta
            JOIN posiciones p ON ap.id_posicion = p.id_posicion
            ORDER BY a.id_atleta, p.id_posicion;
        """;

        try (Connection conn = ConexionBD.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sqlAtletaPos)) {

            while (rs.next()) {
                int idAtleta = rs.getInt("id_atleta");
                String nombreAtleta = rs.getString("nombre");
                String posicion = rs.getString("nombre_posicion");
                System.out.printf("Atleta #%d (%s) -> %s%n",
                        idAtleta, nombreAtleta, posicion);
            }

        } catch (SQLException e) {
            System.out.println("Error al listar atleta_posicion: " + e.getMessage());
        }
        System.out.println();
    }

    // ----------------------------------------------------
    // PRUEBA 5: MEDIDAS_ATLETA
    // ----------------------------------------------------
    private static void probarMedidasAtletas() {
        System.out.println("---- PRUEBA 5: MEDIDAS FISICAS DE LOS ATLETAS ----");

        String sql = """
            SELECT m.id_medicion, a.nombre, m.fecha_medicion,
                   m.estatura_cm, m.peso_kg, m.envergadura_cm,
                   m.salto_pasivo_cm, m.salto_activo_cm, m.distancia_bloqueo_cm
            FROM medidas_atleta m
            JOIN atletas a ON m.id_atleta = a.id_atleta
            ORDER BY a.id_atleta, m.fecha_medicion;
        """;

        try (Connection conn = ConexionBD.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                int idMedicion = rs.getInt("id_medicion");
                String nombre = rs.getString("nombre");
                String fecha = rs.getString("fecha_medicion");
                double est = rs.getDouble("estatura_cm");
                double peso = rs.getDouble("peso_kg");
                double env = rs.getDouble("envergadura_cm");
                double sp = rs.getDouble("salto_pasivo_cm");
                double sa = rs.getDouble("salto_activo_cm");
                double db = rs.getDouble("distancia_bloqueo_cm");

                System.out.printf(
                        "Medicion #%d | %s | %s -> est=%.1f cm, peso=%.1f kg, env=%.1f cm, saltoP=%.1f cm, saltoA=%.1f cm, distBloq=%.1f cm%n",
                        idMedicion, nombre, fecha, est, peso, env, sp, sa, db
                );
            }

        } catch (SQLException e) {
            System.out.println("Error al listar medidas: " + e.getMessage());
        }
        System.out.println();
    }

    // ----------------------------------------------------
    // PRUEBA 6: TORNEOS Y PARTICIPACION
    // ----------------------------------------------------
    private static void probarTorneosYParticipacion() {
        System.out.println("---- PRUEBA 6A: TORNEOS REGISTRADOS ----");

        String sqlTor = """
            SELECT id_torneo, nombre_torneo, es_nacional, anio
            FROM torneos
            ORDER BY id_torneo;
        """;

        try (Connection conn = ConexionBD.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sqlTor)) {

            while (rs.next()) {
                int id = rs.getInt("id_torneo");
                String nombre = rs.getString("nombre_torneo");
                String esNac = rs.getString("es_nacional");
                int anio = rs.getInt("anio");
                System.out.printf("Torneo #%d | %s | Nacional=%s | Anio=%d%n",
                        id, nombre, esNac, anio);
            }

        } catch (SQLException e) {
            System.out.println("Error al listar torneos: " + e.getMessage());
        }

        System.out.println();
        System.out.println("---- PRUEBA 6B: PARTICIPACION DE ATLETAS EN TORNEOS ----");

        String sqlPart = """
            SELECT a.nombre AS atleta, t.nombre_torneo, t.es_nacional, t.anio
            FROM participacion_torneo pt
            JOIN atletas a  ON pt.id_atleta = a.id_atleta
            JOIN torneos t  ON pt.id_torneo = t.id_torneo
            ORDER BY a.id_atleta, t.id_torneo;
        """;

        try (Connection conn = ConexionBD.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sqlPart)) {

            while (rs.next()) {
                String atleta = rs.getString("atleta");
                String torneo = rs.getString("nombre_torneo");
                String esNac = rs.getString("es_nacional");
                int anio = rs.getInt("anio");
                System.out.printf("%s -> %s (Nacional=%s, %d)%n",
                        atleta, torneo, esNac, anio);
            }

        } catch (SQLException e) {
            System.out.println("Error al listar participacion: " + e.getMessage());
        }
        System.out.println();
    }

    // ----------------------------------------------------
    // PRUEBA 7: MEMBRESIAS + PAGOS POR ATLETA
    // ----------------------------------------------------
    private static void probarMembresiasYPagosPorAtleta(
            MembresiaDAO membDAO,
            PagoMensualDAO pagoDAO,
            int idAtleta,
            int anio) {

        System.out.println("---- PRUEBA 7: MEMBRESIA Y PAGOS DEL ATLETA ID "
                + idAtleta + " PARA EL ANIO " + anio + " ----");

        Membresia mActiva = membDAO.obtenerMembresiaActiva(idAtleta);
        if (mActiva == null) {
            System.out.println("No hay membresia activa para ese atleta.");
            System.out.println();
            return;
        }

        System.out.println("Membresia activa:");
        System.out.println(mActiva);

        List<PagoMensual> pagos = pagoDAO.obtenerPagosPorMembresiaYAnio(
                mActiva.getIdMembresia(), anio);

        if (pagos.isEmpty()) {
            System.out.println("No hay pagos registrados para ese anio.");
        } else {
            System.out.println("Mes        | Estado      | Monto | FechaPago");
            for (PagoMensual p : pagos) {
                String mesNombre = nombreMes(p.getMes());
                System.out.println(
                        String.format("%-10s | %-10s | %5.2f | %s",
                                mesNombre,
                                p.getEstadoPago(),
                                p.getMonto(),
                                p.getFechaPago())
                );
            }
        }
        System.out.println();
    }

    // ----------------------------------------------------
    // PRUEBA 8: REGISTRAR/ACTUALIZAR UN PAGO Y VOLVER A LISTAR
    // ----------------------------------------------------
    private static void registrarYMostrarNuevoPago(
            MembresiaDAO membDAO,
            PagoMensualDAO pagoDAO,
            int idAtleta,
            int anio,
            int mes) {

        System.out.println("---- PRUEBA 8: REGISTRAR PAGO DE PRUEBA ----");
        System.out.printf("Atleta ID: %d | Anio: %d | Mes: %d%n", idAtleta, anio, mes);

        Membresia mActiva = membDAO.obtenerMembresiaActiva(idAtleta);
        if (mActiva == null) {
            System.out.println("No hay membresia activa, no se puede registrar el pago.");
            System.out.println();
            return;
        }

        boolean okPago = pagoDAO.registrarPago(
                mActiva.getIdMembresia(),
                mes,
                anio,
                10.0,              // monto de prueba
                "10/" + String.format("%02d", mes) + "/" + anio,
                "Pagado"
        );
        System.out.println("Pago registrado/actualizado correctamente? " + okPago);

        System.out.println();
        System.out.println("Pagos del anio " + anio
                + " despues de registrar/actualizar el mes " + mes + ":");

        List<PagoMensual> pagos = pagoDAO.obtenerPagosPorMembresiaYAnio(
                mActiva.getIdMembresia(), anio);

        if (pagos.isEmpty()) {
            System.out.println("No hay pagos registrados para ese anio.");
        } else {
            System.out.println("Mes        | Estado      | Monto | FechaPago");
            for (PagoMensual p : pagos) {
                String mesNombre = nombreMes(p.getMes());
                System.out.println(
                        String.format("%-10s | %-10s | %5.2f | %s",
                                mesNombre,
                                p.getEstadoPago(),
                                p.getMonto(),
                                p.getFechaPago())
                );
            }
        }
        System.out.println();
    }

    // ----------------------------------------------------
    // HELPER: convertir numero de mes (1-12) a nombre
    // ----------------------------------------------------
    private static String nombreMes(int mes) {
        switch (mes) {
            case 1:  return "Enero";
            case 2:  return "Febrero";
            case 3:  return "Marzo";
            case 4:  return "Abril";
            case 5:  return "Mayo";
            case 6:  return "Junio";
            case 7:  return "Julio";
            case 8:  return "Agosto";
            case 9:  return "Septiembre";
            case 10: return "Octubre";
            case 11: return "Noviembre";
            case 12: return "Diciembre";
            default: return "Mes " + mes;
        }
    }
}