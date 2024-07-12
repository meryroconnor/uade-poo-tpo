package vista;

import DTOs.PacienteDTO;
import DTOs.PeticionDTO;
import controlador.ControladorAtencion;
import controlador.ControladorPaciente;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EliminarPeticionDialog extends JDialog {
    private JTextField idTextField;
    private JButton eliminarButton;
    private JButton cancelButton;


    public EliminarPeticionDialog(Frame owner) {
        super(owner, "Eliminar Petición", true);
        setLayout(new BorderLayout());
        setSize(350, 180);

        // Panel de contenido
        JPanel contentPanel = new JPanel(new GridLayout(2, 1));
        contentPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        JLabel label = new JLabel("Ingrese ID de la Peticion que desea Eliminar: ");
        contentPanel.add(label);

        idTextField = new JTextField();
        contentPanel.add(idTextField);

        add(contentPanel, BorderLayout.CENTER);

        // Panel de botones
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        eliminarButton = new JButton("Eliminar");
        eliminarButton.setForeground(new Color(213, 0, 50));

        buttonPanel.add(eliminarButton);

        cancelButton = new JButton("Cancelar");
        buttonPanel.add(cancelButton);

        add(buttonPanel, BorderLayout.SOUTH);

        setLocationRelativeTo(owner);
        asociarEventos();

    }
    private void asociarEventos(){
        eliminarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String idText = idTextField.getText();
                if (!idText.isEmpty()) {
                    int id = Integer.parseInt(idText);
                    // Lógica para eliminar la petición con el ID capturado
                    eliminarPeticion(id);
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(EliminarPeticionDialog.this,
                            "El campo de ID no puede estar vacío.",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }

    private void eliminarPeticion(int id) {
        System.out.println("Eliminar petición con ID: " + id);
        ControladorAtencion controladorAtencion = ControladorAtencion.getInstance();
        controladorAtencion.deletePeticion(id);

    }
}


