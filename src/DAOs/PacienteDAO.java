package DAOs;


import DTOs.PacienteDTO;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PacienteDAO extends GenericDAO {

    public PacienteDAO() throws Exception {
        super(PacienteDTO.class, "./src/txtDataFiles/Pacientes.db");
    }

    public void crearPaciente(PacienteDTO pacienteDTO ) throws Exception {
        try {
            if (!Objects.isNull(obtenerPaciente(pacienteDTO))){
                throw new Exception("Paciente ya existente");
            }
            this.save(pacienteDTO);
        } catch (Exception e) {
            throw new Exception("Error al crear el paciente: " + e.getMessage(), e);
        }
    }

    public boolean actualizarPaciente(PacienteDTO pacienteDTO) throws Exception {
        boolean fueActualizado = false;
        try {
            fueActualizado = this.update(pacienteDTO); //1 - parametro DTO
        } catch (Exception e) {
            throw new Exception("Error al actualizar el paciente: " + e.getMessage(), e);
        }

        return fueActualizado;
    }

    public boolean borrarPaciente(PacienteDTO pacienteDTO) throws Exception {
        int pacienteID = pacienteDTO.getPacienteID();
        boolean fueBorrado = false;
        try {
            fueBorrado = this.delete(pacienteID);
        } catch (Exception e) {
            throw new Exception("Error al borrar el paciente: " + e.getMessage(), e);
        }
        return fueBorrado;
    }

    public PacienteDTO obtenerPaciente(PacienteDTO pacienteDTOParam) throws FileNotFoundException { //idem anterior
        int pacienteID = pacienteDTOParam.getPacienteID();
        PacienteDTO pacienteDTO;
        try {
            pacienteDTO = (PacienteDTO) this.search(pacienteID);
        } catch (FileNotFoundException e) {
            throw (e);
        }
        return pacienteDTO;
    }

    public List<PacienteDTO> obtenerPacientes() throws FileNotFoundException {
        List<PacienteDTO> pacientes = new ArrayList<>();
        try {
            pacientes = (List<PacienteDTO>) this.getAll();
        } catch (Exception e) {
            throw new RuntimeException (e);
        }
        return pacientes;
    }
}
