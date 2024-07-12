package controlador;

import DAOs.PacienteDAO;
import DAOs.PeticionDAO;
import DAOs.PracticaDAO;
import DTOs.EstudioDTO;
import DTOs.PacienteDTO;
import DTOs.PeticionDTO;
import DTOs.PracticaDTO;
import Laboratorio.Paciente;
import Laboratorio.Peticion;
import Laboratorio.Practica;
import Laboratorio.Sucursal;

import java.util.ArrayList;
import java.util.List;

public class ControladorPractica {
    private static ControladorPractica instance;
    private List<Practica> practicas;
    private int nextCodigoPractica;

    // Constructor privado para implementar el patrón Singleton
    private ControladorPractica() {
        this.practicas = new ArrayList<>();
        this.nextCodigoPractica = 1;

        this.loadPracticasFromDAOToModel();
    }

    // Método para obtener la única instancia de ControladorPaciente (Singleton)
    public static ControladorPractica getInstance() {
        if (instance == null) {
            instance = new ControladorPractica();
        }
        return instance;
    }

    private void loadPracticasFromDAOToModel(){
        List<PracticaDTO> practicaDTOS = new ArrayList<>();
        practicaDTOS = getPracticasFromDAO();
        for(PracticaDTO practicaDTO : practicaDTOS){
            createPractica(practicaDTO); // se mantiene el orden de los parametros ID porque tienen el orden en el que aparecen en el JSON
        }
    }

    // Método para crear una nueva Practica
    public void createPractica(PracticaDTO practicaParam) {
        Practica practica = new Practica(nextCodigoPractica++, practicaParam.getNombrePractica(), practicaParam.getIndiceCriticoDTO().getValue(),practicaParam.getIndiceCriticoDTO().getLowLimit(),practicaParam.getIndiceCriticoDTO().getHighLimit(),practicaParam.getIndiceReservadoDTO().getValue(), practicaParam.getIndiceReservadoDTO().getLowLimit(), practicaParam.getIndiceReservadoDTO().getHighLimit(), practicaParam.getCantidadHorasDemora());

        if (getPractica(practica.getCodigoPractica()) == null){
            practicas.add(practica);
            if (getPracticaFromDAO(practica.toDTO()) == null){
                savePracticaToDAO(practica.toDTO());
            }
        } else{
            practica = null;
            System.out.println("Practica Existente Cancelando Operacion");
        }
    }

    public String deletePractica(int codigoPractica) {
       Practica practica = findPractica(codigoPractica);
       if (practica!=null) {
           if (practicaTienePeticionesAsociadas(practica)) {
               return "La práctica tiene peticiones, NO PUEDE SER ELIMINADA.";
           }
       }
       practicas.remove(practica);
       deletePracticaFromDAO(practica.toDTO());
       return "La práctica fue eliminada!";
    }

    private void deletePracticaFromDAO(PracticaDTO practicaDTO){
        try {
            PracticaDAO practicaDAO = new PracticaDAO();
            practicaDAO.borrarPractica(practicaDTO);
        } catch (Exception e) {
            System.out.println("Error ocurrido: " + e.getMessage());
        }
    }

    private boolean practicaTienePeticionesAsociadas(Practica practica) {
        ControladorAtencion controladorAtencion = ControladorAtencion.getInstance();
        List<PeticionDTO> peticiones = controladorAtencion.getPeticiones();
        for (PeticionDTO peticion : peticiones) {
            for (EstudioDTO estudio : peticion.getEstudiosDTO()) {
                if (estudio.getPracticaDTO().getCodigoPractica() == practica.getCodigoPractica()){
                    return true;
                }
            }
        }
        return false;
    }

    private PracticaDTO getPracticaFromDAO(PracticaDTO practicaParam){
        PracticaDTO practicaEncontrada = null;
        try{
            PracticaDAO practicaDAO = new PracticaDAO();
            practicaEncontrada = practicaDAO.obtenerPractica(practicaParam);
        } catch (Exception e){
            System.out.println("Error Ocurrido: " + e);
        }
        return  practicaEncontrada;
    }

    private List<PracticaDTO> getPracticasFromDAO(){
        List<PracticaDTO> practicasDTOS = null;
        try{
            PracticaDAO practicaDAO = new PracticaDAO();
            practicasDTOS = practicaDAO.obtenerPracticas();
        } catch (Exception e){
            System.out.println("Error Ocurrido: " + e);
        }
        return  practicasDTOS;
    }

    private void savePracticaToDAO(PracticaDTO practicaParam){
        try{
            PracticaDAO practicaDAO = new PracticaDAO();
            practicaDAO.crearPractica(practicaParam);
        } catch (Exception e){
            System.out.println("Error Ocurrido: " + e);
        }
    }

    public PracticaDTO getPractica(int codigoPractica){
        PracticaDTO practicaEncontrada = null;
        for (Practica practica : practicas){
            if(codigoPractica == practica.getCodigoPractica()){
                practicaEncontrada = practica.toDTO();
                break;
            }
        }
        return practicaEncontrada;
    }

    // Método para agregar un paciente existente (si se requiere)
    public void addPractica(Practica practica) {
        practicas.add(practica);
    }

    public PracticaDTO obtenerPractica(int codigoPractica){
        Practica practicaEncontrada = findPractica(codigoPractica);
        if (practicaEncontrada == null){
            System.out.println(String.format("PracticaID: %d No Encontrada", codigoPractica));
        }
        return practicaEncontrada.toDTO(); // puede provocar Null!
    }

    protected Practica findPractica(int codigoPractica){
        Practica practicaEncontrada = null;
        for (Practica practica : practicas){
            if (practica.getCodigoPractica() == codigoPractica){
                practicaEncontrada = practica;
                break;
            }
        }
        return practicaEncontrada;
    }

    // Método para obtener la lista de practicas (opcional)
    public List<PracticaDTO> getPracticas() {
        List<PracticaDTO> practicaDTOS = new ArrayList<>();
        for (Practica practica : practicas){
            practicaDTOS.add(practica.toDTO());
        }
        return practicaDTOS;
    }
}


