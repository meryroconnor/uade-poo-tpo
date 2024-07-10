package vista;

import DTOs.ObraSocialDTO;
import DTOs.PacienteDTO;
import DTOs.PeticionDTO;
import DTOs.SucursalDTO;
import controlador.ControladorAtencion;
import controlador.ControladorPaciente;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SucursalDialog extends JDialog {
    private JTextField nombreField, responsableField;

    private JButton guardarButton, cancelButton;
    private  JComboBox sucursalCombo;

    public SucursalDialog(Frame owner, JComboBox sucursalCombo) {
        super(owner, "Agregar Sucursal", true);
        this.sucursalCombo = sucursalCombo;

        setLayout(new BorderLayout());
        setSize(350, 230);

        // Panel de título
        JPanel titlePanel = new JPanel();
        titlePanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        titlePanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        JLabel titleLabel = new JLabel("Registro de Sucursal");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16)); // Configura la fuente del título
        titlePanel.add(titleLabel);
        add(titlePanel, BorderLayout.NORTH);

        // Panel de contenido
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new GridLayout(0, 2, 10, 10)); // 10 pixels of gap between columns and rows
        contentPanel.setBorder(new EmptyBorder(10, 10, 10, 10)); // Margins around the whole content panel
        

        contentPanel.add(new JLabel("Nombre:"));
        nombreField = new JTextField();
        contentPanel.add(nombreField);

        contentPanel.add(new JLabel("Responsable:"));
        responsableField = new JTextField();
        contentPanel.add(responsableField);


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
        this.asociarEventos();

    }

    private void asociarEventos() {
        guardarButton.addActionListener(e -> {
            String nombre = nombreField.getText();
            int responsable = Integer.parseInt(responsableField.getText());
            List<PeticionDTO> peticionesDTO = null;

            SucursalDTO sucursalDTO = new SucursalDTO(0, nombre, responsable, peticionesDTO);

            try{
                ControladorAtencion controladorAtencion = ControladorAtencion.getInstance();
                controladorAtencion.createSucursal(sucursalDTO);

                sucursalCombo.addItem(nombre);
            } catch (Exception err){
                System.out.println("Error ocurrido: " + err.getMessage());
            }

            dispose();
            // Aquí iría la lógica para guardar los datos del Sucursal
            dispose();
        });

        cancelButton.addActionListener(e -> dispose());
    }
}
