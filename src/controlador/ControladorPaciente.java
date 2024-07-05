package controlador;

import DAOs.ObraSocialDAO;
import DTOs.ObraSocialDTO;
import DTOs.PacienteDTO;
import Laboratorio.ObraSocial;
import Laboratorio.Paciente;

import java.util.ArrayList;
import java.util.List;

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
    }

    // Método para obtener la única instancia de ControladorPaciente (Singleton)
    public static ControladorPaciente getInstance() {
        if (instance == null) {
            instance = new ControladorPaciente();
        }
        return instance;
    }

    public ObraSocialDTO[] getObrasSocialesFromDAO(){
        List<ObraSocialDTO> obrasSociales = new ArrayList<>();
        try{
            ObraSocialDAO obraSocialDAO = new ObraSocialDAO();
            obrasSociales = obraSocialDAO.obtenerObrasSociales();
            System.out.println(String.format("Obras Sociales Encontradas"));

        } catch (Exception e){
            System.out.println("Obras Sociales No Existentes: " + e);
        }
        ObraSocialDTO[] obrasSocialesVector = new ObraSocialDTO[obrasSociales.size()];
        for (int i = 0; i < obrasSociales.size(); i++ ){
            obrasSocialesVector[i] = obrasSociales.get(i);
        }
        return obrasSocialesVector;
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
        obrasSociales.add(obraSocial);
        saveObraSocialToDAO(obraSocial.toDTO());
    } // no hace falta metodo para obtener obra social porque una vez que se crea no se toca mas

    // Método para crear un nuevo Paciente
    public PacienteDTO createPaciente(String nombreApellido, String sexo, String DNI, String email, ObraSocialDTO obraSocialDTO) {
        Paciente paciente = new Paciente(nextPacienteID++, nombreApellido,sexo, DNI, email);
        pacientes.add(paciente);
        this.addObraSocialToPaciente(paciente, obraSocialDTO);
        return paciente.toDTO();
    }

    public PacienteDTO obtenerPaciente(int pacienteID){ //Necesario porque el paciente sufre actualizaciones
        Paciente pacienteEncontrado = findPaciente(pacienteID);
        if (pacienteEncontrado == null){
            System.out.println(String.format("PacienteID: %d No Encontrado", pacienteID));
        }
        return pacienteEncontrado.toDTO(); // puede provocar Null!
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


