package DAOs;

import DTOs.UserDTO;
import Laboratorio.User;

import java.io.FileNotFoundException;
import java.util.Objects;

public class UserDAO extends GenericDAO {

    public UserDAO() throws Exception {
        super(User.class, "./src/txtDataFiles/Users.db");
    }//chequear el file

    public void CrearUser(UserDTO userDTO) throws Exception {
        try {
            if (!Objects.isNull(ObtenerUser(userDTO))){
                throw new Exception("Usuario ya existente");
            }
            this.save(userDTO);
        } catch (Exception e) {
            throw new Exception("Error al crear el Usuario: " + e.getMessage(), e);
        }
    }

    public boolean ActualizarUser(UserDTO userDTO) throws Exception {
        boolean fueActualizado = false;
        try {
            fueActualizado = this.update(userDTO);
        } catch (Exception e) {
            throw new Exception("Error al actualizar el Usuario: " + e.getMessage(), e);
        }

        return fueActualizado;
    }

    public boolean BorrarUser (UserDTO userDTO) throws Exception {
        int userID = userDTO.getUserID();
        boolean fueBorrado = false;
        try {
            fueBorrado = this.delete(userID);
        } catch (Exception e) {
            throw new Exception("Error al borrar al Usuario: " + e.getMessage(), e);
        }
        return fueBorrado;
    }

    public UserDTO ObtenerUser(UserDTO userDTOParam) throws FileNotFoundException {
        int userID = userDTOParam.getUserID();
        UserDTO userDTO;
        try {
            userDTO = (UserDTO) this.search(userID);
        } catch (FileNotFoundException e) {
            throw (e);
        }
        return userDTO;
    }
}
