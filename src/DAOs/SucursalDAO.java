package DAOs;

import DTOs.SucursalDTO;

import java.io.FileNotFoundException;
import java.util.Objects;

public class SucursalDAO extends GenericDAO {

    public SucursalDAO() throws Exception {
        super(SucursalDTO.class, "./src/txtDataFiles/Sucursal/Sucursal_db");
    }//chequear el file

    public void CrearSucursal(SucursalDTO sucursalDTO) throws Exception {
        try {
            if (!Objects.isNull(ObtenerSucursal(sucursalDTO))){
                throw new Exception("Sucursal ya existente");
            }
            this.save(sucursalDTO);
        } catch (Exception e) {
            throw new Exception("Error al crear la Sucursal: " + e.getMessage(), e);
        }
    }

    public boolean ActualizarSucursal(SucursalDTO sucursalDTO) throws Exception {
        boolean fueActualizado = false;
        try {
            fueActualizado = this.update(sucursalDTO);
        } catch (Exception e) {
            throw new Exception("Error al actualizar la Sucursal: " + e.getMessage(), e);
        }

        return fueActualizado;
    }

    public boolean BorrarSucursal (SucursalDTO sucursalDTO) throws Exception {
        int sucursalID = sucursalDTO.getSucursalID();
        boolean fueBorrado = false;
        try {
            fueBorrado = this.delete(sucursalID);
        } catch (Exception e) {
            throw new Exception("Error al borrar la Sucursal: " + e.getMessage(), e);
        }
        return fueBorrado;
    }

    public SucursalDTO ObtenerSucursal(SucursalDTO sucursalDTOParam) throws FileNotFoundException {
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
