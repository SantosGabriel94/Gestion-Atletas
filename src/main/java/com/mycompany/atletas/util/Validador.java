/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.atletas.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;
import java.time.format.DateTimeParseException;

/**
 *
 * @author Admin
 */
public class Validador {

    // Cédula CR: 9 dígitos (ej: 123456789)
    public static boolean esCedulaCRValida(String cedula) {
        if (cedula == null) return false;
        String limpia = cedula.trim().replaceAll("[^0-9]", "");
        return limpia.matches("\\d{9}");
    }

    // Teléfono CR: 8 dígitos que empiezan en 2,4,5,6,7 u 8
    public static boolean esTelefonoCRValido(String telefono) {
        if (telefono == null) return false;
        String limpia = telefono.trim().replaceAll("[^0-9]", "");
        return limpia.matches("[245678]\\d{7}");
    }

    // Correo electrónico: formato general profesional
    public static boolean esCorreoValido(String correo) {
        if (correo == null) return false;
        String c = correo.trim();
        return c.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
    }

    // Fecha estricta dd/MM/yyyy (no acepta 31/11, 30/02, etc.)
    public static boolean esFechaValida(String fecha) {
        if (fecha == null) return false;
        String f = fecha.trim();

        DateTimeFormatter formatter = DateTimeFormatter
                .ofPattern("dd/MM/uuuu")
                .withResolverStyle(ResolverStyle.STRICT);

        try {
            LocalDate.parse(f, formatter);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }
}