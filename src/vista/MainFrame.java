package vista;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class MainFrame extends JFrame {

    private JTabbedPane tabbedPane;

    public MainFrame(String rol) {
        setTitle("Gestión de Laboratorio");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        tabbedPane = new JTabbedPane();

        // Agregar paneles
        tabbedPane.add("Pacientes", new PacientePanel());
        tabbedPane.add("Consultar Peticiones", new PeticionPanel());

        if (Objects.equals(rol, "admin")) {
            tabbedPane.add("Sucursales", new SucursalesPanel());
        }

        if (Objects.equals(rol, "admin") || Objects.equals(rol, "laboratorista")) {
            tabbedPane.add("Laboratorio", new LabPanel());
        }

        add(tabbedPane, BorderLayout.CENTER);
    }

}


