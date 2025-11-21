/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.View;
import com.mycompany.atletas.controller.AtletaController;
import com.mycompany.atletas.model.ConexionBD;
import javax.swing.SwingUtilities;
/**
 *
 * @author Admin
 */

import com.mycompany.atletas.controller.AtletaController;

public class Atletas {

    public static void main(String[] args) {

        // Inicializar la base de datos (crea la tabla si no existe)
        ConexionBD.inicializarBaseDatos();

        // Insertar datos de prueba (los del Excel)
        ConexionBD.insertarDatosEjemplo();

        // Mostrar la ventana gráfica (si la quieres usar más adelante)
        SwingUtilities.invokeLater(() -> {
            AtletasFrame frame = new AtletasFrame();
            frame.setVisible(true);
        });
    }
}
