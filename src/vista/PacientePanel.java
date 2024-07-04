package vista;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

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

        JLabel titleLabel = new JLabel("Recepción de Pacientes");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        inputPanel.add(titleLabel);
        inputPanel.add(new JLabel()); // Espacio vacío para alinear correctamente

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

        JPanel sendPanel = new JPanel(new GridLayout(2, 3));

        JLabel manageLabel = new JLabel(" ");
        manageLabel.setFont(new Font("Arial", Font.BOLD, 18));
        manageLabel.setBorder(new EmptyBorder(10, 0, 10, 0)); // Márgenes superior e inferior
        sendPanel.add(manageLabel);
        sendPanel.add(new JLabel());
        sendPanel.add(new JLabel());


        agregarButton = new JButton("Registrar Paciente");
        agregarButton.setForeground(new Color(0, 141, 213));
        agregarButton.setFont(new Font("Lucida Bright", Font.PLAIN, 13));
        agregarButton.setIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("resources/plus.png"))));

        eliminarButton = new JButton("Eliminar Paciente");
        eliminarButton.setForeground(new Color(213, 0, 50));
        eliminarButton.setFont(new Font("Lucida Bright", Font.PLAIN, 13));
        eliminarButton.setIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("resources/trash.png"))));

        buscarButton = new JButton("Buscar Paciente");
        buscarButton.setFont(new Font("Lucida Bright", Font.PLAIN, 13));
        buscarButton.setIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("resources/search1.png"))));

        sendPanel.add(agregarButton);
        sendPanel.add(eliminarButton);
        sendPanel.add(buscarButton);

        add(inputPanel, BorderLayout.NORTH);
        add(sendPanel, BorderLayout.SOUTH);

        outputArea = new JTextArea();
        add(new JScrollPane(outputArea), BorderLayout.CENTER);

        this.asociarEventos();

    }

    private void asociarEventos() {
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



