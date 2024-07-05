package controlador;

import DAOs.ObraSocialDAO;
import DAOs.PacienteDAO;
import DTOs.ObraSocialDTO;
import DTOs.PacienteDTO;
import Laboratorio.ObraSocial;
import Laboratorio.Paciente;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ControladorPaciente {
    private static ControladorPaciente instance;
    private List<Paciente> pacientes;
    private List<ObraSocial> obrasSociales;
    private int nextPacienteID;
    private int nextObraSocialID;

    // Constructor privado para implementar el patrón Singleton
    private ControladorPaciente() {
        this.pacientes = new ArrayList<>();
        this.nextPacienteID = 1;
        this.obrasSociales = new ArrayList<>();
        this.nextObraSocialID = 1;

        this.loadObrasSocialesToModelFromDAO();
        this.loadPacientesToModelFromDAO();
    }

    // Método para obtener la única instancia de ControladorPaciente (Singleton)
    public static ControladorPaciente getInstance() {
        if (instance == null) {
            instance = new ControladorPaciente();
        }
        return instance;
    }

    private void loadObrasSocialesToModelFromDAO(){
        List<ObraSocialDTO> obrasSocialesDTO = new ArrayList<>();
        obrasSocialesDTO = getObrasSocialesFromDAO();
        for(ObraSocialDTO obraSocial : obrasSocialesDTO){
            createObraSocial(obraSocial.getObraSocial()); // se mantiene el orden de los parametros ID porque tienen el orden en el que aparecen en el JSON
        }
    }

    private void loadPacientesToModelFromDAO(){
        List<PacienteDTO> pacienteDTOS = new ArrayList<>();
        pacienteDTOS = getPacientesFromDAO();
        for(Paciente paciente : pacientes){
            createPaciente(paciente.toDTO()); // se mantiene el orden de los parametros ID porque tienen el orden en el que aparecen en el JSON
        }
    }

    private List<ObraSocialDTO> getObrasSocialesFromDAO(){
        List<ObraSocialDTO> obrasSociales = new ArrayList<>();
        try{
            ObraSocialDAO obraSocialDAO = new ObraSocialDAO();
            obrasSociales = obraSocialDAO.obtenerObrasSociales();
            System.out.println(String.format("Obras Sociales Encontradas"));

        } catch (Exception e){
            System.out.println("Obras Sociales No Existentes: " + e);
        }
        return obrasSociales;
    }

    private void saveObraSocialToDAO(ObraSocialDTO obraSocialParam){
        try{
            ObraSocialDAO obraSocialDAO = new ObraSocialDAO();
            obraSocialDAO.crearObraSocial(obraSocialParam);
            System.out.println(String.format("Obra Social Creada: %s", obraSocialParam.getObraSocial()));
        } catch (Exception e){
            System.out.println("Obra Social Existente: " + e);
        }
    }

    public ObraSocialDTO getObraSocial(ObraSocialDTO obraSocialParam){
        ObraSocialDTO obraSocialEncontrada = null;
        for (ObraSocial obraSocial : obrasSociales){
            if (Objects.equals(obraSocial.getObraSocial(), obraSocialParam.getObraSocial())){
                obraSocialEncontrada = obraSocial.toDTO();
                break;
            }
        }
        return obraSocialEncontrada;
    }

    public List<ObraSocialDTO> getObrasSociales(){
        List<ObraSocialDTO> obrasSocialesDTO = new ArrayList<>();
        for (ObraSocial obraSocial : obrasSociales){
            obrasSocialesDTO.add(obraSocial.toDTO());
        }
        return  obrasSocialesDTO;
    }

    private List<PacienteDTO> getPacientesFromDAO(){
        List<PacienteDTO> pacientes = new ArrayList<>();
        try{
            PacienteDAO pacienteDAO = new PacienteDAO();
            pacientes = pacienteDAO.obtenerPacientes();
        } catch (Exception e){
            System.out.println("Error Ocurrido: " + e);
        }
        return pacientes;
    }

    private PacienteDTO getPacienteFromDAO(PacienteDTO pacienteParam){
        PacienteDTO pacienteEncontrado = null;
        try{
            PacienteDAO pacienteDAO = new PacienteDAO();
            pacienteEncontrado = pacienteDAO.obtenerPaciente(pacienteParam);
            System.out.println(String.format("PacienteID Encontrado: %s", pacienteParam.getPacienteID()));

        } catch (Exception e){
            System.out.println("Paciente No Existente: " + e);
        }
        return pacienteEncontrado;
    }

    private ObraSocialDTO getObraSocialFromDAO(ObraSocialDTO obraSocialParam){
        ObraSocialDTO obraSocialEncontrada = null;
        try{
            ObraSocialDAO obraSocialDAO = new ObraSocialDAO();
            obraSocialEncontrada = obraSocialDAO.obtenerObraSocial(obraSocialParam);
            System.out.println(String.format("Obra Social Encontrada: %s", obraSocialParam.getObraSocial()));

        } catch (Exception e){
            System.out.println("Obra Social No Existente: " + e);
        }
        return obraSocialEncontrada;
    }

    public void createObraSocial(String nombreObraSocial){
        ObraSocial obraSocial = new ObraSocial(nombreObraSocial, nextObraSocialID++);
        if (getObraSocial(obraSocial.toDTO()) == null){
            obrasSociales.add(obraSocial);

            if (getObraSocialFromDAO(obraSocial.toDTO()) == null){
                saveObraSocialToDAO(obraSocial.toDTO()); // significa que no existe en el DAO entonces la guarda.
            }

        } else {
            obraSocial = null;
            System.out.println("ObraSocial Existente Cancelando Operacion");
        }
    } // no hace falta metodo para obtener obra social porque una vez que se crea no se toca mas

    // Método para crear un nuevo Paciente
    public void createPaciente(PacienteDTO pacienteParam) {
        ObraSocialDTO obraSocialDTO = getObraSocial(pacienteParam.getObraSocialDTO());
        ObraSocial obraSocial = new ObraSocial(obraSocialDTO.getObraSocial(), obraSocialDTO.getObraSocialID());
        Paciente paciente = new Paciente(nextPacienteID++, pacienteParam.getNombreApellido(),pacienteParam.getSexo(), pacienteParam.getDNI(), pacienteParam.getEmail(), obraSocial);
        if (getPaciente(paciente.toDTO()) == null){
            pacientes.add(paciente);

            if (getPacienteFromDAO(paciente.toDTO()) == null){
                savePacienteToDAO(paciente.toDTO());
            }

        } else {
            obraSocialDTO = null;
            paciente = null;
            obraSocial = null;
            System.out.println("Paciente Existente cancelando operacion");
        }
    }

    private void savePacienteToDAO(PacienteDTO pacienteParam){
        try {
            PacienteDAO pacienteDAO = new PacienteDAO();
            pacienteDAO.crearPaciente(pacienteParam);
            System.out.println(String.format("Paciente Creado >>> DNI: %s Sexo: %s", pacienteParam.getDNI(), pacienteParam.getSexo()));
        } catch (Exception e) {
            System.out.println("Error ocurrido: " + e);
        }
    }

    public PacienteDTO getPaciente(PacienteDTO pacienteParam){ //Necesario porque el paciente sufre actualizaciones
        PacienteDTO pacienteEncontrado = null;
        for (Paciente paciente : pacientes){
            if (Objects.equals(pacienteParam.getDNI(), paciente.getDNI()) && Objects.equals(pacienteParam.getSexo(), paciente.getSexo())){
                pacienteEncontrado = paciente.toDTO();
            }
        }
        return  pacienteEncontrado;
    }

    // Método para agregar un paciente existente (si se requiere)
    public void addPaciente(Paciente paciente) {
        pacientes.add(paciente);
    }

    // Método para eliminar un paciente por su ID
    public void deletePaciente(int pacienteID) {
        Paciente pacienteAEliminar = null;
        Boolean pacienteEncontrado = false;
        for (Paciente paciente : pacientes) {
            if (paciente.getPacienteID() == pacienteID) {
                pacienteEncontrado = true;
                if (!paciente.tieneResultadosFinalizados()) {
                    pacienteAEliminar = paciente;
                } else {
                    System.out.println("No se puede eliminar el paciente. Tiene resultados finalizados.");
                    break;
                }
                break;
            }
        }
        if (pacienteAEliminar != null) {
            pacientes.remove(pacienteAEliminar);
            System.out.println("Paciente eliminado.");
        } if (!pacienteEncontrado){
            System.out.println("Paciente no encontrado.");
        }
    }

    // Método para obtener la lista de pacientes (opcional)
    public List<PacienteDTO> getPacientes() {
        List<PacienteDTO> pacienteDTOS = new ArrayList<>();
        for (Paciente paciente : pacientes){
            pacienteDTOS.add(paciente.toDTO());
        }
        return pacienteDTOS;
    }

    //Buscar paciente
    public List<PacienteDTO> buscarPaciente(String pacienteID, String dni) {
        List<PacienteDTO> resultadosDTOS = new ArrayList<>();
        for (Paciente paciente : pacientes) {
            if ((pacienteID != null && !pacienteID.isEmpty() && paciente.getPacienteID() == Integer.parseInt(pacienteID)) ||
                    (dni != null && !dni.isEmpty() && paciente.getDNI().equals(dni))) {
                resultadosDTOS.add(paciente.toDTO());
            }
        }
        return resultadosDTOS;
    }

    protected Paciente findPaciente(int pacienteID){
        Paciente pacienteEncontrado = null;
        for (Paciente paciente : pacientes){
            if (paciente.getPacienteID() == pacienteID){
                pacienteEncontrado = paciente;
                break;
            }
        }
        return pacienteEncontrado;
    }

    private ObraSocial findObraSocial(int obraSocialID){
        ObraSocial obraSocialEncontrada = null;
        for (ObraSocial obraSocial : obrasSociales){
            if (obraSocialID == obraSocial.getObraSocialID()){
                obraSocialEncontrada = obraSocial;
                break;
            }
        }
        return obraSocialEncontrada;
    }

    private void addObraSocialToPaciente(Paciente paciente, ObraSocialDTO obraSocialDTO){
        int pacienteID = paciente.getPacienteID();
        int obraSocialID = obraSocialDTO.getObraSocialID();
        Paciente pacienteEncontrado = findPaciente(pacienteID);
        ObraSocial obraSocialEncontrada = findObraSocial(obraSocialID);
        if(pacienteEncontrado != null && obraSocialEncontrada != null){
            pacienteEncontrado.setObraSocial(obraSocialEncontrada);

            System.out.println(String.format("PacienteID: %d --> Agregada ObraSocial: %s", pacienteID, obraSocialEncontrada.getObraSocial()));
        }
    }

}


