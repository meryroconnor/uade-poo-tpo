package vista;

import DTOs.SucursalDTO;
import controlador.ControladorAtencion;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Objects;

public class SucursalesPanel extends JPanel {

    private JTextField sucursalField;
    private JComboBox<String> sucursalComboBox;
    private JButton editarButton;
    private JButton buscarButton;
    private JButton agregarButton;
    private JButton eliminarButton;
    private JTextArea outputArea;
    private JPanel inputPanel;
    private JPanel sendPanel;

    public SucursalesPanel() {
        setLayout(new BorderLayout());
        setSize(600, 300);

        JPanel inputPanel = new JPanel(new GridLayout(0, 2));

        JLabel titleLabel = new JLabel("Gestión de Sucursales");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        inputPanel.add(titleLabel);
        inputPanel.add(new JLabel()); // Espacio vacío para alinear correctamente

//        inputPanel.add(new JLabel("Sucursal ID:"));
//        sucursalField = new JTextField();
//        inputPanel.add(sucursalField);

        inputPanel.add(new JLabel("Sucursal:"));
        sucursalComboBox =new JComboBox<>(Objects.requireNonNull(getDireccionSucursal()));
        inputPanel.add(sucursalComboBox);

        inputPanel.add(new JLabel()); // Espacio vacío para alinear correctamente
        editarButton = new JButton("Editar Sucursal");
        editarButton.setIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("resources/edit.png"))));
        inputPanel.add(editarButton);

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
        editarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nombreSucursal = sucursalComboBox.getSelectedItem().toString();

                try {
                    ControladorAtencion controladorAtencion = ControladorAtencion.getInstance();
                    SucursalDTO sucursalDTO = controladorAtencion.getSucursalFromDireccion(nombreSucursal);

                    if (sucursalDTO != null){
                        EditSucursalDialog dialog = new EditSucursalDialog(JOptionPane.getFrameForComponent(SucursalesPanel.this), sucursalDTO);
                        dialog.setVisible(true);

                        List<SucursalDTO> sucursales = controladorAtencion.getSucursales();
                        sucursalComboBox.removeAllItems();
                        for (int i = 0; i < sucursales.size(); i++){
                            sucursalComboBox.addItem(sucursales.get(i).getDireccion());
                        }
                    }
                } catch (Exception err){
                    System.out.println(err.getMessage());
                }
            }
        });

        agregarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SucursalDialog dialog = new SucursalDialog(JOptionPane.getFrameForComponent(SucursalesPanel.this), sucursalComboBox);
                dialog.setVisible(true);
            }
        });

        // Action listener for "Eliminar Sucursal" button
        eliminarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String sucursal = (String) sucursalComboBox.getSelectedItem();

                try {
                    ControladorAtencion controladorAtencion = ControladorAtencion.getInstance();
                    int sucursalID = controladorAtencion.getSucursalFromDireccion(sucursal).getSucursalID();
                    String output = controladorAtencion.deleteSucursal(sucursalID);

                    outputArea.append("##Solicitud de Eliminacion: Sucursal "+sucursal+ "##\n");
                    outputArea.append(output+"\n\n");
                    sucursalComboBox.removeItem(sucursal);



                } catch (Exception err) {
                    outputArea.append("Error: " + err.getMessage() + "\n\n");
                    err.printStackTrace();
                }
            }
        });

        // Action listener for "Buscar Sucursal" button
        buscarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String sucursal = (String) sucursalComboBox.getSelectedItem();
                try {
                    ControladorAtencion controladorAtencion = ControladorAtencion.getInstance();
                    int sucursalID = controladorAtencion.getSucursalFromDireccion(sucursal).getSucursalID();
                    SucursalDTO sucursalDTO = controladorAtencion.getSucursal(sucursalID);

                    if (sucursalDTO != null) {
                        outputArea.append("Buscar Sucursal:\n");
                        outputArea.append("Direccion: " + sucursalDTO.getDireccion() + "\n");
                        outputArea.append("Sucursal ID: " + sucursalDTO.getSucursalID() + "\n");
                        outputArea.append("Mattricula del Responsable: " + sucursalDTO.getResponsableMatricula() + "\n\n");

                    } else {
                        outputArea.append("Sucursal NO ENCONTRADA\n\n");
                    }

                } catch (Exception err) {
                    outputArea.append("Error al buscar el sucursal: " + err.getMessage() + "\n\n");
                    err.printStackTrace();
                }
            }
        });
    }

    private String[] getDireccionSucursal(){
        ControladorAtencion controladorAtencion = ControladorAtencion.getInstance();

        List<SucursalDTO> sucursales = controladorAtencion.getSucursales();

        String[] vectorSucursales = new String[sucursales.size()];
        for (int i = 0; i < sucursales.size(); i++){
            vectorSucursales[i] = sucursales.get(i).getDireccion();
        }
        return vectorSucursales;
    }




//    metodo de controlador: implementado!
//    private SucursalDTO getSucursal(String direccion){
//        ControladorAtencion controladorAtencion = ControladorAtencion.getInstance();
//        List<SucursalDTO> sucursales = controladorAtencion.getSucursales();
//        SucursalDTO sucursalEncontrada = null;
//
//        for (SucursalDTO sucursal : sucursales){
//            if (Objects.equals(sucursal.getDireccion(), direccion)) {
//                sucursalEncontrada = sucursal;
//            }
//        }
//        return sucursalEncontrada;
//    }


    }