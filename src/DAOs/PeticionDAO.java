package DAOs;

import DTOs.PeticionDTO;

import java.io.FileNotFoundException;
import java.util.Objects;

public class PeticionDAO extends GenericDAO {

    public PeticionDAO() throws Exception {
        super(PeticionDTO.class, "./src/txtDataFiles/Peticiones.db");
    }//chequear el file

    public void CrearPeticion(PeticionDTO peticionDTO) throws Exception {
        try {
            if (!Objects.isNull(ObtenerPeticion(peticionDTO))){
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

    public boolean BorrarPeticion (PeticionDTO peticionDTO) throws Exception {
        int peticionID = peticionDTO.getPeticionID();
        boolean fueBorrado = false;
        try {
            fueBorrado = this.delete(peticionID);
        } catch (Exception e) {
            throw new Exception("Error al borrar la Peticion: " + e.getMessage(), e);
        }
        return fueBorrado;
    }

    public PeticionDTO ObtenerPeticion(PeticionDTO peticionDTOParam) throws FileNotFoundException {
        int peticionID = peticionDTOParam.getPeticionID();
        PeticionDTO peticionDTO;
        try {
            peticionDTO = (PeticionDTO) this.search(peticionID);
        } catch (FileNotFoundException e) {
            throw (e);
        }
        return peticionDTO;
    }
}
