package vista;

import DTOs.PracticaDTO;
import controlador.ControladorAtencion;
import controlador.ControladorPaciente;
import controlador.ControladorPractica;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Objects;

public class PracticaPanel extends JPanel {

    private JComboBox<String> practicaComboBox;
    private JButton buscarButton;
    private JButton agregarButton;
    private JButton eliminarButton;
    private JTextArea outputArea;

    public PracticaPanel() {
        setLayout(new BorderLayout());
        setSize(600, 300);

        JPanel inputPanel = new JPanel(new GridLayout(0, 2));

        JLabel titleLabel = new JLabel("Gestión de Prácticas");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        inputPanel.add(titleLabel);
        inputPanel.add(new JLabel()); // Espacio vacío para alinear correctamente


        inputPanel.add(new JLabel("Práctica:"));
        practicaComboBox = new JComboBox<>(Objects.requireNonNull(getNombrePracticas()));
        inputPanel.add(practicaComboBox);


        JPanel sendPanel = new JPanel(new GridLayout(2, 3));

        JLabel manageLabel = new JLabel(" ");
        manageLabel.setFont(new Font("Arial", Font.BOLD, 18));
        manageLabel.setBorder(new EmptyBorder(10, 0, 10, 0)); // Márgenes superior e inferior
        sendPanel.add(manageLabel);
        sendPanel.add(new JLabel());
        sendPanel.add(new JLabel());

        agregarButton = new JButton("Agregar Práctica");
        agregarButton.setForeground(new Color(0, 141, 213));
        agregarButton.setFont(new Font("Lucida Bright", Font.PLAIN, 13));
        agregarButton.setIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("resources/plus.png"))));

        eliminarButton = new JButton("Eliminar Práctica");
        eliminarButton.setForeground(new Color(213, 0, 50));
        eliminarButton.setFont(new Font("Lucida Bright", Font.PLAIN, 13));
        eliminarButton.setIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("resources/trash.png"))));

        buscarButton = new JButton("Buscar Práctica");
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
        // Action listener for "Agregar Practica" button
        agregarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PracticaDialog dialog = new PracticaDialog(JOptionPane.getFrameForComponent(PracticaPanel.this), practicaComboBox);
                dialog.setVisible(true);
            }
        });

        // Action listener for "Eliminar Practica" button
        eliminarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String practica = (String) practicaComboBox.getSelectedItem();
                int practicaID = getPractica(practica).getCodigoPractica();

                try {
                    ControladorPractica controladorPractica = ControladorPractica.getInstance();
                    String output = controladorPractica.deletePractica(practicaID);

                    outputArea.append("#### Solicitud de Eliminación: Practica "+practica+ " ####\n");
                    outputArea.append(output+"\n\n");
                    if (output.contains("fue eliminada")) {
                        practicaComboBox.removeItem(practica);
                    }


                } catch (Exception err) {
                    outputArea.append("Error: " + err.getMessage() + "\n\n");
                    err.printStackTrace();
                }
            }
        });

        // Action listener for "Buscar Practica" button
        buscarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String practicaNombre = (String) practicaComboBox.getSelectedItem();
                PracticaDTO practicaDTO = getPractica(practicaNombre);

                try {
                    if (practicaDTO != null) {
                        outputArea.append("####### Buscar Práctica: #######\n");
                        outputArea.append("Denominación: " + practicaDTO.getNombrePractica() + "\n");
                        outputArea.append("Práctica ID: " + practicaDTO.getCodigoPractica() + "\n");
                        outputArea.append("Cantidad de horas que demora: " + practicaDTO.getCantidadHorasDemora() + "\n\n");
                        outputArea.append("** Indice Critico: **" + "\n");
                        if(practicaDTO.getIndiceCriticoDTO().getValue()!= null) {
                            outputArea.append(">> Valor: " + practicaDTO.getIndiceCriticoDTO().getValue() + "\n\n");
                        } else {
                            outputArea.append(">> Rango: " + practicaDTO.getIndiceCriticoDTO().getLowLimit() +"-"+ practicaDTO.getIndiceCriticoDTO().getHighLimit() +"\n\n");
                        }
                        outputArea.append("** Indice Reservado: **" + "\n");
                        if(practicaDTO.getIndiceReservadoDTO().getValue()!= null) {
                            outputArea.append(">> Valor: " + practicaDTO.getIndiceReservadoDTO().getValue() +"\n\n");
                        } else {
                            outputArea.append(">> Rango: " + practicaDTO.getIndiceReservadoDTO().getLowLimit() + "-" + practicaDTO.getIndiceReservadoDTO().getHighLimit() + "\n\n");
                        }

                    } else {
                        outputArea.append("Práctica NO ENCONTRADA\n\n");
                    }

                } catch (Exception err) {
                    outputArea.append("Error al buscar el sucursal: " + err.getMessage() + "\n\n");
                    err.printStackTrace();
                }
            }
        });
    }
    private String[] getNombrePracticas(){
        ControladorPractica controladorPractica = ControladorPractica.getInstance();

        List<PracticaDTO> practicas = controladorPractica.getPracticas();

        String[] vectorPracticas = new String[practicas.size()];
        for (int i = 0; i < practicas.size(); i++){
            vectorPracticas[i] = practicas.get(i).getNombrePractica();
        }
        return vectorPracticas;
    }
    private PracticaDTO getPractica(String nombrePractica){
        ControladorPractica controladorPractica = ControladorPractica.getInstance();
        List<PracticaDTO> practicas = controladorPractica.getPracticas();
        PracticaDTO practicaEncontrada = null;

        for (PracticaDTO practica : practicas){
            if (Objects.equals(practica.getNombrePractica(), nombrePractica)) {
                practicaEncontrada = practica;
            }
        }
        return practicaEncontrada;
    }
}