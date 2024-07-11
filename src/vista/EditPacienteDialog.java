package vista;

        import DTOs.ObraSocialDTO;
        import DTOs.PacienteDTO;
        import DTOs.PeticionDTO;
        import controlador.ControladorPaciente;

        import javax.swing.*;
        import javax.swing.border.EmptyBorder;
        import java.awt.*;
        import java.util.ArrayList;
        import java.util.List;
        import java.util.Objects;

public class EditPacienteDialog extends JDialog {
    private JTextField dniField, nombreField, emailField, edadField, domicilioField, nroAfiliadoField;
    private JComboBox<String> sexoComboBox, obraSocialComboBox;
    private JButton guardarButton, cancelButton;

    public EditPacienteDialog(Frame owner, PacienteDTO paciente) {
        super(owner, "Agregar Paciente", true);

        String DNI = paciente.getDNI();
        String sexo= paciente.getSexo();
        String nombre = paciente.getNombreApellido();
        String email= paciente.getEmail();
        int edad= paciente.getEdad();
        String domicilio= paciente.getDomicilio();
        int nroAfiliado= paciente.getObraSocialDTO().getObraSocialID();
        String obraSocial = paciente.getObraSocialDTO().getObraSocial();


        setLayout(new BorderLayout());
        setSize(350, 480);

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
        dniField.setText(DNI);
        contentPanel.add(dniField);

        contentPanel.add(new JLabel("Nombre:"));
        nombreField = new JTextField();
        nombreField.setText(nombre);
        contentPanel.add(nombreField);

        contentPanel.add(new JLabel("Email:"));
        emailField = new JTextField();
        emailField.setText(email);
        contentPanel.add(emailField);

        contentPanel.add(new JLabel("Edad:"));
        edadField = new JTextField();
        edadField.setText(Objects.toString(edad));
        contentPanel.add(edadField);

        contentPanel.add(new JLabel("Domicilio:"));
        domicilioField = new JTextField();
        domicilioField.setText(domicilio);
        contentPanel.add(domicilioField);

        contentPanel.add(new JLabel("Sexo:"));
        sexoComboBox = new JComboBox<>(new String[]{"F", "M"});
        sexoComboBox.setSelectedItem(sexo);
        contentPanel.add(sexoComboBox);

        contentPanel.add(new JLabel("Obra Social:"));
        obraSocialComboBox = new JComboBox<>(Objects.requireNonNull(obtenerObrasSociales()));
        obraSocialComboBox.setSelectedItem(obraSocial);
        contentPanel.add(obraSocialComboBox);

        contentPanel.add(new JLabel("Nro Afiliado:")); //sacar esto
        nroAfiliadoField = new JTextField();
        nroAfiliadoField.setText(Objects.toString(nroAfiliado));
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

        guardarButton.setEnabled(false);

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

        nombreField.addActionListener( e -> {
            guardarButton.setEnabled(true);
        });

        dniField.addActionListener( e -> {
            guardarButton.setEnabled(true);
        });

        emailField.addActionListener( e -> {
            guardarButton.setEnabled(true);
        });

        edadField.addActionListener( e -> {
            guardarButton.setEnabled(true);
        });

        domicilioField.addActionListener( e -> {
            guardarButton.setEnabled(true);
        });

        nroAfiliadoField.addActionListener( e -> {
            guardarButton.setEnabled(true);
        });

        obraSocialComboBox.addActionListener( e -> {
            guardarButton.setEnabled(true);
        });

        sexoComboBox.addActionListener( e -> {
            guardarButton.setEnabled(true);
        });

        guardarButton.addActionListener(e -> {

            String nombre = nombreField.getText();
            String DNI = dniField.getText();
            String mail = emailField.getText();
            int edad = Integer.parseInt(edadField.getText());
            String domicilio = domicilioField.getText();
            String nroAfiliadoText = nroAfiliadoField.getText();
            String obraSocial = Objects.requireNonNull(obraSocialComboBox.getSelectedItem()).toString();
            String sexo = Objects.requireNonNull(sexoComboBox.getSelectedItem()).toString();

            ObraSocialDTO obraSocialDTO;
            if (nroAfiliadoText.isEmpty() && "Sin Obra Social".equals(obraSocial)) {
                obraSocialDTO = new ObraSocialDTO(null, 0);
            } else {
                int nroAfiliado = Integer.parseInt(nroAfiliadoText);
                obraSocialDTO = new ObraSocialDTO(obraSocial, nroAfiliado);
            }

            List<PeticionDTO> peticionesDTO = new ArrayList<>();
            PacienteDTO pacienteDTO = new PacienteDTO(0, nombre, sexo, DNI, mail, edad, domicilio,peticionesDTO, obraSocialDTO);

            try{
                ControladorPaciente controladorPaciente = ControladorPaciente.getInstance();
                controladorPaciente.createPaciente(pacienteDTO);
            } catch (Exception err){
                System.out.println("Error ocurrido: " + err.getMessage());
            }

            dispose();
        });



        cancelButton.addActionListener(e -> dispose());
    }
}


