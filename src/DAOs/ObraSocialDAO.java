package DAOs;
import DTOs.ObraSocialDTO;

import java.io.FileNotFoundException;
import java.util.Objects;

public class ObraSocialDAO extends GenericDAO {

    public ObraSocialDAO() throws Exception {
        super(ObraSocialDTO.class, "./src/txtDataFiles/ObraSocial/ObraSocial_db");
    }

    public void CrearAfiliado(ObraSocialDTO obraSocialDTO) throws Exception {
        try {
            if (!Objects.isNull(ObtenerAfiliado(obraSocialDTO.getNumeroAfiliado()))){
                throw new Exception("numero afiliado ya existente");
            }
            this.save(obraSocialDTO);
        } catch (Exception e) {
            throw new Exception("Error al crear la afiliado: " + e.getMessage(), e);
        }
    }

    public boolean ActualizarAfiliado(ObraSocialDTO obraSocialDTO) throws Exception {
        boolean fueActualizado = false;
        try {
            fueActualizado = this.update(obraSocialDTO);
        } catch (Exception e) {
            throw new Exception("Error al actualizar al afiliado: " + e.getMessage(), e);
        }

        return fueActualizado;
    }

    public boolean BorrarAfiliado(int numeroAfiliado) throws Exception { // reemplazar con numero de afiliacion.
        boolean fueBorrado = false;
        try {
            fueBorrado = this.delete(numeroAfiliado);
        } catch (Exception e) {
            throw new Exception("Error al borrar al afiliado: " + e.getMessage(), e);
        }
        return fueBorrado;
    }

    public ObraSocialDTO ObtenerAfiliado(int numeroAfiliado) throws FileNotFoundException {
        ObraSocialDTO obraSocialDTO;
        try {
            obraSocialDTO = (ObraSocialDTO) this.search(numeroAfiliado);
        } catch (FileNotFoundException e) {
            throw (e);
        }
        return obraSocialDTO;
    }
}
