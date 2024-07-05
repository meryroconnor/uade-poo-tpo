package controlador;

import DAOs.PeticionDAO;
import DAOs.SucursalDAO;
import DTOs.PacienteDTO;
import DTOs.PeticionDTO;
import DTOs.PracticaDTO;
import DTOs.SucursalDTO;
import Laboratorio.*;

import java.util.ArrayList;
import java.util.List;
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
    public void createPeticion() {
        Peticion peticion = new Peticion(nextPeticionID++);

        if (getPeticion(peticion.toDTO()) == null){
            peticiones.add(peticion);
            if (getPeticionFromDAO(peticion.toDTO()) == null){
                savePeticionToDAO(peticion.toDTO());
            }
        } else{
            peticion = null;
            System.out.println("Peticion Existente Cancelando Operacion");
        }
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

    public PeticionDTO getPeticion(PeticionDTO peticionParam){
        PeticionDTO peticionEncontrada = null;
        for (Peticion peticion : peticiones){
            if(peticionParam.getPeticionID() == peticion.getPeticionID()){
                peticionEncontrada = peticion.toDTO();
                break;
            }
        }
        return peticionEncontrada;
    }



    // Método para crear una nueva Sucursal
    public void createSucursal(SucursalDTO sucursalParam) {
        Sucursal sucursal = new Sucursal(nextSucursalID++, sucursalParam.getDireccion(), sucursalParam.getResponsableMatricula());
        if (obtenerSucursal(sucursal.toDTO()) == null){
            sucursales.add(sucursal);
            if (getSucursalFromDAO(sucursal.toDTO()) == null){
                saveSucursalToDAO(sucursal.toDTO());
            }
        } else {
            sucursal = null;
            System.out.println("Sucursal Existente Cancelando Operacion");
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



    // SUCURSAL

    private  void bootControllerFromDaoToModel(){
        List<SucursalDTO> sucursalDTOS;
        sucursalDTOS = getSucursalesFromDAO();

        List<PeticionDTO> peticionDTOS;
        peticionDTOS = getPeticionesFromDAO();

        for(SucursalDTO sucursalDTO : sucursalDTOS){
            createSucursal(sucursalDTO);
        }

        for(PeticionDTO peticionDTO : peticionDTOS){
            createPeticion();
        }

        for(SucursalDTO sucursalDTO : sucursalDTOS){
            Sucursal sucursal = findSucursal(sucursalDTO.getSucursalID());
            for (PeticionDTO peticionDTO : sucursalDTO.getPeticionesDTO()){
                Peticion peticion = findPeticion(peticionDTO.getPeticionID());
                sucursal.addPeticion(peticion);
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
            if (peticion.tieneResultados()) {
                peticionesActivas.add(peticion);
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
            for (Resultado resultado : peticion.getResultados()) {
                if (resultado.isResultadoCritico()){
                    peticionesCriticas.add(peticion.getPeticionID());
                }
            }
        }
        return peticionesCriticas;
    }

    public void showResultados(int peticionID) {
        Peticion peticionBuscada = null;
        for (Peticion peticion : peticiones) {
            if (peticion.getPeticionID() == peticionID) {
                peticionBuscada = peticion;
                for (Resultado resultado: peticion.getResultados()){
                    System.out.println("RESULTADOS PETICION #"+ peticionID);
                    if(resultado.isResultadoReservado()){
                        System.out.println("Practica #" +resultado.getPractica().getCodigoPractica() + "= RETIRAR POR SUCURSAL");
                    } else {
                        System.out.println("Practica #" +resultado.getPractica().getCodigoPractica()+ "=" + resultado.getDescripcionResultado() + resultado.getValorResultado());
                    }
                    break;
                }
            }
        }
        if (peticionBuscada == null) {
            System.out.println("La peticion solicitada no existe.");
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

    public PeticionDTO obtenerPeticion(int peticionID){ //Necesario porque la peticion sufre actualizaciones
        Peticion peticionEncontrada = findPeticion(peticionID);
        if (peticionEncontrada == null){
            System.out.println(String.format("PeticionID: %d No Encontrada", peticionID));
        }
        return peticionEncontrada.toDTO(); // puede provocar Null!
    }

    public SucursalDTO obtenerSucursal(SucursalDTO sucursalParam){ //Necesario porque la sucursal sufre actualizaciones
        int sucursalID = sucursalParam.getSucursalID();
        Sucursal sucursalEncontrada = findSucursal(sucursalID);
        if (sucursalEncontrada == null){
            System.out.println(String.format("SucursalID: %d No Encontrada", sucursalID));
        }
        return sucursalEncontrada.toDTO(); // puede provocar Null!
    }

    private Peticion findPeticion(int peticionID){
        Peticion peticionEncontrada = null;
        for(Peticion peticion : peticiones){
            if (peticion.getPeticionID() == peticionID){
                peticionEncontrada = peticion;
                break;
            }
        }
        return peticionEncontrada;
    }

    private Practica findPracticaInPeticion(Peticion peticion, int codigoPractica){
        Practica practicaEncontrada = null;
        for(Practica practica : peticion.getPracticas()){
            if (practica.getCodigoPractica() == codigoPractica){
                practicaEncontrada = practica;
                break;
            }
        }
        return practicaEncontrada;
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

    public void addResultadoToPeticion(PeticionDTO peticionDTO, PracticaDTO practicaDTO, float valorResultado, String descripcionResultado){
        int peticionID = peticionDTO.getPeticionID();
        Peticion peticionEncontrada = findPeticion(peticionID);
        Practica practicaEncontrada = findPracticaInPeticion(peticionEncontrada, practicaDTO.getCodigoPractica());
        if (peticionEncontrada != null && practicaEncontrada != null){

            peticionEncontrada.addResultado(valorResultado, descripcionResultado, practicaEncontrada);

            System.out.println(String.format("PeticionID: %d >>> PracticaID: %d --> Agregado Resultado >>> valorResultado: %f, descripcionResultado: %s", peticionID, practicaDTO.getCodigoPractica(), valorResultado, descripcionResultado));
        }

    }

    public void addPracticaToPeticion(PeticionDTO peticionDTO, PracticaDTO practicaDTO){
        int peticionID = peticionDTO.getPeticionID();
        int practicaID = practicaDTO.getCodigoPractica();
        Peticion peticionEncontrada = findPeticion(peticionID);
        Practica practicaEncontrada = ControladorPractica.getInstance().findPractica(practicaID);

        if (peticionEncontrada != null && practicaEncontrada != null){

            peticionEncontrada.addPractica(practicaEncontrada);

            System.out.println(String.format("PeticionID: %d --> Agregada PracticaID: %d", peticionID, practicaDTO.getCodigoPractica()));

        }
    }

    public void addPeticionToSucursal(SucursalDTO sucursalDTO, PeticionDTO peticionDTO){
        int sucursalID = sucursalDTO.getSucursalID();
        int peticionID = peticionDTO.getPeticionID();
        Sucursal sucursalEncontrada = findSucursal(sucursalID);
        Peticion peticionEncontrada = findPeticion(peticionID);
        if (sucursalEncontrada != null && peticionEncontrada != null){

            sucursalEncontrada.addPeticion(peticionEncontrada);

            System.out.println(String.format("SucursalID: %d --> Agregada PeticionID: %d", sucursalID, peticionID));

        }
    }

    public void addPeticionToPaciente(PacienteDTO pacienteDTO, PeticionDTO peticionDTO){
        int pacienteID = pacienteDTO.getPacienteID();
        int peticionID = peticionDTO.getPeticionID();
        Paciente pacienteEncontrado = ControladorPaciente.getInstance().findPaciente(pacienteID);
        Peticion peticionEncontrada = findPeticion(peticionID);
        if (pacienteEncontrado != null && peticionEncontrada != null){

            pacienteEncontrado.addPeticion(peticionEncontrada);

            System.out.println(String.format("PacienteID: %d --> Agregada PeticionID: %d", pacienteID, peticionID));

        }

    }
}
