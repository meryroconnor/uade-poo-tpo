package vista;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class RegisterUserDialog extends JDialog {
    private JTextField dniField, nombreField, emailField, usernameField, passwordField;
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
            dispose();
        });
        buttonPanel.add(guardarButton);

        cancelButton = new JButton("Cancelar");
        cancelButton.addActionListener(e -> dispose());
        buttonPanel.add(cancelButton);

        add(buttonPanel, BorderLayout.SOUTH);

        setLocationRelativeTo(owner);
    }
}