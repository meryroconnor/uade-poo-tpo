package vista;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ResultadoPanel extends JPanel {

    private JTextField valorResultadoField;
    private JTextField descripcionField;
    private JButton agregarButton;
    private JTextArea outputArea;

    public ResultadoPanel() {
        setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel(new GridLayout(3, 2));
        inputPanel.add(new JLabel("Valor del Resultado:"));
        valorResultadoField = new JTextField();
        inputPanel.add(valorResultadoField);

        inputPanel.add(new JLabel("Descripción:"));
        descripcionField = new JTextField();
        inputPanel.add(descripcionField);

        agregarButton = new JButton("Agregar Resultado");
        inputPanel.add(agregarButton);

        add(inputPanel, BorderLayout.NORTH);

        outputArea = new JTextArea();
        add(new JScrollPane(outputArea), BorderLayout.CENTER);

        agregarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Lógica para agregar resultado
                String valorResultado = valorResultadoField.getText();
                String descripcion = descripcionField.getText();
                outputArea.append("Resultado agregado: " + valorResultado + " - " + descripcion + "\n");
            }
        });
    }
}


