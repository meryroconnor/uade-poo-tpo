package vista;

import DTOs.*;
import controlador.ControladorPaciente;
import controlador.ControladorPractica;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PracticaDialog extends JDialog {
    private JTextField nombreField, qtyHorasField, lowIRField, highIRField, valueIRField, lowICField, highICField, valueICField;
    private JComboBox<String> tipoIRComboBox, tipoICComboBox, practicasComboBox;
    private JButton guardarButton, cancelButton;
    private JPanel irPanel, icPanel;

    public PracticaDialog(Frame owner, JComboBox practicasComboBox) {
        super(owner, "Agregar Práctica", true);
        this.practicasComboBox=practicasComboBox;
        setLayout(new BorderLayout());
        setSize(600, 480);

        // Panel de título
        JPanel titlePanel = new JPanel();
        titlePanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        titlePanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        JLabel titleLabel = new JLabel("Registro de Práctica");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16)); // Configura la fuente del título
        titlePanel.add(titleLabel);
        add(titlePanel, BorderLayout.NORTH);

        // Panel de contenido
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BorderLayout());

        JPanel practicaPanel = new JPanel();
        practicaPanel.setLayout(new GridLayout(0, 2)); // 10 pixels of gap between columns and rows
        practicaPanel.setBorder(new EmptyBorder(10, 10, 10, 10)); // Margins around the whole content panel

        practicaPanel.add(new JLabel("Nombre de la Práctica:"));
        nombreField = new JTextField();
        practicaPanel.add(nombreField);

        practicaPanel.add(new JLabel("Cantidad de hs para obtener resultado:"));
        qtyHorasField = new JTextField();
        practicaPanel.add(qtyHorasField);

        centerPanel.add(practicaPanel, BorderLayout.NORTH);

        JPanel indicesPanel = new JPanel();
        indicesPanel.setLayout(new GridLayout(0, 1));
        indicesPanel.setBorder(new EmptyBorder(10, 10, 10, 10)); // Margins around the whole content panel
        JLabel irTitle = new JLabel("Indice Reservado:");
        irTitle.setFont(new Font("Arial", Font.BOLD, 14));
        indicesPanel.add(irTitle);

        tipoIRComboBox = new JComboBox<>(new String[]{"Medir con valor (texto)", "Medir con rango (numérico)"});
        indicesPanel.add(tipoIRComboBox);
        indicesPanel.add(new JLabel());

        irPanel = new JPanel(new GridLayout(0,1));
        valueIRField = new JTextField();
        lowIRField = new JTextField();
        highIRField = new JTextField();

        updateIRPanel("Medir con valor (texto)");
        indicesPanel.add(irPanel);

        indicesPanel.add(new JLabel());
        JLabel icTitle = new JLabel("Indice Critico:");
        icTitle.setFont(new Font("Arial", Font.BOLD, 14));
        indicesPanel.add(icTitle);
        tipoICComboBox = new JComboBox<>(new String[]{"Medir con valor (texto)", "Medir con rango (numérico)"});
        indicesPanel.add(tipoICComboBox);
        indicesPanel.add(new JLabel());

        icPanel = new JPanel(new GridLayout(0, 2));
        valueICField = new JTextField();
        lowICField = new JTextField();
        highICField = new JTextField();

        updateICPanel("Medir con valor (texto)");
        indicesPanel.add(icPanel);
//
        centerPanel.add(indicesPanel, BorderLayout.CENTER);
        add(centerPanel, BorderLayout.CENTER);

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

    private void updateIRPanel(String tipo) {
        irPanel.removeAll();
        if ("Medir con valor (texto)".equals(tipo)) {
            irPanel.setLayout(new GridLayout(0,2));
            irPanel.add(new JLabel("Valor (texto):"));
            irPanel.add(valueIRField);
        } else {
            irPanel.setLayout(new GridLayout(0,3));
            irPanel.add(new JLabel("Rango entre x1 - x2:"));
            irPanel.add(lowIRField);
            irPanel.add(highIRField);
        }
        irPanel.revalidate();
        irPanel.repaint();
    }

    private void updateICPanel(String tipo) {
        icPanel.removeAll();
        if ("Medir con valor (texto)".equals(tipo)) {
            icPanel.setLayout(new GridLayout(0, 2));
            icPanel.add(new JLabel("Valor (texto):"));
            icPanel.add(valueICField);
        } else {
            icPanel.setLayout(new GridLayout(0, 3));
            icPanel.add(new JLabel("Rango entre x1 - x2:"));
            icPanel.add(lowICField);
            icPanel.add(highICField);
        }
        icPanel.revalidate();
        icPanel.repaint();
    }

    private void registrarPacienteEventos() {
        tipoIRComboBox.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                updateIRPanel((String) e.getItem());
            }
        });

        tipoICComboBox.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                updateICPanel((String) e.getItem());
            }
        });

        guardarButton.addActionListener(e -> {
            String nombre = nombreField.getText();
            int qtyHoras = Integer.parseInt(qtyHorasField.getText());
            Float lowIR;
            Float highIR;
            String valueIR;
            Float lowIC;
            Float highIC;
            String valueIC;

            String  selectedICtipo = Objects.requireNonNull(tipoICComboBox.getSelectedItem()).toString();
            String  selectedIRtipo = Objects.requireNonNull(tipoIRComboBox.getSelectedItem()).toString();

            if (selectedIRtipo.equals("Medir con valor (texto)")){
                lowIR = 0f;
                highIR = 0f;
                valueIR = valueIRField.getText();
            } else {
                lowIR = Float.parseFloat(lowIRField.getText());
                highIR = Float.parseFloat(highIRField.getText());
                valueIR = null;
            }

            if (selectedICtipo.equals("Medir con valor (texto)")){
                lowIC = 0f;
                highIC = 0f;
                valueIC = valueICField.getText();
            } else {
                lowIC = Float.parseFloat(lowICField.getText());
                highIC = Float.parseFloat(highICField.getText());
                valueIC = null;
            }

            IndiceCriticoDTO ic = new IndiceCriticoDTO(valueIC,lowIC,highIC);
            IndiceReservadoDTO ir = new IndiceReservadoDTO(valueIR,lowIR,highIR);
            PracticaDTO practicaDTO = new PracticaDTO(0, nombre, ir, ic, qtyHoras);

            try {
                ControladorPractica controladorPractica = ControladorPractica.getInstance();
                controladorPractica.createPractica(practicaDTO);

                practicasComboBox.addItem(nombre);
            }catch (Exception err) {
                System.out.println(err.getMessage());
            }

            dispose();
        });

        cancelButton.addActionListener(e -> dispose());
    }
}

