package vista;

import DTOs.PeticionDTO;
import DTOs.SucursalDTO;
import controlador.ControladorAtencion;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class EditSucursalDialog extends JDialog {
    private JPanel contentPane;
    private JTextField nombreField, responsableField;
    private JButton guardarButton, cancelButton;
    private String oldNombre; //variable global en la vista para busqueda (Ver Editar Paciente)
    public EditSucursalDialog(Frame owner, SucursalDTO sucursalDTO) {
        super(owner, "Edicion - SUCURSALES", true);
        setLayout(new BorderLayout());
        setSize(450, 230);

        String nombre = sucursalDTO.getDireccion();
        int responsable = sucursalDTO.getResponsableMatricula();

        oldNombre = sucursalDTO.getDireccion();

        // Panel de título
        JPanel titlePanel = new JPanel();
        titlePanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        titlePanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        JLabel titleLabel = new JLabel("Edicion de Sucursal");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16)); // Configura la fuente del título
        titlePanel.add(titleLabel);
        add(titlePanel, BorderLayout.NORTH);

        // Panel de contenido
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new GridLayout(0, 2, 10, 10)); // 10 pixels of gap between columns and rows
        contentPanel.setBorder(new EmptyBorder(10, 10, 10, 10)); // Margins around the whole content panel

        contentPanel.add(new JLabel("Direccion:"));
        nombreField = new JTextField(nombre);
        contentPanel.add(nombreField);

        contentPanel.add(new JLabel("Responsable:"));
        responsableField = new JTextField(Objects.toString(responsable));
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

        guardarButton.setEnabled(false);

        this.registrarSucursalEventos();
    }

    private void registrarSucursalEventos(){

        nombreField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                guardarButton.setEnabled(true);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                guardarButton.setEnabled(true);
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                guardarButton.setEnabled(true);
            }
        });

        responsableField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                guardarButton.setEnabled(true);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                guardarButton.setEnabled(true);
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                guardarButton.setEnabled(true);
            }
        });

        guardarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nombre = nombreField.getText();
                int responsable = Integer.parseInt(responsableField.getText());

                List<PeticionDTO> peticiones = new ArrayList<>();
                SucursalDTO sucursalDTO = new SucursalDTO(0, nombre, responsable, peticiones);

                try{
                    ControladorAtencion controladorAtencion = ControladorAtencion.getInstance();
                    controladorAtencion.updateSucursal(sucursalDTO, oldNombre);
                }catch (Exception er){
                    System.out.println(er.getMessage());
                }

                dispose();
            }
        });

        cancelButton.addActionListener(e->{dispose();});

    }
}
