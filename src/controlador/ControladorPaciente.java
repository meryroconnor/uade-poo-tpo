package controlador;

import DAOs.ObraSocialDAO;
import DAOs.PacienteDAO;
import DTOs.ObraSocialDTO;
import DTOs.PacienteDTO;
import DTOs.PeticionDTO;
import Laboratorio.ObraSocial;
import Laboratorio.Paciente;
import Laboratorio.Peticion;

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
        List<ObraSocialDTO> obrasSocialesDTO;
        obrasSocialesDTO = getObrasSocialesFromDAO();
        for(ObraSocialDTO obraSocial : obrasSocialesDTO){
            parametrizeCobertura(obraSocial.getObraSocial()); // por ahora es solo para traer las coverturas aceptadas
        }
    }

    private void loadPacientesToModelFromDAO(){
        List<PacienteDTO> pacienteDTOS;
        pacienteDTOS = getPacientesFromDAO();
        int maxPacienteID = 0;
        for(PacienteDTO paciente : pacienteDTOS){
            loadPaciente(paciente);
            for (PeticionDTO peticionDTO: paciente.getPeticionesDTO()){ addPeticionToPaciente(paciente, peticionDTO);}
            if (paciente.getPacienteID()> maxPacienteID) {maxPacienteID = paciente.getPacienteID();}
        }
        nextPacienteID=maxPacienteID+1;
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

    public void parametrizeCobertura(String nombreObraSocial){
        ObraSocial obraSocial = new ObraSocial(nombreObraSocial, nextObraSocialID++);
        if (getObraSocial(obraSocial.toDTO()) == null){
            obrasSociales.add(obraSocial);

            if (getObraSocialFromDAO(obraSocial.toDTO()) == null){
                saveObraSocialToDAO(obraSocial.toDTO()); // significa que no existe en el DAO entonces la guarda.
            }

        } else {
            System.out.println("ObraSocial Existente Cancelando Operacion");
        }
    }

    // Método para crear un nuevo Paciente
    public void createPaciente(PacienteDTO pacienteParam) {
        ObraSocialDTO obraSocialDTO = pacienteParam.getObraSocialDTO();
        Paciente paciente = new Paciente(nextPacienteID++, pacienteParam.getNombreApellido(),pacienteParam.getSexo(), pacienteParam.getDNI(), pacienteParam.getEmail(), pacienteParam.getEdad(), pacienteParam.getDomicilio(), obraSocialDTO.getObraSocial(), obraSocialDTO.getObraSocialID());

        if (getPaciente(paciente.getDNI(), paciente.getSexo()) == null){
            pacientes.add(paciente);

            if (getPacienteFromDAO(paciente.toDTO()) == null){
                savePacienteToDAO(paciente.toDTO());
            }

        } else {
            System.out.println("Paciente Existente cancelando operacion");
        }
    }

    // Método para Cargar Paciente (solo se ejecuta en la carga dao->sistema)
    private void loadPaciente(PacienteDTO pacienteParam) {
        ObraSocialDTO obraSocialDTO = pacienteParam.getObraSocialDTO();
        Paciente paciente = new Paciente(pacienteParam.getPacienteID(), pacienteParam.getNombreApellido(),pacienteParam.getSexo(), pacienteParam.getDNI(), pacienteParam.getEmail(), pacienteParam.getEdad(), pacienteParam.getDomicilio(), obraSocialDTO.getObraSocial(), obraSocialDTO.getObraSocialID());

        if (getPaciente(paciente.getDNI(), paciente.getSexo()) == null){
            pacientes.add(paciente);

            if (getPacienteFromDAO(paciente.toDTO()) == null){
                savePacienteToDAO(paciente.toDTO());
            }

        } else {
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

    public PacienteDTO getPaciente(String dni, String sexo){ //Necesario porque el paciente sufre actualizaciones
        PacienteDTO pacienteEncontrado = null;
        for (Paciente paciente : pacientes){
            if (Objects.equals(dni, paciente.getDNI()) && Objects.equals(sexo, paciente.getSexo())){
                pacienteEncontrado = paciente.toDTO();
            }
        }
        return  pacienteEncontrado;
    }

    protected Paciente getPacienteFromPeticion(int peticionID){
        Paciente pacienteEncontrado = null;
        for (Paciente paciente : pacientes){
            for (Peticion peticion : paciente.getPeticiones()) {
                if (peticion.getPeticionID() == peticionID){
                    pacienteEncontrado = paciente;
                    return  pacienteEncontrado;
                }
            }
        }
        return  pacienteEncontrado;
    }

    // Método para agregar un paciente existente (si se requiere)
    public void addPaciente(Paciente paciente) {
        pacientes.add(paciente);
    }

    // Método para eliminar un paciente por su ID
    public String deletePaciente(String dni, String sexo) {
        Paciente pacienteAEliminar = null;
        Boolean pacienteEncontrado = false;
        String output;
        for (Paciente paciente : pacientes) {
            if (Objects.equals(dni, paciente.getDNI()) && Objects.equals(sexo, paciente.getSexo())) {
                pacienteEncontrado = true;
                if (!paciente.tieneResultadosFinalizados()) {
                    pacienteAEliminar = paciente;
                } else {
                    System.out.println("No se puede eliminar el paciente. Tiene resultados finalizados.");
                    output = "No se puede eliminar el paciente. Tiene resultados finalizados.";
                    return output;
                }
                break;
            }
        }
        if (pacienteAEliminar != null) {
            PacienteDTO pacienteDTO =  pacienteAEliminar.toDTO();

            pacientes.remove(pacienteAEliminar); // Elimino en sistema
            deletePacienteFromDAO(pacienteDTO); // Elimino en db

            System.out.println("Paciente eliminado.");
            output = "Paciente eliminado. DNI "+dni;
            return output;
        }

        System.out.println("Paciente no encontrado.");
        output = "Paciente no encontrado.";
        return output;

    }

    private void deletePacienteFromDAO(PacienteDTO pacienteParam){
        try {
            PacienteDAO pacienteDAO = new PacienteDAO();
            pacienteDAO.borrarPaciente(pacienteParam);
        } catch (Exception e) {
            System.out.println("Error ocurrido: " + e);
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
    public void addPeticionToPaciente(PacienteDTO pacienteDTO, PeticionDTO peticionDTO){
        int pacienteID = pacienteDTO.getPacienteID();
        int peticionID = peticionDTO.getPeticionID();
        Paciente pacienteEncontrado = findPaciente(pacienteID);
        ControladorAtencion controladorAtencion = ControladorAtencion.getInstance();
        Peticion peticionEncontrada = controladorAtencion.findPeticion(peticionID);
        if (pacienteEncontrado != null && peticionEncontrada != null){

            pacienteEncontrado.addPeticion(peticionEncontrada);

            try {
                PacienteDAO pacienteDAO = new PacienteDAO();
                pacienteDAO.actualizarPaciente(pacienteEncontrado.toDTO());
                System.out.println(String.format("Paciente Actualizado >>> DNI: %s Sexo: %s", pacienteEncontrado.getDNI(), pacienteEncontrado.getSexo()));
            } catch (Exception e) {
                System.out.println("Error ocurrido: " + e);
            }

            System.out.println(String.format("PacienteID: %d --> Agregada PeticionID: %d", pacienteID, peticionID));

        }

    }

}


