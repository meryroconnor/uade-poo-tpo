package DAOs;

import DTOs.EstudioDTO;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Objects;

public class EstudioDAO extends GenericDAO {

    public EstudioDAO() throws Exception {
        super(EstudioDTO.class, "./src/txtDataFiles/Estudio.db");
    }

    public void crearEstudio(EstudioDTO estudioDTO) throws Exception {
        try {
            if (!Objects.isNull(obtenerEstudio(estudioDTO))){
                throw new Exception("Obra Social Existente");
            }
            this.save(estudioDTO);
        } catch (Exception e) {
            throw new Exception("Error al crear la afiliado: " + e.getMessage(), e);
        }
    }

    public boolean actualizarAfiliado(EstudioDTO estudioDTO) throws Exception {
        boolean fueActualizado = false;
        try {
            fueActualizado = this.update2(estudioDTO, estudioDTO.getCodigoEstudio());
        } catch (Exception e) {
            throw new Exception("Error al actualizar al afiliado: " + e.getMessage(), e);
        }

        return fueActualizado;
    }

    public boolean borrarEstudio(EstudioDTO estudioDTO) throws Exception {
        int estudioID = estudioDTO.getCodigoEstudio();
        boolean fueBorrado = false;
        try {
            fueBorrado = this.delete(estudioID);
        } catch (Exception e) {
            throw new Exception("Error al borrar al afiliado: " + e.getMessage(), e);
        }
        return fueBorrado;
    }

    public EstudioDTO obtenerEstudio(EstudioDTO estudioDTOParam) throws FileNotFoundException {
        String estudioID = Integer.toString(estudioDTOParam.getCodigoEstudio());
        EstudioDTO estudioDTO;
        try {
            estudioDTO = (EstudioDTO) this.searchByAttribute("codigoEstudio", estudioID);
        } catch (FileNotFoundException e) {
            throw (e);
        }
        return estudioDTO;
    }

    public List<EstudioDTO> obtenerEstudios() throws FileNotFoundException {
        List<EstudioDTO> estudios;
        try {
            estudios = (List<EstudioDTO>) this.getAll();
        } catch (Exception e) {
            throw new RuntimeException (e);
        }
        return estudios;
    }
}