package controlador;

import DAOs.PacienteDAO;
import DAOs.PeticionDAO;
import DAOs.PracticaDAO;
import DAOs.SucursalDAO;
import DTOs.*;
import Laboratorio.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class ControladorAtencion {
    private static ControladorAtencion instance;
    private List<Peticion> peticiones;
    private List<Sucursal> sucursales;
    private int nextPeticionID;
    private int nextSucursalID;

    // Constructor privado para implementar el patrón Singleton
    // En el contructor tambien cargamos las sucursales y peticiones preexistentes (DAO->Model)
    private ControladorAtencion() {
        this.peticiones = new ArrayList<>();
        this.sucursales = new ArrayList<>();
        this.nextPeticionID = 1;
        this.nextSucursalID = 1;


        this.bootControllerFromDaoToModel();
    }

    // Método para obtener la única instancia de ControladorAtencion
    public static ControladorAtencion getInstance() {
        if (instance == null) {
            instance = new ControladorAtencion();
        }
        return instance;
    }

    // Método para crear una nueva Peticion
    public PeticionDTO createPeticion() {
        Peticion peticion = new Peticion(nextPeticionID++);
        PeticionDTO peticionDTO = peticion.toDTO();

        if (getPeticion(peticion.getPeticionID()) == null){
            peticiones.add(peticion);
            if (getPeticionFromDAO(peticion.toDTO()) == null){
                savePeticionToDAO(peticion.toDTO());
            }
        }
        return peticionDTO;
    }

    private PeticionDTO getPeticionFromDAO(PeticionDTO peticionParam){
        PeticionDTO peticionEncontrada = null;
        try{
            PeticionDAO peticionDAO = new PeticionDAO();
            peticionEncontrada = peticionDAO.obtenerPeticion(peticionParam);
        } catch (Exception e){
            System.out.println("Error Ocurrido: " + e);
        }
        return  peticionEncontrada;
    }

    private void savePeticionToDAO(PeticionDTO peticionParam){
        try{
            PeticionDAO peticionDAO = new PeticionDAO();
            peticionDAO.crearPeticion(peticionParam);
        } catch (Exception e){
            System.out.println("Error Ocurrido: " + e);
        }
    }

    public PeticionDTO getPeticion(int peticionID){
        Peticion peticion = findPeticion(peticionID);
        PeticionDTO peticionEncontrada = null;

        if (peticion != null) {
            peticionEncontrada = peticion.toDTO();
        }
            return peticionEncontrada;
    }




    // Método para crear una nueva Sucursal
    public void createSucursal(SucursalDTO sucursalParam) {
        Sucursal sucursal = new Sucursal(nextSucursalID++, sucursalParam.getDireccion(), sucursalParam.getResponsableMatricula());
        if (getSucursal(sucursal.toDTO()) == null){
            sucursales.add(sucursal);
            if (getSucursalFromDAO(sucursal.toDTO()) == null){
                saveSucursalToDAO(sucursal.toDTO());
            }
        } else {
            System.out.println("Sucursal Existente Cancelando Operacion");
        }
    }

    // Método para cargar una Sucursal desde dao al sistema
    public void cargarSucursal(SucursalDTO sucursalParam) {
        Sucursal sucursal = new Sucursal(sucursalParam.getSucursalID(), sucursalParam.getDireccion(), sucursalParam.getResponsableMatricula());
        if (getSucursal(sucursal.toDTO()) == null){
            sucursales.add(sucursal);
            if (getSucursalFromDAO(sucursal.toDTO()) == null){
                saveSucursalToDAO(sucursal.toDTO());
            }
        } else {
            System.out.println("Sucursal Existente Cancelando Operacion");
        }
    }

    // Método para cargar una Peticion desde dao al sistema
    public void cargarPeticion(int peticionID) {
        Peticion peticion = new Peticion(peticionID);

        if (getPeticion(peticion.getPeticionID()) == null){
            peticiones.add(peticion);
            if (getPeticionFromDAO(peticion.toDTO()) == null){
                savePeticionToDAO(peticion.toDTO());
            }
        } else{
            System.out.println("Peticion Existente Cancelando Operacion");
        }
    }


    private List<PeticionDTO> getPeticionesFromDAO(){
        List<PeticionDTO> peticionDTOS = null;
        try{
            PeticionDAO peticionDAO = new PeticionDAO();
            peticionDTOS = peticionDAO.obtenerPeticiones();
        } catch (Exception e){
            System.out.println("Error Ocurrido: " + e);
        }
        return  peticionDTOS;
    }


    private  void bootControllerFromDaoToModel(){
        List<SucursalDTO> sucursalDTOS;
        sucursalDTOS = getSucursalesFromDAO();
        int maxSucursalID = 0;
        int maxPeticionID = 0;

        List<PeticionDTO> peticionDTOS;
        peticionDTOS = getPeticionesFromDAO();

        for(SucursalDTO sucursalDTO : sucursalDTOS){
            cargarSucursal(sucursalDTO);
            for (PeticionDTO peticionDTO: sucursalDTO.getPeticionesDTO()){ addPeticionToSucursal(peticionDTO, sucursalDTO);}
            if (sucursalDTO.getSucursalID()> maxSucursalID) {maxSucursalID = sucursalDTO.getSucursalID();}
        }

        for(PeticionDTO peticionDTO : peticionDTOS){
            cargarPeticion(peticionDTO.getPeticionID());
            for (EstudioDTO estudioDTO: peticionDTO.getEstudiosDTO()){ addEstudioToPeticion(estudioDTO, peticionDTO);}
            if (peticionDTO.getPeticionID()> maxPeticionID) {maxPeticionID = peticionDTO.getPeticionID();}
        }

        nextPeticionID = maxPeticionID+1;
        nextSucursalID = maxSucursalID+1;

        for(SucursalDTO sucursalDTO : sucursalDTOS){
            for (PeticionDTO peticionDTO : sucursalDTO.getPeticionesDTO()){
                addPeticionToSucursal(peticionDTO,sucursalDTO);
            }
        }

    }

    private List<SucursalDTO> getSucursalesFromDAO(){
        List<SucursalDTO> sucursalDTOS = null;
        try{
            SucursalDAO sucursalDAO = new SucursalDAO();
            sucursalDTOS = sucursalDAO.obtenerSucursales();
        } catch (Exception e){
            System.out.println("Error Ocurrido: " + e);
        }
        return  sucursalDTOS;
    }

    private SucursalDTO getSucursalFromDAO(SucursalDTO sucursalParam){
        SucursalDTO sucursalEncontrada = null;
        try{
            SucursalDAO sucursalDAO = new SucursalDAO();
            sucursalEncontrada = sucursalDAO.obtenerSucursal(sucursalParam);
        } catch (Exception e){
            System.out.println("Error Ocurrido: " + e);
        }
        return  sucursalEncontrada;
    }



    private void saveSucursalToDAO(SucursalDTO sucursalParam){
        try{
            SucursalDAO sucursalDAO = new SucursalDAO();
            sucursalDAO.crearSucursal(sucursalParam);
        } catch (Exception e){
            System.out.println("Error Ocurrido: " + e);
        }
    }

    // Método para eliminar una sucursal por su ID
    public void deleteSucursal(int sucursalID) {
        Sucursal sucursalAEliminar = null;
        for (Sucursal sucursal : sucursales) {
            if (sucursal.getSucursalID() == sucursalID) {
                sucursalAEliminar = sucursal;
                break;
            }
        }

        if (sucursalAEliminar != null) {
            System.out.println("Sucursal encontrada: "+sucursalAEliminar.getDireccion());
            List<Peticion> peticionesActivas = getPeticionesActivas(sucursalAEliminar);

            if (peticionesActivas.isEmpty()) {
                // No tiene peticiones activas, eliminar sucursal directamente
                sucursales.remove(sucursalAEliminar);
                System.out.println("*** Sucursal eliminada. ***");
            } else if (sucursales.size() > 1) {
                // Tiene peticiones activas y hay otras sucursales disponibles
                transferirPeticionesActivas(sucursalAEliminar, peticionesActivas);
                sucursales.remove(sucursalAEliminar);
                System.out.println("*** Sucursal eliminada. ***");
            } else {
                System.out.println("No se puede eliminar la sucursal. No hay otras sucursales disponibles para transferir las peticiones activas.");
            }
        } else {
            System.out.println("Sucursal no encontrada.");
        }
    }

    // Método para obtener las peticiones activas de una sucursal
    private List<Peticion> getPeticionesActivas(Sucursal sucursal) {
        List<Peticion> peticionesActivas = new ArrayList<>();
        for (Peticion peticion : sucursal.getPeticiones()) {
            for (Estudio estudio : peticion.getEstudios()) {
                if (estudio.tieneResultado()) {
                    peticionesActivas.add(peticion);
                }
            }
        }
        return peticionesActivas;
    }

    // Método para transferir peticiones activas a otra sucursal aleatoria
    // Método para transferir peticiones activas a otra sucursal aleatoria
    private void transferirPeticionesActivas(Sucursal sucursalAEliminar, List<Peticion> peticionesActivas) {
        Sucursal sucursalDestino = getRandomSucursal(sucursalAEliminar);
        for (Peticion peticion : peticionesActivas) {
            sucursalAEliminar.getPeticiones().remove(peticion);
            sucursalDestino.addPeticion(peticion);
            System.out.println(">> Peticion " + peticion.getPeticionID() + " transferida a " + sucursalDestino.getDireccion());
        }
    }

    // Método para obtener una sucursal aleatoria diferente de la que se va a eliminar
    private Sucursal getRandomSucursal(Sucursal excludeSucursal) {
        Random random = new Random();
        Sucursal sucursalRandom;
        do {
            sucursalRandom = sucursales.get(random.nextInt(sucursales.size()));
        } while (sucursalRandom.equals(excludeSucursal));
        return sucursalRandom;
    }

    // Método para listar las peticiones con resultados críticos
    public List<Integer> listarPeticionesConResultadoCritico() { //modificar a dto? solo devuelve enteros.
        List<Integer> peticionesCriticas = new ArrayList<>();
        for (Peticion peticion : peticiones) {
            for (Estudio estudio: peticion.getEstudios()) {
                if (estudio.isResultadoCritico()) {
                    peticionesCriticas.add(peticion.getPeticionID());
                }

            }
        }
        return peticionesCriticas;
    }

    public String showResultados(int peticionID, int codigoEstudio) {
        Estudio estudioBuscado = findEstudioInPeticion(peticionID,codigoEstudio);
        if (!estudioBuscado.tieneResultado()){
            return "No disponible";
        }else {
            /*if (estudioBuscado.isResultadoReservado()){
                return "Retirar por sucursal";
            } else if (estudioBuscado.isResultadoCritico()) {
                return "Resultado Critico contactar Paciente";
            }else {*/
                String descripcion = estudioBuscado.getResultado().getDescripcionResultado();
                Float valor = estudioBuscado.getResultado().getValorResultado();
                return descripcion==null ? Objects.toString(valor) : descripcion;
            /*}*/
        }
    }

    // Método para obtener la lista de sucursales (opcional)
    public List<SucursalDTO> getSucursales() {
        List<SucursalDTO> sucursalDTOS = new ArrayList<>();
        for (Sucursal sucursal : sucursales){
            sucursalDTOS.add(sucursal.toDTO());
        }
        return sucursalDTOS;
    }

    // Método para obtener la lista de peticiones (opcional)
    public List<PeticionDTO> getPeticiones() {
        List<PeticionDTO> peticionDTOS = new ArrayList<>();
        for (Peticion peticion : peticiones){
            peticionDTOS.add(peticion.toDTO());
        }
        return peticionDTOS;
    }



    public SucursalDTO getSucursal(SucursalDTO sucursalParam){ //Necesario porque la sucursal sufre actualizaciones
        int sucursalID = sucursalParam.getSucursalID();
        SucursalDTO sucursalEncontrada = null;

        for (Sucursal sucursal : sucursales){
            if (Objects.equals(sucursalID, sucursal.getSucursalID())){
                sucursalEncontrada = sucursal.toDTO();
            }
        }
        return  sucursalEncontrada;
    }

    protected Peticion findPeticion(int peticionID){
        Peticion peticionEncontrada = null;
        for(Peticion peticion : peticiones){
            if (peticion.getPeticionID() == peticionID){
                peticionEncontrada = peticion;
                break;
            }
        }
        return peticionEncontrada;
    }

    private Sucursal findSucursal(int sucursalID){
        Sucursal sucursalEncontrada = null;

        for(Sucursal sucursal : sucursales){
            if (sucursalID == sucursal.getSucursalID()){
                sucursalEncontrada = sucursal;
                break;
            }
        }
        return  sucursalEncontrada;
    }

    private Estudio findEstudioInPeticion(int peticionID, int codigoEstudio){
        Peticion peticionEncontrada = findPeticion(peticionID);
        Estudio estudioEncontrado = null;

        for(Estudio estudio : peticionEncontrada.getEstudios()){
            if (codigoEstudio == estudio.getCodigoEstudio()){
                estudioEncontrado = estudio;
                break;
            }
        }
        return  estudioEncontrado;
    }

    private Estudio findEstudioFromPractica(Peticion peticion, int codigoPractica){
        Estudio estudioEncontrado = null;

        for(Estudio estudio : peticion.getEstudios()){
            if (estudio.getPractica().getCodigoPractica() == codigoPractica){
                estudioEncontrado = estudio;
                break;
            }
        }
        return  estudioEncontrado;
    }

    public SucursalDTO obtenerSucursalOfPeticion(int peticionID){
        SucursalDTO sucursalDTO = null;

        for(Sucursal sucursal : sucursales){
            for (Peticion peticion : sucursal.getPeticiones()){
                if (peticion.getPeticionID() == peticionID){
                    sucursalDTO = sucursal.toDTO();
                    return sucursalDTO;
                }
            }
        }
        return  sucursalDTO;
    }

    public void addResultadoToEstudio(int peticionID, int codigoPractica, float valorResultado, String descripcionResultado){
        Peticion peticionEncontrada = findPeticion(peticionID);
        Estudio estudioEncontrado = findEstudioFromPractica(peticionEncontrada, codigoPractica);
        if (peticionEncontrada != null && estudioEncontrado != null){

            estudioEncontrado.setResultado(valorResultado, descripcionResultado);
            try {
                PeticionDAO peticionDAO = new PeticionDAO();
                peticionDAO.actualizarPeticion(peticionEncontrada.toDTO());

                ControladorPaciente controladorPaciente = ControladorPaciente.getInstance();
                Paciente paciente = controladorPaciente.getPacienteFromPeticion(peticionID);

                PacienteDAO pacienteDAO = new PacienteDAO();
                pacienteDAO.actualizarPaciente(paciente.toDTO());

                SucursalDAO sucursalDAO = new SucursalDAO();
                sucursalDAO.actualizarSucursal(obtenerSucursalOfPeticion(peticionID));

            } catch (Exception e) {
                System.out.println("Error ocurrido: " + e);
            }


            System.out.println(String.format("PeticionID: %d >>> PracticaID: %d --> Agregado Resultado >>> valorResultado: %f, descripcionResultado: %s", peticionID, codigoPractica, valorResultado, descripcionResultado));
        }

    }

    public void addEstudioToPeticion(EstudioDTO estudioDTO, PeticionDTO peticionDTO){
        int peticionID = peticionDTO.getPeticionID();
        int codigoPractica= estudioDTO.getPracticaDTO().getCodigoPractica();
        Peticion peticionEncontrada = findPeticion(peticionID);
        Practica practicaEncontrada = ControladorPractica.getInstance().findPractica(codigoPractica);

        if (peticionEncontrada != null && practicaEncontrada != null){

            peticionEncontrada.addEstudio(practicaEncontrada, estudioDTO.getResultadoDTO().getValorResultado(), estudioDTO.getResultadoDTO().getDescripcionResultado());
            try {
                PeticionDAO peticionDAO = new PeticionDAO();
                peticionDAO.actualizarPeticion(peticionEncontrada.toDTO());
            } catch (Exception e) {
                System.out.println("Error ocurrido: " + e);
            }

            System.out.println(String.format("PeticionID: %d --> Agregada Estudio para practica: %d", peticionID, estudioDTO.getPracticaDTO().getCodigoPractica()));

        }
    }

    public void addPeticionToSucursal( PeticionDTO peticionDTO, SucursalDTO sucursalDTO){
        int sucursalID = sucursalDTO.getSucursalID();
        int peticionID = peticionDTO.getPeticionID();
        Sucursal sucursalEncontrada = findSucursal(sucursalID);
        Peticion peticionEncontrada = findPeticion(peticionID);
        if (sucursalEncontrada != null && peticionEncontrada != null){

            sucursalEncontrada.addPeticion(peticionEncontrada);
            try {
                SucursalDAO sucursalDAO = new SucursalDAO();
                sucursalDAO.actualizarSucursal(sucursalEncontrada.toDTO());
            } catch (Exception e) {
                System.out.println("Error ocurrido: " + e);
            }

            System.out.println(String.format("SucursalID: %d --> Agregada PeticionID: %d", sucursalID, peticionID));

        }
    }
}
