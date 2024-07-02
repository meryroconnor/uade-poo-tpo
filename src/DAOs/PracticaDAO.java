package DAOs;

import DTOs.PracticaDTO;
import Laboratorio.Practica;

import java.io.FileNotFoundException;
import java.util.Objects;

public class PracticaDAO extends GenericDAO {

    public PracticaDAO() throws Exception {
        super(Practica.class, "./src/txtDataFiles/Practica/Practica_db");
    }//chequear el file

    public void CrearPractica(PracticaDTO practicaDTO) throws Exception {
        try {
            if (!Objects.isNull(ObtenerPractica(practicaDTO.getCodigoPractica()))){ //faltan getters and setters
                throw new Exception("Practica ya existente");
            }
            this.save(practicaDTO);
        } catch (Exception e) {
            throw new Exception("Error al crear la Practica: " + e.getMessage(), e);
        }
    }

    public boolean ActualizarPractica(PracticaDTO practicaDTO) throws Exception {
        boolean fueActualizado = false;
        try {
            fueActualizado = this.update(practicaDTO);
        } catch (Exception e) {
            throw new Exception("Error al actualizar la Practica: " + e.getMessage(), e);
        }

        return fueActualizado;
    }

    public boolean BorrarPractica (int codigoPractica) throws Exception {
        boolean fueBorrado = false;
        try {
            fueBorrado = this.delete(codigoPractica);
        } catch (Exception e) {
            throw new Exception("Error al borrar la Practica: " + e.getMessage(), e);
        }
        return fueBorrado;
    }

    public PracticaDTO ObtenerPractica(int codigoPractica) throws FileNotFoundException {
        PracticaDTO practicaDTO;
        try {
            practicaDTO = (PracticaDTO) this.search(codigoPractica);
        } catch (FileNotFoundException e) {
            throw (e);
        }
        return practicaDTO;
    }
}
