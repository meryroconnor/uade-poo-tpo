package vista;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    private JTabbedPane tabbedPane;

    public MainFrame() {
        setTitle("Gestión de Laboratorio");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        tabbedPane = new JTabbedPane();

        // Agregar paneles
        tabbedPane.add("Pacientes", new PacientePanel());
        tabbedPane.add("Sucursales", new SucursalesPanel());
        tabbedPane.add("Laboratorio", new LabPanel());

        add(tabbedPane, BorderLayout.CENTER);
    }

}


