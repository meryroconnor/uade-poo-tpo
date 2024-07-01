package DAOs;

import Laboratorio.User;

import java.io.FileNotFoundException;
import java.util.Objects;

public class UserDAO extends GenericDAO {

    public UserDAO() throws Exception {
        super(User.class, "./src/goldenfiles/users/users_db");
    }//chequear el file

    public void CrearUser(User p) throws Exception {
        try {
            if (!Objects.isNull(ObtenerUser(p.getUserID()))){ //faltan getters and setters
                throw new Exception("Usuario ya existente");
            }
            this.save(p);
        } catch (Exception e) {
            throw new Exception("Error al crear el Usuario: " + e.getMessage(), e);
        }
    }

    public boolean ActualizarUser(User p) throws Exception {
        boolean fueActualizado = false;
        try {
            fueActualizado = this.update(p);
        } catch (Exception e) {
            throw new Exception("Error al actualizar el Usuario: " + e.getMessage(), e);
        }

        return fueActualizado;
    }

    public boolean BorrarUser (int userID) throws Exception {
        boolean fueBorrado = false;
        try {
            fueBorrado = this.delete(userID);
        } catch (Exception e) {
            throw new Exception("Error al borrar al Usuario: " + e.getMessage(), e);
        }
        return fueBorrado;
    }

    public User ObtenerUser(int userID) throws FileNotFoundException {
        User userDTO;
        try {
            userDTO = (User) this.search(userID);
        } catch (FileNotFoundException e) {
            throw (e);
        }
        return userDTO;
    }
}
