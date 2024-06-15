import Laboratorio.*;


public class Main {
    public static void main(String[] args) {
        ControladorAtencion controladorAtencion = ControladorAtencion.getInstance();
        ControladorPaciente controladorPaciente = ControladorPaciente.getInstance();

        // Crear sucursales
        Sucursal sucursal1 = controladorAtencion.createSucursal("Sucursal Centro", 1);
        Sucursal sucursal2 = controladorAtencion.createSucursal("Sucursal Norte", 2);

        // Crear pacientes
        Paciente paciente1 = controladorPaciente.createPaciente("Juan Perez");
        Paciente paciente2 = controladorPaciente.createPaciente("Maria Gomez");

        // Crear peticiones y asociarlas a sucursales y pacientes
        Peticion peticion1 = controladorAtencion.createPeticion();
        Practica practica1 = new Practica(2001);
        peticion1.addPractica(practica1);
        peticion1.addResultado(9.5f, "Resultado de la practica 1");

        Peticion peticion2 = controladorAtencion.createPeticion();
        Practica practica2 = new Practica(2002);
        peticion2.addPractica(practica2);

        sucursal1.addPeticion(peticion1);
        sucursal2.addPeticion(peticion2);

        paciente1.addPeticion(peticion1);
        paciente2.addPeticion(peticion2);

        // Intentar eliminar sucursales
        controladorAtencion.deleteSucursal(1); // Transferir peticiones activas y eliminar
        controladorAtencion.deleteSucursal(2); // No se puede eliminar porque no hay otras sucursales

        // Intentar eliminar pacientes
        controladorPaciente.deletePaciente(1); // No se puede eliminar
        controladorPaciente.deletePaciente(2); // Se puede eliminar
    }
}

