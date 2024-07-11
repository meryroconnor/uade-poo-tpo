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
        String[] columnNames = {"Petición ID", "Paciente ID", "Práctica", "Resultado", "Sucursal", "Fecha de Carga"};
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

        this.asociarEventos();
        add(menuPanel, BorderLayout.SOUTH);
    }

    private void asociarEventos() {
        // Configurar el botón de filtrado
        filterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) { actualizarTablaConBusquedaFiltrada(); }
        });

        getAllButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {actualizarTablaConTodasLasPeticiones();}
        });

        // Configurar el botón de filtrar peticiones CRITICAS
        getPeticionesCriticasButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) { actualizarTablaConPeticionesCriticas(); }
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
                // Lógica para eliminar una petición
                System.out.println("Eliminar petición");
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
            if (paciente.getPeticionesDTO().size()!=0) {
                for (PeticionDTO peticion : paciente.getPeticionesDTO()) {
                    for (EstudioDTO estudio : peticion.getEstudiosDTO()) {
                        String resultado = controladorAtencion.showResultados(peticion.getPeticionID(), estudio.getCodigoEstudio());
                        String sucursal = controladorAtencion.obtenerSucursalOfPeticion(peticion.getPeticionID()).getDireccion();

                        //TODO: Resolver que pasa si value es null: Cannot invoke "java.lang.Float.floatValue()" because the return value of "Laboratorio.IndiceReservado.getLowLimit()" is null

                        Object[] rowData = new Object[]{
                                peticion.getPeticionID(),
                                paciente.getNombreApellido(),
                                estudio.getPracticaDTO().getNombrePractica(),
                                resultado,
                                sucursal,
                                peticion.getFechaCarga().toString()
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
        if (filterPatientId.getText().equals("")  || filterRequestId.getText().equals("")) {
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
                                            paciente.getNombreApellido(),
                                            estudio.getPracticaDTO().getNombrePractica(),
                                            resultado,
                                            sucursal
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
            if (paciente.getPeticionesDTO().size()!=0) {
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
                                    paciente.getNombreApellido(),
                                    estudio.getPracticaDTO().getNombrePractica(),
                                    resultado,
                                    sucursal
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


