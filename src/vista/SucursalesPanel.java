package vista;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SucursalesPanel extends JPanel {

    private JTextField sucursalField;
    private JComboBox<String> osComboBox;
    private JButton buscarButton;
    private JButton agregarButton;
    private JButton eliminarButton;
    private JTextArea outputArea;
    private JPanel inputPanel;
    private JPanel sendPanel;

    public SucursalesPanel() {
        setLayout(new BorderLayout());
        setSize(600, 300);

        JPanel inputPanel = new JPanel(new GridLayout(3, 2));

        inputPanel.add(new JLabel("Sucursal ID:"));
        sucursalField = new JTextField();
        inputPanel.add(sucursalField);

        inputPanel.add(new JLabel("Sucursal:"));
        osComboBox = new JComboBox<>(new String[] {"Sucursal Barrio Norte", "Sucursal Belgrano"});
        inputPanel.add(osComboBox);


        JPanel sendPanel = new JPanel(new GridLayout(1, 4));
        buscarButton = new JButton("Buscar Sucursal");
        agregarButton = new JButton("Agregar Sucursal");
        eliminarButton = new JButton("Eliminar Sucursal");
        sendPanel.add(buscarButton);
        sendPanel.add(agregarButton);
        sendPanel.add(eliminarButton);

        add(inputPanel, BorderLayout.NORTH);
        add(sendPanel, BorderLayout.SOUTH);

        outputArea = new JTextArea();
        add(new JScrollPane(outputArea), BorderLayout.CENTER);

        // Action listener for "Agregar Sucursal" button
        agregarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String sucursalID = sucursalField.getText();
                String sucursal = (String) osComboBox.getSelectedItem();
                outputArea.append("Agregar Sucursal:\n");
                outputArea.append("Sucursal ID: " + sucursalID + "\n");
                outputArea.append("Obra Social: " + sucursal + "\n\n");
            }
        });

        // Action listener for "Eliminar Sucursal" button
        eliminarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String sucursalID = sucursalField.getText();
                String sucursal = (String) osComboBox.getSelectedItem();
                outputArea.append("Eliminar Sucursal:\n");
                outputArea.append("Sucursal ID: " + sucursalID + "\n");
                outputArea.append("Obra Social: " + sucursal + "\n\n");
            }
        });

        // Action listener for "Buscar Sucursal" button
        buscarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String sucursalID = sucursalField.getText();
                String sucursal = (String) osComboBox.getSelectedItem();
                outputArea.append("Buscar Sucursal:\n");
                outputArea.append("Sucursal ID: " + sucursalID + "\n");
                outputArea.append("Obra Social: " + sucursal + "\n\n");
            }
        });


    }
}