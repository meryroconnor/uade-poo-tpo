package vista;

import DAOs.PacienteDAO;
import Laboratorio.Paciente;
import controlador.ControladorPaciente;
import vista.RegisterUserDialog;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

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

        this.asociarEventos();

    }

    private void asociarEventos() {
        // Action listener for "Agregar Paciente" button
        agregarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PacienteDialog dialog = new PacienteDialog(JOptionPane.getFrameForComponent(PacientePanel.this));
                dialog.setVisible(true);

                //agarro los datos
                String dni = dniField.getText();
                String pacienteId = pacienteField.getText();
                String os = (String) osComboBox.getSelectedItem();
                String osId= osIDField.getText();
                //String sexo = (String) rolComboBox.getSelectedItem();
                System.out.println("Datos ingresados:" + ", " + pacienteId + ", " + os + ", " + osId +  ", " + dni);

                ControladorPaciente controlador = ControladorPaciente.getInstance();
                Paciente paciente= controlador.createPaciente();
                System.out.println("Paciente creado"+ paciente);
                PacienteDAO dao= new PacienteDAO();
                dao.saveAll();
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

                //Paciente paciente= new Paciente();
                //paciente.deletePaciente();

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

                //llamo al metodo que valida que exista el paciente
                ControladorPaciente controlador = ControladorPaciente.getInstance();
                List<Paciente> pacientes= controlador.buscarPaciente("",dni);
                //devuelve en DTO
            }
        });
    }
}



