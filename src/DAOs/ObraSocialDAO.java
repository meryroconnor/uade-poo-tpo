package DAOs;
import DTOs.ObraSocialDTO;

import java.io.FileNotFoundException;
import java.util.Objects;

public class ObraSocialDAO extends GenericDAO {

    public ObraSocialDAO() throws Exception {
        super(ObraSocialDTO.class, "./src/txtDataFiles/ObraSocial.db");
    }

    public void crearObraSocial(ObraSocialDTO obraSocialDTO) throws Exception {
        try {
            if (!Objects.isNull(obtenerAfiliado(obraSocialDTO))){
                throw new Exception("numero afiliado ya existente");
            }
            this.save(obraSocialDTO);
        } catch (Exception e) {
            throw new Exception("Error al crear la afiliado: " + e.getMessage(), e);
        }
    }

    public boolean actualizarAfiliado(ObraSocialDTO obraSocialDTO) throws Exception {
        boolean fueActualizado = false;
        try {
            fueActualizado = this.update(obraSocialDTO);
        } catch (Exception e) {
            throw new Exception("Error al actualizar al afiliado: " + e.getMessage(), e);
        }

        return fueActualizado;
    }

    public boolean borrarObraSocial(ObraSocialDTO obraSocialDTO) throws Exception {
        int numeroAfiliado = obraSocialDTO.getNumeroAfiliado();
        boolean fueBorrado = false;
        try {
            fueBorrado = this.delete(numeroAfiliado);
        } catch (Exception e) {
            throw new Exception("Error al borrar al afiliado: " + e.getMessage(), e);
        }
        return fueBorrado;
    }

    public ObraSocialDTO obtenerAfiliado(ObraSocialDTO obraSocialDTOParam) throws FileNotFoundException {
        int numeroAfiliado = obraSocialDTOParam.getNumeroAfiliado();
        ObraSocialDTO obraSocialDTO;
        try {
            obraSocialDTO = (ObraSocialDTO) this.search(numeroAfiliado);
        } catch (FileNotFoundException e) {
            throw (e);
        }
        return obraSocialDTO;
    }
}
