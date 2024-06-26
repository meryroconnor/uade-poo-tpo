package vista;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PacientePanel extends JPanel {

    private JTextField nombreField;
    private JTextField dniField;
    private JButton agregarButton;
    private JButton eliminarButton;
    private JTextArea outputArea;

    public PacientePanel() {
        setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel(new GridLayout(3, 2));
        inputPanel.add(new JLabel("Nombre:"));
        nombreField = new JTextField();
        inputPanel.add(nombreField);

        inputPanel.add(new JLabel("DNI:"));
        dniField = new JTextField();
        inputPanel.add(dniField);

        agregarButton = new JButton("Agregar Paciente");
        eliminarButton = new JButton("Eliminar Paciente");
        inputPanel.add(agregarButton);
        inputPanel.add(eliminarButton);

        add(inputPanel, BorderLayout.NORTH);

        outputArea = new JTextArea();
        add(new JScrollPane(outputArea), BorderLayout.CENTER);

        agregarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Lógica para agregar paciente
                String nombre = nombreField.getText();
                String dni = dniField.getText();
                outputArea.append("Paciente agregado: " + nombre + " (DNI: " + dni + ")\n");
            }
        });

        eliminarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Lógica para eliminar paciente
                String dni = dniField.getText();
                outputArea.append("Paciente eliminado con DNI: " + dni + "\n");
            }
        });
    }
}



