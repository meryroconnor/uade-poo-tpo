package DAOs;

import DTOs.PeticionDTO;

import java.io.FileNotFoundException;
import java.util.Objects;

public class PeticionDAO extends GenericDAO {

    public PeticionDAO() throws Exception {
        super(PeticionDTO.class, "./src/txtDataFiles/peticiones/peticiones_db");
    }//chequear el file

    public void CrearPeticion(PeticionDTO peticionDTO) throws Exception {
        try {
            if (!Objects.isNull(ObtenerPeticion(peticionDTO.getPeticionID()))){ //faltan getters and setters
                throw new Exception("Peticion ya existente");
            }
            this.save(peticionDTO);
        } catch (Exception e) {
            throw new Exception("Error al crear la Peticion: " + e.getMessage(), e);
        }
    }

    public boolean ActualizarPeticion(PeticionDTO peticionDTO) throws Exception {
        boolean fueActualizado = false;
        try {
            fueActualizado = this.update(peticionDTO);
        } catch (Exception e) {
            throw new Exception("Error al actualizar la Peticion: " + e.getMessage(), e);
        }

        return fueActualizado;
    }

    public boolean BorrarPeticion (int peticionID) throws Exception {
        boolean fueBorrado = false;
        try {
            fueBorrado = this.delete(peticionID);
        } catch (Exception e) {
            throw new Exception("Error al borrar la Peticion: " + e.getMessage(), e);
        }
        return fueBorrado;
    }

    public PeticionDTO ObtenerPeticion(int peticionID) throws FileNotFoundException {
        PeticionDTO peticionDTO;
        try {
            peticionDTO = (PeticionDTO) this.search(peticionID);
        } catch (FileNotFoundException e) {
            throw (e);
        }
        return peticionDTO;
    }
}
