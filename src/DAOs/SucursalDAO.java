package DAOs;

import DTOs.SucursalDTO;

import java.io.FileNotFoundException;
import java.util.Objects;

public class SucursalDAO extends GenericDAO {

    public SucursalDAO() throws Exception {
        super(SucursalDTO.class, "./src/txtDataFiles/Sucursal.db");
    }//chequear el file

    public void crearSucursal(SucursalDTO sucursalDTO) throws Exception {
        try {
            if (!Objects.isNull(obtenerSucursal(sucursalDTO))){
                throw new Exception("Sucursal ya existente");
            }
            this.save(sucursalDTO);
        } catch (Exception e) {
            throw new Exception("Error al crear la Sucursal: " + e.getMessage(), e);
        }
    }

    public boolean actualizarSucursal(SucursalDTO sucursalDTO) throws Exception {
        boolean fueActualizado = false;
        try {
            fueActualizado = this.update(sucursalDTO);
        } catch (Exception e) {
            throw new Exception("Error al actualizar la Sucursal: " + e.getMessage(), e);
        }

        return fueActualizado;
    }

    public boolean borrarSucursal (SucursalDTO sucursalDTO) throws Exception {
        int sucursalID = sucursalDTO.getSucursalID();
        boolean fueBorrado = false;
        try {
            fueBorrado = this.delete(sucursalID);
        } catch (Exception e) {
            throw new Exception("Error al borrar la Sucursal: " + e.getMessage(), e);
        }
        return fueBorrado;
    }

    public SucursalDTO obtenerSucursal(SucursalDTO sucursalDTOParam) throws FileNotFoundException {
        int sucursalID = sucursalDTOParam.getSucursalID();
        SucursalDTO sucursalDTO;
        try {
            sucursalDTO = (SucursalDTO) this.search(sucursalID);
        } catch (FileNotFoundException e) {
            throw (e);
        }
        return sucursalDTO;
    }
}
