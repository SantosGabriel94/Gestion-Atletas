/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.atletas.model;

import com.mycompany.atletas.model.Entrenador;
import com.mycompany.atletas.model.EntrenadorDAO;
import com.mycompany.atletas.util.Validador;

import java.util.List;

/**
 * Lógica de negocio para Entrenador:
 * - Validar datos antes de guardar
 * 
 */
public class EntrenadorService {

    private final EntrenadorDAO dao;

    public EntrenadorService() {
        this.dao = new EntrenadorDAO();
    }

    // =========================================
    // CREAR ENTRENADOR
    // =========================================
    /**
     * Crea un nuevo entrenador solo si los datos son válidos.
     * @return null si todo bien, o un mensaje de error si algo está mal.
     */
    public String crearEntrenador(String nombre,
                                  String telefono,
                                  String correo,
                                  String licenciaFederacion,
                                  String categoriaLicencia) {

        // Validaciones básicas
        if (nombre == null || nombre.trim().isEmpty()) {
            return "El nombre del entrenador no puede estar vacío.";
        }

        if (telefono != null && !telefono.trim().isEmpty()) {
            if (!Validador.esTelefonoCRValido(telefono)) {
                return "El teléfono del entrenador no es válido (8 dígitos CR).";
            }
        }

        if (correo != null && !correo.trim().isEmpty()) {
            if (!Validador.esCorreoValido(correo)) {
                return "El correo del entrenador no tiene un formato válido.";
            }
        }

        if (licenciaFederacion == null || licenciaFederacion.trim().isEmpty()) {
            return "La licencia de federación no puede estar vacía.";
        }

        // categoriaLicencia puede ser opcional, pero si viene, limpiamos espacios
        String categoriaLimpia = (categoriaLicencia == null)
                ? ""
                : categoriaLicencia.trim();

        // Si todo está bien, construimos el objeto y lo guardamos
        Entrenador e = new Entrenador(
                0,                        // id_entrenador (lo genera la BD)
                nombre.trim(),
                telefono == null ? "" : telefono.trim(),
                correo == null ? "" : correo.trim(),
                licenciaFederacion.trim(),
                categoriaLimpia
        );

        boolean ok = dao.insertar(e);
        if (!ok) {
            return "No se pudo guardar el entrenador en la base de datos.";
        }

        // null = ningún error
        return null;
    }

    // =========================================
    // ACTUALIZAR ENTRENADOR
    // =========================================
    /**
     * Actualiza un entrenador existente.
     * @return null si todo bien, o mensaje de error si algo está mal.
     */
    public String actualizarEntrenador(Entrenador existente,
                                       String nuevoNombre,
                                       String nuevoTelefono,
                                       String nuevoCorreo,
                                       String nuevaLicenciaFederacion,
                                       String nuevaCategoriaLicencia) {

        if (existente == null) {
            return "El entrenador a actualizar no existe.";
        }

        if (nuevoNombre == null || nuevoNombre.trim().isEmpty()) {
            return "El nombre del entrenador no puede estar vacío.";
        }

        if (nuevoTelefono != null && !nuevoTelefono.trim().isEmpty()) {
            if (!Validador.esTelefonoCRValido(nuevoTelefono)) {
                return "El teléfono del entrenador no es válido (8 dígitos CR).";
            }
        }

        if (nuevoCorreo != null && !nuevoCorreo.trim().isEmpty()) {
            if (!Validador.esCorreoValido(nuevoCorreo)) {
                return "El correo del entrenador no tiene un formato válido.";
            }
        }

        if (nuevaLicenciaFederacion == null || nuevaLicenciaFederacion.trim().isEmpty()) {
            return "La licencia de federación no puede estar vacía.";
        }

        // Actualizamos el objeto en memoria
        existente.setNombre(nuevoNombre.trim());
        existente.setTelefono(nuevoTelefono == null ? "" : nuevoTelefono.trim());
        existente.setCorreo(nuevoCorreo == null ? "" : nuevoCorreo.trim());
        existente.setLicenciaFederacion(nuevaLicenciaFederacion.trim());
        existente.setCategoriaLicencia(
                nuevaCategoriaLicencia == null ? "" : nuevaCategoriaLicencia.trim()
        );

        boolean ok = dao.actualizar(existente);
        if (!ok) {
            return "No se pudo actualizar el entrenador en la base de datos.";
        }

        return null;
    }

    // =========================================
    // LECTURAS (para consola / interfaz)
    // =========================================

    public List<Entrenador> listarTodos() {
        return dao.listarTodos();
    }

    public Entrenador buscarPorId(int id) {
        return dao.buscarPorId(id);
    }

    public boolean eliminar(int id) {
        return dao.eliminar(id);
    }
}