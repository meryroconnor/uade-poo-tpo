package vista;

import DTOs.EstudioDTO;
import DTOs.PacienteDTO;
import DTOs.PeticionDTO;
import controlador.ControladorAtencion;
import controlador.ControladorPaciente;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Objects;

public class LabPanel extends JPanel {
    private JTextField filterRequestId;
    private JButton getAllButton;
    private JTable table;
    private DefaultTableModel tableModel;

    public LabPanel() {
        setLayout(new BorderLayout());

        // Panel de filtros
        JPanel filterPanel = new JPanel(new GridLayout(4, 2));
        filterPanel.add(new JLabel("Filtrar por ID de Petición:"));
        filterRequestId = new JTextField();
        filterPanel.add(filterRequestId);


        filterPanel.add(new JLabel());
        getAllButton = new JButton("Buscar Petición");
        getAllButton.setFont(new Font("Lucida Bright", Font.PLAIN, 13));
        getAllButton.setIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("resources/search1.png"))));
        filterPanel.add(getAllButton);


        JLabel titleLabel = new JLabel("Carga Interactiva de Resultados:");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        filterPanel.add(titleLabel);
        filterPanel.add(new JLabel()); // Espacio vacío para alinear correctamente
        filterPanel.add(new JLabel());

        add(filterPanel, BorderLayout.NORTH);

        // Modelo de la tabla
        String[] columnNames = {"Petición ID", "Práctica", "Resultado"};
        Object[][] data = {}; // Data inicial vacía
        tableModel = new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                // Hacer que solo la columna "Resultado" sea editable
                return column == 2; // Index 2 corresponde a la columna "Resultado"
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

        this.asociarEventos();
    }

    private void asociarEventos() {
        // Agregar el TableModelListener
        tableModel.addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                if (e.getType() == TableModelEvent.UPDATE && e.getColumn() == 2) { // Índice 2 para la columna "Resultado"
                    int row = e.getFirstRow();
                    int column = e.getColumn();
                    Object data = tableModel.getValueAt(row, column);
                    Object petitionID = tableModel.getValueAt(row, 0); // la Petición ID está en la columna 0
                    Object practiceID = tableModel.getValueAt(row, 1); // la Práctica está en la columna 2
                    createResult(petitionID, practiceID, data);
                }
            }
        });

        // Configurar el botón de filtrado
        getAllButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) { actualizarTablaConBusquedaFiltrada();
            }
        });
    }

    private void createResult(Object petitionID, Object practiceID, Object result) {
        // tenemos que convertir el nombre de la practica en su id
        // tenemos que entener que cargo si valor o descripcion
//        Float valorResultado;
//        String descripcion;
//
//        Objects.toString(result);

        ControladorAtencion controladorAtencion = ControladorAtencion.getInstance();
        controladorAtencion.addResultadoToEstudio(petitionID, practiceID, valorResultado, descripcion);

        System.out.println("Crear resultado para Petición ID: " + petitionID + ", Práctica: " + practiceID + ", Resultado: " + result);
    }

    public void addRow(Object[] rowData) {
        tableModel.addRow(rowData);
    }

    private void actualizarTablaConBusquedaFiltrada() {
        try {
            int peticionID = Integer.parseInt(filterRequestId.getText());

            ControladorPaciente controladorPaciente = ControladorPaciente.getInstance();
            List<PacienteDTO> pacientes = controladorPaciente.getPacientes();
            ControladorAtencion controladorAtencion = ControladorAtencion.getInstance();

            tableModel.setRowCount(0); // Limpia la tabla antes de agregar nuevas filas
            for (PacienteDTO paciente : pacientes) {
                if (paciente.getPeticionesDTO().size() != 0) {
                    for (PeticionDTO peticion : paciente.getPeticionesDTO()) {
                        if (peticion.getPeticionID() == peticionID) {
                            for (EstudioDTO estudio : peticion.getEstudiosDTO()) {
                                String resultado = controladorAtencion.showResultados(peticion.getPeticionID(), estudio.getCodigoEstudio());

                                Object[] rowData = new Object[]{
                                        peticion.getPeticionID(),
                                        estudio.getPracticaDTO().getNombrePractica(),
                                        resultado
                                };
                                tableModel.addRow(rowData);
                            }
                        }
                    }
                }
            }
        } catch(Exception e) {
            // Mostrar un mensaje de error si no se encuentra el paciente
            JOptionPane.showMessageDialog(this,
                    "Debe ingresar el ID de la Peticion!",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}







