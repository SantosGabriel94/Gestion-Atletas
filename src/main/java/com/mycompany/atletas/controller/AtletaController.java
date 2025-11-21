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
    private final AtletaView view;
    private final Scanner scanner;

    public AtletaController() {
        this.dao = new AtletaDAO();
        this.view = new AtletaView();
        this.scanner = new Scanner(System.in);
    }

    // ==============================
    // CREATE — insertar atleta
    // ==============================
    public void crearAtleta() {
        System.out.println("--- Crear atleta ---");

        System.out.print("Nombre completo: ");
        String nombre = leerNoVacio();

        System.out.print("Cédula (9 dígitos, sin guiones): ");
        String cedula = leerCedulaCR();

        System.out.print("Dirección: ");
        String direccion = leerNoVacio();

        System.out.print("Teléfono (8 dígitos CR): ");
        String telefono = leerTelefonoCR();

        System.out.print("Correo: ");
        String correo = leerCorreo();

        String fechaNacimiento = leerFecha("Fecha de nacimiento (dd/mm/aaaa): ");

        // Mostrar entrenadores disponibles antes de pedir ID
        mostrarEntrenadoresDisponibles();
        System.out.print("ID del entrenador: ");
        int idEntrenador = leerEntero();

        // Mostrar categorías disponibles antes de pedir ID
        mostrarCategoriasDisponibles();
        System.out.print("ID de la categoría: ");
        int idCategoria = leerEntero();

        System.out.print("Sexo (M/F): ");
        String sexo = leerOpcion(new String[]{"M", "F"});

        System.out.print("Lateralidad (Derecho/Zurdo): ");
        String lateralidad = leerOpcion(new String[]{"Derecho", "Zurdo"});

        String fechaIngreso = leerFecha("Fecha ingreso a la asociación (dd/mm/aaaa): ");

        System.out.print("Estado (Activo/Inactivo): ");
        String estado = leerOpcion(new String[]{"Activo", "Inactivo"});

        System.out.print("¿Es federado? (Si/No): ");
        String esFederado = leerOpcion(new String[]{"Si", "No"});

        Atleta atleta = new Atleta(
                0,
                nombre,
                cedula,
                direccion,
                telefono,
                correo,
                fechaNacimiento,
                idEntrenador,
                idCategoria,
                sexo,
                lateralidad,
                fechaIngreso,
                estado,
                esFederado
        );

        boolean ok = dao.insertar(atleta);
        if (ok) {
            view.mostrarMensaje("Atleta guardado correctamente.");
        } else {
            view.mostrarMensaje("No se pudo guardar el atleta. Revise los datos.");
        }
    }

    // ==============================
    // READ — listar atletas
    // ==============================
    public void listarAtletas() {
        List<Atleta> atletas = dao.listarTodos();
        view.mostrarAtletas(atletas);
    }

    // ==============================
    // READ FILTRADO — buscar atleta
    // ==============================
    public void buscarAtleta() {
        System.out.println("--- Buscar atleta ---");
        System.out.print("Digite parte del nombre o la cédula: ");
        String patron = scanner.nextLine().trim();

        List<Atleta> resultados = dao.buscarPorNombreOCedula(patron);
        if (resultados.isEmpty()) {
            view.mostrarMensaje("No se encontraron atletas que coincidan.");
        } else {
            view.mostrarAtletas(resultados);
        }
    }

    // ==============================
    // UPDATE — actualizar atleta
    // ==============================
    public void actualizarAtleta() {
        System.out.println("--- Actualizar atleta ---");
        System.out.print("Ingrese el ID del atleta a actualizar: ");
        int id = leerEntero();

        Atleta actual = dao.buscarPorId(id);
        if (actual == null) {
            view.mostrarMensaje("No se encontró un atleta con ese ID.");
            return;
        }

        System.out.println("Datos actuales:");
        System.out.println(actual);
        System.out.println("Deje en blanco para mantener el valor actual.");

        // Nombre
        System.out.print("Nombre (" + actual.getNombre() + "): ");
        String nombre = scanner.nextLine();
        if (nombre.isBlank()) {
            nombre = actual.getNombre();
        } else {
            nombre = nombre.trim();
        }

        // Cédula con validación
        String cedula;
        while (true) {
            System.out.print("Cédula (" + actual.getCedula() + "): ");
            String entrada = scanner.nextLine().trim();
            if (entrada.isBlank()) {
                cedula = actual.getCedula();
                break;
            }
            if (Validador.esCedulaCRValida(entrada)) {
                cedula = entrada.replaceAll("[^0-9]", "");
                break;
            }
            view.mostrarMensaje("Cédula inválida. Debe tener 9 dígitos numéricos.");
        }

        // Dirección
        System.out.print("Dirección (" + actual.getDireccion() + "): ");
        String direccion = scanner.nextLine();
        if (direccion.isBlank()) {
            direccion = actual.getDireccion();
        } else {
            direccion = direccion.trim();
        }

        // Teléfono con validación
        String telefono;
        while (true) {
            System.out.print("Teléfono (" + actual.getTelefono() + "): ");
            String entrada = scanner.nextLine().trim();
            if (entrada.isBlank()) {
                telefono = actual.getTelefono();
                break;
            }
            if (Validador.esTelefonoCRValido(entrada)) {
                telefono = entrada.replaceAll("[^0-9]", "");
                break;
            }
            view.mostrarMensaje("Teléfono inválido. Debe ser de 8 dígitos CR y empezar en 2,4,5,6,7 u 8.");
        }

        // Correo con validación
        String correo;
        while (true) {
            System.out.print("Correo (" + actual.getCorreo() + "): ");
            String entrada = scanner.nextLine().trim();
            if (entrada.isBlank()) {
                correo = actual.getCorreo();
                break;
            }
            if (Validador.esCorreoValido(entrada)) {
                correo = entrada;
                break;
            }
            view.mostrarMensaje("Correo inválido. Use un formato como usuario@correo.com");
        }

        // Fecha nacimiento
        String fechaNacimiento;
        while (true) {
            System.out.print("Fecha nacimiento (" + actual.getFechaNacimiento() + ") [dd/mm/aaaa]: ");
            String entrada = scanner.nextLine().trim();
            if (entrada.isBlank()) {
                fechaNacimiento = actual.getFechaNacimiento();
                break;
            }
            if (Validador.esFechaValida(entrada)) {
                fechaNacimiento = entrada;
                break;
            }
            view.mostrarMensaje("Fecha inválida. Use formato dd/mm/aaaa.");
        }

        // ID entrenador
        System.out.print("ID entrenador (" + actual.getIdEntrenador() + "): ");
        String lineaEnt = scanner.nextLine();
        int idEntrenador = lineaEnt.isBlank()
                ? actual.getIdEntrenador()
                : parseOrDefault(lineaEnt, actual.getIdEntrenador());

        // ID categoría
        System.out.print("ID categoría (" + actual.getIdCategoria() + "): ");
        String lineaCat = scanner.nextLine();
        int idCategoria = lineaCat.isBlank()
                ? actual.getIdCategoria()
                : parseOrDefault(lineaCat, actual.getIdCategoria());

        // Sexo
        System.out.print("Sexo (" + actual.getSexo() + ") [M/F]: ");
        String sexoIn = scanner.nextLine().trim();
        String sexo = sexoIn.isBlank() ? actual.getSexo()
                : leerOpcionDesdeValorInicial(sexoIn, new String[]{"M", "F"}, actual.getSexo());

        // Lateralidad
        System.out.print("Lateralidad (" + actual.getLateralidad() + ") [Derecho/Zurdo]: ");
        String latIn = scanner.nextLine().trim();
        String lateralidad = latIn.isBlank() ? actual.getLateralidad()
                : leerOpcionDesdeValorInicial(latIn, new String[]{"Derecho", "Zurdo"}, actual.getLateralidad());

        // Fecha ingreso
        String fechaIngreso;
        while (true) {
            System.out.print("Fecha ingreso (" + actual.getFechaIngresoAsociacion() + ") [dd/mm/aaaa]: ");
            String entrada = scanner.nextLine().trim();
            if (entrada.isBlank()) {
                fechaIngreso = actual.getFechaIngresoAsociacion();
                break;
            }
            if (Validador.esFechaValida(entrada)) {
                fechaIngreso = entrada;
                break;
            }
            view.mostrarMensaje("Fecha inválida. Use formato dd/mm/aaaa.");
        }

        // Estado
        System.out.print("Estado (" + actual.getEstado() + ") [Activo/Inactivo]: ");
        String estIn = scanner.nextLine().trim();
        String estado = estIn.isBlank() ? actual.getEstado()
                : leerOpcionDesdeValorInicial(estIn, new String[]{"Activo", "Inactivo"}, actual.getEstado());

        // Es federado
        System.out.print("Es federado (" + actual.getEsFederado() + ") [Si/No]: ");
        String fedIn = scanner.nextLine().trim();
        String esFederado = fedIn.isBlank() ? actual.getEsFederado()
                : leerOpcionDesdeValorInicial(fedIn, new String[]{"Si", "No"}, actual.getEsFederado());

        Atleta actualizado = new Atleta(
                id,
                nombre,
                cedula,
                direccion,
                telefono,
                correo,
                fechaNacimiento,
                idEntrenador,
                idCategoria,
                sexo,
                lateralidad,
                fechaIngreso,
                estado,
                esFederado
        );

        boolean ok = dao.actualizar(actualizado);
        if (ok) {
            view.mostrarMensaje("Atleta actualizado correctamente.");
        } else {
            view.mostrarMensaje("No se pudo actualizar el atleta.");
        }
    }

    // ==============================
    // DELETE — eliminar atleta
    // ==============================
    public void eliminarAtleta() {
        System.out.println("--- Eliminar atleta ---");
        System.out.print("Ingrese el ID del atleta a eliminar: ");
        int id = leerEntero();

        Atleta actual = dao.buscarPorId(id);
        if (actual == null) {
            view.mostrarMensaje("No existe un atleta con ese ID.");
            return;
        }

        System.out.println("Se eliminará el siguiente atleta:");
        System.out.println(actual);
        System.out.print("¿Está seguro? (s/n): ");
        String resp = scanner.nextLine().trim().toLowerCase();
        if (!resp.equals("s")) {
            view.mostrarMensaje("Operación cancelada.");
            return;
        }

        boolean ok = dao.eliminar(id);
        if (ok) {
            view.mostrarMensaje("Atleta eliminado correctamente.");
        } else {
            view.mostrarMensaje("No se pudo eliminar el atleta.");
        }
    }

    // ==============================
    // AUXILIARES (entrenadores / categorías)
    // ==============================
    public void mostrarEntrenadoresDisponibles() {
        System.out.println("\n=== ENTRENADORES DISPONIBLES ===");
        String sql = "SELECT id_entrenador, nombre FROM entrenadores;";
        try (Connection conn = ConexionBD.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                System.out.println(rs.getInt("id_entrenador") + " - " + rs.getString("nombre"));
            }

        } catch (SQLException e) {
            System.out.println("Error al listar entrenadores: " + e.getMessage());
        }
    }

    public void mostrarCategoriasDisponibles() {
        System.out.println("\n=== CATEGORÍAS DISPONIBLES ===");
        String sql = "SELECT id_categoria, nombre_categoria, edad_min, edad_max FROM categorias_edad;";
        try (Connection conn = ConexionBD.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                System.out.println(
                        rs.getInt("id_categoria") + " - " +
                                rs.getString("nombre_categoria") + " (" +
                                rs.getInt("edad_min") + "-" +
                                rs.getInt("edad_max") + " años)"
                );
            }

        } catch (SQLException e) {
            System.out.println("Error al listar categorías: " + e.getMessage());
        }
    }

    // ==============================
    // UTILIDADES / VALIDACIÓN
    // ==============================

    private int leerEntero() {
        while (!scanner.hasNextInt()) {
            scanner.nextLine();
            view.mostrarMensaje("Ingrese un número válido:");
        }
        int valor = scanner.nextInt();
        scanner.nextLine(); // limpiar buffer
        return valor;
    }

    private int parseOrDefault(String texto, int valorActual) {
        try {
            return Integer.parseInt(texto.trim());
        } catch (NumberFormatException e) {
            return valorActual;
        }
    }

    private String leerNoVacio() {
        String texto;
        do {
            texto = scanner.nextLine().trim();
            if (texto.isEmpty()) {
                view.mostrarMensaje("Este campo no puede estar vacío. Intente de nuevo:");
            }
        } while (texto.isEmpty());
        return texto;
    }

    private String leerCedulaCR() {
        while (true) {
            String entrada = scanner.nextLine().trim();
            if (Validador.esCedulaCRValida(entrada)) {
                return entrada.replaceAll("[^0-9]", "");
            }
            view.mostrarMensaje("Cédula inválida. Debe tener 9 dígitos numéricos. Intente de nuevo:");
        }
    }

    private String leerTelefonoCR() {
        while (true) {
            String entrada = scanner.nextLine().trim();
            if (Validador.esTelefonoCRValido(entrada)) {
                return entrada.replaceAll("[^0-9]", "");
            }
            view.mostrarMensaje("Teléfono inválido. Debe ser de 8 dígitos CR y empezar en 2,4,5,6,7 u 8. Intente de nuevo:");
        }
    }

    private String leerCorreo() {
        while (true) {
            String entrada = scanner.nextLine().trim();
            if (Validador.esCorreoValido(entrada)) {
                return entrada;
            }
            view.mostrarMensaje("Correo inválido. Intente de nuevo (ejemplo: usuario@correo.com):");
        }
    }

    private String leerOpcion(String[] validos) {
        while (true) {
            String entrada = scanner.nextLine().trim();
            for (String v : validos) {
                if (entrada.equalsIgnoreCase(v)) {
                    return v;
                }
            }
            StringBuilder sb = new StringBuilder("Valor inválido. Opciones válidas: ");
            for (int i = 0; i < validos.length; i++) {
                sb.append(validos[i]);
                if (i < validos.length - 1) sb.append(" / ");
            }
            view.mostrarMensaje(sb.toString());
            view.mostrarMensaje("Intente de nuevo:");
        }
    }

    private String leerFecha(String etiqueta) {
        while (true) {
            System.out.print(etiqueta);
            String entrada = scanner.nextLine().trim();
            if (Validador.esFechaValida(entrada)) {
                return entrada;
            }
            view.mostrarMensaje("Fecha inválida. Use el formato dd/mm/aaaa, por ejemplo 05/11/2025.");
        }
    }

    private String leerOpcionDesdeValorInicial(String entradaInicial, String[] validos, String valorActual) {
        String entrada = entradaInicial;
        while (true) {
            for (String v : validos) {
                if (entrada.equalsIgnoreCase(v)) {
                    return v;
                }
            }
            StringBuilder sb = new StringBuilder("Valor inválido. Opciones válidas: ");
            for (int i = 0; i < validos.length; i++) {
                sb.append(validos[i]);
                if (i < validos.length - 1) sb.append(" / ");
            }
            view.mostrarMensaje(sb.toString());
            view.mostrarMensaje("Intente de nuevo (o deje en blanco para mantener el valor actual):");
            entrada = scanner.nextLine().trim();
            if (entrada.isBlank()) {
                return valorActual;
            }
        }
    }
}