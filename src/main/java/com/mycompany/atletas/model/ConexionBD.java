/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.atletas.model;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Admin
 */
public class ConexionBD {

    // Archivo de la base de datos SQLite (se crea en la carpeta del proyecto)
    private static final String URL = "jdbc:sqlite:atletas.db";

    // Devuelve una conexión a la base de datos
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL);
    }

    // Crea la tabla con la estructura definitiva
    public static void inicializarBaseDatos() {
    try (Connection conn = getConnection();
         Statement stmt = conn.createStatement()) {

        // Muy importante en SQLite para que funcionen los FOREIGN KEY
        stmt.execute("PRAGMA foreign_keys = ON;");

        // OJO: Estos DROP borran datos, úsalos solo en desarrollo
        stmt.executeUpdate("DROP TABLE IF EXISTS pagos_mensuales;");
        stmt.executeUpdate("DROP TABLE IF EXISTS membresias;");
        stmt.executeUpdate("DROP TABLE IF EXISTS participacion_torneo;");
        stmt.executeUpdate("DROP TABLE IF EXISTS medidas_atleta;");
        stmt.executeUpdate("DROP TABLE IF EXISTS atleta_posicion;");
        stmt.executeUpdate("DROP TABLE IF EXISTS torneos;");
        stmt.executeUpdate("DROP TABLE IF EXISTS posiciones;");
        stmt.executeUpdate("DROP TABLE IF EXISTS atletas;");
        stmt.executeUpdate("DROP TABLE IF EXISTS entrenadores;");
        stmt.executeUpdate("DROP TABLE IF EXISTS categorias_edad;");

        // ====== CATEGORIAS_EDAD ======
        stmt.executeUpdate("""
            CREATE TABLE IF NOT EXISTS categorias_edad (
                id_categoria     INTEGER PRIMARY KEY AUTOINCREMENT,
                nombre_categoria TEXT    NOT NULL UNIQUE,
                edad_min         INTEGER NOT NULL,
                edad_max         INTEGER NOT NULL
            );
        """);

        // ====== ENTRENADORES ======
        stmt.executeUpdate("""
            CREATE TABLE IF NOT EXISTS entrenadores (
                id_entrenador       INTEGER PRIMARY KEY AUTOINCREMENT,
                nombre              TEXT NOT NULL,
                telefono            TEXT,
                correo              TEXT,
                licencia_federacion TEXT,
                categoria_licencia  TEXT
            );
        """);

        // ====== ATLETAS ======
        stmt.executeUpdate("""
            CREATE TABLE IF NOT EXISTS atletas (
                id_atleta               INTEGER PRIMARY KEY AUTOINCREMENT,
                nombre                  TEXT NOT NULL,
                cedula                  TEXT,
                direccion               TEXT,
                telefono                TEXT,
                correo                  TEXT,
                fecha_nacimiento        TEXT,
                id_entrenador           INTEGER,
                id_categoria            INTEGER,
                sexo                    TEXT CHECK(sexo IN ('M','F')) ,
                lateralidad             TEXT CHECK(lateralidad IN ('Derecho','Zurdo')),
                fecha_ingreso_asociacion TEXT,
                estado                  TEXT CHECK(estado IN ('Activo','Inactivo')) DEFAULT 'Activo',
                es_federado             TEXT CHECK(es_federado IN ('Si','No'))     DEFAULT 'No',
                FOREIGN KEY (id_entrenador) REFERENCES entrenadores(id_entrenador),
                FOREIGN KEY (id_categoria)  REFERENCES categorias_edad(id_categoria)
            );
        """);

        // ====== POSICIONES ======
        stmt.executeUpdate("""
            CREATE TABLE IF NOT EXISTS posiciones (
                id_posicion     INTEGER PRIMARY KEY AUTOINCREMENT,
                nombre_posicion TEXT NOT NULL
            );
        """);

        // ====== ATLETA_POSICION (muchos a muchos) ======
        stmt.executeUpdate("""
            CREATE TABLE IF NOT EXISTS atleta_posicion (
                id_atleta   INTEGER NOT NULL,
                id_posicion INTEGER NOT NULL,
                PRIMARY KEY (id_atleta, id_posicion),
                FOREIGN KEY (id_atleta)   REFERENCES atletas(id_atleta),
                FOREIGN KEY (id_posicion) REFERENCES posiciones(id_posicion)
            );
        """);

        // ====== MEDIDAS_ATLETA ======
        stmt.executeUpdate("""
            CREATE TABLE IF NOT EXISTS medidas_atleta (
                id_medicion          INTEGER PRIMARY KEY AUTOINCREMENT,
                id_atleta            INTEGER NOT NULL,
                fecha_medicion       TEXT,
                estatura_cm          REAL,
                peso_kg              REAL,
                envergadura_cm       REAL,
                salto_pasivo_cm      REAL,
                salto_activo_cm      REAL,
                distancia_bloqueo_cm REAL,
                FOREIGN KEY (id_atleta) REFERENCES atletas(id_atleta)
            );
        """);

        // ====== TORNEOS ======
        stmt.executeUpdate("""
            CREATE TABLE IF NOT EXISTS torneos (
                id_torneo      INTEGER PRIMARY KEY AUTOINCREMENT,
                nombre_torneo  TEXT NOT NULL,
                es_nacional    TEXT CHECK(es_nacional IN ('Si','No')),
                anio           INTEGER
            );
        """);

        // ====== PARTICIPACION_TORNEO ======
        stmt.executeUpdate("""
            CREATE TABLE IF NOT EXISTS participacion_torneo (
                id_atleta INTEGER NOT NULL,
                id_torneo INTEGER NOT NULL,
                PRIMARY KEY (id_atleta, id_torneo),
                FOREIGN KEY (id_atleta) REFERENCES atletas(id_atleta),
                FOREIGN KEY (id_torneo) REFERENCES torneos(id_torneo)
            );
        """);

        // ====== MEMBRESIAS ======
        stmt.executeUpdate("""
            CREATE TABLE IF NOT EXISTS membresias (
                id_membresia INTEGER PRIMARY KEY AUTOINCREMENT,
                id_atleta    INTEGER NOT NULL,
                fecha_inicio TEXT,
                fecha_fin    TEXT,
                estado       TEXT,
                FOREIGN KEY (id_atleta) REFERENCES atletas(id_atleta)
            );
        """);

        // ====== PAGOS_MENSUALES ======
        stmt.executeUpdate("""
            CREATE TABLE IF NOT EXISTS pagos_mensuales (
                id_pago      INTEGER PRIMARY KEY AUTOINCREMENT,
                id_membresia INTEGER NOT NULL,
                mes          INTEGER NOT NULL,
                anio         INTEGER NOT NULL,
                monto        REAL,
                fecha_pago   TEXT,
                estado_pago  TEXT CHECK(estado_pago IN ('Pagado','Pendiente','Atrasado')) DEFAULT 'Pendiente',
                FOREIGN KEY (id_membresia) REFERENCES membresias(id_membresia),
                UNIQUE(id_membresia, anio, mes)
            );
        """);

        System.out.println("Base de datos lista con todas las tablas normalizadas.");

    } catch (SQLException e) {
        System.out.println("Error al inicializar la base de datos: " + e.getMessage());
    }
}
    
        // Inserta datos de ejemplo basados en el Excel
    public static void insertarDatosEjemplo() {
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {

            // ===== CATEGORIAS_EDAD =====
            stmt.executeUpdate("""
                INSERT INTO categorias_edad (id_categoria, nombre_categoria, edad_min, edad_max) VALUES
                (1, 'Mini',     8, 11),
                (2, 'Infantil', 12, 13),
                (3, 'Juvenil',  14, 17);
            """);

            // ===== ENTRENADORES =====
            stmt.executeUpdate("""
                INSERT INTO entrenadores (id_entrenador, nombre, telefono, correo, licencia_federacion, categoria_licencia) VALUES
                (1, 'Juan Pérez Gómez',  '8888-0000', 'juan.perez@correo.com', 'L-1234', 'Juvenil'),
                (2, 'María Salas Rojas', '8999-1111', 'maria.salas@correo.com', 'L-5678', 'Infantil');
            """);

            // ===== POSICIONES =====
            stmt.executeUpdate("""
                INSERT INTO posiciones (id_posicion, nombre_posicion) VALUES
                (1, 'Central'),
                (2, 'Armador'),
                (3, 'Libero');
            """);

            // ===== ATLETAS =====
            stmt.executeUpdate("""
                INSERT INTO atletas (
                    id_atleta, nombre, cedula, direccion, telefono, correo,
                    fecha_nacimiento, id_entrenador, id_categoria,
                    sexo, lateralidad, fecha_ingreso_asociacion, estado, es_federado
                ) VALUES
                (1, 'Carlos López Mora', '1-1111-1111', 'Limón centro', '8888-2222',
                 'carlos.lopez@correo.com', '10/05/2008', 1, 3,
                 'M', 'Derecho', '15/02/2023', 'Activo', 'Si'),
                (2, 'Ana Rojas Méndez', '2-2222-2222', 'Guápiles', '8999-3333',
                 'ana.rojas@correo.com', '03/09/2010', 2, 2,
                 'F', 'Zurdo', '20/01/2024', 'Activo', 'No');
            """);

            // ===== ATLETA_POSICION =====
            stmt.executeUpdate("""
                INSERT INTO atleta_posicion (id_atleta, id_posicion) VALUES
                (1, 1),
                (1, 2),
                (2, 3);
            """);

            // ===== MEDIDAS_ATLETA =====
            stmt.executeUpdate("""
                INSERT INTO medidas_atleta (
                    id_medicion, id_atleta, fecha_medicion,
                    estatura_cm, peso_kg, envergadura_cm,
                    salto_pasivo_cm, salto_activo_cm, distancia_bloqueo_cm
                ) VALUES
                (1, 1, '10/03/2024', 185, 72, 192, 45, 58, 30),
                (2, 1, '10/03/2025', 186, 74, 194, 48, 61, 32);
            """);

            // ===== TORNEOS =====
            stmt.executeUpdate("""
                INSERT INTO torneos (id_torneo, nombre_torneo, es_nacional, anio) VALUES
                (1, 'Juegos Nacionales 2024', 'Si', 2024),
                (2, 'Copa Caribe 2024',       'No', 2024);
            """);

            // ===== PARTICIPACION_TORNEO =====
            stmt.executeUpdate("""
                INSERT INTO participacion_torneo (id_atleta, id_torneo) VALUES
                (1, 1),
                (1, 2),
                (2, 2);
            """);

            // ===== MEMBRESIAS =====
            stmt.executeUpdate("""
                INSERT INTO membresias (id_membresia, id_atleta, fecha_inicio, fecha_fin, estado) VALUES
                (1, 1, '01/01/2023', '', 'Activa'),
                (2, 2, '01/01/2024', '', 'Activa');
            """);

            // ===== PAGOS_MENSUALES =====
            stmt.executeUpdate("""
                INSERT INTO pagos_mensuales (
                    id_pago, id_membresia, mes, anio, monto, fecha_pago, estado_pago
                ) VALUES
                (1, 1, 1, 2024, 10, '05/01/2024', 'Pagado'),
                (2, 1, 2, 2024, 10, '20/02/2024', 'Pagado'),
                (3, 1, 3, 2024, 10, '',           'Atrasado'),
                (4, 2, 1, 2024, 10, '08/01/2024', 'Pagado');
            """);

            System.out.println("Datos de ejemplo insertados correctamente.");

        } catch (SQLException e) {
            System.out.println("Error al insertar datos de ejemplo: " + e.getMessage());
        }
    }

    
 }