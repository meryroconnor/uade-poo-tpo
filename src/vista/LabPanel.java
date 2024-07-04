package vista;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
        String[] columnNames = {"Petición ID", "Práctica", "Resultado", "Retirar por sucursal"};
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
        String requestId = filterRequestId.getText().trim();

        // Aquí deberías reemplazar estos datos con una consulta real
        Object[][] newData = {
                {requestId, "Análisis de Sangre", "Normal", "No"},
                {requestId, "Rayos X", "Fractura detectada", "Sí"}
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







