package DAOs;

import Laboratorio.Practica;

import java.io.FileNotFoundException;
import java.util.Objects;

public class PracticaDAO extends GenericDAO {

    public PracticaDAO() throws Exception {
        super(Practica.class, "./src/goldenfiles/Practica/Practica_db");
    }//chequear el file

    public void CrearPractica(Practica p) throws Exception {
        try {
            if (!Objects.isNull(ObtenerPractica(p.getCodigoPractica()))){ //faltan getters and setters
                throw new Exception("Practica ya existente");
            }
            this.save(p);
        } catch (Exception e) {
            throw new Exception("Error al crear la Practica: " + e.getMessage(), e);
        }
    }

    public boolean ActualizarPractica(Practica p) throws Exception {
        boolean fueActualizado = false;
        try {
            fueActualizado = this.update(p);
        } catch (Exception e) {
            throw new Exception("Error al actualizar la Practica: " + e.getMessage(), e);
        }

        return fueActualizado;
    }

    public boolean BorrarPractica (int codigoPractica) throws Exception {
        boolean fueBorrado = false;
        try {
            fueBorrado = this.delete(codigoPractica);
        } catch (Exception e) {
            throw new Exception("Error al borrar la Practica: " + e.getMessage(), e);
        }
        return fueBorrado;
    }

    public Practica ObtenerPractica(int codigoPractica) throws FileNotFoundException {
        Practica practicaDTO;
        try {
            practicaDTO = (Practica) this.search(codigoPractica);
        } catch (FileNotFoundException e) {
            throw (e);
        }
        return practicaDTO;
    }
}
