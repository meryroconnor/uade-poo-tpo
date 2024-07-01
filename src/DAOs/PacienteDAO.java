package DAOs;



import Laboratorio.Paciente;

import java.io.FileNotFoundException;
import java.util.Objects;

public class PacienteDAO extends GenericDAO {

    public PacienteDAO() throws Exception {
        super(Paciente.class, "./src/goldenfiles/pacientes/pacientes_db");
    }

    public void CrearPaciente(Paciente paciente ) throws Exception {
        try {
            if (!Objects.isNull(ObtenerPaciente(paciente.getPacienteID()))){ //faltan getters and setters
                throw new Exception("Paciente ya existente");
            }
            this.save(paciente);
        } catch (Exception e) {
            throw new Exception("Error al crear el paciente: " + e.getMessage(), e);
        }
    }

    public boolean ActualizarPaciente(Paciente paciente) throws Exception {
        boolean fueActualizado = false;
        try {
            fueActualizado = this.update(paciente);
        } catch (Exception e) {
            throw new Exception("Error al actualizar el paciente: " + e.getMessage(), e);
        }

        return fueActualizado;
    }

    public boolean BorrarPaciente(int pacienteID) throws Exception {
        boolean fueBorrado = false;
        try {
            fueBorrado = this.delete(pacienteID);
        } catch (Exception e) {
            throw new Exception("Error al borrar el paciente: " + e.getMessage(), e);
        }
        return fueBorrado;
    }

    public Paciente ObtenerPaciente(int pacienteID) throws FileNotFoundException {
        Paciente pacienteDTO;
        try {
            pacienteDTO = (Paciente) this.search(pacienteID);
        } catch (FileNotFoundException e) {
            throw (e);
        }
        return pacienteDTO;
    }
}
