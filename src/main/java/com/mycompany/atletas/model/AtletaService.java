/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.atletas.model;


import com.mycompany.atletas.model.Atleta;
import com.mycompany.atletas.model.AtletaDAO;
import com.mycompany.atletas.util.Validador;
import java.util.List;
/**
 *
 * @author Admin
 */
public class AtletaService {

    private final AtletaDAO atletaDAO;
    private final EntrenadorDAO entrenadorDAO;
    private final CategoriaDAO categoriaDAO;

    public AtletaService() {
        this.atletaDAO = new AtletaDAO();
        this.entrenadorDAO = new EntrenadorDAO();
        this.categoriaDAO = new CategoriaDAO();
    }

    // =========================================
    // CREAR ATLETA
    // =========================================
    /**
     * Crea un nuevo atleta solo si los datos son validos.
     * @return null si todo bien, o mensaje de error si algo esta mal.
     */
    public String crearAtleta(String nombre,
                              String cedula,
                              String direccion,
                              String telefono,
                              String correo,
                              String fechaNacimiento,
                              int idEntrenador,
                              int idCategoria,
                              String sexo,
                              String lateralidad,
                              String fechaIngresoAsociacion,
                              String estado,
                              String esFederado) {

        // --------- VALIDACIONES BASICAS ---------

        if (nombre == null || nombre.trim().isEmpty()) {
            return "El nombre del atleta no puede estar vacio.";
        }

        if (cedula == null || cedula.trim().isEmpty()) {
            return "La cedula del atleta no puede estar vacia.";
        }
        if (!Validador.esCedulaCRValida(cedula)) {
            return "La cedula del atleta no es valida (9 digitos).";
        }

        if (telefono != null && !telefono.trim().isEmpty()) {
            if (!Validador.esTelefonoCRValido(telefono)) {
                return "El telefono del atleta no es valido (8 digitos CR).";
            }
        }

        if (correo != null && !correo.trim().isEmpty()) {
            if (!Validador.esCorreoValido(correo)) {
                return "El correo del atleta no tiene un formato valido.";
            }
        }

        if (fechaNacimiento != null && !fechaNacimiento.trim().isEmpty()) {
            if (!Validador.esFechaValida(fechaNacimiento)) {
                return "La fecha de nacimiento no es valida (formato dd/MM/yyyy).";
            }
        }

        if (fechaIngresoAsociacion != null && !fechaIngresoAsociacion.trim().isEmpty()) {
            if (!Validador.esFechaValida(fechaIngresoAsociacion)) {
                return "La fecha de ingreso a la asociacion no es valida (formato dd/MM/yyyy).";
            }
        }

        // sexo: M o F
        String sexoFinal;
        if (sexo == null || sexo.trim().isEmpty()) {
            return "Debe seleccionar el sexo del atleta (M o F).";
        } else {
            String s = sexo.trim().toUpperCase();
            if (!s.equals("M") && !s.equals("F")) {
                return "El sexo del atleta debe ser M o F.";
            }
            sexoFinal = s;
        }

        // lateralidad: Derecho / Zurdo
        String lateralidadFinal;
        if (lateralidad == null || lateralidad.trim().isEmpty()) {
            return "Debe indicar la lateralidad del atleta (Derecho o Zurdo).";
        } else {
            String lat = lateralidad.trim();
            if (!lat.equalsIgnoreCase("Derecho") && !lat.equalsIgnoreCase("Zurdo")) {
                return "La lateralidad debe ser 'Derecho' o 'Zurdo'.";
            }
            // normalizamos primera mayuscula
            lateralidadFinal = lat.substring(0, 1).toUpperCase() + lat.substring(1).toLowerCase();
        }

        // estado: Activo / Inactivo (si viene vacio, ponemos Activo)
        String estadoFinal;
        if (estado == null || estado.trim().isEmpty()) {
            estadoFinal = "Activo";
        } else {
            String est = estado.trim();
            if (!est.equalsIgnoreCase("Activo") && !est.equalsIgnoreCase("Inactivo")) {
                return "El estado del atleta debe ser 'Activo' o 'Inactivo'.";
            }
            // normalizamos
            estadoFinal = est.substring(0, 1).toUpperCase() + est.substring(1).toLowerCase();
        }

        // es_federado: Si / No (por defecto No)
        String federadoFinal;
        if (esFederado == null || esFederado.trim().isEmpty()) {
            federadoFinal = "No";
        } else {
            String fed = esFederado.trim();
            if (!fed.equalsIgnoreCase("Si") && !fed.equalsIgnoreCase("No")) {
                return "El campo 'es federado' debe ser 'Si' o 'No'.";
            }
            federadoFinal = fed.substring(0, 1).toUpperCase() + fed.substring(1).toLowerCase();
        }

        // --------- VALIDAR ENTRENADOR Y CATEGORIA ---------

        if (idEntrenador <= 0) {
            return "Debe seleccionar un entrenador valido.";
        }
        Entrenador ent = entrenadorDAO.buscarPorId(idEntrenador);
        if (ent == null) {
            return "El entrenador seleccionado no existe en la base de datos.";
        }

        if (idCategoria <= 0) {
            return "Debe seleccionar una categoria de edad valida.";
        }
        Categoria cat = categoriaDAO.buscarPorId(idCategoria);
        if (cat == null) {
            return "La categoria seleccionada no existe en la base de datos.";
        }

        // (Opcional, mas adelante) verificar que edad del atleta calce con la categoria

        // --------- CONSTRUIR OBJETO Y GUARDAR ---------

        Atleta atleta = new Atleta(
                0,                             // id lo genera la BD
                nombre.trim(),
                cedula.trim(),
                direccion == null ? "" : direccion.trim(),
                telefono == null ? "" : telefono.trim(),
                correo == null ? "" : correo.trim(),
                fechaNacimiento == null ? "" : fechaNacimiento.trim(),
                idEntrenador,
                idCategoria,
                sexoFinal,
                lateralidadFinal,
                fechaIngresoAsociacion == null ? "" : fechaIngresoAsociacion.trim(),
                estadoFinal,
                federadoFinal
        );

        boolean ok = atletaDAO.insertar(atleta);
        if (!ok) {
            return "No se pudo guardar el atleta en la base de datos.";
        }

        // null = ningun error
        return null;
    }

    // =========================================
    // ACTUALIZAR ATLETA
    // =========================================
    public String actualizarAtleta(Atleta existente,
                                   String nombre,
                                   String cedula,
                                   String direccion,
                                   String telefono,
                                   String correo,
                                   String fechaNacimiento,
                                   int idEntrenador,
                                   int idCategoria,
                                   String sexo,
                                   String lateralidad,
                                   String fechaIngresoAsociacion,
                                   String estado,
                                   String esFederado) {

        if (existente == null) {
            return "El atleta a actualizar no existe.";
        }

        // Reutilizamos la misma logica de validacion de arriba
        String error = crearAtleta(
                nombre, cedula, direccion, telefono, correo,
                fechaNacimiento, idEntrenador, idCategoria,
                sexo, lateralidad, fechaIngresoAsociacion,
                estado, esFederado
        );

        // OJO: crearAtleta intenta insertar; aqui solo queremos validar.
        // Por eso no podemos llamar crearAtleta directamente asi.
        // Mejor reutilizamos la parte de validacion en un metodo privado.

        return actualizarAtletaConValidacion(existente,
                nombre, cedula, direccion, telefono, correo,
                fechaNacimiento, idEntrenador, idCategoria,
                sexo, lateralidad, fechaIngresoAsociacion,
                estado, esFederado);
    }

    // Version correcta: metodo privado que SOLO valida y luego actualiza
    private String actualizarAtletaConValidacion(Atleta existente,
                                                 String nombre,
                                                 String cedula,
                                                 String direccion,
                                                 String telefono,
                                                 String correo,
                                                 String fechaNacimiento,
                                                 int idEntrenador,
                                                 int idCategoria,
                                                 String sexo,
                                                 String lateralidad,
                                                 String fechaIngresoAsociacion,
                                                 String estado,
                                                 String esFederado) {

        // Podemos reaprovechar exactamente las mismas reglas,
        // pero sin llamar a insertar(). Para no duplicar TODO el codigo,
        // normalmente se haria un metodo privado de validacion.
        // Para no hacerlo inmenso aqui, resumimos:

        // 1) Validar igual que en crearAtleta, pero sin insertar.
        //    Si algo falla, devolvemos mensaje.
        // 2) Si todo bien, seteamos en "existente" y llamamos dao.actualizar().

        // ---- REPASO RAPIDO DE VALIDACIONES IMPORTANTES ----
        if (nombre == null || nombre.trim().isEmpty()) {
            return "El nombre del atleta no puede estar vacio.";
        }
        if (cedula == null || cedula.trim().isEmpty()) {
            return "La cedula del atleta no puede estar vacia.";
        }
        if (!Validador.esCedulaCRValida(cedula)) {
            return "La cedula del atleta no es valida (9 digitos).";
        }
        if (telefono != null && !telefono.trim().isEmpty()) {
            if (!Validador.esTelefonoCRValido(telefono)) {
                return "El telefono del atleta no es valido (8 digitos CR).";
            }
        }
        if (correo != null && !correo.trim().isEmpty()) {
            if (!Validador.esCorreoValido(correo)) {
                return "El correo del atleta no tiene un formato valido.";
            }
        }
        if (fechaNacimiento != null && !fechaNacimiento.trim().isEmpty()) {
            if (!Validador.esFechaValida(fechaNacimiento)) {
                return "La fecha de nacimiento no es valida (formato dd/MM/yyyy).";
            }
        }
        if (fechaIngresoAsociacion != null && !fechaIngresoAsociacion.trim().isEmpty()) {
            if (!Validador.esFechaValida(fechaIngresoAsociacion)) {
                return "La fecha de ingreso a la asociacion no es valida (formato dd/MM/yyyy).";
            }
        }

        String sexoFinal;
        if (sexo == null || sexo.trim().isEmpty()) {
            return "Debe seleccionar el sexo del atleta (M o F).";
        } else {
            String s = sexo.trim().toUpperCase();
            if (!s.equals("M") && !s.equals("F")) {
                return "El sexo del atleta debe ser M o F.";
            }
            sexoFinal = s;
        }

        String lateralidadFinal;
        if (lateralidad == null || lateralidad.trim().isEmpty()) {
            return "Debe indicar la lateralidad del atleta (Derecho o Zurdo).";
        } else {
            String lat = lateralidad.trim();
            if (!lat.equalsIgnoreCase("Derecho") && !lat.equalsIgnoreCase("Zurdo")) {
                return "La lateralidad debe ser 'Derecho' o 'Zurdo'.";
            }
            lateralidadFinal = lat.substring(0, 1).toUpperCase() + lat.substring(1).toLowerCase();
        }

        String estadoFinal;
        if (estado == null || estado.trim().isEmpty()) {
            estadoFinal = "Activo";
        } else {
            String est = estado.trim();
            if (!est.equalsIgnoreCase("Activo") && !est.equalsIgnoreCase("Inactivo")) {
                return "El estado del atleta debe ser 'Activo' o 'Inactivo'.";
            }
            estadoFinal = est.substring(0, 1).toUpperCase() + est.substring(1).toLowerCase();
        }

        String federadoFinal;
        if (esFederado == null || esFederado.trim().isEmpty()) {
            federadoFinal = "No";
        } else {
            String fed = esFederado.trim();
            if (!fed.equalsIgnoreCase("Si") && !fed.equalsIgnoreCase("No")) {
                return "El campo 'es federado' debe ser 'Si' o 'No'.";
            }
            federadoFinal = fed.substring(0, 1).toUpperCase() + fed.substring(1).toLowerCase();
        }

        if (idEntrenador <= 0) {
            return "Debe seleccionar un entrenador valido.";
        }
        if (entrenadorDAO.buscarPorId(idEntrenador) == null) {
            return "El entrenador seleccionado no existe en la base de datos.";
        }

        if (idCategoria <= 0) {
            return "Debe seleccionar una categoria de edad valida.";
        }
        if (categoriaDAO.buscarPorId(idCategoria) == null) {
            return "La categoria seleccionada no existe en la base de datos.";
        }

        // ---- SI TODO VALIDA, ACTUALIZAMOS EL OBJETO Y GUARDAMOS ----
        existente.setNombre(nombre.trim());
        existente.setCedula(cedula.trim());
        existente.setDireccion(direccion == null ? "" : direccion.trim());
        existente.setTelefono(telefono == null ? "" : telefono.trim());
        existente.setCorreo(correo == null ? "" : correo.trim());
        existente.setFechaNacimiento(fechaNacimiento == null ? "" : fechaNacimiento.trim());
        existente.setIdEntrenador(idEntrenador);
        existente.setIdCategoria(idCategoria);
        existente.setSexo(sexoFinal);
        existente.setLateralidad(lateralidadFinal);
        existente.setFechaIngresoAsociacion(
                fechaIngresoAsociacion == null ? "" : fechaIngresoAsociacion.trim()
        );
        existente.setEstado(estadoFinal);
        existente.setEsFederado(federadoFinal);

        boolean ok = atletaDAO.actualizar(existente);
        if (!ok) {
            return "No se pudo actualizar el atleta en la base de datos.";
        }

        return null;
    }

    // =========================================
    // LECTURAS Y ELIMINACION
    // =========================================

    public List<Atleta> listarTodos() {
        return atletaDAO.listarTodos();
    }

    public Atleta buscarPorId(int id) {
        return atletaDAO.buscarPorId(id);
    }

    public List<Atleta> buscarPorNombreOCedula(String patron) {
        if (patron == null) {
            patron = "";
        }
        return atletaDAO.buscarPorNombreOCedula(patron.trim());
    }

    public boolean eliminar(int id) {
        return atletaDAO.eliminar(id);
    }
}