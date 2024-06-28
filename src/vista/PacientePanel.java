package vista;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PacientePanel extends JPanel {

    private JTextField pacienteField;
    private JTextField dniField;
    private JComboBox<String> osComboBox;
    private JTextField osIDField;
    private JButton buscarButton;
    private JButton agregarButton;
    private JButton eliminarButton;
    private JTextArea outputArea;
    private JPanel inputPanel;
    private JPanel sendPanel;

    public PacientePanel() {
        setLayout(new BorderLayout());
        setSize(600, 300);

        JPanel inputPanel = new JPanel(new GridLayout(6, 2));
        inputPanel.add(new JLabel("DNI:"));
        dniField = new JTextField();
        inputPanel.add(dniField);

        inputPanel.add(new JLabel("Paciente ID:"));
        pacienteField = new JTextField();
        inputPanel.add(pacienteField);

        inputPanel.add(new JLabel("Obra Social:"));
        osComboBox = new JComboBox<>(new String[] {"osde", "swiss medical"});
        inputPanel.add(osComboBox);

        inputPanel.add(new JLabel("Obra Social ID:"));
        osIDField = new JTextField();
        inputPanel.add(osIDField);

        JPanel sendPanel = new JPanel(new GridLayout(1, 4));
        buscarButton = new JButton("Buscar Paciente");
        agregarButton = new JButton("Agregar Paciente");
        eliminarButton = new JButton("Eliminar Paciente");
        sendPanel.add(buscarButton);
        sendPanel.add(agregarButton);
        sendPanel.add(eliminarButton);

        add(inputPanel, BorderLayout.NORTH);
        add(sendPanel, BorderLayout.SOUTH);

        outputArea = new JTextArea();
        add(new JScrollPane(outputArea), BorderLayout.CENTER);

        // Action listener for "Agregar Paciente" button
        agregarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PacienteDialog dialog = new PacienteDialog(JOptionPane.getFrameForComponent(PacientePanel.this));
                dialog.setVisible(true);
            }
        });

        // Action listener for "Eliminar Paciente" button
        eliminarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String dni = dniField.getText();
                String pacienteID = pacienteField.getText();
                String obraSocial = (String) osComboBox.getSelectedItem();
                String osID = osIDField.getText();
                outputArea.append("Eliminar Paciente:\n");
                outputArea.append("DNI: " + dni + "\n");
                outputArea.append("Paciente ID: " + pacienteID + "\n");
                outputArea.append("Obra Social: " + obraSocial + "\n");
                outputArea.append("Obra Social ID: " + osID + "\n\n");
            }
        });

        // Action listener for "Buscar Paciente" button
        buscarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String dni = dniField.getText();
                String pacienteID = pacienteField.getText();
                String obraSocial = (String) osComboBox.getSelectedItem();
                String osID = osIDField.getText();
                outputArea.append("Buscar Paciente:\n");
                outputArea.append("DNI: " + dni + "\n");
                outputArea.append("Paciente ID: " + pacienteID + "\n");
                outputArea.append("Obra Social: " + obraSocial + "\n");
                outputArea.append("Obra Social ID: " + osID + "\n\n");
            }
        });


    }
}



