import DTOs.*;
import controlador.ControladorAtencion;
import controlador.ControladorPaciente;
import controlador.ControladorPractica;
import controlador.ControladorUsuario;

import java.util.List;


public class Main {
    public static void main(String[] args) {
        ControladorAtencion controladorAtencion = ControladorAtencion.getInstance();
        ControladorPaciente controladorPaciente = ControladorPaciente.getInstance();
        ControladorPractica controladorPractica = ControladorPractica.getInstance();
        ControladorUsuario controladorUsuario = ControladorUsuario.getInstance();

        //Crear Obras Sociales
        controladorPaciente.createObraSocial("Osxe");
        controladorPaciente.createObraSocial("Osec");
        controladorPaciente.createObraSocial("IOFA");

        // Crear sucursales
        SucursalDTO sucursal1 = controladorAtencion.createSucursal("Sucursal Centro", 1);
        SucursalDTO sucursal2 = controladorAtencion.createSucursal("Sucursal Norte", 2);

        // Crear pacientes
        //PacienteDTO paciente1 = controladorPaciente.createPaciente("Juan Perez", "M", "39644881",  "jperez@gmail.com", obra1);
        //PacienteDTO paciente2 = controladorPaciente.createPaciente("Maria Gomez", "F", "39655771", "mgomez@gmail.com", obra2);


        // Crear peticiones y asociarlas a sucursales y pacientes
        PeticionDTO peticion1 = controladorAtencion.createPeticion();
        PracticaDTO practica1 = controladorPractica.createPractica(null, 0.20f, 3.0f, "POSITIVO", null, null);

        //peticion1.addPractica(practica1);
        controladorAtencion.addPracticaToPeticion(peticion1, practica1);

        //peticion1.addResultado(9.5f, "POSITIVO", practica1);
        controladorAtencion.addResultadoToPeticion(peticion1, practica1, 9.5f, "POSITIVO");

        PeticionDTO peticion2 = controladorAtencion.createPeticion();
        PracticaDTO practica2 = controladorPractica.createPractica( "POSITIVO", null, null, "POSITIVO", 0f, 0f);

        //peticion2.addPractica(practica2);
        controladorAtencion.addPracticaToPeticion(peticion2, practica2);

        //sucursal1.addPeticion(peticion1);
        controladorAtencion.addPeticionToSucursal(sucursal1, peticion1);

        //sucursal2.addPeticion(peticion2);
        controladorAtencion.addPeticionToSucursal(sucursal2, peticion2);

        //paciente1.addPeticion(peticion1);
        //controladorAtencion.addPeticionToPaciente(paciente1, peticion1);

        //paciente2.addPeticion(peticion2);
        //controladorAtencion.addPeticionToPaciente(paciente2, peticion2);

        // Mostrar resultados
        controladorAtencion.showResultados(1);
        // Listar peticiones con resultado critico:
        List<Integer> criticas = controladorAtencion.listarPeticionesConResultadoCritico();
        System.out.println("Lista de peticiones con valores criticos:" + criticas);


        // Intentar eliminar sucursales
        controladorAtencion.deleteSucursal(1); // Transferir peticiones activas y eliminar
        controladorAtencion.deleteSucursal(2); // No se puede eliminar porque no hay otras sucursales

        // Intentar eliminar pacientes
        controladorPaciente.deletePaciente(1); // No se puede eliminar
        controladorPaciente.deletePaciente(2); // Se puede eliminar

        //Creacion de Usuarios
        //UserDTO usuario1 = controladorUsuario.crearUsuario("FedeB", "123", "feder@gmail.com", "fede", "1234", "admin");


        //DAO testing
//        ObraSocialDAO obraSocialDAO = null;
//        PacienteDAO pacienteDAO = null;
//        PeticionDAO peticionDAO = null;
//        PracticaDAO practicaDAO = null;
//        SucursalDAO sucursalDAO = null;
//        UserDAO userDAO = null;
//        try {
//            obraSocialDAO = new ObraSocialDAO();
//            pacienteDAO = new PacienteDAO();
//            peticionDAO = new PeticionDAO();
//            practicaDAO = new PracticaDAO();
//            sucursalDAO = new SucursalDAO();
//            userDAO = new UserDAO();
//
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//        try {
//            obraSocialDAO.crearObraSocial(obra1);
//            obraSocialDAO.borrarObraSocial(obra1);
//            obraSocialDAO.crearObraSocial(obra2);
//            pacienteDAO.crearPaciente(paciente1);
//            pacienteDAO.crearPaciente(paciente2);
//            pacienteDAO.borrarPaciente(paciente1);
//            peticionDAO.crearPeticion(peticion1);
//            peticionDAO.crearPeticion(peticion2);
//            peticionDAO.borrarPeticion(peticion1);
//            practicaDAO.crearPractica(practica1);
//            practicaDAO.crearPractica(practica2);
//            practicaDAO.borrarPractica(practica1);
//            sucursalDAO.crearSucursal(sucursal1);
//            sucursalDAO.crearSucursal(sucursal2);
//            sucursalDAO.borrarSucursal(sucursal1);
//            userDAO.crearUser(usuario1);
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
    }
}

