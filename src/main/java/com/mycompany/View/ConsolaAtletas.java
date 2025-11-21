/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.View;

import com.mycompany.atletas.controller.AtletaController;
import com.mycompany.atletas.model.ConexionBD;

/**
 *
 * @author Admin
 */
public class ConsolaAtletas {

    public static void main(String[] args) {

        // 1. Inicializar la base de datos
        ConexionBD.inicializarBaseDatos();

        // 2. (Opcional) Insertar datos de ejemplo
        ConexionBD.insertarDatosEjemplo();

        // 3. Iniciar el menú de atletas (por consola)
        AtletaController controller = new AtletaController();
        //.iniciar();
    }
}