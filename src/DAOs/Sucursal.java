package DAOs;

import Laboratorio.Sucursal;

import java.io.FileNotFoundException;
import java.util.Objects;

public class SucursalDAO extends GenericDAO {

    public SucursalDAO() throws Exception {
        super(Sucursal.class, "./src/goldenfiles/Sucursal/Sucursal_db");
    }//chequear el file

    public void CrearSucursal(Sucursal p) throws Exception {
        try {
            if (!Objects.isNull(ObtenerSucursal(p.getSucursalID()))){ //faltan getters and setters
                throw new Exception("Sucursal ya existente");
            }
            this.save(p);
        } catch (Exception e) {
            throw new Exception("Error al crear la Sucursal: " + e.getMessage(), e);
        }
    }

    public boolean ActualizarSucursal(Sucursal p) throws Exception {
        boolean fueActualizado = false;
        try {
            fueActualizado = this.update(p);
        } catch (Exception e) {
            throw new Exception("Error al actualizar la Sucursal: " + e.getMessage(), e);
        }

        return fueActualizado;
    }

    public boolean BorrarSucursal (int sucursalID) throws Exception {
        boolean fueBorrado = false;
        try {
            fueBorrado = this.delete(sucursalID);
        } catch (Exception e) {
            throw new Exception("Error al borrar la Sucursal: " + e.getMessage(), e);
        }
        return fueBorrado;
    }

    public Sucursal ObtenerSucursal(int sucursalID) throws FileNotFoundException {
        Sucursal sucursalDTO;
        try {
            sucursalDTO = (Sucursal) this.search(sucursalID);
        } catch (FileNotFoundException e) {
            throw (e);
        }
        return sucursalDTO;
    }
}
