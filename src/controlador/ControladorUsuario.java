package controlador;

import DAOs.UserDAO;
import DTOs.UserDTO;
import Laboratorio.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ControladorUsuario {
    private static ControladorUsuario instance;
    private  List<User> usuarios;
    private int nextUserID;

    private ControladorUsuario(){
        nextUserID = 1;
        this.usuarios = new ArrayList<>();
    }

    public static ControladorUsuario getInstance(){
        if (instance == null){
            instance = new ControladorUsuario();
        }
        return instance;
    }

    private void saveUsuarioToDAO(UserDTO userParam){
        try{
            UserDAO userDAO = new UserDAO();
            userDAO.crearUser(userParam);
            System.out.println(String.format("Usuario Creado: %s", userParam.getUsername()));
        } catch (Exception e){
            System.out.println("Usuario Existente: " + e);
        }
    }

    private UserDTO getUsuarioFromDAO(UserDTO userParam){
        UserDTO userEncontrado = null;
        try{
            UserDAO userDAO = new UserDAO();
            userEncontrado = userDAO.obtenerUser(userParam);
            System.out.println(String.format("Usuario Encontrado: %s", userParam.getUsername()));

        } catch (Exception e){
            System.out.println("Usuario No Existente: " + e);
        }
        return userEncontrado;
    }

    public UserDTO checkCredentials(UserDTO userParam){
        UserDTO usuarioDAO = null;
        usuarioDAO = getUsuarioFromDAO(userParam);
        if (usuarioDAO != null){
            if (Objects.equals(userParam.getPassword(), usuarioDAO.getPassword())){
                return usuarioDAO;
            }
        }
        return null;
    }

    public void crearUsuario(UserDTO userParam){
        User user = new User(nextUserID++, userParam.getNombreApellido(), userParam.getDNI(), userParam.getEmail(), userParam.getUsername(), userParam.getPassword(), userParam.getRol());
        usuarios.add(user);
        saveUsuarioToDAO(user.toDTO());
    } // tal vez no haria falta un metodo para obtener el usuario se supone que una vez que se creo no se toca mas.

    public void eliminarUsuario(int userID){
        boolean usuarioEncontrado = false;
        for (User usuario : usuarios){
            if (usuario.getUserID() == userID){
                usuarioEncontrado = true;
                usuarios.remove(usuario);
                break;
            }
        }
        if (usuarioEncontrado == true){
            System.out.println(String.format("UsuarioID: %d Eliminado", userID));
        } else {
            System.out.println(String.format("UsuarioID: %d No Encontrado", userID));
        }
    }
}


