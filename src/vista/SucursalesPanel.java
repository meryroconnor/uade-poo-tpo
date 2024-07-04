package vista;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

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


        JPanel sendPanel = new JPanel(new GridLayout(2, 3));

        JLabel manageLabel = new JLabel(" ");
        manageLabel.setFont(new Font("Arial", Font.BOLD, 18));
        manageLabel.setBorder(new EmptyBorder(10, 0, 10, 0)); // Márgenes superior e inferior
        sendPanel.add(manageLabel);
        sendPanel.add(new JLabel());
        sendPanel.add(new JLabel());

        agregarButton = new JButton("Agregar Sucursal");
        agregarButton.setForeground(new Color(0, 141, 213));
        agregarButton.setFont(new Font("Lucida Bright", Font.PLAIN, 13));
        agregarButton.setIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("resources/plus.png"))));

        eliminarButton = new JButton("Eliminar Sucursal");
        eliminarButton.setForeground(new Color(213, 0, 50));
        eliminarButton.setFont(new Font("Lucida Bright", Font.PLAIN, 13));
        eliminarButton.setIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("resources/trash.png"))));

        buscarButton = new JButton("Buscar Sucursal");
        buscarButton.setFont(new Font("Lucida Bright", Font.PLAIN, 13));
        buscarButton.setIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("resources/search1.png"))));

        sendPanel.add(agregarButton);
        sendPanel.add(eliminarButton);
        sendPanel.add(buscarButton);

        add(inputPanel, BorderLayout.NORTH);
        add(sendPanel, BorderLayout.SOUTH);

        outputArea = new JTextArea();
        add(new JScrollPane(outputArea), BorderLayout.CENTER);

        this.asociarEventos();

    }

    private void asociarEventos() {
        // Action listener for "Agregar Sucursal" button
        agregarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SucursalDialog dialog = new SucursalDialog(JOptionPane.getFrameForComponent(SucursalesPanel.this));
                dialog.setVisible(true);
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