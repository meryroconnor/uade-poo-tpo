package DAOs;

import DTOs.UserDTO;

import java.io.FileNotFoundException;
import java.util.Objects;

public class UserDAO extends GenericDAO {

    public UserDAO() throws Exception {
        super(UserDTO.class, "./src/txtDataFiles/Users.db");
    }//chequear el file

    public void crearUser(UserDTO userDTO) throws Exception {
        try {
            if (!Objects.isNull(obtenerUser(userDTO))){
                throw new Exception("Usuario ya existente");
            }
            this.save(userDTO);
        } catch (Exception e) {
            throw new Exception("Error al crear el Usuario: " + e.getMessage(), e);
        }
    }

    public boolean actualizarUser(UserDTO userDTO) throws Exception {
        boolean fueActualizado = false;
        try {
            fueActualizado = this.update(userDTO);
        } catch (Exception e) {
            throw new Exception("Error al actualizar el Usuario: " + e.getMessage(), e);
        }

        return fueActualizado;
    }

    public boolean borrarUser (UserDTO userDTO) throws Exception {
        int userID = userDTO.getUserID();
        boolean fueBorrado = false;
        try {
            fueBorrado = this.delete(userID);
        } catch (Exception e) {
            throw new Exception("Error al borrar al Usuario: " + e.getMessage(), e);
        }
        return fueBorrado;
    }

    public UserDTO obtenerUser(UserDTO userDTOParam) throws FileNotFoundException {
        String username = userDTOParam.getUsername();
        UserDTO userDTO;
        try {
            userDTO = (UserDTO) this.searchByAttribute("username", userDTOParam.getUsername());
        } catch (FileNotFoundException e) {
            throw (e);
        }
        return userDTO;
    }
}
