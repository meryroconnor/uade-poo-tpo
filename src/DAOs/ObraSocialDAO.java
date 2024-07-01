package DAOs;



import Laboratorio.ObraSocial;

import java.io.FileNotFoundException;
import java.util.Objects;

public class ObraSocialDAO extends GenericDAO {

    public ObraSocialDAO() throws Exception {
        super(ObraSocial.class, "./src/goldenfiles/ObraSocial/ObraSocial_db");
    }

    public void CrearAfiliado(ObraSocial obraSocial) throws Exception {
        try {
            if (!Objects.isNull(ObtenerAfiliado(obraSocial.getNumeroAfiliado()))){ //faltan getters and setters
                throw new Exception("numero afiliado ya existente");
            }
            this.save(obraSocial);
        } catch (Exception e) {
            throw new Exception("Error al crear la afiliado: " + e.getMessage(), e);
        }
    }

    public boolean ActualizarAfiliado(ObraSocial obraSocial) throws Exception {
        boolean fueActualizado = false;
        try {
            fueActualizado = this.update(obraSocial);
        } catch (Exception e) {
            throw new Exception("Error al actualizar al afiliado: " + e.getMessage(), e);
        }

        return fueActualizado;
    }

    public boolean BorrarAfiliado(int numeroAfiliado) throws Exception {
        boolean fueBorrado = false;
        try {
            fueBorrado = this.delete(numeroAfiliado);
        } catch (Exception e) {
            throw new Exception("Error al borrar al afiliado: " + e.getMessage(), e);
        }
        return fueBorrado;
    }

    public ObraSocial ObtenerAfiliado(int numeroAfiliado) throws FileNotFoundException {
        ObraSocial ObraSocialDTO;
        try {
            ObraSocialDTO = (ObraSocial) this.search(numeroAfiliado);
        } catch (FileNotFoundException e) {
            throw (e);
        }
        return ObraSocialDTO;
    }
}
