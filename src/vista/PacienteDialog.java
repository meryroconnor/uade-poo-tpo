package vista;

import DTOs.ObraSocialDTO;
import controlador.ControladorPaciente;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.List;
import java.util.Objects;

public class PacienteDialog extends JDialog {
    private JTextField dniField, nombreField, emailField, fechaNacimientoField, nroAfiliadoField;
    private JComboBox<String> sexoComboBox, obraSocialComboBox;
    private JButton guardarButton, cancelButton;

    public PacienteDialog(Frame owner) {
        super(owner, "Agregar Paciente", true);
        setLayout(new BorderLayout());
        setSize(350, 450);

        // Panel de título
        JPanel titlePanel = new JPanel();
        titlePanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        titlePanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        JLabel titleLabel = new JLabel("Registro de Paciente");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16)); // Configura la fuente del título
        titlePanel.add(titleLabel);
        add(titlePanel, BorderLayout.NORTH);

        // Panel de contenido
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new GridLayout(0, 2, 10, 10)); // 10 pixels of gap between columns and rows
        contentPanel.setBorder(new EmptyBorder(10, 10, 10, 10)); // Margins around the whole content panel

        contentPanel.add(new JLabel("DNI:"));
        dniField = new JTextField();
        contentPanel.add(dniField);

        contentPanel.add(new JLabel("Nombre:"));
        nombreField = new JTextField();
        contentPanel.add(nombreField);

        contentPanel.add(new JLabel("Email:"));
        emailField = new JTextField();
        contentPanel.add(emailField);

        contentPanel.add(new JLabel("Fecha de Nacimiento:"));
        fechaNacimientoField = new JTextField();
        contentPanel.add(fechaNacimientoField);

        contentPanel.add(new JLabel("Sexo:"));
        sexoComboBox = new JComboBox<>(new String[]{"F", "M"});
        contentPanel.add(sexoComboBox);

        contentPanel.add(new JLabel("Obra Social:"));
        obraSocialComboBox = new JComboBox<>(Objects.requireNonNull(obtenerObrasSociales()));
        contentPanel.add(obraSocialComboBox);

        contentPanel.add(new JLabel("Nro Afiliado:"));
        nroAfiliadoField = new JTextField();
        contentPanel.add(nroAfiliadoField);

        add(contentPanel, BorderLayout.CENTER);

        // Panel de botones
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        guardarButton = new JButton("Guardar");
        guardarButton.setForeground(new Color(0, 141, 213));

        cancelButton = new JButton("Cancelar");

        buttonPanel.add(guardarButton);
        buttonPanel.add(cancelButton);

        add(buttonPanel, BorderLayout.SOUTH);

        setLocationRelativeTo(owner);

        this.registrarPacienteEventos();
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

    private void registrarPacienteEventos() {
        guardarButton.addActionListener(e -> {
            // Aquí iría la lógica para guardar los datos del paciente
            dispose();
        });

        cancelButton.addActionListener(e -> dispose());

    }
}


