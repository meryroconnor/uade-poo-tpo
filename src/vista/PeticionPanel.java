package vista;

import DTOs.*;
import controlador.ControladorAtencion;
import controlador.ControladorPaciente;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.List;
import java.util.Objects;

public class PeticionPanel extends JPanel {
    private JTextField filterPatientId;
    private JTextField filterRequestId;
    private JButton filterButton;
    private JButton getAllButton;

    private JButton getPeticionesCriticasButton;
    private JButton settingsButton;
    private JButton deleteButton;
    private JButton updateButton;
    private JTable table;
    private DefaultTableModel tableModel;

    public PeticionPanel() {
        setLayout(new BorderLayout());

        // Panel de filtros
        JPanel filterPanel = new JPanel(new GridLayout(6, 2));

        JLabel filterLabel = new JLabel("Consultar Peticiones y Resultados");
        filterLabel.setFont(new Font("Arial", Font.BOLD, 18));
        filterPanel.add(filterLabel);
        filterPanel.add(new JLabel()); // Espacio vacío para alinear correctamente

        filterPanel.add(new JLabel("Filtrar por ID de Paciente:"));
        filterPatientId = new JTextField();
        filterPanel.add(filterPatientId);
        filterPanel.add(new JLabel("Filtrar por ID de Petición:"));
        filterRequestId = new JTextField();
        filterPanel.add(filterRequestId);

        filterPanel.add(new JLabel());
        filterButton = new JButton("Busqueda Filtrada");
        filterButton.setFont(new Font("Lucida Bright", Font.PLAIN, 13));
        filterButton.setIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("resources/search1.png"))));
        filterPanel.add(filterButton);

        filterPanel.add(new JLabel());
        getAllButton = new JButton("Ver todas las Peticiones");
        getAllButton.setFont(new Font("Lucida Bright", Font.PLAIN, 13));
        getAllButton.setIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("resources/search1.png"))));
        filterPanel.add(getAllButton);

        filterPanel.add(new JLabel());
        getPeticionesCriticasButton = new JButton("Ver Peticiones Criticas");
        getPeticionesCriticasButton.setFont(new Font("Lucida Bright", Font.PLAIN, 13));
        getPeticionesCriticasButton.setIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("resources/alarm.png"))));
        filterPanel.add(getPeticionesCriticasButton);

        add(filterPanel, BorderLayout.NORTH);

        // Modelo de la tabla
        String[] columnNames = {"Petición ID", "Paciente ID", "Nombre Paciente", "Práctica", "Resultado", "Sucursal", "Fecha de Carga", "Fecha de Terminacion Estimada"};
        Object[][] data = {}; // Data inicial vacía
        tableModel = new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                // Hacer que ninguna columna sea editable
                return false;
            }
        };
        table = new JTable(tableModel);

        // Ajustar el color de fondo y el color del texto del encabezado de la tabla
        JTableHeader header = table.getTableHeader();
        header.setBackground(Color.darkGray);
        header.setForeground(Color.white);

        // ScrollPane para la tabla
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // Panel de Gestionar Peticion
        JPanel menuPanel = new JPanel(new GridLayout(2, 3));

        JLabel manageLabel = new JLabel("Gestionar Peticiones");
        manageLabel.setFont(new Font("Arial", Font.BOLD, 18));
        manageLabel.setBorder(new EmptyBorder(10, 0, 10, 0)); // Márgenes superior e inferior
        menuPanel.add(manageLabel);
        menuPanel.add(new JLabel());
        menuPanel.add(new JLabel());

        settingsButton = new JButton("Crear Petición");
        settingsButton.setForeground(new Color(0, 141, 213));
        settingsButton.setFont(new Font("Lucida Bright", Font.PLAIN, 13));
        settingsButton.setIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("resources/plus.png"))));

        deleteButton = new JButton("Eliminar Petición");
        deleteButton.setForeground(new Color(213, 0, 50));
        deleteButton.setFont(new Font("Lucida Bright", Font.PLAIN, 13));
        deleteButton.setIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("resources/trash.png"))));

        updateButton = new JButton("Modificar Petición");
        updateButton.setFont(new Font("Lucida Bright", Font.PLAIN, 13));
        updateButton.setIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("resources/edit.png"))));

        menuPanel.add(settingsButton);
        menuPanel.add(deleteButton);
        menuPanel.add(updateButton);

        updateButton.setEnabled(false);

        this.asociarEventos();
        add(menuPanel, BorderLayout.SOUTH);
    }

    private void asociarEventos() {
        // Configurar el botón de filtrado
        filterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateButton.setEnabled(false);
                actualizarTablaConBusquedaFiltrada();
            }
        });

        getAllButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateButton.setEnabled(false);
                actualizarTablaConTodasLasPeticiones();
            }
        });

        // Configurar el botón de filtrar peticiones CRITICAS
        getPeticionesCriticasButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateButton.setEnabled(false);
                actualizarTablaConPeticionesCriticas();
            }
        });

        // Configurar el botón de creación
        settingsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Lógica para crear una nueva petición
                System.out.println("Crear nueva petición");
                RegisterPetitionDialog dialog = new RegisterPetitionDialog(JOptionPane.getFrameForComponent(PeticionPanel.this));
                dialog.setVisible(true);
            }
        });

        // Configurar el botón de eliminación
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Mostrar diálogo para eliminar una petición
                EliminarPeticionDialog dialog = new EliminarPeticionDialog(JOptionPane.getFrameForComponent(PeticionPanel.this));
                dialog.setVisible(true);
                updateButton.setEnabled(false);
            }
        });

        table.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                updateButton.setEnabled(true);
            }

            @Override
            public void focusLost(FocusEvent e) {

            }
        });

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean selected = Objects.nonNull(table.getSelectedRow());

                if (selected){
                    int row = table.getSelectedRow();
                    int peticionID = Integer.parseInt(table.getValueAt(row, 0).toString());
                    int pacienteID = Integer.parseInt(table.getValueAt(row, 1).toString());
                    PeticionDTO peticionDTO = null;
                    String DNI;
                    String sexo;
                    String sucursal;

                    try{
                        ControladorAtencion controladorAtencion = ControladorAtencion.getInstance();
                        ControladorPaciente controladorPaciente = ControladorPaciente.getInstance();

                        peticionDTO = controladorAtencion.getPeticion(peticionID);
                        PacienteDTO pacienteDTO = controladorPaciente.getPacienteFromPacienteID(pacienteID);
                        SucursalDTO sucursalDTO = controladorAtencion.obtenerSucursalOfPeticion(peticionID);

                        DNI = pacienteDTO.getDNI();
                        sexo = pacienteDTO.getSexo();
                        sucursal = sucursalDTO.getDireccion();

                        List<EstudioDTO> estudioDTOS = peticionDTO.getEstudiosDTO();
                        for (EstudioDTO estudio : estudioDTOS){
                            if (estudio.getResultadoDTO().getDescripcionResultado() != null || estudio.getResultadoDTO().getValorResultado() != 0){
                                throw new Exception("No puede modificar una peticion con resultados finalizados");
                            }
                        }


                        JDialog dialog = new EditPeticionDialog(JOptionPane.getFrameForComponent(PeticionPanel.this), peticionDTO, DNI, sexo, sucursal);
                        dialog.setVisible(true);
                    }catch (Exception err){
                        JOptionPane.showMessageDialog(PeticionPanel.this,
                                err.getMessage(),
                                "Error",
                                JOptionPane.ERROR_MESSAGE);
                    }


                } else {
                    JOptionPane.showMessageDialog(PeticionPanel.this,
                            "Ninguna peticion seleccionada",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                }


            }

        });

    }

    // Método para actualizar la tabla con todas las peticiones
    private void actualizarTablaConTodasLasPeticiones() {
        ControladorPaciente controladorPaciente = ControladorPaciente.getInstance();
        List<PacienteDTO> pacientes = controladorPaciente.getPacientes();
        ControladorAtencion controladorAtencion = ControladorAtencion.getInstance();

        tableModel.setRowCount(0); // Limpia la tabla antes de agregar nuevas filas
        for (PacienteDTO paciente : pacientes) {
            if (paciente.getPeticionesDTO().size() != 0) {
                for (PeticionDTO peticion : paciente.getPeticionesDTO()) {
                    for (EstudioDTO estudio : peticion.getEstudiosDTO()) {
                        String resultado = controladorAtencion.showResultados(peticion.getPeticionID(), estudio.getCodigoEstudio());
                        String sucursal = controladorAtencion.obtenerSucursalOfPeticion(peticion.getPeticionID()).getDireccion();


                        Object[] rowData = new Object[]{
                                peticion.getPeticionID(),
                                paciente.getPacienteID(),
                                paciente.getNombreApellido(),
                                estudio.getPracticaDTO().getNombrePractica(),
                                resultado,
                                sucursal,
                                peticion.getFechaCarga().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM, FormatStyle.SHORT)),
                                peticion.getFechaTerminacionEstimada().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM, FormatStyle.SHORT))
                        };
                        tableModel.addRow(rowData);
                    }
                }
            }
        }
    }

    // Método para actualizar la tabla Busqueda filtrada de peticiones
    //TODO: Permitir filtras solo por paciente o solo por peticion ... si ambos estan nulos informa error.
    private void actualizarTablaConBusquedaFiltrada() {
        if (filterPatientId.getText().equals("") || filterRequestId.getText().equals("")) {
            // Mostrar un mensaje de error si no se encuentra el paciente
            JOptionPane.showMessageDialog(this,
                    "Para la busqueda filtrada debe ingresar Paciente ID y Peticion!",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        } else {
            int pacienteID = Integer.parseInt(filterPatientId.getText());
            int peticionID = Integer.parseInt(filterRequestId.getText());

            ControladorPaciente controladorPaciente = ControladorPaciente.getInstance();
            List<PacienteDTO> pacientes = controladorPaciente.getPacientes();
            ControladorAtencion controladorAtencion = ControladorAtencion.getInstance();

            tableModel.setRowCount(0); // Limpia la tabla antes de agregar nuevas filas
            for (PacienteDTO paciente : pacientes) {
                if (paciente.getPacienteID() == pacienteID) {
                    if (paciente.getPeticionesDTO().size() != 0) {
                        for (PeticionDTO peticion : paciente.getPeticionesDTO()) {
                            if (peticion.getPeticionID() == peticionID) {
                                for (EstudioDTO estudio : peticion.getEstudiosDTO()) {
                                    String resultado = controladorAtencion.showResultados(peticion.getPeticionID(), estudio.getCodigoEstudio());
                                    String sucursal = controladorAtencion.obtenerSucursalOfPeticion(peticion.getPeticionID()).getDireccion();

                                    Object[] rowData = new Object[]{
                                            peticion.getPeticionID(),
                                            paciente.getPacienteID(),
                                            paciente.getNombreApellido(),
                                            estudio.getPracticaDTO().getNombrePractica(),
                                            resultado,
                                            sucursal,
                                            peticion.getFechaCarga().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM, FormatStyle.SHORT)),
                                            peticion.getFechaTerminacionEstimada().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM, FormatStyle.SHORT))
                                    };
                                    tableModel.addRow(rowData);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private void actualizarTablaConPeticionesCriticas() {
        ControladorPaciente controladorPaciente = ControladorPaciente.getInstance();
        List<PacienteDTO> pacientes = controladorPaciente.getPacientes();
        ControladorAtencion controladorAtencion = ControladorAtencion.getInstance();

        tableModel.setRowCount(0); // Limpia la tabla antes de agregar nuevas filas
        for (PacienteDTO paciente : pacientes) {
            if (paciente.getPeticionesDTO().size() != 0) {
                for (PeticionDTO peticion : paciente.getPeticionesDTO()) {
                    for (EstudioDTO estudio : peticion.getEstudiosDTO()) {
                        String is_critico = controladorAtencion.showResultados(peticion.getPeticionID(), estudio.getCodigoEstudio());
                        String sucursal = controladorAtencion.obtenerSucursalOfPeticion(peticion.getPeticionID()).getDireccion();
                        if (is_critico.equals("Resultado Critico contactar Paciente")) {
                            ResultadoDTO resultadoDTO = controladorAtencion.getResultado(peticion.getPeticionID(), estudio.getCodigoEstudio());
                            String resultado;
                            if (resultadoDTO.getDescripcionResultado() != null) {
                                resultado = resultadoDTO.getDescripcionResultado();
                            } else {
                                resultado = Objects.toString(resultadoDTO.getValorResultado());
                            }
                            Object[] rowData = new Object[]{
                                    peticion.getPeticionID(),
                                    paciente.getPacienteID(),
                                    paciente.getNombreApellido(),
                                    estudio.getPracticaDTO().getNombrePractica(),
                                    resultado,
                                    sucursal,
                                    peticion.getFechaCarga().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM, FormatStyle.SHORT)),
                                    peticion.getFechaTerminacionEstimada().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM, FormatStyle.SHORT))
                            };
                            tableModel.addRow(rowData);
                        }
                    }
                }
            }
        }
    }

    public void addRow(Object[] rowData) {
        tableModel.addRow(rowData);
    }


}


