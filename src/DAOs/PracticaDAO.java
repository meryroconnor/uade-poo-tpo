package DAOs;

import DTOs.PracticaDTO;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PracticaDAO extends GenericDAO {

    public PracticaDAO() throws Exception {
        super(PracticaDTO.class, "./src/txtDataFiles/Practica.db");
    }//chequear el file

    public void crearPractica(PracticaDTO practicaDTO) throws Exception {
        try {
            if (!Objects.isNull(obtenerPractica(practicaDTO))){
                throw new Exception("Practica ya existente");
            }
            this.save(practicaDTO);
        } catch (Exception e) {
            throw new Exception("Error al crear la Practica: " + e.getMessage(), e);
        }
    }

    public boolean actualizarPractica(PracticaDTO practicaDTO) throws Exception {
        boolean fueActualizado = false;
        try {
            fueActualizado = this.update(practicaDTO);
        } catch (Exception e) {
            throw new Exception("Error al actualizar la Practica: " + e.getMessage(), e);
        }

        return fueActualizado;
    }

    public boolean borrarPractica (PracticaDTO practicaDTO) throws Exception {
        int codigoPractica = practicaDTO.getCodigoPractica();
        boolean fueBorrado = false;
        try {
            fueBorrado = this.delete(codigoPractica);
        } catch (Exception e) {
            throw new Exception("Error al borrar la Practica: " + e.getMessage(), e);
        }
        return fueBorrado;
    }

    public PracticaDTO obtenerPractica(PracticaDTO practicaDTOParam) throws FileNotFoundException {
        int codigoPractica = practicaDTOParam.getCodigoPractica();
        PracticaDTO practicaDTO;
        try {
            practicaDTO = (PracticaDTO) this.search(codigoPractica);
        } catch (FileNotFoundException e) {
            throw (e);
        }
        return practicaDTO;
    }

    public List<PracticaDTO> obtenerPracticas() throws FileNotFoundException {
        List<PracticaDTO> practicas = new ArrayList<>();
        try {
            practicas = (List<PracticaDTO>) this.getAll();
        } catch (Exception e) {
            throw new RuntimeException (e);
        }
        return practicas;
    }


}
