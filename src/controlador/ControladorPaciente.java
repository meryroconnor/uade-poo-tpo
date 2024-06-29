package Laboratorio;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ControladorPaciente {
    private static ControladorPaciente instance;
    private List<Paciente> pacientes;
    private int nextPacienteID;

    // Constructor privado para implementar el patrón Singleton
    private ControladorPaciente() {
        this.pacientes = new ArrayList<>();
        this.nextPacienteID = 1;
    }

    // Método para obtener la única instancia de ControladorPaciente (Singleton)
    public static ControladorPaciente getInstance() {
        if (instance == null) {
            instance = new ControladorPaciente();
        }
        return instance;
    }

    // Método para crear un nuevo Paciente
    public Paciente createPaciente(String nombreApellido, String sexo, String DNI, String email) {
        Paciente paciente = new Paciente(nextPacienteID++, nombreApellido,sexo, DNI, email);
        pacientes.add(paciente);
        return paciente;
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
    public List<Paciente> getPacientes() {
        return pacientes;
    }

    //Buscar paciente
    public List<Paciente> buscarPaciente(String pacienteID, String dni) {
        List<Paciente> resultados = new ArrayList<>();
        for (Paciente paciente : pacientes) {
            if ((pacienteID != null && !pacienteID.isEmpty() && paciente.getPacienteID() == Integer.parseInt(pacienteID)) ||
                    (dni != null && !dni.isEmpty() && paciente.getDNI().equals(dni))) {
                resultados.add(paciente);
            }
        }
        return resultados;
    }
}


