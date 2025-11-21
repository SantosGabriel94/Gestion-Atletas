/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.atletas.controller;
import com.mycompany.atletas.model.ConexionBD;

import java.util.Scanner;
/**
 *
 * @author Admin
 */
public class AtletaControllerPruebas {

    public static void main(String[] args) {

        // Inicializar BD
        ConexionBD.inicializarBaseDatos();

        AtletaController controller = new AtletaController();
        Scanner scanner = new Scanner(System.in);

        int opcion;
        do {
            System.out.println("==== Sistema de Atletas (Voleibol) ====");
            System.out.println("1. Agregar atleta");
            System.out.println("2. Listar atletas");
            System.out.println("3. Actualizar atleta");
            System.out.println("4. Eliminar atleta");
            System.out.println("5. Buscar atleta");
            System.out.println("0. Salir");
            System.out.print("Opción: ");

            while (!scanner.hasNextInt()) {
                scanner.nextLine();
                System.out.print("Ingrese un número válido: ");
            }
            opcion = scanner.nextInt();
            scanner.nextLine(); // limpiar buffer

            switch (opcion) {
                case 1 -> controller.crearAtleta();
                case 2 -> controller.listarAtletas();
                case 3 -> controller.actualizarAtleta();
                case 4 -> controller.eliminarAtleta();
                case 5 -> controller.buscarAtleta();
                case 0 -> System.out.println("Saliendo...");
                default -> System.out.println("Opción inválida.");
            }

            System.out.println();

        } while (opcion != 0);
    }
}
