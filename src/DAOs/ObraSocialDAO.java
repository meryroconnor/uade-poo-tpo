package DAOs;

import DTOs.ObraSocialDTO;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ObraSocialDAO extends GenericDAO {

    public ObraSocialDAO() throws Exception {
        super(ObraSocialDTO.class, "./src/txtDataFiles/ObraSocial.db");
    }

    public void crearObraSocial(ObraSocialDTO obraSocialDTO) throws Exception {
        try {
            if (!Objects.isNull(obtenerObraSocial(obraSocialDTO))){
                throw new Exception("Obra Social Existente");
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
        int ObraSocialID = obraSocialDTO.getObraSocialID();
        boolean fueBorrado = false;
        try {
            fueBorrado = this.delete(ObraSocialID);
        } catch (Exception e) {
            throw new Exception("Error al borrar al afiliado: " + e.getMessage(), e);
        }
        return fueBorrado;
    }

    public ObraSocialDTO obtenerObraSocial(ObraSocialDTO obraSocialDTOParam) throws FileNotFoundException {
        int ObraSocialID = obraSocialDTOParam.getObraSocialID();
        ObraSocialDTO obraSocialDTO;
        try {
            obraSocialDTO = (ObraSocialDTO) this.searchByAttribute("obraSocial", obraSocialDTOParam.getObraSocial());
        } catch (FileNotFoundException e) {
            throw (e);
        }
        return obraSocialDTO;
    }

    public List<ObraSocialDTO> obtenerObrasSociales() throws FileNotFoundException {
        List<ObraSocialDTO> obrasSociales = new ArrayList<>();
        try {
            obrasSociales = (List<ObraSocialDTO>) this.getAll();
        } catch (Exception e) {
            throw new RuntimeException (e);
        }
        return obrasSociales;
    }
}
