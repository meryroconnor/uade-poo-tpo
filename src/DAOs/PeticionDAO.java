package DAOs;

import Laboratorio.Peticion;

import java.io.FileNotFoundException;
import java.util.Objects;

public class PeticionDAO extends GenericDAO {

    public PeticionDAO() throws Exception {
        super(Peticion.class, "./src/goldenfiles/peticiones/peticiones_db");
    }//chequear el file

    public void CrearPeticion(Peticion p) throws Exception {
        try {
            if (!Objects.isNull(ObtenerPeticion(p.getPeticionID()))){ //faltan getters and setters
                throw new Exception("Peticion ya existente");
            }
            this.save(p);
        } catch (Exception e) {
            throw new Exception("Error al crear la Peticion: " + e.getMessage(), e);
        }
    }

    public boolean ActualizarPeticion(Peticion p) throws Exception {
        boolean fueActualizado = false;
        try {
            fueActualizado = this.update(p);
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
