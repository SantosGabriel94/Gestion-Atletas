/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.atletas.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 *
 * @author Admin
 */
public class Validador {

    private static final DateTimeFormatter FORMAT_DDMMYYYY =
            DateTimeFormatter.ofPattern("dd/MM/yyyy");

    // Cédula CR: 9 dígitos numéricos
    public static boolean esCedulaCRValida(String cedula) {
        if (cedula == null) return false;
        String limpia = cedula.trim().replaceAll("[^0-9]", "");
        return limpia.matches("\\d{9}");
    }

    // Teléfono CR: 8 dígitos, empieza en 2,4,5,6,7 u 8
    public static boolean esTelefonoCRValido(String tel) {
        if (tel == null) return false;
        String limpia = tel.trim().replaceAll("[^0-9]", "");
        return limpia.matches("[245678]\\d{7}");
    }

    // Correo con formato básico válido
    public static boolean esCorreoValido(String correo) {
        if (correo == null) return false;
        String c = correo.trim();
        return c.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
    }

    // Fecha exactamente en formato dd/MM/yyyy y FECHA REAL
    public static boolean esFechaValida(String fechaDDMMYYYY) {
        if (fechaDDMMYYYY == null) return false;
        String f = fechaDDMMYYYY.trim();
        try {
            LocalDate.parse(f, FORMAT_DDMMYYYY);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }
}
