package vista;

import DAOs.PacienteDAO;
import Laboratorio.Paciente;
import controlador.ControladorPaciente;
import controlador.ControladorVista;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.List;

public class RegisterUserDialog extends JDialog {
    private JTextField dniField, nombreField, emailField, usernameField, passwordField, sexoField;
    private JComboBox<String> rolComboBox;
    private JButton guardarButton, cancelButton;

    public RegisterUserDialog(Frame owner) {
        super(owner, "Registrarme", true);
        setLayout(new BorderLayout());
        setSize(350, 450);

        // Panel de título
        JPanel titlePanel = new JPanel();
        titlePanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        titlePanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        JLabel titleLabel = new JLabel("Registro de Usuario");
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

        contentPanel.add(new JLabel("Username:"));
        usernameField = new JTextField();
        contentPanel.add(usernameField);

        contentPanel.add(new JLabel("Rol:"));
        rolComboBox = new JComboBox<>(new String[]{"admin", "recepcionista", "laboratorista"});
        contentPanel.add(rolComboBox);

        //contentPanel.add(new JLabel("Sexo:"));
        //rolComboBox = new JComboBox<>(new String[]{"Femenino", "Masculino"});
        //contentPanel.add(sexoField);

        contentPanel.add(new JLabel("Password:"));
        passwordField = new JTextField();
        contentPanel.add(passwordField);

        add(contentPanel, BorderLayout.CENTER);

        // Panel de botones
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        guardarButton = new JButton("Guardar");
        guardarButton.setForeground(new Color(0, 141, 213));
        guardarButton.addActionListener(e -> {
            // Aquí iría la lógica para guardar los datos del paciente
            try {
                validarDatos();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
            dispose();
        });
        buttonPanel.add(guardarButton);

        cancelButton = new JButton("Cancelar");
        cancelButton.addActionListener(e -> dispose());
        buttonPanel.add(cancelButton);

        add(buttonPanel, BorderLayout.SOUTH);

        setLocationRelativeTo(owner);
    }
        //EVENTOS

        public void validarDatos() throws Exception {

            //agarro los datos
            String dni = dniField.getText();
            String nombre = nombreField.getText();
            String mail = emailField.getText();
            String username = usernameField.getText();
            String rol = (String) rolComboBox.getSelectedItem();
            String password= passwordField.getText();
            //String sexo = (String) rolComboBox.getSelectedItem();
            System.out.println("Datos ingresados:" + dni + ", " + nombre + ", " + mail + ", " + username + ", " + rol + ", " + password);

            //llamo al metodo que valida que exista el paciente
            ControladorPaciente controladorPaciente = ControladorPaciente.getInstance();
            //List<Paciente> pacientes; = controladorPaciente.buscarPaciente("",dni);
            //devuelve en DTO

            List<Paciente> pacientes= (List<Paciente>) controladorPaciente.createPaciente(nombre,"",dni,mail);

            //verifico que no exista y lo creo

            //el dao va en un try and catch

            //if(pacientes.isEmpty()){
                System.out.println("Paciente creado"+ pacientes);
                //PacienteDAO dao= new PacienteDAO();
                //dao.saveAll(pacientes);





            //}


            //seteo globalmente el usuario y el rol para control de pantallas
            SingletonSistema.getInstance().setUsername(username);
            SingletonSistema.getInstance().setRol(rol);


        };
}