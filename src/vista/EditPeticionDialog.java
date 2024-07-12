package vista;

import DTOs.*;
import controlador.ControladorAtencion;
import controlador.ControladorPaciente;
import controlador.ControladorPractica;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class EditPeticionDialog extends JDialog {
    private JTextField dniField;
    private JComboBox<String> sexoComboBox, sucursalComboBox;
    private JComboBox<String> practicaComboBox;
    private JList<String> practicaList;
    private DefaultListModel<String> practicaListModel;
    private JButton guardarButton, cancelButton;
    private JButton cargarItemButton, eliminarItemButton;
    private int peticionID; //global a la vista para poder usar en el actionListener

    public EditPeticionDialog(Frame owner, PeticionDTO peticionDTO, String DNI, String sexo, String sucursal) {
        super(owner, "Editar Peticion", true);
        setSize(500, 500); // Ajustar tamaño para acomodar nuevos componentes
        setLayout(new BorderLayout());

        peticionID = peticionDTO.getPeticionID();

        List<EstudioDTO> estudiosCargados = peticionDTO.getEstudiosDTO();
        List<PracticaDTO> practicasCargadas = new ArrayList<>();

        for (EstudioDTO estudio : estudiosCargados){
            practicasCargadas.add(estudio.getPracticaDTO());
        }

        // Panel norte con identificación del paciente y selección de Sucursal y Prácticas
        JPanel northPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        northPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        northPanel.add(new JLabel("DNI:"));
        dniField = new JTextField(DNI);
        northPanel.add(dniField);
        dniField.setEnabled(false);

        northPanel.add(new JLabel("Sexo:"));
        sexoComboBox = new JComboBox<>(new String[]{"F", "M"});
        sexoComboBox.setSelectedItem(sexo);
        northPanel.add(sexoComboBox);
        sexoComboBox.setEnabled(false);

        northPanel.add(new JLabel("Sucursal:"));
        sucursalComboBox = new JComboBox<>(Objects.requireNonNull(getDireccionSucursal()));
        sucursalComboBox.setSelectedItem(sucursal);
        northPanel.add(sucursalComboBox);
        sucursalComboBox.setEnabled(false);

        northPanel.add(new JLabel("Seleccione una práctica:"));
        practicaComboBox = new JComboBox<>(Objects.requireNonNull(getNombrePracticas()));
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

        for(PracticaDTO practica : practicasCargadas){
            String categoria = practica.getNombrePractica();
            practicaListModel.addElement(categoria);
        }

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

        guardarButton.setEnabled(false);

        add(buttonPanel, BorderLayout.SOUTH);

        setLocationRelativeTo(owner);
        this.editarPeticionEventos();
    }

    private void editarPeticionEventos() {
        guardarButton.addActionListener(e -> {
            try{

                ControladorAtencion controladorAtencion = ControladorAtencion.getInstance();
                ControladorPaciente controladorPaciente = ControladorPaciente.getInstance();

                List<EstudioDTO> estudiosDTOPropuestos = new ArrayList<>();

                for (int i = 0; i < practicaListModel.size(); i++) {
                    PracticaDTO practicaDTO =  getPractica(practicaListModel.getElementAt(i));
                    ResultadoDTO resultadoDTO = new ResultadoDTO(0,null);
                    EstudioDTO estudioDTO = new EstudioDTO(0, practicaDTO, resultadoDTO, null, null);
                    // ese DTO solo es para pasar info de que estudio se adiciona a la peticion las fechas no importan ya que
                    // se usaran las del objeto del modelo
                    estudiosDTOPropuestos.add(estudioDTO);
                }

                PeticionDTO peticionDTOPropuesta = new PeticionDTO(peticionID, estudiosDTOPropuestos, null, null);

                controladorAtencion.updatePeticion(peticionDTOPropuesta);

                dispose();

            }catch (Exception err){
                System.out.println(err.getMessage());
            }


    });
        cancelButton.addActionListener(e -> dispose());
        cargarItemButton.addActionListener(e -> {
            String selectedCategory = (String) practicaComboBox.getSelectedItem();
            if (selectedCategory != null && !practicaListModel.contains(selectedCategory)) {
                practicaListModel.addElement(selectedCategory);
            }
            guardarButton.setEnabled(true);
        });
        eliminarItemButton.addActionListener(e -> {
            String selectedCategory = practicaList.getSelectedValue();
            if (selectedCategory != null) {
                practicaListModel.removeElement(selectedCategory);
            }
            guardarButton.setEnabled(true);
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

    private SucursalDTO getSucursal(String direccion){
        ControladorAtencion controladorAtencion = ControladorAtencion.getInstance();
        List<SucursalDTO> sucursales = controladorAtencion.getSucursales();
        SucursalDTO sucursalEncontrada = null;

        for (SucursalDTO sucursal : sucursales){
            if (Objects.equals(sucursal.getDireccion(), direccion)) {
                sucursalEncontrada = sucursal;
            }
        }
        return sucursalEncontrada;
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

