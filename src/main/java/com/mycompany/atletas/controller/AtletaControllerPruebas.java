/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.atletas.controller;

import com.mycompany.atletas.model.Atleta;
import com.mycompany.atletas.model.AtletaDAO;
import com.mycompany.atletas.model.ConexionBD;
import com.mycompany.atletas.model.Membresia;
import com.mycompany.atletas.model.MembresiaDAO;
import com.mycompany.atletas.model.PagoMensual;
import com.mycompany.atletas.model.PagoMensualDAO;

import java.util.List;
/**
 *
 * @author Admin
 */
public class AtletaControllerPruebas {

    public static void main(String[] args) {

        // 1) Inicializar BD y datos de ejemplo
        ConexionBD.inicializarBaseDatos();
        ConexionBD.insertarDatosEjemplo();

        AtletaDAO atletaDAO = new AtletaDAO();
        MembresiaDAO membDAO = new MembresiaDAO();
        PagoMensualDAO pagoDAO = new PagoMensualDAO();

        System.out.println("===== PRUEBA GENERAL DEL SISTEMA =====");

        // 2) Listar atletas
        System.out.println("\n--- Atletas actuales en la BD ---");
        List<Atleta> atletas = atletaDAO.listarTodos();
        for (Atleta a : atletas) {
            System.out.println(a);
        }

        // Supongamos que queremos probar con el atleta con id 1 (Carlos)
        int idAtletaPrueba = 1;

        // 3) Obtener membresía activa de ese atleta
        System.out.println("\n--- Membresía activa del atleta ID " + idAtletaPrueba + " ---");
        Membresia mActiva = membDAO.obtenerMembresiaActiva(idAtletaPrueba);
        if (mActiva == null) {
            System.out.println("No hay membresía activa para ese atleta.");
        } else {
            System.out.println(mActiva);

            // 4) Ver pagos del año 2024
            int anioPrueba = 2024;
            System.out.println("\n--- Pagos del año " + anioPrueba +
                               " para la membresía " + mActiva.getIdMembresia() + " ---");
            List<PagoMensual> pagos = pagoDAO.obtenerPagosPorMembresiaYAnio(
                    mActiva.getIdMembresia(),
                    anioPrueba
            );

            if (pagos.isEmpty()) {
                System.out.println("No hay pagos registrados para ese año.");
            } else {
                System.out.println("Mes | Estado | Monto | FechaPago");
                for (PagoMensual p : pagos) {
                    System.out.println(
                            String.format("%2d  | %-9s | %5.2f | %s",
                                    p.getMes(),
                                    p.getEstadoPago(),
                                    p.getMonto(),
                                    p.getFechaPago())
                    );
                }
            }

            // 5) Registrar un nuevo pago de prueba (por ejemplo abril 2024)
            System.out.println("\n--- Registrando pago de prueba (abril 2024) ---");
            boolean okPago = pagoDAO.registrarPago(
                    mActiva.getIdMembresia(),
                    4,             // mes abril
                    anioPrueba,
                    10.0,          // monto
                    "10/04/" + anioPrueba,
                    "Pagado"
            );
            System.out.println("Pago registrado correctamente? " + okPago);

            // 6) Volver a listar pagos para ver el cambio
            System.out.println("\n--- Pagos del año " + anioPrueba +
                               " después de registrar abril ---");
            pagos = pagoDAO.obtenerPagosPorMembresiaYAnio(
                    mActiva.getIdMembresia(),
                    anioPrueba
            );
            System.out.println("Mes | Estado   | Monto | FechaPago");
            for (PagoMensual p : pagos) {
                System.out.println(
                        String.format("%2d  | %-9s | %5.2f | %s",
                                p.getMes(),
                                p.getEstadoPago(),
                                p.getMonto(),
                                p.getFechaPago())
                );
            }
        }

        System.out.println("\n===== FIN PRUEBA GENERAL =====");
    }
}