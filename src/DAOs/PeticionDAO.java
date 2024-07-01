package DAOs;

import Laboratorio.Peticion;

import java.io.FileNotFoundException;
import java.util.Objects;

public class PeticionDAO extends GenericDAO {

    public PeticionDAO() throws Exception {
        super(Peticion.class, "./src/goldenfiles/peticiones/peticiones_db");
    }//chequear el file

    public void CrearPeticion(Peticion peticion) throws Exception {
        try {
            if (!Objects.isNull(CrearPeticion(peticion.getPeticionID()))){ //faltan getters and setters
                throw new Exception("Peticion ya existente");
            }
            this.save(peticion);
        } catch (Exception e) {
            throw new Exception("Error al crear la Peticion: " + e.getMessage(), e);
        }
    }

    public boolean ActualizarPeticion(Peticion peticion) throws Exception {
        boolean fueActualizado = false;
        try {
            fueActualizado = this.update(peticion);
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

    public Peticion ObtenerPaciente(int peticionID) throws FileNotFoundException {
        Peticion peticionDTO;
        try {
            peticionDTO = (Peticion) this.search(peticionID);
        } catch (FileNotFoundException e) {
            throw (e);
        }
        return peticionDTO;
    }
}
