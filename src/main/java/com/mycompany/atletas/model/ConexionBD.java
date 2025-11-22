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

    private static final String URL = "jdbc:sqlite:atletas.db";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL);
    }

    // ============================================================
    //  MODO REAL — PERSISTENCIA (NO BORRA DATOS)
    // ============================================================
    public static void inicializarBaseDatos() {
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {

            stmt.execute("PRAGMA foreign_keys = ON;");

            // ================= TABLAS =================
            stmt.executeUpdate("""
                CREATE TABLE IF NOT EXISTS categorias_edad (
                    id_categoria     INTEGER PRIMARY KEY AUTOINCREMENT,
                    nombre_categoria TEXT NOT NULL UNIQUE,
                    edad_min         INTEGER NOT NULL,
                    edad_max         INTEGER NOT NULL
                );
            """);

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

            stmt.executeUpdate("""
                CREATE TABLE IF NOT EXISTS atletas (
                    id_atleta                INTEGER PRIMARY KEY AUTOINCREMENT,
                    nombre                   TEXT NOT NULL,
                    cedula                   TEXT,
                    direccion                TEXT,
                    telefono                 TEXT,
                    correo                   TEXT,
                    fecha_nacimiento         TEXT,
                    id_entrenador            INTEGER,
                    id_categoria             INTEGER,
                    sexo                     TEXT CHECK(sexo IN ('M','F')),
                    lateralidad              TEXT CHECK(lateralidad IN ('Derecho','Zurdo')),
                    fecha_ingreso_asociacion TEXT,
                    estado                   TEXT CHECK(estado IN ('Activo','Inactivo')) DEFAULT 'Activo',
                    es_federado              TEXT CHECK(es_federado IN ('Si','No')) DEFAULT 'No',
                    FOREIGN KEY (id_entrenador) REFERENCES entrenadores(id_entrenador),
                    FOREIGN KEY (id_categoria) REFERENCES categorias_edad(id_categoria)
                );
            """);

            stmt.executeUpdate("""
                CREATE TABLE IF NOT EXISTS posiciones (
                    id_posicion     INTEGER PRIMARY KEY AUTOINCREMENT,
                    nombre_posicion TEXT NOT NULL
                );
            """);

            stmt.executeUpdate("""
                CREATE TABLE IF NOT EXISTS atleta_posicion (
                    id_atleta   INTEGER NOT NULL,
                    id_posicion INTEGER NOT NULL,
                    PRIMARY KEY (id_atleta, id_posicion),
                    FOREIGN KEY (id_atleta)   REFERENCES atletas(id_atleta),
                    FOREIGN KEY (id_posicion) REFERENCES posiciones(id_posicion)
                );
            """);

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

            stmt.executeUpdate("""
                CREATE TABLE IF NOT EXISTS torneos (
                    id_torneo      INTEGER PRIMARY KEY AUTOINCREMENT,
                    nombre_torneo  TEXT NOT NULL,
                    es_nacional    TEXT CHECK(es_nacional IN ('Si','No')),
                    anio           INTEGER
                );
            """);

            stmt.executeUpdate("""
                CREATE TABLE IF NOT EXISTS participacion_torneo (
                    id_atleta INTEGER NOT NULL,
                    id_torneo INTEGER NOT NULL,
                    PRIMARY KEY (id_atleta, id_torneo),
                    FOREIGN KEY (id_atleta) REFERENCES atletas(id_atleta),
                    FOREIGN KEY (id_torneo) REFERENCES torneos(id_torneo)
                );
            """);

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

            System.out.println("Base de datos lista (modo real, sin borrar datos).");

        } catch (SQLException e) {
            System.out.println("Error al inicializar BD: " + e.getMessage());
        }
    }

    // ============================================================
    //  MODO PRUEBAS — BORRA TODO Y RECARGA DESDE CERO
    // ============================================================
    public static void inicializarBaseDatosModoPruebas() {
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {

            stmt.execute("PRAGMA foreign_keys = ON;");

            // ==== BORRA TODO ====
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

            // === RECREA TODAS ===
            inicializarBaseDatos(); // llama la version sin DROP

            System.out.println("Base de datos lista (modo pruebas, datos eliminados).");

        } catch (SQLException e) {
            System.out.println("Error al reiniciar BD en modo pruebas: " + e.getMessage());
        }
    }

    // ============================================================
    //  DATOS DE EJEMPLO (SOLO PARA PRUEBAS)
    // ============================================================
    public static void insertarDatosEjemplo() {
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {

            stmt.executeUpdate("""
                INSERT INTO categorias_edad VALUES
                (1,'Mini',8,11),
                (2,'Infantil',12,13),
                (3,'Juvenil',14,17);
            """);

            stmt.executeUpdate("""
                INSERT INTO entrenadores VALUES
                (1,'Juan Perez Gomez','8888-0000','juan.perez@correo.com','L-1234','Juvenil'),
                (2,'Maria Salas Rojas','8999-1111','maria.salas@correo.com','L-5678','Infantil');
            """);

            // Posiciones actualizadas a voleibol
            stmt.executeUpdate("""
                INSERT INTO posiciones VALUES
                (1,'Central'),
                (2,'Armador'),
                (3,'Opuesto'),
                (4,'Punta'),
                (5,'Libero');
            """);

            stmt.executeUpdate("""
                INSERT INTO atletas VALUES
                (1,'Carlos Lopez Mora','1-1111-1111','Limon centro','8888-2222',
                'carlos.lopez@correo.com','10/05/2008',1,3,
                'M','Derecho','15/02/2023','Activo','Si'),

                (2,'Ana Rojas Mendez','2-2222-2222','Guapiles','8999-3333',
                'ana.rojas@correo.com','03/09/2010',2,2,
                'F','Zurdo','20/01/2024','Activo','No');
            """);

            stmt.executeUpdate("""
                INSERT INTO atleta_posicion VALUES
                (1,1),
                (1,2),
                (2,5);
            """);

            stmt.executeUpdate("""
                INSERT INTO medidas_atleta VALUES
                (1,1,'10/03/2024',185,72,192,45,58,30),
                (2,1,'10/03/2025',186,74,194,48,61,32);
            """);

            stmt.executeUpdate("""
                INSERT INTO torneos VALUES
                (1,'Juegos Nacionales 2024','Si',2024),
                (2,'Copa Caribe 2024','No',2024);
            """);

            stmt.executeUpdate("""
                INSERT INTO participacion_torneo VALUES
                (1,1),
                (1,2),
                (2,2);
            """);

            stmt.executeUpdate("""
                INSERT INTO membresias VALUES
                (1,1,'01/01/2023','','Activa'),
                (2,2,'01/01/2024','','Activa');
            """);

            stmt.executeUpdate("""
                INSERT INTO pagos_mensuales VALUES
                (1,1,1,2024,10,'05/01/2024','Pagado'),
                (2,1,2,2024,10,'20/02/2024','Pagado'),
                (3,1,3,2024,10,'','Atrasado'),
                (4,2,1,2024,10,'08/01/2024','Pagado');
            """);

            System.out.println("Datos de ejemplo insertados.");

        } catch (SQLException e) {
            System.out.println("Error al insertar datos de ejemplo: " + e.getMessage());
        }
    }
}