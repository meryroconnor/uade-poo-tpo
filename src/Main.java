import DTOs.PacienteDTO;
import DTOs.ObraSocialDTO;
import DTOs.SucursalDTO;
import DTOs.PracticaDTO;
import DTOs.PeticionDTO;
import controlador.ControladorAtencion;
import controlador.ControladorPaciente;
import controlador.ControladorPractica;

import java.util.List;


public class Main {
    public static void main(String[] args) {
        ControladorAtencion controladorAtencion = ControladorAtencion.getInstance();
        ControladorPaciente controladorPaciente = ControladorPaciente.getInstance();
        ControladorPractica controladorPractica = ControladorPractica.getInstance();

        //Crear Obras Sociales
        ObraSocialDTO obra1 = controladorPaciente.createObraSocial("Osxe", 5001);
        ObraSocialDTO obra2 = controladorPaciente.createObraSocial("Osec", 5002);

        // Crear sucursales
        SucursalDTO sucursal1 = controladorAtencion.createSucursal("Sucursal Centro", 1);
        SucursalDTO sucursal2 = controladorAtencion.createSucursal("Sucursal Norte", 2);

        // Crear pacientes
        PacienteDTO paciente1 = controladorPaciente.createPaciente("Juan Perez", "M", "39644881",  "jperez@gmail.com", obra1);
        PacienteDTO paciente2 = controladorPaciente.createPaciente("Maria Gomez", "F", "39655771", "mgomez@gmail.com", obra2);


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
        controladorAtencion.addPeticionToPaciente(paciente1, peticion1);

        //paciente2.addPeticion(peticion2);
        controladorAtencion.addPeticionToPaciente(paciente2, peticion2);

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
    }
}

