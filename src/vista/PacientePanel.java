package vista;

import DTOs.ObraSocialDTO;
import DTOs.PacienteDTO;
import controlador.ControladorPaciente;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
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
    private JComboBox sexoComboBox;

    private TextField sexoField;

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

//        inputPanel.add(new JLabel("Paciente ID:"));
//        pacienteField = new JTextField();
//        inputPanel.add(pacienteField);

//        inputPanel.add(new JLabel("Obra Social:"));
//        osComboBox = new JComboBox<>(Objects.requireNonNull(obtenerObrasSociales()));
//        inputPanel.add(osComboBox);

//        inputPanel.add(new JLabel("Obra Social ID:"));
//        osIDField = new JTextField();
//        inputPanel.add(osIDField);

        inputPanel.add(new JLabel("Sexo:"));
        sexoComboBox = new JComboBox<>(new String[]{"F", "M"});
        inputPanel.add(sexoComboBox);


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
    private String[] obtenerObrasSociales(){
        try{
            ControladorPaciente controladorPaciente = ControladorPaciente.getInstance();
            List<ObraSocialDTO> obrasSocialesDTO = controladorPaciente.getObrasSociales();
            String[] obrasSociales = new String[obrasSocialesDTO.size()];
            for (int i = 0; i < obrasSocialesDTO.size(); i++){
                obrasSociales[i] = obrasSocialesDTO.get(i).getObraSocial();
            }
            return  obrasSociales;
        } catch (Exception e){
            System.out.println("Error ocurrido: " + e);
        }
        return null;
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
                int pacienteID = Integer.parseInt(pacienteField.getText());
                String obraSocial = (String) osComboBox.getSelectedItem();
                //String osID = osIDField.getText(); para que quiero el id de la obra social si tengo el nombre.



                outputArea.append("Eliminar Paciente:\n");
                outputArea.append("DNI: " + dni + "\n");
                outputArea.append("Paciente ID: " + pacienteID + "\n");
                outputArea.append("Obra Social: " + obraSocial + "\n");
                //outputArea.append("Obra Social ID: " + osID + "\n\n");
            }
        });

        // Action listener for "Buscar Paciente" button
        buscarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String dni = dniField.getText();
                //String pacienteID = pacienteField.getText();
                //String obraSocial = (String) osComboBox.getSelectedItem();
                //String osID = osIDField.getText();
                String sexo = Objects.requireNonNull(sexoComboBox.getSelectedItem()).toString();

                //ObraSocialDTO obraSocialDTO = new ObraSocialDTO(obraSocial, 0); //no importa el id se sobreescribe porque se usa para buscar


                PacienteDTO pacienteDTO = new PacienteDTO(0, null, sexo, dni, null, null, null);

                try{
                    ControladorPaciente controladorPaciente = ControladorPaciente.getInstance();
                    pacienteDTO = controladorPaciente.getPaciente(pacienteDTO);
                } catch (Exception err ){
                    System.out.println("Error ocurrido: " + e);
                }

                if (pacienteDTO != null) {
                    outputArea.append("Buscar Paciente:\n");
                    outputArea.append("Nombre: " + pacienteDTO.getNombreApellido() + "\n");
                    outputArea.append("DNI: " + pacienteDTO.getDNI() + "\n");
                    outputArea.append("Mail: " + pacienteDTO.getEmail() + "\n");
                    outputArea.append("Sexo: " + pacienteDTO.getSexo() + "\n");
                    outputArea.append("Paciente ID: " + pacienteDTO.getPacienteID() + "\n");
                    outputArea.append("Obra Social: " + pacienteDTO.getObraSocialDTO().getObraSocial() + "\n");
                    outputArea.append("Obra Social ID: " + pacienteDTO.getObraSocialDTO().getObraSocialID() + "\n\n");
                } else {
                    outputArea.append("PACIENTE NO ENCONTRADO\n");
                }
            }
        });
    }
}



