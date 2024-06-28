package vista;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LabPanel extends JPanel {
    private JTextField filterPatientId;
    private JTextField filterRequestId;
    private JTextField filterBranchId; // Campo para filtrar por sucursal
    private JButton filterButton;
    private JButton getAllButton;
    private JTable table;
    private DefaultTableModel tableModel;

    public LabPanel() {
        setLayout(new BorderLayout());

        // Panel de filtros
        JPanel filterPanel = new JPanel(new GridLayout(5, 2));
        filterPanel.add(new JLabel("Filtrar por ID de Paciente:"));
        filterPatientId = new JTextField();
        filterPanel.add(filterPatientId);
        filterPanel.add(new JLabel("Filtrar por ID de Petición:"));
        filterRequestId = new JTextField();
        filterPanel.add(filterRequestId);
        filterPanel.add(new JLabel("Filtrar por Sucursal:"));
        filterBranchId = new JTextField();
        filterPanel.add(filterBranchId);
        filterPanel.add(new JLabel());
        filterButton = new JButton("Filtrar");
        filterPanel.add(filterButton);
        filterPanel.add(new JLabel());
        getAllButton = new JButton("Ver todo");
        getAllButton.setForeground(new Color(0, 141, 213));
        filterPanel.add(getAllButton);

        add(filterPanel, BorderLayout.NORTH);

        // Modelo de la tabla
        String[] columnNames = {"Petición ID", "Paciente ID", "Práctica", "Resultado", "Retirar por sucursal", "Sucursal"};
        Object[][] data = {}; // Data inicial vacía
        tableModel = new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                // Hacer que solo la columna "Resultado" sea editable
                return column == 3; // Index 3 corresponde a la columna "Resultado"
            }
        };
        table = new JTable(tableModel);

        // Agregar el TableModelListener
        tableModel.addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                if (e.getType() == TableModelEvent.UPDATE && e.getColumn() == 3) { // Índice 3 para la columna "Resultado"
                    int row = e.getFirstRow();
                    int column = e.getColumn();
                    Object data = tableModel.getValueAt(row, column);
                    Object petitionID = tableModel.getValueAt(row, 0); // Suponiendo que la Petición ID está en la columna 0
                    Object practiceID = tableModel.getValueAt(row, 2); // Suponiendo que la Práctica está en la columna 2
                    createResult(petitionID, practiceID, data);
                }
            }
        });

        // Ajustar el color de fondo y el color del texto del encabezado de la tabla
        JTableHeader header = table.getTableHeader();
        header.setBackground(Color.darkGray);
        header.setForeground(Color.white);

        // ScrollPane para la tabla
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // Configurar el botón de filtrado
        filterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                applyFilters();
            }
        });
    }

    private void createResult(Object petitionID, Object practiceID, Object result) {
        // Llamar a la lógica de negocio o al controlador para manejar la creación de un nuevo resultado
        System.out.println("Crear resultado para Petición ID: " + petitionID + ", Práctica: " + practiceID + ", Resultado: " + result);
        // Aquí puedes colocar el código que interactúa con la base de datos o el backend
    }

    private void applyFilters() {
        // Implementación del filtrado según los inputs
        String patientId = filterPatientId.getText().trim();
        String requestId = filterRequestId.getText().trim();
        String branchId = filterBranchId.getText().trim();

        // Aquí deberías reemplazar estos datos con una consulta real
        Object[][] newData = {
                {requestId, patientId, "Análisis de Sangre", "Normal", "No", branchId},
                {requestId, patientId, "Rayos X", "Fractura detectada", "Sí", branchId}
        };

        tableModel.setRowCount(0);
        for (Object[] row : newData) {
            tableModel.addRow(row);
        }
    }

    public void addRow(Object[] rowData) {
        tableModel.addRow(rowData);
    }
}







