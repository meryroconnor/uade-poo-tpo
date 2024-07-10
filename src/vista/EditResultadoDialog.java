package vista;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import controlador.ControladorAtencion;
import DTOs.PracticaDTO;
import controlador.ControladorPractica;

public class EditResultadoDialog extends JDialog {
    private int peticionID;
    private String nombrePractica;
    private int row;
    private DefaultTableModel tableModel;

    public EditResultadoDialog(int peticionID, String nombrePractica, int row, DefaultTableModel tableModel) {
        super((Frame) null, "Registrar/Modificar Resultado", true);
        this.peticionID = peticionID;
        this.nombrePractica = nombrePractica;
        this.row = row;
        this.tableModel = tableModel;

        setLayout(new BorderLayout());
        //setLayout(new GridLayout(3, 1));

        // Panel de título
        JPanel titlePanel = new JPanel();
        titlePanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        titlePanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        JLabel titleLabel = new JLabel("Editar Resultado");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16)); // Configura la fuente del título
        titlePanel.add(titleLabel);

        JLabel subtitleLabel = new JLabel("Práctica: " + nombrePractica + " | Petición ID: " + peticionID);
        subtitleLabel.setFont(new Font("Arial", Font.PLAIN, 12)); // Configura la fuente del subtítulo
        titlePanel.add(subtitleLabel);
        add(titlePanel, BorderLayout.NORTH);

        // Panel de contenido
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new GridLayout(0, 1, 10, 10)); // 10 pixels of gap between columns and rows
        contentPanel.setBorder(new EmptyBorder(10, 10, 10, 10)); // Margins around the whole content panel

        contentPanel.add(new JLabel("Indique resultado:"));
        JTextField resultadoField = new JTextField();
        contentPanel.add(resultadoField);
        add(contentPanel, BorderLayout.CENTER);

        // Panel de botones
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        JButton saveButton = new JButton("Guardar");
        saveButton.setForeground(new Color(0, 141, 213));

        JButton cancelButton = new JButton("Cancelar");

        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);

        add(buttonPanel, BorderLayout.SOUTH);


        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String resultado = resultadoField.getText();
                createResult(peticionID, nombrePractica, resultado);
                tableModel.setValueAt(resultado, row, 2); // Actualizar el resultado en la tabla
                dispose();
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        setSize(1200, 250);
        setLocationRelativeTo(null);
    }

    private void createResult(int petitionID, String nombrePractica, Object result) {
        Float valorResultado;
        String descripcion;

        try {
            valorResultado = Float.parseFloat(result.toString());
            descripcion = null;
        } catch (Exception e) {
            descripcion = result.toString();
            valorResultado = 0f;
        }

        PracticaDTO practica = getPractica(nombrePractica);
        ControladorAtencion controladorAtencion = ControladorAtencion.getInstance();
        controladorAtencion.addResultadoToEstudio(petitionID, practica.getCodigoPractica(), valorResultado, descripcion);

        System.out.println("Crear resultado para Petición ID: " + petitionID + ", Práctica: " + nombrePractica + ", Resultado: " + result);
    }

    private PracticaDTO getPractica(String nombrePractica) {
        ControladorPractica controladorPractica = ControladorPractica.getInstance();
        java.util.List<PracticaDTO> practicas = controladorPractica.getPracticas();
        PracticaDTO practicaEncontrada = null;

        for (PracticaDTO practica : practicas) {
            if (java.util.Objects.equals(practica.getNombrePractica(), nombrePractica)) {
                practicaEncontrada = practica;
            }
        }
        return practicaEncontrada;
    }
}
