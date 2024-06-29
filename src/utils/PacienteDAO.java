package utils;



import Laboratorio.Paciente;

import java.io.FileNotFoundException;
import java.util.Objects;

public class PacienteDAO extends utils.GenericDAO {

    public PacienteDAO() throws Exception {
        super(Paciente.class, "./src/goldenfiles/pacientes/pacientes_db");
    }

    public void CrearPaciente(Paciente p) throws Exception {
        try {
            if (!Objects.isNull(ObtenerPaciente(p.pacienteID))){ //faltan getters and setters
                throw new Exception("Paciente ya existente");
            }
            this.save(p);
        } catch (Exception e) {
            throw (e);
        }
    }

    public boolean ActualizarPaciente(Paciente p) throws Exception {
        boolean fueActualizado = false;
        try {
            fueActualizado = this.update(p);
        } catch (Exception e) {
            throw (e);
        }

        return fueActualizado;
    }

    public boolean BorrarPaciente(int pacienteID) throws Exception {
        boolean fueBorrado = false;
        try {
            fueBorrado = this.delete(pacienteID);
        } catch (Exception e) {
            throw (e);
        }
        return fueBorrado;
    }

    public Paciente ObtenerPaciente(int pacienteID) throws FileNotFoundException {
        Paciente pacienteDTO = new Paciente();
        try {
            pacienteDTO = (Paciente) this.search(pacienteID);
        } catch (Exception e) {
            throw (e);
        }
        return pacienteDTO;
    }
}
