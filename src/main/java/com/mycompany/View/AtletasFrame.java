/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.View;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;
import com.mycompany.atletas.model.Atleta;
import com.mycompany.atletas.model.AtletaDAO;

/**
 *
 * @author Admin
 */
public class AtletasFrame extends javax.swing.JFrame {

    private final AtletaDAO dao;
    private JTable tablaAtletas;
    private JButton btnCargar;

    public AtletasFrame() {
        this.dao = new AtletaDAO();
        initComponents();
        setTitle("Gestión de Atletas (Voleibol)");
        setLocationRelativeTo(null);
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {

        btnCargar = new JButton("Cargar atletas");
        tablaAtletas = new JTable();

        // columnas reales según la nueva BD
        tablaAtletas.setModel(new DefaultTableModel(
                new Object[][]{},
                new String[]{
                        "ID", "Nombre", "Cédula", "Dirección", "Teléfono", "Correo",
                        "F. Nacimiento", "ID Entrenador", "ID Categoría",
                        "Sexo", "Lateralidad", "Ingreso", "Estado", "Federado"
                }
        ));

        JScrollPane scrollPane = new JScrollPane(tablaAtletas);

        btnCargar.addActionListener(e -> cargarAtletasEnTabla());

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);

        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 900, Short.MAX_VALUE)
                                        .addComponent(btnCargar))
                                .addContainerGap())
        );

        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(btnCargar)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 500, Short.MAX_VALUE)
                                .addContainerGap())
        );

        pack();
    }

    private void cargarAtletasEnTabla() {
        DefaultTableModel model = (DefaultTableModel) tablaAtletas.getModel();
        model.setRowCount(0);

        List<Atleta> atletas = dao.listarTodos();

        for (Atleta a : atletas) {
            model.addRow(new Object[]{
                    a.getId(),
                    a.getNombre(),
                    a.getCedula(),
                    a.getDireccion(),
                    a.getTelefono(),
                    a.getCorreo(),
                    a.getFechaNacimiento(),
                    a.getIdEntrenador(),
                    a.getIdCategoria(),
                    a.getSexo(),
                    a.getLateralidad(),
                    a.getFechaIngresoAsociacion(),
                    a.getEstado(),
                    a.getEsFederado()
            });
        }
    }
}