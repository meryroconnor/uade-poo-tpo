package vista;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PeticionPanel extends JPanel {

    private JTextField codigoPracticaField;
    private JTextField descripcionField;
    private JButton agregarButton;
    private JTextArea outputArea;

    public PeticionPanel() {
        setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel(new GridLayout(3, 2));
        inputPanel.add(new JLabel("Código de Práctica:"));
        codigoPracticaField = new JTextField();
        inputPanel.add(codigoPracticaField);

        inputPanel.add(new JLabel("Descripción:"));
        descripcionField = new JTextField();
        inputPanel.add(descripcionField);

        agregarButton = new JButton("Agregar Petición");
        inputPanel.add(agregarButton);

        add(inputPanel, BorderLayout.NORTH);

        outputArea = new JTextArea();
        add(new JScrollPane(outputArea), BorderLayout.CENTER);

        agregarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Lógica para agregar petición
                String codigoPractica = codigoPracticaField.getText();
                String descripcion = descripcionField.getText();
                outputArea.append("Petición agregada: " + codigoPractica + " - " + descripcion + "\n");
            }
        });
    }
}
