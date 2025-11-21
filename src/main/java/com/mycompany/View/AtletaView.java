/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.View;


import com.mycompany.atletas.model.Atleta;
import java.util.List;
/**
 *
 * @author Admin
 */

public class AtletaView {

    public void mostrarMenu() {
        System.out.println("==== Sistema de Atletas (Voleibol) ====");
        System.out.println("1. Agregar atleta");
        System.out.println("2. Listar atletas");
        System.out.println("3. Actualizar atleta");
        System.out.println("4. Eliminar atleta");
        System.out.println("0. Salir");
        System.out.print("Opción: ");
    }

    public void mostrarAtletas(List<Atleta> atletas) {
        if (atletas.isEmpty()) {
            System.out.println("No hay atletas registrados.");
        } else {
            System.out.println("=== Lista de atletas ===");
            for (Atleta a : atletas) {
                System.out.println(a);
            }
        }
    }

    public void mostrarMensaje(String msg) {
        System.out.println(msg);
    }
}
