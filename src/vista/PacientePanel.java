package vista;

import DTOs.PacienteDTO;
import controlador.ControladorPaciente;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

public class PacientePanel extends JPanel {

    private JTextField dniField;
    private JComboBox sexoComboBox;
    private JButton editarButton;
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


        inputPanel.add(new JLabel("Sexo:"));
        sexoComboBox = new JComboBox<>(new String[]{"F", "M"});
        inputPanel.add(sexoComboBox);

        inputPanel.add(new JLabel());
        editarButton = new JButton("Editar Paciente");
        editarButton.setIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("resources/edit.png"))));
        inputPanel.add(editarButton);

        // Panel Con botones para accionar
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
                String sexo = (String) sexoComboBox.getSelectedItem();

                try {
                    ControladorPaciente controladorPaciente = ControladorPaciente.getInstance();
                    String output = controladorPaciente.deletePaciente(dni, sexo);


                    outputArea.append(output);


                } catch (Exception err) {
                    outputArea.append("Error: " + err.getMessage() + "\n\n");
                    err.printStackTrace();
                }
            }
        });

        // Action listener for "Buscar Paciente" button
        buscarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String dni = dniField.getText();
                String sexo = Objects.requireNonNull(sexoComboBox.getSelectedItem()).toString();

                try {
                    ControladorPaciente controladorPaciente = ControladorPaciente.getInstance();
                    PacienteDTO pacienteDTO = controladorPaciente.getPaciente(dni, sexo);

                    if (pacienteDTO != null) {
                        outputArea.append("Buscar Paciente:\n");
                        outputArea.append("Nombre: " + pacienteDTO.getNombreApellido() + "\n");
                        outputArea.append("DNI: " + pacienteDTO.getDNI() + "\n");
                        outputArea.append("Mail: " + pacienteDTO.getEmail() + "\n");
                        outputArea.append("Edad: " + pacienteDTO.getEdad() + "\n");
                        outputArea.append("Domicilio: " + pacienteDTO.getDomicilio() + "\n");
                        outputArea.append("Paciente ID: " + pacienteDTO.getPacienteID() + "\n");

                        if (pacienteDTO.getObraSocialDTO().getObraSocial() != null) {
                            outputArea.append("Paciente tiene Obra Social\n");
                            outputArea.append("Obra Social: " + pacienteDTO.getObraSocialDTO().getObraSocial() + "\n");
                            outputArea.append("Obra Social ID: " + pacienteDTO.getObraSocialDTO().getObraSocialID() + "\n\n");
                        } else {
                            outputArea.append("Paciente NO tiene Obra Social \n\n");
                        }
                    } else {
                        outputArea.append("PACIENTE NO ENCONTRADO\n\n");
                    }

                } catch (Exception err) {
                    outputArea.append("Error al buscar el paciente: " + err.getMessage() + "\n\n");
                    err.printStackTrace();
                }
            }
        });
    }

}



