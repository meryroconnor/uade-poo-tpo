package vista;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.List;
import java.util.Objects;

public class RegisterPetitionDialog extends JDialog {
    private JTextField dniField, pacienteIDField;
    private JComboBox<String> practicaComboBox;
    private JList<String> practicaList;
    private DefaultListModel<String> practicaListModel;
    private JButton guardarButton, cancelButton;
    private JButton cargarItemButton, eliminarItemButton;

    public RegisterPetitionDialog(Frame owner) {
        super(owner, "Agregar Peticion", true);
        setSize(500, 500); // Ajustar tamaño para acomodar nuevos componentes
        setLayout(new BorderLayout());

        // Panel norte con identificación del paciente y selección de prácticas
        JPanel northPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        northPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        northPanel.add(new JLabel("DNI:"));
        dniField = new JTextField();
        northPanel.add(dniField);
        northPanel.add(new JLabel("ID Paciente:"));
        pacienteIDField = new JTextField();
        northPanel.add(pacienteIDField);
        northPanel.add(new JLabel("Seleccione una práctica:"));
        practicaComboBox = new JComboBox<>(new String[]{"Glucemia", "Resonancia Magnetica", "Ecografia", "Mamografía"});
        northPanel.add(practicaComboBox);

        add(northPanel, BorderLayout.NORTH);

        // Panel central con lista de prácticas y botones de manejo
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        JPanel listPanel = new JPanel(new GridLayout(0, 2, 10, 10));
        listPanel.add(new JLabel("Prácticas Seleccionadas:"));
        practicaListModel = new DefaultListModel<>();
        practicaList = new JList<>(practicaListModel);
        listPanel.add(new JScrollPane(practicaList));

        centerPanel.add(listPanel, BorderLayout.CENTER);

        JPanel loadPanel= new JPanel(new GridLayout(0, 2, 10, 10));
        loadPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        cargarItemButton = new JButton("Cargar Item");
        eliminarItemButton = new JButton("Eliminar Item");
        loadPanel.add(cargarItemButton);
        loadPanel.add(eliminarItemButton);

        centerPanel.add(loadPanel, BorderLayout.SOUTH);

        add(centerPanel, BorderLayout.CENTER);

        // Panel de botones en el sur
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        guardarButton = new JButton("Guardar");
        cancelButton = new JButton("Cancelar");
        buttonPanel.add(guardarButton);
        buttonPanel.add(cancelButton);

        add(buttonPanel, BorderLayout.SOUTH);

        setLocationRelativeTo(owner);
        this.registrarPeticionEventos();
    }

    private void registrarPeticionEventos() {
        guardarButton.addActionListener(e -> dispose());
        cancelButton.addActionListener(e -> dispose());
        cargarItemButton.addActionListener(e -> {
            String selectedCategory = (String) practicaComboBox.getSelectedItem();
            if (selectedCategory != null && !practicaListModel.contains(selectedCategory)) {
                practicaListModel.addElement(selectedCategory);
            }
        });
        eliminarItemButton.addActionListener(e -> {
            String selectedCategory = practicaList.getSelectedValue();
            if (selectedCategory != null) {
                practicaListModel.removeElement(selectedCategory);
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

    private String[] getDireccionSucursal(){
        ControladorAtencion controladorAtencion = ControladorAtencion.getInstance();

        List<SucursalDTO> sucursales = controladorAtencion.getSucursales();

        String[] vectorSucursales = new String[sucursales.size()];
        for (int i = 0; i < sucursales.size(); i++){
            vectorSucursales[i] = sucursales.get(i).getDireccion();
        }
        return vectorSucursales;
    }

    private String[] getSucursalID(String SucursalDireccion){
        ControladorAtencion controladorAtencion = ControladorAtencion.getInstance();

        List<SucursalDTO> sucursales = controladorAtencion.getSucursales();

        String[] vectorSucursales = new String[sucursales.size()];
        for (int i = 0; i < sucursales.size(); i++){
            vectorSucursales[i] = sucursales.get(i).getDireccion();
        }
        return vectorSucursales;
    }
}

